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

import net.minecraft.world.entity.Pose;

/** Shared pose-aware eye-height scaling for transformed players. */
public final class AlienEyeHeight {
    private static final float STANDING_RATIO = 0.9F;
    private static final float CROUCH_RATIO = 1.27F / 1.62F;
    private static final float LOW_POSE_RATIO = 0.4F / 1.62F;

    private AlienEyeHeight() {}

    public static float forPose(float hitboxHeight, Pose pose) {
        float ratio = switch (pose) {
            case CROUCHING -> CROUCH_RATIO;
            case SWIMMING, FALL_FLYING, SPIN_ATTACK -> LOW_POSE_RATIO;
            default -> 1.0F;
        };
        return Math.min(hitboxHeight, hitboxHeight * STANDING_RATIO * ratio);
    }
}
