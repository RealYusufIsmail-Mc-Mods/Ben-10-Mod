package com.Yusuf.bentenmobmod.client.entityrenderer;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.client.model.VilgaxModel;
import com.Yusuf.bentenmobmod.common.entity.VilgaxEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


@OnlyIn(Dist.CLIENT)
public class VilgaxRenderer extends GeoEntityRenderer<VilgaxEntity>
{
  
	public VilgaxRenderer(EntityRendererManager renderManagerIn)
    {
		 super(renderManagerIn, new VilgaxModel<>());
    }


	@Override
    public RenderType getRenderType(VilgaxEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getEntityTexture(animatable));
    }
	
	
	@Override
    public ResourceLocation getEntityTexture(VilgaxEntity entity) {
        return new ResourceLocation(Main.MOD_ID, "textures/entity/vilgax/vilgax_entity.png");
    }
}