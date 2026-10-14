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
package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

/** GeckoLib resources for the existing 3D watch asset. */
public final class OmnitrixWatchModel<T extends Item & GeoAnimatable> extends GeoModel<T> {
    private static final ResourceLocation MODEL =
            new ResourceLocation(BenTenMod.MOD_ID, "geo/item/omnitrix.geo.json");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/item/omnitrix_device.png");
    private static final ResourceLocation ANIMATION =
            new ResourceLocation(BenTenMod.MOD_ID, "animations/omnitrix.animation.json");

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATION;
    }
}
