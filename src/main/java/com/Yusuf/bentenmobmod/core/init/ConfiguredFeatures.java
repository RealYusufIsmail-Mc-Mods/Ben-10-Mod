package com.yusuf.bentenmobmod.core.init;

import com.Yusuf.bentenmobmod.Main;
	import net.minecraft.util.ResourceLocation;
	import net.minecraft.util.registry.Registry;
	import net.minecraft.util.registry.WorldGenRegistries;
	import net.minecraft.world.gen.feature.ConfiguredFeature;
	import net.minecraft.world.gen.feature.Feature;
	import net.minecraft.world.gen.feature.OreFeatureConfig;
	import net.minecraft.world.gen.placement.Placement;
	import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class ConfiguredFeatures {
	
	
	public static ConfiguredFeature<?, ?> LEGENDARY_ORE_BLOCK = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.LEGENDARY_ORE_BLOCK.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(3, 1, 20)))
            .squared().count(3);
	

	public static ConfiguredFeature<?, ?> RUBY_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.RUBY_ORE.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(3, 1, 20)))
            .squared().count(3);

	public static ConfiguredFeature<?, ?> BLACK_DIAMOND_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState(), 3))
			.decorated(Placement.RANGE.configured(new TopSolidRangeConfig(3, 1, 20)))
			.squared().count(3);
	public static ConfiguredFeature<?, ?> SPEED_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.SPEED_ORE.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(3, 1, 15)))
            .squared().count(3);
	public static ConfiguredFeature<?, ?> OMNITRIX_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.OMNITRIX_ORE.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(3, 1, 20)))
            .squared().count(3);
	public static ConfiguredFeature<?, ?> FIRE_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES, BlockInit.FIRE_ORE.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(3, 1, 20)))
            .squared().count(3);
	public static void registerConfiguredFeatures() {
		Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
	    Registry.register(registry, new ResourceLocation(Main.MOD_ID, "legendary_ore_block"), LEGENDARY_ORE_BLOCK);
	    Registry.register(registry, new ResourceLocation(Main.MOD_ID, "ruby_ore"), RUBY_ORE);
	    Registry.register(registry, new ResourceLocation(Main.MOD_ID, "speed_ore"), SPEED_ORE);
	    Registry.register(registry, new ResourceLocation(Main.MOD_ID, "omnitrix_ore"), OMNITRIX_ORE);
	    Registry.register(registry, new ResourceLocation(Main.MOD_ID, "fire_ore"), FIRE_ORE);
		Registry.register(registry, new ResourceLocation(Main.MOD_ID, "black_diamond_ore"), BLACK_DIAMOND_ORE);
	}
	
}

