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
        block(BlockInit.LEGENDARY_ORE_BLOCK, "Legendary Ore");
        block(BlockInit.BLACK_DIAMOND_ORE, "Black Diamond Ore");
        block(BlockInit.OMNITRIX_ORE, "Omnitrix Ore");
        block(BlockInit.FIRE_ORE, "Fire Ore");
        block(BlockInit.SPEED_ORE, "Speed Ore");
        block(BlockInit.RUBY_ORE, "Ruby Ore");
        block(BlockInit.INFINITUM_ORE, "Infinitun Ore");

        //ingots
        item(ItemInit.LEGENDARY_ORE, "Legendary");
        item(ItemInit.OMNITRIX, "Omnitrix");
        item(ItemInit.FIRE, "Fire(ingot)");
        item(ItemInit.RUBY, "Ruby");
        item(ItemInit.SPEED, "Speed");
        item(ItemInit.BLACK_DIAMOND, "Black Diamond");
        item(ItemInit.BLACK_DIAMOND_SCRAP, "Black Diamond scrap");
        item(ItemInit.INFINITUM, "Infinitum");
        item(ItemInit.IMPERIUM, "Imperium");
        item(ItemInit.INFINITUM_SCRAP, "Infinitum scrap");

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
        item(ItemInit.BOOT, "Knight Boots");
        item(ItemInit.HEATBLAST_HELMET, "Heatblast helmet");
        item(ItemInit.HEATBLAST_CHESTPLATE, "Heatblast chestplate");
        item(ItemInit.HEATBLAST_LEGGINGS, "Heatblast leggings");
        item(ItemInit.HEATBLAST_BOOT, "Heatblast boots");
        item(ItemInit.XLR8_HELMET, "xlr8 helmet");
        item(ItemInit.XLR8_CHESTPLATE, "xlr8 chestplate");
        item(ItemInit.XLR8_LEGGINGS, "xlr8 legging");
        item(ItemInit.XLR8_BOOT, "xlr8 boots");
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
        add(LangKeys.ALIEN_TABLE_DISC, "A crafting table use to create aliens from the ben 10 series");
        entity(EntityTypesInit.VILGAX_ENTITY, "Vilgax");
        entity1(EntityTypesInit.KRAAB_ENTITY, "krab");
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
