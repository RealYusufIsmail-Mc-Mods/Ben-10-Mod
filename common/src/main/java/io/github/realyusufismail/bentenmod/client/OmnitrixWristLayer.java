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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * A wrist-mounted Omnitrix on the player's left forearm: a snug band plus a raised dial on the
 * outer side. Drawn for equipped, untransformed players only.
 *
 * <p>
 * The model is code-defined and baked once, so no model layer needs registering in a loader. Cube
 * sizes and UVs match the atlas written by {@code tools/ui-assets/generate.py}
 * ({@code textures/entity/omnitrix_wrist.png}, 32x32): band_wide at (0,0) 4x3x4, band_slim at (0,8)
 * 3x3x4, dial at (0,16) 1x3x3 with the hourglass on its +X face. The hourglass is drawn a second
 * time, emissive and gently pulsing, from {@code omnitrix_wrist_glow.png}.
 *
 * <p>
 * All coordinates are in the left arm's local space, so they follow the arm's swing: the arm cube
 * spans y -2..10 (shoulder pivot at 0), x -1..3 (wide) or -1..2 (slim), z -2..2.
 */
public final class OmnitrixWristLayer
        extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/entity/omnitrix_wrist.png");
    private static final ResourceLocation GLOW =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/entity/omnitrix_wrist_glow.png");

    private static final int FULL_BRIGHT = 15728880;

    /**
     * Band: 3 px tall near the wrist end (arm ends at y=10), inflated so it clears the sleeve
     * (0.25).
     */
    private static final float BAND_Y = 6.8F;
    private static final float BAND_HEIGHT = 3.0F;
    private static final float BAND_INFLATE = 0.4F;
    /** Dial: 3x3 face, 1 px deep, standing 0.8 px proud of the band on the outer (+X) side. */
    private static final float DIAL_PROUD = 0.8F;

    private final ModelPart wide = bake(false);
    private final ModelPart slim = bake(true);

    public OmnitrixWristLayer(
            RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    private static ModelPart bake(boolean slimArm) {
        MeshDefinition mesh = new MeshDefinition();
        float armWidth = slimArm ? 3.0F : 4.0F;
        float outerFace = -1.0F + armWidth + BAND_INFLATE;

        mesh.getRoot()
            .addOrReplaceChild("band",
                    CubeListBuilder.create()
                        .texOffs(0, slimArm ? 8 : 0)
                        .addBox(-1.0F, BAND_Y, -2.0F, armWidth, BAND_HEIGHT, 4.0F,
                                new CubeDeformation(BAND_INFLATE)),
                    PartPose.ZERO);
        mesh.getRoot()
            .addOrReplaceChild("dial",
                    CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(outerFace + DIAL_PROUD - 1.0F, BAND_Y, -1.5F, 1.0F, 3.0F, 3.0F),
                    PartPose.ZERO);
        return LayerDefinition.create(mesh, 32, 32).bakeRoot();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
            AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        OmnitrixSnapshot state = ClientOmnitrixState.get(player.getUUID());
        if (state == null || !state.equipped() || state.currentAlien() != null
                || player.isInvisible()) {
            return;
        }

        ModelPart model = "slim".equals(player.getModelName()) ? slim : wide;
        int overlay = LivingEntityRenderer.getOverlayCoords(player, 0.0F);

        poseStack.pushPose();
        getParentModel().leftArm.translateAndRotate(poseStack);

        VertexConsumer body = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.render(poseStack, body, packedLight, overlay);

        // 0.75..1.0 pulse; the eyes render type is additive so scaling the colour dims the glow.
        float pulse = 0.875F + 0.125F * Mth.sin(ageInTicks * 0.12F);
        VertexConsumer glow = buffer.getBuffer(RenderType.eyes(GLOW));
        model.getChild("dial")
            .render(poseStack, glow, FULL_BRIGHT, overlay, pulse, pulse, pulse, 1.0F);
        poseStack.popPose();
    }
}
