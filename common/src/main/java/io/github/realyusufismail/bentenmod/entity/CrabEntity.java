/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.entity;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.ai.CrabWaterAvoidingRandomWalkingGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class CrabEntity extends Monster implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> SHOOTING =
            SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent =
            new ServerBossEvent(Component.translatable("entity." + BenTenMod.MOD_ID + ".crab"),
                    BossBarColor.RED, BossBarOverlay.PROGRESS);
    private int shootAnimationTicks;

    public CrabEntity(EntityType<? extends CrabEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 40)
            .add(Attributes.MOVEMENT_SPEED, 0.27)
            .add(Attributes.ATTACK_DAMAGE, 12)
            .add(Attributes.ARMOR, 12)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
            .add(Attributes.MAX_HEALTH, 80);
    }

    public static <T extends Mob> boolean canKrabEntitySpawn(EntityType<T> entityType,
            ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false) {
            @Override
            public boolean canUse() {
                LivingEntity target = CrabEntity.this.getTarget();
                return target != null && CrabEntity.this.distanceToSqr(target) <= 16.0
                        && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                LivingEntity target = CrabEntity.this.getTarget();
                return target != null && CrabEntity.this.distanceToSqr(target) <= 16.0
                        && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 40, 12.0F) {
            @Override
            public boolean canUse() {
                LivingEntity target = CrabEntity.this.getTarget();
                return target != null && CrabEntity.this.distanceToSqr(target) > 16.0
                        && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                LivingEntity target = CrabEntity.this.getTarget();
                return target != null && CrabEntity.this.distanceToSqr(target) > 16.0
                        && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(3, new CrabWaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOTING, false);
    }

    public boolean isShooting() {
        return this.entityData.get(SHOOTING);
    }

    private void setShooting(boolean shooting) {
        this.entityData.set(SHOOTING, shooting);
    }

    @Override
    public int getHeadRotSpeed() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        updateSwingTime();
        if (this.level().isClientSide) {
            if (isShooting()) {
                shootAnimationTicks++;
            } else {
                shootAnimationTicks = 0;
            }
        } else {
            this.bossEvent
                .setProgress(this.getMaxHealth() <= 0 ? 0 : this.getHealth() / this.getMaxHealth());
            if (shootAnimationTicks > 0 && --shootAnimationTicks == 0) {
                setShooting(false);
            }
        }
    }

    public float getShootAnim(float partialTicks) {
        return (shootAnimationTicks + partialTicks) / 40.0F;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        Vec3 origin = position().add(0, getEyeHeight() * 0.75, 0).add(getLookAngle().scale(1.1));
        CrabBoltEntity bolt = new CrabBoltEntity(origin.x, origin.y, origin.z, level(), this);
        bolt.shoot(target.getX() - origin.x, target.getEyeY() - origin.y, target.getZ() - origin.z,
                0.8F, 1.0F);
        level().addFreshEntity(bolt);
        shootAnimationTicks = 40;
        setShooting(true);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void die(DamageSource source) {
        this.bossEvent.removeAllPlayers();
        super.die(source);
    }

    @Override
    public void remove(RemovalReason reason) {
        this.bossEvent.removeAllPlayers();
        super.remove(reason);
    }
}
