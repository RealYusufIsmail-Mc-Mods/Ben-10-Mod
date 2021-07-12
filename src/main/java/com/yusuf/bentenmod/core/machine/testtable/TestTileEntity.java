package com.yusuf.bentenmod.core.machine.testtable;

import com.yusuf.bentenmod.common.LangKeys;
import com.yusuf.bentenmod.core.machine.bententable.TableContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class TestTileEntity extends LockableLootTileEntity {
    public static final int slots = 9;
    private NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    protected TestTileEntity(TileEntityType<?> p_i48284_1_) {
        super(p_i48284_1_);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {

    }

    @Override
    protected ITextComponent getDefaultName() {
        return LangKeys.ALIEN_TABLE_SCREEN;
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return new TestContainer(p_213906_1_, p_213906_2_, this, data);
    }

    @Override
    public int getContainerSize() {
        return slots;
    }


}
