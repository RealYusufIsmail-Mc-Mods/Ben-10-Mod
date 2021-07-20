package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.modules.alientable.recipes.AlienRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class RecipeSerializerInit {
    IRecipeSerializer<AlienRecipe> ALIEN_RECIPE = register("alien_recipe", new AlienRecipe.Serializer());
    
    static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register(String p_222156_0_, S p_222156_1_) {
        return Registry.register(Registry.RECIPE_SERIALIZER, p_222156_0_, p_222156_1_);
    }
}
