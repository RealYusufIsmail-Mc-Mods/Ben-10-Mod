package com.yusuf.bentenmobmod.common.material;

import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CustomToolMaterial implements IItemTier {

	SWORD(20, 2500, 60f, 10f, 40, () -> Ingredient.fromItems(ItemInit.LEGENDARY_ORE.get())),
	HEATBLAST_SWORD(20, 2500, 60f, 10f, 40, () -> Ingredient.fromItems(ItemInit.FIRE.get())),
	BLACK_DIAMOND_SWORD(20, 2000, 60f, 12f, 40, () -> Ingredient.fromItems(ItemInit.BLACK_DIAMOND.get())),
	BLACK_DIAMOND_AXE(20, 2500, 60f, 11f, 40, () -> Ingredient.fromItems(ItemInit.BLACK_DIAMOND.get())),
	BLACK_DIAMOND_PICKAXE(20, 2500, 60f, 8f, 40, () -> Ingredient.fromItems(ItemInit.BLACK_DIAMOND.get())),
	BLACK_DIAMOND_SHOVEL(20, 2500, 60f, 9f, 40, () -> Ingredient.fromItems(ItemInit.BLACK_DIAMOND.get())),
	BLACK_DIAMOND_HOE(20, 2500, 60f, 6f, 40, () -> Ingredient.fromItems(ItemInit.BLACK_DIAMOND.get())),
	INFINITUM_SWORD(20, 3000, 70f, 14f, 40, () -> Ingredient.fromItems(ItemInit.INFINITUM.get())),
	INFINITUM_AXE(20, 3000, 70f, 13f, 40, () -> Ingredient.fromItems(ItemInit.INFINITUM.get())),
	INFINITUM_PICKAXE(20, 3000, 70f, 12f, 40, () -> Ingredient.fromItems(ItemInit.INFINITUM.get())),
	INFINITUM_SHOVEL(20, 3000, 70f, 10f, 40, () -> Ingredient.fromItems(ItemInit.INFINITUM.get())),
	INFINITUM_HOE(20, 3000, 70f, 7f, 40, () -> Ingredient.fromItems(ItemInit.INFINITUM.get())),
	AXE(50, 2500, 80f, 8, 50, () -> Ingredient.fromItems(ItemInit.RUBY.get()));

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
	public int getMaxUses() {
		return this.maxUses;
	}

	@Override
	public float getEfficiency() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial;
	}

}
