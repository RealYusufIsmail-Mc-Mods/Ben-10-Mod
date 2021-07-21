package com.yusuf.bentenmod.core.machine.bententable;

import com.yusuf.bentenmod.common.LangKeys;
import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import com.yusuf.bentenmod.core.init.TileEntityInit;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TableTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    public static final int slots = 4;
    private final int maxTick = 200;
    private NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);
    private int currentTick;
    public IIntArray data = new IIntArray() {
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

    public TableTileEntity(TileEntityType<?> p_i48284_1_) {
        super(p_i48284_1_);
    }

    public TableTileEntity() {
        this(TileEntityInit.TABLE_TILE.get());
    }


    //This is the MOST IMPORTANT
    //This method will be called  <strong> EVERY SINGLE TICK <strong/>

    @Override
    public void tick() {
        assert level != null;
        if (!level.isClientSide) {
            ItemStack input1 = items.get(0);
            ItemStack input2 = items.get(1);
            ItemStack input3 = items.get(2);

            if (!empty(input1) && !empty(input2) && !empty(input3)) {
                TableRecipe recipe = getRecipe();
                if (canProcessFromRecipe(recipe)) {
                    currentTick++;
                    level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(TableBlock.POWERED, Boolean.TRUE), 3);
                    if (currentTick >= maxTick) {
                        processing(recipe);
                        currentTick = 0;
                    }
                } else {
                    level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(TableBlock.POWERED, Boolean.FALSE), 3);
                    currentTick = 0;
                    setChanged();
                    return;
                }
            } else {
                level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(TableBlock.POWERED, Boolean.FALSE), 3);
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
        if (!empty(items.get(0)) && !empty(items.get(1)) && !empty(items.get(2)) && recipe != null) {
            ItemStack stack = recipe.getResultItem();
            if (stack.isEmpty()) {
                return false;
            } else {
                ItemStack resultSlot = items.get(3);
                if (resultSlot.isEmpty()) {
                    return true;
                } else if (!resultSlot.sameItem(stack)) {
                    return false;
                } else if (resultSlot.getCount() + stack.getCount() <= 64 && resultSlot.getCount() + stack.getCount() <= resultSlot.getMaxStackSize()) {
                    return true;
                } else {
                    return resultSlot.getCount() + stack.getCount() <= stack.getMaxStackSize();
                }
            }
        } else return false;
    }


    private boolean empty(ItemStack stack) {
        return stack.isEmpty();
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
        return LangKeys.TABLE_SCREEN;
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
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
            return level.getRecipeManager().getRecipeFor(RegisterRecipeInit.TABLE_RECIPE, this, level).orElse(null);
        }
    }
}
