package com.yusuf.bentenmod.gui;

import com.mojang.datafixers.util.Pair;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.backpack.BackpackItem;
import com.yusuf.bentenmod.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.yusuf.realyusufismailcore.backpack.SBContainerSlot;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nonnull;

public class SBContainer extends Container {

    public SBContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(ContainerInit.BACKPACK_CONTAINER.get(), id);

        playerInv = playerInventory;
        ItemStack stack = findBackpack(playerInventory.player);

        if (stack.isEmpty()) {
            playerInventory.player.closeContainer();
            return;
        }

        backpackItem = stack;
        LazyOptional<IItemHandler> capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        capability.ifPresent(cap -> {
            // The check here is likely redundant
            if (cap instanceof BackpackItem) {
                slotcount = cap.getSlots();
                itemKey = stack.getDescriptionId();

                addMySlots(cap);
                addPlayerSlots(playerInv);
            }
        });

        if (!capability.isPresent()) {
            playerInventory.player.closeContainer();
        }
    }

    public int slotcount = 0;
    public ItemStack backpackItem;
    public String itemKey = "";
    private PlayerInventory playerInv;

    // Used to check if the player is still holding the bag
    private Pair<SlotOwner, Integer> slotLocation;

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        if (slotLocation.getFirst() == SlotOwner.OFFHAND) {
            return BackpackItem.isBackpack(playerIn.getOffhandItem()); //whoops guess you can...
        }

        if (slotLocation.getFirst() == SlotOwner.CURIOS) {
            return CuriosApi.getCuriosHelper().findEquippedCurio(BackpackItem::isBackpack, playerIn)
                    .map(e -> !e.getRight().isEmpty())
                    .orElse(false);
        }

        if (slotLocation.getFirst() == SlotOwner.INVENTORY) {
            return !BackpackItem.isBackpack(playerIn.inventory.getItem(slotLocation.getSecond()));
        }

        return false;
    }

    @Override
    public ItemStack clicked(int slot, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (slot >= 0) {
            if (getSlot(slot).getItem().getItem() instanceof BackpackItem)
                return ItemStack.EMPTY;
        }
        if (clickTypeIn == ClickType.SWAP)
            return ItemStack.EMPTY;

        if (slot >= 0) getSlot(slot).container.setChanged();
        return super.clicked(slot, dragType, clickTypeIn, player);
    }

    private void addPlayerSlots(PlayerInventory playerInventory) {
        int originX = ((BackpackItem) backpackItem.getItem()).backpack.slotXOffset;
        int originY = ((BackpackItem) backpackItem.getItem()).backpack.slotYOffset;

        //Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = originX + col * 18;
                int y = originY + row * 18;
                int index = (col + row * 9) + 9;
                this.addSlot(new Slot(playerInventory, index, x+1, y+1));
            }
        }

        //Hotbar
        for (int col = 0; col < 9; col++) {
            int x = originX + col * 18;
            int y = originY + 58;
            this.addSlot(new Slot(playerInventory, col, x+1, y+1));
        }
    }

    private void addMySlots(IItemHandler handler) {
        int cols = slotcount == 18? 9:11;
        int rows = slotcount / cols;
        int slotindex = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = 7 + col * 18;
                int y = 17 + row * 18;

                this.addSlot(new SBContainerSlot(handler, slotindex, x + 1, y + 1));
                slotindex++;
                if (slotindex >= slotcount)
                    break;
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            int bagslotcount = slots.size() - playerIn.inventory.items.size();
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < bagslotcount) {
                if (!this.moveItemStackTo(itemstack1, bagslotcount, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(itemstack1, 0, bagslotcount, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        }
        return itemstack;
    }

    @Nonnull
    private ItemStack findBackpack(PlayerEntity playerEntity) {
        PlayerInventory inv = playerEntity.inventory;

        if (playerEntity.getOffhandItem().getItem() instanceof BackpackItem) {
            for (int i = 0; i <= 35; i++) {
                ItemStack stack = inv.getItem(i);
                if (stack == playerEntity.getOffhandItem()) {
                    slotLocation = Pair.of(SlotOwner.INVENTORY, i);
                    return stack;
                }
            }
        }

        if (playerEntity.getOffhandItem().getItem() instanceof BackpackItem) {
            slotLocation = Pair.of(SlotOwner.OFFHAND, -1); // -1 because we know what slot the offhand is
            return playerEntity.getOffhandItem();
        }

        // Before a full inventory, check the players curios if loaded
        if (BenTenMod.curiosLoaded) {
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(BackpackItem::isBackpack, playerEntity)
                    .map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            if (!stack.isEmpty()) {
                slotLocation = Pair.of(SlotOwner.CURIOS, -1);
                return stack;
            }
        }

        for (int i = 0; i <= 35; i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof BackpackItem) {
                slotLocation = Pair.of(SlotOwner.INVENTORY, i);
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private enum SlotOwner {
        CURIOS,
        OFFHAND,
        INVENTORY
    }
}
