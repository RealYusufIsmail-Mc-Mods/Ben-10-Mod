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

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.bentenmod.ability.Ability;
import io.github.realyusufismail.bentenmod.ability.AbilityTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

/**
 * Abilities granted while every listed piece is worn. Loaded from
 * {@code data/<namespace>/armor_sets/<name>.json}:
 *
 * <pre>
 * {@code
 * {
 *   "pieces": {"head": "bentenmod:heatblast_helmet", "chest": "bentenmod:heatblast_chestplate"},
 *   "abilities": [{"type": "bentenmod:effect", "effect": "minecraft:fire_resistance"}]
 * }
 * }
 * </pre>
 */
public record ArmorSet(Map<EquipmentSlot, Item> pieces, List<Ability> abilities) {

    private static final Codec<EquipmentSlot> SLOT_CODEC = Codec.STRING.comapFlatMap(name -> {
        EquipmentSlot slot = byNameOrNull(name);
        return slot != null && slot.getType() == EquipmentSlot.Type.ARMOR ? DataResult.success(slot)
                : DataResult.error(() -> "Not an armour slot: " + name);
    }, EquipmentSlot::getName);

    public static final Codec<ArmorSet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.unboundedMap(SLOT_CODEC, BuiltInRegistries.ITEM.byNameCodec())
                .fieldOf("pieces")
                .forGetter(ArmorSet::pieces),
            AbilityTypes.ABILITY_CODEC.listOf().fieldOf("abilities").forGetter(ArmorSet::abilities))
        .apply(i, ArmorSet::new));

    public boolean isWornBy(Player player) {
        return !pieces.isEmpty() && pieces.entrySet()
            .stream()
            .allMatch(piece -> player.getItemBySlot(piece.getKey()).is(piece.getValue()));
    }

    private static EquipmentSlot byNameOrNull(String name) {
        try {
            return EquipmentSlot.byName(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
