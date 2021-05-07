package com.Yusuf.bentenmobmod.data.client;

import com.Yusuf.bentenmobmod.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }



    @Override
    protected void registerModels() {
        withExistingParent("heatblast_sword", modLoc("item/heatblast_sword"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
    }
}
