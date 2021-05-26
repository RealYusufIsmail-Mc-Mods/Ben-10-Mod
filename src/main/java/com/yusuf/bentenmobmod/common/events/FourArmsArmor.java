
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

public class FourArmsArmor extends ArmorItem implements IForgeItem {

	public final PlayerAbilities abilities = new PlayerAbilities();

	public FourArmsArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);

	}



	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

		ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
		ItemStack legs = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
		ItemStack chest = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
		ItemStack head = player.getItemStackFromSlot(EquipmentSlotType.HEAD);


		if (boots.getItem() == ItemInit.FOURARMS_HELMET.get() && legs.getItem() == ItemInit.FOURARMS_LEGGINGS.get() && chest.getItem() == ItemInit.FOURARMS_CHESTPLATE.get() && head.getItem() == ItemInit.FOURARMS_BOOT.get()) {
			player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 2, false, false, true));
			player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 100, 2, false, false, true));
			player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 100, 3, false, false, true));
		}}
}


