/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.bentenmod.data.loot;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityLootTables extends EntityLootTables {

    @Override
    protected void addTables() {
        add(
                EntityTypesInit.VILGAX_ENTITY.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(ItemInit.ENCHANTED_OMNITRIX_APPLE.get())
                                        .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                        .apply(LootingEnchantBonus.lootingMultiplier(
                                                RandomValueRange.between(0.0F, 2.0F))))));
        add(
                EntityTypesInit.KRAAB_ENTITY.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(ItemInit.FIRE.get())
                                        .apply(SetCount.setCount(RandomValueRange.between(0.0F, 6.0F)))
                                        .apply(LootingEnchantBonus.lootingMultiplier(
                                                RandomValueRange.between(0.0F, 6.0F))))));
    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return StreamSupport.stream(ForgeRegistries.ENTITIES.spliterator(), false)
                .filter(entry -> entry.getRegistryName() != null
                        && entry.getRegistryName().getNamespace().equals(BenTenMod.MOD_ID))
                .collect(Collectors.toSet());
    }
}
