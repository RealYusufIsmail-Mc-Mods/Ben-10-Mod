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
        withExistingParent("fire_block", modLoc("block/fire_block"));
        withExistingParent("fire_ore", modLoc("block/fire_ore"));
        withExistingParent("legendary_block", modLoc("block/legendary_block"));
        withExistingParent("omnitrix_block", modLoc("block/omnitrix_block"));
        withExistingParent("speed_block", modLoc("block/speed_block"));
        withExistingParent("ruby_block", modLoc("block/ruby_block"));
        withExistingParent("ruby_ore", modLoc("block/infinitum_block"));
        withExistingParent("infinitum_block", modLoc("block/ruby_ore"));
        withExistingParent("speed_ore", modLoc("block/speed_ore"));
        withExistingParent("omnitrix_ore", modLoc("block/omnitrix_ore"));
        withExistingParent("vilgax_block", modLoc("block/vilgax_block"));
        withExistingParent("legendary_ore_block", modLoc("block/legendary_ore_block"));


        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemhandHeld = getExistingFile(mcLoc("item/handheld"));

        //items
        builder(itemGenerated, "black_diamond");
        builder(itemGenerated, "black_diamond_scrap");
        builder(itemGenerated, "infinitum");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "boot");
        builder(itemGenerated, "chestplate");
        builder(itemGenerated, "legendary_ore");
        builder(itemGenerated, "omnitrix");
        builder(itemGenerated, "fire");
        builder(itemGenerated, "ruby");
        builder(itemGenerated, "speed");
        builder(itemGenerated, "helmet");
        builder(itemGenerated, "leggings");
        builder(itemGenerated, "jacket");
        builder(itemGenerated, "heatblast_helmet");
        builder(itemGenerated, "heatblast_chestplate");
        builder(itemGenerated, "heatblast_leggings");
        builder(itemGenerated, "heatblast_boot");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "infinitum_scrap");




        //tools
        tool(itemGenerated, "axe");
        tool(itemGenerated, "sword");
        tool(itemGenerated, "heatblast_sword");


    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
    private ItemModelBuilder tool(ModelFile itemhandHeld, String name) {
        return getBuilder(name).parent(itemhandHeld).texture("layer0", "item/" + name);
    }
}
