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
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.NULL_CRYSTAL.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.INFINITUM.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_HELMET.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_CHESTPLATE.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_LEGGINGS.get()).setWeight(1))
                .add(LootItem.lootTableItem(ItemInit.PLUMBER_BOOTS.get()).setWeight(1)))
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));

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
