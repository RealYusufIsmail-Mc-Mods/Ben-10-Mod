/*
package com.yusuf.bentenmod.modules.bententable.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TableRecipe  implements IRecipe<IInventory> {
    private final ResourceLocation id;
    private final TableRecipe.Upgradeore item1;
    private final TableRecipe.Result item2;
    public TableRecipe(ResourceLocation id, TableRecipe.Upgradeore item1, TableRecipe.Result item2) {

        this.id = id;
        this.item1 = item1;
        this.item2 = item2;
    }
    public TableRecipe.Upgradeore getItem1() {
        return item1;
    }

    public TableRecipe.Result getItem2() {
        return item2;
    }
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return TableModule.SMITHING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return TableModule.SPAWNER_RECIPE_TYPE;
    }
}

 */
