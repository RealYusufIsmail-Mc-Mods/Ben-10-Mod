package com.yusuf.bentenmod.data.lang;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.common.LangKeys;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.EntityTypesInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmod.entity.KraabEntity;
import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen, BenTenMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //block
        block(BlockInit.LEGENDARY_BLOCK, "Legerndary Block");
        block(BlockInit.OMNITRIX_BLOCK, "Omnitrix Block");
        block(BlockInit.BLACK_DIAMOND_BLOCK, "Black Diamond Block");
        block(BlockInit.INFINITUM_BLOCK, "Infinitun Block");
        block(BlockInit.FIRE_BLOCK, "FIre Block");
        block(BlockInit.SPEED_BLOCK, "Speed Block");
        block(BlockInit.RUBY_BLOCK, "Ruby Block");
        block(BlockInit.IMPERIUM_BLOCK, "Imperium Block");
        block(BlockInit.TABLE_BLOCK, "Upgrade and Crafting Table");
        block(BlockInit.ALIEN_TABLE, "Alien Crafting Table");


        //ores
        block(BlockInit.IMPERIUM_ORE, "Imperium Ore");
        block(BlockInit.LEGENDARY_ORE, "Legendary Ore");
        block(BlockInit.BLACK_DIAMOND_ORE, "Black Diamond Ore");
        block(BlockInit.OMNITRIX_ORE, "Omnitrix Ore");
        block(BlockInit.FIRE_ORE, "Fire Ore");
        block(BlockInit.SPEED_ORE, "Speed Ore");
        block(BlockInit.RUBY_ORE, "Ruby Ore");
        block(BlockInit.INFINITUM_ORE, "Infinitun Ore");

        //deepslate ores
        block(BlockInit.DEEPSLATE_IMPERIUM_ORE, "Deepslate Imperium Ore");
        block(BlockInit.DEEPSLATE_LEGENDARY_ORE, "Deepslate Legendary Ore");
        block(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE, "Deepslate Black Diamond Ore");
        block(BlockInit.DEEPSLATE_OMNITRIX_ORE, "Deepslate Omnitrix Ore");
        block(BlockInit.DEEPSLATE_SPEED_ORE, "Deepslate Speed Ore");
        block(BlockInit.DEEPSLATE_RUBY_ORE, "Deepslate Ruby Ore");
        //ingots
        item(ItemInit.LEGENDARY, "Legendary");
        item(ItemInit.OMNITRIX, "Omnitrix");
        item(ItemInit.FIRE, "Fire(ingot)");
        item(ItemInit.RUBY, "Ruby");
        item(ItemInit.SPEED, "Speed");
        item(ItemInit.BLACK_DIAMOND, "Black Diamond");
        item(ItemInit.BLACK_DIAMOND_SCRAP, "Black Diamond scrap");
        item(ItemInit.INFINITUM, "Infinitum");
        item(ItemInit.IMPERIUM, "Imperium");
        item(ItemInit.INFINITUM_SCRAP, "Infinitum scrap");
        item(ItemInit.RAW_IMPERIUM, "Raw Imperium");
        item(ItemInit.RAW_LEGENDARY, "Raw Legendaryy");
        item(ItemInit.RAW_OMNITRIX, "Raw Omnitrix");

        //tools
        item(ItemInit.SWORD, "ASCALON");
        item(ItemInit.AXE, "AXE SLAYER");
        item(ItemInit.HEATBLAST_SWORD, "Heatblast Sword");
        item(ItemInit.BLACK_DIAMOND_SWORD, "Black Diamond Sword");
        item(ItemInit.BLACK_DIAMOND_AXE, "Black Diamond Axe");
        item(ItemInit.BLACK_DIAMOND_PICKAXE, "Black Diamond Pickaxe");
        item(ItemInit.BLACK_DIAMOND_SHOVEL, "Black Diamond Shovel");
        item(ItemInit.BLACK_DIAMOND_HOE, "Black Diamond Hoe");
        item(ItemInit.INFINITUM_SWORD, "Infinitum Sword");
        item(ItemInit.INFINITUM_AXE, "Infinitum Axe");
        item(ItemInit.INFINITUM_PICKAXE, "Infinitum Pickaxe");
        item(ItemInit.INFINITUM_SHOVEL, "Infinitum Shovel");
        item(ItemInit.INFINITUM_HOE, "Infinitum Hoe");
        item(ItemInit.IMPERIUM_SWORD, "Imperium Sword");
        item(ItemInit.IMPERIUM_PICKAXE, "Imperium Pickaxe");
        item(ItemInit.IMPERIUM_PICKAXE_UPGRADED, "Ultamte Imperium Pickaxe");
        item(ItemInit.IMPERIUM_AXE, "Imperium Axe");

        //armour
        item(ItemInit.HELMET, "Knight helmet");
        item(ItemInit.CHESTPLATE, "Knight Chestplate");
        item(ItemInit.LEGGINGS, "Knight leggings");
        item(ItemInit.BOOTS, "Knight Boots");
        item(ItemInit.HEATBLAST_HELMET, "Heatblast helmet");
        item(ItemInit.HEATBLAST_CHESTPLATE, "Heatblast chestplate");
        item(ItemInit.HEATBLAST_LEGGINGS, "Heatblast leggings");
        item(ItemInit.HEATBLAST_BOOTS, "Heatblast boots");
        item(ItemInit.XLR8_HELMET, "xlr8 helmet");
        item(ItemInit.XLR8_CHESTPLATE, "xlr8 chestplate");
        item(ItemInit.XLR8_LEGGINGS, "xlr8 legging");
        item(ItemInit.XLR8_BOOTS, "xlr8 boots");
        //item(ItemInit.FOURARMS_HELMET, "4Arms helmet");
        //item(ItemInit.FOURARMS_CHESTPLATE, "4Arms chestplate");
        //item(ItemInit.FOURARMS_LEGGINGS, "4Arms legging");
        //item(ItemInit.FOURARMS_BOOT, "4Arms boots");
        item(ItemInit.INFINITUM_HELMET, "Infinitum helmet");
        item(ItemInit.INFINITUM_CHESTPLATE, "Infinitum chestplate");
        item(ItemInit.INFINITUM_LEGGINGS, "Infinitum legging");
        item(ItemInit.INFINITUM_BOOTS, "Infinitum boots");
        item(ItemInit.BLACK_DIAMOND_HELMET, "Black Diamond helmet");
        item(ItemInit.BLACK_DIAMOND_CHESTPLATE, "Black Diamond chestplate");
        item(ItemInit.BLACK_DIAMOND_LEGGINGS, "Black Diamond legging");
        item(ItemInit.BLACK_DIAMOND_BOOTS, "Black Diamond boots");

        //food
        item(ItemInit.OMNITRIX_APPLE, "Omnitrix Apple");
        item(ItemInit.ENCHANTED_OMNITRIX_APPLE, "Enchanted Omnitrix Apple");
        item(ItemInit.FIRE_APPLE, "Fire Apple");
        item(ItemInit.RUBY_APPLE, "Ruby Apple");

        //other items
        item(EntityTypesInit.VILGAX_SPAWN_EGG, "Vilgax Spawn Egg");
        item(EntityTypesInit.KRAAB_SPAWN_EGG, "Krab Spawn Egg");
        add(MainItemGroup.MAIN.getDisplayName().getString(), "Ben Ten Item Group");
        add(LangKeys.TABLE_DISC, "A table used to upgrade items and tools in the game");
        add(LangKeys.TABLE_SCREEN, "Upgrade and Crafting Table");
        add(LangKeys.ALIEN_TABLE_SCREEN, "Alien Crafting Table");
        add(LangKeys.ALIEN_TABLE_DISC, "A crafting table use to create aliens from the ben 10 series");
        entity(EntityTypesInit.VILGAX_ENTITY, "Vilgax");
        entity1(EntityTypesInit.KRAAB_ENTITY, "krab");

        //advancements
        add(new TranslationTextComponent("advancements.bentenmod.root.title"), "Download Ben ten mod");
        add(new TranslationTextComponent("advancements.bentenmod.root.description"), "Thank you for downloading my mod");
        //black diamond advancements
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_block.title"), "Made Black Diamond Block");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_block.description"), "You have made a Black Diamond block using nine Black Diamond ingot.");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_ingot.title"), "Made Black Diamond Ingo");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_ingot.description"), "You have made an Black Diamond ingot which means you can use a smithing table to convert diamond tool or armour to black diamond.");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_scrap.title"), "Mined Black Diamond and received Black Diamond scrap");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_scrap.description"), "Get 3 more to be able to make an ingot to make armour and tools in smithing table.");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_tools.title"), "Made all the black diamond Tools");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_tools.description"), "You have made all the black diamond tool set which is better than diamond");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_armour.title"), "Made all the black diamond armour set");
        add(new TranslationTextComponent("advancements.bentenmod.black_diamond_armour.description"), "You have made all the black diamond armour set which is better than diamond.");
        //legendary advancements
        add(new TranslationTextComponent("advancements.bentenmod.legendary_block.title"), "Made a Legendary block");
        add(new TranslationTextComponent("advancements.bentenmod.legendary_block.description"), "You are rich enough to make a Legendary Block");
        add(new TranslationTextComponent("advancements.bentenmod.knight_armour.title"), "Made knight armour");
        add(new TranslationTextComponent("advancements.bentenmod.knight_armour.description"), "You have the most powerful armour. All hall the strongest knight");
        add(new TranslationTextComponent("advancements.bentenmod.legendary_ingot.title"), "Smelted Legendary ore");
        add(new TranslationTextComponent("advancements.bentenmod.legendary_ingot.description"), "You smelted legendary ore and now you can make a sword and armour");
        add(new TranslationTextComponent("advancements.bentenmod.legendary_ore.title"), "Mined Legendary ore");
        add(new TranslationTextComponent("advancements.bentenmod.legendary_ore.description"), "You just mined one of the most OP ores in the game");
        //speed advancements
        add(new TranslationTextComponent("advancements.bentenmod.speed_ingot.title"), "You have mined speed ore and can now craft XLR8");
        add(new TranslationTextComponent("advancements.bentenmod.speed_ingot.description"), "you have speed");
        add(new TranslationTextComponent("advancements.bentenmod.speed_block.title"), "You have made a Speed Block");
        add(new TranslationTextComponent("advancements.bentenmod.speed_block.description"), "You have made a Speed Block using nine speed ingots");
        add(new TranslationTextComponent("advancements.bentenmod.xlr8_armour.title"), "you are now XLR8");
        add(new TranslationTextComponent("advancements.bentenmod.xlr8_armour.description"), "You are now the fastest player/entity in the game WOW!");
        //imperium advancements
        add(new TranslationTextComponent("advancements.bentenmod.imperium_ore.title"), "Mined Imperium ore");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_ore.description"), "You have mined an ore which is better than iron ore.");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_ingot.title"), "Smelted Imperium ore");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_ingot.description"), "You have smelted imperium ore and now can craft armour and items");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_block.title"), "Smelted Legendary ore");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_block.description"), "You smelted legendary ore and now you can make a sword and armour");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_tools.title"), "Made all the Imperium Tools");
        add(new TranslationTextComponent("advancements.bentenmod.imperium_tools.description"), "You have made all the imperium tool set which is better than iron");
        add(new TranslationTextComponent("advancements.bentenmod.upgraded_imperium_pickaxe.title"), "Made the Upgraded Imperium Pickaxe");
        add(new TranslationTextComponent("advancements.bentenmod.upgraded_imperium_pickaxe.description"), "You have made the Upgraded Imperium Pickaxe which is better than the normal Imperium Pickaxe");
        //ruby advancements
        add(new TranslationTextComponent("advancements.bentenmod.ruby_ore.title"), "Mined Ruby ore");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_ore.description"), "You just mined one of the most OP ores in the game which gives you the ability to craft an apple which gives you ultimate regeneration.");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_ingot.title"), "Smelted Ruby ore");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_ingot.description"), "you smelted Ruby ore");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_block.title"), "Made Ruby block");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_block.description"), "you made a ruby block and can now make a ruby apple which gives you regeneration for a long time");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_apple.title"), "Made Ruby Apple");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_apple.description"), "you have a made a very op apple. Which gives regeneration for a long time");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_axe.title"), "Made the fastest axe in the game");
        add(new TranslationTextComponent("advancements.bentenmod.ruby_axe.description"), "you have made the fastest axe in the game. Nice");
        //omnitrix advancements
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_ore.title"), "Mined Omnitrix ore");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_ore.description"), "You just mined one of the most OP ores in the game which gives you the ability to craft certain apples.");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_ingot.title"), "Smelted Omnitrix ore");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_ingot.description"), "You smelted Omnitrix ore and now you can make an the omnitrix apple which will give unbelievable power.");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_block.title"), "Made a Omnitrix block");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_block.description"), "you have made omnitrix block which unlocks a certain enchanted apple.");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_apple.title"), "Made an Omnitrix apple");
        add(new TranslationTextComponent("advancements.bentenmod.omnitrix_apple.description"), "You have a made an apple stronger than a normal golden apple");
        add(new TranslationTextComponent("advancements.bentenmod.enchanted_omnitrix_apple.title"), "You have a made an apple stronger than a normal golden apple.");
        add(new TranslationTextComponent("advancements.bentenmod.enchanted_omnitrix_apple.description"), "You have a made an apple stronger than no other apart from a secrete apple.");
        //fire advancements
        add(new TranslationTextComponent("advancements.bentenmod.fire_ore.title"), "Mined fire ore");
        add(new TranslationTextComponent("advancements.bentenmod.fire_ore.description"), "You just mined one of the most OP ores in the game.");
        add(new TranslationTextComponent("advancements.bentenmod.fire_ingot.title"), "Smelted Fire Ore");
        add(new TranslationTextComponent("advancements.bentenmod.fire_ingot.description"), "You are now able to make a fire apple and Heatblast.");
        add(new TranslationTextComponent("advancements.bentenmod.fire_block.title"), "Made a Fire block");
        add(new TranslationTextComponent("advancements.bentenmod.fire_block.description"), "You have crafted a fire block.");
        add(new TranslationTextComponent("advancements.bentenmod.fire_apple.title"), "Made a Fire Apple");
        add(new TranslationTextComponent("advancements.bentenmod.fire_apple.description"), "You are immunte to fire now.");
        add(new TranslationTextComponent("advancements.bentenmod.heatblast_armour.title"), "you are now Heatblast");
        add(new TranslationTextComponent("advancements.bentenmod.heatblast_armour.description"), "You are now able to fly and are resistant to fire");
        add(new TranslationTextComponent("advancements.bentenmod.heatblast_sword.title"), "you have made Heatblast sword");
        add(new TranslationTextComponent("advancements.bentenmod.heatblast_sword.description"), "You have made a sword which gives you the ability to set a mob/entity on fire by hitting it.");
        //copper
        add(new TranslationTextComponent("advancements.bentenmod.copper_ingot.title"), "Obtained Copper inogt");
        add(new TranslationTextComponent("advancements.bentenmod.copper_ingot.description"), "You have obtained copper ingot by slaying Kraab");
        add(new TranslationTextComponent("advancements.bentenmod.copper_block.title"), "Made a Copper block");
        add(new TranslationTextComponent("advancements.bentenmod.copper_block.description"), "You just made a copper block");
        //ininitum advancements
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_scrap.title"), "Mined Infinitum and received Infinitum scrap");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_scrap.description"), "Get 3 more to be able to make an ingot to make armour and tools in smithing table.");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_ingot.title"), "Made an Infinitum Ingot");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_ingot.description"), "You have made an Infinitum ingot which means you can use a smithing table to convert netherite tool or armour to infinitum.");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_block.title"), "Made an Infinitum block");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_block.description"), "You have crafted an Infinitum block.");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_armour.title"), "Made all the Infinitum armour set");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_armour.description"), "You have made all the Infinitum armour set which is better than Black diamond and netherite.");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_tools.title"), "Made all the Infinitum Tools");
        add(new TranslationTextComponent("advancements.bentenmod.infinitum_tools.description"), "You have made all the Infinitum tool set which is better than Black Diamond and Netherite.");
    }

    private <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Entity> void entity(RegistryObject<EntityType<VilgaxEntity>> entry, String name) {
        add(entry.get(), name);
    }

    private <T extends Entity> void entity1(RegistryObject<EntityType<KraabEntity>> entry, String name) {
        add(entry.get(), name);
    }

    private void add(ITextComponent key, String lang) {
        super.add(key.getString(), lang);
    }
}