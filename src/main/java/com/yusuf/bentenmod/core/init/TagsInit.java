package com.yusuf.bentenmod.core.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class TagsInit {

    public static final class Blocks {
        ///ores
        public static final ITag.INamedTag<Block> ORES_BLACK_DIAMOND = forgeOres("black_diamond");
        public static final ITag.INamedTag<Block> ORES_RUBY = forgeOres("ruby");
        public static final ITag.INamedTag<Block> ORES_INFINITUM = forgeOres("infinitum");
        public static final ITag.INamedTag<Block> ORES_FIRE = forgeOres("fire");
        public static final ITag.INamedTag<Block> ORES_SPEED = forgeOres("speed");
        public static final ITag.INamedTag<Block> ORES_OMNITRIX = forgeOres("omnitrix");
        public static final ITag.INamedTag<Block> ORES_IMPERIUM = forgeOres("imperium");
        //blocks
        public static final ITag.INamedTag<Block> STORAGE_LEGENDARY = storageBlock("legendary");
        public static final ITag.INamedTag<Block> STORAGE_OMNITRIX = storageBlock("omnitrix");
        public static final ITag.INamedTag<Block> STORAGE_INFINITUM = storageBlock("infinitum");
        public static final ITag.INamedTag<Block> STORAGE_FIRE = storageBlock("fire");
        public static final ITag.INamedTag<Block> STORAGE_SPEED = storageBlock("speed");
        public static final ITag.INamedTag<Block> STORAGE_BLACK_DIAMOND = storageBlock("black_diamond");
        public static final ITag.INamedTag<Block> STORAGE_VILGAX = storageBlock("vilgax");

        private static ITag.INamedTag<Block> storageBlock(String entry) {
            //TODO : check if a string entry have "forge:/ores"
            for (int i = 0; i < entry.length(); i++) {
                if (entry.charAt(i) == ':') {
                    throw new IllegalStateException("the method is already have 'forge:storage_blocks/' entry");
                }
            }
            forge("forge:storage_blocks");
            return forge("forge:storage_blocks/" + entry);
        }

        private static ITag.INamedTag<Block> forgeOres(String entry) {
            //TODO : check if a string entry have "forge:/ores"
            for (int i = 0; i < entry.length(); i++) {
                if (entry.charAt(i) == ':') {
                    throw new IllegalStateException("the method is already have 'forge:ores/' entry");
                }
            }

            forge("forge:ores");
            return forge("forge:ores/" + entry);
        }

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(path);
        }

        /*private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(BenTenMod.MOD_ID, path).toString());
        }*/
    }

    public static final class Items {
        //ores
        public static final ITag.INamedTag<Item> ORES_BLACK_DIAMOND = forgeOres("black_diamond");
        public static final ITag.INamedTag<Item> ORES_RUBY = forgeOres("ruby");
        public static final ITag.INamedTag<Item> ORES_INFINITUM = forgeOres("infinitum");
        public static final ITag.INamedTag<Item> ORES_FIRE = forgeOres("fire");
        public static final ITag.INamedTag<Item> ORES_SPEED = forgeOres("speed");
        public static final ITag.INamedTag<Item> ORES_OMNITRIX = forgeOres("omnitrix");
        public static final ITag.INamedTag<Item> ORES_IMPERIUM = forgeOres("imperium");
        //blocks
        public static final ITag.INamedTag<Item> STORAGE_LEGENDARY = storageBlock("legendary");
        public static final ITag.INamedTag<Item> STORAGE_OMNITRIX = storageBlock("omnitrix");
        public static final ITag.INamedTag<Item> STORAGE_INFINITUM = storageBlock("infinitum");
        public static final ITag.INamedTag<Item> STORAGE_FIRE = storageBlock("fire");
        public static final ITag.INamedTag<Item> STORAGE_SPEED = storageBlock("speed");
        public static final ITag.INamedTag<Item> STORAGE_BLACK_DIAMOND = storageBlock("black_diamond");
        public static final ITag.INamedTag<Item> STORAGE_VILGAX = storageBlock("vilgax");

        //items / ingot
        public static final ITag.INamedTag<Item> INGOTS_LEGENDARY_ORE = ingots("legendary_ore");
        public static final ITag.INamedTag<Item> INGOTS_OMNITRIX = ingots("omnitrix");
        public static final ITag.INamedTag<Item> INGOTS_FIRE = ingots("fire");
        public static final ITag.INamedTag<Item> INGOTS_RUBY = ingots("ruby");
        public static final ITag.INamedTag<Item> INGOTS_SPEED = ingots("speed");
        public static final ITag.INamedTag<Item> INGOTS_BLACK_DIAMOND = ingots("black_diamond");
        public static final ITag.INamedTag<Item> SCRAP_BLACK_DIAMOND_SCRAP = forge("forge:scraps/black_diamond_scrap");
        public static final ITag.INamedTag<Item> INGOTS_INFINITUM = ingots("infinitum");
        public static final ITag.INamedTag<Item> SCRAP_INFINITUM_SCRAP = forge("forge:scraps/infinitum_scrap");
        public static final ITag.INamedTag<Item> INGOTS_IMPERIUM = ingots("imperium");
        //food


        private static ITag.INamedTag<Item> forgeOres(String entry) {
            //TODO : check if a string entry have "forge:/ores"
            for (int i = 0; i < entry.length(); i++) {
                if (entry.charAt(i) == ':') {
                    throw new IllegalStateException("the method is already have 'forge:ores/' entry");
                }
            }
            forge("forge:ores");
            return forge("forge:ores/" + entry);
        }

        private static ITag.INamedTag<Item> storageBlock(String entry) {
            //TODO : check if a string entry have "forge:/ores"
            for (int i = 0; i < entry.length(); i++) {
                if (entry.charAt(i) == ':') {
                    throw new IllegalStateException("the method is already have 'forge:storage_blocks/' entry");
                }
            }
            forge("forge:storage_blocks");

            return forge("forge:storage_blocks/" + entry);
        }

        private static ITag.INamedTag<Item> ingots(String path) {
            //TODO : check if a string entry have "forge:/ores"
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == ':') {
                    throw new IllegalStateException("the method is already have 'forge:ingots/' entry");
                }
            }
            forge("forge:ingots");
            return forge("forge:ingots/" + path);
        }

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(path);
        }

        /*private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(BenTenMod.MOD_ID, path).toString());
        }*/
    }
}
