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

import io.github.realyusufismail.bentenmod.common.LangKeys;
import io.github.realyusufismail.bentenmod.core.init.BlockEntityInit;
import io.github.realyusufismail.bentenmod.core.init.RegisterRecipeInit;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.recipes.TableRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TableBlockEntity extends RandomizableContainerBlockEntity
        implements BlockEntityTicker {
    public static final int slots = 4;
    private final int maxTick = 200;
    private NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);
    private int currentTick;
    public ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return currentTick;
                case 1:
                    return maxTick;
                default:
                    throw new ArrayIndexOutOfBoundsException();
            }
        }

        @Override
        public void set(int p_221477_1_, int p_221477_2_) {
            throw new IllegalStateException("Can not access");
        }

        @Override
        public int getCount() {
            return 2;
        }
    };


    public TableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityInit.TABLE_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    // This is the MOST IMPORTANT
    // This method will be called <strong> EVERY SINGLE TICK <strong/>

    @Override
    public void tick(Level p_155253_, BlockPos p_155254_, BlockState p_155255_,
            BlockEntity p_155256_) {
        assert level != null;
        if (!level.isClientSide) {
            ItemStack input1 = items.get(0);
            ItemStack input2 = items.get(1);
            ItemStack input3 = items.get(2);

            if (empty(input1) && empty(input2) && empty(input3)) {
                TableRecipe recipe = getRecipe();
                if (canProcessFromRecipe(recipe)) {
                    currentTick++;
                    level.setBlock(worldPosition, level.getBlockState(worldPosition)
                        .setValue(TableBlock.POWERED, Boolean.TRUE), 3);
                    if (currentTick >= maxTick) {
                        processing(recipe);
                        currentTick = 0;
                    }
                } else {
                    level.setBlock(worldPosition, level.getBlockState(worldPosition)
                        .setValue(TableBlock.POWERED, Boolean.FALSE), 3);
                    currentTick = 0;
                    setChanged();
                    return;
                }
            } else {
                level.setBlock(worldPosition, level.getBlockState(worldPosition)
                    .setValue(TableBlock.POWERED, Boolean.FALSE), 3);
                currentTick = 0;
                setChanged();
                return;
            }
        }
        setChanged();
    }

    private void processing(@Nullable TableRecipe recipe) {
        if (recipe != null && canProcessFromRecipe(recipe)) {
            ItemStack input1 = items.get(0);
            ItemStack input2 = items.get(1);
            ItemStack input3 = items.get(2);
            ItemStack result = recipe.getResultItem();
            ItemStack outputSlot = items.get(3);

            if (outputSlot.isEmpty()) {
                items.set(3, result.copy());
            } else if (outputSlot.getItem() == result.getItem()) {
                outputSlot.grow(1);
            }
            input1.shrink(1);
            input2.shrink(1);
            input3.shrink(1);
            setChanged();
        }
    }

    private boolean canProcessFromRecipe(@Nullable TableRecipe recipe) {
        if (empty(items.get(0)) && empty(items.get(1)) && empty(items.get(2)) && recipe != null) {
            ItemStack stack = recipe.getResultItem();
            if (stack.isEmpty()) {
                return false;
            } else {
                ItemStack resultSlot = items.get(3);
                if (resultSlot.isEmpty()) {
                    return true;
                } else if (!resultSlot.sameItem(stack)) {
                    return false;
                } else if (resultSlot.getCount() + stack.getCount() <= 64 && resultSlot.getCount()
                        + stack.getCount() <= resultSlot.getMaxStackSize()) {
                    return true;
                } else {
                    return resultSlot.getCount() + stack.getCount() <= stack.getMaxStackSize();
                }
            }
        } else
            return false;
    }


    private boolean empty(ItemStack stack) {
        return !stack.isEmpty();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public void encodeExtraData(FriendlyByteBuf buffer) {
        buffer.writeByte(this.items.size());
        buffer.writeByte(this.data.getCount());
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }

    @Override
    protected Component getDefaultName() {
        return LangKeys.TABLE_SCREEN;
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_213906_1_, Inventory p_213906_2_) {
        return new TableContainer(p_213906_1_, p_213906_2_, this, data);
    }

    @Override
    public int getContainerSize() {
        return slots;
    }

    @Nullable
    private TableRecipe getRecipe() {
        if (items.get(0).isEmpty() || items.get(1).isEmpty() || items.get(2).isEmpty()) {
            return null;
        } else {
            assert level != null;
            return level.getRecipeManager()
                .getRecipeFor(RegisterRecipeInit.TABLE_RECIPE, this, level)
                .orElse(null);
        }
    }
}
