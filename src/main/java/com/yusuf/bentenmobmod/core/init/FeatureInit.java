package com.yusuf.bentenmobmod.core.init;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class FeatureInit {

	public static void addOres(final BiomeLoadingEvent event) {

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.LEGENDARY_ORE_BLOCK.get().defaultBlockState(), 3, 0, 18, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.IMPERIUM_ORE.get().defaultBlockState(), 4, 0, 20, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.RUBY_ORE.get().defaultBlockState(), 3, 0, 15, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState(), 3, 0, 14, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.SPEED_ORE.get().defaultBlockState(), 3, 0, 14, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.OMNITRIX_ORE.get().defaultBlockState(), 3, 0, 18, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK,
				BlockInit.INFINITUM_ORE.get().defaultBlockState(), 4, 6, 20, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK,
				BlockInit.FIRE_ORE.get().defaultBlockState(), 4, 7, 21, 20);
	}


	public static void addOre(final Biome event, RuleTest rule, BlockState state, int veinSize,
							  int minHeight, int maxHeight, int amount) {
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE.configured(new OreFeatureConfig(rule, state, veinSize))
						.decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
						.squared().count(amount));
//cuase game to crash. DOnt think is right
	}
}


