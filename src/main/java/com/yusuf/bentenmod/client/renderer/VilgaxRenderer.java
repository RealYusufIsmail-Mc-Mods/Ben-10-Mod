package com.yusuf.bentenmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yusuf.bentenmod.client.model.VilgaxModel;
import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class VilgaxRenderer extends GeoEntityRenderer<VilgaxEntity> {

public VilgaxRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new VilgaxModel());
		this.shadowRadius = 0.7F;
}
public RenderType getRenderType(VilgaxEntity animatable, float partialTicks, MatrixStack stack,
		IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
		ResourceLocation textureLocation) {
	return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
