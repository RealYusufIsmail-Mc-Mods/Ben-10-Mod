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
