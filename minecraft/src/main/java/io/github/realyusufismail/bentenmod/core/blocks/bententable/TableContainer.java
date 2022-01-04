/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import io.github.realyusufismail.bentenmod.core.init.ContainerInit;
import io.github.realyusufismail.realyusufismailcore.util.OutputSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class TableContainer extends AbstractContainerMenu {
    private final ContainerData data;

    /*
     * ALT + ENTER > CONSTRUCTOR SHIFT + F6 > CHANGE VARIABLE NAME
     */
    protected TableContainer(int id, Inventory inv, BlockEntity be, ContainerData data) {
        super(ContainerInit.TABLE_CONTAINER.get(), id);

        this.data = data;

        addSlot(new Slot((Container) be, 0, 16, 40));
        addSlot(new Slot((Container) be, 1, 43, 40));
        addSlot(new Slot((Container) be, 2, 70, 40));

        addSlot(new OutputSlot((Container) be, 3, 138, 40));

        // player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18,
                        (166 - (4 - row) * 18 - 10) + 13));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142 + 13));
        }
        addDataSlots(data);
    }

    public TableContainer(int id, Inventory playerInv, FriendlyByteBuf buffer) {
        this(id, playerInv, getBlockEntity(playerInv, buffer), new SimpleContainerData(2));
    }

    private static TableBlockEntity getBlockEntity(final Inventory playerInv,
            final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
        Objects.requireNonNull(data, "Packet Buffer cannot be null.");
        final BlockEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (te instanceof TableBlockEntity) {
            return (TableBlockEntity) te;
        }
        throw new IllegalStateException("Tile Entity Is Not Correct");
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (index < TableBlockEntity.slots && !this.moveItemStackTo(stack1,
                    TableBlockEntity.slots, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(stack1, 0, TableBlockEntity.slots, false)) {
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

