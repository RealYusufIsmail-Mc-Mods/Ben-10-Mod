/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.core.init;

import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGenerationInit {

    public static void addOres(final BiomeLoadingEvent event) {

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.LEGENDARY_ORE.get().defaultBlockState(), 3, 11, 17, 7);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.DEEPSLATE_LEGENDARY_ORE.get().defaultBlockState(), 3, 0, 11, 7);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.IMPERIUM_ORE.get().defaultBlockState(), 4, 11, 20, 8);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.DEEPSLATE_IMPERIUM_ORE.get().defaultBlockState(), 4, 0, 11, 8);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.RUBY_ORE.get().defaultBlockState(), 3, 11, 17, 7);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.DEEPSLATE_RUBY_ORE.get().defaultBlockState(), 3, 0, 11, 7);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState(), 5, 11, 15, 7);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get().defaultBlockState(), 5, 0, 11, 7);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.SPEED_ORE.get().defaultBlockState(), 3, 11, 15, 5);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.DEEPSLATE_SPEED_ORE.get().defaultBlockState(), 3, 0, 11, 5);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.OMNITRIX_ORE.get().defaultBlockState(), 3, 11, 16, 6);

        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.DEEPSLATE_OMNITRIX_ORE.get().defaultBlockState(), 3, 0, 11, 6);

        addOre(event, OreConfiguration.Predicates.NETHERRACK,
                BlockInit.INFINITUM_ORE.get().defaultBlockState(), 3, 8, 21, 5);

        addOre(event, OreConfiguration.Predicates.NETHERRACK,
                BlockInit.FIRE_ORE.get().defaultBlockState(), 4, 8, 22, 5);
    }


    public static void addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize,
                              int minHeight, int maxHeight, int amount) {
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreConfiguration(rule, state, veinSize))
                        .decorated(FeatureDecorator.RANGE.configured(Features.Decorators.RANGE_4_4))
                        .squared().count(amount));
    }
}


