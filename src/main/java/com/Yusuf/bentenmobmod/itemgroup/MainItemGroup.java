package com.Yusuf.bentenmobmod.itemgroup;

import com.Yusuf.bentenmobmod.init.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MainItemGroup extends ItemGroup {

	public static final MainItemGroup MAIN = new MainItemGroup(ItemGroup.TABS.length, "main");

	public MainItemGroup(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.SWORD.get());
	}

}
