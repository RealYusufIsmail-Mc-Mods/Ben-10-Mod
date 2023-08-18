package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;
import java.util.function.Supplier;

public class RecipeCategoriesInit {
    private static final Supplier<RecipeBookCategories> OMNITRIX_CRAFTING_SEARCH =
            () -> RecipeBookCategories.create("omnitrix_crafting_search",
                    new ItemStack(Items.COMPASS));

    private static final Supplier<RecipeBookCategories> OMNTRIX_CRAFTER_TOOLS =
            () -> RecipeBookCategories.create("omnitrix_crafter_tools",
                    new ItemStack(ItemInit.IMPERIUM_PICKAXE_UPGRADED.get()));

    private static final Supplier<RecipeBookCategories> OMNTRIX_CRAFTER_MISC =
            () -> RecipeBookCategories.create("omnitrix_crafter_misc",
                    new ItemStack(Items.WHITE_WOOL));

    public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(RecipeBookTypeInit.OMNITRIX_CRAFTER,
                List.of(OMNITRIX_CRAFTING_SEARCH.get(), OMNTRIX_CRAFTER_TOOLS.get(),
                        OMNTRIX_CRAFTER_MISC.get()));

        event.registerAggregateCategory(OMNITRIX_CRAFTING_SEARCH.get(),
                List.of(OMNTRIX_CRAFTER_TOOLS.get(), OMNTRIX_CRAFTER_MISC.get()));

        event.registerAggregateCategory(OMNTRIX_CRAFTER_TOOLS.get(),
                List.of(OMNTRIX_CRAFTER_TOOLS.get()));

        event.registerAggregateCategory(OMNTRIX_CRAFTER_MISC.get(),
                List.of(OMNTRIX_CRAFTER_MISC.get()));

        event.registerRecipeCategoryFinder(RecipeTypeInit.OMNITRIX_CRAFTER_TYPE.get(), (it) -> {
            if (it instanceof OmntrixCrafterShapedRecipe) {
                return switch (((OmntrixCrafterShapedRecipe) it).getCategory()) {
                    case TOOLS -> OMNTRIX_CRAFTER_TOOLS.get();
                    case MISC -> OMNTRIX_CRAFTER_MISC.get();
                };
            } else {
                return OMNTRIX_CRAFTER_MISC.get();
            }
        });
    }
}
