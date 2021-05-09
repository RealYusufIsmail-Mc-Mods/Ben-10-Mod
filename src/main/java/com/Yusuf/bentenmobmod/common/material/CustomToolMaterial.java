package com.yusuf.bentenmobmod.common.material;

import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CustomToolMaterial implements IItemTier {

	SWORD(20, 2500, 60f, 10f, 40, () -> Ingredient.of(ItemInit.LEGENDARY_ORE.get())),
	HEATBLAST_SWORD(20, 2500, 60f, 10f, 40, () -> Ingredient.of(ItemInit.FIRE.get())),
	BLACK_DIAMOND_SWORD(20, 2000, 60f, 11f, 40, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
	AXE(50, 2500, 80f, 15f, 50, () -> Ingredient.of(ItemInit.RUBY.get()));

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final Ingredient repairMaterial;

	CustomToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
			Supplier<Ingredient> repairMaterial) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial.get();
	}

	@Override
	public int getUses() {
		return this.maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial;
	}

}
