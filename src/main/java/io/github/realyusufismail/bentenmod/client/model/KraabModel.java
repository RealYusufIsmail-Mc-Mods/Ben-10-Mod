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

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.realyusufismail.bentenmod.entity.KraabEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class KraabModel extends EntityModel<KraabEntity> {

    private static final float PI = (float) Math.PI;

    public ModelRenderer body1;
    public ModelRenderer upperLeg1;
    public ModelRenderer body2;
    public ModelRenderer upperLeg2;
    public ModelRenderer upperLeg4;
    public ModelRenderer upperLeg3;
    public ModelRenderer lowerLeg1;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer rightArm1;
    public ModelRenderer leftArm1;
    public ModelRenderer neck;
    public ModelRenderer head1;
    public ModelRenderer mouth;
    public ModelRenderer head2;
    public ModelRenderer head3;
    public ModelRenderer rightArm2;
    public ModelRenderer rightFinger1;
    public ModelRenderer rightFinger2;
    public ModelRenderer rightFinger3;
    public ModelRenderer leftArm2;
    public ModelRenderer claw1;
    public ModelRenderer claw2;
    public ModelRenderer claw11;
    public ModelRenderer claw12;
    public ModelRenderer claw21;
    public ModelRenderer claw22;
    public ModelRenderer lowerLeg2;
    public ModelRenderer lowerLeg4;
    public ModelRenderer lowerLeg3;

    public KraabModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.claw12 = new ModelRenderer(this, 30, 11);
        this.claw12.setPos(0.0F, 0.0F, -3.8F);
        this.claw12.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw12, 0.0F, 0.7853981633974483F, 0.0F);
        this.mouth = new ModelRenderer(this, 0, 0);
        this.mouth.setPos(0.0F, 0.5F, -2.0F);
        this.mouth.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.claw1 = new ModelRenderer(this, 0, 51);
        this.claw1.setPos(1.0F, 0.0F, -8.0F);
        this.claw1.addBox(0.0F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, 0.02f);
        this.setRotateAngle(claw1, 0.0F, -0.3864158857389002F, 0.0F);
        this.leftArm1 = new ModelRenderer(this, 0, 36);
        this.leftArm1.setPos(4.0F, 1.0F, 0.0F);
        this.leftArm1.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm1, 0.0F, 0.0F, -1.0164797856562695F);
        this.rightFinger1 = new ModelRenderer(this, 24, 0);
        this.rightFinger1.setPos(-1.0F, 7.5F, 0.0F);
        this.rightFinger1.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFinger1, -0.12880530079455235F, 0.0F, 0.5152212031782094F);
        this.claw22 = new ModelRenderer(this, 30, 11);
        this.claw22.setPos(0.0F, 0.0F, -3.8F);
        this.claw22.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw22, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightArm1 = new ModelRenderer(this, 0, 36);
        this.rightArm1.setPos(-4.0F, 1.0F, 0.0F);
        this.rightArm1.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.02f);
        this.setRotateAngle(rightArm1, 0.0F, 0.0F, 1.0164797856562695F);
        this.upperLeg3 = new ModelRenderer(this, 32, 0);
        this.upperLeg3.setPos(-3.0F, 1.5F, 3.0F);
        this.upperLeg3.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg3, -1.0471975511965976F, 2.3739969231311973F, 0.0F);
        this.neck = new ModelRenderer(this, 0, 27);
        this.neck.setPos(0.0F, -2.0F, 1.0F);
        this.neck.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.2617993877991494F, 0.0F, 0.0F);
        this.leftArm2 = new ModelRenderer(this, 4, 39);
        this.leftArm2.setPos(0.0F, 7.0F, 1.0F);
        this.leftArm2.addBox(-3.0F, -2.0F, -8.0F, 6.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.claw21 = new ModelRenderer(this, 30, 11);
        this.claw21.setPos(0.0F, 0.0F, -6.6F);
        this.claw21.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw21, 0.0F, 0.7853981633974483F, 0.0F);
        this.body4 = new ModelRenderer(this, 28, 19);
        this.body4.setPos(0.0F, -1.0F, -2.0F);
        this.body4.addBox(-3.5F, 0.0F, -4.0F, 7.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body4, 0.27366763203903305F, 0.0F, 0.0F);
        this.lowerLeg1 = new ModelRenderer(this, 44, 0);
        this.lowerLeg1.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg1.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg1, 0.7853981633974483F, 0.0F, 0.0F);
        this.head3 = new ModelRenderer(this, 34, 49);
        this.head3.setPos(0.0F, -2.0F, 0.0F);
        this.head3.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.upperLeg1 = new ModelRenderer(this, 32, 0);
        this.upperLeg1.setPos(3.0F, 1.5F, -3.0F);
        this.upperLeg1.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg1, -1.0471975511965976F, -0.7853981633974483F, 0.0F);
        this.claw11 = new ModelRenderer(this, 30, 11);
        this.claw11.setPos(0.0F, 0.0F, -6.6F);
        this.claw11.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw11, 0.0F, 0.7853981633974483F, 0.0F);
        this.lowerLeg4 = new ModelRenderer(this, 44, 0);
        this.lowerLeg4.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg4.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg4, 0.7853981633974483F, 0.0F, 0.0F);
        this.upperLeg2 = new ModelRenderer(this, 32, 0);
        this.upperLeg2.setPos(3.0F, 1.5F, 3.0F);
        this.upperLeg2.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg2, -1.0471975511965976F, -2.4169318735932372F, 0.0F);
        this.head2 = new ModelRenderer(this, 14, 51);
        this.head2.setPos(0.0F, -1.0F, 0.0F);
        this.head2.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.lowerLeg3 = new ModelRenderer(this, 44, 0);
        this.lowerLeg3.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg3.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg3, 0.7853981633974483F, 0.0F, 0.0F);
        this.upperLeg4 = new ModelRenderer(this, 32, 0);
        this.upperLeg4.setPos(-3.0F, 1.5F, -3.0F);
        this.upperLeg4.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg4, -1.0471975511965976F, 0.7853981633974483F, 0.0F);
        this.rightArm2 = new ModelRenderer(this, 46, 30);
        this.rightArm2.setPos(0.0F, 7.0F, 0.0F);
        this.rightArm2.addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm2, 0.0F, 0.0F, -0.6579891413176677F);
        this.body3 = new ModelRenderer(this, 0, 12);
        this.body3.setPos(0.0F, -6.0F, 0.0F);
        this.body3.addBox(-4.5F, 0.0F, -4.5F, 9.0F, 6.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.claw2 = new ModelRenderer(this, 0, 51);
        this.claw2.setPos(-1.0F, 0.0F, -8.0F);
        this.claw2.addBox(-3.0F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, 0.02f);
        this.setRotateAngle(claw2, 0.0F, 0.42935100264850773F, 0.0F);
        this.body1 = new ModelRenderer(this, 0, 0);
        this.body1.setPos(0.0F, 11.0F, 0.0F);
        this.body1.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.rightFinger3 = new ModelRenderer(this, 24, 0);
        this.rightFinger3.setPos(1.0F, 7.5F, -1.0F);
        this.rightFinger3.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFinger3, -0.3864158857389002F, 0.0F, -1.1163125735966066F);
        this.rightFinger2 = new ModelRenderer(this, 24, 0);
        this.rightFinger2.setPos(1.0F, 7.5F, 1.0F);
        this.rightFinger2.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFinger2, 0.5581562867983033F, 0.0F, -0.6869616042376124F);
        this.lowerLeg2 = new ModelRenderer(this, 44, 0);
        this.lowerLeg2.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg2.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg2, 0.7853981633974483F, 0.0F, 0.0F);
        this.head1 = new ModelRenderer(this, 10, 29);
        this.head1.setPos(0.0F, -2.0F, 0.0F);
        this.head1.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.body2 = new ModelRenderer(this, 38, 10);
        this.body2.setPos(0.0F, -3.0F, 0.0F);
        this.body2.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.claw1.addChild(this.claw12);
        this.head1.addChild(this.mouth);
        this.leftArm2.addChild(this.claw1);
        this.body3.addChild(this.leftArm1);
        this.rightArm2.addChild(this.rightFinger1);
        this.claw2.addChild(this.claw22);
        this.body3.addChild(this.rightArm1);
        this.body1.addChild(this.upperLeg3);
        this.body4.addChild(this.neck);
        this.leftArm1.addChild(this.leftArm2);
        this.claw2.addChild(this.claw21);
        this.body3.addChild(this.body4);
        this.upperLeg1.addChild(this.lowerLeg1);
        this.head2.addChild(this.head3);
        this.body1.addChild(this.upperLeg1);
        this.claw1.addChild(this.claw11);
        this.upperLeg4.addChild(this.lowerLeg4);
        this.body1.addChild(this.upperLeg2);
        this.head1.addChild(this.head2);
        this.upperLeg3.addChild(this.lowerLeg3);
        this.body1.addChild(this.upperLeg4);
        this.rightArm1.addChild(this.rightArm2);
        this.body2.addChild(this.body3);
        this.leftArm2.addChild(this.claw2);
        this.rightArm2.addChild(this.rightFinger3);
        this.rightArm2.addChild(this.rightFinger2);
        this.upperLeg2.addChild(this.lowerLeg2);
        this.neck.addChild(this.head1);
        this.body1.addChild(this.body2);
    }

    @Override
    public void renderToBuffer(
            MatrixStack matrixStackIn,
            IVertexBuilder bufferIn,
            int packedLightIn,
            int packedOverlayIn,
            float red,
            float green,
            float blue,
            float alpha) {
        ImmutableList.of(this.body1).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(
            KraabEntity entityIn,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        // Head
        head1.yRot = netHeadYaw * (PI / 180f);

        // Legs
        upperLeg1.xRot = -PI / 180 * 60 - Math.abs(MathHelper.cos(limbSwing * 0.5f) * 1f * limbSwingAmount);
        upperLeg2.xRot = -PI / 180 * 60 - Math.abs(MathHelper.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount);
        upperLeg3.xRot = -PI / 180 * 60 - Math.abs(MathHelper.cos(limbSwing * 0.5f) * 1f * limbSwingAmount);
        upperLeg4.xRot = -PI / 180 * 60 - Math.abs(MathHelper.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount);
        upperLeg1.yRot = -PI / 180 * 45 + MathHelper.cos(limbSwing * 0.5f) * 1f * limbSwingAmount;
        upperLeg2.yRot = -PI / 180 * 135 + MathHelper.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount;
        upperLeg3.yRot = PI / 180 * 135 + MathHelper.cos(limbSwing * 0.5f) * 1f * limbSwingAmount;
        upperLeg4.yRot = PI / 180 * 45 + MathHelper.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount;

        // Right Arm
        rightArm1.zRot = PI / 180 * 60 - MathHelper.cos(ageInTicks * 0.1f) * 0.05f;
        rightArm2.zRot = -PI / 180 * 40 - MathHelper.cos(ageInTicks * 0.1f) * 0.05f;

        // Attack
        if (!entityIn.isShooting()) {
            claw1.yRot = -PI / 180 * 10 - MathHelper.sin(attackTime * PI);
            claw2.yRot = PI / 180 * 10 + MathHelper.sin(attackTime * PI);
        }
    }

    @Override
    public void prepareMobModel(KraabEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        float bodyRot = 180 - MathHelper.rotLerp(partialTick, entityIn.yBodyRotO, entityIn.yBodyRot);
        body2.yRot = (PI / 180f) * bodyRot;

        // Shoot
        if (entityIn.isShooting()) {
            float shootTime = entityIn.getShootAnim(partialTick);
            claw1.yRot = -PI / 180 * 10 - shootTime * 50 * PI / 180;
            claw2.yRot = PI / 180 * 10 + shootTime * 50 * PI / 180;
        }
    }
}
