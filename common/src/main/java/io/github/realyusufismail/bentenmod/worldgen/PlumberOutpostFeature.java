package io.github.realyusufismail.bentenmod.worldgen;

import com.mojang.serialization.Codec;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class PlumberOutpostFeature extends Feature<NoneFeatureConfiguration> {
    private static final int RADIUS = 7;
    private static final int HEIGHT = 4;
    private static final ResourceLocation LOOT_TABLE =
            new ResourceLocation("bentenmod", "chests/plumber_outpost");

    public PlumberOutpostFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin);
        int baseY = surface.getY();
        BlockPos ground = surface.below();
        if (baseY <= level.getMinBuildHeight() || baseY + HEIGHT >= level.getMaxBuildHeight()
                || !level.getBlockState(ground).isFaceSturdy(level, ground, Direction.UP)
                || !canWrite(level, origin, baseY)) {
            return false;
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        BlockState hull = BlockInit.PLUMBER_HULL.get().defaultBlockState();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                boolean center = x == 0 && z == 0;
                if (center || context.random().nextInt(8) != 0) {
                    set(level, pos, origin, x, baseY, z, hull);
                }
                if (Math.abs(x) == RADIUS || Math.abs(z) == RADIUS) {
                    for (int y = 1; y <= HEIGHT; y++) {
                        boolean doorway = z == RADIUS && x == 0 && y <= 2;
                        boolean broken = context.random().nextInt(y == HEIGHT ? 2 : 4) == 0;
                        if (!doorway && !broken) {
                            set(level, pos, origin, x, baseY + y, z, hull);
                        }
                    }
                }
            }
        }
        set(level, pos, origin, -3, baseY + 1, 2, hull);
        set(level, pos, origin, -2, baseY + 1, 2, hull);
        set(level, pos, origin, 3, baseY + 1, -2, hull);

        BlockPos chestPos = new BlockPos(origin.getX(), baseY + 1, origin.getZ());
        level.setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 3);
        if (level.getBlockEntity(chestPos) instanceof RandomizableContainerBlockEntity chest) {
            chest.setLootTable(LOOT_TABLE, context.random().nextLong());
        }
        return true;
    }

    private static boolean canWrite(WorldGenLevel level, BlockPos origin, int baseY) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                for (int y = baseY; y <= baseY + HEIGHT; y++) {
                    if (!level
                        .ensureCanWrite(new BlockPos(origin.getX() + x, y, origin.getZ() + z))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void set(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockPos origin,
            int x, int y, int z, BlockState state) {
        pos.set(origin.getX() + x, y, origin.getZ() + z);
        level.setBlock(pos, state, 2);
    }
}
