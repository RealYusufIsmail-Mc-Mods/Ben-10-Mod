/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagsInit {

    public static final class Blocks {
        private Blocks() {}

        /// ores
        public static final Tag.Named<Block> ORES_BLACK_DIAMONDS =
                BlockTags.bind("forge:ores/black_diamonds");
        public static final Tag.Named<Block> ORES_RUBYS = BlockTags.bind("forge:ores/rubsy");
        public static final Tag.Named<Block> ORES_INFINITUM =
                BlockTags.bind("forge:ores/infinitum");
        public static final Tag.Named<Block> ORES_FIRE = BlockTags.bind("forge:ores/fire");
        public static final Tag.Named<Block> ORES_SPEEDS = BlockTags.bind("forge:ores/speeds");
        public static final Tag.Named<Block> ORES_OMNITRIXS =
                BlockTags.bind("forge:ores/omnitrixs");
        public static final Tag.Named<Block> ORES_IMPERIUMS =
                BlockTags.bind("forge:ores/imperiums");
        public static final Tag.Named<Block> ORES_LEGENDARY_ORES =
                BlockTags.bind("forge:ores/legendary_ores");

        // blocks
        public static final Tag.Named<Block> STORAGE_LEGENDARY =
                BlockTags.bind("forge:storage_blocks/legendary");
        public static final Tag.Named<Block> STORAGE_OMNITRIX =
                BlockTags.bind("forge:storage_blocks/omnitrix");
        public static final Tag.Named<Block> STORAGE_INFINITUM =
                BlockTags.bind("forge:storage_blocks/infinitum");
        public static final Tag.Named<Block> STORAGE_FIRE =
                BlockTags.bind("forge:storage_blocks/fire");
        public static final Tag.Named<Block> STORAGE_SPEED =
                BlockTags.bind("forge:storage_blocks/speed");
        public static final Tag.Named<Block> STORAGE_BLACK_DIAMOND =
                BlockTags.bind("forge:storage_blocks/black_diamond");


        private static Tag.Named<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static Tag.Named<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(BenTenMod.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        private Items() {}

        // ores
        public static final Tag.Named<Item> ORES_BLACK_DIAMONDS =
                ItemTags.bind("forge:ores/black_diamonds");
        public static final Tag.Named<Item> ORES_RUBYS = ItemTags.bind("forge:ores/rubys");
        public static final Tag.Named<Item> ORES_INFINITUM = ItemTags.bind("forge:ores/infinitum");
        public static final Tag.Named<Item> ORES_FIRE = ItemTags.bind("forge:ores/fire");
        public static final Tag.Named<Item> ORES_SPEEDS = ItemTags.bind("forge:ores/speeds");
        public static final Tag.Named<Item> ORES_OMNITRIXS = ItemTags.bind("forge:ores/omnitrixs");
        public static final Tag.Named<Item> ORES_IMPERIUMS = ItemTags.bind("forge:ores/imperiums");
        public static final Tag.Named<Item> ORES_LEGENDARY_ORES =
                ItemTags.bind("forge:ores/legendary_ores");


        // blocks
        public static final Tag.Named<Item> STORAGE_LEGENDARY =
                ItemTags.bind("forge:storage_blocks/legendary");
        public static final Tag.Named<Item> STORAGE_OMNITRIX =
                ItemTags.bind("forge:storage_blocks/omnitrix");
        public static final Tag.Named<Item> STORAGE_INFINITUM =
                ItemTags.bind("forge:storage_blocks/infinitum");
        public static final Tag.Named<Item> STORAGE_FIRE =
                ItemTags.bind("forge:storage_blocks/fire");
        public static final Tag.Named<Item> STORAGE_SPEED =
                ItemTags.bind("forge:storage_blocks/speed");
        public static final Tag.Named<Item> STORAGE_BLACK_DIAMOND =
                ItemTags.bind("forge:storage_blocks/black_diamond");
        // items
        public static final Tag.Named<Item> INGOTS_LEGENDARY =
                ItemTags.bind("forge:ingots/legendary");
        public static final Tag.Named<Item> INGOTS_OMNITRIX =
                ItemTags.bind("forge:ingots/omnitrix");
        public static final Tag.Named<Item> INGOTS_FIRE = ItemTags.bind("forge:ingots/fire");
        public static final Tag.Named<Item> INGOTS_RUBY = ItemTags.bind("forge:ingots/ruby");
        public static final Tag.Named<Item> INGOTS_SPEED = ItemTags.bind("forge:ingots/speed");
        public static final Tag.Named<Item> INGOTS_BLACK_DIAMOND =
                ItemTags.bind("forge:ingots/black_diamond");
        public static final Tag.Named<Item> SCRAP_BLACK_DIAMOND_SCRAP =
                ItemTags.bind("forge:scraps/black_diamond_scrap");
        public static final Tag.Named<Item> INGOTS_INFINITUM =
                ItemTags.bind("forge:ingots/infinitum");
        public static final Tag.Named<Item> SCRAP_INFINITUM_SCRAP =
                ItemTags.bind("forge:scraps/infinitum_scrap");
        public static final Tag.Named<Item> INGOTS_IMPERIUM =
                ItemTags.bind("forge:ingots/imperium");
        public static final Tag.Named<Item> SPAW_EGGS = ItemTags.bind("forge:ingots/spawn_eggs");

        // tools
        public static final Tag.Named<Item> TOOLS_IMPERIUM_PICKAXE =
                ItemTags.bind("forge:tools/imperium_pickaxe");

        // raw ores
        public static final Tag.Named<Item> RAW_ORE = ItemTags.bind("forge:scraps/raw_ore");

        private static Tag.Named<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static Tag.Named<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(BenTenMod.MOD_ID, path).toString());
        }
    }
}


