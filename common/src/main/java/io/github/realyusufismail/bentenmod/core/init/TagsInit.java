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
package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagsInit {

    public static final class Blocks {
        /// ores
        public static final TagKey<Block> ORES_BLACK_DIAMONDS = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:ores/black_diamonds"));
        public static final TagKey<Block> ORES_RUBYS =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/rubsy"));
        public static final TagKey<Block> ORES_INFINITUM =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/infinitum"));
        public static final TagKey<Block> ORES_FIRE =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/fire"));
        public static final TagKey<Block> ORES_SPEEDS =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/speeds"));
        public static final TagKey<Block> ORES_OMNITRIXS =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/omnitrixs"));
        public static final TagKey<Block> ORES_IMPERIUMS =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/imperiums"));
        public static final TagKey<Block> ORES_LEGENDARY_ORES = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:ores/legendary_ores"));
        public static final TagKey<Block> ORES_TAYDENITE =
                TagKey.create(Registries.BLOCK, ResourceLocation.tryParse("forge:ores/taydenite"));
        // blocks
        public static final TagKey<Block> STORAGE_LEGENDARY = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/legendary"));
        public static final TagKey<Block> STORAGE_OMNITRIX = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/omnitrix"));
        public static final TagKey<Block> STORAGE_INFINITUM = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/infinitum"));
        public static final TagKey<Block> STORAGE_FIRE = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/fire"));
        public static final TagKey<Block> STORAGE_SPEED = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/speed"));
        public static final TagKey<Block> STORAGE_BLACK_DIAMOND = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/black_diamond"));
        public static final TagKey<Block> STORAGE_TAYDENITE = TagKey.create(Registries.BLOCK,
                ResourceLocation.tryParse("forge:storage_blocks/taydenite"));

        private Blocks() {}

        private static TagKey<Block> forge(String path) {
            return TagKey.create(Registries.BLOCK,
                    ResourceLocation.tryParse(new ResourceLocation("forge", path).toString()));
        }

        private static TagKey<Block> mod(String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation
                .tryParse(new ResourceLocation(BenTenMod.MOD_ID, path).toString()));
        }
    }

    public static final class Items {
        // ores
        public static final TagKey<Item> ORES_BLACK_DIAMONDS = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:ores/black_diamonds"));
        public static final TagKey<Item> ORES_RUBYS =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/rubys"));
        public static final TagKey<Item> ORES_INFINITUM =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/infinitum"));
        public static final TagKey<Item> ORES_FIRE =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/fire"));
        public static final TagKey<Item> ORES_SPEEDS =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/speeds"));
        public static final TagKey<Item> ORES_OMNITRIXS =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/omnitrixs"));
        public static final TagKey<Item> ORES_IMPERIUMS =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/imperiums"));
        public static final TagKey<Item> ORES_LEGENDARY_ORES = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:ores/legendary_ores"));
        public static final TagKey<Item> ORES_TAYDENITE =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ores/taydenite"));
        // blocks
        public static final TagKey<Item> STORAGE_LEGENDARY = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:storage_blocks/legendary"));
        public static final TagKey<Item> STORAGE_OMNITRIX = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:storage_blocks/omnitrix"));
        public static final TagKey<Item> STORAGE_INFINITUM = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:storage_blocks/infinitum"));
        public static final TagKey<Item> STORAGE_FIRE = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:storage_blocks/fire"));
        public static final TagKey<Item> STORAGE_SPEED = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:storage_blocks/speed"));
        public static final TagKey<Item> STORAGE_BLACK_DIAMOND = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:storage_blocks/black_diamond"));
        // items
        public static final TagKey<Item> INGOTS_LEGENDARY =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/legendary"));
        public static final TagKey<Item> INGOTS_OMNITRIX =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/omnitrix"));
        public static final TagKey<Item> INGOTS_FIRE =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/fire"));
        public static final TagKey<Item> INGOTS_RUBY =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/ruby"));
        public static final TagKey<Item> INGOTS_SPEED =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/speed"));
        public static final TagKey<Item> INGOTS_BLACK_DIAMOND = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:ingots/black_diamond"));
        public static final TagKey<Item> SCRAP_BLACK_DIAMOND_SCRAP = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:scraps/black_diamond_scrap"));
        public static final TagKey<Item> INGOTS_INFINITUM =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/infinitum"));
        public static final TagKey<Item> SCRAP_INFINITUM_SCRAP = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:scraps/infinitum_scrap"));
        public static final TagKey<Item> INGOTS_IMPERIUM =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:ingots/imperium"));
        public static final TagKey<Item> SPAW_EGGS = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:ingots/spawn_eggs"));
        // tools
        public static final TagKey<Item> TOOLS_IMPERIUM_PICKAXE = TagKey.create(Registries.ITEM,
                ResourceLocation.tryParse("forge:tools/imperium_pickaxe"));
        // raw ores
        public static final TagKey<Item> RAW_ORE =
                TagKey.create(Registries.ITEM, ResourceLocation.tryParse("forge:scraps/raw_ore"));

        private Items() {}

        private static TagKey<Item> forge(String path) {
            return TagKey.create(Registries.ITEM,
                    ResourceLocation.tryParse(new ResourceLocation("forge", path).toString()));
        }

        private static TagKey<Item> mod(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation
                .tryParse(new ResourceLocation(BenTenMod.MOD_ID, path).toString()));
        }
    }
}


