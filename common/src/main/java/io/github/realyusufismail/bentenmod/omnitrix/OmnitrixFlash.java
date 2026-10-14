package io.github.realyusufismail.bentenmod.omnitrix;

/** Client-side flash envelope, triggered only when a server snapshot changes forms. */
public final class OmnitrixFlash {
    private static final int DURATION_TICKS = 12;
    private static int remainingTicks;
    private static boolean timeoutFlash;

    private OmnitrixFlash() {}

    public static void trigger() {
        trigger(false);
    }

    public static void trigger(boolean timeout) {
        remainingTicks = DURATION_TICKS;
        timeoutFlash = timeout;
    }

    public static void reset() {
        remainingTicks = 0;
        timeoutFlash = false;
    }

    public static void tick() {
        if (remainingTicks > 0) {
            remainingTicks--;
            if (remainingTicks == 0) {
                timeoutFlash = false;
            }
        }
    }

    public static boolean isTimeoutFlash() {
        return timeoutFlash;
    }

    public static float alpha(float partialTick) {
        return Math.max(0, Math.min(1, (remainingTicks - partialTick) / DURATION_TICKS));
    }
}
