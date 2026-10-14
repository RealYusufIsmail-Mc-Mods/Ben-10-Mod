/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * @see OrePlacements
 */
public class ModOrePlacements {
    public static final ResourceKey<PlacedFeature> LEGENDARY_ORE = createKey("legendary_ore");
    public static final ResourceKey<PlacedFeature> RUBY_ORE = createKey("ruby_ore");
    public static final ResourceKey<PlacedFeature> BLACK_DIAMOND_ORE =
            createKey("black_diamond_ore");
    public static final ResourceKey<PlacedFeature> IMPERIUM_ORE = createKey("imperium_ore");
    public static final ResourceKey<PlacedFeature> SPEED_ORE = createKey("speed_ore");
    public static final ResourceKey<PlacedFeature> OMNITRIX_ORE = createKey("omnitrix_ore");
    public static final ResourceKey<PlacedFeature> FIRE_ORE = createKey("fire_ore");
    public static final ResourceKey<PlacedFeature> INFINITUM_ORE = createKey("infinitum_ore");

    /** Ores that each loader adds to overworld biomes (Forge biome modifier, Fabric API). */
    public static final List<ResourceKey<PlacedFeature>> OVERWORLD_ORES = List.of(LEGENDARY_ORE,
            RUBY_ORE, BLACK_DIAMOND_ORE, IMPERIUM_ORE, SPEED_ORE, OMNITRIX_ORE);
    /** Ores that each loader adds to Nether biomes. */
    public static final List<ResourceKey<PlacedFeature>> NETHER_ORES =
            List.of(FIRE_ORE, INFINITUM_ORE);

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, BenTenMod.getModIdAndName(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter =
                context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder =
                holdergetter.getOrThrow(ModOreFeatures.LEGENDARY_ORE);
        Holder<ConfiguredFeature<?, ?>> holder1 = holdergetter.getOrThrow(ModOreFeatures.RUBY_ORE);
        Holder<ConfiguredFeature<?, ?>> holder2 =
                holdergetter.getOrThrow(ModOreFeatures.BLACK_DIAMOND_ORE);
        Holder<ConfiguredFeature<?, ?>> holder3 =
                holdergetter.getOrThrow(ModOreFeatures.IMPERIUM_ORE);
        Holder<ConfiguredFeature<?, ?>> holder4 = holdergetter.getOrThrow(ModOreFeatures.SPEED_ORE);
        Holder<ConfiguredFeature<?, ?>> holder5 =
                holdergetter.getOrThrow(ModOreFeatures.OMNITRIX_ORE);
        Holder<ConfiguredFeature<?, ?>> holder6 = holdergetter.getOrThrow(ModOreFeatures.FIRE_ORE);
        Holder<ConfiguredFeature<?, ?>> holder7 =
                holdergetter.getOrThrow(ModOreFeatures.INFINITUM_ORE);

        // Rarity follows power; numbers and rationale in docs/design/progression.md (section 1).
        register(context, RUBY_ORE, holder1, commonOrePlacement(10, HeightRangePlacement
            .triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(64))));
        register(context, SPEED_ORE, holder4, commonOrePlacement(4, HeightRangePlacement
            .uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        register(context, LEGENDARY_ORE, holder, commonOrePlacement(4, HeightRangePlacement
            .triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(16))));
        register(context, IMPERIUM_ORE, holder3, commonOrePlacement(5, HeightRangePlacement
            .triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(48))));
        register(context, BLACK_DIAMOND_ORE, holder2, commonOrePlacement(3, HeightRangePlacement
            .triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(32))));
        register(context, OMNITRIX_ORE, holder5, rareOrePlacement(2, HeightRangePlacement
            .uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48))));
        register(context, FIRE_ORE, holder6, commonOrePlacement(12, HeightRangePlacement
            .uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(117))));
        register(context, INFINITUM_ORE, holder7, commonOrePlacement(1, HeightRangePlacement
            .uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24))));
    }

    private static void register(BootstapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> holder,
            List<PlacementModifier> placement) {
        context.register(key, new PlacedFeature(holder, placement));
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
