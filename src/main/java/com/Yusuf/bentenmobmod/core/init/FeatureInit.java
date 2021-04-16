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
				BlockInit.LEGENDARY_ORE_BLOCK.get().defaultBlockState(), 3, 1, 20, 3);
		
			addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE , 
					BlockInit.RUBY_ORE.get().defaultBlockState(), 3, 1, 20, 3);
			
			addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE , 
					BlockInit.OMNITRIX_ORE.get().defaultBlockState(), 3, 1, 15, 3);
			
			addOre(event, OreFeatureConfig.FillerBlockType.NETHERRACK , 
					BlockInit.FIRE_ORE.get().defaultBlockState(), 3, 1, 15, 2);
	}
	
	public static void  addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize, int minHeight, int maxHeight, int amount) {
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
				Feature.ORE.configured(new OreFeatureConfig(rule, state, veinSize))
				.decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
				.squared().count(amount));
	}

}
	