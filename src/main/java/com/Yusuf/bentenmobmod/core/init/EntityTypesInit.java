
package com.Yusuf.bentenmobmod.core.init;

import javax.swing.text.html.parser.Entity;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.entities.VilgaxEntity;
import com.Yusuf.bentenmobmod.objects.items.ModSpawnEggItem;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Main.MOD_ID);

	    
	    
	    private static final EntityType<VilgaxEntity> vilgax = createStandardEntityType("vilgax", VilgaxEntity::new,
				EntityClassification.CREATURE, 0.7f, 1.56f);

	 // registering the entities
		public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES.register("vilgax",
				() -> vilgax);
		
		
		// register spawn eggs
		public static final RegistryObject<ModSpawnEggItem> VILGAX_SPAWN_EGG = ItemInit.register("vilgax_spawn_egg",
				() -> new SpawnEggItem(vilgax, 0x339BFF, 0x133B60, spawn_egg_props));
		
		private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
				EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
			return EntityType.Builder.create(factory, classification).size(width, height)
					.build(Main.MOD_ID + ":" + entity_name);
		}
		
}
