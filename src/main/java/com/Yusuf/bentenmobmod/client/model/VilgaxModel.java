package com.Yusuf.bentenmobmod.client.model;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.entities.VilgaxEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VilgaxModel extends AnimatedGeoModel<VilgaxEntity>{

	    public ResourceLocation getModelLocation(VilgaxEntity object)
	    {
	        return new ResourceLocation(Main.MOD_ID, "geo/vilgaxmodel.geo.json");
	    }

	   
	    public ResourceLocation getTextureLocation(VilgaxEntity object)
	    {
	        return new ResourceLocation(Main.MOD_ID, "textures/entities/vilgax/vilgax_entity.png");
	    }

	 
	    public ResourceLocation getAnimationFileLocation(VilgaxEntity object)
	    {
	        return new ResourceLocation(Main.MOD_ID, "animations/vilgax.animation.json");
	    }
}
