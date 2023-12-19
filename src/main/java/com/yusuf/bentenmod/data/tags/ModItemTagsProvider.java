/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.yusuf.bentenmod.data.tags;

import static com.yusuf.bentenmod.core.init.EntityTypesInit.KRAAB_SPAWN_EGG;
import static com.yusuf.bentenmod.core.init.EntityTypesInit.VILGAX_SPAWN_EGG;
import static com.yusuf.bentenmod.core.init.ItemInit.*;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.TagsInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(
            DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // ores
        copy(TagsInit.Blocks.ORES_BLACK_DIAMONDS, TagsInit.Items.ORES_BLACK_DIAMONDS);
        copy(TagsInit.Blocks.ORES_INFINITUM, TagsInit.Items.ORES_INFINITUM);
        copy(TagsInit.Blocks.ORES_RUBYS, TagsInit.Items.ORES_RUBYS);
        copy(TagsInit.Blocks.ORES_FIRE, TagsInit.Items.ORES_FIRE);
        copy(TagsInit.Blocks.ORES_SPEEDS, TagsInit.Items.ORES_SPEEDS);
        copy(TagsInit.Blocks.ORES_OMNITRIXS, TagsInit.Items.ORES_OMNITRIXS);
        copy(TagsInit.Blocks.ORES_IMPERIUMS, TagsInit.Items.ORES_IMPERIUMS);
        copy(TagsInit.Blocks.ORES_LEGENDARY_ORES, TagsInit.Items.ORES_LEGENDARY_ORES);

        // blocks
        copy(TagsInit.Blocks.STORAGE_LEGENDARY, TagsInit.Items.STORAGE_LEGENDARY);
        copy(TagsInit.Blocks.STORAGE_OMNITRIX, TagsInit.Items.STORAGE_OMNITRIX);
        copy(TagsInit.Blocks.STORAGE_INFINITUM, TagsInit.Items.STORAGE_INFINITUM);
        copy(TagsInit.Blocks.STORAGE_FIRE, TagsInit.Items.STORAGE_FIRE);
        copy(TagsInit.Blocks.STORAGE_SPEED, TagsInit.Items.STORAGE_SPEED);
        copy(TagsInit.Blocks.STORAGE_BLACK_DIAMOND, TagsInit.Items.STORAGE_BLACK_DIAMOND);

        // ingots
        tag(TagsInit.Items.INGOTS_LEGENDARY).add(LEGENDARY.get());
        tag(TagsInit.Items.INGOTS_OMNITRIX).add(OMNITRIX.get());
        tag(TagsInit.Items.INGOTS_FIRE).add(FIRE.get());
        tag(TagsInit.Items.INGOTS_RUBY).add(RUBY.get());
        tag(TagsInit.Items.INGOTS_SPEED).add(SPEED.get());
        tag(TagsInit.Items.INGOTS_BLACK_DIAMOND).add(BLACK_DIAMOND.get());
        tag(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP).add(BLACK_DIAMOND_SCRAP.get());
        tag(TagsInit.Items.INGOTS_INFINITUM).add(INFINITUM.get());
        tag(TagsInit.Items.SCRAP_INFINITUM_SCRAP).add(INFINITUM_SCRAP.get());
        tag(TagsInit.Items.INGOTS_IMPERIUM).add(IMPERIUM.get());

        // tools
        tag(TagsInit.Items.TOOLS_IMPERIUM_PICKAXE).add(IMPERIUM_PICKAXE.get());

        // raw ores
        tag(TagsInit.Items.RAW_ORE).add(RAW_LEGENDARY.get(), RAW_OMNITRIX.get(), RAW_IMPERIUM.get());

        // others
        tag(TagsInit.Items.SPAW_EGGS).add(VILGAX_SPAWN_EGG.get(), KRAAB_SPAWN_EGG.get());
    }
}
