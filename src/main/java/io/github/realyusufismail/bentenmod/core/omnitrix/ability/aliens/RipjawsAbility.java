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
package io.github.realyusufismail.bentenmod.core.omnitrix.ability.aliens;

import io.github.realyusufismail.bentenmod.core.omnitrix.ability.AlienAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

public class RipjawsAbility implements AlienAbility {

    @Override
    public void onTransform(PlayerEntity player) {
        applyEffect(player, Effects.WATER_BREATHING, 200, 0);
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.WATER_BREATHING, 60, 0);

        if (player.isInWater()) {
            applyEffect(player, Effects.CONDUIT_POWER, 60, 0);
            applyEffect(player, Effects.DOLPHINS_GRACE, 60, 0);
            applyEffect(player, Effects.DAMAGE_BOOST, 60, 1);
            removeEffect(player, Effects.MOVEMENT_SLOWDOWN);
            removeEffect(player, Effects.DIG_SLOWDOWN);
        } else {
            applyEffect(player, Effects.MOVEMENT_SLOWDOWN, 60, 1);
            applyEffect(player, Effects.DIG_SLOWDOWN, 60, 0);
            removeEffect(player, Effects.CONDUIT_POWER);
            removeEffect(player, Effects.DOLPHINS_GRACE);
        }
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.WATER_BREATHING);
        removeEffect(player, Effects.CONDUIT_POWER);
        removeEffect(player, Effects.DOLPHINS_GRACE);
        removeEffect(player, Effects.DAMAGE_BOOST);
        removeEffect(player, Effects.MOVEMENT_SLOWDOWN);
        removeEffect(player, Effects.DIG_SLOWDOWN);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        if (player.isInWater()) {
            target.hurt(DamageSource.playerAttack(player), 8.0f);
        }
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (!player.isInWater()) return;
        // Lunge forward in water
        Vector3d look = player.getLookAngle();
        player.setDeltaMovement(player.getDeltaMovement().add(look.scale(1.5)));
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    private static void applyEffect(PlayerEntity player, Effect effect, int duration, int amplifier) {
        player.addEffect(new EffectInstance(effect, duration, amplifier, false, false, true));
    }

    private static void removeEffect(PlayerEntity player, Effect effect) {
        player.removeEffect(effect);
    }
}
