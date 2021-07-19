package com.yusuf.bentenmod.modules.alientable.recipes;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;

public class AlienTableResultSlot extends CraftingResultSlot {
    private final PlayerEntity player;
    private final CraftingInventory craftSlots;

    public AlienTableResultSlot(PlayerEntity p_i45790_1_, CraftingInventory p_i45790_2_, IInventory p_i45790_3_, int p_i45790_4_, int p_i45790_5_, int p_i45790_6_) {
        super(p_i45790_1_, p_i45790_2_, p_i45790_3_, p_i45790_4_, p_i45790_5_, p_i45790_6_);
        this.player = p_i45790_1_;
        this.craftSlots = p_i45790_2_;
    }

    @Override
    public ItemStack onTake(PlayerEntity p_190901_1_, ItemStack p_190901_2_) {
        this.checkTakeAchievements(p_190901_2_);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(p_190901_1_);
        NonNullList<ItemStack> nonnulllist = p_190901_1_.level.getRecipeManager().getRemainingItemsFor(IRecipeType.CRAFTING, this.craftSlots, p_190901_1_.level);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = this.craftSlots.getItem(i);
            ItemStack itemstack1 = nonnulllist.get(i);
            if (!itemstack.isEmpty()) {
                this.craftSlots.removeItem(i, 1);
                itemstack = this.craftSlots.getItem(i);
            }

            if (!itemstack1.isEmpty()) {
                if (itemstack.isEmpty()) {
                    this.craftSlots.setItem(i, itemstack1);
                } else if (ItemStack.isSame(itemstack, itemstack1) && ItemStack.tagMatches(itemstack, itemstack1)) {
                    itemstack1.grow(itemstack.getCount());
                    this.craftSlots.setItem(i, itemstack1);
                } else if (!this.player.inventory.add(itemstack1)) {
                    this.player.drop(itemstack1, false);
                }
            }
        }

        return p_190901_2_;
    }
}
