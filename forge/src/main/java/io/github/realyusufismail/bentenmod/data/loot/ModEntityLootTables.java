/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import net.minecraft.world.item.Items;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.stream.Stream;


public class ModEntityLootTables extends EntityLootSubProvider {

    protected ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(EntityTypesInit.CRAB_ENTITY.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.KRAAB_CLAW.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
        add(EntityTypesInit.VILGAX_ENTITY.get(),
                LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ItemInit.VILGAX_CORE.get()))));
        add(EntityTypesInit.VILGAX_DRONE_ENTITY.get(), LootTable.lootTable());
        add(EntityTypesInit.NULL_VOID_PRISONER.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.PRISONER_CUFFS.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                    .apply(LootingEnchantFunction.lootingMultiplier(ConstantValue.exactly(0.5F))
                        .setLimit(2)))));
        add(EntityTypesInit.NULL_GUARDIAN.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.NULL_GUARDIAN_MEMBRANE.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
        add(EntityTypesInit.LEPIDOPTERAN.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.SLIME_BALL)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.05F))
                .add(LootItem.lootTableItem(ItemInit.TAYDENITE.get()))));
        add(EntityTypesInit.PRISON_WARDEN.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.CELL_KEY.get())))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.PRISONER_CUFFS.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                    .apply(LootingEnchantFunction.lootingMultiplier(ConstantValue.exactly(0.5F))
                        .setLimit(2)))));
        add(EntityTypesInit.ZS_SKAYR_ENTITY.get(),
                LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ItemInit.MASTER_CONTROL.get()))));

    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ForgeRegistries.ENTITY_TYPES.getValues()
            .stream()
            .filter(entry -> Optional.ofNullable(ForgeRegistries.ENTITY_TYPES.getKey(entry))
                .filter(key -> key.getNamespace().equals(BenTenMod.MOD_ID))
                .isPresent());
    }
}
