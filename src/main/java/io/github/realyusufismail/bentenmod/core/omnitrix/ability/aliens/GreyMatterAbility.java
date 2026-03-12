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
import net.minecraft.util.math.AxisAlignedBB;

public class GreyMatterAbility implements AlienAbility {

    @Override
    public void onTransform(PlayerEntity player) {
        player.refreshDimensions();
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.MOVEMENT_SPEED, 60, 0);
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.MOVEMENT_SPEED);
        player.refreshDimensions();
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        // Minimal damage — Grey Matter is not a fighter
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        // Analyze — give GLOWING to all entities within 16 blocks
        AxisAlignedBB aabb = player.getBoundingBox().inflate(16.0);
        player.level.getEntitiesOfClass(LivingEntity.class, aabb, e -> e != player)
                .forEach(entity -> entity.addEffect(new EffectInstance(Effects.GLOWING, 200, 0, false, true)));
    }

    @Override
    public float getWidth() {
        return 0.3f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    private static void applyEffect(PlayerEntity player, Effect effect, int duration, int amplifier) {
        player.addEffect(new EffectInstance(effect, duration, amplifier, false, false, true));
    }

    private static void removeEffect(PlayerEntity player, Effect effect) {
        player.removeEffect(effect);
    }
}
