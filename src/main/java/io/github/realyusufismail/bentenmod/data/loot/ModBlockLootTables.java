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
import io.github.realyusufismail.bentenmod.core.init.*;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        // ores
        add(
                BlockInit.BLACK_DIAMOND_ORE.get(),
                createOreDrop(BlockInit.BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));
        add(
                BlockInit.INFINITUM_ORE.get(),
                createOreDrop(BlockInit.INFINITUM_ORE.get(), ItemInit.INFINITUM_SCRAP.get()));
        add(BlockInit.SPEED_ORE.get(), createOreDrop(BlockInit.SPEED_ORE.get(), ItemInit.SPEED.get()));

        dropSelf(BlockInit.LEGENDARY_ORE.get());
        dropSelf(BlockInit.IMPERIUM_ORE.get());
        dropSelf(BlockInit.OMNITRIX_ORE.get());
        dropSelf(BlockInit.FIRE_ORE.get());
        dropSelf(BlockInit.RUBY_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_IMPERIUM_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_OMNITRIX_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_LEGENDARY_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_RUBY_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_SPEED_ORE.get());

        // blocks
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
        dropSelf(BlockInit.FIRE_BLOCK.get());
        dropSelf(BlockInit.RUBY_BLOCK.get());
        dropSelf(BlockInit.SPEED_BLOCK.get());
        dropSelf(BlockInit.INFINITUM_BLOCK.get());
        dropSelf(BlockInit.BLACK_DIAMOND_BLOCK.get());
        dropSelf(BlockInit.LEGENDARY_BLOCK.get());
        dropSelf(BlockInit.IMPERIUM_BLOCK.get());
        dropSelf(BlockInit.TABLE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> BenTenMod.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
