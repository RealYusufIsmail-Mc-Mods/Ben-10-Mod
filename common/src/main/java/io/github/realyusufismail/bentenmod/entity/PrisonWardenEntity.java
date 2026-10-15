package io.github.realyusufismail.bentenmod.entity;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PrisonWardenEntity extends Monster implements GeoEntity {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.prison_warden.idle");
    private static final RawAnimation WALK =
            RawAnimation.begin().thenLoop("animation.prison_warden.walk");
    private static final RawAnimation ATTACK =
            RawAnimation.begin().thenPlay("animation.prison_warden.attack");
    private static final RawAnimation SUMMON =
            RawAnimation.begin().thenPlay("animation.prison_warden.summon");
    private static final int SUMMON_INTERVAL = 20 * 20;
    private static final int MAX_PRISONERS = 4;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.translatable("entity." + BenTenMod.MOD_ID + ".prison_warden"),
            BossBarColor.RED, BossBarOverlay.PROGRESS);
    private int summonCooldown = SUMMON_INTERVAL;
    private boolean firstSightLineSpoken;
    private boolean summonLineSpoken;

    public PrisonWardenEntity(EntityType<? extends PrisonWardenEntity> type, Level level) {
        super(type, level);
        this.bossEvent.setDarkenScreen(true);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.FOLLOW_RANGE, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.24)
            .add(Attributes.ATTACK_DAMAGE, 10.0)
            .add(Attributes.ARMOR, 10.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
            .add(Attributes.MAX_HEALTH, 150.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        this.bossEvent.setProgress(getHealth() / getMaxHealth());
        if (this.level().isClientSide || !(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!this.firstSightLineSpoken) {
            Player nearest = this.level().getNearestPlayer(this, 24.0);
            if (nearest != null) {
                this.firstSightLineSpoken = BossChatter.sayVariant(this, ChatFormatting.DARK_RED,
                        "warden.sighting", 2, nearest.getName());
            }
        }
        if (--this.summonCooldown <= 0) {
            summonPrisoners(serverLevel);
            this.summonCooldown = SUMMON_INTERVAL;
        }
    }

    private void summonPrisoners(ServerLevel serverLevel) {
        int nearby =
                serverLevel
                    .getEntitiesOfClass(NullVoidPrisonerEntity.class,
                            getBoundingBox().inflate(32.0),
                            prisoner -> prisoner.isAlive()
                                    && prisoner.distanceToSqr(this) <= 1024.0)
                    .size();
        int count = Math.min(2, MAX_PRISONERS - nearby);
        if (count <= 0) {
            return;
        }
        triggerAnim("controller", "summon");
        if (!this.summonLineSpoken) {
            this.summonLineSpoken =
                    BossChatter.sayVariant(this, ChatFormatting.DARK_RED, "warden.summon", 2);
        }
        for (int i = 0; i < count; i++) {
            NullVoidPrisonerEntity prisoner =
                    EntityTypesInit.NULL_VOID_PRISONER.get().create(serverLevel);
            if (prisoner == null) {
                continue;
            }
            prisoner.moveTo(getX() + (this.random.nextDouble() - 0.5) * 6.0, getY(),
                    getZ() + (this.random.nextDouble() - 0.5) * 6.0,
                    this.random.nextFloat() * 360.0F, 0.0F);
            if (getTarget() != null) {
                prisoner.setTarget(getTarget());
            }
            serverLevel.addFreshEntity(prisoner);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            Vec3 knockback = living.position().subtract(position()).normalize().scale(0.65);
            living.push(knockback.x, 0.2, knockback.z);
            living.hurtMarked = true;
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0));
            triggerAnim("controller", "attack");
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::movementAnimation)
            .triggerableAnim("attack", ATTACK)
            .triggerableAnim("summon", SUMMON));
    }

    private PlayState movementAnimation(AnimationState<PrisonWardenEntity> state) {
        return state.setAndContinue(state.isMoving() ? WALK : IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
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
        BossChatter.sayVariant(this, ChatFormatting.DARK_RED, "warden.death", 2);
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
        tag.putInt("summon_cooldown", this.summonCooldown);
        tag.putBoolean("chatter_sighting", this.firstSightLineSpoken);
        tag.putBoolean("chatter_summon", this.summonLineSpoken);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.summonCooldown =
                tag.contains("summon_cooldown") ? tag.getInt("summon_cooldown") : SUMMON_INTERVAL;
        this.firstSightLineSpoken = tag.getBoolean("chatter_sighting");
        this.summonLineSpoken = tag.getBoolean("chatter_summon");
        setPersistenceRequired();
    }
}
