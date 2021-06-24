package com.yusuf.bentenmod.client.renderer;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.client.model.KraabModel;
import com.yusuf.bentenmod.entity.KraabEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class KraabRenderer extends MobRenderer<KraabEntity, KraabModel> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID,
            "textures/model/entities/kraab.png");

    public KraabRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new KraabModel(), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(KraabEntity entity) {
        return TEXTURES;
    }
}
