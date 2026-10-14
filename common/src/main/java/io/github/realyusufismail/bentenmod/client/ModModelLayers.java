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
package io.github.realyusufismail.bentenmod.client;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

/** Model layers for the mod's vanilla-style entity models. Client-only. */
public final class ModModelLayers {
    public static final ModelLayerLocation KRAAB =
            new ModelLayerLocation(new ResourceLocation(BenTenMod.MOD_ID, "kraab"), "main");

    private ModModelLayers() {}
}
