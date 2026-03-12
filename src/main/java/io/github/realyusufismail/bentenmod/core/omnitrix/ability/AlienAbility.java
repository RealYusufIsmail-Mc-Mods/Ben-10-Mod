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
package io.github.realyusufismail.bentenmod.core.omnitrix.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface AlienAbility {

    /** Called once when the player transforms into this alien. */
    void onTransform(PlayerEntity player);

    /** Called every tick while the player is transformed. */
    void onTick(PlayerEntity player);

    /** Called once when the player reverts back to human form. */
    void onRevert(PlayerEntity player);

    /** Called when the transformed player lands a melee hit. */
    void onAttack(PlayerEntity player, LivingEntity target);

    /** Called when the player right-clicks while transformed (special ability). */
    void onRightClick(PlayerEntity player);

    /** Custom hitbox width when transformed. */
    float getWidth();

    /** Custom hitbox height when transformed. */
    float getHeight();
}
