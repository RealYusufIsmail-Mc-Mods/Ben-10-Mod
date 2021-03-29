package com.Yusuf.bentenmobmod.core.init;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.entities.VilgaxEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {
	  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

	    public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES
	            .register("vilgax_entity",() -> EntityType.Builder.<VilgaxEntity>of(VilgaxEntity::new, EntityClassification.CREATURE)
	                    .sized(0.7f,1.56f)
	                    .build(new ResourceLocation(Main.MOD_ID, "vilgax_entity").toString()));

}