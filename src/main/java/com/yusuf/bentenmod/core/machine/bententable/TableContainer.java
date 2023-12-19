/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.yusuf.bentenmod.core.machine.bententable;

import com.yusuf.bentenmod.core.init.ContainerInit;
import com.yusuf.bentenmod.core.machine.bententable.slot.OutputSlot;
import java.util.Objects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class TableContainer extends Container {
    private final IIntArray data;

    /*
     * ALT + ENTER > CONSTRUCTOR
     * SHIFT + F6 > CHANGE VARIABLE NAME
     */
    protected TableContainer(int id, PlayerInventory inv, TileEntity te, IIntArray data) {
        super(ContainerInit.TABLE_CONTAINER.get(), id);

        this.data = data;

        addSlot(new Slot((IInventory) te, 0, 16, 40));
        addSlot(new Slot((IInventory) te, 1, 43, 40));
        addSlot(new Slot((IInventory) te, 2, 70, 40));

        addSlot(new OutputSlot((IInventory) te, 3, 138, 40));

        // player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, (166 - (4 - row) * 18 - 10) + 13));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142 + 13));
        }
        addDataSlots(data);
    }

    public TableContainer(int id, PlayerInventory playerInv, PacketBuffer buffer) {
        this(id, playerInv, getTileEntity(playerInv, buffer), new IntArray(2));
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
        return true;
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

    public int getProcess() {
        int process = data.get(0);
        int maxTick = data.get(1);
        return maxTick != 0 && process != 0 ? process * 24 / maxTick : 0;
    }
}
