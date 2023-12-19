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
package io.github.realyusufismail.bentenmod.entity.ai;

import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class VilgaxAttackGoal extends MeleeAttackGoal {
    private final VilgaxEntity vilgax;
    private int raiseArmTicks;

    public VilgaxAttackGoal(VilgaxEntity vilgax, double p_i46803_2_, boolean p_i46803_4_) {
        super(vilgax, p_i46803_2_, p_i46803_4_);
        this.vilgax = vilgax;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    public void stop() {
        super.stop();
        this.vilgax.setAggressive(false);
    }

    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        this.vilgax.setAggressive(
                this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);
    }
}
