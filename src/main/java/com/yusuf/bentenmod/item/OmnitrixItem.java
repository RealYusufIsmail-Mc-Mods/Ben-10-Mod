
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!player.isCrouching()) {
            final ItemStack stack = getHeldOmnitrix(player);
            GuiHandler.openScreen(world, player, stack);

        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }


    public static ItemStack getHeldItem(PlayerEntity player, Item item) {
        if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == item) {
            return player.getMainHandItem();
        } else if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == item) {
            return player.getMainHandItem();
        }

        return ItemStack.EMPTY;
    }

    public static ItemStack getHeldOmnitrix(PlayerEntity player) {
        return getHeldItem(player, ItemInit.omnitrix);
    }





}

