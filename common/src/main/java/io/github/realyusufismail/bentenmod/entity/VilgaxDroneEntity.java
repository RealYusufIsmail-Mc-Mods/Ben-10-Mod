package io.github.realyusufismail.bentenmod.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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

import java.util.UUID;

public class VilgaxDroneEntity extends Monster implements GeoEntity {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.vilgax_drone.idle");
    private static final RawAnimation FLY =
            RawAnimation.begin().thenLoop("animation.vilgax_drone.fly");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.vilgax_drone.attack");
    private static final int MAX_LIFETIME = 20 * 180;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private UUID vilgaxId;
    private int lifetime;
    private int dashCooldown = 30;
    private boolean ownerSeen;

    public VilgaxDroneEntity(EntityType<? extends VilgaxDroneEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        setNoGravity(true);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 32)
            .add(Attributes.MOVEMENT_SPEED, 0.32)
            .add(Attributes.FLYING_SPEED, 0.7)
            .add(Attributes.ATTACK_DAMAGE, 3)
            .add(Attributes.MAX_HEALTH, 16);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::movementAnimation)
            .triggerableAnim("attack", ATTACK));
    }

    private PlayState movementAnimation(AnimationState<VilgaxDroneEntity> state) {
        return state.setAndContinue(state.isMoving() ? FLY : IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void setVilgaxId(UUID id) {
        this.vilgaxId = id;
    }

    public boolean belongsTo(UUID id) {
        return id.equals(this.vilgaxId);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (++this.lifetime >= MAX_LIFETIME) {
            discard();
            return;
        }
        if (this.vilgaxId != null && this.level() instanceof ServerLevel serverLevel) {
            var owner = serverLevel.getEntity(this.vilgaxId);
            if (owner instanceof VilgaxEntity vilgax) {
                this.ownerSeen = true;
                if (!vilgax.isAlive()) {
                    discard();
                    return;
                }
            } else if (this.ownerSeen) {
                discard();
                return;
            }
        }
        LivingEntity target = getTarget();
        if (target != null && target.isAlive() && --this.dashCooldown <= 0
                && distanceToSqr(target) < 64.0 && distanceToSqr(target) > 3.0) {
            Vec3 dash = target.position().subtract(position()).normalize().scale(0.75);
            setDeltaMovement(dash);
            hasImpulse = true;
            this.dashCooldown = 40;
            triggerAnim("controller", "attack");
        } else if (this.dashCooldown < 0) {
            this.dashCooldown = 0;
        }
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            Vec3 dash = living.position().subtract(position()).normalize().scale(0.35);
            living.push(dash.x, 0.15, dash.z);
            living.hurtMarked = true;
            this.dashCooldown = 40;
        }
        return hit;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.vilgaxId != null) {
            tag.putUUID("vilgax", this.vilgaxId);
        }
        tag.putInt("lifetime", this.lifetime);
        tag.putInt("dash_cooldown", this.dashCooldown);
        tag.putBoolean("owner_seen", this.ownerSeen);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.vilgaxId = tag.hasUUID("vilgax") ? tag.getUUID("vilgax") : null;
        this.lifetime = tag.getInt("lifetime");
        this.dashCooldown = tag.contains("dash_cooldown") ? tag.getInt("dash_cooldown") : 30;
        this.ownerSeen = tag.getBoolean("owner_seen");
    }
}
