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

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Read-only client cache populated exclusively by the server's state packets. */
public final class ClientOmnitrixState {
    private static final Map<UUID, OmnitrixSnapshot> PLAYERS = new ConcurrentHashMap<>();
    private static Consumer<UUID> dimensionRefresh = ignored -> {
    };
    private static BiConsumer<UUID, Boolean> localFormTransition = (ignored, timeout) -> {
    };

    private ClientOmnitrixState() {}

    public static OmnitrixSnapshot get(UUID playerId) {
        return PLAYERS.get(playerId);
    }

    public static void accept(OmnitrixSnapshot snapshot) {
        OmnitrixSnapshot previous = PLAYERS.put(snapshot.playerId(), snapshot);
        ResourceLocation previousAlien = previous == null ? null : previous.currentAlien();
        if (!Objects.equals(previousAlien, snapshot.currentAlien())) {
            boolean timeout = previousAlien != null && snapshot.currentAlien() == null
                    && snapshot.rechargeUntil() > (previous == null ? 0 : previous.rechargeUntil());
            localFormTransition.accept(snapshot.playerId(), timeout);
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
    public static void installLocalFormTransition(BiConsumer<UUID, Boolean> transition) {
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
