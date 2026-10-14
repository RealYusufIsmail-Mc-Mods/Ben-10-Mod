/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
