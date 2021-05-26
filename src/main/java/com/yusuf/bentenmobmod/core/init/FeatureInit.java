/*
package com.yusuf.bentenmobmod.core.init;


import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;


public class FeatureInit {

	public static void addOres(final Biome event) {

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.LEGENDARY_ORE_BLOCK.get().getDefaultState(), 3, 0, 18, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.IMPERIUM_ORE.get().getDefaultState(), 4, 0, 20, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.RUBY_ORE.get().getDefaultState(), 3, 0, 15, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.BLACK_DIAMOND_ORE.get().getDefaultState(), 3, 0, 14, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.SPEED_ORE.get().getDefaultState(), 3, 0, 14, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlockInit.OMNITRIX_ORE.get().getDefaultState(), 3, 0, 18, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK,
				BlockInit.INFINITUM_ORE.get().getDefaultState(), 4, 6, 20, 20);

		addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK,
				BlockInit.FIRE_ORE.get().getDefaultState(), 4, 7, 21, 20);
	}


	public static void addOre(final Biome event, OreFeatureConfig.FillerBlockType rule, BlockState state, int veinSize,
							  int minHeight, int maxHeight, int amount) {
		event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE.withConfiguration(new OreFeatureConfig(rule, state, veinSize))
						.withPlacement(	Placement.COUNT_RANGE.configure(new TopSolidRangeConfig(minHeight,0, maxHeight)))
						.squared().func_242731_b(amount));
	}
}


 */

