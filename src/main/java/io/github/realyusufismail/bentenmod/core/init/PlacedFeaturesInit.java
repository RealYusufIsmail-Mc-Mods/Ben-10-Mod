package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class PlacedFeaturesInit {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, BenTenMod.MOD_ID);

    public static final RegistryObject<PlacedFeature> LEGENDARY_ORE_PLACED = register(
            "legendary_ore_placed", ConfiguredFeaturesInit.LEGENDARY_ORE, 4, -80, 50, false);

    public static final RegistryObject<PlacedFeature> RUBY_ORE_PLACED =
            register("ruby_ore_placed", ConfiguredFeaturesInit.RUBY_ORE, 4, -80, 50, true);

    public static final RegistryObject<PlacedFeature> BLACK_DIAMOND_ORE_PLACED = register(
            "black_diamond_ore_placed", ConfiguredFeaturesInit.BLACK_DIAMOND_ORE, 4, -80, 30, true);

    public static final RegistryObject<PlacedFeature> IMPERIUM_ORE_PLACED =
            register("imperium_ore_placed", ConfiguredFeaturesInit.IMPERIUM_ORE, 4, -80, 40, true);

    public static final RegistryObject<PlacedFeature> SPEED_ORE_PLACED =
            register("speed_ore_placed", ConfiguredFeaturesInit.SPEED_ORE, 4, -80, 40, true);

    public static final RegistryObject<PlacedFeature> OMNITRIX_ORE_PLACED =
            register("omnitrix_ore_placed", ConfiguredFeaturesInit.OMNITRIX_ORE, 4, -80, 50, true);


    public static RegistryObject<PlacedFeature> register(String name,
            RegistryObject<ConfiguredFeature<?, ?>> holder, int orePlacement,
            int aboveBottomInMinus, int aboveBottomInPositive, boolean rare) {
        if (!rare) {
            return PLACED_FEATURES
                .register(name,
                        () -> new PlacedFeature(holder.getHolder().get(),
                                commonOrePlacement(orePlacement, HeightRangePlacement.triangle(
                                        VerticalAnchor.aboveBottom(aboveBottomInMinus),
                                        VerticalAnchor.aboveBottom(aboveBottomInPositive)))));
        } else {
            return PLACED_FEATURES
                .register(name,
                        () -> new PlacedFeature(holder.getHolder().get(),
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
