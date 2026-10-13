package io.github.realyusufismail.bentenmod.data.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.worldgen.ModOreFeatures;
import io.github.realyusufismail.bentenmod.worldgen.ModOrePlacements;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @see VanillaRegistries
 */
public class ModWorldGenerationProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
        .add(Registries.CONFIGURED_FEATURE, ModOreFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, ModOrePlacements::bootstrap)
        .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModWorldGenerationProvider::biomeModifiers);

    public ModWorldGenerationProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BenTenMod.MOD_ID));
    }

    /** Adds the ores to biomes. Fabric does the same in BenTenModFabric#addOres. */
    private static void biomeModifiers(BootstapContext<BiomeModifier> context) {
        addOres(context, "add_overworld_ores", BiomeTags.IS_OVERWORLD,
                ModOrePlacements.OVERWORLD_ORES);
        addOres(context, "add_nether_ores", BiomeTags.IS_NETHER, ModOrePlacements.NETHER_ORES);
    }

    private static void addOres(BootstapContext<BiomeModifier> context, String name,
            TagKey<Biome> biomes, List<ResourceKey<PlacedFeature>> ores) {
        var biomeLookup = context.lookup(Registries.BIOME);
        var featureLookup = context.lookup(Registries.PLACED_FEATURE);
        context.register(
                ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
                        BenTenMod.getModIdAndName(name)),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(biomes),
                        HolderSet.direct(ores.stream().map(featureLookup::getOrThrow).toList()),
                        GenerationStep.Decoration.UNDERGROUND_ORES));
    }
}
