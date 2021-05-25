package com.yusuf.bentenmobmod.common.events;

import com.yusuf.bentenmobmod.core.init.ItemInit;

import net.minecraft.entity.player.PlayerAbilities;
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
public final PlayerAbilities abilities = new PlayerAbilities();

	public HeatblastArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);	
		
	}
	
	
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		
		ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
		ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
		ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
		ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
		
		if (boots.getItem() == ItemInit.HEATBLAST_BOOT.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
			player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 3, false, false, true));
			player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 3, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 3, false, false, true));
			player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 100, 1, false, false, true));
			player.abilities.mayfly = true;
			player.fallDistance = 0.0F;
			player.abilities.setFlyingSpeed(0.1F);
		}
		
		
} 
	}
	

