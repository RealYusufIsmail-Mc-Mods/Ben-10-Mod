package com.yusuf.bentenmod.block.tablemachine;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.TileEntityInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TableTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    public TableTileEntity(TileEntityType<?> p_i48284_1_) {
        super(p_i48284_1_);
    }



    public TableTileEntity() {
        this(TileEntityInit.TABLE_TE.get());
    }
    // declares number of slots in your GUi
    public static final int slots = 4;

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    public static final ITextComponent TITLE = new TranslationTextComponent("container. " + Main.MOD_ID, ".table_block");

    @Override
    public void tick() {

    }


    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;

    }

    @Override
    protected ITextComponent getDefaultName() {
        return TITLE;
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return new TableContainer(p_213906_1_, p_213906_2_, this);
    }

    @Override
    public int getContainerSize() {
        return slots;
    }


}
