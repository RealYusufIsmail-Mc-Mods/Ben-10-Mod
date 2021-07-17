package com.yusuf.bentenmod.data.tags;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.TagsInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.yusuf.bentenmod.core.init.BlockInit.*;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //ores
        tag(TagsInit.Blocks.ORES_FIRE).add(FIRE_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_FIRE);

        tag(TagsInit.Blocks.ORES_OMNITRIXS).add(OMNITRIX_ORE.get(), DEEPSLATE_OMNITRIX_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_OMNITRIXS);

        tag(TagsInit.Blocks.ORES_INFINITUM).add(INFINITUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_INFINITUM);

        tag(TagsInit.Blocks.ORES_RUBYS).add(RUBY_ORE.get(), DEEPSLATE_RUBY_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_RUBYS);

        tag(TagsInit.Blocks.ORES_SPEEDS).add(SPEED_ORE.get(), DEEPSLATE_SPEED_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_SPEEDS);

        tag(TagsInit.Blocks.ORES_IMPERIUMS).add(IMPERIUM_ORE.get(), DEEPSLATE_IMPERIUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_IMPERIUMS);

        tag(TagsInit.Blocks.ORES_LEGENDARY_ORE_BLOCKS).add(LEGENDARY_BLOCK.get(), DEEPSLATE_LEGENDARY_ORE_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_LEGENDARY_ORE_BLOCKS);

        tag(TagsInit.Blocks.ORES_BLACK_DIAMONDS).add(BLACK_DIAMOND_ORE.get(), DEEPSLATE_BLACK_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_BLACK_DIAMONDS);

        //blocks
        tag(TagsInit.Blocks.STORAGE_LEGENDARY).add(LEGENDARY_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_LEGENDARY);

        tag(TagsInit.Blocks.STORAGE_OMNITRIX).add(OMNITRIX_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_OMNITRIX);

        tag(TagsInit.Blocks.STORAGE_INFINITUM).add(INFINITUM_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_INFINITUM);

        tag(TagsInit.Blocks.STORAGE_FIRE).add(FIRE_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_FIRE);

        tag(TagsInit.Blocks.STORAGE_SPEED).add(SPEED_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_SPEED);

        tag(TagsInit.Blocks.STORAGE_BLACK_DIAMOND).add(BLACK_DIAMOND_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_BLACK_DIAMOND);

    }
}

