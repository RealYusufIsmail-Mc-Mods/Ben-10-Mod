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
package io.github.realyusufismail.bentenmod.entity;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class KraabEntity extends CreatureEntity {

    private static final DataParameter<Boolean> SHOOTING =
            EntityDataManager.defineId(KraabEntity.class, DataSerializers.BOOLEAN);

    private float shootTimer;

    public KraabEntity(EntityType<? extends KraabEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MAX_HEALTH, 25);
    }

    public static boolean canKrabEntitySpawn(
            EntityType<KraabEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ShootGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOTING, false);
    }

    public boolean isShooting() {
        return entityData.get(SHOOTING);
    }

    private void setShooting(boolean b) {
        entityData.set(SHOOTING, b);
    }

    @Override
    public int getHeadRotSpeed() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        updateSwingTime();

        if (level.isClientSide)
            if (isShooting()) shootTimer++;
            else shootTimer = 0;
    }

    public float getShootAnim(float partialTicks) {
        return (shootTimer + partialTicks) / ShootGoal.MAX_TIMER;
    }

    public void shoot(LivingEntity target) {
        Vector3d direction = Vector3d.directionFromRotation(0, yRot).yRot((float) Math.PI / 2);
        Vector3d pos = position().add(0, 1, 0).add(direction);
        KraabBoltEntity bolt = new KraabBoltEntity(pos.x, pos.y, pos.z, level, this);
        bolt.shoot(target.getX() - pos.x, target.getEyeY() - 1 - pos.y, target.getZ() - pos.z, 0.75f, 6);
        level.addFreshEntity(bolt);
    }

    public static class ShootGoal extends Goal {

        private static final int MAX_COOLDOWN = 60;
        private static final int MAX_DISTANCE = 100;
        private static final int MAX_TIMER = 40;

        private final KraabEntity entity;
        private int cooldown, attackTimer;

        public ShootGoal(KraabEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = entity.getTarget();
            return cooldown-- < 0 && entity.getTarget() != null && entity.distanceToSqr(target) < MAX_DISTANCE;
        }

        @Override
        public void start() {
            attackTimer = MAX_TIMER;
            entity.setShooting(true);
        }

        @Override
        public void stop() {
            cooldown = MAX_COOLDOWN;
            attackTimer = MAX_TIMER;
            entity.setShooting(false);
        }

        @Override
        public void tick() {
            LivingEntity target = entity.getTarget();
            attackTimer--;
            entity.lookAt(target, 30, 30);
            entity.getLookControl().setLookAt(entity, 30, 30);

            if (attackTimer < 0) {
                cooldown = MAX_COOLDOWN;
                entity.shoot(target);
            }
        }
    }
}
