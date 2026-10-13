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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.bentenmod.ability.Ability;
import io.github.realyusufismail.bentenmod.ability.AbilitySlot;
import io.github.realyusufismail.bentenmod.ability.AbilityType;
import io.github.realyusufismail.bentenmod.ability.AbilityTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

/**
 * Keeps a potion effect on the player. JSON: {@code {"type": "bentenmod:effect", "effect":
 * "minecraft:speed", "amplifier": 2}}; amplifier 0 is level I.
 */
public record EffectAbility(MobEffect effect, int amplifier) implements Ability {
    /** Long enough that night vision never starts flashing (it does below 200 ticks). */
    private static final int DURATION = 300;
    private static final int REFRESH_BELOW = 220;

    public static final Codec<EffectAbility> CODEC = RecordCodecBuilder.create(
            i -> i
                .group(BuiltInRegistries.MOB_EFFECT.byNameCodec()
                    .fieldOf("effect")
                    .forGetter(EffectAbility::effect),
                        Codec.intRange(0, 255)
                            .optionalFieldOf("amplifier", 0)
                            .forGetter(EffectAbility::amplifier))
                .apply(i, EffectAbility::new));

    @Override
    public AbilityType<?> type() {
        return AbilityTypes.EFFECT;
    }

    @Override
    public void tick(ServerPlayer player, AbilitySlot slot) {
        MobEffectInstance current = player.getEffect(effect);
        if (current == null || current.getAmplifier() < amplifier
                || current.getAmplifier() == amplifier && current.getDuration() < REFRESH_BELOW) {
            player
                .addEffect(new MobEffectInstance(effect, DURATION, amplifier, false, false, true));
        }
    }
}
