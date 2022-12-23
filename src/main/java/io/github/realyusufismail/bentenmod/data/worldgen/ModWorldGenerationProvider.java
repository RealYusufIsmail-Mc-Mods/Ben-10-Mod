package io.github.realyusufismail.bentenmod.data.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @see VanillaRegistries
 */
public class ModWorldGenerationProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
        .add(Registries.CONFIGURED_FEATURE, ModOreFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, PlacementUtils::bootstrap);

    public ModWorldGenerationProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BenTenMod.MOD_ID));
    }

    public static HolderLookup.Provider createLookup() {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
    }
}
