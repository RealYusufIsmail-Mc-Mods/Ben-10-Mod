package io.github.realyusufismail.bentenmod.core.init;

import com.google.common.base.Suppliers;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.realyusufismailcore.core.init.GeneralBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

/**
 * @see OreFeatures
 * @see OreConfiguration
 * @see BlockTags
 * @see FeatureUtils
 */
public class ConfiguredFeaturesInit {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, BenTenMod.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_LEGENDARY_ORE =
            regOverWoldOres(BlockInit.LEGENDARY_ORE, BlockInit.DEEPSLATE_LEGENDARY_ORE);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_RUBY_ORE =
            regOverWoldOres(BlockInit.RUBY_ORE, BlockInit.DEEPSLATE_RUBY_ORE);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_BLACK_DIAMOND_ORE =
            regOverWoldOres(BlockInit.BLACK_DIAMOND_ORE, BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_IMPERIUM_ORE =
            regOverWoldOres(BlockInit.IMPERIUM_ORE, BlockInit.DEEPSLATE_IMPERIUM_ORE);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SPEED_ORE =
            regOverWoldOres(BlockInit.SPEED_ORE, BlockInit.DEEPSLATE_SPEED_ORE);
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_OMNITRIX_ORE =
            regOverWoldOres(BlockInit.OMNITRIX_ORE, BlockInit.DEEPSLATE_OMNITRIX_ORE);

    public static final RegistryObject<ConfiguredFeature<?, ?>> LEGENDARY_ORE =
            registerOre("legendary_ore", OVERWORLD_LEGENDARY_ORE, 5);
    public static final RegistryObject<ConfiguredFeature<?, ?>> RUBY_ORE =
            registerOre("ruby_ore", OVERWORLD_RUBY_ORE, 4);
    public static final RegistryObject<ConfiguredFeature<?, ?>> BLACK_DIAMOND_ORE =
            registerOre("black_diamond_ore", OVERWORLD_BLACK_DIAMOND_ORE, 3);
    public static final RegistryObject<ConfiguredFeature<?, ?>> IMPERIUM_ORE =
            registerOre("imperium_ore", OVERWORLD_IMPERIUM_ORE, 4);
    public static final RegistryObject<ConfiguredFeature<?, ?>> SPEED_ORE =
            registerOre("speed_ore", OVERWORLD_SPEED_ORE, 3);
    public static final RegistryObject<ConfiguredFeature<?, ?>> OMNITRIX_ORE =
            registerOre("omnitrix_ore", OVERWORLD_OMNITRIX_ORE, 3);


    public static Supplier<List<OreConfiguration.TargetBlockState>> regOverWoldOres(
            RegistryObject<GeneralBlock> normalOre, RegistryObject<GeneralBlock> deepslateOre) {
        return Suppliers.memoize(() -> List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                        normalOre.get().defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                        deepslateOre.get().defaultBlockState())));
    }

    public static Supplier<List<OreConfiguration.TargetBlockState>> regNetherOre(Block netherOre) {
        return Suppliers.memoize(() -> List
            .of(OreConfiguration.target(new TagMatchTest(BlockTags.NETHER_CARVER_REPLACEABLES),
                    netherOre.defaultBlockState())));
    }

    public static Supplier<List<OreConfiguration.TargetBlockState>> regEndOre(Block endOre) {
        return Suppliers.memoize(() -> List.of(OreConfiguration
            .target(new BlockMatchTest(Blocks.END_STONE), endOre.defaultBlockState())));
    }

    public static RegistryObject<ConfiguredFeature<?, ?>> registerOre(String name,
            Supplier<List<OreConfiguration.TargetBlockState>> ore, int size) {
        return CONFIGURED_FEATURES.register(name,
                () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ore.get(), size)));
    }
}
