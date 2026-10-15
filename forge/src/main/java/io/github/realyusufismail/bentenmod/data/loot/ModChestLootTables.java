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
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModChestLootTables implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(new ResourceLocation(BenTenMod.MOD_ID, "chests/null_void_fortress"), LootTable
            .lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.NULL_CRYSTAL.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.INFINITUM.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));

        output.accept(new ResourceLocation(BenTenMod.MOD_ID, "chests/prison_vault"), LootTable
            .lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.TAYDENITE.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.NULL_CRYSTAL.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.INFINITUM.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.DIAMOND)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(2))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_HELMET.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_CHESTPLATE.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_LEGGINGS.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BOOTS.get()).setWeight(1)))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BLASTER.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.NULL_VOID_GLIDER.get()).setWeight(1)))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                    .setWeight(3)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))));

        output.accept(new ResourceLocation(BenTenMod.MOD_ID, "chests/prison_cell"), LootTable
            .lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(UniformGenerator.between(3.0F, 5.0F))
                .add(LootItem.lootTableItem(ItemInit.TAYDENITE.get())
                    .setWeight(3)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                .add(LootItem.lootTableItem(ItemInit.NULL_CRYSTAL.get())
                    .setWeight(4)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                .add(LootItem.lootTableItem(ItemInit.PRISONER_CUFFS.get()).setWeight(3))
                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                    .setWeight(4)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                .add(LootItem.lootTableItem(Items.BREAD)
                    .setWeight(3)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BADGE.get()).setWeight(1))));

        output.accept(new ResourceLocation(BenTenMod.MOD_ID, "chests/plumber_outpost"), LootTable
            .lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(2))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BADGE.get()).setWeight(2))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BLASTER.get()).setWeight(2))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_HELMET.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_CHESTPLATE.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_LEGGINGS.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BOOTS.get()).setWeight(1)))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.TAYDENITE.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))));
    }
}
