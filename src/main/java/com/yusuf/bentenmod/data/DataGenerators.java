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
package com.yusuf.bentenmod.data;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.data.advancments.ModAdvancementProvider;
import com.yusuf.bentenmod.data.client.ModBlockStateProvider;
import com.yusuf.bentenmod.data.client.ModItemModelProvider;
import com.yusuf.bentenmod.data.lang.ModEnLangProvider;
import com.yusuf.bentenmod.data.loot.ModLootTables;
import com.yusuf.bentenmod.data.recipe.ModRecipeProvider;
import com.yusuf.bentenmod.data.recipe.ModSmithingRecipeBuild;
import com.yusuf.bentenmod.data.tags.ModBlockTagsProvider;
import com.yusuf.bentenmod.data.tags.ModItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));

        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));

        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModSmithingRecipeBuild(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(new ModEnLangProvider(gen));
        gen.addProvider(new ModAdvancementProvider(gen));
    }
}
