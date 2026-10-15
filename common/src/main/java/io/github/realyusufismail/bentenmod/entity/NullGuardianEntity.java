package io.github.realyusufismail.bentenmod.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NullGuardianEntity extends Monster implements GeoEntity {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.null_guardian.idle");
    private static final RawAnimation FLY =
            RawAnimation.begin().thenLoop("animation.null_guardian.fly");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.null_guardian.attack");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int beamTicks;
    private int beamCooldown;
    private BlockPos patrolOrigin;

    public NullGuardianEntity(EntityType<? extends NullGuardianEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        setNoGravity(true);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .add(Attributes.MOVEMENT_SPEED, 0.28)
            .add(Attributes.FLYING_SPEED, 0.5)
            .add(Attributes.ATTACK_DAMAGE, 8.0)
            .add(Attributes.ARMOR, 8.0)
            .add(Attributes.MAX_HEALTH, 60.0);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DelayedMeleeAttackGoal());
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.75));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        super.registerGoals();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && !this.level().isClientSide
                && source.getEntity() instanceof LivingEntity attacker) {
            startBeam(attacker);
            if (attacker instanceof Player) {
                for (NullGuardianEntity ally : this.level()
                    .getEntitiesOfClass(NullGuardianEntity.class, getBoundingBox().inflate(24.0),
                            other -> other != this && other.isAlive())) {
                    ally.startBeam(attacker);
                }
            }
        }
        return hurt;
    }

    private void startBeam(LivingEntity target) {
        setTarget(target);
        if (this.beamTicks <= 0 && this.beamCooldown <= 0) {
            this.beamTicks = 40;
            triggerAnim("controller", "attack");
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (this.patrolOrigin == null) {
            this.patrolOrigin = blockPosition();
            restrictTo(this.patrolOrigin, 16);
        }
        if (this.beamCooldown > 0) {
            this.beamCooldown--;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            this.beamTicks = 0;
            return;
        }
        if (this.beamTicks <= 0 && this.beamCooldown <= 0 && distanceToSqr(target) <= 400.0) {
            startBeam(target);
        }
        if (this.beamTicks > 0) {
            pullWithBeam(target);
            this.beamTicks--;
            if (this.beamTicks == 0) {
                this.beamCooldown = 100;
            }
        }
    }

    private void pullWithBeam(LivingEntity target) {
        Vec3 start = getEyePosition();
        Vec3 end = target.getBoundingBox().getCenter();
        Vec3 pull = start.subtract(end).normalize().scale(0.2).add(0.0, 0.06, 0.0);
        target.setDeltaMovement(target.getDeltaMovement().scale(0.55).add(pull));
        target.hasImpulse = true;
        target.hurtMarked = true;
        if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 3 == 0) {
            Vec3 beam = end.subtract(start);
            for (int i = 1; i <= 6; i++) {
                Vec3 point = start.add(beam.scale(i / 7.0));
                serverLevel.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1,
                        0.015, 0.015, 0.015, 0.0);
            }
        }
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        if (this.beamTicks > 0) {
            return false;
        }
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

    private PlayState movementAnimation(AnimationState<NullGuardianEntity> state) {
        return state.setAndContinue(state.isMoving() ? FLY : IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private final class DelayedMeleeAttackGoal extends MeleeAttackGoal {
        private DelayedMeleeAttackGoal() {
            super(NullGuardianEntity.this, 1.0, false);
        }

        @Override
        public boolean canUse() {
            return beamTicks <= 0 && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return beamTicks <= 0 && super.canContinueToUse();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("beam_ticks", this.beamTicks);
        tag.putInt("beam_cooldown", this.beamCooldown);
        if (this.patrolOrigin != null) {
            tag.putLong("patrol_origin", this.patrolOrigin.asLong());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.beamTicks = tag.getInt("beam_ticks");
        this.beamCooldown = tag.getInt("beam_cooldown");
        this.patrolOrigin =
                tag.contains("patrol_origin") ? BlockPos.of(tag.getLong("patrol_origin")) : null;
        if (this.patrolOrigin != null) {
            restrictTo(this.patrolOrigin, 16);
        }
        setNoGravity(true);
    }
}
