package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagsInit {

    public static final class Blocks {
        ///ores
        public static final ITag.INamedTag<Block> ORES_BLACK_DIAMONDS = BlockTags.bind("forge:ores/black_diamonds");
        public static final ITag.INamedTag<Block> ORES_RUBYS = BlockTags.bind("forge:ores/rubsy");
        public static final ITag.INamedTag<Block> ORES_INFINITUM = BlockTags.bind("forge:ores/infinitum");
        public static final ITag.INamedTag<Block> ORES_FIRE = BlockTags.bind("forge:ores/fire");
        public static final ITag.INamedTag<Block> ORES_SPEEDS = BlockTags.bind("forge:ores/speeds");
        public static final ITag.INamedTag<Block> ORES_OMNITRIXS = BlockTags.bind("forge:ores/omnitrixs");
        public static final ITag.INamedTag<Block> ORES_IMPERIUMS = BlockTags.bind("forge:ores/imperiums");
        public static final ITag.INamedTag<Block> ORES_LEGENDARY_ORES = BlockTags.bind("forge:ores/legendary_ores");

        //blocks
        public static final ITag.INamedTag<Block> STORAGE_LEGENDARY = BlockTags.bind("forge:storage_blocks/legendary");
        public static final ITag.INamedTag<Block> STORAGE_OMNITRIX = BlockTags.bind("forge:storage_blocks/omnitrix");
        public static final ITag.INamedTag<Block> STORAGE_INFINITUM = BlockTags.bind("forge:storage_blocks/infinitum");
        public static final ITag.INamedTag<Block> STORAGE_FIRE = BlockTags.bind("forge:storage_blocks/fire");
        public static final ITag.INamedTag<Block> STORAGE_SPEED = BlockTags.bind("forge:storage_blocks/speed");
        public static final ITag.INamedTag<Block> STORAGE_BLACK_DIAMOND = BlockTags.bind("forge:storage_blocks/black_diamond");


        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(BenTenMod.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        //ores
        public static final ITag.INamedTag<Item> ORES_BLACK_DIAMONDS = ItemTags.bind("forge:ores/black_diamonds");
        public static final ITag.INamedTag<Item> ORES_RUBYS = ItemTags.bind("forge:ores/rubys");
        public static final ITag.INamedTag<Item> ORES_INFINITUM = ItemTags.bind("forge:ores/infinitum");
        public static final ITag.INamedTag<Item> ORES_FIRE = ItemTags.bind("forge:ores/fire");
        public static final ITag.INamedTag<Item> ORES_SPEEDS = ItemTags.bind("forge:ores/speeds");
        public static final ITag.INamedTag<Item> ORES_OMNITRIXS = ItemTags.bind("forge:ores/omnitrixs");
        public static final ITag.INamedTag<Item> ORES_IMPERIUMS = ItemTags.bind("forge:ores/imperiums");
        public static final ITag.INamedTag<Item> ORES_LEGENDARY_ORES = ItemTags.bind("forge:ores/legendary_ores");


        //blocks


        public static final ITag.INamedTag<Item> STORAGE_LEGENDARY = ItemTags.bind("forge:storage_blocks/legendary");
        public static final ITag.INamedTag<Item> STORAGE_OMNITRIX = ItemTags.bind("forge:storage_blocks/omnitrix");
        public static final ITag.INamedTag<Item> STORAGE_INFINITUM = ItemTags.bind("forge:storage_blocks/infinitum");
        public static final ITag.INamedTag<Item> STORAGE_FIRE = ItemTags.bind("forge:storage_blocks/fire");
        public static final ITag.INamedTag<Item> STORAGE_SPEED = ItemTags.bind("forge:storage_blocks/speed");
        public static final ITag.INamedTag<Item> STORAGE_BLACK_DIAMOND = ItemTags.bind("forge:storage_blocks/black_diamond");
        //items
        public static final ITag.INamedTag<Item> INGOTS_LEGENDARY_ORE = ItemTags.bind("forge:ingots/legendary_ore");
        public static final ITag.INamedTag<Item> INGOTS_OMNITRIX = ItemTags.bind("forge:ingots/omnitrix");
        public static final ITag.INamedTag<Item> INGOTS_FIRE = ItemTags.bind("forge:ingots/fire");
        public static final ITag.INamedTag<Item> INGOTS_RUBY = ItemTags.bind("forge:ingots/ruby");
        public static final ITag.INamedTag<Item> INGOTS_SPEED = ItemTags.bind("forge:ingots/speed");
        public static final ITag.INamedTag<Item> INGOTS_BLACK_DIAMOND = ItemTags.bind("forge:ingots/black_diamond");
        public static final ITag.INamedTag<Item> SCRAP_BLACK_DIAMOND_SCRAP = ItemTags.bind("forge:scraps/black_diamond_scrap");
        public static final ITag.INamedTag<Item> INGOTS_INFINITUM = ItemTags.bind("forge:ingots/infinitum");
        public static final ITag.INamedTag<Item> SCRAP_INFINITUM_SCRAP = ItemTags.bind("forge:scraps/infinitum_scrap");
        public static final ITag.INamedTag<Item> INGOTS_IMPERIUM = ItemTags.bind("forge:ingots/imperium");

        //tools
        public static final ITag.INamedTag<Item> TOOLS_IMPERIUM_PICKAXE = ItemTags.bind("forge:tools/imperium_pickaxe");

        //food
        public static final ITag.INamedTag<Item> RAW_CRAB = ItemTags.bind("forge:foods/raw_crab");

        //raw ores
        public static final ITag.INamedTag<Item> RAW_ORE = ItemTags.bind("forge:scraps/raw_ore");

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(BenTenMod.MOD_ID, path).toString());
        }
    }
}


