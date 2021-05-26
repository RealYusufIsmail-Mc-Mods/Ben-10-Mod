package com.yusuf.bentenmobmod.data.client;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }



    @Override
    protected void registerModels() {



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
        builder(itemGenerated, "xlr8_helmet");
        builder(itemGenerated, "xlr8_chestplate");
        builder(itemGenerated, "xlr8_leggings");
        builder(itemGenerated, "xlr8_boot");
        builder(itemGenerated, "omnitrix_apple");
        builder(itemGenerated, "enchanted_omnitrix_apple");
        builder(itemGenerated, "fire_apple");
        builder(itemGenerated, "ruby_apple");
        builder(itemGenerated, "vilgax_spawn_egg");
        builder(itemGenerated, "black_diamond_helmet");
        builder(itemGenerated, "black_diamond_chestplate");
        builder(itemGenerated, "black_diamond_leggings");
        builder(itemGenerated, "black_diamond_boots");
        builder(itemGenerated, "infinitum_helmet");
        builder(itemGenerated, "infinitum_chestplate");
        builder(itemGenerated, "infinitum_leggings");
        builder(itemGenerated, "infinitum_boots");
        builder(itemGenerated, "fourarms_helmet");
        builder(itemGenerated, "fourarms_chestplate");
        builder(itemGenerated, "fourarms_leggings");
        builder(itemGenerated, "fourarms_boot");




        //tools
        tool(itemhandHeld, "axe");
        tool(itemhandHeld, "sword");
        tool(itemhandHeld, "heatblast_sword");
        tool(itemhandHeld, "black_diamond_sword");
        tool(itemhandHeld, "black_diamond_pickaxe");
        tool(itemhandHeld, "black_diamond_shovel");
        tool(itemhandHeld, "black_diamond_axe");
        tool(itemhandHeld, "black_diamond_hoe");
        tool(itemhandHeld, "infinitum_sword");
        tool(itemhandHeld, "infinitum_axe");
        tool(itemhandHeld, "infinitum_pickaxe");
        tool(itemhandHeld, "infinitum_shovel");
        tool(itemhandHeld, "infinitum_hoe");


    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
    private ItemModelBuilder tool(ModelFile itemhandHeld, String name) {
        return getBuilder(name).parent(itemhandHeld).texture("layer0",  "item/" + name);
    }


}
