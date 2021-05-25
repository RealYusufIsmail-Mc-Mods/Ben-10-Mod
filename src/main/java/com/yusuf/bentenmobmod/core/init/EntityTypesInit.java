
package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmobmod.entity.VilgaxEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Main.MODID);

	private static final Properties spawn_egg_props = new Item.Properties().group(MainItemGroup.MAIN);


	private static final EntityType<VilgaxEntity> vilgax = createStandardEntityType("vilgax", VilgaxEntity::new,
			EntityClassification.MONSTER, 1f, 1f);

	// registering the entities
	public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES.register("vilgax",
			() -> vilgax);

	// This is where to register the actual attributes of the entities
	public static void registerEntityAttributes() {
		EntityType.Builder(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxEntity.registerNatives().build());
	}

	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
																			 EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
		return EntityType.Builder.of(factory, classification).sized(width, height)
				.build(Main.MOD_ID + ":" + entity_name);
	}
	
	// register spawn eggs
	public static final RegistryObject<Item> VILGAX_SPAWN_EGG = ItemInit.ITEMS.register("vilgax_spawn_egg",
			() -> new SpawnEggItem(vilgax, 0xC4AA79, 0x7A5F22, spawn_egg_props));
}
