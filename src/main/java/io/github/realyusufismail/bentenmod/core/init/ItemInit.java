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
import io.github.realyusufismail.bentenmod.core.itemgroup.MainItemGroup;
import io.github.realyusufismail.bentenmod.core.material.CustomArmorMaterial;
import io.github.realyusufismail.bentenmod.core.material.CustomToolMaterial;
import io.github.realyusufismail.bentenmod.events.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    // ores
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BenTenMod.MOD_ID);

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

    // tools
    public static final RegistryObject<SwordItem> SWORD =
            ITEMS.register("sword", () -> new SwordItem(CustomToolMaterial.SWORD, 0, 7f,
                    new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<AxeItem> AXE =
            ITEMS.register("axe", () -> new AxeItem(CustomToolMaterial.AXE, 0, 8f,
                    new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<SwordItem> HEATBLAST_SWORD = ITEMS
        .register("heatblast_sword", () -> new SwordItem(CustomToolMaterial.HEATBLAST_SWORD, 0, 7f,
                new Item.Properties().fireResistant().stacksTo(1).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<SwordItem> BLACK_DIAMOND_SWORD = ITEMS
        .register("black_diamond_sword", () -> new SwordItem(CustomToolMaterial.BLACK_DIAMOND_SWORD,
                0, 7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<AxeItem> BLACK_DIAMOND_AXE = ITEMS
        .register("black_diamond_axe", () -> new AxeItem(CustomToolMaterial.BLACK_DIAMOND_AXE, 0,
                7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<PickaxeItem> BLACK_DIAMOND_PICKAXE = ITEMS.register(
            "black_diamond_pickaxe",
            () -> new PickaxeItem(CustomToolMaterial.BLACK_DIAMOND_PICKAXE, 0, 7f,
                    new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<ShovelItem> BLACK_DIAMOND_SHOVEL = ITEMS.register(
            "black_diamond_shovel", () -> new ShovelItem(CustomToolMaterial.BLACK_DIAMOND_SHOVEL, 0,
                    7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<HoeItem> BLACK_DIAMOND_HOE = ITEMS
        .register("black_diamond_hoe", () -> new HoeItem(CustomToolMaterial.BLACK_DIAMOND_HOE, 0,
                7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<SwordItem> INFINITUM_SWORD = ITEMS
        .register("infinitum_sword", () -> new SwordItem(CustomToolMaterial.INFINITUM_SWORD, 0, 7f,
                new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<AxeItem> INFINITUM_AXE =
            ITEMS.register("infinitum_axe", () -> new AxeItem(CustomToolMaterial.INFINITUM_AXE, 0,
                    7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<PickaxeItem> INFINITUM_PICKAXE = ITEMS
        .register("infinitum_pickaxe", () -> new PickaxeItem(CustomToolMaterial.INFINITUM_PICKAXE,
                0, 7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<ShovelItem> INFINITUM_SHOVEL = ITEMS
        .register("infinitum_shovel", () -> new ShovelItem(CustomToolMaterial.INFINITUM_SHOVEL, 0,
                7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<HoeItem> INFINITUM_HOE =
            ITEMS.register("infinitum_hoe", () -> new HoeItem(CustomToolMaterial.INFINITUM_HOE, 0,
                    7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<SwordItem> IMPERIUM_SWORD = ITEMS.register("imperium_sword",
            () -> new SwordItem(CustomToolMaterial.IMPERIUM_SWORD, 0, 7f,
                    new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<PickaxeItem> IMPERIUM_PICKAXE = ITEMS
        .register("imperium_pickaxe", () -> new PickaxeItem(CustomToolMaterial.IMPERIUM_PICKAXE, 0,
                7f, new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<PickaxeItem> IMPERIUM_PICKAXE_UPGRADED = ITEMS.register(
            "imperium_pickaxe_upgraded",
            () -> new PickaxeItem(CustomToolMaterial.IMPERIUM_PICKAXE_UPGRADED, 0, 7f,
                    new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<AxeItem> IMPERIUM_AXE =
            ITEMS.register("imperium_axe", () -> new AxeItem(CustomToolMaterial.IMPERIUM_AXE, 0, 7f,
                    new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

    // armour

    // knight armour
    public static final RegistryObject<Item> HELMET =
            ITEMS.register("helmet", () -> new LegendaryArmor(CustomArmorMaterial.ARMOUR,
                    EquipmentSlot.HEAD, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> CHESTPLATE =
            ITEMS.register("chestplate", () -> new LegendaryArmor(CustomArmorMaterial.ARMOUR,
                    EquipmentSlot.CHEST, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> LEGGINGS =
            ITEMS.register("leggings", () -> new LegendaryArmor(CustomArmorMaterial.ARMOUR,
                    EquipmentSlot.LEGS, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> BOOTS =
            ITEMS.register("boots", () -> new LegendaryArmor(CustomArmorMaterial.ARMOUR,
                    EquipmentSlot.FEET, new Item.Properties().tab(MainItemGroup.MAIN)));
    // ben ten clothing
    public static final RegistryObject<Item> JACKET =
            ITEMS.register("jacket", () -> new JacketArmorItem(CustomArmorMaterial.JACKET,
                    EquipmentSlot.CHEST, new Item.Properties().tab(MainItemGroup.MAIN)));

    // Heatblast armour

    public static final RegistryObject<Item> HEATBLAST_HELMET = ITEMS.register("heatblast_helmet",
            () -> new HeatBlastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlot.HEAD,
                    new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> HEATBLAST_CHESTPLATE = ITEMS.register(
            "heatblast_chestplate",
            () -> new HeatBlastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlot.CHEST,
                    new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> HEATBLAST_LEGGINGS = ITEMS.register(
            "heatblast_leggings",
            () -> new HeatBlastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlot.LEGS,
                    new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> HEATBLAST_BOOTS = ITEMS.register("heatblast_boots",
            () -> new HeatBlastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlot.FEET,
                    new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

    // xlr8 armour
    public static final RegistryObject<Item> XLR8_HELMET =
            ITEMS.register("xlr8_helmet", () -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR,
                    EquipmentSlot.HEAD, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> XLR8_CHESTPLATE =
            ITEMS.register("xlr8_chestplate", () -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR,
                    EquipmentSlot.CHEST, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> XLR8_LEGGINGS =
            ITEMS.register("xlr8_leggings", () -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR,
                    EquipmentSlot.LEGS, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> XLR8_BOOTS =
            ITEMS.register("xlr8_boots", () -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR,
                    EquipmentSlot.FEET, new Item.Properties().tab(MainItemGroup.MAIN)));
    // 4 Arms

    public static final RegistryObject<Item> FOURARMS_HELMET = ITEMS.register("fourarms_helmet",
            () -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlot.HEAD,
                    new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> FOURARMS_CHESTPLATE = ITEMS.register(
            "fourarms_chestplate", () -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR,
                    EquipmentSlot.CHEST, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> FOURARMS_LEGGINGS = ITEMS.register("fourarms_leggings",
            () -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlot.LEGS,
                    new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> FOURARMS_BOOT = ITEMS.register("fourarms_boot",
            () -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlot.FEET,
                    new Item.Properties().tab(MainItemGroup.MAIN)));


    // INFINITUM Armour
    public static final RegistryObject<Item> INFINITUM_HELMET = ITEMS.register("infinitum_helmet",
            () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, EquipmentSlot.HEAD,
                    new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> INFINITUM_CHESTPLATE = ITEMS
        .register("infinitum_chestplate", () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR,
                EquipmentSlot.CHEST, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> INFINITUM_LEGGINGS = ITEMS
        .register("infinitum_leggings", () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR,
                EquipmentSlot.LEGS, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> INFINITUM_BOOTS = ITEMS.register("infinitum_boots",
            () -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, EquipmentSlot.FEET,
                    new Item.Properties().tab(MainItemGroup.MAIN)));
    // black diamond armour
    public static final RegistryObject<Item> BLACK_DIAMOND_HELMET = ITEMS.register(
            "black_diamond_helmet", () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                    EquipmentSlot.HEAD, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> BLACK_DIAMOND_CHESTPLATE =
            ITEMS.register("black_diamond_chestplate",
                    () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                            EquipmentSlot.CHEST, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> BLACK_DIAMOND_LEGGINGS = ITEMS.register(
            "black_diamond_leggings", () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                    EquipmentSlot.LEGS, new Item.Properties().tab(MainItemGroup.MAIN)));

    public static final RegistryObject<Item> BLACK_DIAMOND_BOOTS = ITEMS.register(
            "black_diamond_boots", () -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR,
                    EquipmentSlot.FEET, new Item.Properties().tab(MainItemGroup.MAIN)));
    // Food
    public static final RegistryObject<Item> OMNITRIX_APPLE =
            ITEMS.register("omnitrix_apple", () -> new Item(
                    new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.OMNITRIX_APPLE)));

    public static final RegistryObject<Item> ENCHANTED_OMNITRIX_APPLE =
            ITEMS.register("enchanted_omnitrix_apple",
                    () -> new EnchantedGoldenAppleItem(new Item.Properties().tab(MainItemGroup.MAIN)
                        .food(FoodInit.ENCHANTED_OMNITRIX_APPLE)));

    public static final RegistryObject<Item> FIRE_APPLE =
            ITEMS.register("fire_apple", () -> new Item(
                    new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.FIRE_APPLE)));

    public static final RegistryObject<Item> RUBY_APPLE =
            ITEMS.register("ruby_apple", () -> new EnchantedGoldenAppleItem(
                    new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.RUBY_APPLE)));
    // other items

    public static final RegistryObject<Item> OMNITRIX_WATCH = register("omnitrix_watch");

    private static RegistryObject<Item> register(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));
    }
}
