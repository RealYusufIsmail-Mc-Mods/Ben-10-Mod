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
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.capability.IOmnitrixData;
import io.github.realyusufismail.bentenmod.core.init.KeybindsInit;
import io.github.realyusufismail.bentenmod.core.network.CTransformPacket;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

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

    // Needle smoothly rotates toward hovered alien
    private double needleAngle = -Math.PI / 2; // starts pointing up
    private double targetNeedleAngle = -Math.PI / 2;
    private int needlePulse = 0; // ticks for pulse effect

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
            omnitrixData = mc.player.getCapability(CapabilityHandler.OMNITRIX_CAP).orElse(null);
        }
    }

    @Override
    public void tick() {
        // Smooth needle rotation — lerp toward target angle (shortest path)
        double diff = targetNeedleAngle - needleAngle;
        // Wrap to [-PI, PI]
        while (diff > Math.PI) diff -= 2 * Math.PI;
        while (diff < -Math.PI) diff += 2 * Math.PI;
        needleAngle += diff * 0.25;

        if (needlePulse > 0) needlePulse--;

        if (confirmTicksRemaining > 0) {
            confirmTicksRemaining--;
            if (confirmTicksRemaining <= 0 && confirmingAlien != null) {
                PacketHandler.CHANNEL.sendToServer(new CTransformPacket(confirmingAlien));
                this.onClose();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Dark semi-transparent overlay
        fillGradient(matrixStack, 0, 0, this.width, this.height, 0xC0000000, 0xC0000000);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Draw outer ring (segment lines dividing the 10 slots)
        drawSegmentLines(matrixStack, centerX, centerY);

        // Draw alien icons
        hoveredAlien = null;
        AlienType[] aliens = AlienType.values();

        for (int i = 0; i < aliens.length; i++) {
            AlienType alien = aliens[i];
            double angle = slotAngle(i, aliens.length);
            int iconX = centerX + (int) (RADIUS * Math.cos(angle)) - ICON_SIZE / 2;
            int iconY = centerY + (int) (RADIUS * Math.sin(angle)) - ICON_SIZE / 2;

            boolean isHovered = mouseX >= iconX && mouseX <= iconX + ICON_SIZE
                    && mouseY >= iconY && mouseY <= iconY + ICON_SIZE;
            boolean isUnlocked = omnitrixData != null && omnitrixData.isAlienUnlocked(alien);
            boolean isConfirming = alien == confirmingAlien;

            if (isHovered) {
                hoveredAlien = alien;
                targetNeedleAngle = angle;
            }

            // Green glow highlight for hovered unlocked alien
            if (isHovered && isUnlocked) {
                fillGradient(matrixStack, iconX - 3, iconY - 3, iconX + ICON_SIZE + 3, iconY + ICON_SIZE + 3,
                        0x9900FF00, 0x9900CC00);
            }

            // Draw alien icon
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Minecraft.getInstance().getTextureManager().bind(alien.getIconTexture());
            if (isUnlocked) {
                blit(matrixStack, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
            } else {
                // Grey tint for locked
                RenderSystem.color4f(0.4f, 0.4f, 0.4f, 0.9f);
                blit(matrixStack, iconX, iconY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
                RenderSystem.color4f(1f, 1f, 1f, 1f);
                // Lock overlay
                Minecraft.getInstance().getTextureManager().bind(LOCKED_TEXTURE);
                blit(matrixStack, iconX + 8, iconY + 8, 0, 0, 16, 16, 16, 16);
            }

            // Confirmation flash
            if (isConfirming && confirmTicksRemaining > 0) {
                float flashAlpha = (float) Math.abs(Math.sin(confirmTicksRemaining * 0.42)) * 0.7f;
                fillGradient(matrixStack, iconX - 5, iconY - 5, iconX + ICON_SIZE + 5, iconY + ICON_SIZE + 5,
                        (int) (flashAlpha * 255) << 24 | 0x00FF00,
                        (int) (flashAlpha * 255) << 24 | 0x00FF00);
            }
        }

        // Draw needle (on top of icons, below dial center)
        drawNeedle(matrixStack, centerX, centerY, partialTicks);

        // Draw central Omnitrix dial on top
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        Minecraft.getInstance().getTextureManager().bind(DIAL_TEXTURE);
        blit(matrixStack, centerX - DIAL_SIZE / 2, centerY - DIAL_SIZE / 2, 0, 0, DIAL_SIZE, DIAL_SIZE, DIAL_SIZE, DIAL_SIZE);

        // Alien name label
        if (hoveredAlien != null) {
            boolean unlocked = omnitrixData != null && omnitrixData.isAlienUnlocked(hoveredAlien);
            ITextComponent label = new StringTextComponent(hoveredAlien.getDisplayName())
                    .withStyle(unlocked ? TextFormatting.GREEN : TextFormatting.RED);
            int textWidth = this.font.width(label);
            this.font.drawShadow(matrixStack, label, centerX - textWidth / 2f, centerY + RADIUS + 10, 0xFFFFFFFF);

            if (!unlocked) {
                ITextComponent locked = new StringTextComponent("[ LOCKED ]").withStyle(TextFormatting.RED);
                int lw = this.font.width(locked);
                this.font.drawShadow(matrixStack, locked, centerX - lw / 2f, centerY + RADIUS + 22, 0xFF4444FF);
            }
        }

        // Cooldown banner
        if (omnitrixData != null && omnitrixData.getCooldownTicksRemaining() > 0) {
            int cdTicks = omnitrixData.getCooldownTicksRemaining();
            int cdSecs = (cdTicks / 20) + 1;
            ITextComponent cooldown = new StringTextComponent("Recharging... " + cdSecs + "s").withStyle(TextFormatting.RED);
            int cw = this.font.width(cooldown);
            this.font.drawShadow(matrixStack, cooldown, centerX - cw / 2f, centerY - RADIUS - 20, 0xFF6666FF);
        }

        // Hint text at bottom
        ITextComponent hint = new StringTextComponent("[O] Close   [R] Revert").withStyle(TextFormatting.DARK_GRAY);
        int hw = this.font.width(hint);
        this.font.drawShadow(matrixStack, hint, centerX - hw / 2f, this.height - 16, 0x888888FF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Draws the 10 segment dividing lines radiating out from just outside the dial to just inside
     * the icon ring — like the Omnitrix faceplate grooves.
     */
    private void drawSegmentLines(MatrixStack matrixStack, int centerX, int centerY) {
        int numAliens = AlienType.values().length;
        int innerR = DIAL_SIZE / 2 + 4;
        int outerR = RADIUS + ICON_SIZE / 2 + 6;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(1.5f);

        Matrix4f pose = matrixStack.last().pose();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuilder();

        buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < numAliens; i++) {
            // Each line sits halfway between two slot centers
            double angle = (2 * Math.PI * i / numAliens) - (Math.PI / 2) - (Math.PI / numAliens);
            float x1 = centerX + (float) (innerR * Math.cos(angle));
            float y1 = centerY + (float) (innerR * Math.sin(angle));
            float x2 = centerX + (float) (outerR * Math.cos(angle));
            float y2 = centerY + (float) (outerR * Math.sin(angle));
            buf.vertex(pose, x1, y1, 0).color(0, 200, 0, 80).endVertex();
            buf.vertex(pose, x2, y2, 0).color(0, 200, 0, 80).endVertex();
        }
        tessellator.end();

        // Outer circle arc (dotted ring around the icon positions)
        buf.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
        int segments = 60;
        for (int i = 0; i < segments; i++) {
            double a = 2 * Math.PI * i / segments;
            buf.vertex(pose,
                    centerX + (float) (RADIUS * Math.cos(a)),
                    centerY + (float) (RADIUS * Math.sin(a)),
                    0).color(0, 180, 0, 60).endVertex();
        }
        tessellator.end();

        RenderSystem.enableTexture();
    }

    /**
     * Draws the rotating needle from dial center toward the hovered alien slot.
     * The needle smoothly animates via {@link #needleAngle}.
     */
    private void drawNeedle(MatrixStack matrixStack, int centerX, int centerY, float partialTicks) {
        int needleLength = DIAL_SIZE / 2 + 12;
        int needleBase = 6; // half-width of the arrowhead base

        double angle = needleAngle;

        float tipX = centerX + (float) (needleLength * Math.cos(angle));
        float tipY = centerY + (float) (needleLength * Math.sin(angle));

        // Perpendicular for arrowhead base
        float perpX = (float) (needleBase * Math.cos(angle + Math.PI / 2));
        float perpY = (float) (needleBase * Math.sin(angle + Math.PI / 2));

        float baseX = centerX + (float) (4 * Math.cos(angle + Math.PI));
        float baseY = centerY + (float) (4 * Math.sin(angle + Math.PI));

        // Pulse green brightness on needlePulse
        int green = needlePulse > 0 ? 255 : 180;

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Matrix4f pose = matrixStack.last().pose();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuilder();

        // Filled triangle arrow
        buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
        buf.vertex(pose, tipX, tipY, 0).color(0, green, 0, 220).endVertex();
        buf.vertex(pose, baseX + perpX, baseY + perpY, 0).color(0, green / 2, 0, 180).endVertex();
        buf.vertex(pose, baseX - perpX, baseY - perpY, 0).color(0, green / 2, 0, 180).endVertex();
        tessellator.end();

        // Center dot
        buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        int dotR = 5;
        buf.vertex(pose, centerX, centerY, 0).color(0, 255, 0, 255).endVertex();
        for (int i = 0; i <= 12; i++) {
            double a = 2 * Math.PI * i / 12;
            buf.vertex(pose,
                    centerX + (float) (dotR * Math.cos(a)),
                    centerY + (float) (dotR * Math.sin(a)),
                    0).color(0, 200, 0, 200).endVertex();
        }
        tessellator.end();

        RenderSystem.enableTexture();
    }

    /** Angle for alien slot i (pointing up at -PI/2 for first slot). */
    private static double slotAngle(int i, int total) {
        return (2 * Math.PI * i / total) - (Math.PI / 2);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        if (confirmingAlien != null) return true;

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        AlienType[] aliens = AlienType.values();

        for (int i = 0; i < aliens.length; i++) {
            AlienType alien = aliens[i];
            double angle = slotAngle(i, aliens.length);
            int iconX = centerX + (int) (RADIUS * Math.cos(angle)) - ICON_SIZE / 2;
            int iconY = centerY + (int) (RADIUS * Math.sin(angle)) - ICON_SIZE / 2;

            if (mouseX >= iconX && mouseX <= iconX + ICON_SIZE
                    && mouseY >= iconY && mouseY <= iconY + ICON_SIZE) {
                if (omnitrixData == null) return true;

                boolean unlocked = omnitrixData.isAlienUnlocked(alien);
                boolean onCooldown = omnitrixData.getCooldownTicksRemaining() > 0;

                if (unlocked && !onCooldown) {
                    confirmingAlien = alien;
                    confirmTicksRemaining = 15;
                    needlePulse = 15;
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
