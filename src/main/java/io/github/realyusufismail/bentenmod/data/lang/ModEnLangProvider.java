/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.data.lang;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.common.LangKeys;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.itemgroup.MainItemGroup;
import io.github.realyusufismail.realyusufismailcore.data.support.lang.ModEnLangProviderSupport;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModEnLangProvider extends ModEnLangProviderSupport {

    public ModEnLangProvider(DataGenerator gen) {
        super(gen, BenTenMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // block
        block(BlockInit.LEGENDARY_BLOCK, "Legendary Block");
        block(BlockInit.OMNITRIX_BLOCK, "Omnitrix Block");
        block(BlockInit.BLACK_DIAMOND_BLOCK, "Black Diamond Block");
        block(BlockInit.INFINITUM_BLOCK, "Infinitun Block");
        block(BlockInit.FIRE_BLOCK, "FIre Block");
        block(BlockInit.SPEED_BLOCK, "Speed Block");
        block(BlockInit.RUBY_BLOCK, "Ruby Block");
        block(BlockInit.IMPERIUM_BLOCK, "Imperium Block");
        block(BlockInit.OmnitrixCrafter, "Omntrix Crafter");

        // ores
        block(BlockInit.IMPERIUM_ORE, "Imperium Ore");
        block(BlockInit.LEGENDARY_ORE, "Legendary Ore");
        block(BlockInit.BLACK_DIAMOND_ORE, "Black Diamond Ore");
        block(BlockInit.OMNITRIX_ORE, "Omnitrix Ore");
        block(BlockInit.FIRE_ORE, "Fire Ore");
        block(BlockInit.SPEED_ORE, "Speed Ore");
        block(BlockInit.RUBY_ORE, "Ruby Ore");
        block(BlockInit.INFINITUM_ORE, "Infinitun Ore");

        // deepslate ores
        block(BlockInit.DEEPSLATE_IMPERIUM_ORE, "Deepslate Imperium Ore");
        block(BlockInit.DEEPSLATE_LEGENDARY_ORE, "Deepslate Legendary Ore");
        block(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE, "Deepslate Black Diamond Ore");
        block(BlockInit.DEEPSLATE_OMNITRIX_ORE, "Deepslate Omnitrix Ore");
        block(BlockInit.DEEPSLATE_SPEED_ORE, "Deepslate Speed Ore");
        block(BlockInit.DEEPSLATE_RUBY_ORE, "Deepslate Ruby Ore");
        // ingots
        item(ItemInit.LEGENDARY, "Legendary");
        item(ItemInit.OMNITRIX, "Omnitrix Ingot");
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

        // tools
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

        // armour
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
        item(ItemInit.FOURARMS_HELMET, "4Arms helmet");
        item(ItemInit.FOURARMS_CHESTPLATE, "4Arms chestplate");
        item(ItemInit.FOURARMS_LEGGINGS, "4Arms legging");
        item(ItemInit.FOURARMS_BOOT, "4Arms boots");
        item(ItemInit.INFINITUM_HELMET, "Infinitum helmet");
        item(ItemInit.INFINITUM_CHESTPLATE, "Infinitum chestplate");
        item(ItemInit.INFINITUM_LEGGINGS, "Infinitum legging");
        item(ItemInit.INFINITUM_BOOTS, "Infinitum boots");
        item(ItemInit.BLACK_DIAMOND_HELMET, "Black Diamond helmet");
        item(ItemInit.BLACK_DIAMOND_CHESTPLATE, "Black Diamond chestplate");
        item(ItemInit.BLACK_DIAMOND_LEGGINGS, "Black Diamond legging");
        item(ItemInit.BLACK_DIAMOND_BOOTS, "Black Diamond boots");

        // food
        item(ItemInit.OMNITRIX_APPLE, "Omnitrix Apple");
        item(ItemInit.ENCHANTED_OMNITRIX_APPLE, "Enchanted Omnitrix Apple");
        item(ItemInit.FIRE_APPLE, "Fire Apple");
        item(ItemInit.RUBY_APPLE, "Ruby Apple");

        // other items
        item(EntityTypesInit.VILGAX_SPAWN_EGG, "Vilgax Spawn Egg");
        item(ItemInit.OMNITRIX_WATCH, "Omnitrix");
        item(EntityTypesInit.CRAB_SPAWN_EGG, "Crab Spawn Egg");
        add(MainItemGroup.MAIN.getDisplayName().getString(), "Ben Ten Item Group");
        add(LangKeys.OMNITRIX_DESCRIPTION,
                "A custom crafting table used to create special items such as the Omnitrix.");
        add(LangKeys.OMNITRIX_SCREEN, "Omnitrix Craftier");
        entity(EntityTypesInit.VILGAX_ENTITY.get(), "Vilgax");
        entity(EntityTypesInit.CRAB_ENTITY.get(), "crab");

        // advancements
        add(Component.translatable("advancements.bentenmod.root.title"), "Download Ben ten mod");
        add(Component.translatable("advancements.bentenmod.root.description"),
                "Thank you for downloading my mod");
        // black diamond advancements
        add(Component.translatable("advancements.bentenmod.black_diamond_block.title"),
                "Made Black Diamond Block");
        add(Component.translatable("advancements.bentenmod.black_diamond_block.description"),
                "You have made a Black Diamond block using nine Black Diamond ingot.");
        add(Component.translatable("advancements.bentenmod.black_diamond_ingot.title"),
                "Made Black Diamond Ingo");
        add(Component.translatable("advancements.bentenmod.black_diamond_ingot.description"),
                "You have made an Black Diamond ingot which means you can use a smithing table to convert diamond tool or armour to black diamond.");
        add(Component.translatable("advancements.bentenmod.black_diamond_scrap.title"),
                "Mined Black Diamond and received Black Diamond scrap");
        add(Component.translatable("advancements.bentenmod.black_diamond_scrap.description"),
                "Get 3 more to be able to make an ingot to make armour and tools in smithing table.");
        add(Component.translatable("advancements.bentenmod.black_diamond_tools.title"),
                "Made all the black diamond Tools");
        add(Component.translatable("advancements.bentenmod.black_diamond_tools.description"),
                "You have made all the black diamond tool set which is better than diamond");
        add(Component.translatable("advancements.bentenmod.black_diamond_armour.title"),
                "Made all the black diamond armour set");
        add(Component.translatable("advancements.bentenmod.black_diamond_armour.description"),
                "You have made all the black diamond armour set which is better than diamond.");
        // legendary advancements
        add(Component.translatable("advancements.bentenmod.legendary_block.title"),
                "Made a Legendary block");
        add(Component.translatable("advancements.bentenmod.legendary_block.description"),
                "You are rich enough to make a Legendary Block");
        add(Component.translatable("advancements.bentenmod.knight_armour.title"),
                "Made knight armour");
        add(Component.translatable("advancements.bentenmod.knight_armour.description"),
                "You have the most powerful armour. All hall the strongest knight");
        add(Component.translatable("advancements.bentenmod.legendary_ingot.title"),
                "Smelted Legendary ore");
        add(Component.translatable("advancements.bentenmod.legendary_ingot.description"),
                "You smelted legendary ore and now you can make a sword and armour");
        add(Component.translatable("advancements.bentenmod.legendary_ore.title"),
                "Mined Legendary ore");
        add(Component.translatable("advancements.bentenmod.legendary_ore.description"),
                "You just mined one of the most OP ores in the game");
        // speed advancements
        add(Component.translatable("advancements.bentenmod.speed_ingot.title"),
                "You have mined speed ore and can now craft XLR8");
        add(Component.translatable("advancements.bentenmod.speed_ingot.description"),
                "you have speed");
        add(Component.translatable("advancements.bentenmod.speed_block.title"),
                "You have made a Speed Block");
        add(Component.translatable("advancements.bentenmod.speed_block.description"),
                "You have made a Speed Block using nine speed ingots");
        add(Component.translatable("advancements.bentenmod.xlr8_armour.title"), "you are now XLR8");
        add(Component.translatable("advancements.bentenmod.xlr8_armour.description"),
                "You are now the fastest player/entity in the game WOW!");
        // imperium advancements
        add(Component.translatable("advancements.bentenmod.imperium_ore.title"),
                "Mined Imperium ore");
        add(Component.translatable("advancements.bentenmod.imperium_ore.description"),
                "You have mined an ore which is better than iron ore.");
        add(Component.translatable("advancements.bentenmod.imperium_ingot.title"),
                "Smelted Imperium ore");
        add(Component.translatable("advancements.bentenmod.imperium_ingot.description"),
                "You have smelted imperium ore and now can craft armour and items");
        add(Component.translatable("advancements.bentenmod.imperium_block.title"),
                "Smelted Legendary ore");
        add(Component.translatable("advancements.bentenmod.imperium_block.description"),
                "You smelted legendary ore and now you can make a sword and armour");
        add(Component.translatable("advancements.bentenmod.imperium_tools.title"),
                "Made all the Imperium Tools");
        add(Component.translatable("advancements.bentenmod.imperium_tools.description"),
                "You have made all the imperium tool set which is better than iron");
        add(Component.translatable("advancements.bentenmod.upgraded_imperium_pickaxe.title"),
                "Made the Upgraded Imperium Pickaxe");
        add(Component.translatable("advancements.bentenmod.upgraded_imperium_pickaxe.description"),
                "You have made the Upgraded Imperium Pickaxe which is better than the normal Imperium Pickaxe");
        // ruby advancements
        add(Component.translatable("advancements.bentenmod.ruby_ore.title"), "Mined Ruby ore");
        add(Component.translatable("advancements.bentenmod.ruby_ore.description"),
                "You just mined one of the most OP ores in the game which gives you the ability to craft an apple which gives you ultimate regeneration.");
        add(Component.translatable("advancements.bentenmod.ruby_ingot.title"), "Smelted Ruby ore");
        add(Component.translatable("advancements.bentenmod.ruby_ingot.description"),
                "you smelted Ruby ore");
        add(Component.translatable("advancements.bentenmod.ruby_block.title"), "Made Ruby block");
        add(Component.translatable("advancements.bentenmod.ruby_block.description"),
                "you made a ruby block and can now make a ruby apple which gives you regeneration for a long time");
        add(Component.translatable("advancements.bentenmod.ruby_apple.title"), "Made Ruby Apple");
        add(Component.translatable("advancements.bentenmod.ruby_apple.description"),
                "you have a made a very op apple. Which gives regeneration for a long time");
        add(Component.translatable("advancements.bentenmod.ruby_axe.title"),
                "Made the fastest axe in the game");
        add(Component.translatable("advancements.bentenmod.ruby_axe.description"),
                "you have made the fastest axe in the game. Nice");
        // omnitrix advancements
        add(Component.translatable("advancements.bentenmod.omnitrix_ore.title"),
                "Mined Omnitrix ore");
        add(Component.translatable("advancements.bentenmod.omnitrix_ore.description"),
                "You just mined one of the most OP ores in the game which gives you the ability to craft certain apples.");
        add(Component.translatable("advancements.bentenmod.omnitrix_ingot.title"),
                "Smelted Omnitrix ore");
        add(Component.translatable("advancements.bentenmod.omnitrix_ingot.description"),
                "You smelted Omnitrix ore and now you can make an the omnitrix apple which will give unbelievable power.");
        add(Component.translatable("advancements.bentenmod.omnitrix_block.title"),
                "Made a Omnitrix block");
        add(Component.translatable("advancements.bentenmod.omnitrix_block.description"),
                "you have made omnitrix block which unlocks a certain enchanted apple.");
        add(Component.translatable("advancements.bentenmod.omnitrix_apple.title"),
                "Made an Omnitrix apple");
        add(Component.translatable("advancements.bentenmod.omnitrix_apple.description"),
                "You have a made an apple stronger than a normal golden apple");
        add(Component.translatable("advancements.bentenmod.enchanted_omnitrix_apple.title"),
                "You have a made an apple stronger than a normal golden apple.");
        add(Component.translatable("advancements.bentenmod.enchanted_omnitrix_apple.description"),
                "You have a made an apple stronger than no other apart from a secrete apple.");
        // fire advancements
        add(Component.translatable("advancements.bentenmod.fire_ore.title"), "Mined fire ore");
        add(Component.translatable("advancements.bentenmod.fire_ore.description"),
                "You just mined one of the most OP ores in the game.");
        add(Component.translatable("advancements.bentenmod.fire_ingot.title"), "Smelted Fire Ore");
        add(Component.translatable("advancements.bentenmod.fire_ingot.description"),
                "You are now able to make a fire apple and Heatblast.");
        add(Component.translatable("advancements.bentenmod.fire_block.title"), "Made a Fire block");
        add(Component.translatable("advancements.bentenmod.fire_block.description"),
                "You have crafted a fire block.");
        add(Component.translatable("advancements.bentenmod.fire_apple.title"), "Made a Fire Apple");
        add(Component.translatable("advancements.bentenmod.fire_apple.description"),
                "You are immunte to fire now.");
        add(Component.translatable("advancements.bentenmod.heatblast_armour.title"),
                "you are now Heatblast");
        add(Component.translatable("advancements.bentenmod.heatblast_armour.description"),
                "You are now able to fly and are resistant to fire");
        add(Component.translatable("advancements.bentenmod.heatblast_sword.title"),
                "you have made Heatblast sword");
        add(Component.translatable("advancements.bentenmod.heatblast_sword.description"),
                "You have made a sword which gives you the ability to set a mob/entity on fire by hitting it.");
        // copper
        add(Component.translatable("advancements.bentenmod.copper_ingot.title"),
                "Obtained Copper inogt");
        add(Component.translatable("advancements.bentenmod.copper_ingot.description"),
                "You have obtained copper ingot by slaying Kraab");
        add(Component.translatable("advancements.bentenmod.copper_block.title"),
                "Made a Copper block");
        add(Component.translatable("advancements.bentenmod.copper_block.description"),
                "You just made a copper block");
        // ininitum advancements
        add(Component.translatable("advancements.bentenmod.infinitum_scrap.title"),
                "Mined Infinitum and received Infinitum scrap");
        add(Component.translatable("advancements.bentenmod.infinitum_scrap.description"),
                "Get 3 more to be able to make an ingot to make armour and tools in smithing table.");
        add(Component.translatable("advancements.bentenmod.infinitum_ingot.title"),
                "Made an Infinitum Ingot");
        add(Component.translatable("advancements.bentenmod.infinitum_ingot.description"),
                "You have made an Infinitum ingot which means you can use a smithing table to convert netherite tool or armour to infinitum.");
        add(Component.translatable("advancements.bentenmod.infinitum_block.title"),
                "Made an Infinitum block");
        add(Component.translatable("advancements.bentenmod.infinitum_block.description"),
                "You have crafted an Infinitum block.");
        add(Component.translatable("advancements.bentenmod.infinitum_armour.title"),
                "Made all the Infinitum armour set");
        add(Component.translatable("advancements.bentenmod.infinitum_armour.description"),
                "You have made all the Infinitum armour set which is better than Black diamond and netherite.");
        add(Component.translatable("advancements.bentenmod.infinitum_tools.title"),
                "Made all the Infinitum Tools");
        add(Component.translatable("advancements.bentenmod.infinitum_tools.description"),
                "You have made all the Infinitum tool set which is better than Black Diamond and Netherite.");
    }

    @Override
    protected <T extends Item> void item(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    @Override
    protected <T extends Block> void block(RegistryObject<T> entry, String name) {
        add(entry.get(), name);
    }

    @Override
    protected <T extends Entity> void entity(EntityType<?> key, String name) {
        add(key.getDescriptionId(), name);
    }

    @Override
    protected void add(Component translatableComponent, String lang) {
        super.add(translatableComponent.getString(), lang);
    }
}
