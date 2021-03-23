package com.Yusuf.bentenmobmod.common.material;


import java.util.function.Supplier;

import com.Yusuf.bentenmobmod.core.init.ItemInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum  CustomToolMaterial implements IItemTier {
	
	TOOL(20, 60, 60f, 10f, 40, () -> Ingredient.fromItems(ItemInit.LEGENDARY_ORE.get()));
	    
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
