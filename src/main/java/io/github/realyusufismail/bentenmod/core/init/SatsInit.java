/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.bentenmod.core.init;

import static net.minecraft.stats.Stats.CUSTOM;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SatsInit {
    public static final ResourceLocation INTERACT_WITH_ALIEN_TABLE =
            makeCustomStat("interact_with_alien_table", IStatFormatter.DEFAULT);

    public static ResourceLocation makeCustomStat(String key, IStatFormatter formatter) {
        ResourceLocation resourceLocation = new ResourceLocation(key);
        Registry.register(Registry.CUSTOM_STAT, key, resourceLocation);
        CUSTOM.get(resourceLocation, formatter);
        return resourceLocation;
    }
}
