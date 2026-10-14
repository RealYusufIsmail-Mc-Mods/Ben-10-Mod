package io.github.realyusufismail.bentenmod.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.FeatureInit;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public final class OmnitrixCrashSiteWorldgen {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, BenTenMod.getModIdAndName("omnitrix_crash_site"));
    public static final ResourceKey<PlacedFeature> PLACED_FEATURE = ResourceKey
        .create(Registries.PLACED_FEATURE, BenTenMod.getModIdAndName("omnitrix_crash_site"));
    public static final TagKey<Biome> BIOMES = TagKey.create(Registries.BIOME,
            BenTenMod.getModIdAndName("omnitrix_crash_site_biomes"));

    private OmnitrixCrashSiteWorldgen() {}

    public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(CONFIGURED_FEATURE, new ConfiguredFeature<>(
                FeatureInit.OMNITRIX_CRASH_SITE.get(), NoneFeatureConfiguration.INSTANCE));
    }

    public static void bootstrapPlaced(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures =
                context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> configuredFeature =
                configuredFeatures.getOrThrow(CONFIGURED_FEATURE);
        context.register(PLACED_FEATURE, new PlacedFeature(configuredFeature,
                List.of(RarityFilter.onAverageOnceEvery(300), InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BiomeFilter.biome())));
    }
}
