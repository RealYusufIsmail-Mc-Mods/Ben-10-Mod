/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.core.init;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.List;

// TODO check the gen hight.
public class OreGenerationInit {
    private OreGenerationInit() {
        throw new IllegalStateException("Ore gen class");
    }

    protected static final List<PlacedFeature> OVERWORLD_ORES = new ArrayList<>();
    // protected static final List<PlacedFeature> END_ORES = new ArrayList<>();
    protected static final List<PlacedFeature> NETHER_ORES = new ArrayList<>();

    // public static final RuleTest END_TEST = new BlockMatchTest(Blocks.END_STONE);


    /**
     * VerticalAnchor.aboveBottom(30) means its minimum y position is 30 blocks above the bottom of
     * the world VerticalAnchor.belowTop(40) means its maximum y position is 40 blocks below the top
     * of the world
     *
     * and then its distributed uniformly across that range (which is what the
     * HeightRangePlacement.uniform means)
     */

    public static void registerOres() {
        final ConfiguredFeature<?, ?> blackDiamondOre = FeatureUtils.register("black_diamond_ore",
                Feature.ORE.place(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.BLACK_DIAMOND_BLOCK.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get().defaultBlockState())),
                        8), null, null, null, null));

        final PlacedFeature placedBlackDiamondOre = PlacementUtils.register("black_diamond_ore",
                blackDiamondOre.place(
                        // Min height for ore to spawn (worldMinHeight + height)
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(9),
                                VerticalAnchor.belowTop(2)),
                        // Causes the randomness in the ore veins
                        InSquarePlacement.spread(),
                        // Attempts per chunk
                        CountPlacement.of(10)));


        final ConfiguredFeature<?, ?> omnitrixOre = FeatureUtils.register("omnitrix_ore",
                Feature.ORE.place(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.OMNITRIX_ORE.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.DEEPSLATE_OMNITRIX_ORE.get().defaultBlockState())),
                        8)));

        final PlacedFeature placedOmnitrixOre = PlacementUtils.register("omnitrix_ore",
                omnitrixOre.place(
                        // Min height for ore to spawn (worldMinHeight + height)
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(9),
                                VerticalAnchor.belowTop(2)),
                        // Causes the randomness in the ore veins
                        InSquarePlacement.spread(),
                        // Attempts per chunk
                        CountPlacement.of(5)));

        final ConfiguredFeature<?, ?> legendaryOre = FeatureUtils.register("legendary_ore",
                Feature.ORE.place(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.LEGENDARY_ORE.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.DEEPSLATE_LEGENDARY_ORE.get().defaultBlockState())),
                        8)));

        final PlacedFeature placedLegendaryOre = PlacementUtils.register("legendary_ore",
                legendaryOre.place(
                        // Min height for ore to spawn (worldMinHeight + height)
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(9),
                                VerticalAnchor.belowTop(2)),
                        // Causes the randomness in the ore veins
                        InSquarePlacement.spread(),
                        // Attempts per chunk
                        CountPlacement.of(5)));

        final ConfiguredFeature<?, ?> speedOre = FeatureUtils.register("speed_ore",
                Feature.ORE.place(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.SPEED_ORE.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.DEEPSLATE_SPEED_ORE.get().defaultBlockState())),
                        8)));

        final PlacedFeature placedSpeedOre = PlacementUtils.register("speed_ore", speedOre.place(
                // Min height for ore to spawn (worldMinHeight + height)
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(30),
                        VerticalAnchor.belowTop(40)),
                // Causes the randomness in the ore veins
                InSquarePlacement.spread(),
                // Attempts per chunk
                CountPlacement.of(5)));


        final ConfiguredFeature<?, ?> rubyOre = FeatureUtils.register("ruby_ore",
                Feature.ORE.place(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.RUBY_ORE.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.RUBY_ORE.get().defaultBlockState())),
                        8)));

        final PlacedFeature placedRubyOre = PlacementUtils.register("ruby_ore", rubyOre.place(
                // Min height for ore to spawn (worldMinHeight + height)
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(30),
                        VerticalAnchor.belowTop(40)),
                // Causes the randomness in the ore veins
                InSquarePlacement.spread(),
                // Attempts per chunk
                CountPlacement.of(5)));

        final ConfiguredFeature<?, ?> imperiumOre = FeatureUtils.register("imperium_ore",
                Feature.ORE.place(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.SPEED_ORE.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.DEEPSLATE_SPEED_ORE.get().defaultBlockState())),
                        8)));

        final PlacedFeature placedImperiumOre = PlacementUtils.register("imperium_ore",
                imperiumOre.place(
                        // Min height for ore to spawn (worldMinHeight + height)
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(30),
                                VerticalAnchor.belowTop(40)),
                        // Causes the randomness in the ore veins
                        InSquarePlacement.spread(),
                        // Attempts per chunk
                        CountPlacement.of(5)));

        // nether ores
        final ConfiguredFeature<?, ?> fireOre = FeatureUtils.register("fire_ore",
                Feature.ORE.place(new OreConfiguration(
                        List.of(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES,
                                BlockInit.FIRE_ORE.get().defaultBlockState())),
                        9)));

        final PlacedFeature placedFireOre = PlacementUtils.register("fire_ore", fireOre.place(
                // Min height for ore to spawn (worldMinHeight + height)
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(30),
                        VerticalAnchor.belowTop(40)),
                InSquarePlacement.spread(), CountPlacement.of(5)));


        final ConfiguredFeature<?, ?> infinitumOre = FeatureUtils.register("infinitum_ore",
                Feature.ORE.place(new OreConfiguration(
                        List.of(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES,
                                BlockInit.INFINITUM_ORE.get().defaultBlockState())),
                        9)));

        final PlacedFeature placedInfinitumOre = PlacementUtils.register("infinitum_ore",
                infinitumOre.place(
                        // Min height for ore to spawn (worldMinHeight + height)
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(30),
                                VerticalAnchor.belowTop(40)),
                        InSquarePlacement.spread(), CountPlacement.of(5)));

        // add ores
        OVERWORLD_ORES.add(placedBlackDiamondOre);
        OVERWORLD_ORES.add(placedOmnitrixOre);
        OVERWORLD_ORES.add(placedLegendaryOre);
        OVERWORLD_ORES.add(placedSpeedOre);
        OVERWORLD_ORES.add(placedRubyOre);
        OVERWORLD_ORES.add(placedImperiumOre);
        NETHER_ORES.add(placedFireOre);
        NETHER_ORES.add(placedInfinitumOre);
    }
}


