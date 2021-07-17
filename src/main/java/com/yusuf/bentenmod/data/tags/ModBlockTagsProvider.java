package com.yusuf.bentenmod.data.tags;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.TagsInit;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yusuf.realyusufismailcore.block.IBlockProvider;

import java.util.Arrays;

import static com.yusuf.bentenmod.core.init.BlockInit.*;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, BenTenMod.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags() {
        //ores
        tag(TagsInit.Blocks.ORES_BLACK_DIAMOND).add(BLACK_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_BLACK_DIAMOND);

        tag(TagsInit.Blocks.ORES_FIRE).add(FIRE_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_FIRE);

        tag(TagsInit.Blocks.ORES_OMNITRIX).add(OMNITRIX_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_OMNITRIX);

        tag(TagsInit.Blocks.ORES_INFINITUM).add(INFINITUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_INFINITUM);

        tag(TagsInit.Blocks.ORES_RUBY).add(RUBY_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_RUBY);

        tag(TagsInit.Blocks.ORES_SPEED).add(SPEED_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_SPEED);

        tag(TagsInit.Blocks.ORES_IMPERIUM).add(IMPERIUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_IMPERIUM);

        tag(TagsInit.Blocks.ORES_LEGENDARY_ORE_BLOCK).add(LEGENDARY_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_LEGENDARY_ORE_BLOCK);


        tag(TagsInit.Blocks.ORES_BLACK_DIAMOND).add(BLACK_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_BLACK_DIAMOND);


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

       // builder(TagsInit.Blocks.ORES_LEGENDARY_ORE_BLOCKS, BORT_ORE, ModBlocks.DEEPSLATE_BORT_ORE);

    }
    private void builder(ITag.INamedTag<Block> tag, IBlockProvider... items) {
        getBuilder(tag).add(Arrays.stream(items).map(IBlockProvider::asBlock).toArray(Block[]::new));
    }
    protected TagsProvider.Builder<Block> getBuilder(ITag.INamedTag<Block> tag) {
        return tag(tag);
    }

}

