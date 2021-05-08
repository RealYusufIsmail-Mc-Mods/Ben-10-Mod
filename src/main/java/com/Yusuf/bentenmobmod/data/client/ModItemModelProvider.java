package com.yusuf.bentenmobmod.data.client;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }



    @Override
    protected void registerModels() {
        withExistingParent("black_diamond_block", modLoc("block/black_diamond_block"));
        withExistingParent("black_diamond_ore", modLoc("block/black_diamond_ore"));
        withExistingParent("infinitum_ore", modLoc("block/infinitum_ore"));
        withExistingParent("infinitum_block", modLoc("block/infinitum_block"));


        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemhandHeld = getExistingFile(mcLoc("item/handheld"));

        builder(itemGenerated, "black_diamond");
        builder(itemGenerated, "black_diamond_scrap");
        builder(itemGenerated, "infinitum");
        builder(itemGenerated, "infinitum_scrap");
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
    private ItemModelBuilder wepons(ModelFile itemhandHeld, String name) {
        return getBuilder(name).parent(itemhandHeld).texture("layer0", "item/" + name);
    }
}
