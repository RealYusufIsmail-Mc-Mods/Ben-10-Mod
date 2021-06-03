package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagsInit {

    public static final class Blocks {
        ///ores
        public static final ITag.INamedTag<Block> ORES_BLACK_DIAMOND = forge("ores/black_diamond");
        public static final ITag.INamedTag<Block> ORES_RUBY = forge("ores/ruby");
        public static final ITag.INamedTag<Block> ORES_INFINITUM = forge("ores/infinitum");
        public static final ITag.INamedTag<Block> ORES_FIRE = forge("ores/fire");
        public static final ITag.INamedTag<Block> ORES_SPEED = forge("ores/speed");
        public static final ITag.INamedTag<Block> ORES_OMNITRIX = forge("ores/omnitrix");
        public static final ITag.INamedTag<Block> ORES_IMPERIUM = forge("ores/imperium");
        public static final ITag.INamedTag<Block> ORES_LEGENDARY_ORE_BLOCK = forge("ores/legendary_ore_block");
        public static final ITag.INamedTag<Block> ORES_DEEPSLATE_LEGENDARY_ORE_BLOCK = forge("ores/legendary_ore_block");
        public static final ITag.INamedTag<Block> ORES_DEEPSLATE_BLACK_DIAMOND = forge("ores/deepslate_black_diamond");
        public static final ITag.INamedTag<Block> ORES_DEEPSLATE_RUBY = forge("ores/deepslate_ruby");
        public static final ITag.INamedTag<Block> ORES_DEEPSLATE_SPEED = forge("ores/deepslate_speed");
        public static final ITag.INamedTag<Block> ORES_DEEPSLATE_OMNITRIX = forge("ores/deepslate_omnitrix");
        public static final ITag.INamedTag<Block> ORES_DEEPSLATE_IMPERIUM = forge("ores/deepslate_imperium");

        //blocks
        public static final ITag.INamedTag<Block> STORAGE_LEGENDARY = forge("storage_blocks/legendary");
        public static final ITag.INamedTag<Block> STORAGE_OMNITRIX = forge("storage_blocks/omnitrix");
        public static final ITag.INamedTag<Block> STORAGE_INFINITUM = forge("storage_blocks/infinitum");
        public static final ITag.INamedTag<Block> STORAGE_FIRE = forge("storage_blocks/fire");
        public static final ITag.INamedTag<Block> STORAGE_SPEED = forge("storage_blocks/speed");
        public static final ITag.INamedTag<Block> STORAGE_BLACK_DIAMOND = forge("storage_blocks/black_diamond");
        public static final ITag.INamedTag<Block> STORAGE_VILGAX = forge("storage_blocks/vilgax");

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(Main.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        //ores
        public static final ITag.INamedTag<Item> ORES_BLACK_DIAMOND = forge("ores/black_diamond");
        public static final ITag.INamedTag<Item> ORES_RUBY = forge("ores/ruby");
        public static final ITag.INamedTag<Item> ORES_INFINITUM = forge("ores/infinitum");
        public static final ITag.INamedTag<Item> ORES_FIRE = forge("ores/fire");
        public static final ITag.INamedTag<Item> ORES_SPEED = forge("ores/speed");
        public static final ITag.INamedTag<Item> ORES_OMNITRIX = forge("ores/omnitrix");
        public static final ITag.INamedTag<Item> ORES_IMPERIUM = forge("ores/imperium");
        public static final ITag.INamedTag<Item> ORES_DEEPSLATE_BLACK_DIAMOND = forge("ores/deepslate_black_diamond");
        public static final ITag.INamedTag<Item> ORES_DEEPSLATE_RUBY = forge("ores/deepslate_ruby");
        public static final ITag.INamedTag<Item> ORES_DEEPSLATE_SPEED = forge("ores/deepslate_speed");
        public static final ITag.INamedTag<Item> ORES_DEEPSLATE_OMNITRIX = forge("ores/deepslate_omnitrix");
        public static final ITag.INamedTag<Item> ORES_DEEPSLATE_IMPERIUM = forge("ores/deepslate_imperium");
        public static final ITag.INamedTag<Item> ORES_LEGENDARY_ORE_BLOCK = forge("ores/legendary_ore_block");
        public static final ITag.INamedTag<Item> ORES_DEEPSLATE_LEGENDARY_ORE_BLOCK = forge("ores/legendary_ore_block");
        //blocks
        public static final ITag.INamedTag<Item> STORAGE_LEGENDARY = forge("storage_blocks/legendary");
        public static final ITag.INamedTag<Item> STORAGE_OMNITRIX = forge("storage_blocks/omnitrix");
        public static final ITag.INamedTag<Item> STORAGE_INFINITUM = forge("storage_blocks/infinitum");
        public static final ITag.INamedTag<Item> STORAGE_FIRE = forge("storage_blocks/fire");
        public static final ITag.INamedTag<Item> STORAGE_SPEED = forge("storage_blocks/speed");
        public static final ITag.INamedTag<Item> STORAGE_BLACK_DIAMOND = forge("storage_blocks/black_diamond");
        public static final ITag.INamedTag<Item> STORAGE_VILGAX = forge("storage_blocks/vilgax");

        //items
        public static final ITag.INamedTag<Item> INGOTS_LEGENDARY_ORE = forge("ingots/legendary_ore");
        public static final ITag.INamedTag<Item> INGOTS_OMNITRIX = forge("ingots/omnitrix");
        public static final ITag.INamedTag<Item> INGOTS_FIRE = forge("ingots/fire");
        public static final ITag.INamedTag<Item> INGOTS_RUBY = forge("ingots/ruby");
        public static final ITag.INamedTag<Item> INGOTS_SPEED = forge("ingots/speed");
        public static final ITag.INamedTag<Item> INGOTS_BLACK_DIAMOND = forge("ingots/black_diamond");
        public static final ITag.INamedTag<Item> INGOTS_BLACK_DIAMOND_SCRAP = forge("ingots/black_diamond_scrap");
        public static final ITag.INamedTag<Item> INGOTS_INFINITUM = forge("ingots/infinitum");
        public static final ITag.INamedTag<Item> INGOTS_INFINITUM_SCRAP = ItemTags.bind("ingots/infinitum_scrap");

        //food


        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(Main.MOD_ID, path).toString());
        }
    }
}


