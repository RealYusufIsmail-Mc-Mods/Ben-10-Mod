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
import net.minecraft.util.math.AxisAlignedBB;

public class GhostfreakAbility implements AlienAbility {

    @Override
    public void onTransform(PlayerEntity player) {
        player.setInvisible(true);
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.INVISIBILITY, 60, 0);
        player.setInvisible(true);

        // Scare nearby mobs
        AxisAlignedBB aabb = player.getBoundingBox().inflate(8.0);
        player.level.getEntitiesOfClass(LivingEntity.class, aabb, e -> e != player)
                .forEach(entity -> {
                    entity.addEffect(new EffectInstance(Effects.WEAKNESS, 40, 0, false, false));
                    entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 40, 0, false, false));
                });

        // Phasing — drain timer faster while sneaking
        if (player.isCrouching()) {
            player.noPhysics = true;
        } else {
            player.noPhysics = false;
        }
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.INVISIBILITY);
        player.setInvisible(false);
        player.noPhysics = false;
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        target.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0));
        target.hurt(DamageSource.playerAttack(player), 4.0f);
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        // Toggle phasing
        player.noPhysics = !player.noPhysics;
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
