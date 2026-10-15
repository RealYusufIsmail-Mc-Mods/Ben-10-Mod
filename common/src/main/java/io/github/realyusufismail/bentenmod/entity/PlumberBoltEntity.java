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
package io.github.realyusufismail.bentenmod.entity;

import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

/** Straight-flying, fixed-damage Plumber energy projectile. */
public final class PlumberBoltEntity extends ThrowableProjectile {
    public static final float DAMAGE = 6.0F;
    public static final int LIFETIME_TICKS = 40;
    public static final float SPEED = 3.8F;
    public static final int ITEM_COOLDOWN_TICKS = 8;
    private static final DustParticleOptions TRAIL =
            new DustParticleOptions(new Vector3f(0.08F, 0.95F, 0.78F), 0.8F);

    public PlumberBoltEntity(EntityType<? extends PlumberBoltEntity> type, Level level) {
        super(type, level);
        setNoGravity(true);
    }

    public PlumberBoltEntity(Level level, LivingEntity owner) {
        this(EntityTypesInit.PLUMBER_BOLT.get(), level);
        setOwner(owner);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
        setNoGravity(true);
        if (level().isClientSide && tickCount < LIFETIME_TICKS) {
            level().addParticle(TRAIL, getX(), getY(), getZ(), 0, 0, 0);
        } else if (!level().isClientSide && tickCount >= LIFETIME_TICKS) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        Entity owner = getOwner();
        Entity target = hit.getEntity();
        if (!level().isClientSide && owner instanceof ServerPlayer player
                && !protects(player, target) && target instanceof LivingEntity living) {
            damageTarget(player, living);
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, getX(), getY(), getZ(), 12,
                        0.18, 0.18, 0.18, 0.08);
                serverLevel.sendParticles(TRAIL, getX(), getY(), getZ(), 8, 0.12, 0.12, 0.12, 0.04);
            }
            level().playSound(null, blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT,
                    SoundSource.PLAYERS, 0.35F, 1.9F);
        }
        discard();
    }

    public void damageTarget(ServerPlayer owner, LivingEntity target) {
        if (!protects(owner, target)) {
            target.hurt(level().damageSources().mobProjectile(this, owner), DAMAGE);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hit) {
        if (!level().isClientSide) {
            level().playSound(null, blockPosition(), SoundEvents.LIGHTNING_BOLT_IMPACT,
                    SoundSource.PLAYERS, 0.25F, 2.0F);
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, getX(), getY(), getZ(), 8,
                        0.12, 0.12, 0.12, 0.06);
            }
        }
        discard();
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        Entity owner = getOwner();
        return !(owner instanceof ServerPlayer player && protects(player, entity))
                && super.canHitEntity(entity);
    }

    private static boolean protects(ServerPlayer player, Entity target) {
        if (target == player || target.isAlliedTo(player)) {
            return true;
        }
        return target instanceof OwnableEntity ownable
                && player.getUUID().equals(ownable.getOwnerUUID());
    }
}
