package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;

/**
 * @see OrePlacements
 */
public class OrePlacementsInit {
    public static final ResourceKey<PlacedFeature> LEGENDARY_ORE =
            PlacementUtils.createKey("legendary_ore");
    public static final ResourceKey<PlacedFeature> RUBY_ORE = PlacementUtils.createKey("ruby_ore");
    public static final ResourceKey<PlacedFeature> BLACK_DIAMOND_ORE =
            PlacementUtils.createKey("black_diamond_ore");
    public static final ResourceKey<PlacedFeature> IMPERIUM_ORE =
            PlacementUtils.createKey("imperium_ore");
    public static final ResourceKey<PlacedFeature> SPEED_ORE =
            PlacementUtils.createKey("speed_ore");
    public static final ResourceKey<PlacedFeature> OMNITRIX_ORE =
            PlacementUtils.createKey("omnitrix_ore");
    public static final ResourceKey<PlacedFeature> FIRE_ORE = PlacementUtils.createKey("fire_ore");
    public static final ResourceKey<PlacedFeature> INFINITUM_ORE =
            PlacementUtils.createKey("infinitum_ore");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter =
                context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder =
                holdergetter.getOrThrow(OreFeaturesInit.LEGENDARY_ORE);
        Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(OreFeaturesInit.RUBY_ORE);
        Holder<ConfiguredFeature<?, ?>> holder2 =
                holdergetter.getOrThrow(OreFeaturesInit.BLACK_DIAMOND_ORE);
        Holder<ConfiguredFeature<?, ?>> holder3 =
                holdergetter.getOrThrow(OreFeaturesInit.IMPERIUM_ORE);
        Holder<ConfiguredFeature<?, ?>> holder4 =
                holdergetter.getOrThrow(OreFeaturesInit.SPEED_ORE);
        Holder<ConfiguredFeature<?, ?>> holder5 =
                holdergetter.getOrThrow(OreFeaturesInit.OMNITRIX_ORE);
        Holder<ConfiguredFeature<?, ?>> holder6 = holdergetter.getOrThrow(OreFeaturesInit.FIRE_ORE);
        Holder<ConfiguredFeature<?, ?>> holder7 =
                holdergetter.getOrThrow(OreFeaturesInit.INFINITUM_ORE);

        PlacementUtils.register(context, LEGENDARY_ORE, holder,
                commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50),
                        VerticalAnchor.absolute(50))));
        PlacementUtils.register(context, RUBY_ORE, holder1,
                commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50),
                        VerticalAnchor.absolute(50))));
        PlacementUtils.register(context, BLACK_DIAMOND_ORE, holder2,
                rareOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-70),
                        VerticalAnchor.absolute(70))));
        PlacementUtils.register(context, IMPERIUM_ORE, holder3,
                commonOrePlacement(11, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50),
                        VerticalAnchor.absolute(50))));
        PlacementUtils.register(context, SPEED_ORE, holder4,
                rareOrePlacement(11, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40),
                        VerticalAnchor.absolute(40))));
        PlacementUtils.register(context, OMNITRIX_ORE, holder5,
                rareOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80),
                        VerticalAnchor.absolute(80))));
        PlacementUtils.register(context, FIRE_ORE, holder6, rareOrePlacement(3, HeightRangePlacement
            .triangle(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(60))));
        PlacementUtils.register(context, INFINITUM_ORE, holder7,
                rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-60),
                        VerticalAnchor.absolute(60))));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier,
            PlacementModifier modifier1) {
        return List.of(modifier, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pCount,
            PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int pChance,
            PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
