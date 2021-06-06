
package com.yusuf.bentenmod.item;

import com.yusuf.bentenmod.core.init.ItemInit;
import com.yusuf.bentenmod.gui.GuiHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OmnitrixItem extends Item {

    public OmnitrixItem(Item.Properties properties) {
        super(properties);
    }

    @Override //this method is responsible for what happens when you right click
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isCrouching()) {
            final ItemStack stack = getHeldOmnitrix(player);
            GuiHandler.openGUI(world, player, stack);
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getHeldItem(hand));
    }


    public static ItemStack getHeldItem(PlayerEntity player, Item item) {
        if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == item) {
            return player.getHeldItemMainhand();
        } else if (!player.getHeldItemOffhand().isEmpty() && player.getHeldItemOffhand().getItem() == item) {
            return player.getHeldItemOffhand();
        }

        return ItemStack.EMPTY;
    }

    public static ItemStack getHeldOmnitrix(PlayerEntity player) {
        return getHeldItem(player, ItemInit.omnitrix);
    }





}

