package com.yusuf.bentenmod.data.client;


import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BenTenMod.MOD_ID, existingFileHelper);
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
        withExistingParent("ruby_ore", modLoc("block/ruby_ore"));
        withExistingParent("infinitum_block", modLoc("block/infinitum_block"));
        withExistingParent("speed_ore", modLoc("block/speed_ore"));
        withExistingParent("omnitrix_ore", modLoc("block/omnitrix_ore"));
        withExistingParent("imperium_ore", modLoc("block/imperium_ore"));
        withExistingParent("legendary_ore", modLoc("block/legendary_ore"));
        withExistingParent("imperium_block", modLoc("block/imperium_block"));
        withExistingParent("deepslate_legendary_ore", modLoc("block/deepslate_legendary_ore"));
        withExistingParent("deepslate_ruby_ore", modLoc("block/deepslate_ruby_ore"));
        withExistingParent("deepslate_black_diamond_ore", modLoc("block/deepslate_black_diamond_ore"));
        withExistingParent("deepslate_imperium_ore", modLoc("block/deepslate_imperium_ore"));
        withExistingParent("deepslate_speed_ore", modLoc("block/deepslate_speed_ore"));
        withExistingParent("deepslate_omnitrix_ore", modLoc("block/deepslate_omnitrix_ore"));
        withExistingParent("deepslate_omnitrix_ore", modLoc("block/deepslate_omnitrix_ore"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemhandHeld = getExistingFile(mcLoc("item/handheld"));

        //items
        builder(itemGenerated, "black_diamond");
        builder(itemGenerated, "black_diamond_scrap");
        builder(itemGenerated, "infinitum");
        builder(itemGenerated, "infinitum_scrap");
        builder(itemGenerated, "boots");
        builder(itemGenerated, "chestplate");
        builder(itemGenerated, "legendary");
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
        builder(itemGenerated, "heatblast_boots");
        builder(itemGenerated, "xlr8_helmet");
        builder(itemGenerated, "xlr8_chestplate");
        builder(itemGenerated, "xlr8_leggings");
        builder(itemGenerated, "xlr8_boots");
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
        builder(itemGenerated, "imperium");
        builder(itemGenerated, "copper");
        builder(itemGenerated, "kraab_spawn_egg");
        builder(itemGenerated, "raw_legendary");
        builder(itemGenerated, "raw_omnitrix");
        builder(itemGenerated, "raw_imperium");


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
        tool(itemhandHeld, "imperium_sword");
        tool(itemhandHeld, "imperium_pickaxe");
        tool(itemhandHeld, "imperium_pickaxe_upgraded");
        tool(itemhandHeld, "imperium_axe");

    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder tool(ModelFile itemhandHeld, String name) {
        return getBuilder(name).parent(itemhandHeld).texture("layer0", "item/" + name);
    }

}


