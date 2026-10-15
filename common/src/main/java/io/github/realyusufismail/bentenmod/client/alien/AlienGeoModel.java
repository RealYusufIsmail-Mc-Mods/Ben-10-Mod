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
package io.github.realyusufismail.bentenmod.client.alien;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import java.util.function.Function;

/**
 * GeckoLib model for every alien. Resources follow the layout written by {@code tools/alien-gen}:
 *
 * <ul>
 * <li>{@code geo/alien/<id>.geo.json}</li>
 * <li>{@code textures/entity/alien/<id>.png} (+ {@code <id>_glowmask.png})</li>
 * <li>{@code animations/<id>.animation.json} with {@code idle}, {@code walk}, {@code transform} and
 * {@code attack}</li>
 * </ul>
 *
 * @param <T> whatever is being rendered as the alien; tells the model which alien it is
 */
public class AlienGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
    private final Function<T, ResourceLocation> alienId;

    public AlienGeoModel(Function<T, ResourceLocation> alienId) {
        this.alienId = alienId;
    }

    public static ResourceLocation modelOf(ResourceLocation alien) {
        return alien.withPath(path -> "geo/alien/" + path + ".geo.json");
    }

    public static ResourceLocation textureOf(ResourceLocation alien) {
        return alien.withPath(path -> "textures/entity/alien/" + path + ".png");
    }

    public static ResourceLocation animationsOf(ResourceLocation alien) {
        return alien.withPath(path -> "animations/" + path + ".animation.json");
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return modelOf(alienId.apply(animatable));
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return textureOf(alienId.apply(animatable));
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animationsOf(alienId.apply(animatable));
    }
}
