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

import net.minecraft.server.level.ServerPlayer;

/**
 * A passive power granted while a source is active: a worn armour set now, an alien form later.
 * Implementations are immutable records described in JSON as {@code {"type": "bentenmod:...",
 * ...}}.
 *
 * <p>
 * {@link AbilityTicker} calls {@link #start} when the source becomes active, {@link #tick} every
 * server tick while it stays active, and {@link #stop} when it ends. Parse JSON with
 * {@link AbilityTypes#ABILITY_CODEC}.
 */
public interface Ability {
    AbilityType<?> type();

    default void start(ServerPlayer player, AbilitySlot slot) {}

    default void tick(ServerPlayer player, AbilitySlot slot) {}

    default void stop(ServerPlayer player, AbilitySlot slot) {}
}
