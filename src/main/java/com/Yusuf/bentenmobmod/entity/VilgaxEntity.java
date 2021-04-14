	

package com.Yusuf.bentenmobmod.entity;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
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

	public VilgaxEntity(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
	
	}
	
	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }
        if (!this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
            return PlayState.CONTINUE;
        }else
        {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }}
	
	 @Override
		public void registerControllers(AnimationData data) {
			data.addAnimationController(new AnimationController<VilgaxEntity>(this, "controller", 0, this::predicate));
		}
		

	

	
	
	 public static AttributeModifierMap.MutableAttribute registerAttributes() {
		 return MobEntity.createMobAttributes()
	                .add(Attributes.FOLLOW_RANGE, 35.0D)
	                .add(Attributes.ATTACK_KNOCKBACK, 10.0D)
	                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
	                .add(Attributes.ATTACK_DAMAGE, 10.0D)
	                .add(Attributes.MAX_HEALTH, 300.0D);
	        
                    
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
		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new SwimGoal(this));
			this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
			this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
			 this.addBehaviourGoals();
		   }
		 protected void addBehaviourGoals() {
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
			 this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
			this.targetSelector.addGoal(5, (new HurtByTargetGoal(this).setAlertOthers()));
		}

		@Override
		public void onSyncedDataUpdated(DataParameter<?> key) {
			super.onSyncedDataUpdated(key);
		}
		public boolean attackEntityAsMob(net.minecraft.entity.Entity entityIn) {
	        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
	        if (flag) {
	            this.doEnchantDamageEffects(this, entityIn);
	        }

	        return flag;
	    }
		public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
			if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canHarmPlayer((PlayerEntity) target)) {
                return true;
				  
			  }else {
		            return false;
			  }
		}
		

		@Override
		public void tick() {
			super.tick();
		}

		@Override
		public void aiStep() {
			super.aiStep();
		}
};