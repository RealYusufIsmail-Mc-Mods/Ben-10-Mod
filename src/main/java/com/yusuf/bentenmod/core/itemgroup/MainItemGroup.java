package com.yusuf.bentenmod.core.itemgroup;

import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MainItemGroup extends CreativeModeTab {

    public static final MainItemGroup MAIN = new MainItemGroup(CreativeModeTab.TABS.length, "main");

    public MainItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.OMNITRIX.get());
    }

}