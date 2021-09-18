/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.yusuf.bentenmod.entity.KraabEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class KraabModel extends EntityModel<KraabEntity> {

    private static final float PI = (float) Math.PI;

    public ModelPart body1;
    public ModelPart upperLeg1;
    public ModelPart body2;
    public ModelPart upperLeg2;
    public ModelPart upperLeg4;
    public ModelPart upperLeg3;
    public ModelPart lowerLeg1;
    public ModelPart body3;
    public ModelPart body4;
    public ModelPart rightArm1;
    public ModelPart leftArm1;
    public ModelPart neck;
    public ModelPart head1;
    public ModelPart mouth;
    public ModelPart head2;
    public ModelPart head3;
    public ModelPart rightArm2;
    public ModelPart rightFinger1;
    public ModelPart rightFinger2;
    public ModelPart rightFinger3;
    public ModelPart leftArm2;
    public ModelPart claw1;
    public ModelPart claw2;
    public ModelPart claw11;
    public ModelPart claw12;
    public ModelPart claw21;
    public ModelPart claw22;
    public ModelPart lowerLeg2;
    public ModelPart lowerLeg4;
    public ModelPart lowerLeg3;

    public KraabModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.claw12 = new ModelPart(this, 30, 11);
        this.claw12.setPos(0.0F, 0.0F, -3.8F);
        this.claw12.visit(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw12, 0.0F, 0.7853981633974483F, 0.0F);
        this.mouth = new ModelPart(this, 0, 0);
        this.mouth.setPos(0.0F, 0.5F, -2.0F);
        this.mouth.visit(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.claw1 = new ModelPart(this, 0, 51);
        this.claw1.setPos(1.0F, 0.0F, -8.0F);
        this.claw1.visit(0.0F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, 0.02f);
        this.setRotateAngle(claw1, 0.0F, -0.3864158857389002F, 0.0F);
        this.leftArm1 = new ModelPart(this, 0, 36);
        this.leftArm1.setPos(4.0F, 1.0F, 0.0F);
        this.leftArm1.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm1, 0.0F, 0.0F, -1.0164797856562695F);
        this.rightFinger1 = new ModelPart(this, 24, 0);
        this.rightFinger1.setPos(-1.0F, 7.5F, 0.0F);
        this.rightFinger1.visit(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFinger1, -0.12880530079455235F, 0.0F, 0.5152212031782094F);
        this.claw22 = new ModelPart(this, 30, 11);
        this.claw22.setPos(0.0F, 0.0F, -3.8F);
        this.claw22.visit(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw22, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightArm1 = new ModelPart(this, 0, 36);
        this.rightArm1.setPos(-4.0F, 1.0F, 0.0F);
        this.rightArm1.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.02f);
        this.setRotateAngle(rightArm1, 0.0F, 0.0F, 1.0164797856562695F);
        this.upperLeg3 = new ModelPart(this, 32, 0);
        this.upperLeg3.setPos(-3.0F, 1.5F, 3.0F);
        this.upperLeg3.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg3, -1.0471975511965976F, 2.3739969231311973F, 0.0F);
        this.neck = new ModelPart(this, 0, 27);
        this.neck.setPos(0.0F, -2.0F, 1.0F);
        this.neck.visit(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, -0.2617993877991494F, 0.0F, 0.0F);
        this.leftArm2 = new ModelPart(this, 4, 39);
        this.leftArm2.setPos(0.0F, 7.0F, 1.0F);
        this.leftArm2.visit(-3.0F, -2.0F, -8.0F, 6.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.claw21 = new ModelPart(this, 30, 11);
        this.claw21.setPos(0.0F, 0.0F, -6.6F);
        this.claw21.visit(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw21, 0.0F, 0.7853981633974483F, 0.0F);
        this.body4 = new ModelPart(this, 28, 19);
        this.body4.setPos(0.0F, -1.0F, -2.0F);
        this.body4.visit(-3.5F, 0.0F, -4.0F, 7.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body4, 0.27366763203903305F, 0.0F, 0.0F);
        this.lowerLeg1 = new ModelPart(this, 44, 0);
        this.lowerLeg1.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg1.visit(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg1, 0.7853981633974483F, 0.0F, 0.0F);
        this.head3 = new ModelPart(this, 34, 49);
        this.head3.setPos(0.0F, -2.0F, 0.0F);
        this.head3.visit(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.upperLeg1 = new ModelPart(this, 32, 0);
        this.upperLeg1.setPos(3.0F, 1.5F, -3.0F);
        this.upperLeg1.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg1, -1.0471975511965976F, -0.7853981633974483F, 0.0F);
        this.claw11 = new ModelPart(this, 30, 11);
        this.claw11.setPos(0.0F, 0.0F, -6.6F);
        this.claw11.visit(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw11, 0.0F, 0.7853981633974483F, 0.0F);
        this.lowerLeg4 = new ModelPart(this, 44, 0);
        this.lowerLeg4.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg4.visit(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg4, 0.7853981633974483F, 0.0F, 0.0F);
        this.upperLeg2 = new ModelPart(this, 32, 0);
        this.upperLeg2.setPos(3.0F, 1.5F, 3.0F);
        this.upperLeg2.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg2, -1.0471975511965976F, -2.4169318735932372F, 0.0F);
        this.head2 = new ModelPart(this, 14, 51);
        this.head2.setPos(0.0F, -1.0F, 0.0F);
        this.head2.visit(-2.5F, 0.0F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.lowerLeg3 = new ModelPart(this, 44, 0);
        this.lowerLeg3.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg3.visit(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg3, 0.7853981633974483F, 0.0F, 0.0F);
        this.upperLeg4 = new ModelPart(this, 32, 0);
        this.upperLeg4.setPos(-3.0F, 1.5F, -3.0F);
        this.upperLeg4.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(upperLeg4, -1.0471975511965976F, 0.7853981633974483F, 0.0F);
        this.rightArm2 = new ModelPart(this, 46, 30);
        this.rightArm2.setPos(0.0F, 7.0F, 0.0F);
        this.rightArm2.visit(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm2, 0.0F, 0.0F, -0.6579891413176677F);
        this.body3 = new ModelPart(this, 0, 12);
        this.body3.setPos(0.0F, -6.0F, 0.0F);
        this.body3.visit(-4.5F, 0.0F, -4.5F, 9.0F, 6.0F, 9.0F, 0.0F, 0.0F, 0.0F);
        this.claw2 = new ModelPart(this, 0, 51);
        this.claw2.setPos(-1.0F, 0.0F, -8.0F);
        this.claw2.visit(-3.0F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, 0.02f);
        this.setRotateAngle(claw2, 0.0F, 0.42935100264850773F, 0.0F);
        this.body1 = new ModelPart(this, 0, 0);
        this.body1.setPos(0.0F, 11.0F, 0.0F);
        this.body1.visit(-4.0F, 0.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.rightFinger3 = new ModelPart(this, 24, 0);
        this.rightFinger3.setPos(1.0F, 7.5F, -1.0F);
        this.rightFinger3.visit(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFinger3, -0.3864158857389002F, 0.0F, -1.1163125735966066F);
        this.rightFinger2 = new ModelPart(this, 24, 0);
        this.rightFinger2.setPos(1.0F, 7.5F, 1.0F);
        this.rightFinger2.visit(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightFinger2, 0.5581562867983033F, 0.0F, -0.6869616042376124F);
        this.lowerLeg2 = new ModelPart(this, 44, 0);
        this.lowerLeg2.setPos(0.0F, 7.0F, 0.0F);
        this.lowerLeg2.visit(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(lowerLeg2, 0.7853981633974483F, 0.0F, 0.0F);
        this.head1 = new ModelPart(this, 10, 29);
        this.head1.setPos(0.0F, -2.0F, 0.0F);
        this.head1.visit(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.body2 = new ModelPart(this, 38, 10);
        this.body2.setPos(0.0F, -3.0F, 0.0F);
        this.body2.visit(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.claw1.getChild(this.claw12);
        this.head1.getChild(this.mouth);
        this.leftArm2.getChild(this.claw1);
        this.body3.getChild(this.leftArm1);
        this.rightArm2.getChild(this.rightFinger1);
        this.claw2.getChild(this.claw22);
        this.body3.getChild(this.rightArm1);
        this.body1.getChild(this.upperLeg3);
        this.body4.getChild(this.neck);
        this.leftArm1.getChild(this.leftArm2);
        this.claw2.getChild(this.claw21);
        this.body3.getChild(this.body4);
        this.upperLeg1.getChild(this.lowerLeg1);
        this.head2.getChild(this.head3);
        this.body1.getChild(this.upperLeg1);
        this.claw1.getChild(this.claw11);
        this.upperLeg4.getChild(this.lowerLeg4);
        this.body1.getChild(this.upperLeg2);
        this.head1.getChild(this.head2);
        this.upperLeg3.getChild(this.lowerLeg3);
        this.body1.getChild(this.upperLeg4);
        this.rightArm1.getChild(this.rightArm2);
        this.body2.getChild(this.body3);
        this.leftArm2.getChild(this.claw2);
        this.rightArm2.getChild(this.rightFinger3);
        this.rightArm2.getChild(this.rightFinger2);
        this.upperLeg2.getChild(this.lowerLeg2);
        this.neck.getChild(this.head1);
        this.body1.getChild(this.body2);
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        ImmutableList.of(this.body1).forEach((modelPart) -> {
            modelPart.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
        });
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelPart, float x, float y, float z) {
        modelPart.xRot = x;
        modelPart.yRot = y;
        modelPart.zRot = z;
    }

    @Override
    public void setupAnim(KraabEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {

        // Head
        head1.yRot = netHeadYaw * (PI / 180f);

        // Legs
        upperLeg1.xRot = -PI / 180 * 60 - Math.abs(Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount);
        upperLeg2.xRot = -PI / 180 * 60 - Math.abs(Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount);
        upperLeg3.xRot = -PI / 180 * 60 - Math.abs(Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount);
        upperLeg4.xRot = -PI / 180 * 60 - Math.abs(Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount);
        upperLeg1.yRot = -PI / 180 * 45 + Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount;
        upperLeg2.yRot = -PI / 180 * 135 + Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount;
        upperLeg3.yRot = PI / 180 * 135 + Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount;
        upperLeg4.yRot = PI / 180 * 45 + Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount;

        // Right Arm
        rightArm1.zRot = PI / 180 * 60 - Mth.cos(ageInTicks * 0.1f) * 0.05f;
        rightArm2.zRot = -PI / 180 * 40 - Mth.cos(ageInTicks * 0.1f) * 0.05f;

        // Attack
        if (!entityIn.isShooting()) {
            claw1.yRot = -PI / 180 * 10 - Mth.sin(attackTime * PI);
            claw2.yRot = PI / 180 * 10 + Mth.sin(attackTime * PI);
        }
    }

    @Override
    public void prepareMobModel(KraabEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        float bodyRot = 180 - Mth.rotLerp(partialTick, entityIn.yBodyRotO, entityIn.yBodyRot);
        body2.yRot = (PI / 180f) * bodyRot;

        // Shoot
        if (entityIn.isShooting()) {
            float shootTime = entityIn.getShootAnim(partialTick);
            claw1.yRot = -PI / 180 * 10 - shootTime * 50 * PI / 180;
            claw2.yRot = PI / 180 * 10 + shootTime * 50 * PI / 180;
        }
    }

}