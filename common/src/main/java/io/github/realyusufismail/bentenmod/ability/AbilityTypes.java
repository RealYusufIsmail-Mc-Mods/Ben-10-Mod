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

package io.github.realyusufismail.bentenmod.ability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.ability.type.AttributeAbility;
import io.github.realyusufismail.bentenmod.ability.type.EffectAbility;
import io.github.realyusufismail.bentenmod.ability.type.FlightAbility;
import io.github.realyusufismail.bentenmod.ability.type.NoFallDamageAbility;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

/** Every ability type the JSON can name. Add new types here. */
public final class AbilityTypes {
    private static final Map<ResourceLocation, AbilityType<?>> TYPES = new LinkedHashMap<>();

    public static final AbilityType<EffectAbility> EFFECT = register("effect", EffectAbility.CODEC);
    public static final AbilityType<FlightAbility> FLIGHT = register("flight", FlightAbility.CODEC);
    public static final AbilityType<AttributeAbility> ATTRIBUTE =
            register("attribute", AttributeAbility.CODEC);
    public static final AbilityType<NoFallDamageAbility> NO_FALL_DAMAGE =
            register("no_fall_damage", NoFallDamageAbility.CODEC);

    private static final Codec<AbilityType<?>> TYPE_CODEC =
            ResourceLocation.CODEC.comapFlatMap(id -> {
                AbilityType<?> type = TYPES.get(id);
                return type != null ? DataResult.success(type)
                        : DataResult.error(() -> "Unknown ability type " + id);
            }, AbilityType::id);

    /** Reads any ability from JSON by its {@code type} field. Declared after every type. */
    public static final Codec<Ability> ABILITY_CODEC =
            TYPE_CODEC.dispatch(Ability::type, AbilityType::codec);

    private AbilityTypes() {}

    private static <A extends Ability> AbilityType<A> register(String name, Codec<A> codec) {
        AbilityType<A> type = new AbilityType<>(BenTenMod.getModIdAndName(name), codec);
        TYPES.put(type.id(), type);
        return type;
    }
}
