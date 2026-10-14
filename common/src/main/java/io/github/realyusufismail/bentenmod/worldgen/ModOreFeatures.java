package io.github.realyusufismail.bentenmod.worldgen;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * @see OreFeatures
 */
public class ModOreFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LEGENDARY_ORE =
            createKey("legendary_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ORE = createKey("ruby_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_DIAMOND_ORE =
            createKey("black_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IMPERIUM_ORE =
            createKey("imperium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPEED_ORE = createKey("speed_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OMNITRIX_ORE =
            createKey("omnitrix_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_ORE = createKey("fire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INFINITUM_ORE =
            createKey("infinitum_ore");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, BenTenMod.getModIdAndName(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruleTest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest ruleTest3 = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest ruleTest4 = new TagMatchTest(BlockTags.BASE_STONE_NETHER);

        List<OreConfiguration.TargetBlockState> legendary = List.of(
                OreConfiguration.target(ruleTest1,
                        BlockInit.LEGENDARY_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2,
                        BlockInit.LEGENDARY_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> ruby = List.of(
                OreConfiguration.target(ruleTest1, BlockInit.RUBY_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2, BlockInit.RUBY_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> black_diamond = List.of(
                OreConfiguration.target(ruleTest1,
                        BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2,
                        BlockInit.BLACK_DIAMOND_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> imperium = List.of(
                OreConfiguration.target(ruleTest1,
                        BlockInit.IMPERIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2,
                        BlockInit.IMPERIUM_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> speed = List.of(
                OreConfiguration.target(ruleTest1, BlockInit.SPEED_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2, BlockInit.SPEED_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> omnitrix = List.of(
                OreConfiguration.target(ruleTest1,
                        BlockInit.OMNITRIX_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2,
                        BlockInit.OMNITRIX_ORE.get().defaultBlockState()));

        // Vein sizes follow the ore ladder in docs/design/progression.md (section 1).
        registerOre(context, LEGENDARY_ORE, legendary, 9, 0.0F);
        registerOre(context, RUBY_ORE, ruby, 9, 0.0F);
        registerOre(context, BLACK_DIAMOND_ORE, black_diamond, 4, 0.0F);
        registerOre(context, IMPERIUM_ORE, imperium, 6, 0.5F);
        registerOre(context, SPEED_ORE, speed, 6, 0.0F);
        registerOre(context, OMNITRIX_ORE, omnitrix, 3, 0.0F);
        registerOre(context, FIRE_ORE, ruleTest3, BlockInit.FIRE_ORE.get().defaultBlockState(), 10,
                0.0F);
        registerOre(context, INFINITUM_ORE, ruleTest4,
                BlockInit.INFINITUM_ORE.get().defaultBlockState(), 3, 1.0F);
    }

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore,
            List<OreConfiguration.TargetBlockState> targetBlockStates, int size,
            float discardChanceOnAirExposure) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(targetBlockStates, size, discardChanceOnAirExposure)));
    }

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore, RuleTest ruleTest, BlockState blockState,
            int size, float discardChanceOnAirExposure) {
        context.register(ore,
                new ConfiguredFeature<>(Feature.ORE,
                        new OreConfiguration(List.of(OreConfiguration.target(ruleTest, blockState)),
                                size, discardChanceOnAirExposure)));
    }
}
