package com.Yusuf.bentenmobmod.client.entities.model;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.entities.Vilgax;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class VilgaxModel<B extends AnimalEntity> extends AnimatedGeoModel<Vilgax> {
	
    public ResourceLocation getModelLocation(Vilgax entity) {
        return new ResourceLocation(Main.MOD_ID, "geo/vilgax.json");
    }

    
    public ResourceLocation getAnimationFileLocation(Vilgax entity) {
        return new ResourceLocation(Main.MOD_ID, "animations/vilgax.json");
    }


    public ResourceLocation getTextureLocation(Vilgax entity) {
        return null;
    }
}

