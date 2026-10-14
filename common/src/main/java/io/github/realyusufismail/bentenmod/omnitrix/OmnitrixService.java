package io.github.realyusufismail.bentenmod.omnitrix;

import io.github.realyusufismail.bentenmod.alien.AlienDefinition;
import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/** Server-authoritative transformation and unlock operations. */
public final class OmnitrixService {
    private OmnitrixService() {}

    public static OmnitrixSnapshot snapshot(ServerPlayer player) {
        OmnitrixSavedData.PlayerState state = state(player);
        AlienDefinition definition = state.currentAlien() == null ? null
                : Aliens.DATA.get(state.currentAlien()).orElse(null);
        OmnitrixSnapshot saved = state.snapshot(player.getUUID());
        return new OmnitrixSnapshot(saved.playerId(), saved.currentAlien(), saved.expiresAt(),
                saved.equipped(), saved.unlockedAlienIds(),
                definition == null ? 0 : definition.width(),
                definition == null ? 0 : definition.height(), saved.timeoutDisabled());
    }

    /** Tracking clients need appearance and dimensions, while unlocks belong to the owner. */
    public static OmnitrixSnapshot trackingSnapshot(ServerPlayer player) {
        OmnitrixSnapshot state = snapshot(player);
        return new OmnitrixSnapshot(state.playerId(), state.currentAlien(), state.expiresAt(),
                state.equipped(), java.util.List.of(), state.width(), state.height());
    }

    public static Optional<AlienDefinition> activeDefinition(ServerPlayer player) {
        OmnitrixSavedData.PlayerState state = state(player);
        if (state.currentAlien() == null) {
            return Optional.empty();
        }
        return Aliens.DATA.get(state.currentAlien());
    }

    /** Initializes the player's starter unlocks and reapplies saved dimensions after reconnect. */
    public static OmnitrixSnapshot onJoin(ServerPlayer player) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData.PlayerState state = data.getOrCreate(player);
        long now = player.serverLevel().getServer().overworld().getGameTime();
        tick(player, now);
        if (state.equipped() && !data.hasSpawnedHunter(player.getUUID())
                && data.hunterSpawnAt(player.getUUID()) < 0) {
            long delay = 2_400L + player.getRandom().nextInt(3_601);
            data.scheduleHunterSpawn(player.getUUID(), now + delay);
        }
        if (state.currentAlien() != null) {
            applyDimensions(player, Aliens.DATA.get(state.currentAlien()).orElse(null));
        }
        return snapshot(player);
    }

    /** Starts a form if the server confirms the equipped Omnitrix, definition and unlock. */
    public static boolean transform(ServerPlayer player, ResourceLocation alien) {
        if (!isEquipped(player)) {
            return false;
        }
        AlienDefinition definition = Aliens.DATA.get(alien).orElse(null);
        if (definition == null) {
            return false;
        }
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData.PlayerState state = data.getOrCreate(player);
        if (!state.isUnlocked(alien)) {
            return false;
        }
        long now = player.serverLevel().getServer().overworld().getGameTime();
        state.transform(alien,
                state.timeoutDisabled() ? Long.MAX_VALUE : now + definition.durationTicks());
        data.setDirty();
        player.refreshDimensions();
        applyDimensions(player, definition);
        return true;
    }

    /** Reverting requires the permanently equipped Omnitrix. */
    public static boolean revert(ServerPlayer player) {
        if (!isEquipped(player)) {
            return false;
        }
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData.PlayerState state = data.getOrCreate(player);
        if (state.currentAlien() == null) {
            return false;
        }
        state.revert();
        data.setDirty();
        player.refreshDimensions();
        return true;
    }

    /** Expires forms and reapplies their JSON hitbox after vanilla pose/dimension refreshes. */
    public static boolean tick(ServerPlayer player, long overworldTime) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData.PlayerState state = data.getOrCreate(player);
        ResourceLocation alien = state.currentAlien();
        if (alien == null) {
            return false;
        }
        if ((!state.timeoutDisabled() && state.expiresAt() <= overworldTime)
                || Aliens.DATA.get(alien).isEmpty()) {
            state.revert();
            data.setDirty();
            player.refreshDimensions();
            return true;
        }
        applyDimensions(player, Aliens.DATA.get(alien).orElseThrow());
        return false;
    }

    /** Adds a discovered alien to an existing Omnitrix and persists the unlock. */
    public static boolean unlock(ServerPlayer player, ResourceLocation alien) {
        if (Aliens.DATA.get(alien).isEmpty()) {
            return false;
        }
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        boolean changed = data.getOrCreate(player).unlock(alien);
        if (changed) {
            data.setDirty();
        }
        return changed;
    }

    public static boolean applyMasterControl(ServerPlayer player) {
        if (!isEquipped(player)) {
            return false;
        }
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData.PlayerState state = data.getOrCreate(player);
        state.disableTimeout();
        Aliens.DATA.all().keySet().forEach(state::unlock);
        data.setDirty();
        return true;
    }

    /** Permanently binds a watch to the player's saved UUID state. */
    public static boolean equip(ServerPlayer player) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        boolean changed = data.getOrCreate(player).equip();
        if (changed) {
            long now = player.serverLevel().getServer().overworld().getGameTime();
            long delay = 2_400L + player.getRandom().nextInt(3_601);
            data.scheduleHunterSpawn(player.getUUID(), now + delay);
            data.setDirty();
        }
        return changed;
    }

    public static void tickHunterSpawn(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        OmnitrixSavedData data = OmnitrixSavedData.get(level);
        long now = level.getServer().overworld().getGameTime();
        long spawnAt = data.hunterSpawnAt(player.getUUID());
        if (spawnAt < 0 || now < spawnAt) {
            return;
        }
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            data.rescheduleHunterSpawn(player.getUUID(), now + 1_200L);
            return;
        }

        BlockPos spawnPos = findHunterSpawnPosition(level, player);
        if (spawnPos == null) {
            data.rescheduleHunterSpawn(player.getUUID(), now + 1_200L);
            return;
        }

        CrabEntity hunter = EntityTypesInit.CRAB_ENTITY.get().create(level);
        if (hunter == null) {
            data.rescheduleHunterSpawn(player.getUUID(), now + 1_200L);
            return;
        }
        hunter.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5,
                level.getRandom().nextFloat() * 360.0F, 0.0F);
        hunter.setPersistenceRequired();
        if (level.addFreshEntity(hunter)) {
            data.completeHunterSpawn(player.getUUID());
        } else {
            data.rescheduleHunterSpawn(player.getUUID(), now + 1_200L);
        }
    }

    private static BlockPos findHunterSpawnPosition(ServerLevel level, ServerPlayer player) {
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = player.getRandom().nextDouble() * Math.PI * 2.0;
            int distance = 16 + player.getRandom().nextInt(17);
            int x = (int) Math.floor(player.getX() + Math.cos(angle) * distance);
            int z = (int) Math.floor(player.getZ() + Math.sin(angle) * distance);
            double dx = x + 0.5 - player.getX();
            double dz = z + 0.5 - player.getZ();
            double distanceSqr = dx * dx + dz * dz;
            if (distanceSqr < 256.0 || distanceSqr > 1_024.0) {
                continue;
            }
            BlockPos feet = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    new BlockPos(x, 0, z));
            BlockPos ground = feet.below();
            if (!level.getWorldBorder().isWithinBounds(feet)
                    || feet.getY() <= level.getMinBuildHeight()
                    || feet.getY() + 1 >= level.getMaxBuildHeight()
                    || !level.getBlockState(ground).isFaceSturdy(level, ground, Direction.UP)
                    || !level.getBlockState(feet).isAir()
                    || !level.getBlockState(feet.above()).isAir()
                    || !level.getFluidState(feet).isEmpty()
                    || !level.getFluidState(feet.above()).isEmpty()) {
                continue;
            }
            CrabEntity candidate = EntityTypesInit.CRAB_ENTITY.get().create(level);
            if (candidate == null) {
                return null;
            }
            candidate.setPos(feet.getX() + 0.5, feet.getY(), feet.getZ() + 0.5);
            boolean clear = level.getWorldBorder().isWithinBounds(candidate.getBoundingBox())
                    && level.noCollision(candidate);
            candidate.discard();
            if (clear) {
                return feet;
            }
        }
        return null;
    }

    public static boolean isEquipped(ServerPlayer player) {
        return state(player).equipped();
    }

    private static OmnitrixSavedData.PlayerState state(ServerPlayer player) {
        return OmnitrixSavedData.get(player.serverLevel()).getOrCreate(player);
    }

    private static void applyDimensions(ServerPlayer player, AlienDefinition definition) {
        if (definition == null) {
            return;
        }
        double width = definition.width();
        double height = definition.height();
        AABB current = player.getBoundingBox();
        if (Math.abs(current.getXsize() - width) < 0.001
                && Math.abs(current.getZsize() - width) < 0.001
                && Math.abs(current.getYsize() - height) < 0.001) {
            return;
        }
        player.setBoundingBox(
                AABB.ofSize(player.position().add(0, height / 2, 0), width, height, width));
    }
}
