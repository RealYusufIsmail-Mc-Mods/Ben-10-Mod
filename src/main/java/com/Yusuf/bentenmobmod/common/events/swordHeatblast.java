package com.Yusuf.bentenmobmod.common.events;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.extensions.IForgeItem;


public class swordHeatblast extends SwordItem implements IForgeItem{
	
	swordHeatblast(IItemTier material, int damage, float speed, Properties properties) {
		super(material, damage, speed, properties );	
		}
}
