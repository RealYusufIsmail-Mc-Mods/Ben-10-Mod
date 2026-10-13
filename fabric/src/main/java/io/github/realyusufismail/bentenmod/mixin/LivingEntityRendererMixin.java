package io.github.realyusufismail.bentenmod.mixin;

import io.github.realyusufismail.bentenmod.client.alien.AlienPlayerReplacementRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Replaces only transformed player renders; every normal player uses vanilla rendering. */
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void bentenmod$renderAlien(LivingEntity entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            CallbackInfo callback) {
        if (!(entity instanceof AbstractClientPlayer player)
                || !AlienPlayerReplacementRenderer.isTransformed(player)) {
            return;
        }
        AlienPlayerReplacementRenderer.get()
            .render(player, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        callback.cancel();
    }
}
