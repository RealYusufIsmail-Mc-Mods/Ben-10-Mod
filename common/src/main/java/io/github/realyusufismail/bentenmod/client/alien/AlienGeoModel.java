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
