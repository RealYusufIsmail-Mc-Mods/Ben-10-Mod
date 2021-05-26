package com.yusuf.bentenmobmod.common.events;

import com.yusuf.bentenmobmod.core.init.ItemInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class JacketArmorItem extends ArmorItem implements IForgeItem{

	public JacketArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);		
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
			EffectInstance effect = new EffectInstance(Effects.RESISTANCE, 100, 2, false, false, true);
			if (stack.getItem() == ItemInit.JACKET.get()) {
				player.addPotionEffect(effect);
		}
	}
}

