package com.yusuf.bentenmod.data;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.data.client.ModBlockStateProvider;
import com.yusuf.bentenmod.data.client.ModelProvider;
import com.yusuf.bentenmod.data.loot.ModLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModelProvider.BlockState(gen, existingFileHelper));
        gen.addProvider(new ModelProvider.Item(gen, existingFileHelper));


        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));

        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModSmitingRecipeBuild(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(new ModLangProvider(gen));

        

    }
}
