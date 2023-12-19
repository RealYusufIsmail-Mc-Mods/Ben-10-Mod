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
package com.yusuf.bentenmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.client.model.KraabModel;
import com.yusuf.bentenmod.entity.KraabEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class KraabRenderer extends MobRenderer<KraabEntity, KraabModel> {

    private static final ResourceLocation TEXTURES =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/model/entities/kraab.png");

    public KraabRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new KraabModel(), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(KraabEntity entity) {
        return TEXTURES;
    }

    @Override
    protected void setupRotations(
            KraabEntity p_225621_1_,
            MatrixStack p_225621_2_,
            float p_225621_3_,
            float p_225621_4_,
            float p_225621_5_) {}
}
