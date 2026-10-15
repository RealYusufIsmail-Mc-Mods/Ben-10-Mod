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
package io.github.realyusufismail.bentenmod.alien.power;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

/** Every active power type that alien JSON may name. */
public final class PowerTypes {
    private static final Map<ResourceLocation, PowerType> TYPES = new LinkedHashMap<>();

    public static final PowerType FIREBALL = register("fireball", 5);
    public static final PowerType GROUND_SLAM = register("ground_slam", 40);
    public static final PowerType DASH = register("dash", 10);
    public static final PowerType CRYSTAL_SHARD_VOLLEY = register("crystal_shard_volley", 8);
    public static final PowerType SLIME_SPIT = register("slime_spit", 8);
    public static final PowerType POUNCE = register("pounce", 20);
    public static final PowerType LUNGE_BITE = register("lunge_bite", 15);
    public static final PowerType EMP_PULSE = register("emp_pulse", 100);
    public static final PowerType PHASE = register("phase", 100);
    public static final PowerType ANALYSE = register("analyse", 100);

    private static final Codec<PowerType> TYPE_CODEC = ResourceLocation.CODEC.comapFlatMap(id -> {
        PowerType type = TYPES.get(id);
        return type == null ? DataResult.error(() -> "Unknown power type " + id)
                : DataResult.success(type);
    }, PowerType::id);

    public static final Codec<AlienPower> POWER_CODEC =
            TYPE_CODEC.dispatch(power -> TYPES.get(power.type()), PowerType::codec);

    private PowerTypes() {}

    private static PowerType register(String name, int defaultCooldownTicks) {
        ResourceLocation id = BenTenMod.getModIdAndName(name);
        Codec<AlienPower> codec = RecordCodecBuilder.create(i -> i
            .group(Codec.intRange(1, 72000)
                .optionalFieldOf("cooldown_ticks", defaultCooldownTicks)
                .forGetter(AlienPower::cooldownTicks))
            .apply(i, ticks -> new AlienPower(id, ticks)));
        PowerType type = new PowerType(id, codec);
        TYPES.put(id, type);
        return type;
    }
}
