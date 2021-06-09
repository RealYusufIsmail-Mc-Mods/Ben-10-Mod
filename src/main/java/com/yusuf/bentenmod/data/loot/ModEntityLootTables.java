package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.core.init.EntityTypesInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;

public class ModEntityLootTables extends EntityLootTables {

  @Override
    protected void addTables() {
      add(EntityTypesInit.VILGAX_ENTITY, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ItemInit.ENCHANTED_OMNITRIX_APPLE.get()).apply(SetCount.setCount(RandomValueRange.between(0.0F, 1.0F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));


  }

}
