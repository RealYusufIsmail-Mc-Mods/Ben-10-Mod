package com.yusuf.bentenmod.modules.alientable.recipes;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.item.crafting.IRecipeType;

public class AlienRecipeType implements IRecipeType<AlienRecipe> {
    @Override
    public String toString() {
        return BenTenMod.MOD_ID + ":" + "alien_recipe";
    }
}
