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
