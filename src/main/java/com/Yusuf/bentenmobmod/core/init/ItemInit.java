package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.common.events.HeatblastArmor;
import com.yusuf.bentenmobmod.common.events.JacketArmorItem;
import com.yusuf.bentenmobmod.common.events.LegendaryArmor;
import com.yusuf.bentenmobmod.common.events.Xlr8Armor;
import com.yusuf.bentenmobmod.common.material.CustomArmorMaterial;
import com.yusuf.bentenmobmod.common.material.CustomToolMaterial;
import com.yusuf.bentenmobmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmobmod.item.ModSpawnEggItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.EnchantedGoldenAppleItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	// ores
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

	public static final RegistryObject<Item> LEGENDARY_ORE = ITEMS.register("legendary_ore",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> OMNITRIX = ITEMS.register("omnitrix",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> FIRE = ITEMS.register("fire",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));
	
	public static final RegistryObject<Item> SPEED = ITEMS.register("speed",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BLACK_DIAMOND = ITEMS.register("black_diamond",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BLACK_DIAMOND_SCRAP = ITEMS.register("black_diamond_scrap",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));


	public static final RegistryObject<Item> INFINITUM = ITEMS.register("infinitum",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> INFINITUM_SCRAP = ITEMS.register("infinitum_scrap",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));



	// tools
	public static final RegistryObject<SwordItem> SWORD = ITEMS.register("sword",
			() -> new SwordItem(CustomToolMaterial.SWORD, 14, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<AxeItem> AXE = ITEMS.register("axe",
			() -> new AxeItem(CustomToolMaterial.AXE, 6, 8f, new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> HEATBLAST_SWORD = ITEMS.register("heatblast_sword",
			() -> new SwordItem(CustomToolMaterial.HEATBLAST_SWORD, 17, 7f,
					new Item.Properties().fireResistant().stacksTo(1).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> BLACK_DIAMOND_SWORD = ITEMS.register("black_diamond_sword",
			() -> new SwordItem(CustomToolMaterial.BLACK_DIAMOND_SWORD, 14, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

//armour
	
	//knight armour
	public static final RegistryObject<Item> HELMET = ITEMS.register("helmet",
			() -> new LegendaryArmor(CustomArmorMaterial.ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> CHESTPLATE = ITEMS.register("chestplate",
			() -> new LegendaryArmor(CustomArmorMaterial.ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> LEGGINGS = ITEMS.register("leggings",
			() -> new LegendaryArmor(CustomArmorMaterial.ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BOOT = ITEMS.register("boot",
			() -> new LegendaryArmor(CustomArmorMaterial.ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));
	//ben ten clothing
	public static final RegistryObject<Item> JACKET = ITEMS.register("jacket",
			() -> new JacketArmorItem(CustomArmorMaterial.JACKET, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));
	
	//Heatblast armour
	
	public static final RegistryObject<Item> HEATBLAST_HELMET = ITEMS.register("heatblast_helmet",
			() -> new HeatblastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> HEATBLAST_CHESTPLATE = ITEMS.register("heatblast_chestplate",
			() -> new HeatblastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> HEATBLAST_LEGGINGS = ITEMS.register("heatblast_leggings",
			() -> new HeatblastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> HEATBLAST_BOOT = ITEMS.register("heatblast_boot",
			() -> new HeatblastArmor(CustomArmorMaterial.HEATBLAST_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().fireResistant().durability(600).tab(MainItemGroup.MAIN)));
	
	//xlr8 armour
	public static final RegistryObject<Item> XLR8_HELMET = ITEMS.register("xlr8_helmet",
			() -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> XLR8_CHESTPLATE = ITEMS.register("xlr8_chestplate",
			() -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> XLR8_LEGGINGS = ITEMS.register("xlr8_leggings",
			() -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> XLR8_BOOT = ITEMS.register("xlr8_boot",
			() -> new Xlr8Armor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));
	//4 Arms
	/*
	public static final RegistryObject<Item> 4ARMS_HELMET = ITEMS.register("4arms_helmet",
			() -> new FourArmsArmor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> 4ARMS_CHESTPLATE = ITEMS.register("4arms_chestplate",
			() -> new FourArmsArmor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> 4ARMS_LEGGINGS = ITEMS.register("4arms_leggings",
			() -> new FourArmsArmor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> 4ARMS_BOOT = ITEMS.register("4arms_boot",
			() -> new FourArmsArmor(CustomArmorMaterial.XLR8_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));
		*/

	//black diamond armour

	// Food
	public static final RegistryObject<Item> OMNITRIX_APPLE = ITEMS.register("omnitrix_apple",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.OMNITRIX_APPLE)));

	public static final RegistryObject<Item> ENCHANTED_OMNITRIX_APPLE = ITEMS.register("enchanted_omnitrix_apple",
			() -> new EnchantedGoldenAppleItem(
					new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.ENCHANTED_OMNITRIX_APPLE)));

	public static final RegistryObject<Item> FIRE_APPLE = ITEMS.register("fire_apple",
			() -> new Item( new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.FIRE_APPLE)));
	

	public static final RegistryObject<Item> RUBY_APPLE = ITEMS.register("ruby_apple",
			() -> new EnchantedGoldenAppleItem(
					 new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.RUBY_APPLE)));
	//other items

	public static final RegistryObject<ModSpawnEggItem> VILGAX_SPAWN_EGG = ITEMS.register("vilgax_spawn_egg",
			() -> new ModSpawnEggItem(EntityTypesInit.VILGAX_ENTITY, 0xC4AA79, 0x7A5F22,
					new Item.Properties().tab(MainItemGroup.MAIN).stacksTo(16)));


}
