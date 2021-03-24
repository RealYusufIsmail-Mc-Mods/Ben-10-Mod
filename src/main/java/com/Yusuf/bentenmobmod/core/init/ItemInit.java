package com.Yusuf.bentenmobmod.core.init;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.common.material.CustomArmorMaterial;
import com.Yusuf.bentenmobmod.common.material.CustomToolMaterial;
import com.Yusuf.bentenmobmod.core.itemgroup.MainItemGroup;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

	public static final RegistryObject<Item> LEGENDARY_ORE = ITEMS.register("legendary_ore",
			() -> new Item(new Item.Properties().group(MainItemGroup.MAIN)));

	public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
			() -> new Item(new Item.Properties().group(MainItemGroup.MAIN)));

	public static final RegistryObject<SwordItem> SWORD = ITEMS.register("sword",
			() -> new SwordItem(CustomToolMaterial.TOOL, 10, 1.6f, 
					new Item.Properties().group(MainItemGroup.MAIN)));

	public static final RegistryObject<ArmorItem> HELMET = ITEMS.register("helmet",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.HEAD,
					new Item.Properties().group(MainItemGroup.MAIN)));

	public static final RegistryObject<ArmorItem> CHESTPLATE = ITEMS.register("chestplate",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.CHEST,
					new Item.Properties().group(MainItemGroup.MAIN)));

	public static final RegistryObject<ArmorItem> BOOTS = ITEMS.register("boots",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.FEET,
					new Item.Properties().group(MainItemGroup.MAIN)));

	public static final RegistryObject<ArmorItem> LEGGINGS = ITEMS.register("leggings",
			() -> new ArmorItem(CustomArmorMaterial.ARMOUR, EquipmentSlotType.LEGS,
					new Item.Properties().group(MainItemGroup.MAIN)));

}
