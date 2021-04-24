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

public class jacket extends ArmorItem implements IForgeItem{

	public jacket(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);		
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
			EffectInstance effect = new EffectInstance(Effects.REGENERATION, 940000000, 0, false, false, true);
			if (stack.getItem() == ItemInit.JACKET.get()) {
				player.addEffect(effect);
			}
			EffectInstance effect1 = new EffectInstance(Effects.DAMAGE_RESISTANCE, 940000000, 0, false, false, true);
			if (stack.getItem() == ItemInit.JACKET.get()) {
				player.addEffect(effect1);
		}
	}
}

