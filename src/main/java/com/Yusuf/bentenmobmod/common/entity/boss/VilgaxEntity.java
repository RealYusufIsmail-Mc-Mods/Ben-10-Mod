package com.Yusuf.bentenmobmod.common.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
		   value = Dist.CLIENT,
		   _interface = IChargeableMob.class
		)
public class VilgaxEntity extends MonsterEntity {

	protected VilgaxEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		// TODO Auto-generated constructor stub
	}
}
