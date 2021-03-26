package com.Yusuf.bentenmobmod.common.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
		   value = Dist.CLIENT,
		   _interface = IChargeableMob.class
		)
public class VilgaxEntity extends MonsterEntity {
	

public VilgaxEntity(EntityType<? extends WitherEntity> wither, World world) {
    super(wither, world);
    this.setHealth(this.getMaxHealth());
    this.getNavigator().setCanSwim(true);
    this.experienceValue = 100;
};


protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_WITHER_AMBIENT;
 };

 protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_WITHER_HURT;
 }

 protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WITHER_DEATH;
 }}



