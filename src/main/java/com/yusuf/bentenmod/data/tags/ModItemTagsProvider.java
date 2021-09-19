/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.data.tags;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.TagsInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.yusuf.bentenmod.core.init.EntityTypesInit.KRAAB_SPAWN_EGG;
import static com.yusuf.bentenmod.core.init.EntityTypesInit.VILGAX_SPAWN_EGG;
import static com.yusuf.bentenmod.core.init.ItemInit.*;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //ores
        copy(TagsInit.Blocks.ORES_BLACK_DIAMONDS, TagsInit.Items.ORES_BLACK_DIAMONDS);
        copy(TagsInit.Blocks.ORES_INFINITUM, TagsInit.Items.ORES_INFINITUM);
        copy(TagsInit.Blocks.ORES_RUBYS, TagsInit.Items.ORES_RUBYS);
        copy(TagsInit.Blocks.ORES_FIRE, TagsInit.Items.ORES_FIRE);
        copy(TagsInit.Blocks.ORES_SPEEDS, TagsInit.Items.ORES_SPEEDS);
        copy(TagsInit.Blocks.ORES_OMNITRIXS, TagsInit.Items.ORES_OMNITRIXS);
        copy(TagsInit.Blocks.ORES_IMPERIUMS, TagsInit.Items.ORES_IMPERIUMS);
        copy(TagsInit.Blocks.ORES_LEGENDARY_ORES, TagsInit.Items.ORES_LEGENDARY_ORES);


        //blocks
        copy(TagsInit.Blocks.STORAGE_LEGENDARY, TagsInit.Items.STORAGE_LEGENDARY);
        copy(TagsInit.Blocks.STORAGE_OMNITRIX, TagsInit.Items.STORAGE_OMNITRIX);
        copy(TagsInit.Blocks.STORAGE_INFINITUM, TagsInit.Items.STORAGE_INFINITUM);
        copy(TagsInit.Blocks.STORAGE_FIRE, TagsInit.Items.STORAGE_FIRE);
        copy(TagsInit.Blocks.STORAGE_SPEED, TagsInit.Items.STORAGE_SPEED);
        copy(TagsInit.Blocks.STORAGE_BLACK_DIAMOND, TagsInit.Items.STORAGE_BLACK_DIAMOND);


        //ingots
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

        //tools
        tag(TagsInit.Items.TOOLS_IMPERIUM_PICKAXE).add(IMPERIUM_PICKAXE.get());

        //raw ores
        tag(TagsInit.Items.RAW_ORE).add(RAW_LEGENDARY.get(), RAW_OMNITRIX.get(), RAW_IMPERIUM.get());

        //others
        tag(TagsInit.Items.SPAW_EGGS).add(VILGAX_SPAWN_EGG.get(), KRAAB_SPAWN_EGG.get());
    }
}
