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
package io.github.realyusufismail.bentenmod.ability;

import io.github.realyusufismail.bentenmod.armorset.ArmorSets;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    /** Every armour-set and transformation source active for this player right now. */
    private static Map<ResourceLocation, List<Ability>> currentSources(ServerPlayer player) {
        Map<ResourceLocation, List<Ability>> current =
                new LinkedHashMap<>(ArmorSets.activeSets(player));
        OmnitrixService.activeDefinition(player)
            .ifPresent(definition -> current.put(
                    new ResourceLocation("bentenmod",
                            "transformation/"
                                    + OmnitrixService.snapshot(player).currentAlien().getPath()),
                    definition.abilities()));
        return current;
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
