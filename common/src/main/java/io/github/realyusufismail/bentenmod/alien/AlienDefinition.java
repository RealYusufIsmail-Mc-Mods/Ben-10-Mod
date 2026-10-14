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
