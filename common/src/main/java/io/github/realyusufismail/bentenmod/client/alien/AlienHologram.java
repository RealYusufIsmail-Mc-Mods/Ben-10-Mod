package io.github.realyusufismail.bentenmod.client.alien;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.HashMap;
import java.util.Map;

/** A green hologram of an alien, drawn in GUIs such as the Omnitrix dial. */
public final class AlienHologram implements GeoAnimatable {
    private static final Map<ResourceLocation, Float> HEIGHTS = new HashMap<>();
    private static Renderer renderer;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ResourceLocation alienId;
    private float alpha = 1;

    public ResourceLocation alienId() {
        return alienId;
    }

    /**
     * Draws the alien standing on {@code (x, y)}, scaled so it is {@code height} GUI pixels tall.
     */
    public void render(GuiGraphics graphics, ResourceLocation alien, float x, float y, float height,
            float spinDegrees, float alpha) {
        if (renderer == null) {
            renderer = new Renderer();
        }
        this.alienId = alien;
        this.alpha = alpha;
        float scale = height / modelHeight(alien);
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(x, y, 150);
        pose.scale(scale, scale, -scale);
        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.mulPose(Axis.XP.rotationDegrees(-12));
        pose.mulPose(Axis.YP.rotationDegrees(spinDegrees));
        // GeoObjectRenderer centres models on a block; undo that so the feet sit on the origin.
        pose.translate(-0.5, -0.51, -0.5);
        Lighting.setupForEntityInInventory();
        MultiBufferSource.BufferSource buffers = graphics.bufferSource();
        renderer.render(pose, this, buffers, null, null, LightTexture.FULL_BRIGHT);
        graphics.flush();
        Lighting.setupFor3DItems();
        pose.popPose();
    }

    private static float modelHeight(ResourceLocation alien) {
        return HEIGHTS.computeIfAbsent(alien, id -> {
            BakedGeoModel model = renderer.getGeoModel().getBakedModel(AlienGeoModel.modelOf(id));
            float[] bounds = {Float.MAX_VALUE, -Float.MAX_VALUE};
            model.topLevelBones().forEach(bone -> extendBounds(bone, bounds));
            float height = bounds[1] - bounds[0];
            return height > 0.1f ? height : 2;
        });
    }

    private static void extendBounds(GeoBone bone, float[] bounds) {
        for (GeoCube cube : bone.getCubes()) {
            for (GeoQuad quad : cube.quads()) {
                if (quad == null) {
                    continue;
                }
                for (GeoVertex vertex : quad.vertices()) {
                    bounds[0] = Math.min(bounds[0], vertex.position().y());
                    bounds[1] = Math.max(bounds[1], vertex.position().y());
                }
            }
        }
        bone.getChildBones().forEach(child -> extendBounds(child, bounds));
    }

    /** Forget cached model sizes after a resource reload. */
    public static void clearCache() {
        HEIGHTS.clear();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "hologram", state -> {
            if (alienId == null) {
                return PlayState.STOP;
            }
            return state.setAndContinue(
                    RawAnimation.begin().thenLoop("animation." + alienId.getPath() + ".idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object instance) {
        return RenderUtils.getCurrentTick();
    }

    private static final class Renderer extends GeoObjectRenderer<AlienHologram> {
        Renderer() {
            super(new AlienGeoModel<>(AlienHologram::alienId));
        }

        @Override
        public long getInstanceId(AlienHologram animatable) {
            return animatable.alienId == null ? 0 : animatable.alienId.hashCode();
        }

        @Override
        public RenderType getRenderType(AlienHologram animatable, ResourceLocation texture,
                @Nullable MultiBufferSource bufferSource, float partialTick) {
            return RenderType.entityTranslucent(texture);
        }

        @Override
        public Color getRenderColor(AlienHologram animatable, float partialTick, int packedLight) {
            return Color.ofRGBA(0.55f, 1f, 0.6f, animatable.alpha);
        }
    }
}
