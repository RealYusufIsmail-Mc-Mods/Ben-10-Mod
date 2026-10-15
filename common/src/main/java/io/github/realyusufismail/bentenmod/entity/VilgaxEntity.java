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

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VilgaxEntity extends Monster implements GeoEntity {
    private static final EntityDataAccessor<Integer> PHASE =
            SynchedEntityData.defineId(VilgaxEntity.class, EntityDataSerializers.INT);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.vilgax.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.vilgax.walk");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.vilgax.attack");
    private static final RawAnimation SUMMON =
            RawAnimation.begin().thenPlay("animation.vilgax.summon");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent =
            new ServerBossEvent(Component.translatable("entity." + BenTenMod.MOD_ID + ".vilgax"),
                    BossBarColor.RED, BossBarOverlay.PROGRESS);
    private int slamCooldown = 160;
    private int summonCooldown = 300;
    private int beaconSummonDelay;
    private int animatedPhase = 1;
    private int phaseSummonTicks;

    public VilgaxEntity(EntityType<? extends VilgaxEntity> type, Level level) {
        super(type, level);
        this.bossEvent.setDarkenScreen(true);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 48)
            .add(Attributes.MOVEMENT_SPEED, 0.28)
            .add(Attributes.ATTACK_DAMAGE, 18)
            .add(Attributes.ATTACK_KNOCKBACK, 3)
            .add(Attributes.ARMOR, 16)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .add(Attributes.MAX_HEALTH, 400);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PHASE, 1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::movementAnimation)
            .triggerableAnim("attack", ATTACK)
            .triggerableAnim("summon", SUMMON));
    }

    private PlayState movementAnimation(AnimationState<VilgaxEntity> state) {
        int phase = getPhase();
        if (phase != this.animatedPhase) {
            this.animatedPhase = phase;
            if (phase == 2) {
                this.phaseSummonTicks = 30;
            }
        }
        if (this.phaseSummonTicks > 0) {
            this.phaseSummonTicks--;
            return state.setAndContinue(SUMMON);
        }
        return state.setAndContinue(state.isMoving() ? WALK : IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    public void beginBeaconSummon(int delayTicks) {
        this.beaconSummonDelay = Math.max(1, delayTicks);
        setPersistenceRequired();
        setInvisible(true);
        setNoAi(true);
        this.bossEvent.setVisible(false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }

        if (this.beaconSummonDelay > 0) {
            if (--this.beaconSummonDelay == 0) {
                setInvisible(false);
                setNoAi(false);
                this.bossEvent.setVisible(true);
                triggerAnim("controller", "summon");
                this.level()
                    .playSound(null, blockPosition(), net.minecraft.sounds.SoundEvents.WITHER_SPAWN,
                            net.minecraft.sounds.SoundSource.HOSTILE, 1.0F, 0.8F);
            }
            return;
        }

        updatePhaseFromHealth();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        if (getPhase() == 1) {
            if (--this.slamCooldown <= 0) {
                groundSlam();
                this.slamCooldown = 160;
            }
        } else {
            applyPhaseTwoEffects();
            if (--this.summonCooldown <= 0) {
                summonDrones();
                this.summonCooldown = 300;
            }
        }
    }

    private void updatePhaseFromHealth() {
        if (getPhase() == 1 && this.getHealth() <= this.getMaxHealth() * 0.5F) {
            this.entityData.set(PHASE, 2);
            this.bossEvent.setColor(BossBarColor.PURPLE);
            applyPhaseTwoEffects();
        }
    }

    private void applyPhaseTwoEffects() {
        if (!hasEffect(MobEffects.MOVEMENT_SPEED)) {
            addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1_000_000, 1, true, false));
        }
        if (!hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
            addEffect(
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1_000_000, 0, true, false));
        }
    }

    private void groundSlam() {
        triggerAnim("controller", "attack");
        AABB area = getBoundingBox().inflate(8.0, 3.0, 8.0);
        for (LivingEntity target : this.level()
            .getEntitiesOfClass(LivingEntity.class, area, entity -> entity != this
                    && !(entity instanceof VilgaxDroneEntity) && entity.isAlive())) {
            Vec3 away = target.position().subtract(position()).multiply(1, 0, 1).normalize();
            if (away.lengthSqr() < 1.0E-5) {
                away = new Vec3(0, 0, 1);
            }
            target.hurt(damageSources().mobAttack(this), 8.0F);
            target.push(away.x * 1.6, 0.65, away.z * 1.6);
            target.hurtMarked = true;
        }
        this.level()
            .playSound(null, blockPosition(), net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE,
                    net.minecraft.sounds.SoundSource.HOSTILE, 1.0F, 0.75F);
    }

    private void summonDrones() {
        triggerAnim("controller", "summon");
        int alive = 0;
        if (this.level() instanceof ServerLevel serverLevel) {
            for (net.minecraft.world.entity.Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof VilgaxDroneEntity drone && drone.isAlive()
                        && drone.belongsTo(getUUID())) {
                    alive++;
                }
            }
        }
        int count = Math.min(3, Math.max(0, 6 - alive));
        for (int i = 0; i < count; i++) {
            VilgaxDroneEntity drone =
                    EntityTypesInit.VILGAX_DRONE_ENTITY.get().create(this.level());
            if (drone == null) {
                continue;
            }
            drone.setVilgaxId(getUUID());
            drone.setPos(getX() + (this.random.nextDouble() - 0.5) * 5.0,
                    getY() + 1.0 + this.random.nextDouble() * 2.0,
                    getZ() + (this.random.nextDouble() - 0.5) * 5.0);
            if (getTarget() != null) {
                drone.setTarget(getTarget());
            }
            this.level().addFreshEntity(drone);
        }
    }

    @Override
    public void die(DamageSource source) {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (net.minecraft.world.entity.Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof VilgaxDroneEntity drone && drone.belongsTo(getUUID())) {
                    drone.discard();
                }
            }
        }
        super.die(source);
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
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void remove(RemovalReason reason) {
        this.bossEvent.removeAllPlayers();
        super.remove(reason);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("phase", getPhase());
        tag.putInt("slam_cooldown", this.slamCooldown);
        tag.putInt("summon_cooldown", this.summonCooldown);
        tag.putInt("beacon_summon_delay", this.beaconSummonDelay);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        int phase = tag.getInt("phase");
        this.entityData.set(PHASE, phase == 2 ? 2 : 1);
        this.slamCooldown = tag.contains("slam_cooldown") ? tag.getInt("slam_cooldown") : 160;
        this.summonCooldown = tag.contains("summon_cooldown") ? tag.getInt("summon_cooldown") : 300;
        this.beaconSummonDelay = tag.getInt("beacon_summon_delay");
        if (getPhase() == 2) {
            this.bossEvent.setColor(BossBarColor.PURPLE);
        }
        if (this.beaconSummonDelay > 0) {
            setInvisible(true);
            setNoAi(true);
            this.bossEvent.setVisible(false);
        }
    }
}
