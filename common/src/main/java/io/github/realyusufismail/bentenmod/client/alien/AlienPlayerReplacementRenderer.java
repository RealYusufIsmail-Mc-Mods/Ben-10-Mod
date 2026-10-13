package io.github.realyusufismail.bentenmod.client.alien;

import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

import java.util.UUID;

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
        return snapshot != null && snapshot.currentAlien() != null;
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

    @Override
    public long getInstanceId(AlienPlayerAnimatable animatable) {
        return currentEntity == null ? 0 : currentEntity.getId();
    }

    @Override
    public void render(AbstractClientPlayer player, float entityYaw, float partialTick,
            com.mojang.blaze3d.vertex.PoseStack poseStack,
            net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
        ResourceLocation alien = snapshot == null ? null : snapshot.currentAlien();
        if (alien == null) {
            return;
        }
        animatable.bind(player, alien, snapshot.expiresAt());
        super.render(player, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
