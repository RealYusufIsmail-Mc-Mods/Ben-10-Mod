package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.EntityTypesInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.yusuf.realyusufismailcore.core.init.ItemInitCore;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ModEntityLootTables extends EntityLoot {

    @Override
    protected void addTables() {
        add(EntityTypesInit.VILGAX_ENTITY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(ItemLootEntry.lootTableItem(ItemInit.ENCHANTED_OMNITRIX_APPLE.get()).apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(RandomValueRange.between(0.0F, 2.0F))))));
        add(EntityTypesInit.KRAAB_ENTITY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItemCondition.lootTableItem(ItemInitCore.COPPER.get()).apply(SetCount.setCount(RandomValueRange.between(0.0F, 6.0F))).apply(LootingEnchantFunction.lootingMultiplier(RandomValueRange.between(0.0F, 6.0F))))));

    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
                .filter(entry -> entry.getRegistryName() != null && entry.getRegistryName().getNamespace().equals(BenTenMod.MOD_ID))
                .collect(Collectors.toSet());
    }


}
