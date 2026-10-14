package io.github.realyusufismail.bentenmod.client.renderer;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.ZsSkayrEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ZsSkayrRenderer extends GeoEntityRenderer<ZsSkayrEntity> {
    public ZsSkayrRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(
                new ResourceLocation(BenTenMod.MOD_ID, "entity/zs_skayr")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
