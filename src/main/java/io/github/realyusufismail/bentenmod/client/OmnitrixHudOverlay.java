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
package io.github.realyusufismail.bentenmod.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.capability.IOmnitrixData;
import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * Renders a small HUD in the bottom-left corner when the player is transformed
 * or the Omnitrix is on cooldown.
 *
 * <p>Transformed: alien icon (32×32) + green timer bar + alien name.
 * Cooldown: greyed icon area + red recharge bar + "Recharging Xs".
 *
 * <p>Extends {@link AbstractGui} to gain access to the protected
 * {@code blit} overload that accepts explicit texture dimensions.
 */
@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class OmnitrixHudOverlay extends AbstractGui {

    /** Singleton used only for protected-blit access. */
    private static final OmnitrixHudOverlay INSTANCE = new OmnitrixHudOverlay();

    private static final int ICON_SIZE = 32;
    private static final int BAR_WIDTH = 60;
    private static final int BAR_HEIGHT = 6;
    private static final int MARGIN_X = 8;
    private static final int MARGIN_Y = 8;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        IOmnitrixData data =
                mc.player.getCapability(CapabilityHandler.OMNITRIX_CAP).orElse(null);
        if (data == null) return;

        boolean transformed = data.isTransformed();
        boolean onCooldown = !transformed && data.getCooldownTicksRemaining() > 0;
        if (!transformed && !onCooldown) return;

        MatrixStack ms = event.getMatrixStack();
        int screenH = mc.getWindow().getGuiScaledHeight();

        // Bottom-left origin
        int x = MARGIN_X;
        int y = screenH - MARGIN_Y - ICON_SIZE - BAR_HEIGHT - 4;

        AlienType alien = data.getCurrentAlien();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // ── Alien icon ──────────────────────────────────────────────────────
        if (alien != null) {
            mc.getTextureManager().bind(alien.getIconTexture());
            if (!transformed) {
                RenderSystem.color4f(0.4f, 0.4f, 0.4f, 0.8f);
            } else {
                RenderSystem.color4f(1f, 1f, 1f, 1f);
            }
            // Use the protected blit that accepts explicit texture dimensions
            INSTANCE.blit(ms, x, y, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
            RenderSystem.color4f(1f, 1f, 1f, 1f);
        }

        // ── Timer bar ────────────────────────────────────────────────────────
        int barX = x;
        int barY = y + ICON_SIZE + 2;

        fill(ms, barX, barY, barX + BAR_WIDTH, barY + BAR_HEIGHT, 0xAA000000);

        if (transformed && alien != null) {
            int maxTicks = alien.getTransformDurationTicks();
            float fraction = maxTicks > 0 ? (float) data.getTransformTicksRemaining() / maxTicks : 0f;
            int fillW = (int) (BAR_WIDTH * fraction);
            int barColor;
            if (fraction > 0.5f) barColor = 0xFF00DD00;
            else if (fraction > 0.2f) barColor = 0xFFDDDD00;
            else barColor = 0xFFDD2200;
            fill(ms, barX, barY, barX + fillW, barY + BAR_HEIGHT, barColor);
            mc.font.drawShadow(ms, alien.getDisplayName(), barX, barY + BAR_HEIGHT + 2, 0xFF00FF00);
        } else {
            int cdTicks = data.getCooldownTicksRemaining();
            int maxCd = alien != null ? alien.getCooldownTicks() : 600;
            float fraction = maxCd > 0 ? (float) cdTicks / maxCd : 0f;
            int fillW = (int) (BAR_WIDTH * fraction);
            fill(ms, barX, barY, barX + fillW, barY + BAR_HEIGHT, 0xFFDD2200);
            int cdSecs = (cdTicks / 20) + 1;
            mc.font.drawShadow(ms, "Recharging " + cdSecs + "s", barX, barY + BAR_HEIGHT + 2, 0xFFDD4444);
        }

        RenderSystem.disableBlend();
    }
}
