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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Keeps a potion effect on the player. JSON: {@code {"type": "bentenmod:effect", "effect":
 * "minecraft:speed", "amplifier": 2}}; amplifier 0 is level I.
 */
public record EffectAbility(MobEffect effect, int amplifier) implements Ability {

    /** Long enough that night vision never starts flashing (it does below 200 ticks). */
    private static final int DURATION = 300;
    private static final int REFRESH_BELOW = 220;
    private static final Map<EffectKey, Set<AbilitySlot>> OWNERS = new HashMap<>();
    private static final Map<EffectKey, OriginalEffect> ORIGINALS = new HashMap<>();

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
        EffectKey key = new EffectKey(player.getUUID(), effect, amplifier);
        MobEffectInstance current = player.getEffect(effect);
        Set<AbilitySlot> owners = OWNERS.get(key);
        if (current == null || current.getAmplifier() < amplifier
                || current.getAmplifier() == amplifier && current.getDuration() < REFRESH_BELOW) {
            if (owners == null) {
                OWNERS.put(key, owners = new HashSet<>());
                long now = player.serverLevel().getServer().overworld().getGameTime();
                ORIGINALS.put(key, new OriginalEffect(
                        current == null ? null : new MobEffectInstance(current), now));
            }
            owners.add(slot);
            // Ambient is a private marker: stop only removes the instance granted by this ability.
            player.addEffect(new MobEffectInstance(effect, DURATION, amplifier, true, false, true));
        } else if (owners != null && current.isAmbient() && current.getAmplifier() == amplifier) {
            // Multiple active sources can share one effect without removing it prematurely.
            owners.add(slot);
        }
    }

    @Override
    public void stop(ServerPlayer player, AbilitySlot slot) {
        EffectKey key = new EffectKey(player.getUUID(), effect, amplifier);
        Set<AbilitySlot> owners = OWNERS.get(key);
        if (owners == null || !owners.remove(slot) || !owners.isEmpty()) {
            return;
        }
        OWNERS.remove(key);
        OriginalEffect original = ORIGINALS.remove(key);
        MobEffectInstance current = player.getEffect(effect);
        if (current != null && current.isAmbient() && current.getAmplifier() == amplifier) {
            player.removeEffect(effect);
            if (original != null && original.instance != null) {
                long now = player.serverLevel().getServer().overworld().getGameTime();
                int remaining = original.instance.getDuration()
                        - (int) Math.max(0, now - original.appliedAt);
                if (remaining > 0) {
                    MobEffectInstance saved = original.instance;
                    player.addEffect(new MobEffectInstance(effect, remaining, saved.getAmplifier(),
                            saved.isAmbient(), saved.isVisible(), saved.showIcon()));
                }
            }
        }
    }

    private record EffectKey(UUID player, MobEffect effect, int amplifier) {
    }

    private record OriginalEffect(MobEffectInstance instance, long appliedAt) {
    }
}
