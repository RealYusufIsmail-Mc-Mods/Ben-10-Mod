package io.github.realyusufismail.bentenmod.data.worldgen;

import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
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
public class ModOreFeatures extends OreFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LEGENDARY_ORE =
            FeatureUtils.createKey("legendary_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ORE =
            FeatureUtils.createKey("ruby_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_DIAMOND_ORE =
            FeatureUtils.createKey("black_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IMPERIUM_ORE =
            FeatureUtils.createKey("imperium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPEED_ORE =
            FeatureUtils.createKey("speed_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OMNITRIX_ORE =
            FeatureUtils.createKey("omnitrix_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_ORE =
            FeatureUtils.createKey("fire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INFINITUM_ORE =
            FeatureUtils.createKey("infinitum_ore");

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

        registerOre(context, LEGENDARY_ORE, legendary, 7);
        registerOre(context, RUBY_ORE, ruby, 7);
        registerOre(context, BLACK_DIAMOND_ORE, black_diamond, 5);
        registerOre(context, IMPERIUM_ORE, imperium, 5);
        registerOre(context, SPEED_ORE, speed, 5);
        registerOre(context, OMNITRIX_ORE, omnitrix, 5);
        registerOre(context, FIRE_ORE, ruleTest3, BlockInit.FIRE_ORE.get().defaultBlockState(), 6);
        registerOre(context, INFINITUM_ORE, ruleTest4,
                BlockInit.INFINITUM_ORE.get().defaultBlockState(), 4);
    }

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore,
            List<OreConfiguration.TargetBlockState> targetBlockStates, int size) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                targetBlockStates, size)));
    }

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore, RuleTest ruleTest, BlockState blockState,
            int size) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                ruleTest, blockState, size)));
    }
}
