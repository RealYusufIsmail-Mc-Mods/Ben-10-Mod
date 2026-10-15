package io.github.realyusufismail.bentenmod.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;
import java.util.WeakHashMap;

/** Sends one coloured boss line nearby while enforcing a per-boss chat cooldown. */
public final class BossChatter {
    private static final long COOLDOWN_TICKS = 6 * 20L;
    private static final Map<LivingEntity, Long> NEXT_LINE_AT = new WeakHashMap<>();

    private BossChatter() {}

    public static boolean sayVariant(LivingEntity boss, ChatFormatting nameColor, String dialogueId,
            int variants, Object... arguments) {
        if (variants < 1) {
            return false;
        }
        int variant = boss.getRandom().nextInt(variants) + 1;
        String key = "dialogue.bentenmod." + dialogueId + "." + variant;
        return say(boss, nameColor, key, arguments);
    }

    public static boolean say(LivingEntity boss, ChatFormatting nameColor, String dialogueKey,
            Object... arguments) {
        if (!(boss.level() instanceof ServerLevel level)) {
            return false;
        }
        long now = level.getGameTime();
        synchronized (NEXT_LINE_AT) {
            if (NEXT_LINE_AT.getOrDefault(boss, 0L) > now) {
                return false;
            }
            NEXT_LINE_AT.put(boss, now + COOLDOWN_TICKS);
        }
        Component name = boss.getDisplayName().copy().withStyle(nameColor);
        Component line =
                Component.translatable(dialogueKey, arguments).withStyle(ChatFormatting.WHITE);
        Component message =
                Component.empty().append(name).append(Component.literal(": ")).append(line);
        for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(boss) <= 64.0 * 64.0) {
                player.sendSystemMessage(message);
            }
        }
        return true;
    }
}
