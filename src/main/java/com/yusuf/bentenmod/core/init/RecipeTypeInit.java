package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.modules.alientable.recipes.AlienRecipe;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class RecipeTypeInit {
    public static final IRecipeType<TableRecipe> TABLE_BLOCK = new TableRecipe.Type();
    //public static final IRecipeType<TableRecipe> ALIEN_TABLE = new AlienRecipe.Type();

}
