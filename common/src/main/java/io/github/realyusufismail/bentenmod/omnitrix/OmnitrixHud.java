package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

/** Client countdown and the final-ten-second warning. */
public final class OmnitrixHud {
    private static int lastBeepSecond = -1;

    private OmnitrixHud() {}

    public static void tick(Minecraft client) {
        if (client.player == null || client.level == null) {
            lastBeepSecond = -1;
            return;
        }
        OmnitrixSnapshot state = ClientOmnitrixState.get(client.player.getUUID());
        if (state == null || state.currentAlien() == null || state.timeoutDisabled()) {
            lastBeepSecond = -1;
            return;
        }
        int seconds = remainingSeconds(state.expiresAt() - client.level.getGameTime());
        if (seconds < 1 || seconds > 10) {
            lastBeepSecond = -1;
            return;
        }
        if (seconds != lastBeepSecond) {
            client.level.playLocalSound(client.player.blockPosition(),
                    SoundEvents.NOTE_BLOCK_PLING.value(), SoundSource.PLAYERS, 0.35F,
                    1.55F + (10 - seconds) * 0.04F, false);
            lastBeepSecond = seconds;
        }
    }

    public static void render(GuiGraphics graphics, Minecraft client) {
        if (client.player == null || client.level == null) {
            return;
        }
        OmnitrixSnapshot state = ClientOmnitrixState.get(client.player.getUUID());
        if (state == null || state.currentAlien() == null || state.timeoutDisabled()) {
            return;
        }
        long remaining = state.expiresAt() - client.level.getGameTime();
        if (remaining <= 0) {
            return;
        }
        int seconds = remainingSeconds(remaining);
        int color = seconds <= 10 ? 0xFFFF5555 : 0xFF55FF55;
        int x = client.getWindow().getGuiScaledWidth() / 2 + 96;
        int y = client.getWindow().getGuiScaledHeight() - 22;
        graphics.drawString(client.font, Component.literal(formatTicks(remaining)), x, y, color,
                true);
    }

    public static void reset() {
        lastBeepSecond = -1;
    }

    public static String formatTicks(long ticks) {
        int seconds = remainingSeconds(ticks);
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }

    private static int remainingSeconds(long ticks) {
        return (int) Math.min(Integer.MAX_VALUE, Math.max(0, (ticks + 19) / 20));
    }
}
