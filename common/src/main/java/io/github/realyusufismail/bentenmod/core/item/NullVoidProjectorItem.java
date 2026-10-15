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
package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.entity.NullVoidPortalEntity;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSavedData;
import io.github.realyusufismail.bentenmod.platform.Services;
import io.github.realyusufismail.bentenmod.worldgen.NullVoidWorldgen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class NullVoidProjectorItem extends Item {
    public static final int COOLDOWN_TICKS = 100;
    private static final int LANDING_SEARCH_RADIUS = 96;
    private static final int ISLAND_HINT_RADIUS = 256;
    private static final int FALLBACK_Y = 100;

    public NullVoidProjectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
            InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.pass(stack);
        }
        if (serverPlayer.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }
        Vec3 look = serverPlayer.getLookAngle();
        double x = serverPlayer.getX() + look.x * 3.0;
        double y = serverPlayer.getEyeY() - 1.5;
        double z = serverPlayer.getZ() + look.z * 3.0;
        NullVoidPortalEntity portal =
                EntityTypesInit.NULL_VOID_PORTAL.get().create(serverPlayer.serverLevel());
        if (portal == null) {
            return InteractionResultHolder.fail(stack);
        }
        portal.setOwner(serverPlayer.getUUID());
        portal.setPos(x, y, z);
        portal.setYRot(serverPlayer.getYRot() + 180.0F);
        if (!serverPlayer.serverLevel().addFreshEntity(portal)) {
            return InteractionResultHolder.fail(stack);
        }

        serverPlayer.serverLevel()
            .playSound(null, BlockPos.containing(x, y, z), SoundEvents.PORTAL_TRAVEL,
                    SoundSource.BLOCKS, 0.85F, 0.88F);
        stack.hurtAndBreak(1, serverPlayer, broken -> broken.broadcastBreakEvent(hand));
        serverPlayer.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemInit.NULL_CRYSTAL.get());
    }

    public static boolean transferPlayer(ServerPlayer player) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        boolean returning = player.serverLevel().dimension().equals(NullVoidWorldgen.DIMENSION);
        return returning ? returnPlayer(player, data) : enterNullVoid(player, data);
    }

    public static boolean enterNullVoid(ServerPlayer player, OmnitrixSavedData data) {
        ServerLevel target = player.serverLevel().getServer().getLevel(NullVoidWorldgen.DIMENSION);
        if (target == null) {
            return false;
        }

        BlockPos origin = player.blockPosition();
        BlockPos landing = findSafeLanding(target, origin);
        boolean fallback = landing == null;
        BlockPos nearestIsland = null;
        BlockPos fallbackPad = null;
        double x;
        double y;
        double z;
        if (fallback) {
            if (FALLBACK_Y <= target.getMinBuildHeight()
                    || FALLBACK_Y + 8 >= target.getMaxBuildHeight()) {
                return false;
            }
            fallbackPad = buildFallbackPad(target, origin);
            x = fallbackPad.getX() + 0.5;
            y = fallbackPad.getY() + 1.0;
            z = fallbackPad.getZ() + 0.5;
            nearestIsland = findNearestIsland(target, origin, ISLAND_HINT_RADIUS);
        } else {
            x = landing.getX() + 0.5;
            y = landing.getY();
            z = landing.getZ() + 0.5;
        }

        data.setReturnPoint(player.getUUID(),
                new OmnitrixSavedData.ReturnPoint(player.serverLevel().dimension().location(),
                        player.getX(), player.getY(), player.getZ(), player.getYRot(),
                        player.getXRot()));
        Services.PLATFORM.sendPortalTravelEffect(player);
        boolean transferred =
                player.teleportTo(target, x, y, z, Set.of(), player.getYRot(), player.getXRot());
        if (transferred) {
            if (fallbackPad != null && nearestIsland != null) {
                sendIslandTrail(target, fallbackPad, nearestIsland);
            }
            announceArrival(player, data, fallback, origin, nearestIsland);
        }
        return transferred;
    }

    private static void announceArrival(ServerPlayer player, OmnitrixSavedData data,
            boolean fallback, BlockPos origin, BlockPos nearestIsland) {
        if (data.getOrCreate(player).markNullVoidArrivalHintShown()) {
            data.setDirty();
            player.sendSystemMessage(
                    Component.translatable("message.bentenmod.null_void_first_arrival"));
        }
        if (!fallback) {
            return;
        }
        if (nearestIsland == null) {
            player.sendSystemMessage(
                    Component.translatable("message.bentenmod.null_void_no_island_nearby"));
            return;
        }
        int dx = nearestIsland.getX() - origin.getX();
        int dz = nearestIsland.getZ() - origin.getZ();
        int distance = (int) Math.round(Math.sqrt((double) dx * dx + (double) dz * dz));
        String direction = direction(dx, dz);
        String vertical = nearestIsland.getY() >= FALLBACK_Y ? "above" : "below";
        player
            .sendSystemMessage(Component.translatable("message.bentenmod.null_void_nearest_island",
                    Component.translatable("direction.bentenmod." + direction), distance,
                    Component.translatable("direction.bentenmod." + vertical)));
    }

    private static String direction(int dx, int dz) {
        if (Math.abs(dx) > Math.abs(dz)) {
            return dx < 0 ? "west" : "east";
        }
        return dz < 0 ? "north" : "south";
    }

    private static BlockPos buildFallbackPad(ServerLevel level, BlockPos origin) {
        BlockPos center = new BlockPos(origin.getX(), FALLBACK_Y, origin.getZ());
        for (int xOffset = -2; xOffset <= 2; xOffset++) {
            for (int zOffset = -2; zOffset <= 2; zOffset++) {
                BlockPos platform = center.offset(xOffset, 0, zOffset);
                level.setBlock(platform, BlockInit.NULL_VOID_STONE_BRICKS.get().defaultBlockState(),
                        3);
            }
        }
        level.setBlock(center, Blocks.SEA_LANTERN.defaultBlockState(), 3);
        for (int yOffset = 1; yOffset <= 7; yOffset++) {
            BlockPos rod = center.offset(1, yOffset, 0);
            level.setBlock(rod, Blocks.END_ROD.defaultBlockState()
                .setValue(DirectionalBlock.FACING, Direction.UP), 3);
        }
        return center;
    }

    private static void sendIslandTrail(ServerLevel level, BlockPos pad, BlockPos island) {
        double dx = island.getX() + 0.5 - (pad.getX() + 0.5);
        double dy = island.getY() + 1.0 - (pad.getY() + 1.0);
        double dz = island.getZ() + 0.5 - (pad.getZ() + 0.5);
        for (int i = 1; i <= 20; i++) {
            double fraction = i / 21.0;
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.END_ROD,
                    pad.getX() + 0.5 + dx * fraction, pad.getY() + 1.0 + dy * fraction,
                    pad.getZ() + 0.5 + dz * fraction, 1, 0.03, 0.03, 0.03, 0.005);
        }
    }

    public static boolean returnPlayer(ServerPlayer player, OmnitrixSavedData data) {
        OmnitrixSavedData.ReturnPoint point = data.returnPoint(player.getUUID());
        ServerLevel target = null;
        if (point != null) {
            ResourceKey<Level> dimension =
                    ResourceKey.create(Registries.DIMENSION, point.dimension());
            target = player.serverLevel().getServer().getLevel(dimension);
        }
        if (target == null) {
            target = player.serverLevel().getServer().overworld();
            BlockPos spawn = target.getSharedSpawnPos();
            Services.PLATFORM.sendPortalTravelEffect(player);
            return player.teleportTo(target, spawn.getX() + 0.5, spawn.getY() + 1.0,
                    spawn.getZ() + 0.5, Set.of(), target.getSharedSpawnAngle(), 0.0F);
        }
        double x = point.x();
        double y = point.y();
        double z = point.z();
        if (!isSafeReturn(target, player, x, y, z)) {
            BlockPos upward = findUpwardReturn(target, player, x, y, z);
            y = upward != null ? upward.getY()
                    : target
                        .getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                                BlockPos.containing(x, 0.0, z))
                        .getY();
        }
        Services.PLATFORM.sendPortalTravelEffect(player);
        return player.teleportTo(target, x, y, z, Set.of(), point.yaw(), point.pitch());
    }

    public static boolean banishEntity(ServerLevel source, Entity entity) {
        ServerLevel target = source.getServer().getLevel(NullVoidWorldgen.DIMENSION);
        if (target == null) {
            return false;
        }
        BlockPos origin = BlockPos.containing(entity.getX() + source.random.nextInt(65) - 32, 0,
                entity.getZ() + source.random.nextInt(65) - 32);
        BlockPos landing = findSafeLanding(target, origin);
        if (landing == null) {
            landing = buildFallbackPad(target, origin);
            landing = landing.above();
        }
        if (source == target) {
            entity.teleportTo(landing.getX() + 0.5, landing.getY(), landing.getZ() + 0.5);
            return true;
        }
        Entity moved = entity.changeDimension(target);
        if (moved == null) {
            return false;
        }
        moved.teleportTo(landing.getX() + 0.5, landing.getY(), landing.getZ() + 0.5);
        return true;
    }

    public static BlockPos findSafeLanding(ServerLevel level, BlockPos origin) {
        for (int radius = 0; radius <= 16; radius++) {
            BlockPos result = checkRing(level, origin, radius, 1);
            if (result != null) {
                return result;
            }
        }
        for (int radius = 20; radius <= LANDING_SEARCH_RADIUS; radius += 4) {
            BlockPos result = checkRing(level, origin, radius, 4);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static BlockPos findNearestIsland(ServerLevel level, BlockPos origin, int radiusLimit) {
        for (int radius = 0; radius <= radiusLimit; radius += 16) {
            BlockPos result = checkRing(level, origin, radius, 16);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static BlockPos checkRing(ServerLevel level, BlockPos origin, int radius, int step) {
        if (radius == 0) {
            return safeFeet(level, origin.getX(), origin.getZ());
        }
        for (int offset = -radius; offset <= radius; offset += step) {
            BlockPos candidate = safeFeet(level, origin.getX() - radius, origin.getZ() + offset);
            if (candidate != null) {
                return candidate;
            }
            candidate = safeFeet(level, origin.getX() + radius, origin.getZ() + offset);
            if (candidate != null) {
                return candidate;
            }
            candidate = safeFeet(level, origin.getX() + offset, origin.getZ() - radius);
            if (candidate != null) {
                return candidate;
            }
            candidate = safeFeet(level, origin.getX() + offset, origin.getZ() + radius);
            if (candidate != null) {
                return candidate;
            }
        }
        return null;
    }

    private static BlockPos safeFeet(ServerLevel level, int x, int z) {
        BlockPos feet = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                new BlockPos(x, 0, z));
        BlockPos ground = feet.below();
        if (feet.getY() <= level.getMinBuildHeight() || feet.getY() + 1 >= level.getMaxBuildHeight()
                || !level.getWorldBorder().isWithinBounds(feet)
                || !level.getBlockState(ground).isFaceSturdy(level, ground, Direction.UP)
                || !level.getBlockState(feet).isAir() || !level.getBlockState(feet.above()).isAir()
                || !level.getFluidState(feet).isEmpty()
                || !level.getFluidState(feet.above()).isEmpty()) {
            return null;
        }
        return feet;
    }

    private static boolean isSafeReturn(ServerLevel level, ServerPlayer player, double x, double y,
            double z) {
        BlockPos feet = BlockPos.containing(x, y, z);
        BlockPos ground = feet.below();
        return level.isInWorldBounds(feet)
                && level.getBlockState(ground).isFaceSturdy(level, ground, Direction.UP) && level
                    .noCollision(player, player.getType().getDimensions().makeBoundingBox(x, y, z));
    }

    private static BlockPos findUpwardReturn(ServerLevel level, ServerPlayer player, double x,
            double y, double z) {
        for (int feetY = (int) Math.floor(y); feetY < level.getMaxBuildHeight(); feetY++) {
            if (isSafeReturn(level, player, x, feetY, z)) {
                return BlockPos.containing(x, feetY, z);
            }
        }
        return null;
    }
}
