package com.Yusuf.bentenmobmod.core.init;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.entity.Vilgax;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class EntityTypesInit {


	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Main.MOD_ID);
	
	public static final RegistryObject<EntityType<Vilgax>> VILGAX = ENTITY_TYPES.register("vilgax", 
			() -> EntityType.Builder.<Vilgax>create(Vilgax::new, EntityClassification.MISC).size(1.5f, 4.0f)
			.build(new ResourceLocation(Main.MOD_ID, "vilgax").toString()));


}
