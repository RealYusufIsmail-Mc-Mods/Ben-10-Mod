
package com.yusuf.bentenmod.modules.bententable.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TableRecipe  implements IRecipe<IInventory> {
    /** Set the input ingredients */
    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    /** Set the output ItemStack */
    public final ItemStack output;
    /** Set recipe id. THIS IS IMPORTANT */
    private final ResourceLocation recipeId;
    /** put all of final variables to constructor */
    public TableRecipe(Ingredient input1, Ingredient input2, Ingredient input3, ItemStack output, ResourceLocation recipeId) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
        this.recipeId = recipeId;
    }


    //int value is the index of Container Slot in your Container class

    @Override
    public boolean matches(IInventory inventory, World p_77569_2_) {
        return input1.test(inventory.getItem(0))
                && input2.test(inventory.getItem(1))
                && input3.test(inventory.getItem(2));
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(input1);
        ingredients.add(input2);
        ingredients.add(input3);
        return ingredients;
    }

    @Override
     public IRecipeType<?> getType() {
        return RegisterRecipe.TABLE_RECIPE;
    }
}
