package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.common.events.*;
import com.yusuf.bentenmod.common.material.CustomArmorMaterial;
import com.yusuf.bentenmod.common.material.CustomToolMaterial;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
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

	public static final RegistryObject<Item> IMPERIUM = ITEMS.register("imperium",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> INFINITUM_SCRAP = ITEMS.register("infinitum_scrap",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	// raw ores

	public static final RegistryObject<Item> RAW_LEGENDARY = ITEMS.register("raw_legendary",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> RAW_OMNITRIX = ITEMS.register("raw_omnitrix",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> RAW_IMPERIUM = ITEMS.register("raw_imperium",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));
	// tools
	public static final RegistryObject<SwordItem> SWORD = ITEMS.register("sword",
			() -> new SwordItem(CustomToolMaterial.SWORD, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<AxeItem> AXE = ITEMS.register("axe",
			() -> new AxeItem(CustomToolMaterial.AXE, 0, 8f, new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> HEATBLAST_SWORD = ITEMS.register("heatblast_sword",
			() -> new SwordItem(CustomToolMaterial.HEATBLAST_SWORD, 0, 7f,
					new Item.Properties().fireResistant().stacksTo(1).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> BLACK_DIAMOND_SWORD = ITEMS.register("black_diamond_sword",
			() -> new SwordItem(CustomToolMaterial.BLACK_DIAMOND_SWORD, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<AxeItem> BLACK_DIAMOND_AXE = ITEMS.register("black_diamond_axe",
			() -> new AxeItem(CustomToolMaterial.BLACK_DIAMOND_AXE, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<PickaxeItem> BLACK_DIAMOND_PICKAXE = ITEMS.register("black_diamond_pickaxe",
			() -> new PickaxeItem(CustomToolMaterial.BLACK_DIAMOND_PICKAXE, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<ShovelItem> BLACK_DIAMOND_SHOVEL = ITEMS.register("black_diamond_shovel",
			() -> new ShovelItem(CustomToolMaterial.BLACK_DIAMOND_SHOVEL, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<HoeItem> BLACK_DIAMOND_HOE = ITEMS.register("black_diamond_hoe",
			() -> new HoeItem(CustomToolMaterial.BLACK_DIAMOND_HOE, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> INFINITUM_SWORD = ITEMS.register("infinitum_sword",
			() -> new SwordItem(CustomToolMaterial.INFINITUM_SWORD, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<AxeItem> INFINITUM_AXE = ITEMS.register("infinitum_axe",
			() -> new AxeItem(CustomToolMaterial.INFINITUM_AXE, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<PickaxeItem> INFINITUM_PICKAXE = ITEMS.register("infinitum_pickaxe",
			() -> new PickaxeItem(CustomToolMaterial.INFINITUM_PICKAXE, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<ShovelItem> INFINITUM_SHOVEL = ITEMS.register("infinitum_shovel",
			() -> new ShovelItem(CustomToolMaterial.INFINITUM_SHOVEL, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

	public static final RegistryObject<HoeItem> INFINITUM_HOE = ITEMS.register("infinitum_hoe",
			() -> new HoeItem(CustomToolMaterial.INFINITUM_HOE, 0, 7f,
					new Item.Properties().stacksTo(1).durability(600).tab(MainItemGroup.MAIN)));

//armour

	// knight armour
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
	// ben ten clothing
	public static final RegistryObject<Item> JACKET = ITEMS.register("jacket",
			() -> new JacketArmorItem(CustomArmorMaterial.JACKET, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	// Heatblast armour

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

	// xlr8 armour
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
	// 4 Arms

	public static final RegistryObject<Item> FOURARMS_HELMET = ITEMS.register("fourarms_helmet",
			() -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> FOURARMS_CHESTPLATE = ITEMS.register("fourarms_chestplate",
			() -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> FOURARMS_LEGGINGS = ITEMS.register("fourarms_leggings",
			() -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> FOURARMS_BOOT = ITEMS.register("fourarms_boot",
			() -> new FourArmsArmor(CustomArmorMaterial.FOURARMS_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));

//INFINITUM Armour
	public static final RegistryObject<Item> INFINITUM_HELMET = ITEMS.register("infinitum_helmet",
			() -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> INFINITUM_CHESTPLATE = ITEMS.register("infinitum_chestplate",
			() -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> INFINITUM_LEGGINGS = ITEMS.register("infinitum_leggings",
			() -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> INFINITUM_BOOTS = ITEMS.register("infinitum_boots",
			() -> new ArmorItem(CustomArmorMaterial.INFINITUM_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));
	// black diamond armour
	public static final RegistryObject<Item> BLACK_DIAMOND_HELMET = ITEMS.register("black_diamond_helmet",
			() -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BLACK_DIAMOND_CHESTPLATE = ITEMS.register("black_diamond_chestplate",
			() -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BLACK_DIAMOND_LEGGINGS = ITEMS.register("black_diamond_leggings",
			() -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BLACK_DIAMOND_BOOTS = ITEMS.register("black_diamond_boots",
			() -> new ArmorItem(CustomArmorMaterial.BLACK_DIAMOND_ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));
	// Food
	public static final RegistryObject<Item> OMNITRIX_APPLE = ITEMS.register("omnitrix_apple",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.OMNITRIX_APPLE)));

	public static final RegistryObject<Item> ENCHANTED_OMNITRIX_APPLE = ITEMS.register("enchanted_omnitrix_apple",
			() -> new EnchantedGoldenAppleItem(
					new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.ENCHANTED_OMNITRIX_APPLE)));

	public static final RegistryObject<Item> FIRE_APPLE = ITEMS.register("fire_apple",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.FIRE_APPLE)));

	public static final RegistryObject<Item> RUBY_APPLE = ITEMS.register("ruby_apple",
			() -> new EnchantedGoldenAppleItem(
					new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.RUBY_APPLE)));
	// other items

//0xC4AA79, 0x7A5F22
}
