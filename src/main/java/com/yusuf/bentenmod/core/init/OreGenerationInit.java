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
package com.yusuf.bentenmod.core.init;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGenerationInit {

    public static void addOres(final BiomeLoadingEvent event) {

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.LEGENDARY_ORE.get().defaultBlockState(),
                3,
                0,
                16,
                7);

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.IMPERIUM_ORE.get().defaultBlockState(),
                4,
                0,
                20,
                8);

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.RUBY_ORE.get().defaultBlockState(),
                3,
                0,
                17,
                7);

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState(),
                5,
                0,
                14,
                7);

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.SPEED_ORE.get().defaultBlockState(),
                3,
                0,
                14,
                5);

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.OMNITRIX_ORE.get().defaultBlockState(),
                3,
                0,
                16,
                6);

        /* Only for Crabbe at the moment
        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.COPPER_ORE.get().defaultBlockState(), 5, 0, 30, 7);
         */
        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NETHERRACK,
                BlockInit.INFINITUM_ORE.get().defaultBlockState(),
                3,
                8,
                21,
                5);

        addOre(
                event,
                OreFeatureConfig.FillerBlockType.NETHERRACK,
                BlockInit.FIRE_ORE.get().defaultBlockState(),
                4,
                8,
                22,
                5);
    }

    public static void addOre(
            final BiomeLoadingEvent event,
            RuleTest rule,
            BlockState state,
            int veinSize,
            int minHeight,
            int maxHeight,
            int amount) {
        event.getGeneration()
                .addFeature(
                        GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE
                                .configured(new OreFeatureConfig(rule, state, veinSize))
                                .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
                                .squared()
                                .count(amount));
    }
}
