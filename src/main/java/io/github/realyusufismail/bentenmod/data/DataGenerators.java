/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.data;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.data.advancments.ModAdvancementProvider;
import io.github.realyusufismail.bentenmod.data.client.ModBlockStateProvider;
import io.github.realyusufismail.bentenmod.data.client.ModItemModelProvider;
import io.github.realyusufismail.bentenmod.data.lang.ModEnLangProvider;
import io.github.realyusufismail.bentenmod.data.loot.ModLootTables;
import io.github.realyusufismail.bentenmod.data.recipe.ModRecipeProvider;
import io.github.realyusufismail.bentenmod.data.tags.ModBlockTagsProvider;
import io.github.realyusufismail.bentenmod.data.tags.ModItemTagsProvider;
import io.github.realyusufismail.bentenmod.data.worldgen.ModWorldGenerationProvider;
import net.minecraft.core.HolderLookup;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var lookup = event.getLookupProvider();

        gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModLootTables(gen));

        // lang
        gen.addProvider(true, new ModEnLangProvider(gen));

        // recipe
        gen.addProvider(true, new ModRecipeProvider(gen));

        // advancements
        gen.addProvider(true,
                new ModAdvancementProvider(gen.getPackOutput(), lookup, existingFileHelper));

        // tags
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper, lookup);
        gen.addProvider(true, blockTags);
        gen.addProvider(true, new ModItemTagsProvider(gen, blockTags, lookup, existingFileHelper));

        // ore gen
        gen.addProvider(true, new ModWorldGenerationProvider(gen.getPackOutput(), lookup));
    }
}
