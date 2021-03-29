package com.Yusuf.bentenmobmod.world;


import com.Yusuf.bentenmobmod.core.init.EntityTypesInit;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEntitySpawing {

	@SubscribeEvent
	public static void onBiomesLoad(BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.Category.NETHER)
			event.getSpawns().getSpawner(EntityClassification.CREATURE)
					.add(new MobSpawnInfo.Spawners(EntityTypesInit.VILGAX_ENTITY.get(), 10, 1, 1));

		
	}
};
