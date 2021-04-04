

package com.Yusuf.bentenmobmod.core.init;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.entities.VilgaxEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.chunk.Chunk.CreateEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class EntityTypesInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Main.MOD_ID);

	public abstract void createStandardEntityType(CreateEntityType VariableDeclaratorId);
	public void createStandardEntityType(createStandardEntityType)
	   
	    private static final EntityType<VilgaxEntity> vilgax = createStandardEntityType("vilgax", VilgaxEntity::new,
				EntityClassification.CREATURE, 0.7f, 1.56f);

	 // registering the entities		
	 // registering the entities
		public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES.register("vilgax",
				() -> vilgax);
		
		
		// register spawn eggs
	
		
		
		
		
}
		