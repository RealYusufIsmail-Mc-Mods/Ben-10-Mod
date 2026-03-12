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
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.client.model.aliens.DiamondheadAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.FourArmsAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.GhostfreakAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.GreyMatterAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.HeatblastAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.RipjawsAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.StinkflyAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.UpgradeAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.WildmuttAlienModel;
import io.github.realyusufismail.bentenmod.client.model.aliens.XLR8AlienModel;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.util.RenderUtils;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class AlienRenderHandler {

    /** Models per alien type (instantiated once, reused). */
    private static final Map<AlienType, AnimatedGeoModel<PlayerEntity>> MODELS =
            new EnumMap<>(AlienType.class);

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

        AnimatedGeoModel<PlayerEntity> model = MODELS.get(alien);
        if (model == null) return;

        renderAlien(event.getPlayer(), alien, model,
                event.getMatrixStack(), event.getBuffers(), event.getPartialRenderTick());
    }

    private static void renderAlien(
            PlayerEntity player,
            AlienType alien,
            AnimatedGeoModel<PlayerEntity> animModel,
            MatrixStack matrixStack,
            IRenderTypeBuffer buffers,
            float partialTicks) {

        matrixStack.pushPose();

        // Rotate to face player yaw
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180f - player.yBodyRot));

        // Scale down for tiny aliens
        if (alien == AlienType.GREY_MATTER) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        // Tick animations
        long uniqueId = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits();
        animModel.setCustomAnimations(player, uniqueId, null);

        GeoModel bakedModel = animModel.getBakedModel(animModel.getModelLocation(player));

        int light = WorldRenderer.getLightColor(player.level, player.blockPosition());
        int overlay = net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

        com.mojang.blaze3d.vertex.IVertexBuilder vertexBuilder =
                buffers.getBuffer(RenderType.entityCutoutNoCull(animModel.getTextureLocation(player)));

        for (software.bernie.geckolib3.geo.render.built.GeoBone bone : bakedModel.topLevelBones) {
            RenderUtils.renderMeshBone(matrixStack, bakedModel, bone, vertexBuilder, light, overlay, 1f, 1f, 1f, 1f);
        }

        matrixStack.popPose();
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
