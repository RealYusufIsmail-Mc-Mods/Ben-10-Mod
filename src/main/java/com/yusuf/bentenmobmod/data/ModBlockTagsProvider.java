
package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import com.yusuf.bentenmobmod.core.init.TagsInit;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.Tags;

import java.util.Arrays;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Ben Ten Mob Mod - Block Tags";
    }


    @Override
    protected void registerTags() {
        //ores
        getBuilder(TagsInit.Blocks.ORES_BLACK_DIAMOND).add(BlockInit.BLACK_DIAMOND_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_BLACK_DIAMOND);

        getBuilder(TagsInit.Blocks.ORES_FIRE).add(BlockInit.FIRE_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_FIRE);

        getBuilder(TagsInit.Blocks.ORES_OMNITRIX).add(BlockInit.OMNITRIX_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_OMNITRIX);

        getBuilder(TagsInit.Blocks.ORES_INFINITUM).add(BlockInit.INFINITUM_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_INFINITUM);

        getBuilder(TagsInit.Blocks.ORES_RUBY).add(BlockInit.RUBY_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_RUBY);

        getBuilder(TagsInit.Blocks.ORES_BLACK_DIAMOND).add(BlockInit.BLACK_DIAMOND_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_BLACK_DIAMOND);

        getBuilder(TagsInit.Blocks.ORES_SPEED).add(BlockInit.SPEED_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_SPEED);

        getBuilder(TagsInit.Blocks.ORES_IMPERIUM).add(BlockInit.IMPERIUM_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.ORES_IMPERIUM);

        //blocks
        getBuilder(TagsInit.Blocks.STORAGE_LEGENDARY).add(BlockInit.LEGENDARY_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_LEGENDARY);

        getBuilder(TagsInit.Blocks.STORAGE_OMNITRIX).add(BlockInit.OMNITRIX_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_OMNITRIX);

        getBuilder(TagsInit.Blocks.STORAGE_INFINITUM).add(BlockInit.INFINITUM_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_INFINITUM);

        getBuilder(TagsInit.Blocks.STORAGE_FIRE).add(BlockInit.FIRE_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_FIRE);

        getBuilder(TagsInit.Blocks.STORAGE_SPEED).add(BlockInit.SPEED_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_SPEED);

        getBuilder(TagsInit.Blocks.STORAGE_BLACK_DIAMOND).add(BlockInit.BLACK_DIAMOND_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_BLACK_DIAMOND);

        getBuilder(TagsInit.Blocks.STORAGE_VILGAX).add(BlockInit.VILGAX_BLOCK.get());
        getBuilder(Tags.Blocks.ORES).add(TagsInit.Blocks.STORAGE_VILGAX);
    }

}



