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
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class HeatblastAbility implements AlienAbility {

    @Override
    public void onTransform(PlayerEntity player) {
        player.setSecondsOnFire(0);
        player.fireImmune();
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.FIRE_RESISTANCE, 60, 0);
        applyEffect(player, Effects.REGENERATION, 60, 1);
        applyEffect(player, Effects.DAMAGE_BOOST, 60, 0);

        player.abilities.mayfly = true;
        player.fallDistance = 0;
        player.abilities.flying = player.abilities.flying; // preserve flying state

        // Spawn flame particles around player (server-side)
        if (!player.level.isClientSide && player.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) player.level;
            serverWorld.sendParticles(
                    ParticleTypes.FLAME,
                    player.getX(),
                    player.getY() + 1.0,
                    player.getZ(),
                    3,
                    0.3, 0.3, 0.3,
                    0.02);
        }
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.FIRE_RESISTANCE);
        removeEffect(player, Effects.REGENERATION);
        removeEffect(player, Effects.DAMAGE_BOOST);
        player.abilities.mayfly = player.isCreative();
        if (!player.isCreative()) {
            player.abilities.flying = false;
        }
        player.onUpdateAbilities();
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        target.setSecondsOnFire(5);
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        Vector3d look = player.getLookAngle();
        SmallFireballEntity fireball = new SmallFireballEntity(
                player.level, player,
                look.x * 1.5, look.y * 1.5, look.z * 1.5);
        fireball.setPos(player.getX() + look.x * 1.5, player.getEyeY(), player.getZ() + look.z * 1.5);
        player.level.addFreshEntity(fireball);
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
