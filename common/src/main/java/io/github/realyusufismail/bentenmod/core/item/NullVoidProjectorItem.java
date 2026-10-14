package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSavedData;
import io.github.realyusufismail.bentenmod.worldgen.NullVoidWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Set;

public class NullVoidProjectorItem extends Item {
    private static final int COOLDOWN_TICKS = 100;

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

        OmnitrixSavedData data = OmnitrixSavedData.get(serverPlayer.serverLevel());
        boolean returning =
                serverPlayer.serverLevel().dimension().equals(NullVoidWorldgen.DIMENSION);
        boolean teleported =
                returning ? returnPlayer(serverPlayer, data) : enterNullVoid(serverPlayer, data);
        if (!teleported) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.bentenmod.null_void_projector_failed"), true);
            return InteractionResultHolder.fail(stack);
        }

        stack.hurtAndBreak(1, serverPlayer, broken -> broken.broadcastBreakEvent(hand));
        serverPlayer.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        serverPlayer.serverLevel()
            .playSound(null, serverPlayer.blockPosition(), SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemInit.NULL_CRYSTAL.get());
    }

    private static boolean enterNullVoid(ServerPlayer player, OmnitrixSavedData data) {
        ServerLevel target = player.serverLevel().getServer().getLevel(NullVoidWorldgen.DIMENSION);
        if (target == null) {
            return false;
        }

        BlockPos landing = findSafeLanding(target, player.blockPosition());
        double x;
        double y;
        double z;
        if (landing == null) {
            BlockPos center = player.blockPosition();
            if (100 <= target.getMinBuildHeight() || 102 >= target.getMaxBuildHeight()) {
                return false;
            }
            for (int xOffset = -2; xOffset <= 2; xOffset++) {
                for (int zOffset = -2; zOffset <= 2; zOffset++) {
                    BlockPos platform =
                            new BlockPos(center.getX() + xOffset, 100, center.getZ() + zOffset);
                    target.setBlock(platform,
                            BlockInit.NULL_VOID_STONE_BRICKS.get().defaultBlockState(), 3);
                }
            }
            x = center.getX() + 0.5;
            y = 101.0;
            z = center.getZ() + 0.5;
        } else {
            x = landing.getX() + 0.5;
            y = landing.getY();
            z = landing.getZ() + 0.5;
        }

        data.setReturnPoint(player.getUUID(),
                new OmnitrixSavedData.ReturnPoint(player.serverLevel().dimension().location(),
                        player.getX(), player.getY(), player.getZ(), player.getYRot(),
                        player.getXRot()));
        return player.teleportTo(target, x, y, z, Set.of(), player.getYRot(), player.getXRot());
    }

    private static boolean returnPlayer(ServerPlayer player, OmnitrixSavedData data) {
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
        return player.teleportTo(target, x, y, z, Set.of(), point.yaw(), point.pitch());
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

    private static BlockPos findSafeLanding(ServerLevel level, BlockPos origin) {
        for (int radius = 0; radius <= 16; radius++) {
            for (int xOffset = -radius; xOffset <= radius; xOffset++) {
                for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                    if (Math.max(Math.abs(xOffset), Math.abs(zOffset)) != radius) {
                        continue;
                    }
                    int x = origin.getX() + xOffset;
                    int z = origin.getZ() + zOffset;
                    BlockPos feet = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            new BlockPos(x, 0, z));
                    BlockPos ground = feet.below();
                    if (feet.getY() <= level.getMinBuildHeight()
                            || feet.getY() + 1 >= level.getMaxBuildHeight()
                            || !level.getWorldBorder().isWithinBounds(feet)
                            || !level.getBlockState(ground)
                                .isFaceSturdy(level, ground, Direction.UP)
                            || !level.getBlockState(feet).isAir()
                            || !level.getBlockState(feet.above()).isAir()
                            || !level.getFluidState(feet).isEmpty()
                            || !level.getFluidState(feet.above()).isEmpty()) {
                        continue;
                    }
                    return feet;
                }
            }
        }
        return null;
    }
}
