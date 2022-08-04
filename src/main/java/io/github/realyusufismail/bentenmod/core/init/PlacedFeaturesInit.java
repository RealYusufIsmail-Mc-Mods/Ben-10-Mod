package io.github.realyusufismail.bentenmod.core.init;

import com.google.common.base.Suppliers;
import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class PlacedFeaturesInit {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BenTenMod.MOD_ID);


    public Supplier<List<OreConfiguration.TargetBlockState>> regOverWoldOres(Block normalOre,
            Block deepslateOre) {
        return Suppliers.memoize(() -> List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                        normalOre.defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        deepslateOre.defaultBlockState())));
    }

    public Supplier<List<OreConfiguration.TargetBlockState>> regNetherOre(Block netherOre) {
        return Suppliers.memoize(() -> List.of(OreConfiguration
            .target(OreFeatures.NETHER_ORE_REPLACEABLES, netherOre.defaultBlockState())));
    }

    public Supplier<List<OreConfiguration.TargetBlockState>> regEndOre(Block endOre) {
        return Suppliers.memoize(() -> List.of(OreConfiguration
            .target(new BlockMatchTest(Blocks.END_STONE), endOre.defaultBlockState())));
    }
}
