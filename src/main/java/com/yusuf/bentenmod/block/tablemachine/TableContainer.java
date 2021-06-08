package com.yusuf.bentenmod.block.tablemachine;

import com.yusuf.bentenmod.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

import java.util.Objects;

public class TableContainer extends Container {

    protected TableContainer(int id, PlayerInventory playerInventory, TileEntity te) {
        super(ContainerInit.TABLE.get(), id);

        //Adding the slots to the GUI.
        addSlot(new Slot((IInventory) te, 0, 13, 18));
        addSlot(new Slot((IInventory) te, 1, 59, 18));
        addSlot(new Slot((IInventory) te, 2, 36, 55));
        addSlot(new OutputSlot((IInventory) te, 3, 134, 36));

    }
    

    public TableContainer(int id, PlayerInventory playerInv, PacketBuffer buffer) {
        this(id, playerInv, getTileEntity(playerInv, buffer));
    }

    private static TableTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (te instanceof TableTileEntity) {
            return (TableTileEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return false;
    }


    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (index < TableTileEntity.slots
                    && !this.moveItemStackTo(stack1, TableTileEntity.slots, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(stack1, 0, TableTileEntity.slots, false)) {
                return ItemStack.EMPTY;
            }
            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    //the end
}
