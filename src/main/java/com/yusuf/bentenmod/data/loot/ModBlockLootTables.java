/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;


public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        //ores
        add(BlockInit.BLACK_DIAMOND_ORE.get(),
                createOreDrop(BlockInit.BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));

        add(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(),
                createOreDrop(BlockInit.BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));

        add(BlockInit.INFINITUM_ORE.get(),
                createOreDrop(BlockInit.INFINITUM_ORE.get(), ItemInit.INFINITUM_SCRAP.get()));

        add(BlockInit.SPEED_ORE.get(),
                createOreDrop(BlockInit.SPEED_ORE.get(), ItemInit.SPEED.get()));

        add(BlockInit.DEEPSLATE_SPEED_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_SPEED_ORE.get(), ItemInit.SPEED.get()));

        add(BlockInit.OMNITRIX_ORE.get(),
                createOreDrop(BlockInit.OMNITRIX_ORE.get(), ItemInit.RAW_OMNITRIX.get()));

        add(BlockInit.DEEPSLATE_OMNITRIX_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_OMNITRIX_ORE.get(), ItemInit.RAW_OMNITRIX.get()));

        add(BlockInit.IMPERIUM_ORE.get(),
                createOreDrop(BlockInit.IMPERIUM_ORE.get(), ItemInit.RAW_IMPERIUM.get()));

        add(BlockInit.DEEPSLATE_IMPERIUM_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_IMPERIUM_ORE.get(), ItemInit.RAW_IMPERIUM.get()));

        add(BlockInit.LEGENDARY_ORE.get(),
                createOreDrop(BlockInit.LEGENDARY_ORE.get(), ItemInit.RAW_LEGENDARY.get()));

        add(BlockInit.DEEPSLATE_LEGENDARY_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_LEGENDARY_ORE.get(), ItemInit.RAW_LEGENDARY.get()));

        add(BlockInit.RUBY_ORE.get(),
                createOreDrop(BlockInit.RUBY_ORE.get(), ItemInit.RUBY.get()));

        add(BlockInit.DEEPSLATE_RUBY_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_LEGENDARY_ORE.get(), ItemInit.RUBY.get()));

        //blocks
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
        dropSelf(BlockInit.FIRE_BLOCK.get());
        dropSelf(BlockInit.RUBY_BLOCK.get());
        dropSelf(BlockInit.SPEED_BLOCK.get());
        dropSelf(BlockInit.INFINITUM_BLOCK.get());
        dropSelf(BlockInit.BLACK_DIAMOND_BLOCK.get());
        dropSelf(BlockInit.LEGENDARY_BLOCK.get());
        dropSelf(BlockInit.IMPERIUM_BLOCK.get());
        dropSelf(BlockInit.FIRE_ORE.get());
        //dropSelf(BlockInit.TABLE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> BenTenMod.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}

