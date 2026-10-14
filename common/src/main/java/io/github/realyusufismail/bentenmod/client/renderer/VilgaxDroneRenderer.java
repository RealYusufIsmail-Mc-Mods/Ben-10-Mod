package io.github.realyusufismail.bentenmod.client.renderer;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.VilgaxDroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VilgaxDroneRenderer extends GeoEntityRenderer<VilgaxDroneEntity> {
    public VilgaxDroneRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(
                new ResourceLocation(BenTenMod.MOD_ID, "vilgax_drone")));
    }
}
