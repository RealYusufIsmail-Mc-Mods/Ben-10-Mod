
package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.core.init.ItemInit;
import com.yusuf.bentenmobmod.core.init.TagsInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }
    @Override
    public String getName() {
        return "Bent Ten Mob Mod - Item Tags";
    }
    @Override
    protected void registerTags() {
        //ores
        copy(TagsInit.Blocks.ORES_BLACK_DIAMOND, TagsInit.Items.ORES_BLACK_DIAMOND);
        copy(TagsInit.Blocks.ORES_INFINITUM, TagsInit.Items.ORES_INFINITUM);
        copy(TagsInit.Blocks.ORES_RUBY, TagsInit.Items.ORES_RUBY);
        copy(TagsInit.Blocks.ORES_FIRE, TagsInit.Items.ORES_FIRE);
        copy(TagsInit.Blocks.ORES_SPEED, TagsInit.Items.ORES_SPEED);
        copy(TagsInit.Blocks.ORES_OMNITRIX, TagsInit.Items.ORES_OMNITRIX);
        copy(TagsInit.Blocks.ORES_IMPERIUM, TagsInit.Items.ORES_IMPERIUM);

        //blocks
        copy(TagsInit.Blocks.STORAGE_LEGENDARY, TagsInit.Items.STORAGE_LEGENDARY);
        copy(TagsInit.Blocks.STORAGE_OMNITRIX, TagsInit.Items.STORAGE_OMNITRIX);
        copy(TagsInit.Blocks.STORAGE_INFINITUM, TagsInit.Items.STORAGE_INFINITUM);
        copy(TagsInit.Blocks.STORAGE_FIRE, TagsInit.Items.STORAGE_FIRE);
        copy(TagsInit.Blocks.STORAGE_SPEED, TagsInit.Items.STORAGE_SPEED);
        copy(TagsInit.Blocks.STORAGE_BLACK_DIAMOND, TagsInit.Items.STORAGE_BLACK_DIAMOND);
        copy(TagsInit.Blocks.STORAGE_VILGAX, TagsInit.Items.STORAGE_VILGAX);

        //ingots
        getBuilder(TagsInit.Items.INGOTS_LEGENDARY_ORE).add(ItemInit.LEGENDARY_ORE.get());
        getBuilder(TagsInit.Items.INGOTS_OMNITRIX).add(ItemInit.OMNITRIX.get());
        getBuilder(TagsInit.Items.INGOTS_FIRE).add(ItemInit.FIRE.get());
        getBuilder(TagsInit.Items.INGOTS_RUBY).add(ItemInit.RUBY.get());
        getBuilder(TagsInit.Items.INGOTS_SPEED).add(ItemInit.SPEED.get());
        getBuilder(TagsInit.Items.INGOTS_BLACK_DIAMOND).add(ItemInit.BLACK_DIAMOND.get());
        getBuilder(TagsInit.Items.INGOTS_BLACK_DIAMOND_SCRAP).add(ItemInit.BLACK_DIAMOND_SCRAP.get());
        getBuilder(TagsInit.Items.INGOTS_INFINITUM).add(ItemInit.INFINITUM.get());
        getBuilder(TagsInit.Items.INGOTS_INFINITUM_SCRAP).add(ItemInit.INFINITUM_SCRAP.get());


    }
}


