package io.github.realyusufismail.bentenmod.client.alien;

import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.state.BoneSnapshot;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/** Shared GeckoLib renderer used by both loader-specific player render hooks. */
public final class AlienPlayerReplacementRenderer
        extends GeoReplacedEntityRenderer<AbstractClientPlayer, AlienPlayerAnimatable> {
    private static AlienPlayerReplacementRenderer instance;
    private final AlienPlayerAnimatable animatable;

    private AlienPlayerReplacementRenderer(EntityRendererProvider.Context context) {
        this(new AlienPlayerAnimatable(), context);
    }

    private AlienPlayerReplacementRenderer(AlienPlayerAnimatable animatable,
            EntityRendererProvider.Context context) {
        super(context, new AlienGeoModel<>(AlienPlayerAnimatable::alienId), animatable);
        this.animatable = animatable;
    }

    /** Gets the one renderer instance after Minecraft's client renderer context exists. */
    public static AlienPlayerReplacementRenderer get() {
        if (instance == null) {
            Minecraft minecraft = Minecraft.getInstance();
            EntityRendererProvider.Context context = new EntityRendererProvider.Context(
                    minecraft.getEntityRenderDispatcher(), minecraft.getItemRenderer(),
                    minecraft.getBlockRenderer(),
                    minecraft.getEntityRenderDispatcher().getItemInHandRenderer(),
                    minecraft.getResourceManager(), minecraft.getEntityModels(), minecraft.font);
            instance = new AlienPlayerReplacementRenderer(context);
        }
        return instance;
    }

    public static boolean isTransformed(AbstractClientPlayer player) {
        OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
        return snapshot != null && (snapshot.currentAlien() != null
                || ClientOmnitrixState.isMorphing(player.getUUID()));
    }

    public static void forgetPlayer(UUID playerId) {
        if (instance != null) {
            instance.animatable.forgetPlayer(playerId);
        }
    }

    public static void clearPlayerStates() {
        if (instance != null) {
            instance.animatable.clearPlayerStates();
        }
    }

    /**
     * Draws the alien's arm in place of the vanilla first-person arm. Returns false when the player
     * isn't transformed or the alien has no arm bone, so the caller falls back to vanilla.
     */
    public boolean renderFirstPersonArm(AbstractClientPlayer player, HumanoidArm arm,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
        ResourceLocation alien = snapshot == null ? null : snapshot.currentAlien();
        if (alien == null) {
            return false;
        }
        animatable.bind(player, alien, snapshot.expiresAt());
        BakedGeoModel model =
                getGeoModel().getBakedModel(getGeoModel().getModelResource(animatable));
        String side = arm == HumanoidArm.RIGHT ? "right" : "left";
        List<GeoBone> bones = Stream.of(side + "_arm", side + "_arm_lower")
            .map(model::getBone)
            .flatMap(Optional::stream)
            .toList();
        if (bones.isEmpty()) {
            return false;
        }
        GeoBone shoulder = bones.get(0);
        // GeckoLib stores bedrock X negated, so the vanilla right shoulder (-5, 2) in model space
        // is (+5, 22) here.
        float shoulderX = arm == HumanoidArm.RIGHT ? 5 : -5;
        RenderType renderType =
                getRenderType(animatable, getTextureLocation(animatable), bufferSource, 0);
        VertexConsumer buffer = bufferSource.getBuffer(renderType);

        poseStack.pushPose();
        // Vanilla arm space is the entity model space (y down, feet at 1.501); GeckoLib bones are
        // y up from the feet. Offset from vanilla's eye height so the hand follows the resized
        // view.
        poseStack.translate(0, 1.501 - (player.getEyeHeight() - 1.62F), 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.translate((shoulderX - shoulder.getPivotX()) / 16f,
                (22 - shoulder.getPivotY()) / 16f, -shoulder.getPivotZ() / 16f);
        for (GeoBone bone : bones) {
            renderAtRest(poseStack, bone, renderType, bufferSource, buffer, packedLight);
        }
        poseStack.popPose();
        return true;
    }

    /**
     * Renders a bone tree in its model pose, ignoring whatever a third-person render left behind.
     */
    private void renderAtRest(PoseStack poseStack, GeoBone bone, RenderType renderType,
            MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight) {
        List<GeoBone> tree = new ArrayList<>();
        collect(bone, tree);
        List<BoneSnapshot> saved = tree.stream().map(BoneSnapshot::new).toList();
        tree.forEach(AlienPlayerReplacementRenderer::resetToInitial);
        renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, true, 0,
                packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        for (int i = 0; i < tree.size(); i++) {
            restore(tree.get(i), saved.get(i));
        }
    }

    private static void collect(GeoBone bone, List<GeoBone> into) {
        into.add(bone);
        bone.getChildBones().forEach(child -> collect(child, into));
    }

    private static void resetToInitial(GeoBone bone) {
        restore(bone, bone.getInitialSnapshot());
    }

    private static void restore(GeoBone bone, BoneSnapshot snapshot) {
        bone.updateRotation(snapshot.getRotX(), snapshot.getRotY(), snapshot.getRotZ());
        bone.updatePosition(snapshot.getOffsetX(), snapshot.getOffsetY(), snapshot.getOffsetZ());
        bone.updateScale(snapshot.getScaleX(), snapshot.getScaleY(), snapshot.getScaleZ());
    }

    @Override
    public long getInstanceId(AlienPlayerAnimatable animatable) {
        return currentEntity == null ? 0 : currentEntity.getId();
    }

    @Override
    public void render(AbstractClientPlayer player, float entityYaw, float partialTick,
            com.mojang.blaze3d.vertex.PoseStack poseStack,
            net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
        ResourceLocation alien =
                snapshot == null ? null : ClientOmnitrixState.renderedAlien(player.getUUID());
        if (alien == null) {
            return;
        }
        animatable.bind(player, alien, snapshot.expiresAt());
        poseStack.pushPose();
        float scale = ClientOmnitrixState.modelScale(player.getUUID(), partialTick);
        poseStack.scale(scale, scale, scale);
        super.render(player, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
