package com.Yusuf.bentenmobmod.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class VilgaxEntity extends MobEntity implements IMob {
 private static final DataParameter<Boolean> DROWNING = EntityDataManager.createKey(VilgaxEntity.class, DataSerializers.BOOLEAN);;

	  
private int exampleTimer;
	
	
 public VilgaxEntity(EntityType<? extends VilgaxEntity> type, World worldIn) {
	  super(EntityType.IRON_GOLEM, worldIn);
 }
	  
	  @Override
	    protected void registerGoals() {
	        super.registerGoals();
	        this.goalSelector.addGoal(0, new SwimGoal(this));
	        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
	        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
	        this.applyEntityAI();
	  }
	        protected void applyEntityAI() {
	            this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
	            this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
	            this.targetSelector.addGoal(3, new HurtByTargetGoal(this, PlayerEntity.class));
	            this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setCallsForHelp());
	            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
	            this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	          this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
	          }
	        public static AttributeModifierMap.MutableAttribute registerAttributes() {
	            return MobEntity.func_233666_p_()
	                    .createMutableAttribute(Attributes.MAX_HEALTH, 300.0D)
	                    .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
	                    .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.23F)
	                 .createMutableAttribute(Attributes.ATTACK_DAMAGE, 35.0D);
	}
	        protected void registerData() {
	            super.registerData();
	            this.getDataManager().register(DROWNING, false);
	         }

	        public boolean isDrowning() {
	            return this.getDataManager().get(DROWNING);
	         }
	        @Override
	        public void livingTick() {
	            if (this.world.isRemote) {
	                this.exampleTimer = Math.max(0, this.exampleTimer - 1);
	            }
	            super.livingTick();
	        }

	        @OnlyIn(Dist.CLIENT)
	        public void handleStatusUpdate(byte id) {
	            if (id == 10) {
	                this.exampleTimer = 40;
	            } else {
	                super.handleStatusUpdate(id);
	            }
	        }

	        public boolean isNotColliding(IWorldReader worldIn) {
	            return worldIn.checkNoEntityCollision(this);
	        }

	        @Override
	        protected int getExperiencePoints(PlayerEntity player) {
	            return 60;
	        }

	        public int getMaxSpawnedInChunk() {
	            return 1;
	        }

	        @Override
	        public void registerControllers(AnimationData animationData) {
	            animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	        }

	        @Override
	        public AnimationFactory getFactory() {
	            return this.factory;
	        }

	       

	         
	           



	         };




