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
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ZsSkayrEntity extends Monster implements GeoEntity {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.zs_skayr.idle");
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("animation.zs_skayr.fly");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.zs_skayr.attack");
    private static final RawAnimation SUMMON =
            RawAnimation.begin().thenPlay("animation.zs_skayr.summon");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent =
            new ServerBossEvent(Component.translatable("entity." + BenTenMod.MOD_ID + ".zs_skayr"),
                    BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
    private int boltCooldown = 35;
    private int summonCooldown = 600;
    private int blinkCooldown = 400;

    public ZsSkayrEntity(EntityType<? extends ZsSkayrEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.bossEvent.setDarkenScreen(true);
        setNoGravity(true);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 48)
            .add(Attributes.MOVEMENT_SPEED, 0.28)
            .add(Attributes.FLYING_SPEED, 0.6)
            .add(Attributes.ATTACK_DAMAGE, 12)
            .add(Attributes.ARMOR, 12)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .add(Attributes.MAX_HEALTH, 500);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.7, 24));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::movementAnimation)
            .triggerableAnim("attack", ATTACK)
            .triggerableAnim("summon", SUMMON));
    }

    private PlayState movementAnimation(AnimationState<ZsSkayrEntity> state) {
        return state.setAndContinue(state.isMoving() ? FLY : IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        this.bossEvent
            .setProgress(this.getMaxHealth() <= 0 ? 0 : this.getHealth() / this.getMaxHealth());
        if (--this.boltCooldown <= 0) {
            LivingEntity target = getTarget();
            if (target != null && target.isAlive() && distanceToSqr(target) <= 1_024.0
                    && hasLineOfSight(target)) {
                shootShadowBolt(target);
                this.boltCooldown = 35;
            } else {
                this.boltCooldown = 10;
            }
        }
        if (--this.summonCooldown <= 0) {
            summonPrisoners();
            this.summonCooldown = 600;
        }
        if (--this.blinkCooldown <= 0) {
            addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 40, 0, true, false));
            this.blinkCooldown = 400;
        }
    }

    private void shootShadowBolt(LivingEntity target) {
        Vec3 origin = new Vec3(getX(), getEyeY() - 0.2, getZ());
        CrabBoltEntity bolt = new CrabBoltEntity(origin.x, origin.y, origin.z, this.level(), this);
        bolt.setBaseDamage(8);
        bolt.shoot(target.getX() - origin.x, target.getEyeY() - origin.y, target.getZ() - origin.z,
                0.8F, 1.0F);
        this.level().addFreshEntity(bolt);
        triggerAnim("controller", "attack");
        this.level()
            .playSound(null, blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 0.8F,
                    0.65F);
    }

    private void summonPrisoners() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        int existing = (int) serverLevel
            .getEntitiesOfClass(NullVoidPrisonerEntity.class, getBoundingBox().inflate(32))
            .stream()
            .filter(Entity::isAlive)
            .count();
        if (existing >= 6) {
            return;
        }
        triggerAnim("controller", "summon");
        int count = Math.min(2 + this.random.nextInt(2), 6 - existing);
        for (int i = 0; i < count; i++) {
            NullVoidPrisonerEntity prisoner =
                    EntityTypesInit.NULL_VOID_PRISONER.get().create(serverLevel);
            if (prisoner == null) {
                continue;
            }
            prisoner.moveTo(getX() + (this.random.nextDouble() - 0.5) * 5.0, getY(),
                    getZ() + (this.random.nextDouble() - 0.5) * 5.0,
                    this.random.nextFloat() * 360.0F, 0.0F);
            if (getTarget() != null) {
                prisoner.setTarget(getTarget());
            }
            serverLevel.addFreshEntity(prisoner);
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
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
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bolt_cooldown", this.boltCooldown);
        tag.putInt("summon_cooldown", this.summonCooldown);
        tag.putInt("blink_cooldown", this.blinkCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.boltCooldown = tag.contains("bolt_cooldown") ? tag.getInt("bolt_cooldown") : 35;
        this.summonCooldown = tag.contains("summon_cooldown") ? tag.getInt("summon_cooldown") : 600;
        this.blinkCooldown = tag.contains("blink_cooldown") ? tag.getInt("blink_cooldown") : 400;
        setNoGravity(true);
        setPersistenceRequired();
    }
}
