package com.yusuf.bentenmobmod.entity;

import com.yusuf.bentenmobmod.entity.ai.VilgaxAttackGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class VilgaxEntity extends MonsterEntity implements IAnimatable {

	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(VilgaxEntity.class,
			DataSerializers.BOOLEAN);

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
            return PlayState.CONTINUE;
        }if (!this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
        else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
	}
	
	 @Override
		public void registerControllers(AnimationData data) {
			data.addAnimationController(new AnimationController<VilgaxEntity>(this, "controller", 0, this::predicate));
		}
		

	

	
	
	
	        


		
		@Override
		public AnimationFactory getFactory()
		{
			return this.factory;

		}

		

	


	

	public VilgaxEntity(EntityType<? extends VilgaxEntity> p_i48549_1_, World p_i48549_2_) {
		super(p_i48549_1_, p_i48549_2_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(1, new VilgaxAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new MoveThroughVillageAtNightGoal(this, 1));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(ZombieEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MonsterEntity.func_233666_p_()
				.createMutableAttribute(SharedMonsterAttributes.FOLLOW_RANGE, 35.0D)
				.createMutableAttribute(SharedMonsterAttributes.MOVEMENT_SPEED, (double)0.23F)
				.add(SharedMonsterAttributes.ATTACK_DAMAGE, 10.0D)
				.createMutableAttribute(SharedMonsterAttributes.ARMOR, 2.0D)
				.createMutableAttribute(SharedMonsterAttributes.MAX_HEALTH, 300.0D);
	}

	protected boolean supportsBreakDoorGoal() {
		return true;
	}

	protected int getExperienceReward(PlayerEntity p_70693_1_) {
		if (this.isChild()) {
			this.experienceValue = (int) ((float) this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(p_70693_1_);
	}

	protected boolean convertsInWater() {
		return true;
	}

	
	protected boolean isSunSensitive() {
		return true;
	}

	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (!super.attackEntityFrom(p_70097_1_, p_70097_2_)) {
			return false;
		} else if (!(this.world instanceof ServerWorld)) {
			return false;
		} else {
			LivingEntity livingentity = this.getAttackTarget();
			if (livingentity == null && p_70097_1_.getTrueSource() instanceof LivingEntity) {
				livingentity = (LivingEntity) p_70097_1_.getTrueSource();
			}

			int i = MathHelper.floor(this.getPosX());
			int j = MathHelper.floor(this.getPosY());
			int k = MathHelper.floor(this.getPosX());

			for (int l = 0; l < 50; ++l) {
				int i1 = i + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
				int j1 = j + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
				int k1 = k + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
				new BlockPos(i1, j1, k1);

			}
		}

		return true;

	}

	public boolean doHurtTarget(Entity p_70652_1_) {
		boolean flag = super.attackEntityAsMob(p_70652_1_);
		if (flag) {
			float f = this.world.getDifficultyForLocation(this.lastPortalPos).getAdditionalDifficulty();
			if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
				p_70652_1_.setFire(2 * (int) f);
			}
		}

		return flag;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_SQUID_AMBIENT	;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_SQUID_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SQUID_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_IRON_GOLEM_STEP;
	}

	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		super.setEquipmentBasedOnDifficulty(p_180481_1_);
		if (this.rand.nextFloat() < (this.world.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
			int i = this.rand.nextInt(3);
			if (i == 0) {
				this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}

	}



	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return this.isChild() ? 0.93F : 1.74F;
	}

	public boolean canHoldItem(ItemStack p_175448_1_) {
		return p_175448_1_.getItem() == Items.EGG && this.isChild() && this.isPassenger() ? false
				: super.canEquipItem(p_175448_1_);
	}

	protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
		super.dropSpecialItems(p_213333_1_, p_213333_2_, p_213333_3_);
		Entity entity = p_213333_1_.getTrueSource();
		if (entity instanceof CreeperEntity) {
			CreeperEntity creeperentity = (CreeperEntity) entity;
			if (creeperentity.ableToCauseSkullDrop()) {
				ItemStack itemstack = null;
				if (!itemstack.isEmpty()) {
					creeperentity.incrementDroppedSkulls();
					this.entityDropItem(itemstack);
				}
			}
		}

	}


}