package com.yusuf.bentenmod.data.tags;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;


public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //ores
        tag(TagsInit.Blocks.ORES_FIRE).add(BlockInit.FIRE_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_FIRE);

        tag(TagsInit.Blocks.ORES_OMNITRIXS).add(BlockInit.OMNITRIX_ORE.get(), BlockInit.DEEPSLATE_OMNITRIX_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_OMNITRIXS);

        tag(TagsInit.Blocks.ORES_INFINITUM).add(BlockInit.INFINITUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_INFINITUM);

        tag(TagsInit.Blocks.ORES_RUBYS).add(BlockInit.RUBY_ORE.get(), BlockInit.DEEPSLATE_RUBY_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_RUBYS);

        tag(TagsInit.Blocks.ORES_SPEEDS).add(BlockInit.SPEED_ORE.get(), BlockInit.DEEPSLATE_SPEED_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_SPEEDS);

        tag(TagsInit.Blocks.ORES_IMPERIUMS).add(BlockInit.IMPERIUM_ORE.get(), BlockInit.DEEPSLATE_IMPERIUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_IMPERIUMS);

        tag(TagsInit.Blocks.ORES_LEGENDARY_ORES).add(BlockInit.LEGENDARY_ORE.get(), BlockInit.DEEPSLATE_LEGENDARY_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_LEGENDARY_ORES);

        tag(TagsInit.Blocks.ORES_BLACK_DIAMONDS).add(BlockInit.BLACK_DIAMOND_ORE.get(), BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_BLACK_DIAMONDS);

        //blocks
        tag(TagsInit.Blocks.STORAGE_LEGENDARY).add(BlockInit.LEGENDARY_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_LEGENDARY);

        tag(TagsInit.Blocks.STORAGE_OMNITRIX).add(BlockInit.OMNITRIX_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_OMNITRIX);

        tag(TagsInit.Blocks.STORAGE_INFINITUM).add(BlockInit.INFINITUM_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_INFINITUM);

        tag(TagsInit.Blocks.STORAGE_FIRE).add(BlockInit.FIRE_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_FIRE);

        tag(TagsInit.Blocks.STORAGE_SPEED).add(BlockInit.SPEED_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_SPEED);

        tag(TagsInit.Blocks.STORAGE_BLACK_DIAMOND).add(BlockInit.BLACK_DIAMOND_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.STORAGE_BLACK_DIAMOND);

    }
}

