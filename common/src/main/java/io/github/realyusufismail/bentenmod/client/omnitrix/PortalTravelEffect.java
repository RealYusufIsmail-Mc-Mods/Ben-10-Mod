package io.github.realyusufismail.bentenmod.client.omnitrix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

/** Short purple-black vignette that closes to black and fades after portal travel. */
public final class PortalTravelEffect {
    private static final int DURATION_TICKS = 20;
    private static int remainingTicks;

    private PortalTravelEffect() {}

    public static void trigger() {
        remainingTicks = DURATION_TICKS;
    }

    public static void tick() {
        if (remainingTicks > 0) {
            remainingTicks--;
        }
    }

    public static void render(GuiGraphics graphics, float partialTick) {
        if (remainingTicks <= 0) {
            return;
        }
        int elapsed = DURATION_TICKS - remainingTicks;
        float intensity;
        if (elapsed < 8) {
            intensity = elapsed / 8.0F;
        } else if (elapsed < 13) {
            intensity = 1.0F;
        } else {
            intensity = Math.max(0.0F, (DURATION_TICKS - elapsed - partialTick) / 7.0F);
        }
        int alpha = Math.min(215, Math.round(intensity * 215));
        Minecraft client = Minecraft.getInstance();
        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();
        int color = (alpha << 24) | (elapsed < 13 ? 0x170321 : 0x030207);
        graphics.fill(0, 0, width, height, color);

        int bandAlpha = Math.round(intensity * 60);
        int bandColor = (bandAlpha << 24) | 0x8D21B4;
        int inset = Math.floorMod(elapsed * 9, Math.max(1, Math.min(width, height) / 5));
        int thickness = 2 + Math.round(intensity * 2);
        graphics.fill(inset, inset, width - inset, inset + thickness, bandColor);
        graphics.fill(width - inset - thickness, inset, width - inset, height - inset, bandColor);
        graphics.fill(inset, height - inset - thickness, width - inset, height - inset, bandColor);
        graphics.fill(inset, inset, inset + thickness, height - inset, bandColor);
    }

    public static void reset() {
        remainingTicks = 0;
    }
}
