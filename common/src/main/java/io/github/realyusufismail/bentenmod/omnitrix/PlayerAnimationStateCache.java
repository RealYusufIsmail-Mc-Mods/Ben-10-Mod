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

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Keeps the transform animation window independently for every player rendered on a client. */
public final class PlayerAnimationStateCache {
    private static final int TRANSFORM_ANIMATION_TICKS = 20;
    private final Map<UUID, State> states = new HashMap<>();

    /** Starts a new animation window only when this player's form session changes. */
    public State bind(UUID playerId, ResourceLocation alienId, long expiresAt, long gameTime) {
        State state = states.get(playerId);
        if (state == null || !state.alienId().equals(alienId) || state.expiresAt() != expiresAt) {
            state = new State(alienId, expiresAt, gameTime + TRANSFORM_ANIMATION_TICKS);
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

    /**
     * Times are level game time, not the player's tick count, which restarts on respawn and
     * dimension change.
     */
    public record State(ResourceLocation alienId, long expiresAt, long transformUntilTick) {
        public boolean isTransforming(long gameTime) {
            return gameTime < transformUntilTick;
        }
    }
}
