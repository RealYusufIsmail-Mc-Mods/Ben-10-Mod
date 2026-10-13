package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/** Read-only client cache populated exclusively by the server's state packets. */
public final class ClientOmnitrixState {
    private static final Map<UUID, OmnitrixSnapshot> PLAYERS = new ConcurrentHashMap<>();
    private static Consumer<UUID> dimensionRefresh = ignored -> {
    };
    private static Consumer<UUID> localFormTransition = ignored -> {
    };

    private ClientOmnitrixState() {}

    public static OmnitrixSnapshot get(UUID playerId) {
        return PLAYERS.get(playerId);
    }

    public static void accept(OmnitrixSnapshot snapshot) {
        OmnitrixSnapshot previous = PLAYERS.put(snapshot.playerId(), snapshot);
        ResourceLocation previousAlien = previous == null ? null : previous.currentAlien();
        if (!Objects.equals(previousAlien, snapshot.currentAlien())) {
            localFormTransition.accept(snapshot.playerId());
            dimensionRefresh.accept(snapshot.playerId());
        }
    }

    /**
     * Installed by the loader's client initializer without loading client classes in common code.
     */
    public static void installDimensionRefresh(Consumer<UUID> refresh) {
        dimensionRefresh = refresh;
    }

    /** Installs a loader-side callback that flashes only for a local-player form change. */
    public static void installLocalFormTransition(Consumer<UUID> transition) {
        localFormTransition = transition;
    }

    public static void remove(UUID playerId) {
        PLAYERS.remove(playerId);
    }

    public static void clear() {
        PLAYERS.clear();
        OmnitrixFlash.reset();
    }
}
