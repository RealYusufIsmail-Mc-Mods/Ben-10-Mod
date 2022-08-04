package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class ConfiguredFeaturesInit {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BenTenMod.MOD_ID);


    public static RegistryObject<?> register(String name, Holder<ConfiguredFeature<?, ?>> holder,
            int orePlacement, int aboveBottomInMinus, int aboveBottomInPositive, boolean rare) {
        if (!rare) {
            return PLACED_FEATURES
                .register(name,
                        () -> new PlacedFeature(holder,
                                commonOrePlacement(orePlacement, HeightRangePlacement.triangle(
                                        VerticalAnchor.aboveBottom(aboveBottomInMinus),
                                        VerticalAnchor.aboveBottom(aboveBottomInPositive)))));
        } else {
            return PLACED_FEATURES
                .register(name,
                        () -> new PlacedFeature(holder,
                                rareOrePlacement(orePlacement, HeightRangePlacement.triangle(
                                        VerticalAnchor.aboveBottom(aboveBottomInMinus),
                                        VerticalAnchor.aboveBottom(aboveBottomInPositive)))));
        }
    }

    public static @Unmodifiable List<PlacementModifier> orePlacement(PlacementModifier p_195347_,
            PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static @Unmodifiable List<PlacementModifier> commonOrePlacement(int p_195344_,
            PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static @Unmodifiable List<PlacementModifier> rareOrePlacement(int p_195350_,
            PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
