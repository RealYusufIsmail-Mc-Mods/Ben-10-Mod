package io.github.realyusufismail.bentenmod.omnitrix;

import io.github.realyusufismail.bentenmod.alien.AlienDefinition;
import io.github.realyusufismail.bentenmod.alien.Aliens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
                definition == null ? 0 : definition.height());
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
        OmnitrixSavedData.PlayerState state = state(player);
        tick(player, player.serverLevel().getServer().overworld().getGameTime());
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
        state.transform(alien, now + definition.durationTicks());
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
        if (state.expiresAt() <= overworldTime || Aliens.DATA.get(alien).isEmpty()) {
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

    /** Permanently binds a watch to the player's saved UUID state. */
    public static boolean equip(ServerPlayer player) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        boolean changed = data.getOrCreate(player).equip();
        if (changed) {
            data.setDirty();
        }
        return changed;
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
