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

        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.LEGENDARY_ORE.get().defaultBlockState(), 3, 0, 16, 7);

        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.IMPERIUM_ORE.get().defaultBlockState(), 4, 0, 20, 8);

        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.RUBY_ORE.get().defaultBlockState(), 3, 0, 17, 7);

        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState(), 5, 0, 14, 7);

        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.SPEED_ORE.get().defaultBlockState(), 3, 0, 14, 5);

        addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInit.OMNITRIX_ORE.get().defaultBlockState(), 3, 0, 16, 6);

		/* Only for Crabbe at the moment
		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.COPPER_ORE.get().defaultBlockState(), 5, 0, 30, 7);
		 */
        addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK,
                BlockInit.INFINITUM_ORE.get().defaultBlockState(), 3, 8, 21, 5);

        addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK,
                BlockInit.FIRE_ORE.get().defaultBlockState(), 4, 8, 22, 5);
    }


    public static void addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize,
                              int minHeight, int maxHeight, int amount) {
        event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreFeatureConfig(rule, state, veinSize))
                        .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
                        .squared().count(amount));
    }
}


