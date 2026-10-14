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
package io.github.realyusufismail.bentenmod.data;

import io.github.realyusufismail.bentenmod.data.advancments.ModAdvancementProvider;
import io.github.realyusufismail.bentenmod.data.client.ModBlockStateProvider;
import io.github.realyusufismail.bentenmod.data.client.ModItemModelProvider;
import io.github.realyusufismail.bentenmod.data.lang.ModEnLangProvider;
import io.github.realyusufismail.bentenmod.data.loot.ModLootTables;
import io.github.realyusufismail.bentenmod.data.recipe.ModRecipeProvider;
import io.github.realyusufismail.bentenmod.data.tags.ModBlockTagsProvider;
import io.github.realyusufismail.bentenmod.data.tags.ModItemTagsProvider;
import io.github.realyusufismail.bentenmod.data.worldgen.ModWorldGenerationProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataGenerators {
    private DataGenerators() {}

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

        // pack.mcmeta
        gen.addProvider(true,
                new PackMetadataGenerator(gen.getPackOutput()).add(PackMetadataSection.TYPE,
                        new PackMetadataSection(Component.literal("BentenMod Mod Resources"),
                                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                                Arrays.stream(PackType.values())
                                    .collect(Collectors.toMap(Function.identity(),
                                            DetectedVersion.BUILT_IN::getPackVersion)))));

    }
}
