/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.entity.KraabEntity;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
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
            event.getSpawns()
                    .getSpawner(EntityClassification.CREATURE)
                    .add(new MobSpawnInfo.Spawners(
                            io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.VILGAX_ENTITY.get(),
                            3,
                            1,
                            2));
        if (event.getCategory() == Biome.Category.DESERT || event.getCategory() == Biome.Category.SAVANNA)
            event.getSpawns()
                    .getSpawner(EntityClassification.MONSTER)
                    .add(new MobSpawnInfo.Spawners(
                            io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.KRAAB_ENTITY.get(), 3, 1, 1));
    }

    public static void entitySpawnPlacementRegistry() {
        EntitySpawnPlacementRegistry.register(
                io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.KRAAB_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                KraabEntity::canKrabEntitySpawn);
        EntitySpawnPlacementRegistry.register(
                io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.VILGAX_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                VilgaxEntity::canVilgaxSpawn);
    }
}
