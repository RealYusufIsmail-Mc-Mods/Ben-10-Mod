/*
package com.yusuf.bentenmobmod.common.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;


public class swordHeatblast extends SwordItem implements IForgeItem{
	
	public swordHeatblast(IItemTier material, int damage, float speed, Properties properties) {
		super(material, damage, speed, properties );	
		}
	
	@Override
	public void IArmorItem(ItemStack stack, World world, PlayerEntity player) {
	
		
	
		PlayerEntity player1 = (PlayerEntity) player1;
		ItemStack equipped = player1.getMainHandItem();
		if(equipped == stack){
			player1.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, false, false, true));
		}
	
	
	@Override
	public void onCraft(swordHeatblast event) {
	    if (event.getRecipe().getResult().is(ItemInit.HEATBLAST_SWORD)) {
	    	EnchantmentHelper.getEnchantments(getDefaultInstance());
	    	
	    
		
	}
}
*/