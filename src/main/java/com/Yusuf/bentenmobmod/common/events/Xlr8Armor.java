package com.Yusuf.bentenmobmod.common.events;

import com.Yusuf.bentenmobmod.core.init.ItemInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class Xlr8Armor extends ArmorItem implements IForgeItem {

	public Xlr8Armor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);		
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		
		ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
		ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
		ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
		ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
		
		if (boots.getItem() == ItemInit.XLR8_BOOT.get() && legs.getItem() == ItemInit.XLR8_LEGGINGS.get() && chest.getItem() == ItemInit.XLR8_CHESTPLATE.get() && head.getItem() == ItemInit.XLR8_HELMET.get()) {
			player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 0, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 0, false, false, true));
			player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 100, 3, false, false, true));
			
		}}
}
