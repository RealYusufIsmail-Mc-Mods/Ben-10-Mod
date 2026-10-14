package io.github.realyusufismail.bentenmod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** Renders the existing GeckoLib watch item at the left wrist for untransformed players. */
public final class OmnitrixWristLayer
        extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ItemStack WATCH = new ItemStack(ItemInit.OMNITRIX_WATCH.get());

    public OmnitrixWristLayer(
            RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
            AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        OmnitrixSnapshot state = ClientOmnitrixState.get(player.getUUID());
        if (state == null || !state.equipped() || state.currentAlien() != null) {
            return;
        }

        poseStack.pushPose();
        getParentModel().leftArm.translateAndRotate(poseStack);
        poseStack.translate(0.0D, 0.52D, 0.0D);
        poseStack.scale(0.55F, 0.55F, 0.55F);
        Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(WATCH, ItemDisplayContext.FIXED, packedLight,
                    net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, poseStack,
                    buffer, player.level(), player.getId());
        poseStack.popPose();
    }
}
