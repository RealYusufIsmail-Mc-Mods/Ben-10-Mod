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
package io.github.realyusufismail.bentenmod.entity.ai;

import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class CrabWaterAvoidingRandomWalkingGoal extends RandomStrollGoal {
    protected final float probability;

    public CrabWaterAvoidingRandomWalkingGoal(CrabEntity kraab, double p_i47301_2_) {
        this(kraab, p_i47301_2_, 0.001F);
    }

    public CrabWaterAvoidingRandomWalkingGoal(CrabEntity kraab, double p_i47302_2_,
            float p_i47302_4_) {
        super(kraab, p_i47302_2_);
        this.probability = p_i47302_4_;
    }


}

