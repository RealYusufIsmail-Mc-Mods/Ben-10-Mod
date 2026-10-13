package io.github.realyusufismail.bentenmod.omnitrix;

/** Client-side flash envelope, triggered only when a server snapshot changes forms. */
public final class OmnitrixFlash {
    private static final int DURATION_TICKS = 12;
    private static int remainingTicks;

    private OmnitrixFlash() {}

    public static void trigger() {
        remainingTicks = DURATION_TICKS;
    }

    public static void reset() {
        remainingTicks = 0;
    }

    public static void tick() {
        if (remainingTicks > 0) {
            remainingTicks--;
        }
    }

    public static float alpha(float partialTick) {
        return Math.max(0, Math.min(1, (remainingTicks - partialTick) / DURATION_TICKS));
    }
}
