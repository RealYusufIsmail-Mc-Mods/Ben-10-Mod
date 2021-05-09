package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.init.TagsInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Main.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags() {
        copy(TagsInit.Blocks.ORES_BLACK_DIAMOND, TagsInit.Items.ORES_BLACK_DIAMOND);
    }
}
