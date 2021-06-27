package com.yusuf.bentenmod.backpack;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SBContainerSlot extends SlotItemHandler {
    public SBContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        return super.getMaxStackSize();
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        //check for shulkers.
        if (stack.getItem() instanceof BlockItem) {
            if (((BlockItem) stack.getItem()).getBlock().is(BlockTags.SHULKER_BOXES)) {
                return false;
            }
        }
        if (stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent())
            return false;
        if (stack.hasTag()) {
            CompoundNBT tag = stack.getTag();
            return !(tag.contains("Items") || tag.contains("Inventory"));
        }
        return true;
    }

    @Override
    public void setChanged() {
        super.setChanged();

//        if (getItemHandler() instanceof BackpackItemHandler)
//            ((BackpackItemHandler) getItemHandler()).setDirty();
    }
}
