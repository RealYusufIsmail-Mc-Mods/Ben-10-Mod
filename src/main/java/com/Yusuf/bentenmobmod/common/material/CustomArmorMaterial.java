package com.Yusuf.bentenmobmod.common.material;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public enum CustomArmorMaterial implements IArmorMaterial{
	;

	private static final int[] baseDurability = { 128, 144, 160, 112 };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] armorVal;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toghness;
	private final float knockbackResistance;
	private final Ingredient repairIngredient; 
	
	
	CustomArmorMaterial(String name, int durabilityMultiplier, int[] armorVal, int enchantability, 
			SoundEvent equipSound, float toghness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
			this.name = name;
			this.durabilityMultiplier = durabilityMultiplier;
			this.armorVal = armorVal;
			this.enchantability = enchantability;
			this.equipSound = equipSound;
			this.toghness = toghness;
			this.knockbackResistance = knockbackResistance;
			this.repairIngredient = repairIngredient.get();
	}
	
	
	
	@Override
	public int getDurability(EquipmentSlotType slot) {
		return this.baseDurability[slot.getIndex()] * this.durabilityMultiplier;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slot) { 
			return this.armorVal[slot.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;	
	}

	@Override
	public SoundEvent getSoundEvent() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairIngredient;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toghness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

}
