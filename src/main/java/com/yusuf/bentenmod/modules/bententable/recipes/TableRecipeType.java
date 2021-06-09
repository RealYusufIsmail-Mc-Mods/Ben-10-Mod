package com.yusuf.bentenmod.modules.bententable.recipes;

import com.yusuf.bentenmod.Main;
import net.minecraft.item.crafting.IRecipeType;

public class TableRecipeType implements IRecipeType<TableRecipe> {
    @Override
    public String toString() {
        return Main.MOD_ID + ":table_recipe" ;
    }
}
