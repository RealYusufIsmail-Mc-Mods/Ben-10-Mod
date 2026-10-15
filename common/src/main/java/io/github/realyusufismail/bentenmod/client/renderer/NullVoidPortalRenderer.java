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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.NullVoidPortalEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/** Textured, layered Null Void vortex. */
public final class NullVoidPortalRenderer extends EntityRenderer<NullVoidPortalEntity> {
    private static final ResourceLocation SWIRL =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/entity/null_void_portal_swirl.png");
    private static final ResourceLocation RIM =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/entity/null_void_portal_rim.png");

    public NullVoidPortalRenderer(EntityRendererProvider.Context context) {
        super(context);
        shadowRadius = 0;
    }

    @Override
    public void render(NullVoidPortalEntity entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float age = entity.tickCount + partialTick;
        float scale = entity.visualScale(partialTick);
        poseStack.pushPose();
        poseStack.translate(0, 1.5, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        poseStack.scale(scale, scale, scale);
        renderLayer(poseStack, buffer, SWIRL, age * 7.0F, 1.0F, 0.52F, 0.16F, 150);
        renderLayer(poseStack, buffer, SWIRL, -age * 11.0F, 0.86F, 0.34F, 0.12F, 120);
        renderLayer(poseStack, buffer, SWIRL, age * 16.0F, 0.66F, 0.72F, 0.2F, 110);
        renderLayer(poseStack, buffer, RIM, 0, 1.0F, 1.0F, 0.12F, 245);
        poseStack.popPose();
    }

    private static void renderLayer(PoseStack poseStack, MultiBufferSource buffer,
            ResourceLocation texture, float rotation, float scale, float red, float green,
            int alpha) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.scale(scale, scale, 1.0F);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(texture));
        var pose = poseStack.last().pose();
        quad(consumer, pose, -1.0F, -1.5F, 1.0F, 1.5F, red, green, alpha);
        poseStack.popPose();
    }

    private static void quad(VertexConsumer consumer, org.joml.Matrix4f pose, float left,
            float bottom, float right, float top, float red, float green, int alpha) {
        vertex(consumer, pose, left, bottom, 0, 0, 1, red, green, alpha);
        vertex(consumer, pose, right, bottom, 0, 1, 1, red, green, alpha);
        vertex(consumer, pose, right, top, 0, 1, 0, red, green, alpha);
        vertex(consumer, pose, left, top, 0, 0, 0, red, green, alpha);
    }

    private static void vertex(VertexConsumer consumer, org.joml.Matrix4f pose, float x, float y,
            float z, float u, float v, float red, float green, int alpha) {
        consumer.vertex(pose, x, y, z)
            .color((int) (red * 255), (int) (green * 255), 255, alpha)
            .uv(u, v)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(LightTexture.FULL_BRIGHT)
            .normal(0, 0, 1)
            .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(NullVoidPortalEntity entity) {
        return SWIRL;
    }
}
