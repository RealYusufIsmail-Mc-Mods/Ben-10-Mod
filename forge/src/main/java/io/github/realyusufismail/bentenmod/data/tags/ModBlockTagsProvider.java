/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.data.tags;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.TagsInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;


public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper,
            CompletableFuture<HolderLookup.Provider> provider) {
        super(generatorIn.getPackOutput(), provider, BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // ores
        tag(TagsInit.Blocks.ORES_FIRE).add(BlockInit.FIRE_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_FIRE);

        tag(TagsInit.Blocks.ORES_OMNITRIXS).add(BlockInit.OMNITRIX_ORE.get(),
                BlockInit.DEEPSLATE_OMNITRIX_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_OMNITRIXS);

        tag(TagsInit.Blocks.ORES_INFINITUM).add(BlockInit.INFINITUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_INFINITUM);

        tag(TagsInit.Blocks.ORES_RUBYS).add(BlockInit.RUBY_ORE.get(),
                BlockInit.DEEPSLATE_RUBY_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_RUBYS);

        tag(TagsInit.Blocks.ORES_SPEEDS).add(BlockInit.SPEED_ORE.get(),
                BlockInit.DEEPSLATE_SPEED_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_SPEEDS);

        tag(TagsInit.Blocks.ORES_IMPERIUMS).add(BlockInit.IMPERIUM_ORE.get(),
                BlockInit.DEEPSLATE_IMPERIUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_IMPERIUMS);

        tag(TagsInit.Blocks.ORES_LEGENDARY_ORES).add(BlockInit.LEGENDARY_ORE.get(),
                BlockInit.DEEPSLATE_LEGENDARY_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_LEGENDARY_ORES);

        tag(TagsInit.Blocks.ORES_BLACK_DIAMONDS).add(BlockInit.BLACK_DIAMOND_ORE.get(),
                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_BLACK_DIAMONDS);

        tag(Tags.Blocks.ORES).add(BlockInit.NULL_CRYSTAL_ORE.get());
        tag(TagsInit.Blocks.ORES_TAYDENITE).add(BlockInit.TAYDENITE_ORE.get());
        tag(Tags.Blocks.ORES).addTag(TagsInit.Blocks.ORES_TAYDENITE);

        // blocks
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
        tag(TagsInit.Blocks.STORAGE_TAYDENITE).add(BlockInit.TAYDENITE_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.FIRE_ORE.get(),
                BlockInit.OMNITRIX_ORE.get(), BlockInit.DEEPSLATE_OMNITRIX_ORE.get(),
                BlockInit.INFINITUM_ORE.get(), BlockInit.RUBY_ORE.get(),
                BlockInit.DEEPSLATE_RUBY_ORE.get(), BlockInit.SPEED_ORE.get(),
                BlockInit.DEEPSLATE_SPEED_ORE.get(), BlockInit.IMPERIUM_ORE.get(),
                BlockInit.DEEPSLATE_IMPERIUM_ORE.get(), BlockInit.BLACK_DIAMOND_ORE.get(),
                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(), BlockInit.LEGENDARY_ORE.get(),
                BlockInit.DEEPSLATE_LEGENDARY_ORE.get(), BlockInit.FIRE_BLOCK.get(),
                BlockInit.OMNITRIX_BLOCK.get(), BlockInit.INFINITUM_BLOCK.get(),
                BlockInit.LEGENDARY_BLOCK.get(), BlockInit.SPEED_BLOCK.get(),
                BlockInit.BLACK_DIAMOND_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(BlockInit.FIRE_ORE.get(),
                BlockInit.OMNITRIX_ORE.get(), BlockInit.DEEPSLATE_OMNITRIX_ORE.get(),
                BlockInit.INFINITUM_ORE.get(), BlockInit.RUBY_ORE.get(),
                BlockInit.DEEPSLATE_RUBY_ORE.get(), BlockInit.SPEED_ORE.get(),
                BlockInit.DEEPSLATE_SPEED_ORE.get(), BlockInit.IMPERIUM_ORE.get(),
                BlockInit.DEEPSLATE_IMPERIUM_ORE.get(), BlockInit.BLACK_DIAMOND_ORE.get(),
                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(), BlockInit.LEGENDARY_ORE.get(),
                BlockInit.DEEPSLATE_LEGENDARY_ORE.get(), BlockInit.FIRE_BLOCK.get(),
                BlockInit.OMNITRIX_BLOCK.get(), BlockInit.INFINITUM_BLOCK.get(),
                BlockInit.LEGENDARY_BLOCK.get(), BlockInit.SPEED_BLOCK.get(),
                BlockInit.BLACK_DIAMOND_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.OMNITRIX_POD.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.OMNITRIX_POD.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.NULL_VOID_STONE.get(),
                BlockInit.NULL_VOID_STONE_BRICKS.get(), BlockInit.NULL_CRYSTAL_ORE.get(),
                BlockInit.TAYDENITE_ORE.get(), BlockInit.TAYDENITE_BLOCK.get(),
                BlockInit.PRISON_WALL.get(), BlockInit.PLUMBER_HULL.get(),
                BlockInit.PRISON_VAULT.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.NULL_CRYSTAL_ORE.get(),
                BlockInit.TAYDENITE_ORE.get(), BlockInit.TAYDENITE_BLOCK.get());
    }
}

