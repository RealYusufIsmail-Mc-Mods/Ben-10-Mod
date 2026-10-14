package io.github.realyusufismail.bentenmod.worldgen;

import com.mojang.serialization.Codec;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;

public final class OmnitrixCrashSiteFeature extends Feature<NoneFeatureConfiguration> {
    public OmnitrixCrashSiteFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        int radius = 4 + random.nextInt(2);
        int centerX = origin.getX();
        int centerZ = origin.getZ();
        int centerSurface = surfaceHeight(level, centerX, centerZ);
        BlockPos centerGround = new BlockPos(centerX, centerSurface - 1, centerZ);

        if (centerSurface <= level.getMinBuildHeight() + 3
                || centerSurface >= level.getMaxBuildHeight() - 3
                || !isGround(level.getBlockState(centerGround))) {
            return false;
        }

        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                double distance = Math.sqrt(xOffset * xOffset + zOffset * zOffset);
                if (distance > radius) {
                    continue;
                }

                int x = centerX + xOffset;
                int z = centerZ + zOffset;
                int surfaceY = surfaceHeight(level, x, z);
                BlockPos surface = new BlockPos(x, surfaceY - 1, z);
                if (surfaceY <= level.getMinBuildHeight() + 3
                        || !isGround(level.getBlockState(surface))) {
                    continue;
                }

                int depth = (int) Math.round((radius - distance) / radius * 2.0);
                int floorY = surfaceY - 1 - depth;
                for (int y = floorY + 1; y <= surfaceY + 1; y++) {
                    level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 2);
                }
                level.setBlock(new BlockPos(x, floorY, z), craterBlock(distance, radius, random),
                        2);
            }
        }

        int podY = centerSurface - 2;
        level.setBlock(new BlockPos(centerX, podY, centerZ),
                BlockInit.OMNITRIX_POD.get().defaultBlockState(), 2);
        return true;
    }

    private static BlockState craterBlock(double distance, int radius, RandomSource random) {
        if (distance >= radius - 1.1) {
            return random.nextInt(3) == 0 ? Blocks.OBSIDIAN.defaultBlockState()
                    : Blocks.BLACKSTONE.defaultBlockState();
        }
        if (distance < 1.5 || random.nextInt(5) == 0) {
            return Blocks.MAGMA_BLOCK.defaultBlockState();
        }
        return random.nextBoolean() ? Blocks.COARSE_DIRT.defaultBlockState()
                : Blocks.BLACKSTONE.defaultBlockState();
    }

    private static boolean isGround(BlockState state) {
        return !state.isAir() && !state.is(BlockTags.LEAVES) && !state.is(BlockTags.LOGS)
                && state.getFluidState().isEmpty() && state.blocksMotion();
    }

    private static int surfaceHeight(WorldGenLevel level, int x, int z) {
        int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        if (surfaceY > level.getMinBuildHeight()
                && !level.getBlockState(new BlockPos(x, surfaceY - 1, z))
                    .getFluidState()
                    .isEmpty()) {
            return level.getMinBuildHeight();
        }
        while (surfaceY > level.getMinBuildHeight() + 1
                && !isGround(level.getBlockState(new BlockPos(x, surfaceY - 1, z)))) {
            surfaceY--;
        }
        return surfaceY;
    }
}
