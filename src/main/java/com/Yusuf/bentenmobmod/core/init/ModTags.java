package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static final class Blocks {
        public static final ITag.INamedTag<Block> ORES_BLACK_DIAMOND = forge("ores/black_diamond");
        public static final ITag.INamedTag<Block> ORES_RUBY = forge("ores/ruby");
        public static final ITag.INamedTag<Block> ORES_INFINITUM = forge("ores/infinitum");
        public static final ITag.INamedTag<Block> ORES_FIRE = forge("ores/fire");
        public static final ITag.INamedTag<Block> ORES_SPEED = forge("ores/speed");
        public static final ITag.INamedTag<Block> ORES_OMNITRIX = forge("ores/omnitrix");



        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(Main.MOD_ID, path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> ORES_BLACK_DIAMOND = forge("ores/black_diamond");
        public static final ITag.INamedTag<Item> ORES_RUBY = forge("ores/ruby");
        public static final ITag.INamedTag<Item> ORES_INFINITUM = forge("ores/infinitum");
        public static final ITag.INamedTag<Item> ORES_FIRE = forge("ores/fire");
        public static final ITag.INamedTag<Item> ORES_SPEED = forge("ores/speed");
        public static final ITag.INamedTag<Item> ORES_OMNITRIX = forge("ores/omnitrix");

        public static final ITag.INamedTag<Item> INGOTS_LEGENDARY_ORE = forge("ingots/legendary_ore");
        public static final ITag.INamedTag<Item> INGOTS_OMNITRIX = forge("ingots/omnitrix");
        public static final ITag.INamedTag<Item> INGOTS_FIRE = forge("ingots/fire");
        public static final ITag.INamedTag<Item> INGOTS_RUBY = forge("ingots/ruby");
        public static final ITag.INamedTag<Item> INGOTS_SPEED = forge("ingots/speed");
        public static final ITag.INamedTag<Item> INGOTS_BLACK_DIAMOND = forge("ingots/black_diamond");
        public static final ITag.INamedTag<Item> INGOTS_BLACK_DIAMOND_SCRAP = forge("ingots/black_diamond_scrap");
        public static final ITag.INamedTag<Item> INGOTS_INFINITUM = forge("ingots/infinitum");
        public static final ITag.INamedTag<Item> INGOTS_INFINITUM_SCRAP = forge("ingots/infinitum_scrap");

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(Main.MOD_ID, path).toString());
        }
    }
}


