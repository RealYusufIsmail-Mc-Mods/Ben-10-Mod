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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
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

public class LepidopterranEntity extends Monster implements GeoEntity {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.lepidopterran.idle");
    private static final RawAnimation FLY =
            RawAnimation.begin().thenLoop("animation.lepidopterran.fly");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.lepidopterran.attack");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int spitCooldown = 50;
    private int diveCooldown;

    public LepidopterranEntity(EntityType<? extends LepidopterranEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        setNoGravity(true);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .add(Attributes.MOVEMENT_SPEED, 0.42)
            .add(Attributes.FLYING_SPEED, 0.9)
            .add(Attributes.ATTACK_DAMAGE, 4.0)
            .add(Attributes.MAX_HEALTH, 20.0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (this.spitCooldown > 0) {
            this.spitCooldown--;
        }
        if (this.diveCooldown > 0) {
            this.diveCooldown--;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive() || distanceToSqr(target) > 400.0) {
            return;
        }
        Vec3 aim = target.getBoundingBox().getCenter().subtract(getEyePosition());
        if (distanceToSqr(target) <= 196.0 && this.spitCooldown <= 0) {
            SlimeSpitEntity spit = new SlimeSpitEntity(EntityTypesInit.LEPIDOPTERAN_SLIME.get(),
                    this, this.level());
            spit.setPos(getX(), getEyeY(), getZ());
            spit.shoot(aim.x, aim.y, aim.z, 1.65F, 1.0F);
            this.level().addFreshEntity(spit);
            this.spitCooldown = 60;
            triggerAnim("controller", "attack");
        }
        if (distanceToSqr(target) > 9.0 && distanceToSqr(target) < 225.0) {
            Vec3 direction = target.position().subtract(position()).normalize();
            setDeltaMovement(getDeltaMovement().scale(0.75).add(direction.scale(0.13)));
            hasImpulse = true;
            if (this.diveCooldown <= 0 && distanceToSqr(target) < 49.0) {
                this.diveCooldown = 35;
                triggerAnim("controller", "attack");
            }
        }
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            triggerAnim("controller", "attack");
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::movementAnimation)
            .triggerableAnim("attack", ATTACK));
    }

    private PlayState movementAnimation(AnimationState<LepidopterranEntity> state) {
        return state.setAndContinue(state.isMoving() ? FLY : IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("spit_cooldown", this.spitCooldown);
        tag.putInt("dive_cooldown", this.diveCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.spitCooldown = tag.getInt("spit_cooldown");
        this.diveCooldown = tag.getInt("dive_cooldown");
        setNoGravity(true);
    }
}
