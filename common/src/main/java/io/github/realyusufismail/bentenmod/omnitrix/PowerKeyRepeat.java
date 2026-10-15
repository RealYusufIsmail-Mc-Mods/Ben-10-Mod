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

/**
 * Repeats power requests every two ticks while the power key is held. Uses the world clock rather
 * than the player's tick count, which restarts when the player respawns or changes dimension.
 */
public final class PowerKeyRepeat {
    private static final long INTERVAL_TICKS = 2;
    private long lastSent = Long.MIN_VALUE / 2;

    /** Whether a request may be sent at {@code gameTime}; records it if so. */
    public boolean tryFire(long gameTime) {
        if (gameTime - lastSent < INTERVAL_TICKS && gameTime >= lastSent) {
            return false;
        }
        lastSent = gameTime;
        return true;
    }

    public void reset() {
        lastSent = Long.MIN_VALUE / 2;
    }
}
