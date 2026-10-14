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
package io.github.realyusufismail.bentenmod.armorset;

import io.github.realyusufismail.bentenmod.ability.Ability;
import io.github.realyusufismail.bentenmod.data.CodecJsonReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** The armour sets loaded from data, and which of them a player is wearing. */
public final class ArmorSets {
    public static final CodecJsonReloadListener<ArmorSet> DATA =
            new CodecJsonReloadListener<>("armor_sets", ArmorSet.CODEC);

    private ArmorSets() {}

    /** Ability sources for the sets the player has fully equipped, keyed by source id. */
    public static Map<ResourceLocation, List<Ability>> activeSets(ServerPlayer player) {
        Map<ResourceLocation, List<Ability>> active = new LinkedHashMap<>();
        DATA.all().forEach((id, set) -> {
            if (set.isWornBy(player)) {
                active.put(id.withPrefix("armor_set/"), set.abilities());
            }
        });
        return active;
    }
}
