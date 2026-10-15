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
