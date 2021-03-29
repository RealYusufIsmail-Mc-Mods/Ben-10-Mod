package com.Yusuf.bentenmobmod.core.init;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class FeatureInit {
	
	public static void addOres(final BiomeLoadingEvent event) {
		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE , 
				BlockInit.LEGENDARY_ORE_BLOCK.get().getRegistryNamet(), 3, 1, 20, 3);
		
			addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE , 
					BlockInit.RUBY_ORE.get().getDefaultState(), 3, 1, 20, 3);
	}
	
	public static void  addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize, int minHeight, int maxHeight, int amount) {
		event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
				Feature.ORE.withConfiguration(new OreFeatureConfig(rule, state, veinSize))
				.withPlacement(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
				.square().func_242731_b(amount));
	}

}
	