package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

/** GeckoLib resources for the existing 3D watch asset. */
public final class OmnitrixWatchModel<T extends Item & GeoAnimatable> extends GeoModel<T> {
    private static final ResourceLocation MODEL =
            new ResourceLocation(BenTenMod.MOD_ID, "geo/item/omnitrix.geo.json");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/item/omnitrix_device.png");
    private static final ResourceLocation ANIMATION =
            new ResourceLocation(BenTenMod.MOD_ID, "animations/omnitrix.animation.json");

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATION;
    }
}
