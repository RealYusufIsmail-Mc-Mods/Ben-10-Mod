package io.github.realyusufismail.bentenmod.omnitrix;

import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.client.alien.AlienFlightEffects;
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
        AlienFlightEffects.tick(client);
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
        var definition = Aliens.DATA.get(state.currentAlien()).orElse(null);
        if (definition == null || definition.power().isEmpty()
                || definition.power().orElseThrow().cooldownTicks() <= 20) {
            return;
        }
        int total = definition.power().orElseThrow().cooldownTicks();
        long cooldown = Math.max(0, state.powerCooldownUntil() - client.level.getGameTime());
        if (cooldown <= 0) {
            return;
        }
        int barWidth = 56;
        int barX = x;
        int barY = y + 11;
        int fillWidth = (int) Math.ceil(barWidth * Math.min(1.0, cooldown / (double) total));
        graphics.fill(barX, barY, barX + barWidth, barY + 3, 0xB0203020);
        graphics.fill(barX + barWidth - fillWidth, barY, barX + barWidth, barY + 3, 0xFF55FF55);
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
