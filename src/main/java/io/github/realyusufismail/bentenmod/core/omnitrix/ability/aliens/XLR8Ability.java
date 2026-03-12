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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class XLR8Ability implements AlienAbility {

    @Override
    public void onTransform(PlayerEntity player) {}

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.MOVEMENT_SPEED, 60, 9); // SPEED X
        applyEffect(player, Effects.REGENERATION, 60, 1);
        applyEffect(player, Effects.DAMAGE_RESISTANCE, 60, 0);

        // Spawn speed trail particles behind player
        if (!player.level.isClientSide && player.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) player.level;
            Vector3d motion = player.getDeltaMovement();
            double hDistSq = motion.x * motion.x + motion.z * motion.z;
            if (hDistSq > 0.01) {
                serverWorld.sendParticles(
                        ParticleTypes.CLOUD,
                        player.getX() - motion.x * 2,
                        player.getY() + 0.5,
                        player.getZ() - motion.z * 2,
                        2, 0.1, 0.1, 0.1, 0.01);
            }
        }
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.MOVEMENT_SPEED);
        removeEffect(player, Effects.REGENERATION);
        removeEffect(player, Effects.DAMAGE_RESISTANCE);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        // Momentum-based bonus damage
        Vector3d movement = player.getDeltaMovement();
        double speedSq = movement.x * movement.x + movement.z * movement.z;
        if (speedSq > 0.1) {
            float bonus = (float) Math.min(speedSq * 5.0, 8.0);
            target.hurt(net.minecraft.util.DamageSource.playerAttack(player), bonus);
        }
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        // Dash 10 blocks forward in look direction
        Vector3d look = player.getLookAngle().normalize();
        Vector3d origin = player.position();
        Vector3d dest = origin.add(look.scale(10));

        // Simple teleport (no clip check for simplicity)
        player.teleportTo(dest.x, dest.y, dest.z);

        // Spawn particles along path
        if (player.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) player.level;
            for (int i = 0; i <= 10; i++) {
                Vector3d pos = origin.add(look.scale(i));
                serverWorld.sendParticles(ParticleTypes.CLOUD,
                        pos.x, pos.y + 1.0, pos.z,
                        1, 0, 0, 0, 0);
            }
        }
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
