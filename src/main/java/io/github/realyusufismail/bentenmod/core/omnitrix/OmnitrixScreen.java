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
package io.github.realyusufismail.bentenmod.core.omnitrix;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.capability.IOmnitrixData;
import io.github.realyusufismail.bentenmod.core.init.KeybindsInit;
import io.github.realyusufismail.bentenmod.core.network.CTransformPacket;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class OmnitrixScreen extends Screen {

    public static final ResourceLocation SCREEN_ID =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix.png");
    private static final ResourceLocation DIAL_TEXTURE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix_dial.png");
    private static final ResourceLocation LOCKED_TEXTURE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/alien_locked.png");

    private static final int RADIUS = 80;
    private static final int ICON_SIZE = 32;
    private static final int DIAL_SIZE = 64;

    @Nullable
    private AlienType hoveredAlien = null;

    @Nullable
    private AlienType confirmingAlien = null;

    private int confirmTicksRemaining = 0;

    private IOmnitrixData omnitrixData;

    public OmnitrixScreen(ITextComponent title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            omnitrixData =
                    mc.player.getCapability(CapabilityHandler.OMNITRIX_CAP).orElse(null);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Dark semi-transparent overlay
        fillGradient(matrixStack, 0, 0, this.width, this.height, 0xC0000000, 0xC0000000);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Draw central Omnitrix dial
        Minecraft.getInstance().getTextureManager().bind(DIAL_TEXTURE);
        blit(
                matrixStack,
                centerX - DIAL_SIZE / 2,
                centerY - DIAL_SIZE / 2,
                0,
                0,
                DIAL_SIZE,
                DIAL_SIZE,
                DIAL_SIZE,
                DIAL_SIZE);

        hoveredAlien = null;
        AlienType[] aliens = AlienType.values();

        for (int i = 0; i < aliens.length; i++) {
            AlienType alien = aliens[i];
            double angle = (2 * Math.PI * i / aliens.length) - (Math.PI / 2);
            int iconX = centerX + (int) (RADIUS * Math.cos(angle)) - ICON_SIZE / 2;
            int iconY = centerY + (int) (RADIUS * Math.sin(angle)) - ICON_SIZE / 2;

            boolean isHovered =
                    mouseX >= iconX && mouseX <= iconX + ICON_SIZE && mouseY >= iconY && mouseY <= iconY + ICON_SIZE;
            boolean isUnlocked = omnitrixData != null && omnitrixData.isAlienUnlocked(alien);
            boolean isConfirming = alien == confirmingAlien;

            if (isHovered) {
                hoveredAlien = alien;
            }

            // Highlight hovered alien with green glow
            if (isHovered && isUnlocked) {
                fillGradient(
                        matrixStack,
                        iconX - 2,
                        iconY - 2,
                        iconX + ICON_SIZE + 2,
                        iconY + ICON_SIZE + 2,
                        0x8000FF00,
                        0x8000FF00);
            }

            // Draw alien icon (or locked overlay)
            Minecraft.getInstance().getTextureManager().bind(alien.getIconTexture());
            if (isUnlocked) {
                blit(matrixStack, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
            } else {
                // Draw greyed-out overlay
                matrixStack.pushPose();
                fillGradient(matrixStack, iconX, iconY, iconX + ICON_SIZE, iconY + ICON_SIZE, 0x80888888, 0x80888888);
                Minecraft.getInstance().getTextureManager().bind(LOCKED_TEXTURE);
                blit(matrixStack, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
                matrixStack.popPose();
            }

            // Confirming flash animation
            if (isConfirming && confirmTicksRemaining > 0) {
                int alpha = (int) (128 * Math.abs(Math.sin(confirmTicksRemaining * 0.4)));
                int color = (alpha << 24) | 0x00FF00;
                fillGradient(
                        matrixStack,
                        iconX - 4,
                        iconY - 4,
                        iconX + ICON_SIZE + 4,
                        iconY + ICON_SIZE + 4,
                        color | 0xFF000000,
                        color | 0xFF000000);
            }
        }

        // Draw name of hovered alien
        if (hoveredAlien != null) {
            String name = hoveredAlien.getDisplayName();
            boolean unlocked = omnitrixData != null && omnitrixData.isAlienUnlocked(hoveredAlien);
            ITextComponent label =
                    new StringTextComponent(name).withStyle(unlocked ? TextFormatting.GREEN : TextFormatting.RED);
            int textWidth = this.font.width(label);
            this.font.drawShadow(matrixStack, label, centerX - textWidth / 2f, centerY + DIAL_SIZE / 2f + 8, 0xFFFFFF);

            if (!unlocked) {
                ITextComponent locked = new StringTextComponent("LOCKED").withStyle(TextFormatting.RED);
                int lw = this.font.width(locked);
                this.font.drawShadow(matrixStack, locked, centerX - lw / 2f, centerY + DIAL_SIZE / 2f + 20, 0xFF4444);
            }
        }

        // Draw cooldown status
        if (omnitrixData != null && omnitrixData.getCooldownTicksRemaining() > 0) {
            ITextComponent cooldown = new StringTextComponent("Omnitrix Recharging...").withStyle(TextFormatting.RED);
            int cw = this.font.width(cooldown);
            this.font.drawShadow(matrixStack, cooldown, centerX - cw / 2f, centerY - RADIUS - 20, 0xFF6666);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        if (confirmTicksRemaining > 0) {
            confirmTicksRemaining--;
            if (confirmTicksRemaining <= 0 && confirmingAlien != null) {
                // Send transform packet and close
                PacketHandler.CHANNEL.sendToServer(new CTransformPacket(confirmingAlien));
                this.onClose();
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        if (confirmingAlien != null) return true; // Already confirming

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        AlienType[] aliens = AlienType.values();

        for (int i = 0; i < aliens.length; i++) {
            AlienType alien = aliens[i];
            double angle = (2 * Math.PI * i / aliens.length) - (Math.PI / 2);
            int iconX = centerX + (int) (RADIUS * Math.cos(angle)) - ICON_SIZE / 2;
            int iconY = centerY + (int) (RADIUS * Math.sin(angle)) - ICON_SIZE / 2;

            if (mouseX >= iconX && mouseX <= iconX + ICON_SIZE && mouseY >= iconY && mouseY <= iconY + ICON_SIZE) {

                if (omnitrixData == null) return true;

                boolean unlocked = omnitrixData.isAlienUnlocked(alien);
                boolean onCooldown = omnitrixData.getCooldownTicksRemaining() > 0;

                if (unlocked && !onCooldown) {
                    // Start confirmation animation (15 ticks)
                    confirmingAlien = alien;
                    confirmTicksRemaining = 15;
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeybindsInit.OMNITRIX_KEY.isActiveAndMatches(
                net.minecraft.client.util.InputMappings.getKey(keyCode, scanCode))) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
