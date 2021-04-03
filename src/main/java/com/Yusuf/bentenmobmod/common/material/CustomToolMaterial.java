package com.Yusuf.bentenmobmod.common.material;


import java.util.function.Supplier;

import com.Yusuf.bentenmobmod.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum  CustomToolMaterial implements IItemTier {
	
	TOOL(20, 60, 60f, 10f, 40, () -> Ingredient.of(ItemInit.LEGENDARY_ORE.get())),
		SWORD(20, 60, 60f, 10f, 40, () -> Ingredient.of(ItemInit.LEGENDARY_ORE.get())),
		AXE(50, 70, 80f, 15f, 50, () -> Ingredient.of(ItemInit.LEGENDARY_ORE.get()));
		

	    
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
