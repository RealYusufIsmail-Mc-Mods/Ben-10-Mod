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
