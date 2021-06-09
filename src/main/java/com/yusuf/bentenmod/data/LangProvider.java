package com.yusuf.bentenmod.data;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class LangProvider extends LanguageProvider {

    public LangProvider(DataGenerator gen) {
        super(gen, Main.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //block
        block(BlockInit.LEGENDARY_BLOCK, "Legerndary Block");
        block(BlockInit.OMNITRIX_BLOCK, "Omnitrix Block");
        block(BlockInit.BLACK_DIAMOND_BLOCK, "Black Diamond Block");
        block(BlockInit.INFINITUM_BLOCK, "Infinitun Block");
        block(BlockInit.FIRE_BLOCK, "FIre Block");
        block(BlockInit.SPEED_BLOCK, "Speed Block");
        block(BlockInit.RUBY_BLOCK, "Ruby Block");
        block(BlockInit.IMPERIUM_BLOCK, "Imperium Block");
        block(BlockInit.TABLE_BLOCK, "Upgrade and Crafting Table");

        //ores



    }


    private <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
    private <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }
}
