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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

import java.util.EnumSet;

public class NullVoidPrisonerEntity extends Monster implements GeoEntity {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.null_void_prisoner.idle");
    private static final RawAnimation WALK =
            RawAnimation.begin().thenLoop("animation.null_void_prisoner.walk");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.null_void_prisoner.attack");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean fleeing;
    private boolean retreatedAtLowHealth;

    public NullVoidPrisonerEntity(EntityType<? extends NullVoidPrisonerEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 32)
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.ATTACK_DAMAGE, 5)
            .add(Attributes.MAX_HEALTH, 30);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FleeRegroupGoal());
        this.goalSelector.addGoal(1, new FlankGoal());
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (getHealth() > getMaxHealth() * 0.3F) {
            this.retreatedAtLowHealth = false;
        }
        if (this.fleeing) {
            setTarget(null);
        } else if (getTarget() instanceof Player player) {
            for (NullVoidPrisonerEntity ally : this.level()
                .getEntitiesOfClass(NullVoidPrisonerEntity.class, getBoundingBox().inflate(16.0),
                        other -> other != this && other.isAlive()
                                && distanceToSqr(other) <= 256.0)) {
                if (ally.getTarget() == null || !(ally.getTarget() instanceof Player)) {
                    ally.setTarget(player);
                }
            }
        } else if (getTarget() == null && this.random.nextInt(320) == 0
                && this.level().getNearestPlayer(this, 16.0) == null) {
            NullVoidPrisonerEntity rival = this.level()
                .getEntitiesOfClass(NullVoidPrisonerEntity.class, getBoundingBox().inflate(10.0),
                        other -> other != this && other.isAlive() && distanceToSqr(other) <= 100.0)
                .stream()
                .findFirst()
                .orElse(null);
            if (rival != null) {
                setTarget(rival);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::movementAnimation)
            .triggerableAnim("attack", ATTACK));
    }

    private PlayState movementAnimation(AnimationState<NullVoidPrisonerEntity> state) {
        return state.setAndContinue(state.isMoving() ? WALK : IDLE);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            triggerAnim("controller", "attack");
        }
        return hit;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private final class FlankGoal extends Goal {
        private int repathDelay;

        private FlankGoal() {
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = getTarget();
            return target instanceof Player && target.isAlive() && distanceToSqr(target) > 9.0
                    && distanceToSqr(target) < 256.0 && distanceToSqr(flankPosition(target)) > 2.25;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = getTarget();
            return target instanceof Player && target.isAlive() && distanceToSqr(target) > 9.0
                    && distanceToSqr(target) < 256.0 && distanceToSqr(flankPosition(target)) > 2.25;
        }

        @Override
        public void start() {
            this.repathDelay = 0;
        }

        @Override
        public void tick() {
            Player target = (Player) getTarget();
            getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.repathDelay-- <= 0) {
                Vec3 flank = flankPosition(target);
                getNavigation().moveTo(flank.x, flank.y, flank.z, 1.05);
                this.repathDelay = 12;
            }
        }

        private Vec3 flankPosition(LivingEntity target) {
            Vec3 forward = target.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
            if (forward.lengthSqr() < 0.001) {
                forward = new Vec3(0.0, 0.0, 1.0);
            }
            Vec3 side = new Vec3(-forward.z, 0.0, forward.x).scale(getId() % 2 == 0 ? 1.8 : -1.8);
            return target.position().subtract(forward.scale(1.6)).add(side);
        }
    }

    private final class FleeRegroupGoal extends Goal {
        private int ticks;

        private FleeRegroupGoal() {
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !retreatedAtLowHealth && getHealth() <= getMaxHealth() * 0.3F;
        }

        @Override
        public boolean canContinueToUse() {
            return this.ticks < 160 && isAlive();
        }

        @Override
        public void start() {
            this.ticks = 0;
            fleeing = true;
            setTarget(null);
        }

        @Override
        public void tick() {
            this.ticks++;
            if (this.ticks < 60) {
                LivingEntity threat = getLastHurtByMob();
                if (threat == null || !threat.isAlive()) {
                    threat = level().getNearestPlayer(NullVoidPrisonerEntity.this, 12.0);
                }
                if (threat != null && this.ticks % 10 == 1) {
                    Vec3 away = position().subtract(threat.position()).normalize();
                    getNavigation().moveTo(getX() + away.x * 8.0, getY(), getZ() + away.z * 8.0,
                            1.2);
                }
                return;
            }
            NullVoidPrisonerEntity packmate = level()
                .getEntitiesOfClass(NullVoidPrisonerEntity.class, getBoundingBox().inflate(24.0),
                        other -> other != NullVoidPrisonerEntity.this && other.isAlive())
                .stream()
                .min((left, right) -> Double.compare(distanceToSqr(left), distanceToSqr(right)))
                .orElse(null);
            if (packmate != null) {
                getNavigation().moveTo(packmate, 1.1);
            }
        }

        @Override
        public void stop() {
            fleeing = false;
            retreatedAtLowHealth = true;
        }
    }
}
