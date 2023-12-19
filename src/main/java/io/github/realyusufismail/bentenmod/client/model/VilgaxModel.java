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
package io.github.realyusufismail.bentenmod.client.model;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class VilgaxModel extends AnimatedGeoModel<VilgaxEntity> {

    public VilgaxModel() {}

    @Override
    public ResourceLocation getModelLocation(VilgaxEntity object) {
        return new ResourceLocation(BenTenMod.MOD_ID, "geo/vilgax.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(VilgaxEntity object) {
        return new ResourceLocation(BenTenMod.MOD_ID, "textures/model/entities/vilgax/vilgax_entity.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VilgaxEntity object) {
        return new ResourceLocation(BenTenMod.MOD_ID, "animations/vilgax.animation.json");
    }
}
