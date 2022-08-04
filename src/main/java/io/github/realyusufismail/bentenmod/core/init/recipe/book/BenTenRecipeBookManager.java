package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.github.realyusufismail.bentenmod.core.init.recipe.book.BenTenRecipeBookCategories.*;

public class BenTenRecipeBookManager {
    // Not using ConcurrentHashMap here because it's slower for lookups, so we only use it during
    // init
    private static final Map<BenTenRecipeBookCategories, List<BenTenRecipeBookCategories>> AGGREGATE_CATEGORIES =
            new HashMap<>();
    private static final Map<BenTenRecipeBookType, List<BenTenRecipeBookCategories>> TYPE_CATEGORIES =
            new HashMap<>();
    private static final Map<RecipeType<?>, Function<Recipe<?>, BenTenRecipeBookCategories>> RECIPE_CATEGORY_LOOKUPS =
            new HashMap<>();
    private static final Map<BenTenRecipeBookCategories, List<BenTenRecipeBookCategories>> AGGREGATE_CATEGORIES_VIEW =
            Collections.unmodifiableMap(AGGREGATE_CATEGORIES);

    /**
     * Finds the category the specified recipe should display in, or null if none.
     */
    @Nullable
    public static <T extends Recipe<?>> BenTenRecipeBookCategories findCategories(
            RecipeType<T> type, T recipe) {
        var lookup = RECIPE_CATEGORY_LOOKUPS.get(type);
        return lookup != null ? lookup.apply(recipe) : null;
    }

    @ApiStatus.Internal
    public static Map<BenTenRecipeBookCategories, List<BenTenRecipeBookCategories>> getAggregateCategories() {
        return AGGREGATE_CATEGORIES_VIEW;
    }

    @ApiStatus.Internal
    public static List<BenTenRecipeBookCategories> getCustomCategoriesOrEmpty(
            BenTenRecipeBookType recipeBookType) {
        return TYPE_CATEGORIES.getOrDefault(recipeBookType, List.of());
    }

    @ApiStatus.Internal
    public static void init() {
        // The ImmutableMap is the patched out value of AGGREGATE_CATEGORIES
        var aggregateCategories = new HashMap<>(ImmutableMap.of(OMNITRIX_SEARCH,
                ImmutableList.of(OMNITRIX_EQUIPMENT, OMNITRIX_TOOLS)));
        var typeCategories =
                new HashMap<BenTenRecipeBookType, ImmutableList<BenTenRecipeBookCategories>>();
        var recipeCategoryLookups =
                new HashMap<RecipeType<?>, Function<Recipe<?>, BenTenRecipeBookCategories>>();
        var event = new BenTenRegisterRecipeBookCategoriesEvent(aggregateCategories, typeCategories,
                recipeCategoryLookups);
        ModLoader.get()
            .postEventWithWrapInModOrder(event,
                    (mc, e) -> ModLoadingContext.get().setActiveContainer(mc),
                    (mc, e) -> ModLoadingContext.get().setActiveContainer(null));
        AGGREGATE_CATEGORIES.putAll(aggregateCategories);
        TYPE_CATEGORIES.putAll(typeCategories);
        RECIPE_CATEGORY_LOOKUPS.putAll(recipeCategoryLookups);
    }
}
