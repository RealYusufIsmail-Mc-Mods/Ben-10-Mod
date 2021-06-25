
package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmod.entity.KraabBoltEntity;
import com.yusuf.bentenmod.entity.KraabEntity;
import com.yusuf.bentenmod.entity.VilgaxEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Main.MOD_ID);
	private static final Properties spawn_egg_props = new Item.Properties().tab(MainItemGroup.MAIN);

	private static final EntityType<VilgaxEntity> vilgax = createStandardEntityType("vilgax", VilgaxEntity::new,
			EntityClassification.MONSTER, 1f, 1f);
	private static final EntityType<KraabEntity> kraab = createStandardEntityType("kraab", KraabEntity::new,
			EntityClassification.MONSTER, 1.3f, 1.8f);
	private static final EntityType<KraabBoltEntity> kraabBolt = createStandardEntityType("kraab", KraabBoltEntity::new,
			EntityClassification.MISC, 0.5F, 0.5F);
	// registering the entities
	public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES.register("vilgax",
			() -> vilgax);
	public static final RegistryObject<EntityType<KraabEntity>> KRAAB_ENTITY = ENTITY_TYPES.register("kraab",
			() -> kraab);
	public static final RegistryObject<EntityType<KraabBoltEntity>> KRAAB_BOLT_ENTITY = ENTITY_TYPES.register("kraab_bolt",
			() -> kraabBolt);
	// This is where to register the actual attributes of the entities
	public static void registerEntityAttributes() {
		GlobalEntityTypeAttributes.put(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxEntity.registerAttributes().build());
		GlobalEntityTypeAttributes.put(EntityTypesInit.KRAAB_ENTITY.get(), KraabEntity.registerAttributes().build());
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
