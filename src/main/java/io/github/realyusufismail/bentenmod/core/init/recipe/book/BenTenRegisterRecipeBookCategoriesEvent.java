package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BenTenRegisterRecipeBookCategoriesEvent extends Event implements IModBusEvent {
    private final Map<BenTenRecipeBookCategories, ImmutableList<BenTenRecipeBookCategories>> aggregateCategories;
    private final Map<BenTenRecipeBookType, ImmutableList<BenTenRecipeBookCategories>> typeCategories;
    private final Map<RecipeType<?>, Function<Recipe<?>, BenTenRecipeBookCategories>> recipeCategoryLookups;

    @ApiStatus.Internal
    public BenTenRegisterRecipeBookCategoriesEvent(
            Map<BenTenRecipeBookCategories, ImmutableList<BenTenRecipeBookCategories>> aggregateCategories,
            Map<BenTenRecipeBookType, ImmutableList<BenTenRecipeBookCategories>> typeCategories,
            Map<RecipeType<?>, Function<Recipe<?>, BenTenRecipeBookCategories>> recipeCategoryLookups) {
        this.aggregateCategories = aggregateCategories;
        this.typeCategories = typeCategories;
        this.recipeCategoryLookups = recipeCategoryLookups;
    }

    /**
     * Registers the list of categories that compose an aggregate category.
     */
    public void registerAggregateCategory(BenTenRecipeBookCategories category,
            List<BenTenRecipeBookCategories> others) {
        aggregateCategories.put(category, ImmutableList.copyOf(others));
    }

    /**
     * Registers the list of categories that compose a recipe book.
     */
    public void registerBookCategories(BenTenRecipeBookType type,
            List<BenTenRecipeBookCategories> categories) {
        typeCategories.put(type, ImmutableList.copyOf(categories));
    }

    /**
     * Registers a category lookup for a certain recipe type.
     */
    public void registerRecipeCategoryFinder(RecipeType<?> type,
            Function<Recipe<?>, BenTenRecipeBookCategories> lookup) {
        recipeCategoryLookups.put(type, lookup);
    }
}

