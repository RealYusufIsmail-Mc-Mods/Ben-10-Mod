package com.Yusuf.bentenmobmod.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class VilgaxEntity extends MobEntity implements IAnimatable {
	private static final DataParameter<Boolean> DROWNING = EntityDataManager.defineId(VilgaxEntity.class,
			DataSerializers.BOOLEAN);;

	private int exampleTimer;

	public VilgaxEntity(EntityType<? extends VilgaxEntity> type, World worldIn) {
		super(EntityType.IRON_GOLEM, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.dataManager.get(WALKING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }

		};

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {

		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
	}
	 public static AttributeModifierMap.MutableAttribute registerAttributes() {
	        return MobEntity.createMobAttributes()
	                .add(Attributes.MAX_HEALTH, 47.0D)
	                .add(Attributes.FOLLOW_RANGE, 35.0D)
	                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
	                .add(Attributes.ATTACK_DAMAGE, 10.0D);
	  }

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DROWNING, false);
	}

	public boolean isDrowning() {
		return this.getEntityData().get(DROWNING);
	}

	@Override
	public void aiStep() {
		if (this.level.isClientSide) {
			this.exampleTimer = Math.max(0, this.exampleTimer - 1);
		}
		super.aiStep();
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 10) {
			this.exampleTimer = 40;
		} else {
			super.handleEntityEvent(id);
		}
	}

	public boolean checkSpawnObstruction(IWorldReader worldIn) {
		return worldIn.isUnobstructed(this);
	}

	@Override
	protected int getExperienceReward(PlayerEntity player) {
		return 60;
	}

	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.getFactory();
	}

};