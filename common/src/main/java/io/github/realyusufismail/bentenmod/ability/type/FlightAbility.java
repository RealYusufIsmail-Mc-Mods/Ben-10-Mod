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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

/**
 * Creative-style flight while active, taken away again when the source ends (unless the player is
 * in creative or spectator). JSON: {@code {"type": "bentenmod:flight", "speed": 0.1}}; vanilla
 * creative flight speed is 0.05.
 */
public record FlightAbility(float speed) implements Ability {
    private static final float DEFAULT_SPEED = 0.05F;

    public static final Codec<FlightAbility> CODEC = RecordCodecBuilder.create(
            i -> i
                .group(Codec.floatRange(0.01F, 1.0F)
                    .optionalFieldOf("speed", DEFAULT_SPEED)
                    .forGetter(FlightAbility::speed))
                .apply(i, FlightAbility::new));

    @Override
    public AbilityType<?> type() {
        return AbilityTypes.FLIGHT;
    }

    @Override
    public void tick(ServerPlayer player, AbilitySlot slot) {
        // Re-asserted every tick so another flight source ending doesn't ground this one.
        var abilities = player.getAbilities();
        if (!abilities.mayfly || abilities.getFlyingSpeed() != speed) {
            abilities.mayfly = true;
            abilities.setFlyingSpeed(speed);
            player.onUpdateAbilities();
        }
    }

    @Override
    public void stop(ServerPlayer player, AbilitySlot slot) {
        var abilities = player.getAbilities();
        abilities.setFlyingSpeed(DEFAULT_SPEED);
        GameType mode = player.gameMode.getGameModeForPlayer();
        if (!mode.isCreative() && mode != GameType.SPECTATOR) {
            abilities.mayfly = false;
            abilities.flying = false;
        }
        player.onUpdateAbilities();
    }
}
