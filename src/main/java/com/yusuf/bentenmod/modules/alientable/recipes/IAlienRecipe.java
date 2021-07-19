package com.yusuf.bentenmod.modules.alientable.recipes;

import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;


public interface IAlienRecipe extends ICraftingRecipe {
    @Override
    default IRecipeType<?> getType() {
        return RegisterRecipeInit.ALIEN_RECIPE;
    }
}
