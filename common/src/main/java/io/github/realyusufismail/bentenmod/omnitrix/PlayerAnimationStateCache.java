package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Keeps the transform animation window independently for every player rendered on a client. */
public final class PlayerAnimationStateCache {
    private static final int TRANSFORM_ANIMATION_TICKS = 20;
    private final Map<UUID, State> states = new HashMap<>();

    /** Starts a new animation window only when this player's form session changes. */
    public State bind(UUID playerId, ResourceLocation alienId, long expiresAt, int playerTick) {
        State state = states.get(playerId);
        if (state == null || !state.alienId().equals(alienId) || state.expiresAt() != expiresAt) {
            state = new State(alienId, expiresAt, playerTick + TRANSFORM_ANIMATION_TICKS);
            states.put(playerId, state);
        }
        return state;
    }

    public State get(UUID playerId) {
        return states.get(playerId);
    }

    public void forget(UUID playerId) {
        states.remove(playerId);
    }

    public void clear() {
        states.clear();
    }

    public record State(ResourceLocation alienId, long expiresAt, int transformUntilTick) {
        public boolean isTransforming(int playerTick) {
            return playerTick < transformUntilTick;
        }
    }
}
