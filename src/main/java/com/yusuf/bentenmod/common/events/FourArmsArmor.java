/*
package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.core.init.ItemInit;

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

		if (boots.getItem() == ItemInit.FOURARMS_BOOT.get() && legs.getItem() == ItemInit.FOURARMS_LEGGINGS.get() && chest.getItem() == ItemInit.FOURARMS_CHESTPLATE.get() && head.getItem() == ItemInit.FOURARMS_BOOT.get()) {
			player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 7, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 2, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 100, 12, false, false, true));

		}


	}}



 */