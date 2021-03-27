package com.Yusuf.bentenmobmod.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Attributes;

public abstract class Vilgax extends TameableEntity implements IAnimatable {

	public Vilgax(EntityType<Vilgax> entityType, World worldIn) {
        super(entityType, worldIn);
        this.setTamed(false);

	protected void applyEntityAI() {
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this, PlayerEntity.class));
		this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setCallsForHelp());
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
            return MobEntity.func_233666_p_()
                    .createMutableAttribute(Attributes.MAX_HEALTH, 47.0D)
                    .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D)
                    .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.23F)
                    .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D);
	}
            
            public boolean attackEntityFrom(DamageSource source, float amount) {
                if (this.isInvulnerableTo(source)) {
                    return false;
                }
                else {
                    return super.attackEntityFrom(source, amount);
                }
            }}

	
