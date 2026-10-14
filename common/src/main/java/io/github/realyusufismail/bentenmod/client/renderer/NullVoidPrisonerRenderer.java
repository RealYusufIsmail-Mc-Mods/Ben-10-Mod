package io.github.realyusufismail.bentenmod.client.renderer;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.NullVoidPrisonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class NullVoidPrisonerRenderer extends GeoEntityRenderer<NullVoidPrisonerEntity> {
    public NullVoidPrisonerRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(
                new ResourceLocation(BenTenMod.MOD_ID, "entity/null_void_prisoner")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
