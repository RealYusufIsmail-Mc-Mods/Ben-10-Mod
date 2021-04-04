package com.Yusuf.bentenmobmod.init;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.material.CustomArmorMaterial;
import com.Yusuf.bentenmobmod.common.material.CustomToolMaterial;
import com.Yusuf.bentenmobmod.item.ModSpawnEggItem;
import com.Yusuf.bentenmobmod.itemgroup.MainItemGroup;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.EnchantedGoldenAppleItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

	public static final RegistryObject<Item> LEGENDARY_ORE = ITEMS.register("legendary_ore",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> OMNITRIX = ITEMS.register("omnitrix",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> fire = ITEMS.register("fire",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN)));

	// ores

	public static final RegistryObject<SwordItem> SWORD = ITEMS.register("sword",
			() -> new SwordItem(CustomToolMaterial.TOOL, 14, 7f,
					new Item.Properties().durability(400).tab(MainItemGroup.MAIN))))))));

	public static final RegistryObject<AxeItem> AXE = ITEMS.register("axe",
			() -> new AxeItem(CustomToolMaterial.TOOL, 6, 8f, new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> HEATBLAST_SWORD = ITEMS.register("heatblast_sword",
			() -> new SwordItem(CustomToolMaterial.TOOL, 17, 7f,
					new Item.Properties().fireResistant().stacksTo(1).tab(MainItemGroup.MAIN))))))));

	// tools

	public static final RegistryObject<Item> HELMET = ITEMS.register("helmet",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> CHESTPLATE = ITEMS.register("chestplate",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> LEGGINGS = ITEMS.register("leggings",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> BOOT = ITEMS.register("boot",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().tab(MainItemGroup.MAIN)));
	// Food
	public static final RegistryObject<Item> OMNITRIX_APPLE = ITEMS.register("omnitrix_apple",
			() -> new Item(new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.OMNITRIX_APPLE)));

	public static final RegistryObject<Item> ENCHANTED_OMNITRIX_APPLE = ITEMS.register("enchanted_omnitrix_apple",
			() -> new EnchantedGoldenAppleItem(
					new Item.Properties().tab(MainItemGroup.MAIN).food(FoodInit.ENCHANTED_OMNITRIX_APPLE)));

	// armour like axes

	public static final RegistryObject<ModSpawnEggItem> VILGAX_SPAWN_EGG = ITEMS.register("vilgax_spawn_egg",
			() -> new ModSpawnEggItem(EntityTypesInit.VILGAX_ENTITY, 0xC4AA79, 0x7A5F22,
					new Item.Properties().tab(MainItemGroup.MAIN).stacksTo(16)));

	/* other items */

}
