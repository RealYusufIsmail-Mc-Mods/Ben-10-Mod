
package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.Tag;
public class TagsInit {
private TagsInit() {}
    public static final class Blocks {
        ///ores
        public static final Tag<Block> ORES_BLACK_DIAMOND = forge("ores/black_diamond");
        public static final Tag<Block> ORES_RUBY = forge("ores/ruby");
        public static final Tag<Block> ORES_INFINITUM = forge("ores/infinitum");
        public static final Tag<Block> ORES_FIRE = forge("ores/fire");
        public static final Tag<Block> ORES_SPEED = forge("ores/speed");
        public static final Tag<Block> ORES_OMNITRIX = forge("ores/omnitrix");
        public static final Tag<Block> ORES_IMPERIUM = forge("ores/imperium");

        //blocks
        public static final Tag<Block> STORAGE_LEGENDARY = forge("storage_blocks/legendary");
        public static final Tag<Block> STORAGE_OMNITRIX = forge("storage_blocks/omnitrix");
        public static final Tag<Block> STORAGE_INFINITUM = forge("storage_blocks/infinitum");
        public static final Tag<Block> STORAGE_FIRE = forge("storage_blocks/fire");
        public static final Tag<Block> STORAGE_SPEED = forge("storage_blocks/speed");
        public static final Tag<Block> STORAGE_BLACK_DIAMOND = forge("storage_blocks/black_diamond");
        public static final Tag<Block> STORAGE_VILGAX = forge("storage_blocks/vilgax");

        private Blocks() {}
        private static Tag<Block> forge(String path) {
            return new BlockTags.Wrapper(new ResourceLocation("forge", path));
        }
    }

    public static final class Items {
        //ores
        public static final Tag<Item> ORES_BLACK_DIAMOND = forge("ores/black_diamond");
        public static final Tag<Item> ORES_RUBY = forge("ores/ruby");
        public static final Tag<Item> ORES_INFINITUM = forge("ores/infinitum");
        public static final Tag<Item> ORES_FIRE = forge("ores/fire");
        public static final Tag<Item> ORES_SPEED = forge("ores/speed");
        public static final Tag<Item> ORES_OMNITRIX = forge("ores/omnitrix");
        public static final Tag<Item> ORES_IMPERIUM = forge("ores/imperium");

        //blocks
        public static final Tag<Item> STORAGE_LEGENDARY = forge("storage_blocks/legendary");
        public static final Tag<Item> STORAGE_OMNITRIX = forge("storage_blocks/omnitrix");
        public static final Tag<Item> STORAGE_INFINITUM = forge("storage_blocks/infinitum");
        public static final Tag<Item> STORAGE_FIRE = forge("storage_blocks/fire");
        public static final Tag<Item> STORAGE_SPEED = forge("storage_blocks/speed");
        public static final Tag<Item> STORAGE_BLACK_DIAMOND = forge("storage_blocks/black_diamond");
        public static final Tag<Item> STORAGE_VILGAX = forge("storage_blocks/vilgax");

        //items
        public static final Tag<Item> INGOTS_LEGENDARY_ORE = forge("ingots/legendary_ore");
        public static final Tag<Item> INGOTS_OMNITRIX = forge("ingots/omnitrix");
        public static final Tag<Item> INGOTS_FIRE = forge("ingots/fire");
        public static final Tag<Item> INGOTS_RUBY = forge("ingots/ruby");
        public static final Tag<Item> INGOTS_SPEED = forge("ingots/speed");
        public static final Tag<Item> INGOTS_BLACK_DIAMOND = forge("ingots/black_diamond");
        public static final Tag<Item> INGOTS_BLACK_DIAMOND_SCRAP = forge("ingots/black_diamond_scrap");
        public static final Tag<Item> INGOTS_INFINITUM = forge("ingots/infinitum");
        public static final Tag<Item> INGOTS_INFINITUM_SCRAP = forge("ingots/infinitum_scrap");

        //food
        private Items() {}

        private static Tag<Item> mod(String path) {
            return new ItemTags.Wrapper(Main.getId(path));
        }
        private static Tag<Item> forge(String path) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", path));
        }
        private static Tag<Item> benTen(String path) {
            return new ItemTags.Wrapper(new ResourceLocation("ben_ten", path));
        }
        private static ResourceLocation forgeId(String path) {
            return new ResourceLocation("forge", path);
        }
    }
    private static ResourceLocation modId(String path) {
        return Main.getId(path);
    }
}




