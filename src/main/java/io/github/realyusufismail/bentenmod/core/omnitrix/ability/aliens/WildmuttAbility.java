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
import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

public class WildmuttAbility implements AlienAbility {

    private static final UUID DAMAGE_MOD_UUID = UUID.fromString("f0h7c013-ej1f-9g34-e0h6-2i1g6j5f8h0f");

    @Override
    public void onTransform(PlayerEntity player) {
        applyEffect(player, Effects.NIGHT_VISION, 400, 0);
        addModifier(player, Attributes.ATTACK_DAMAGE, DAMAGE_MOD_UUID, "wildmutt_damage", 5.0, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.NIGHT_VISION, 400, 0);
        applyEffect(player, Effects.JUMP, 60, 2);
        applyEffect(player, Effects.MOVEMENT_SPEED, 60, 1);

        // Tracking: GLOWING on nearby living entities (server side)
        if (!player.level.isClientSide) {
            AxisAlignedBB aabb = player.getBoundingBox().inflate(32.0);
            player.level.getEntitiesOfClass(LivingEntity.class, aabb, e -> e != player)
                    .forEach(entity -> entity.addEffect(new EffectInstance(Effects.GLOWING, 40, 0, false, false)));
        }
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.NIGHT_VISION);
        removeEffect(player, Effects.JUMP);
        removeEffect(player, Effects.MOVEMENT_SPEED);
        removeModifier(player, Attributes.ATTACK_DAMAGE, DAMAGE_MOD_UUID);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        // Pounce bonus — 2x damage if airborne
        if (!player.isOnGround()) {
            target.hurt(DamageSource.playerAttack(player), target.getHealth() * 0.5f);
        }
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        // Howl — intimidate nearby hostile mobs
        AxisAlignedBB aabb = player.getBoundingBox().inflate(16.0);
        player.level.getEntitiesOfClass(MobEntity.class, aabb)
                .forEach(mob -> {
                    mob.addEffect(new EffectInstance(Effects.WEAKNESS, 200, 0));
                    mob.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 0));
                });
    }

    @Override
    public float getWidth() {
        return 0.8f;
    }

    @Override
    public float getHeight() {
        return 1.0f;
    }

    private static void applyEffect(PlayerEntity player, Effect effect, int duration, int amplifier) {
        player.addEffect(new EffectInstance(effect, duration, amplifier, false, false, true));
    }

    private static void removeEffect(PlayerEntity player, Effect effect) {
        player.removeEffect(effect);
    }

    private static void addModifier(PlayerEntity player, net.minecraft.entity.ai.attributes.Attribute attribute,
            UUID uuid, String name, double value, AttributeModifier.Operation operation) {
        ModifiableAttributeInstance inst = player.getAttribute(attribute);
        if (inst != null && inst.getModifier(uuid) == null) {
            inst.addPermanentModifier(new AttributeModifier(uuid, name, value, operation));
        }
    }

    private static void removeModifier(PlayerEntity player, net.minecraft.entity.ai.attributes.Attribute attribute, UUID uuid) {
        ModifiableAttributeInstance inst = player.getAttribute(attribute);
        if (inst != null) {
            inst.removeModifier(uuid);
        }
    }
}
