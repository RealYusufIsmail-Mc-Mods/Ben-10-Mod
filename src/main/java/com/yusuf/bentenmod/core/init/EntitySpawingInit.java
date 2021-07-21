package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.entity.KraabEntity;
import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntitySpawingInit {
    @SubscribeEvent
    public static void onBiomesLoad(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.NETHER)
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntityTypesInit.VILGAX_ENTITY.get(), 3, 1, 2));
        if (event.getCategory() == Biome.Category.DESERT || event.getCategory() == Biome.Category.SAVANNA)
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(EntityTypesInit.KRAAB_ENTITY.get(), 3, 1, 1));

    }

    public static void entitySpawnPlacementRegistry() {
        EntitySpawnPlacementRegistry.register(EntityTypesInit.KRAAB_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KraabEntity::canKrabEntitySpawn);
        EntitySpawnPlacementRegistry.register(EntityTypesInit.VILGAX_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VilgaxEntity::canVilgaxSpawn);
    }
}
