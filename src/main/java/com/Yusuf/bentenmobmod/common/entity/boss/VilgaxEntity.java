package com.Yusuf.bentenmobmod.common.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;


public class VilgaxEntity extends MonsterEntity {
	

public VilgaxEntity(EntityType<? extends VilgaxEntity> wither, World world) {
    super(wither, world);
    this.setHealth(600);
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
