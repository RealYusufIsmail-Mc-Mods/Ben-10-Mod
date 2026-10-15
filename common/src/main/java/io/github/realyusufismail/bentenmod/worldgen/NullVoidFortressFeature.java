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

import com.mojang.serialization.Codec;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class NullVoidFortressFeature extends Feature<NoneFeatureConfiguration> {
    private static final int RADIUS = 7;
    private static final ResourceLocation LOOT_TABLE =
            new ResourceLocation("bentenmod", "chests/null_void_fortress");

    public NullVoidFortressFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin);
        int baseY = surface.getY();
        BlockPos ground = surface.below();
        if (baseY <= level.getMinBuildHeight() || baseY + 5 >= level.getMaxBuildHeight()
                || !level.getBlockState(ground).isFaceSturdy(level, ground, Direction.UP)
                || !canWrite(level, origin, baseY)) {
            return false;
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                setBlock(level, pos, origin, x, baseY, z,
                        BlockInit.NULL_VOID_STONE_BRICKS.get().defaultBlockState());
            }
        }
        buildWalls(level, pos, origin, baseY);
        buildArena(level, pos, origin, baseY);
        placeChest(level, origin, baseY, -5, -5, context.random());
        placeChest(level, origin, baseY, 5, -5, context.random());
        placeChest(level, origin, baseY, -5, 5, context.random());
        placeChest(level, origin, baseY, 5, 5, context.random());

        var boss = EntityTypesInit.ZS_SKAYR_ENTITY.get().create(level.getLevel());
        if (boss == null) {
            return false;
        }
        boss.moveTo(origin.getX() + 0.5, baseY + 1.0, origin.getZ() + 0.5,
                context.random().nextFloat() * 360.0F, 0.0F);
        boss.setPersistenceRequired();
        return level.addFreshEntity(boss);
    }

    private static boolean canWrite(WorldGenLevel level, BlockPos origin, int baseY) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                for (int y = baseY; y <= baseY + 5; y++) {
                    if (!level
                        .ensureCanWrite(new BlockPos(origin.getX() + x, y, origin.getZ() + z))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void buildWalls(WorldGenLevel level, BlockPos.MutableBlockPos pos,
            BlockPos origin, int baseY) {
        var bricks = BlockInit.NULL_VOID_STONE_BRICKS.get().defaultBlockState();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                boolean edge = Math.abs(x) == RADIUS || Math.abs(z) == RADIUS;
                if (!edge) {
                    continue;
                }
                boolean gate =
                        (x == 0 && Math.abs(z) == RADIUS) || (z == 0 && Math.abs(x) == RADIUS);
                boolean tower = Math.abs(x) == RADIUS && Math.abs(z) == RADIUS;
                int height = tower ? 5 : 2;
                for (int y = 1; y <= height; y++) {
                    if (gate && y <= 2) {
                        continue;
                    }
                    setBlock(level, pos, origin, x, baseY + y, z, bricks);
                }
            }
        }
    }

    private static void buildArena(WorldGenLevel level, BlockPos.MutableBlockPos pos,
            BlockPos origin, int baseY) {
        var bricks = BlockInit.NULL_VOID_STONE_BRICKS.get().defaultBlockState();
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                if (Math.max(Math.abs(x), Math.abs(z)) == 3) {
                    setBlock(level, pos, origin, x, baseY + 1, z, bricks);
                }
            }
        }
    }

    private static void placeChest(WorldGenLevel level, BlockPos origin, int baseY, int x, int z,
            RandomSource random) {
        BlockPos chestPos = new BlockPos(origin.getX() + x, baseY + 1, origin.getZ() + z);
        level.setBlock(chestPos, Blocks.CHEST.defaultBlockState(), 3);
        if (level.getBlockEntity(chestPos) instanceof RandomizableContainerBlockEntity chest) {
            chest.setLootTable(LOOT_TABLE, random.nextLong());
        }
    }

    private static void setBlock(WorldGenLevel level, BlockPos.MutableBlockPos pos, BlockPos origin,
            int x, int y, int z, net.minecraft.world.level.block.state.BlockState state) {
        pos.set(origin.getX() + x, y, origin.getZ() + z);
        level.setBlock(pos, state, 2);
    }
}
