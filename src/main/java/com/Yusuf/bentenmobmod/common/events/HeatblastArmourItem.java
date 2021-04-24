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

public class HeatblastArmourItem extends ArmorItem implements IForgeItem {

	public HeatblastArmourItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);		
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		
			EffectInstance effect = new EffectInstance(Effects.FIRE_RESISTANCE, 10, 0, false, false, true);
			if (stack.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get()) {
				player.addEffect(effect);
			}

			EffectInstance effect1 = new EffectInstance(Effects.REGENERATION, 10, 0, false, false, true);
			if (stack.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get()) {
				player.addEffect(effect1);
			}
			EffectInstance effect11 = new EffectInstance(Effects.ABSORPTION, 10, 2, false, false, true);
			if (stack.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get()) {
				player.addEffect(effect11);
			}
			EffectInstance effect111 = new EffectInstance(Effects.DAMAGE_RESISTANCE, 10, 0, false, false, true);
			if (stack.getItem() == ItemInit.JACKET.get()) {
				player.addEffect(effect111);
		}
	}
}
