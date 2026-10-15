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

import net.minecraft.resources.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Where an ability came from: its source (e.g. {@code bentenmod:armor_set/heatblast}) and its
 * position in that source's list. Gives each ability a stable id for attribute modifiers.
 */
public record AbilitySlot(ResourceLocation source, int index) {

    public UUID uuid() {
        return UUID.nameUUIDFromBytes((source + "#" + index).getBytes(StandardCharsets.UTF_8));
    }
}
