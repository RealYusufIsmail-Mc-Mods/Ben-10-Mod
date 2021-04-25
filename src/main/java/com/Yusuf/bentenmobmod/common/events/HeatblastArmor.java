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

public class HeatblastArmor extends ArmorItem implements IForgeItem {

	public HeatblastArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);		
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		
		ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
		ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
		ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
		ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
		
		EffectInstance effect = new EffectInstance(Effects.FIRE_RESISTANCE, 10, 0, false, false, true);
		if (boots.getItem() == ItemInit.HEATBLAST_BOOT.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
			player.addEffect(effect);
		}
		EffectInstance effect1 = new EffectInstance(Effects.REGENERATION, 10, 0, false, false, true);
		if (boots.getItem() == ItemInit.HEATBLAST_BOOT.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
			player.addEffect(effect1);
		}
		EffectInstance effect11 = new EffectInstance(Effects.ABSORPTION, 10, 2, false, false, true);
		if (boots.getItem() == ItemInit.HEATBLAST_BOOT.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
			player.addEffect(effect11);
		}
		EffectInstance effect111 = new EffectInstance(Effects.DAMAGE_RESISTANCE, 10, 0, false, false, true);
		if (boots.getItem() == ItemInit.HEATBLAST_BOOT.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
			player.addEffect(effect111);
			
			}
		EffectInstance effect12 = new EffectInstance(Effects.DAMAGE_BOOST, 10, 1, false, false, true);
		if (boots.getItem() == ItemInit.HEATBLAST_BOOT.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
			player.addEffect(effect12);
		}
		
		
		
	}	
	}

