/*
 * Copyright 2023 RealYusufIsmail.
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
package io.github.realyusufismail.bentenmod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OmnitrixConfig {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue TRANSFORMATION_DURATION_TICKS;
    public static final ForgeConfigSpec.IntValue COOLDOWN_DURATION_TICKS;
    public static final ForgeConfigSpec.DoubleValue REVERT_ON_LOW_HP_THRESHOLD;
    public static final ForgeConfigSpec.BooleanValue ALLOW_CREATIVE_BYPASS_COOLDOWN;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Omnitrix Configuration").push("omnitrix");

        TRANSFORMATION_DURATION_TICKS = builder
                .comment("Duration of alien transformation in ticks (default: 3600 = 3 minutes)")
                .defineInRange("transformationDurationTicks", 3600, 200, 72000);

        COOLDOWN_DURATION_TICKS = builder
                .comment("Cooldown duration after reverting in ticks (default: 600 = 30 seconds)")
                .defineInRange("cooldownDurationTicks", 600, 0, 36000);

        REVERT_ON_LOW_HP_THRESHOLD = builder
                .comment("Health percentage at which the Omnitrix auto-reverts (default: 0.25 = 25%)")
                .defineInRange("revertOnLowHpThreshold", 0.25, 0.0, 1.0);

        ALLOW_CREATIVE_BYPASS_COOLDOWN = builder
                .comment("Whether creative mode players bypass the cooldown")
                .define("allowCreativeBypassCooldown", true);

        builder.pop();
        SPEC = builder.build();
    }
}
