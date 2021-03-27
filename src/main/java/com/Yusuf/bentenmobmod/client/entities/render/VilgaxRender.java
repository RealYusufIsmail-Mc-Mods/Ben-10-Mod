package com.Yusuf.bentenmobmod.client.entities.render;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.client.entities.model.VilgaxModel;
import com.Yusuf.bentenmobmod.common.entity.Vilgax;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class VilgaxRender extends MobRenderer<Vilgax, VilgaxModel<Vilgax>>
{

	protected static final ResourceLocation VILGAX_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entities/vilgax/vilgax.png");

	   public VilgaxRender(EntityRendererManager renderManagerIn)
	   {
		   super(renderManagerIn, new VilgaxModel<Vilgax>(), 1.0f);
	   }
	   
	   @Override
		public ResourceLocation getEntityTexture(Vilgax entity) {
			
			return VILGAX_TEXTURE;
}};
