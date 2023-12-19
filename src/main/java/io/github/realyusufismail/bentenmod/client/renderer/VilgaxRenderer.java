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
package io.github.realyusufismail.bentenmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.realyusufismail.bentenmod.client.model.VilgaxModel;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VilgaxRenderer extends GeoEntityRenderer<VilgaxEntity> {

    public VilgaxRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VilgaxModel());
        this.shadowRadius = 0.7F;
    }

    public RenderType getRenderType(
            VilgaxEntity animatable,
            float partialTicks,
            MatrixStack stack,
            IRenderTypeBuffer renderTypeBuffer,
            IVertexBuilder vertexBuilder,
            int packedLightIn,
            ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
