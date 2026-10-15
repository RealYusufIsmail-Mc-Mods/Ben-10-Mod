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
