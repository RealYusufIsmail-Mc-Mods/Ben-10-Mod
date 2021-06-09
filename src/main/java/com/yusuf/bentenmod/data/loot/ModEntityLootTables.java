package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.EntityTypesInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ModEntityLootTables extends EntityLootTables {

    @Override
    protected void addTables() {
        add(EntityTypesInit.VILGAX_ENTITY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ItemInit.ENCHANTED_OMNITRIX_APPLE.get()).apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 2.0F))))));

    }
    @Override
    protected Iterable<EntityType<?>> getKnownEntities()
    {
        return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
                .filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(Main.MOD_ID))
                .collect(Collectors.toSet());
    }



}
