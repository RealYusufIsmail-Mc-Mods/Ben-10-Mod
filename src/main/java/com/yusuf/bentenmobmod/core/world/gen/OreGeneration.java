package com.yusuf.bentenmobmod.core.world.gen;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration  {
    private static void setupOreGenerations() {
    for(Biome biome : ForgeRegistries.BIOMES) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        BlockInit.BLACK_DIAMOND_ORE.get().getDefaultState(), 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 3, 0, 16))));
    }
    }


public static void generate() {
        setupOreGenerations();
}
}
