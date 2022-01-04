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

package io.github.realyusufismail.bentenmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Created using Tabula 8.0.0
 */
public class KraabModel extends EntityModel<CrabEntity> {

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

    public KraabModel(ModelPart parts) {
        this.body1 = parts.getChild("body1");
        this.upperLeg1 = body1.getChild("upperLeg1");
        this.body2 = body1.getChild("body2");
        this.upperLeg2 = body1.getChild("upperLeg2");
        this.upperLeg4 = body1.getChild("upperLeg4");
        this.upperLeg3 = body1.getChild("upperLeg3");
        this.lowerLeg1 = upperLeg1.getChild("lowerLeg1");
        this.body3 = body2.getChild("body3");
        this.body4 = body3.getChild("body4");
        this.rightArm1 = body3.getChild("rightArm1");
        this.leftArm1 = body3.getChild("leftArm1");
        this.neck = body4.getChild("neck");
        this.head1 = neck.getChild("head1");
        this.mouth = head1.getChild("mouth");
        this.head2 = head1.getChild("head2");
        this.head3 = head2.getChild("head3");
        this.rightArm2 = rightArm1.getChild("rightArm2");
        this.rightFinger1 = rightArm2.getChild("rightFinger1");
        this.rightFinger2 = rightArm2.getChild("rightFinger2");
        this.rightFinger3 = rightArm2.getChild("rightFinger3");
        this.leftArm2 = leftArm1.getChild("leftArm2");
        this.claw1 = leftArm2.getChild("claw1");
        this.claw2 = leftArm2.getChild("claw2");
        this.claw11 = claw1.getChild("claw11");
        this.claw12 = claw1.getChild("claw12");
        this.claw21 = claw2.getChild("claw21");
        this.claw22 = claw2.getChild("claw22");
        this.lowerLeg2 = upperLeg2.getChild("lowerLeg2");
        this.lowerLeg4 = upperLeg4.getChild("lowerLeg4");
        this.lowerLeg3 = upperLeg3.getChild("lowerLeg3");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition body1 = parts.addOrReplaceChild("body1",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 4.0F, 8.0F, false),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0, 0, 0));
        PartDefinition upperLeg1 = body1.addOrReplaceChild("upperLeg1",
                CubeListBuilder.create()
                    .texOffs(32, 0)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(3.0F, 1.5F, -3.0F, -1.0471975511965976F,
                        -0.7853981633974483F, 0.0F));
        PartDefinition body2 = body1.addOrReplaceChild("body2",
                CubeListBuilder.create()
                    .texOffs(38, 10)
                    .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F, false),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0, 0, 0));
        PartDefinition upperLeg2 = body1.addOrReplaceChild("upperLeg2",
                CubeListBuilder.create()
                    .texOffs(32, 0)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(3.0F, 1.5F, 3.0F, -1.0471975511965976F,
                        -2.4169318735932372F, 0.0F));
        PartDefinition upperLeg4 = body1.addOrReplaceChild("upperLeg4",
                CubeListBuilder.create()
                    .texOffs(32, 0)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(-3.0F, 1.5F, -3.0F, -1.0471975511965976F,
                        0.7853981633974483F, 0.0F));
        PartDefinition upperLeg3 = body1.addOrReplaceChild("upperLeg3",
                CubeListBuilder.create()
                    .texOffs(32, 0)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(-3.0F, 1.5F, 3.0F, -1.0471975511965976F,
                        2.3739969231311973F, 0.0F));
        PartDefinition lowerLeg1 = upperLeg1.addOrReplaceChild("lowerLeg1",
                CubeListBuilder.create()
                    .texOffs(44, 0)
                    .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition body3 = body2.addOrReplaceChild("body3",
                CubeListBuilder.create()
                    .texOffs(0, 12)
                    .addBox(-4.5F, 0.0F, -4.5F, 9.0F, 6.0F, 9.0F, false),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0, 0, 0));
        PartDefinition body4 = body3.addOrReplaceChild("body4",
                CubeListBuilder.create()
                    .texOffs(28, 19)
                    .addBox(-3.5F, 0.0F, -4.0F, 7.0F, 3.0F, 8.0F, false),
                PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.27366763203903305F, 0.0F, 0.0F));
        PartDefinition rightArm1 = body3.addOrReplaceChild("rightArm1",
                CubeListBuilder.create()
                    .texOffs(0, 36)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(-4.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0164797856562695F));
        PartDefinition leftArm1 = body3.addOrReplaceChild("leftArm1",
                CubeListBuilder.create()
                    .texOffs(0, 36)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(4.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0164797856562695F));
        PartDefinition neck = body4.addOrReplaceChild("neck",
                CubeListBuilder.create()
                    .texOffs(0, 27)
                    .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, false),
                PartPose.offsetAndRotation(0.0F, -2.0F, 1.0F, -0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition head1 = neck.addOrReplaceChild("head1",
                CubeListBuilder.create()
                    .texOffs(10, 29)
                    .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, false),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0, 0, 0));
        PartDefinition mouth = head1.addOrReplaceChild("mouth",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-1.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 0.5F, -2.0F, 0, 0, 0));
        PartDefinition head2 = head1.addOrReplaceChild("head2",
                CubeListBuilder.create()
                    .texOffs(14, 51)
                    .addBox(-2.5F, 0.0F, -2.5F, 5.0F, 1.0F, 5.0F, false),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0, 0, 0));
        PartDefinition head3 = head2.addOrReplaceChild("head3",
                CubeListBuilder.create()
                    .texOffs(34, 49)
                    .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F, false),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0, 0, 0));
        PartDefinition rightArm2 = rightArm1.addOrReplaceChild("rightArm2",
                CubeListBuilder.create()
                    .texOffs(46, 30)
                    .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, false),
                PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, -0.6579891413176677F));
        PartDefinition rightFinger1 = rightArm2.addOrReplaceChild("rightFinger1",
                CubeListBuilder.create()
                    .texOffs(24, 0)
                    .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, false),
                PartPose.offsetAndRotation(-1.0F, 7.5F, 0.0F, -0.12880530079455235F, 0.0F,
                        0.5152212031782094F));
        PartDefinition rightFinger2 = rightArm2.addOrReplaceChild("rightFinger2",
                CubeListBuilder.create()
                    .texOffs(24, 0)
                    .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, false),
                PartPose.offsetAndRotation(1.0F, 7.5F, 1.0F, 0.5581562867983033F, 0.0F,
                        -0.6869616042376124F));
        PartDefinition rightFinger3 = rightArm2.addOrReplaceChild("rightFinger3",
                CubeListBuilder.create()
                    .texOffs(24, 0)
                    .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, false),
                PartPose.offsetAndRotation(1.0F, 7.5F, -1.0F, -0.3864158857389002F, 0.0F,
                        -1.1163125735966066F));
        PartDefinition leftArm2 = leftArm1.addOrReplaceChild("leftArm2",
                CubeListBuilder.create()
                    .texOffs(4, 39)
                    .addBox(-3.0F, -2.0F, -8.0F, 6.0F, 4.0F, 8.0F, false),
                PartPose.offsetAndRotation(0.0F, 7.0F, 1.0F, 0, 0, 0));
        PartDefinition claw1 = leftArm2.addOrReplaceChild("claw1",
                CubeListBuilder.create()
                    .texOffs(0, 51)
                    .addBox(0.0F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, false),
                PartPose.offsetAndRotation(1.0F, 0.0F, -8.0F, 0.0F, -0.3864158857389002F, 0.0F));
        PartDefinition claw2 = leftArm2.addOrReplaceChild("claw2",
                CubeListBuilder.create()
                    .texOffs(0, 51)
                    .addBox(-3.0F, -2.0F, -8.0F, 3.0F, 4.0F, 8.0F, false),
                PartPose.offsetAndRotation(-1.0F, 0.0F, -8.0F, 0.0F, 0.42935100264850773F, 0.0F));
        PartDefinition claw11 = claw1.addOrReplaceChild("claw11",
                CubeListBuilder.create()
                    .texOffs(30, 11)
                    .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 0.0F, -6.6F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition claw12 = claw1.addOrReplaceChild("claw12",
                CubeListBuilder.create()
                    .texOffs(30, 11)
                    .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 0.0F, -3.8F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition claw21 = claw2.addOrReplaceChild("claw21",
                CubeListBuilder.create()
                    .texOffs(30, 11)
                    .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 0.0F, -6.6F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition claw22 = claw2.addOrReplaceChild("claw22",
                CubeListBuilder.create()
                    .texOffs(30, 11)
                    .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 0.0F, -3.8F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition lowerLeg2 = upperLeg2.addOrReplaceChild("lowerLeg2",
                CubeListBuilder.create()
                    .texOffs(44, 0)
                    .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition lowerLeg4 = upperLeg4.addOrReplaceChild("lowerLeg4",
                CubeListBuilder.create()
                    .texOffs(44, 0)
                    .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition lowerLeg3 = upperLeg3.addOrReplaceChild("lowerLeg3",
                CubeListBuilder.create()
                    .texOffs(44, 0)
                    .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, false),
                PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.7853981633974483F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_,
            int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        ImmutableList.of(this.body1).forEach((modelPart) -> {
            modelPart.render(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_,
                    p_103117_, p_103118_);
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
    public void setupAnim(CrabEntity entityIn, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {

        // Head
        head1.yRot = netHeadYaw * (PI / 180f);

        // Legs
        upperLeg1.xRot =
                -PI / 180 * 60 - Math.abs(Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount);
        upperLeg2.xRot = -PI / 180 * 60
                - Math.abs(Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount);
        upperLeg3.xRot =
                -PI / 180 * 60 - Math.abs(Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount);
        upperLeg4.xRot = -PI / 180 * 60
                - Math.abs(Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount);
        upperLeg1.yRot = -PI / 180 * 45 + Mth.cos(limbSwing * 0.5f) * 1f * limbSwingAmount;
        upperLeg2.yRot =
                -PI / 180 * 135 + Mth.cos(limbSwing * 0.5f + PI / 2) * 1f * limbSwingAmount;
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
    public void prepareMobModel(CrabEntity entityIn, float limbSwing, float limbSwingAmount,
            float partialTick) {
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
