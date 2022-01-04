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

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// TODO finish this.
public class OreGenerationInit {
    private OreGenerationInit() {
        throw new IllegalStateException("Ore gen class");
    }

    protected static final List<PlacedFeature> OVERWORLD_ORES = new ArrayList<>();
    protected static final List<PlacedFeature> END_ORES = new ArrayList<>();
    protected static final List<PlacedFeature> NETHER_ORES = new ArrayList<>();

    public static final RuleTest END_TEST = new BlockMatchTest(Blocks.END_STONE);


    public static void registerOres() {
        final ConfiguredFeature<?, ?> blackDiamondOre = FeatureUtils.register("black_diamond_ore",
                Feature.ORE.configured(new OreConfiguration(List.of(
                        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                                BlockInit.BLACK_DIAMOND_BLOCK.get().defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get().defaultBlockState())),
                        8)));

        final PlacedFeature placedBlackDiamondOre = PlacementUtils.register("black_diamond_ore",
                blackDiamondOre.placed(
                        HeightRangePlacement.uniform(VerticalAnchor.bottom(),
                                VerticalAnchor.aboveBottom(20)),
                        InSquarePlacement.spread(), CountPlacement.of(100)));



        // add ores
        OVERWORLD_ORES.add(placedBlackDiamondOre);
    }

    @Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusSubscriber {
        private ForgeBusSubscriber() {}

        @SubscribeEvent
        public static void biomeLoading(BiomeLoadingEvent event) {
            final List<Supplier<PlacedFeature>> features =
                    event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

            switch (event.getCategory()) {
                case NETHER -> OreGenerationInit.NETHER_ORES
                    .forEach(ore -> features.add(() -> ore));
                case THEEND -> OreGenerationInit.END_ORES.forEach(ore -> features.add(() -> ore));
                default -> OreGenerationInit.OVERWORLD_ORES.forEach(ore -> features.add(() -> ore));
            }
        }
    }
}


