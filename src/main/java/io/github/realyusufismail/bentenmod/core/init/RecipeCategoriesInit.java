package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.core.blocks.bententable.IOmnitrixCraftingRecipe;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;
import java.util.function.Supplier;

public class RecipeCategoriesInit {
    private static final RecipeType<IOmnitrixCraftingRecipe> OMNITRIX_CRAFTING_RECIPE_TYPE =
            new OmntrixCrafterShapedRecipe.Type();

    private static final Supplier<RecipeBookCategories> OMNTRIX_CRAFTER = () -> RecipeBookCategories
        .create("omnitrix_crafter", new ItemStack(ItemInit.OMNITRIX.get()));

    public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(RecipeBookTypeInit.OMNITRIX_CRAFTER,
                List.of(OMNTRIX_CRAFTER.get()));
        event.registerRecipeCategoryFinder(OMNITRIX_CRAFTING_RECIPE_TYPE,
                RecipeCategoriesInit::getForgingCategory);
    }

    private static RecipeBookCategories getForgingCategory(Recipe<?> recipe) {
        final var recipeType = recipe.getType();
        if (recipeType == OMNITRIX_CRAFTING_RECIPE_TYPE) {
            return OMNTRIX_CRAFTER.get();
        } else {
            return RecipeBookCategories.UNKNOWN;
        }
    }
}
