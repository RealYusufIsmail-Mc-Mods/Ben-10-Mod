package com.yusuf.bentenmod.gui;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.backpack.BackpackItem;
import com.yusuf.bentenmod.backpack.BackpackItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class FilterContainer  extends Container {
    public FilterContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(Main.FILTER_CONTAINER.get(), id);
        player = playerInventory.player;

        item = findBackpack(player);
        if (item.isEmpty()) {
            player.closeContainer();
            return;
        }

        addPlayerSlots(playerInventory);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        if (slotID == -106)
            return BackpackItem.isBackpack(playerIn.getMainHandItem()); //whoops guess you can...

        return BackpackItem.isBackpack(playerIn.inventory.getItem(slotID));
    }

    private int slotID;
    private PlayerEntity player;
    public ItemStack item;

    @Nonnull
    private ItemStack findBackpack(PlayerEntity playerEntity) {
        PlayerInventory inv = playerEntity.inventory;

        if (BackpackItem.isBackpack(playerEntity.getMainHandItem())) {
            for (int i = 0; i <= 35; i++) {
                ItemStack stack = inv.getItem(i);
                if (stack == playerEntity.getMainHandItem()) {
                    slotID = i;
                    return stack;
                }
            }
        } else if (BackpackItem.isBackpack(playerEntity.getMainHandItem())) {
            slotID = -106;
            return playerEntity.getMainHandItem();
        }
        else {
            for (int i = 0; i <= 35; i++) {
                ItemStack stack = inv.getItem(i);
                if (BackpackItem.isBackpack(stack)) {
                    slotID = i;
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }


    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity playerIn) {
        if (slotId >= 0 && getSlot(slotId).getItem() == playerIn.getMainHandItem())
            return ItemStack.EMPTY;

        if (clickTypeIn == ClickType.SWAP)
            return ItemStack.EMPTY;

        if (slotId >= 0) getSlot(slotId).container.setChanged();
        return super.clicked(slotId, dragType, clickTypeIn, playerIn);
    }

    public int getFilterOpts() {
        return item.getOrCreateTag().getInt("Filter-OPT");
    }

    public boolean getPickup() {
        return item.getOrCreateTag().getBoolean("Pickup");
    }

    public boolean togglePickup() {
        CompoundNBT nbt = item.getOrCreateTag();

        boolean Pickup = !nbt.getBoolean("Pickup");
        nbt.putBoolean("Pickup",Pickup);

        if (player.getCommandSenderWorld().isClientSide)
            Main.network.sendToServer(new ToggleMessage());
        return Pickup;
    }

    public int setFilterOpts(int newOpts) {
        CompoundNBT nbt = item.getOrCreateTag();
        nbt.putInt("Filter-OPT", newOpts);
        item.setTag(nbt);
        if (player.getCommandSenderWorld().isClientSide)
            Main.network.sendToServer(new FilterMessage(newOpts));
        return newOpts;
    }

    public void saveFilter(int newOpts) {
        CompoundNBT nbt = item.getOrCreateTag();
        nbt.putInt("Filter-OPT", newOpts);
        item.setTag(nbt);
    }

    public void addPlayerSlots(PlayerInventory playerInventory) {

        int originX = 7;
        int originY = 83;

        //Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = originX + col * 18;
                int y = originY + row * 18;
                this.addSlot(new Slot(playerInventory, (col + row * 9) + 9, x + 1, y + 1));
            }
        }
        //Hot-bar
        for (int col = 0; col < 9; col++) {
            int x = originX + col * 18;
            int y = originY + 58;
            this.addSlot(new Slot(playerInventory, col, x + 1, y + 1));
        }
    }

    @Override
    public boolean clickMenuButton(PlayerEntity playerIn, int id) {
        item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            if (playerIn.inventory.getCarried().isEmpty())
                ((BackpackItemHandler) handler).getFilterHandler().extractItem(id, 1, false);
            else {
                ItemStack fake = playerIn.inventory.getCarried().copy();
                fake.setCount(1);

                ((BackpackItemHandler) handler).getFilterHandler().insertItem(id, fake, false);
            }
        });
        //SimplyBackpacks.LOGGER.info("EnchantPacket: " + id);

        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}

