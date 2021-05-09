/*
package com.yusuf.bentenmobmod.common.events;

import ItemInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class FourArmsArmor extends ArmorItem implements IForgeItem {

	public FourArmsArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);		
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		
		ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
		ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
		ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
		ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
		
		if (boots.getItem() == ItemInit.4ARMS_HELMET.get() && legs.getItem() == ItemInit.4ARMS_LEGGINGS.get() && chest.getItem() == ItemInit.4ARMS_CHESTPLATE.get() && head.getItem() == ItemInit.4ARMS_BOOT.get()) {
			player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 0, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 0, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 100, 3, false, false, true));
		}}
}
*/

