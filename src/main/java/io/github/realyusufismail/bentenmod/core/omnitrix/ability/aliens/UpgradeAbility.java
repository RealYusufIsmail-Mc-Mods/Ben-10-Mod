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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class UpgradeAbility implements AlienAbility {

    private static final UUID ARMOR_MOD_UUID = UUID.fromString("a1b8d124-fa2a-4a45-f1b7-3a2a7b6a9b1a");
    private static final UUID DAMAGE_MOD_UUID = UUID.fromString("a2b9e235-aa3a-4b56-a2b8-4a3b8a7a0b2a");

    private static final net.minecraft.item.Item[] TECH_DROPS = {
        Items.REDSTONE, Items.REPEATER, Items.COMPARATOR, Items.REDSTONE_TORCH
    };

    @Override
    public void onTransform(PlayerEntity player) {
        addModifier(
                player, Attributes.ARMOR, ARMOR_MOD_UUID, "upgrade_armor", 5.0, AttributeModifier.Operation.ADDITION);
        addModifier(
                player,
                Attributes.ATTACK_DAMAGE,
                DAMAGE_MOD_UUID,
                "upgrade_damage",
                3.0,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onTick(PlayerEntity player) {
        applyEffect(player, Effects.DAMAGE_RESISTANCE, 60, 0);

        // Circuit-like particles
        if (!player.level.isClientSide && player.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) player.level;
            serverWorld.sendParticles(
                    ParticleTypes.END_ROD, player.getX(), player.getY() + 1.0, player.getZ(), 1, 0.3, 0.3, 0.3, 0.01);
        }
    }

    @Override
    public void onRevert(PlayerEntity player) {
        removeEffect(player, Effects.DAMAGE_RESISTANCE);
        removeModifier(player, Attributes.ARMOR, ARMOR_MOD_UUID);
        removeModifier(player, Attributes.ATTACK_DAMAGE, DAMAGE_MOD_UUID);
    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {
        // 20% chance to drop a random redstone item
        if (player.level.random.nextFloat() < 0.2f) {
            net.minecraft.item.Item drop = TECH_DROPS[player.level.random.nextInt(TECH_DROPS.length)];
            target.spawnAtLocation(new ItemStack(drop));
        }
    }

    @Override
    public void onRightClick(PlayerEntity player) {
        if (player.level.isClientSide) return;
        // Shoot tech beam (snowball placeholder)
        Vector3d look = player.getLookAngle();
        net.minecraft.entity.projectile.SnowballEntity beam =
                new net.minecraft.entity.projectile.SnowballEntity(player.level, player);
        beam.setPos(player.getX() + look.x, player.getEyeY(), player.getZ() + look.z);
        beam.shoot(look.x, look.y, look.z, 2.0f, 0.0f);
        player.level.addFreshEntity(beam);
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
