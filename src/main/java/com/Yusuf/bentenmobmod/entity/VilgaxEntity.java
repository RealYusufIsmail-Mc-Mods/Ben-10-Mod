

package com.Yusuf.bentenmobmod.entity;

import com.Yusuf.bentenmobmod.Main;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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

public class VilgaxEntity extends CreatureEntity implements IAnimatable 

{
	
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(VilgaxEntity.class,
			DataSerializers.BOOLEAN);
	
	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.vilgax.walking", true));
            return PlayState.CONTINUE;
        }
        if (!this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.vilgax.attacking", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
			public VilgaxEntity(EntityType<? extends CreatureEntity> type, World worldIn)
			{
				super(type, worldIn);
			
			}

	

	
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
	                .add(Attributes.ATTACK_KNOCKBACK, 10.0D)
	                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
	                .add(Attributes.ATTACK_DAMAGE, 10.0D);
                    
	 }
	        
	 	protected SoundEvent getAmbientSound() {
	      return SoundEvents.ENDER_DRAGON_AMBIENT;
	   }
	

	   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
	      return SoundEvents.ENDER_DRAGON_HURT;
	   }

	   protected SoundEvent getDeathSound() {
	      return SoundEvents.ENDER_DRAGON_DEATH;
	   }

	   protected SoundEvent getStepSound() {
	      return SoundEvents.IRON_GOLEM_STEP;
	   }

	   
	   
		@Override
		public void registerControllers(AnimationData data)
		{
			data.addAnimationController(new AnimationController<VilgaxEntity>(this, "controller", 0, this::predicate));
		}
		
		public ResourceLocation getAnimationFileLocation(VilgaxEntity VilgaxEntity) {
	        return new ResourceLocation(Main.MOD_ID, "animation/vilgax.animation.json");

	    }
		
		@Override
		public AnimationFactory getFactory()
		{
			return this.factory;
		}
		@Override
		protected void defineSynchedData() {
			super.defineSynchedData();
			this.entityData.define(ATTACKING, false);
		}

		@OnlyIn(Dist.CLIENT)
		public boolean isAttacking() {
			return this.entityData.get(ATTACKING);
		}
		public void setAttacking(boolean attacking) {
			this.entityData.set(ATTACKING, attacking);
		}
		@Override
		public boolean isBaby() {
			return false;
		}

		protected boolean shouldDrown() {
			return false;
		}

		protected boolean shouldBurnInDay() {
			return false;
		}
		@Override
		public CreatureAttribute getMobType() {
			return CreatureAttribute.UNDEAD;
		}

		@Override
		public int getMaxSpawnClusterSize() {
			return 1;
		}
	    
};