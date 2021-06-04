package com.yusuf.bentenmod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OmnitrixItem extends Item {

    public OmnitrixItem(Item.Properties properties) {
        super(properties);
    }

    @Override //this method is responsible for what happens when you right click
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    }

    @Override //this method is responsible for what happens when you right click
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isCrouching()) {
            //Something will happen here
        }
    }




}
