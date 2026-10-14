/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.omnitrix;

import io.github.realyusufismail.bentenmod.alien.AlienDefinition;
import io.github.realyusufismail.bentenmod.alien.power.AlienPower;
import io.github.realyusufismail.bentenmod.alien.power.PowerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Runs active alien powers and their server-side cooldowns. */
public final class OmnitrixPowerService {
    private static final Map<UUID, Long> COOLDOWNS = new HashMap<>();
    private static final Map<UUID, Long> FALL_PROTECTION = new HashMap<>();
    private static final Map<UUID, Long> PHASE_UNTIL = new HashMap<>();

    private OmnitrixPowerService() {}

    public static boolean use(ServerPlayer player) {
        AlienDefinition definition = OmnitrixService.activeDefinition(player).orElse(null);
        if (definition == null || definition.power().isEmpty()) {
            return false;
        }
        AlienPower power = definition.power().orElseThrow();
        long now = player.serverLevel().getServer().overworld().getGameTime();
        if (COOLDOWNS.getOrDefault(player.getUUID(), 0L) > now) {
            player.displayClientMessage(net.minecraft.network.chat.Component
                .translatable("message.bentenmod.power_recharging"), true);
            return false;
        }
        if (!activate(player, player.serverLevel(), power, now)) {
            return false;
        }
        COOLDOWNS.put(player.getUUID(), now + power.cooldownTicks());
        return true;
    }

    public static void tick(ServerPlayer player, long now) {
        UUID playerId = player.getUUID();
        Long fallUntil = FALL_PROTECTION.get(playerId);
        if (fallUntil != null) {
            if (now < fallUntil) {
                player.fallDistance = 0;
            } else {
                FALL_PROTECTION.remove(playerId);
            }
        }

        Long phaseUntil = PHASE_UNTIL.get(playerId);
        if (phaseUntil != null) {
            if (now < phaseUntil) {
                AABB bounds = player.getBoundingBox().inflate(48);
                player.serverLevel()
                    .getEntitiesOfClass(Mob.class, bounds, mob -> mob.getTarget() == player)
                    .forEach(mob -> mob.setTarget(null));
            } else {
                PHASE_UNTIL.remove(playerId);
            }
        }

        Long cooldownUntil = COOLDOWNS.get(playerId);
        if (cooldownUntil != null && now >= cooldownUntil) {
            COOLDOWNS.remove(playerId);
        }
    }

    public static void forget(ServerPlayer player) {
        UUID playerId = player.getUUID();
        COOLDOWNS.remove(playerId);
        FALL_PROTECTION.remove(playerId);
        PHASE_UNTIL.remove(playerId);
    }

    private static boolean activate(ServerPlayer player, ServerLevel level, AlienPower power,
            long now) {
        if (power.type().equals(PowerTypes.FIREBALL.id())) {
            fireball(player, level);
        } else if (power.type().equals(PowerTypes.GROUND_SLAM.id())) {
            groundSlam(player, level);
        } else if (power.type().equals(PowerTypes.DASH.id())) {
            dash(player, now);
        } else if (power.type().equals(PowerTypes.CRYSTAL_SHARD_VOLLEY.id())) {
            shardVolley(player, level);
        } else if (power.type().equals(PowerTypes.SLIME_SPIT.id())) {
            slimeSpit(player, level);
        } else if (power.type().equals(PowerTypes.POUNCE.id())) {
            pounce(player, level);
        } else if (power.type().equals(PowerTypes.LUNGE_BITE.id())) {
            lungeBite(player, level);
        } else if (power.type().equals(PowerTypes.EMP_PULSE.id())) {
            empPulse(player, level);
        } else if (power.type().equals(PowerTypes.PHASE.id())) {
            phase(player, now);
        } else if (power.type().equals(PowerTypes.ANALYSE.id())) {
            analyse(player, level);
        } else {
            return false;
        }
        return true;
    }

    private static void fireball(ServerPlayer player, ServerLevel level) {
        Vec3 look = player.getLookAngle();
        SafeSmallFireball fireball = new SafeSmallFireball(level, player, look);
        Vec3 start = player.getEyePosition().add(look.scale(0.7));
        fireball.setPos(start.x, start.y, start.z);
        level.addFreshEntity(fireball);
    }

    private static void groundSlam(ServerPlayer player, ServerLevel level) {
        AABB bounds = player.getBoundingBox().inflate(4.5);
        for (var target : level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class,
                bounds, entity -> entity.isAlive() && !protects(player, entity))) {
            target.hurt(player.damageSources().playerAttack(player), 8.0F);
            double pushX = player.getX() - target.getX();
            double pushZ = player.getZ() - target.getZ();
            if (pushX * pushX + pushZ * pushZ < 1.0e-4) {
                Vec3 look = player.getLookAngle();
                pushX = -look.x;
                pushZ = -look.z;
            }
            target.knockback(1.35, pushX, pushZ);
        }
        level.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.15, player.getZ(),
                36, 2.4, 0.08, 2.4, 0.02);
        level.sendParticles(ParticleTypes.CRIT, player.getX(), player.getY() + 0.2, player.getZ(),
                24, 1.8, 0.1, 1.8, 0.12);
    }

    private static void dash(ServerPlayer player, long now) {
        Vec3 look = player.getLookAngle();
        player.setDeltaMovement(look.scale(1.65).add(0, 0.18, 0));
        player.hurtMarked = true;
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60), player);
        FALL_PROTECTION.put(player.getUUID(), now + 60);
    }

    private static void shardVolley(ServerPlayer player, ServerLevel level) {
        Vec3 look = player.getLookAngle();
        Vec3 right = new Vec3(-look.z, 0, look.x);
        if (right.lengthSqr() < 1.0e-4) {
            right = new Vec3(1, 0, 0);
        } else {
            right = right.normalize();
        }
        for (double spread : new double[] {-0.22, 0, 0.22}) {
            Vec3 direction = look.add(right.scale(spread)).normalize();
            SafeArrow arrow = new SafeArrow(level, player);
            Vec3 start = player.getEyePosition().add(direction.scale(0.7));
            arrow.setPos(start.x, start.y, start.z);
            arrow.shoot(direction.x, direction.y, direction.z, 1.8F, 0.0F);
            arrow.setBaseDamage(4.0);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            level.addFreshEntity(arrow);
        }
    }

    private static void slimeSpit(ServerPlayer player, ServerLevel level) {
        Vec3 look = player.getLookAngle();
        SafeLlamaSpit spit = new SafeLlamaSpit(level, player);
        Vec3 start = player.getEyePosition().add(look.scale(0.7));
        spit.setPos(start.x, start.y, start.z);
        spit.shoot(look.x, look.y, look.z, 1.55F, 0.6F);
        level.addFreshEntity(spit);
    }

    private static void pounce(ServerPlayer player, ServerLevel level) {
        Vec3 look = player.getLookAngle();
        player.setDeltaMovement(look.scale(1.35).add(0, 0.52, 0));
        player.hurtMarked = true;
        for (Mob mob : level.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(12),
                Mob::isAlive)) {
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200), player);
        }
    }

    private static void lungeBite(ServerPlayer player, ServerLevel level) {
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        AABB bounds = player.getBoundingBox().expandTowards(look.scale(4.5)).inflate(1.2);
        var target = level
            .getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, bounds,
                    entity -> entity.isAlive() && !protects(player, entity))
            .stream()
            .filter(entity -> {
                Vec3 center = entity.getBoundingBox().getCenter();
                Vec3 toward = center.subtract(eye);
                return toward.lengthSqr() <= 20.25 && toward.normalize().dot(look) >= 0.78
                        && level
                            .clip(new ClipContext(eye, center, ClipContext.Block.COLLIDER,
                                    ClipContext.Fluid.NONE, player))
                            .getType() == HitResult.Type.MISS;
            })
            .min((first, second) -> Double.compare(first.distanceToSqr(player),
                    second.distanceToSqr(player)))
            .orElse(null);
        player.setDeltaMovement(look.scale(player.isInWater() ? 1.0 : 0.75).add(0, 0.12, 0));
        player.hurtMarked = true;
        if (target != null) {
            float damage = player.isInWater() || target.isInWater() ? 16.0F : 12.0F;
            target.hurt(player.damageSources().playerAttack(player), damage);
        }
    }

    private static void empPulse(ServerPlayer player, ServerLevel level) {
        AABB bounds = player.getBoundingBox().inflate(12);
        for (Mob mob : level.getEntitiesOfClass(Mob.class, bounds,
                target -> (target instanceof Monster || target instanceof AbstractGolem)
                        && !protects(player, target))) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3), player);
            mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100), player);
        }
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, player.getX(), player.getY() + 0.8,
                player.getZ(), 48, 4, 1.0, 4, 0.08);
    }

    private static void phase(ServerPlayer player, long now) {
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100), player);
        PHASE_UNTIL.put(player.getUUID(), now + 100);
    }

    private static void analyse(ServerPlayer player, ServerLevel level) {
        for (Mob mob : level.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(24),
                Mob::isAlive)) {
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200), player);
        }
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200), player);
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200), player);
    }

    private static boolean protects(ServerPlayer player, Entity entity) {
        if (entity == player || entity.isAlliedTo(player)) {
            return true;
        }
        return entity instanceof OwnableEntity ownable
                && player.getUUID().equals(ownable.getOwnerUUID());
    }

    private static final class SafeSmallFireball extends SmallFireball {
        private SafeSmallFireball(ServerLevel level, ServerPlayer owner, Vec3 direction) {
            super(level, owner, direction.x, direction.y, direction.z);
        }

        @Override
        protected boolean canHitEntity(Entity entity) {
            Entity owner = getOwner();
            return !(owner instanceof ServerPlayer player && protects(player, entity))
                    && super.canHitEntity(entity);
        }

        @Override
        protected void onHitBlock(BlockHitResult hit) {
            super.onHitBlock(hit);
            if (level().isClientSide
                    || !level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                return;
            }
            BlockPos firePos = hit.getBlockPos().relative(hit.getDirection());
            if (level().isEmptyBlock(firePos)) {
                level().setBlockAndUpdate(firePos, BaseFireBlock.getState(level(), firePos));
            }
        }
    }

    private static final class SafeArrow extends Arrow {
        private SafeArrow(ServerLevel level, ServerPlayer owner) {
            super(level, owner);
        }

        @Override
        protected boolean canHitEntity(Entity entity) {
            Entity owner = getOwner();
            return !(owner instanceof ServerPlayer player && protects(player, entity))
                    && super.canHitEntity(entity);
        }
    }

    private static final class SafeLlamaSpit extends LlamaSpit {
        private SafeLlamaSpit(ServerLevel level, ServerPlayer owner) {
            super(net.minecraft.world.entity.EntityType.LLAMA_SPIT, level);
            setOwner(owner);
        }

        @Override
        protected boolean canHitEntity(Entity entity) {
            Entity owner = getOwner();
            return !(owner instanceof ServerPlayer player && protects(player, entity))
                    && super.canHitEntity(entity);
        }

        @Override
        protected void onHitEntity(EntityHitResult result) {
            Entity owner = getOwner();
            if (owner instanceof ServerPlayer player && protects(player, result.getEntity())) {
                discard();
                return;
            }
            super.onHitEntity(result);
            if (owner instanceof ServerPlayer player && result
                .getEntity() instanceof net.minecraft.world.entity.LivingEntity target) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1),
                        player);
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 80), player);
            }
        }
    }
}
