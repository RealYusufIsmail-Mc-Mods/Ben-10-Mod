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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagsInit {

    public static final class Blocks {
        /// ores
        public static final TagKey<Block> ORES_BLACK_DIAMONDS =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/black_diamonds"));
        public static final TagKey<Block> ORES_RUBYS =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/rubsy"));
        public static final TagKey<Block> ORES_INFINITUM =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/infinitum"));
        public static final TagKey<Block> ORES_FIRE =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/fire"));
        public static final TagKey<Block> ORES_SPEEDS =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/speeds"));
        public static final TagKey<Block> ORES_OMNITRIXS =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/omnitrixs"));
        public static final TagKey<Block> ORES_IMPERIUMS =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/imperiums"));
        public static final TagKey<Block> ORES_LEGENDARY_ORES =
                BlockTags.create(ResourceLocation.tryParse("forge:ores/legendary_ores"));
        // blocks
        public static final TagKey<Block> STORAGE_LEGENDARY =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/legendary"));
        public static final TagKey<Block> STORAGE_OMNITRIX =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/omnitrix"));
        public static final TagKey<Block> STORAGE_INFINITUM =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/infinitum"));
        public static final TagKey<Block> STORAGE_FIRE =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/fire"));
        public static final TagKey<Block> STORAGE_SPEED =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/speed"));
        public static final TagKey<Block> STORAGE_BLACK_DIAMOND =
                BlockTags.create(ResourceLocation.tryParse("forge:storage_blocks/black_diamond"));

        private Blocks() {}

        private static TagKey<Block> forge(String path) {
            return BlockTags
                .create(ResourceLocation.tryParse(new ResourceLocation("forge", path).toString()));
        }

        private static TagKey<Block> mod(String path) {
            return BlockTags.create(ResourceLocation
                .tryParse(new ResourceLocation(BenTenMod.MOD_ID, path).toString()));
        }
    }

    public static final class Items {
        // ores
        public static final TagKey<Item> ORES_BLACK_DIAMONDS =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/black_diamonds"));
        public static final TagKey<Item> ORES_RUBYS =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/rubys"));
        public static final TagKey<Item> ORES_INFINITUM =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/infinitum"));
        public static final TagKey<Item> ORES_FIRE =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/fire"));
        public static final TagKey<Item> ORES_SPEEDS =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/speeds"));
        public static final TagKey<Item> ORES_OMNITRIXS =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/omnitrixs"));
        public static final TagKey<Item> ORES_IMPERIUMS =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/imperiums"));
        public static final TagKey<Item> ORES_LEGENDARY_ORES =
                ItemTags.create(ResourceLocation.tryParse("forge:ores/legendary_ores"));
        // blocks
        public static final TagKey<Item> STORAGE_LEGENDARY =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/legendary"));
        public static final TagKey<Item> STORAGE_OMNITRIX =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/omnitrix"));
        public static final TagKey<Item> STORAGE_INFINITUM =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/infinitum"));
        public static final TagKey<Item> STORAGE_FIRE =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/fire"));
        public static final TagKey<Item> STORAGE_SPEED =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/speed"));
        public static final TagKey<Item> STORAGE_BLACK_DIAMOND =
                ItemTags.create(ResourceLocation.tryParse("forge:storage_blocks/black_diamond"));
        // items
        public static final TagKey<Item> INGOTS_LEGENDARY =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/legendary"));
        public static final TagKey<Item> INGOTS_OMNITRIX =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/omnitrix"));
        public static final TagKey<Item> INGOTS_FIRE =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/fire"));
        public static final TagKey<Item> INGOTS_RUBY =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/ruby"));
        public static final TagKey<Item> INGOTS_SPEED =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/speed"));
        public static final TagKey<Item> INGOTS_BLACK_DIAMOND =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/black_diamond"));
        public static final TagKey<Item> SCRAP_BLACK_DIAMOND_SCRAP =
                ItemTags.create(ResourceLocation.tryParse("forge:scraps/black_diamond_scrap"));
        public static final TagKey<Item> INGOTS_INFINITUM =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/infinitum"));
        public static final TagKey<Item> SCRAP_INFINITUM_SCRAP =
                ItemTags.create(ResourceLocation.tryParse("forge:scraps/infinitum_scrap"));
        public static final TagKey<Item> INGOTS_IMPERIUM =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/imperium"));
        public static final TagKey<Item> SPAW_EGGS =
                ItemTags.create(ResourceLocation.tryParse("forge:ingots/spawn_eggs"));
        // tools
        public static final TagKey<Item> TOOLS_IMPERIUM_PICKAXE =
                ItemTags.create(ResourceLocation.tryParse("forge:tools/imperium_pickaxe"));
        // raw ores
        public static final TagKey<Item> RAW_ORE =
                ItemTags.create(ResourceLocation.tryParse("forge:scraps/raw_ore"));

        private Items() {}

        private static TagKey<Item> forge(String path) {
            return ItemTags
                .create(ResourceLocation.tryParse(new ResourceLocation("forge", path).toString()));
        }

        private static TagKey<Item> mod(String path) {
            return ItemTags.create(ResourceLocation
                .tryParse(new ResourceLocation(BenTenMod.MOD_ID, path).toString()));
        }
    }
}


