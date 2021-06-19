package com.yusuf.bentenmod.entity;

import com.yusuf.bentenmod.entity.ai.VilgaxAttackGoal;
import com.yusuf.bentenmod.entity.ai.VilgaxWaterAvoidingRandomWalkingGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
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



	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(VilgaxEntity.class,
			DataSerializers.BOOLEAN);

	private final AnimationFactory factory = new AnimationFactory(this);

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
		this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(3, new VilgaxAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(4, new MoveThroughVillageAtNightGoal(this, 1));
		this.goalSelector.addGoal(5, new VilgaxWaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglinEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.ATTACK_DAMAGE, 10.0D)
				.add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
				.add(Attributes.MAX_HEALTH, 300.0D);
	}




	protected int getExperienceReward(PlayerEntity p_70693_1_) {
		if (this.isBaby()) {
			this.xpReward = (int) ((float) this.xpReward * 2.5F);
		}

		return super.getExperienceReward(p_70693_1_);
	}



	

	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (!super.hurt(p_70097_1_, p_70097_2_)) {
			return false;
		} else if (!(this.level instanceof ServerWorld)) {
			return false;
		} else {
			LivingEntity livingentity = this.getTarget();
			if (livingentity == null && p_70097_1_.getEntity() instanceof LivingEntity) {
				livingentity = (LivingEntity) p_70097_1_.getEntity();
			}

			int i = MathHelper.floor(this.getX());
			int j = MathHelper.floor(this.getY());
			int k = MathHelper.floor(this.getZ());

			for (int l = 0; l < 50; ++l) {
				int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
				int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
				int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
				new BlockPos(i1, j1, k1);

			}
		}

		return true;

	}

	public boolean doHurtTarget(Entity p_70652_1_) {
		boolean flag = super.doHurtTarget(p_70652_1_);
		if (flag) {
			float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
				p_70652_1_.setSecondsOnFire(2 * (int) f);
			}
		}

		return flag;
	}





	protected SoundEvent getAmbientSound() {
		return SoundEvents.PHANTOM_AMBIENT	;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.IRON_GOLEM_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.IRON_GOLEM_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.IRON_GOLEM_STEP;
	}

	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		super.populateDefaultEquipmentSlots(p_180481_1_);
		if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
			int i = this.random.nextInt(3);
			if (i == 0) {
				this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}

	}

	public void killed(ServerWorld p_241847_1_, LivingEntity p_241847_2_) {
		super.killed(p_241847_1_, p_241847_2_);
		{
			if (p_241847_1_.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return;
			}

		}

	}




	protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
		return this.isBaby() ? 0.93F : 1.74F;
	}

	public boolean canHoldItem(ItemStack p_175448_1_) {
		return (p_175448_1_.getItem() != Items.EGG || !this.isBaby() || !this.isPassenger()) && super.canHoldItem(p_175448_1_);
	}

	protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
		super.dropCustomDeathLoot(p_213333_1_, p_213333_2_, p_213333_3_);
		Entity entity = p_213333_1_.getEntity();
		if (entity instanceof CreeperEntity) {
			CreeperEntity creeperentity = (CreeperEntity) entity;
			if (creeperentity.canDropMobsSkull()) {
				ItemStack itemstack = null;
				if (!itemstack.isEmpty()) {
					creeperentity.increaseDroppedSkulls();
					this.spawnAtLocation(itemstack);
				}
			}
		}

	}


}