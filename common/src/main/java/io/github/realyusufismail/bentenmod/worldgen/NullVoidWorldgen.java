package io.github.realyusufismail.bentenmod.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.FeatureInit;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

public final class NullVoidWorldgen {
    public static final ResourceKey<Level> DIMENSION =
            ResourceKey.create(Registries.DIMENSION, BenTenMod.getModIdAndName("null_void"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> NULL_CRYSTAL_ORE_CONFIGURED =
            configuredKey("null_crystal_ore");
    public static final ResourceKey<PlacedFeature> NULL_CRYSTAL_ORE_PLACED =
            placedKey("null_crystal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FORTRESS_CONFIGURED =
            configuredKey("null_void_fortress");
    public static final ResourceKey<PlacedFeature> FORTRESS_PLACED =
            placedKey("null_void_fortress");

    private NullVoidWorldgen() {}

    public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context
            .register(NULL_CRYSTAL_ORE_CONFIGURED, new ConfiguredFeature<>(Feature.ORE,
                    new OreConfiguration(List.of(OreConfiguration.target(
                            new BlockMatchTest(BlockInit.NULL_VOID_STONE.get()),
                            BlockInit.NULL_CRYSTAL_ORE.get().defaultBlockState())), 4)));
        context.register(FORTRESS_CONFIGURED, new ConfiguredFeature<>(
                FeatureInit.NULL_VOID_FORTRESS.get(), NoneFeatureConfiguration.INSTANCE));
    }

    public static void bootstrapPlaced(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured =
                context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> ore = configured.getOrThrow(NULL_CRYSTAL_ORE_CONFIGURED);
        Holder<ConfiguredFeature<?, ?>> fortress = configured.getOrThrow(FORTRESS_CONFIGURED);
        context.register(NULL_CRYSTAL_ORE_PLACED,
                new PlacedFeature(ore, List.of(
                        CountPlacement.of(8), InSquarePlacement.spread(), HeightRangePlacement
                            .uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)),
                        BiomeFilter.biome())));
        context.register(FORTRESS_PLACED, new PlacedFeature(fortress,
                List.of(RarityFilter.onAverageOnceEvery(400), InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BiomeFilter.biome())));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, BenTenMod.getModIdAndName(name));
    }

    private static ResourceKey<PlacedFeature> placedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, BenTenMod.getModIdAndName(name));
    }
}
