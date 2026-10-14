package io.github.realyusufismail.bentenmod.worldgen;

import com.mojang.serialization.Codec;
import io.github.realyusufismail.bentenmod.core.blocks.PrisonVaultBlock;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class FloatingPrisonFeature extends Feature<NoneFeatureConfiguration> {
    private static final int RADIUS = 8;
    private static final int HEIGHT = 6;

    public FloatingPrisonFeature(Codec<NoneFeatureConfiguration> codec) {
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
        BlockState wall = BlockInit.PRISON_WALL.get().defaultBlockState();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                set(level, pos, origin, x, baseY, z, wall);
                boolean edge = Math.abs(x) == RADIUS || Math.abs(z) == RADIUS;
                for (int y = 1; y <= HEIGHT; y++) {
                    if (edge) {
                        boolean doorway =
                                (z == RADIUS && x == 0 || x == RADIUS && z == 0) && y <= 2;
                        if (!doorway) {
                            set(level, pos, origin, x, baseY + y, z, wall);
                        }
                    }
                    if ((x == -5 || x == -1 || x == 1 || x == 5) && y <= 3 && z >= -6 && z <= 6
                            && z != 0) {
                        set(level, pos, origin, x, baseY + y, z, connectedBars(x, z));
                    }
                    if ((z == -5 || z == 5) && y <= 3 && x >= -6 && x <= 6 && x != 0) {
                        set(level, pos, origin, x, baseY + y, z, connectedBars(x, z));
                    }
                }
            }
        }

        placeSpawner(level, origin, baseY, -3, -3, context.random());
        placeSpawner(level, origin, baseY, 3, -3, context.random());
        BlockPos vaultPos = new BlockPos(origin.getX(), baseY + 1, origin.getZ() + 7);
        level.setBlock(vaultPos,
                BlockInit.PRISON_VAULT.get()
                    .defaultBlockState()
                    .setValue(PrisonVaultBlock.FACING, Direction.SOUTH)
                    .setValue(PrisonVaultBlock.OPEN, false),
                3);

        var warden = EntityTypesInit.PRISON_WARDEN.get().create(level.getLevel());
        if (warden == null) {
            return false;
        }
        warden.moveTo(origin.getX() + 0.5, baseY + 1.0, origin.getZ() + 2.0,
                context.random().nextFloat() * 360.0F, 0.0F);
        warden.setPersistenceRequired();
        return level.addFreshEntity(warden);
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

    private static void placeSpawner(WorldGenLevel level, BlockPos origin, int baseY, int x, int z,
            RandomSource random) {
        BlockPos spawnerPos = new BlockPos(origin.getX() + x, baseY + 1, origin.getZ() + z);
        level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 3);
        if (level.getBlockEntity(spawnerPos) instanceof SpawnerBlockEntity spawner) {
            spawner.setEntityId(EntityTypesInit.NULL_VOID_PRISONER.get(), random);
        }
    }

    private static BlockState connectedBars(int x, int z) {
        return Blocks.IRON_BARS.defaultBlockState()
            .setValue(CrossCollisionBlock.NORTH, isBarAt(x, z - 1))
            .setValue(CrossCollisionBlock.EAST, isBarAt(x + 1, z))
            .setValue(CrossCollisionBlock.SOUTH, isBarAt(x, z + 1))
            .setValue(CrossCollisionBlock.WEST, isBarAt(x - 1, z));
    }

    private static boolean isBarAt(int x, int z) {
        return ((x == -5 || x == -1 || x == 1 || x == 5) && z >= -6 && z <= 6 && z != 0)
                || ((z == -5 || z == 5) && x >= -6 && x <= 6 && x != 0);
    }

    private static void set(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockPos origin,
            int x, int y, int z, BlockState state) {
        pos.set(origin.getX() + x, y, origin.getZ() + z);
        level.setBlock(pos, state, 2);
    }
}
