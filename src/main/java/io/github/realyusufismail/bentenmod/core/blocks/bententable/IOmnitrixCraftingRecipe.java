package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import io.github.realyusufismail.bentenmod.core.init.RecipeTypeInit;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface IOmnitrixCraftingRecipe extends Recipe<OmnitrixCrafterContainer> {
    default RecipeType<?> getType() {
        return RecipeTypeInit.OMNITRIX_CRAFTER_TYPE.get();
    }

}
