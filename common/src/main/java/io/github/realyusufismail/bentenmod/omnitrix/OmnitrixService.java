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

import io.github.realyusufismail.bentenmod.alien.AlienDefinition;
import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import io.github.realyusufismail.bentenmod.entity.BossChatter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.ChatFormatting;

import java.util.List;
import java.util.Optional;

/** Server-authoritative transformation and unlock operations. */
public final class OmnitrixService {
    private OmnitrixService() {}

    public static OmnitrixSnapshot snapshot(ServerPlayer player) {
        OmnitrixSavedData.PlayerState state = state(player);
        AlienDefinition definition = state.currentAlien() == null ? null
                : Aliens.DATA.get(state.currentAlien()).orElse(null);
        OmnitrixSnapshot saved = state.snapshot(player.getUUID());
        List<ResourceLocation> unlocked = player.getAbilities().instabuild
                ? Aliens.DATA.all().keySet().stream().sorted().toList()
                : saved.unlockedAlienIds();
        return new OmnitrixSnapshot(saved.playerId(), saved.currentAlien(), saved.expiresAt(),
                saved.equipped(), unlocked, definition == null ? 0 : definition.width(),
                definition == null ? 0 : definition.height(), saved.timeoutDisabled(),
                saved.rechargeUntil(), OmnitrixPowerService.cooldownUntil(player),
                saved.transitionId(), saved.transitionKind());
    }

    /** Tracking clients need appearance and dimensions, while unlocks belong to the owner. */
    public static OmnitrixSnapshot trackingSnapshot(ServerPlayer player) {
        OmnitrixSnapshot state = snapshot(player);
        return new OmnitrixSnapshot(state.playerId(), state.currentAlien(), state.expiresAt(),
                state.equipped(), List.of(), state.width(), state.height(), state.timeoutDisabled(),
                state.rechargeUntil(), 0, state.transitionId(), state.transitionKind());
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
        if (!state.guideGiven()) {
            ItemStack guide = new ItemStack(ItemInit.OMNITRIX_GUIDE.get());
            if (!player.getInventory().add(guide)) {
                player.drop(guide, false);
            }
            state.markGuideGiven();
            data.setDirty();
        }
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
        long now = player.serverLevel().getServer().overworld().getGameTime();
        if (state.rechargeUntil() > now) {
            player.displayClientMessage(
                    Component.translatable("message.bentenmod.omnitrix_recharging",
                            formatTicks(state.rechargeUntil() - now)),
                    true);
            return false;
        }
        if (!player.getAbilities().instabuild && !state.isUnlocked(alien)) {
            return false;
        }
        state.transform(alien,
                state.timeoutDisabled() ? Long.MAX_VALUE : now + definition.durationTicks());
        state.markTransition(OmnitrixSnapshot.TRANSITION_FORM);
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
        state.markTransition(OmnitrixSnapshot.TRANSITION_REVERT);
        data.setDirty();
        player.refreshDimensions();
        return true;
    }

    /** Death changes the player back; returning from the End keeps the current form. */
    public static OmnitrixSnapshot onRespawn(ServerPlayer player, boolean alive) {
        if (!alive) {
            revert(player);
        }
        return snapshot(player);
    }

    /** Expires forms and reapplies their JSON hitbox after vanilla pose/dimension refreshes. */
    public static boolean tick(ServerPlayer player, long overworldTime) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData.PlayerState state = data.getOrCreate(player);
        OmnitrixPowerService.tick(player, overworldTime);
        ResourceLocation alien = state.currentAlien();
        if (alien == null) {
            return false;
        }
        if (Aliens.DATA.get(alien).isEmpty()) {
            state.revert();
            state.markTransition(OmnitrixSnapshot.TRANSITION_REVERT);
            data.setDirty();
            player.refreshDimensions();
            return true;
        }
        AlienDefinition definition = Aliens.DATA.get(alien).orElseThrow();
        if (!state.timeoutDisabled() && state.expiresAt() <= overworldTime) {
            state.timeout(overworldTime + definition.rechargeTicks());
            state.markTransition(OmnitrixSnapshot.TRANSITION_TIMEOUT);
            data.setDirty();
            player.refreshDimensions();
            player.displayClientMessage(
                    Component.translatable("message.bentenmod.omnitrix_timeout"), true);
            return true;
        }
        applyDimensions(player, definition);
        return false;
    }

    public static boolean usePower(ServerPlayer player) {
        return OmnitrixPowerService.use(player);
    }

    public static void forgetTransient(ServerPlayer player) {
        OmnitrixPowerService.forget(player);
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
            BossChatter.sayVariant(hunter, ChatFormatting.GOLD, "kraab.arrival", 2,
                    player.getName());
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

    private static String formatTicks(long ticks) {
        long seconds = Math.max(0, (ticks + 19) / 20);
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
