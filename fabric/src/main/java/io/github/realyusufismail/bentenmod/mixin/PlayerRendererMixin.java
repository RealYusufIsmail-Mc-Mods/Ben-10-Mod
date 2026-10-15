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
package io.github.realyusufismail.bentenmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.realyusufismail.bentenmod.client.alien.AlienPlayerReplacementRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Fabric's equivalent of Forge's RenderArmEvent: transformed players see the alien's arms. */
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Inject(method = "renderRightHand", at = @At("HEAD"), cancellable = true)
    private void bentenmod$renderAlienRightArm(PoseStack poseStack, MultiBufferSource bufferSource,
            int packedLight, AbstractClientPlayer player, CallbackInfo callback) {
        renderAlienArm(HumanoidArm.RIGHT, poseStack, bufferSource, packedLight, player, callback);
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"), cancellable = true)
    private void bentenmod$renderAlienLeftArm(PoseStack poseStack, MultiBufferSource bufferSource,
            int packedLight, AbstractClientPlayer player, CallbackInfo callback) {
        renderAlienArm(HumanoidArm.LEFT, poseStack, bufferSource, packedLight, player, callback);
    }

    private static void renderAlienArm(HumanoidArm arm, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player,
            CallbackInfo callback) {
        if (AlienPlayerReplacementRenderer.get()
            .renderFirstPersonArm(player, arm, poseStack, bufferSource, packedLight)) {
            callback.cancel();
        }
    }
}
