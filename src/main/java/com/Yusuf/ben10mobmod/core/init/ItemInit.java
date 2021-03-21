package com.Yusuf.ben10mobmod.core.init;

import com.Yusuf.ben10mobmod.Main;


import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

	public static final RegistryObject<Item> SWORD = ITEMS.register("sword",
			() -> new Item(new Item.Properties().group(ItemGroup.COMBAT)));
	
	

}
