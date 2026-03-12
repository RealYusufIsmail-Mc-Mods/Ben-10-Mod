/*
 * Copyright 2026 RealYusufIsmail.
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

public class FourArmsAbility implements AlienAbility {

    private static final UUID DAMAGE_MOD_UUID = UUID.fromString("a5c2a789-be6e-4a79-b5c1-9e8d7f1c3e2a");
    private static final UUID KNOCKBACK_MOD_UUID = UUID.fromString("b4d3c8e1-fa7b-5c90-a6d2-8e7c2f1b4d3b");
    private static final UUID SPEED_MOD_UUID = UUID.fromString("c3e4d9f2-ab8c-4d01-b7e3-9f8d3a2c5e4c");

    @Override
    public void onTransform(PlayerEntity player) {
        addModifier(
                player,
                Attributes.ATTACK_DAMAGE,
                DAMAGE_MOD_UUID,
                "fourarms_damage",
                15.0,
                AttributeModifier.Operation.ADDITION);
        addModifier(
                player,
                Attributes.KNOCKBACK_RESISTANCE,
                KNOCKBACK_MOD_UUID,
                "fourarms_knockback",
                1.0,
                AttributeModifier.Operation.ADDITION);
        addModifier(
                player,
                Attributes.MOVEMENT_SPEED,
                SPEED_MOD_UUID,
                "fourarms_speed",
                -0.02,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.DAMAGE_RESISTANCE, 60, 1);
        applyEffect(player, Effects.DAMAGE_BOOST, 60, 1);
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeModifier(player, Attributes.ATTACK_DAMAGE, DAMAGE_MOD_UUID);
        removeModifier(player, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_MOD_UUID);
        removeModifier(player, Attributes.MOVEMENT_SPEED, SPEED_MOD_UUID);
        removeEffect(player, Effects.DAMAGE_RESISTANCE);
        removeEffect(player, Effects.DAMAGE_BOOST);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        // Double knockback
        double dx = target.getX() - player.getX();
        double dz = target.getZ() - player.getZ();
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len > 0) {
            target.knockback(2.0f, -dx / len, -dz / len);
        }
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        // Ground slam — damage all entities in 5-block radius
        AxisAlignedBB aabb = player.getBoundingBox().inflate(5.0);
        player.level
                .getEntitiesOfClass(LivingEntity.class, aabb, e -> e != player)
                .forEach(entity -> {
                    entity.hurt(DamageSource.playerAttack(player), 10.0f);
                    double dx = entity.getX() - player.getX();
                    double dz = entity.getZ() - player.getZ();
                    double len = Math.sqrt(dx * dx + dz * dz);
                    if (len > 0) {
                        entity.knockback(3.0f, -dx / len, -dz / len);
                    }
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, 1.0, 0));
                });
    }

    @Override
    public float getWidth() {
        return 1.2f;
    }

    @Override
    public float getHeight() {
        return 2.5f;
    }

    private static void applyEffect(PlayerEntity player, Effect effect, int duration, int amplifier) {
        player.addEffect(new EffectInstance(effect, duration, amplifier, false, false, true));
    }

    private static void removeEffect(PlayerEntity player, Effect effect) {
        player.removeEffect(effect);
    }

    private static void addModifier(
            PlayerEntity player,
            net.minecraft.entity.ai.attributes.Attribute attribute,
            UUID uuid,
            String name,
            double value,
            AttributeModifier.Operation operation) {
        ModifiableAttributeInstance inst = player.getAttribute(attribute);
        if (inst != null && inst.getModifier(uuid) == null) {
            inst.addPermanentModifier(new AttributeModifier(uuid, name, value, operation));
        }
    }

    private static void removeModifier(
            PlayerEntity player, net.minecraft.entity.ai.attributes.Attribute attribute, UUID uuid) {
        ModifiableAttributeInstance inst = player.getAttribute(attribute);
        if (inst != null) {
            inst.removeModifier(uuid);
        }
    }
}
