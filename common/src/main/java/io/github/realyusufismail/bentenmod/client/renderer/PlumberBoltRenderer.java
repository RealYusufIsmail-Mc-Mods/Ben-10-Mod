package io.github.realyusufismail.bentenmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.PlumberBoltEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

/** Full-bright, camera-facing energy bolt. */
public final class PlumberBoltRenderer extends EntityRenderer<PlumberBoltEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/entity/plumber_bolt.png");

    public PlumberBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(PlumberBoltEntity entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        float length = 0.32F;
        float width = 0.075F;
        drawQuad(poseStack, consumer, -width, -length, width, length, 0, 0, 1, 1);
        poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(90));
        drawQuad(poseStack, consumer, -width, -length, width, length, 0, 0, 1, 1);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void drawQuad(PoseStack poseStack, VertexConsumer consumer, float minX,
            float minY, float maxX, float maxY, float minU, float minV, float maxU, float maxV) {
        var pose = poseStack.last().pose();
        Vec3 normal = new Vec3(0, 0, 1);
        vertex(consumer, pose, minX, minY, 0, minU, maxV, normal);
        vertex(consumer, pose, maxX, minY, 0, maxU, maxV, normal);
        vertex(consumer, pose, maxX, maxY, 0, maxU, minV, normal);
        vertex(consumer, pose, minX, maxY, 0, minU, minV, normal);
    }

    private static void vertex(VertexConsumer consumer, org.joml.Matrix4f pose, float x, float y,
            float z, float u, float v, Vec3 normal) {
        consumer.vertex(pose, x, y, z)
            .color(255, 255, 255, 255)
            .uv(u, v)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(LightTexture.FULL_BRIGHT)
            .normal((float) normal.x, (float) normal.y, (float) normal.z)
            .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(PlumberBoltEntity entity) {
        return TEXTURE;
    }
}
