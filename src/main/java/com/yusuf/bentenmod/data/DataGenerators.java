package com.yusuf.bentenmod.data;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.data.client.ModBlockStateProvider;
import com.yusuf.bentenmod.data.client.ModItemModelProvider;
import com.yusuf.bentenmod.data.lang.ModEnLangProvider;
import com.yusuf.bentenmod.data.loot.ModLootTables;
import com.yusuf.bentenmod.data.recipe.ModRecipeProvider;
import com.yusuf.bentenmod.data.recipe.ModSmitingRecipeBuild;
import com.yusuf.bentenmod.data.tags.ModBlockTagsProvider;
import com.yusuf.bentenmod.data.tags.ModItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {
    }


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
        gen.addProvider(new ModSmitingRecipeBuild(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(new ModEnLangProvider(gen));


    }
}