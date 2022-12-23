package io.github.realyusufismail.bentenmod.data.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @see VanillaRegistries
 */
public class ModWorldGenerationProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER =
            (new RegistrySetBuilder()).add(Registries.CONFIGURED_FEATURE, ModOreFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModOrePlacements::bootstrap);

    public ModWorldGenerationProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BenTenMod.MOD_ID));
    }
}
