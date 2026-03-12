/*
 * Copyright 2026 RealYusufIsmail.
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
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.client.model.aliens.*;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.geo.render.built.GeoQuad;
import software.bernie.geckolib3.geo.render.built.GeoVertex;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.util.RenderUtils;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class AlienRenderHandler {

    /** Models per alien type (instantiated once, reused). */
    private static final Map<AlienType, GeoModelProvider<Void>> MODELS = new EnumMap<>(AlienType.class);

    /** UUID -> alien type for other players seen in multiplayer. */
    public static final Map<UUID, AlienType> PLAYER_ALIEN_MAP = new HashMap<>();

    static {
        MODELS.put(AlienType.HEATBLAST, new HeatblastAlienModel());
        MODELS.put(AlienType.XLR8, new XLR8AlienModel());
        MODELS.put(AlienType.FOUR_ARMS, new FourArmsAlienModel());
        MODELS.put(AlienType.DIAMONDHEAD, new DiamondheadAlienModel());
        MODELS.put(AlienType.GREY_MATTER, new GreyMatterAlienModel());
        MODELS.put(AlienType.STINKFLY, new StinkflyAlienModel());
        MODELS.put(AlienType.RIPJAWS, new RipjawsAlienModel());
        MODELS.put(AlienType.GHOSTFREAK, new GhostfreakAlienModel());
        MODELS.put(AlienType.WILDMUTT, new WildmuttAlienModel());
        MODELS.put(AlienType.UPGRADE, new UpgradeAlienModel());
    }

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        AlienType alien = getTransformedAlien(event.getPlayer());
        if (alien == null) return;

        // Cancel vanilla player render
        event.setCanceled(true);

        GeoModelProvider<Void> model = MODELS.get(alien);
        if (model == null) return;

        renderAlien(
                event.getPlayer(),
                alien,
                model,
                event.getMatrixStack(),
                event.getBuffers(),
                event.getPartialRenderTick());
    }

    private static void renderAlien(
            PlayerEntity player,
            AlienType alien,
            GeoModelProvider<Void> modelProvider,
            MatrixStack matrixStack,
            IRenderTypeBuffer buffers,
            float partialTicks) {

        ResourceLocation modelLoc = modelProvider.getModelLocation(null);
        ResourceLocation textureLoc = modelProvider.getTextureLocation(null);

        GeoModel bakedModel = modelProvider.getModel(modelLoc);
        if (bakedModel == null) return;

        matrixStack.pushPose();

        // Rotate to face player yaw
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180f - player.yBodyRot));

        // Scale for tiny / large aliens
        if (alien == AlienType.GREY_MATTER) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else if (alien == AlienType.FOUR_ARMS) {
            matrixStack.scale(1.3f, 1.3f, 1.3f);
        }

        int light = WorldRenderer.getLightColor(player.level, player.blockPosition());
        int overlay = OverlayTexture.NO_OVERLAY;

        IVertexBuilder vertexBuilder = buffers.getBuffer(RenderType.entityCutoutNoCull(textureLoc));

        // Render all top-level bones recursively
        for (GeoBone bone : bakedModel.topLevelBones) {
            renderBoneRecursive(bone, matrixStack, vertexBuilder, light, overlay, 1f, 1f, 1f, 1f);
        }

        matrixStack.popPose();
    }

    /**
     * Recursively renders a bone and all of its children. This mirrors the logic in
     * {@link software.bernie.geckolib3.renderers.geo.IGeoRenderer#renderRecursively} but
     * does not require an {@code IAnimatable} type parameter.
     */
    private static void renderBoneRecursive(
            GeoBone bone,
            MatrixStack stack,
            IVertexBuilder bufferIn,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {

        stack.pushPose();

        // Position, pivot, rotation, scale — same order as IGeoRenderer
        RenderUtils.translate(bone, stack);
        RenderUtils.moveToPivot(bone, stack);
        RenderUtils.rotate(bone, stack);
        RenderUtils.scale(bone, stack);
        RenderUtils.moveBackFromPivot(bone, stack);

        // Render cubes of this bone
        if (!bone.isHidden()) {
            for (GeoCube cube : bone.childCubes) {
                stack.pushPose();
                if (!bone.cubesAreHidden()) {
                    renderCube(cube, stack, bufferIn, packedLight, packedOverlay, red, green, blue, alpha);
                }
                stack.popPose();
            }
        }

        // Render child bones
        if (!bone.childBonesAreHiddenToo()) {
            for (GeoBone childBone : bone.childBones) {
                renderBoneRecursive(childBone, stack, bufferIn, packedLight, packedOverlay, red, green, blue, alpha);
            }
        }

        stack.popPose();
    }

    private static void renderCube(
            GeoCube cube,
            MatrixStack stack,
            IVertexBuilder bufferIn,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {

        RenderUtils.moveToPivot(cube, stack);
        RenderUtils.rotate(cube, stack);
        RenderUtils.moveBackFromPivot(cube, stack);

        Matrix3f matrix3f = stack.last().normal();
        Matrix4f matrix4f = stack.last().pose();

        for (GeoQuad quad : cube.quads) {
            if (quad == null) continue;

            Vector3f normal = quad.normal.copy();
            normal.transform(matrix3f);

            // Fix flat-cube shading (same as IGeoRenderer)
            if ((cube.size.y() == 0 || cube.size.z() == 0) && normal.x() < 0) {
                normal.mul(-1, 1, 1);
            }
            if ((cube.size.x() == 0 || cube.size.z() == 0) && normal.y() < 0) {
                normal.mul(1, -1, 1);
            }
            if ((cube.size.x() == 0 || cube.size.y() == 0) && normal.z() < 0) {
                normal.mul(1, 1, -1);
            }

            for (GeoVertex vertex : quad.vertices) {
                Vector4f vector4f = new Vector4f(vertex.position.x(), vertex.position.y(), vertex.position.z(), 1.0F);
                vector4f.transform(matrix4f);
                bufferIn.vertex(
                        vector4f.x(),
                        vector4f.y(),
                        vector4f.z(),
                        red,
                        green,
                        blue,
                        alpha,
                        vertex.textureU,
                        vertex.textureV,
                        packedOverlay,
                        packedLight,
                        normal.x(),
                        normal.y(),
                        normal.z());
            }
        }
    }

    private static AlienType getTransformedAlien(PlayerEntity player) {
        AlienType[] found = {null};
        player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            if (data.isTransformed()) found[0] = data.getCurrentAlien();
        });
        if (found[0] != null) return found[0];
        return PLAYER_ALIEN_MAP.get(player.getUUID());
    }
}
