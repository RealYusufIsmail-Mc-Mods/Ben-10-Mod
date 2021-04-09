
package com.Yusuf.bentenmobmod.core.init;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.entity.VilgaxEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class EntityTypesInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Main.MOD_ID);

	private static final EntityType<VilgaxEntity> vilgax = createStandardEntityType("vilgax", VilgaxEntity::new,
			EntityClassification.CREATURE, 2f, 2f);

	//This is where to register the actual attributes of the entities
	@SuppressWarnings("deprecation")
	public static void registerEntityAttributes() {
		GlobalEntityTypeAttributes.put(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxEntity.registerAttributes().build());
	}

	// registering the entities
	public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES.register("vilgax",
			() -> vilgax);

	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
			EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
		return EntityType.Builder.of(factory, classification).sized(width, height)
				.build(Main.MOD_ID + ":" + entity_name);
	}

	// register spawn eggs

}
