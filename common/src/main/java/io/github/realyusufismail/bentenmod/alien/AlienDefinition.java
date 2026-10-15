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
package io.github.realyusufismail.bentenmod.alien;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.realyusufismail.bentenmod.ability.Ability;
import io.github.realyusufismail.bentenmod.ability.AbilityTypes;
import io.github.realyusufismail.bentenmod.alien.power.AlienPower;
import io.github.realyusufismail.bentenmod.alien.power.PowerTypes;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

/**
 * One Omnitrix alien, loaded from {@code data/<namespace>/aliens/<id>.json}. Its model, texture and
 * animations are found by id (see {@code AlienGeoModel}), so adding an alien needs this file plus
 * the generated assets, no Java.
 *
 * @param width hitbox width in blocks
 * @param height hitbox height in blocks
 * @param abilities passive abilities while transformed
 * @param unlockedByDefault whether a new Omnitrix starts with this alien
 * @param durationTicks how long a transformation lasts before the Omnitrix times out
 * @param dnaSources entity types and blocks that can be scanned to unlock this alien
 * @param rechargeTicks cooldown after a timed-out transformation
 * @param power optional data-driven active power
 */
public record AlienDefinition(float width, float height, List<Ability> abilities,
        boolean unlockedByDefault, int durationTicks, DnaSources dnaSources, int rechargeTicks,
        Optional<AlienPower> power) {

    public record DnaSources(List<ResourceLocation> entities, List<ResourceLocation> blocks) {
        public static final DnaSources EMPTY = new DnaSources(List.of(), List.of());
        public static final Codec<DnaSources> CODEC = RecordCodecBuilder.create(
                i -> i
                    .group(ResourceLocation.CODEC.listOf()
                        .optionalFieldOf("entities", List.of())
                        .forGetter(DnaSources::entities),
                            ResourceLocation.CODEC.listOf()
                                .optionalFieldOf("blocks", List.of())
                                .forGetter(DnaSources::blocks))
                    .apply(i, DnaSources::new));
    }

    public static final Codec<AlienDefinition> CODEC = RecordCodecBuilder.create(i -> i
        .group(Codec.floatRange(0.1F, 8.0F).fieldOf("width").forGetter(AlienDefinition::width),
                Codec.floatRange(0.1F, 8.0F).fieldOf("height").forGetter(AlienDefinition::height),
                AbilityTypes.ABILITY_CODEC.listOf()
                    .optionalFieldOf("abilities", List.of())
                    .forGetter(AlienDefinition::abilities),
                Codec.BOOL.optionalFieldOf("unlocked_by_default", false)
                    .forGetter(AlienDefinition::unlockedByDefault),
                Codec.intRange(20, 72000)
                    .optionalFieldOf("duration_ticks", 6000)
                    .forGetter(AlienDefinition::durationTicks),
                DnaSources.CODEC.optionalFieldOf("dna_sources", DnaSources.EMPTY)
                    .forGetter(AlienDefinition::dnaSources),
                Codec.intRange(0, 72000)
                    .optionalFieldOf("recharge_ticks", 1200)
                    .forGetter(AlienDefinition::rechargeTicks),
                PowerTypes.POWER_CODEC.optionalFieldOf("power").forGetter(AlienDefinition::power))
        .apply(i, AlienDefinition::new));
}
