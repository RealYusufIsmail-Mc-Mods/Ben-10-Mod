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
import net.minecraft.util.math.vector.Vector3d;

public class DiamondheadAbility implements AlienAbility {

    private static final UUID ARMOR_MOD_UUID = UUID.fromString("d2f5a891-ca9d-4e12-c8f4-0b9e4a3d6f5d");
    private static final UUID TOUGHNESS_MOD_UUID = UUID.fromString("e1a6b902-da0e-4f23-d9a5-1a0f5b4e7a6e");

    @Override
    public void onTransform(PlayerEntity player) {
        addModifier(
                player,
                Attributes.ARMOR,
                ARMOR_MOD_UUID,
                "diamondhead_armor",
                20.0,
                AttributeModifier.Operation.ADDITION);
        addModifier(
                player,
                Attributes.ARMOR_TOUGHNESS,
                TOUGHNESS_MOD_UUID,
                "diamondhead_toughness",
                8.0,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.DAMAGE_RESISTANCE, 60, 2);
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeModifier(player, Attributes.ARMOR, ARMOR_MOD_UUID);
        removeModifier(player, Attributes.ARMOR_TOUGHNESS, TOUGHNESS_MOD_UUID);
        removeEffect(player, Effects.DAMAGE_RESISTANCE);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        target.hurt(DamageSource.playerAttack(player), 5.0f);
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        // Shoot crystal shard projectile — using snowball as placeholder
        Vector3d look = player.getLookAngle();
        net.minecraft.entity.projectile.SnowballEntity shard =
                new net.minecraft.entity.projectile.SnowballEntity(player.level, player);
        shard.setPos(player.getX() + look.x, player.getEyeY(), player.getZ() + look.z);
        shard.shoot(look.x, look.y, look.z, 2.0f, 0.0f);
        player.level.addFreshEntity(shard);
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
