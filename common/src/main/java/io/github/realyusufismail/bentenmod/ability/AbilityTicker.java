/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.ability;

import io.github.realyusufismail.bentenmod.armorset.ArmorSets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Runs abilities for each player. Each loader calls {@link #tick} once per player per server tick
 * and {@link #forget} when a player leaves.
 */
public final class AbilityTicker {
    private static final Map<UUID, Map<ResourceLocation, List<Ability>>> ACTIVE = new HashMap<>();

    private AbilityTicker() {}

    /** Every source active for the player right now. Alien forms join this in step 3. */
    private static Map<ResourceLocation, List<Ability>> currentSources(ServerPlayer player) {
        return ArmorSets.activeSets(player);
    }

    public static void tick(ServerPlayer player) {
        Map<ResourceLocation, List<Ability>> previous =
                ACTIVE.getOrDefault(player.getUUID(), Map.of());
        Map<ResourceLocation, List<Ability>> current = currentSources(player);

        // Stops first, so a source that is still active re-asserts its state in the same tick.
        previous.forEach((source, abilities) -> {
            if (!abilities.equals(current.get(source))) {
                forEach(source, abilities, (ability, slot) -> ability.stop(player, slot));
            }
        });
        current.forEach((source, abilities) -> {
            if (!abilities.equals(previous.get(source))) {
                forEach(source, abilities, (ability, slot) -> ability.start(player, slot));
            }
        });
        current.forEach((source, abilities) -> forEach(source, abilities,
                (ability, slot) -> ability.tick(player, slot)));

        if (current.isEmpty()) {
            ACTIVE.remove(player.getUUID());
        } else {
            ACTIVE.put(player.getUUID(), current);
        }
    }

    /** Ends every active ability, e.g. when the player disconnects. */
    public static void forget(ServerPlayer player) {
        Map<ResourceLocation, List<Ability>> previous = ACTIVE.remove(player.getUUID());
        if (previous != null) {
            previous.forEach((source, abilities) -> forEach(source, abilities,
                    (ability, slot) -> ability.stop(player, slot)));
        }
    }

    private static void forEach(ResourceLocation source, List<Ability> abilities,
            AbilityAction action) {
        for (int i = 0; i < abilities.size(); i++) {
            action.run(abilities.get(i), new AbilitySlot(source, i));
        }
    }

    @FunctionalInterface
    private interface AbilityAction {
        void run(Ability ability, AbilitySlot slot);
    }
}
