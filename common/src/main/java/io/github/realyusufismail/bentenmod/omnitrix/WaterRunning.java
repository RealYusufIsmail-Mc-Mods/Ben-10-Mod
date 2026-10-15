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
package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;

/**
 * Keeps a sprinting speedster on top of water. Player movement runs on the client, so the local
 * player's client calls this at the end of every tick; it lifts the player back to the surface and
 * marks them as standing, which also lets them jump off the water.
 */
public final class WaterRunning {
    /** Horizontal speed (blocks per tick) needed to stay up; a sprinting XLR8 runs at about 0.3. */
    public static final double MIN_SPEED = 0.2;
    private static final double MAX_SINK = 0.6;

    private WaterRunning() {}

    /** Returns true when the player was held on the surface this tick. */
    public static boolean keepOnSurface(Player player) {
        if (!player.isSprinting() || player.isShiftKeyDown() || player.getAbilities().flying
                || player.getDeltaMovement().horizontalDistance() < MIN_SPEED) {
            return false;
        }
        Level level = player.level();
        BlockPos feet = BlockPos.containing(player.getX(), player.getY(), player.getZ());
        double surface = surfaceAt(level, feet);
        if (Double.isNaN(surface)) {
            surface = surfaceAt(level, feet.below());
        }
        if (Double.isNaN(surface) || player.getY() > surface
                || player.getY() < surface - MAX_SINK) {
            return false;
        }
        player.setPos(player.getX(), surface, player.getZ());
        var motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, Math.max(0.0, motion.y), motion.z);
        player.setOnGround(true);
        player.resetFallDistance();
        return true;
    }

    /** The top of the water at {@code pos} if it is the topmost water block, else NaN. */
    private static double surfaceAt(Level level, BlockPos pos) {
        FluidState fluid = level.getFluidState(pos);
        if (!fluid.is(FluidTags.WATER) || !level.getFluidState(pos.above()).isEmpty()
                || !level.getBlockState(pos.above())
                    .getCollisionShape(level, pos.above())
                    .isEmpty()) {
            return Double.NaN;
        }
        return pos.getY() + fluid.getHeight(level, pos);
    }
}
