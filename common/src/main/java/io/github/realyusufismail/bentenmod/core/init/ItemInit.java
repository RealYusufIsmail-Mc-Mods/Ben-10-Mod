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

package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.material.CustomArmorMaterial;
import io.github.realyusufismail.bentenmod.core.material.CustomToolMaterial;
import io.github.realyusufismail.bentenmod.core.item.VilgaxBeaconItem;
import io.github.realyusufismail.bentenmod.core.item.MasterControlItem;
import io.github.realyusufismail.bentenmod.core.item.NullVoidProjectorItem;
import io.github.realyusufismail.bentenmod.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import io.github.realyusufismail.bentenmod.core.item.ModTools;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;

public class ItemInit {
    // ores
    public static final RegistrationProvider<Item> ITEMS =
            RegistrationProvider.get(Registries.ITEM, BenTenMod.MOD_ID);

    public static final RegistryObject<Item> LEGENDARY = register("legendary");

    public static final RegistryObject<Item> OMNITRIX = register("omnitrix");
    public static final RegistryObject<Item> FIRE = register("fire");
    public static final RegistryObject<Item> RUBY = register("ruby");
    public static final RegistryObject<Item> SPEED = register("speed");
    public static final RegistryObject<Item> BLACK_DIAMOND = register("black_diamond");
    public static final RegistryObject<Item> BLACK_DIAMOND_SCRAP = register("black_diamond_scrap");
    public static final RegistryObject<Item> INFINITUM = register("infinitum");
    public static final RegistryObject<Item> IMPERIUM = register("imperium");
    public static final RegistryObject<Item> INFINITUM_SCRAP = register("infinitum_scrap");

    // raw ores
    public static final RegistryObject<Item> RAW_LEGENDARY = register("raw_legendary");
    public static final RegistryObject<Item> RAW_OMNITRIX = register("raw_omnitrix");
    public static final RegistryObject<Item> RAW_IMPERIUM = register("raw_imperium");
    public static final RegistryObject<Item> OMNITRIX_CORE = register("omnitrix_core");
    public static final RegistryObject<Item> LEGENDARY_CASING = register("legendary_casing");
    public static final RegistryObject<Item> BLACK_DIAMOND_LENS = register("black_diamond_lens");
    public static final RegistryObject<Item> KRAAB_CLAW = register("kraab_claw");
    public static final RegistryObject<Item> VILGAX_CORE =
            ITEMS.register("vilgax_core", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VILGAX_BEACON =
            ITEMS.register("vilgax_beacon", () -> new VilgaxBeaconItem(new Item.Properties()));
    public static final RegistryObject<Item> NULL_CRYSTAL = register("null_crystal");
    public static final RegistryObject<Item> NULL_VOID_PROJECTOR =
            ITEMS.register("null_void_projector",
                    () -> new NullVoidProjectorItem(new Item.Properties().durability(16)));
    public static final RegistryObject<Item> MASTER_CONTROL = ITEMS.register("master_control",
            () -> new MasterControlItem(new Item.Properties().rarity(Rarity.EPIC)));

    // tools
    public static final RegistryObject<SwordItem> SWORD =
            ITEMS.register("sword", () -> new SwordItem(CustomToolMaterial.SWORD, 0, 7f,
                    new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Axe> AXE = ITEMS.register("axe",
            () -> new ModTools.Axe(CustomToolMaterial.AXE, 0, 8f, new Item.Properties()));

    public static final RegistryObject<SwordItem> HEATBLAST_SWORD = ITEMS
        .register("heatblast_sword", () -> new SwordItem(CustomToolMaterial.HEATBLAST_SWORD, 0, 7f,
                new Item.Properties().fireResistant().stacksTo(1)));

    public static final RegistryObject<SwordItem> BLACK_DIAMOND_SWORD = ITEMS
        .register("black_diamond_sword", () -> new SwordItem(CustomToolMaterial.BLACK_DIAMOND_SWORD,
                0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Axe> BLACK_DIAMOND_AXE = ITEMS
        .register("black_diamond_axe", () -> new ModTools.Axe(CustomToolMaterial.BLACK_DIAMOND_AXE,
                0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Pickaxe> BLACK_DIAMOND_PICKAXE =
            ITEMS.register("black_diamond_pickaxe",
                    () -> new ModTools.Pickaxe(CustomToolMaterial.BLACK_DIAMOND_PICKAXE, 0, 7f,
                            new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ShovelItem> BLACK_DIAMOND_SHOVEL = ITEMS.register(
            "black_diamond_shovel", () -> new ShovelItem(CustomToolMaterial.BLACK_DIAMOND_SHOVEL, 0,
                    7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Hoe> BLACK_DIAMOND_HOE = ITEMS
        .register("black_diamond_hoe", () -> new ModTools.Hoe(CustomToolMaterial.BLACK_DIAMOND_HOE,
                0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<SwordItem> INFINITUM_SWORD = ITEMS
        .register("infinitum_sword", () -> new SwordItem(CustomToolMaterial.INFINITUM_SWORD, 0, 7f,
                new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Axe> INFINITUM_AXE =
            ITEMS.register("infinitum_axe", () -> new ModTools.Axe(CustomToolMaterial.INFINITUM_AXE,
                    0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Pickaxe> INFINITUM_PICKAXE = ITEMS.register(
            "infinitum_pickaxe", () -> new ModTools.Pickaxe(CustomToolMaterial.INFINITUM_PICKAXE, 0,
                    7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ShovelItem> INFINITUM_SHOVEL = ITEMS
        .register("infinitum_shovel", () -> new ShovelItem(CustomToolMaterial.INFINITUM_SHOVEL, 0,
                7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Hoe> INFINITUM_HOE =
            ITEMS.register("infinitum_hoe", () -> new ModTools.Hoe(CustomToolMaterial.INFINITUM_HOE,
                    0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<SwordItem> IMPERIUM_SWORD =
            ITEMS.register("imperium_sword", () -> new SwordItem(CustomToolMaterial.IMPERIUM_SWORD,
                    0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Pickaxe> IMPERIUM_PICKAXE = ITEMS.register(
            "imperium_pickaxe", () -> new ModTools.Pickaxe(CustomToolMaterial.IMPERIUM_PICKAXE, 0,
                    7f, new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Pickaxe> IMPERIUM_PICKAXE_UPGRADED =
            ITEMS.register("imperium_pickaxe_upgraded",
                    () -> new ModTools.Pickaxe(CustomToolMaterial.IMPERIUM_PICKAXE_UPGRADED, 0, 7f,
                            new Item.Properties().stacksTo(1).durability(600)));

    public static final RegistryObject<ModTools.Axe> IMPERIUM_AXE =
            ITEMS.register("imperium_axe", () -> new ModTools.Axe(CustomToolMaterial.IMPERIUM_AXE,
                    0, 7f, new Item.Properties().stacksTo(1).durability(600)));

    // armour

    // knight armour
    public static final RegistryObject<Item> HELMET =
            ITEMS.register("helmet", () -> new ArmorItem(CustomArmorMaterial.ARMOUR,
                    ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> CHESTPLATE =
            ITEMS.register("chestplate", () -> new ArmorItem(CustomArmorMaterial.ARMOUR,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> LEGGINGS =
            ITEMS.register("leggings", () -> new ArmorItem(CustomArmorMaterial.ARMOUR,
                    ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> BOOTS =
            ITEMS.register("boots", () -> new ArmorItem(CustomArmorMaterial.ARMOUR,
                    ArmorItem.Type.BOOTS, new Item.Properties()));
    // ben ten clothing
    public static final RegistryObject<Item> JACKET =
            ITEMS.register("jacket", () -> new ArmorItem(CustomArmorMaterial.JACKET,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    // Heatblast armour

    public static final RegistryObject<Item> HEATBLAST_HELMET = ITEMS.register("heatblast_helmet",
            () -> new ArmorItem(CustomArmorMaterial.HEATBLAST_ARMOUR, ArmorItem.Type.HELMET,
                    new Item.Properties().fireResistant().durability(600)));

    public static final RegistryObject<Item> HEATBLAST_CHESTPLATE = ITEMS
        .register("heatblast_chestplate", () -> new ArmorItem(CustomArmorMaterial.HEATBLAST_ARMOUR,
                ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant().durability(600)));

    public static final RegistryObject<Item> HEATBLAST_LEGGINGS = ITEMS
        .register("heatblast_leggings", () -> new ArmorItem(CustomArmorMaterial.HEATBLAST_ARMOUR,
                ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant().durability(600)));

    public static final RegistryObject<Item> HEATBLAST_BOOTS = ITEMS.register("heatblast_boots",
            () -> new ArmorItem(CustomArmorMaterial.HEATBLAST_ARMOUR, ArmorItem.Type.BOOTS,
                    new Item.Properties().fireResistant().durability(600)));

    // xlr8 armour
    public static final RegistryObject<Item> XLR8_HELMET =
            ITEMS.register("xlr8_helmet", () -> new ArmorItem(CustomArmorMaterial.XLR8_ARMOUR,
                    ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> XLR8_CHESTPLATE =
            ITEMS.register("xlr8_chestplate", () -> new ArmorItem(CustomArmorMaterial.XLR8_ARMOUR,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> XLR8_LEGGINGS =
            ITEMS.register("xlr8_leggings", () -> new ArmorItem(CustomArmorMaterial.XLR8_ARMOUR,
                    ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> XLR8_BOOTS =
            ITEMS.register("xlr8_boots", () -> new ArmorItem(CustomArmorMaterial.XLR8_ARMOUR,
                    ArmorItem.Type.BOOTS, new Item.Properties()));

    // 4 Arms
    public static final RegistryObject<Item> FOURARMS_HELMET = ITEMS.register("fourarms_helmet",
            () -> new ArmorItem(CustomArmorMaterial.FOURARMS_ARMOUR, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> FOURARMS_CHESTPLATE = ITEMS
        .register("fourarms_chestplate", () -> new ArmorItem(CustomArmorMaterial.FOURARMS_ARMOUR,
                ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> FOURARMS_LEGGINGS = ITEMS.register("fourarms_leggings",
            () -> new ArmorItem(CustomArmorMaterial.FOURARMS_ARMOUR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));

    public static final RegistryObject<Item> FOURARMS_BOOT =
            ITEMS.register("fourarms_boot", () -> new ArmorItem(CustomArmorMaterial.FOURARMS_ARMOUR,
                    ArmorItem.Type.BOOTS, new Item.Properties()));


    // INFINITUM Armour
    public static final RegistryObject<Item> INFINITUM_HELMET = ITEMS.register("infinitum_helmet",
            () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> INFINITUM_CHESTPLATE = ITEMS
        .register("infinitum_chestplate", () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR,
                ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> INFINITUM_LEGGINGS = ITEMS
        .register("infinitum_leggings", () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR,
                ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> INFINITUM_BOOTS = ITEMS.register("infinitum_boots",
            () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, ArmorItem.Type.BOOTS,
                    new Item.Properties()));
    // black diamond armour
    public static final RegistryObject<Item> BLACK_DIAMOND_HELMET = ITEMS.register(
            "black_diamond_helmet", () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                    ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> BLACK_DIAMOND_CHESTPLATE =
            ITEMS.register("black_diamond_chestplate",
                    () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                            ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> BLACK_DIAMOND_LEGGINGS = ITEMS.register(
            "black_diamond_leggings", () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                    ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> BLACK_DIAMOND_BOOTS = ITEMS.register(
            "black_diamond_boots", () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                    ArmorItem.Type.BOOTS, new Item.Properties()));
    // Food
    public static final RegistryObject<Item> OMNITRIX_APPLE = ITEMS.register("omnitrix_apple",
            () -> new Item(new Item.Properties().food(FoodInit.OMNITRIX_APPLE)));

    public static final RegistryObject<Item> ENCHANTED_OMNITRIX_APPLE =
            ITEMS.register("enchanted_omnitrix_apple", () -> new EnchantedGoldenAppleItem(
                    new Item.Properties().food(FoodInit.ENCHANTED_OMNITRIX_APPLE)));

    public static final RegistryObject<Item> FIRE_APPLE = ITEMS.register("fire_apple",
            () -> new Item(new Item.Properties().food(FoodInit.FIRE_APPLE)));

    public static final RegistryObject<Item> RUBY_APPLE = ITEMS.register("ruby_apple",
            () -> new EnchantedGoldenAppleItem(new Item.Properties().food(FoodInit.RUBY_APPLE)));
    // other items

    public static final RegistryObject<Item> OMNITRIX_WATCH =
            ITEMS.register("omnitrix_watch", Services.PLATFORM::createOmnitrixWatchItem);

    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
