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

package io.github.realyusufismail.bentenmod.ability.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.bentenmod.ability.Ability;
import io.github.realyusufismail.bentenmod.ability.AbilitySlot;
import io.github.realyusufismail.bentenmod.ability.AbilityType;
import io.github.realyusufismail.bentenmod.ability.AbilityTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Locale;

/**
 * Adds an attribute modifier while active. JSON: {@code {"type": "bentenmod:attribute",
 * "attribute": "minecraft:generic.attack_damage", "operation": "addition", "amount": 6}}.
 * Operations: {@code addition}, {@code multiply_base}, {@code multiply_total}.
 */
public record AttributeAbility(Attribute attribute, AttributeModifier.Operation operation,
        double amount) implements Ability {

    private static final Codec<AttributeModifier.Operation> OPERATION_CODEC =
            Codec.STRING.comapFlatMap(name -> {
                try {
                    return DataResult.success(
                            AttributeModifier.Operation.valueOf(name.toUpperCase(Locale.ROOT)));
                } catch (IllegalArgumentException e) {
                    return DataResult.error(() -> "Unknown attribute operation " + name);
                }
            }, operation -> operation.name().toLowerCase(Locale.ROOT));

    public static final Codec<AttributeAbility> CODEC =
            RecordCodecBuilder.create(
                    i -> i
                        .group(BuiltInRegistries.ATTRIBUTE.byNameCodec()
                            .fieldOf("attribute")
                            .forGetter(AttributeAbility::attribute),
                                OPERATION_CODEC
                                    .optionalFieldOf("operation",
                                            AttributeModifier.Operation.ADDITION)
                                    .forGetter(AttributeAbility::operation),
                                Codec.DOUBLE.fieldOf("amount").forGetter(AttributeAbility::amount))
                        .apply(i, AttributeAbility::new));

    @Override
    public AbilityType<?> type() {
        return AbilityTypes.ATTRIBUTE;
    }

    @Override
    public void tick(ServerPlayer player, AbilitySlot slot) {
        // Checked every tick: a respawned player is a new entity without the modifier.
        var instance = player.getAttribute(attribute);
        if (instance != null && instance.getModifier(slot.uuid()) == null) {
            instance.addTransientModifier(new AttributeModifier(slot.uuid(),
                    slot.source().toString(), amount, operation));
        }
    }

    @Override
    public void stop(ServerPlayer player, AbilitySlot slot) {
        var instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(slot.uuid());
        }
        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }
}
