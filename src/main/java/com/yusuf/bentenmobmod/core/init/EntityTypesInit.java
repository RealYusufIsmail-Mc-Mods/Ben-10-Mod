
package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.entity.VilgaxEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Main.MOD_ID);



	public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES
			.register("vilgax_entity",
					() -> EntityType.Builder.<VilgaxEntity>create(VilgaxEntity::new, EntityClassification.CREATURE)
							.size(0.9f, 1.3f)
							.build(new ResourceLocation(Main.MOD_ID, "vilgax_entity").toString()));

	// register spawn eggs
}
