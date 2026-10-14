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
import io.github.realyusufismail.bentenmod.ability.Ability;
import io.github.realyusufismail.bentenmod.ability.AbilitySlot;
import io.github.realyusufismail.bentenmod.ability.AbilityType;
import io.github.realyusufismail.bentenmod.ability.AbilityTypes;
import net.minecraft.server.level.ServerPlayer;

/** Cancels fall damage while active. JSON: {@code {"type": "bentenmod:no_fall_damage"}}. */
public record NoFallDamageAbility() implements Ability {
    public static final Codec<NoFallDamageAbility> CODEC = Codec.unit(NoFallDamageAbility::new);

    @Override
    public AbilityType<?> type() {
        return AbilityTypes.NO_FALL_DAMAGE;
    }

    @Override
    public void tick(ServerPlayer player, AbilitySlot slot) {
        player.fallDistance = 0.0F;
    }
}
