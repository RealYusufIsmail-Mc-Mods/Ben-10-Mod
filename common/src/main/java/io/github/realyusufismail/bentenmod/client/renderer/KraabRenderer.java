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
package io.github.realyusufismail.bentenmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.client.ModModelLayers;
import io.github.realyusufismail.bentenmod.client.model.KraabModel;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KraabRenderer extends MobRenderer<CrabEntity, KraabModel> {

    private static final ResourceLocation TEXTURES =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/model/entities/kraab.png");

    public KraabRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new KraabModel(renderManagerIn.bakeLayer(ModModelLayers.KRAAB)),
                0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity entity) {
        return TEXTURES;
    }

    @Override
    protected void setupRotations(CrabEntity p_225621_1_, PoseStack p_225621_2_, float p_225621_3_,
            float p_225621_4_, float p_225621_5_) {}
}
