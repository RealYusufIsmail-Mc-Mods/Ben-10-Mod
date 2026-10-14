/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.recipebook;

import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.init.RecipeTypeInit;
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

    private static final Supplier<RecipeBookCategories> OMNITRIX_CRAFTER_OMNITRIX =
            () -> RecipeBookCategories.create("omnitrix_crafter_omnitrix",
                    new ItemStack(ItemInit.OMNITRIX_CORE.get()));

    private static final Supplier<RecipeBookCategories> OMNITRIX_CRAFTER_ARMOUR =
            () -> RecipeBookCategories.create("omnitrix_crafter_armour",
                    new ItemStack(ItemInit.HEATBLAST_HELMET.get()));

    private static final Supplier<RecipeBookCategories> OMNTRIX_CRAFTER_TOOLS =
            () -> RecipeBookCategories.create("omnitrix_crafter_tools",
                    new ItemStack(ItemInit.IMPERIUM_PICKAXE_UPGRADED.get()));

    private static final Supplier<RecipeBookCategories> OMNTRIX_CRAFTER_MISC =
            () -> RecipeBookCategories.create("omnitrix_crafter_misc",
                    new ItemStack(Items.WHITE_WOOL));

    public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(RecipeBookTypeInit.OMNITRIX_CRAFTER,
                List.of(OMNITRIX_CRAFTING_SEARCH.get(), OMNITRIX_CRAFTER_OMNITRIX.get(),
                        OMNITRIX_CRAFTER_ARMOUR.get(), OMNTRIX_CRAFTER_TOOLS.get(),
                        OMNTRIX_CRAFTER_MISC.get()));

        event.registerAggregateCategory(OMNITRIX_CRAFTING_SEARCH.get(),
                List.of(OMNITRIX_CRAFTER_OMNITRIX.get(), OMNITRIX_CRAFTER_ARMOUR.get(),
                        OMNTRIX_CRAFTER_TOOLS.get(), OMNTRIX_CRAFTER_MISC.get()));

        event.registerAggregateCategory(OMNITRIX_CRAFTER_OMNITRIX.get(),
                List.of(OMNITRIX_CRAFTER_OMNITRIX.get()));

        event.registerAggregateCategory(OMNITRIX_CRAFTER_ARMOUR.get(),
                List.of(OMNITRIX_CRAFTER_ARMOUR.get()));

        event.registerAggregateCategory(OMNTRIX_CRAFTER_TOOLS.get(),
                List.of(OMNTRIX_CRAFTER_TOOLS.get()));

        event.registerAggregateCategory(OMNTRIX_CRAFTER_MISC.get(),
                List.of(OMNTRIX_CRAFTER_MISC.get()));

        event.registerRecipeCategoryFinder(RecipeTypeInit.OMNITRIX_CRAFTER_TYPE.get(), (it) -> {
            if (it instanceof OmntrixCrafterShapedRecipe) {
                return switch (((OmntrixCrafterShapedRecipe) it).getCategory()) {
                    case OMNITRIX -> OMNITRIX_CRAFTER_OMNITRIX.get();
                    case ARMOUR -> OMNITRIX_CRAFTER_ARMOUR.get();
                    case TOOLS -> OMNTRIX_CRAFTER_TOOLS.get();
                    case MISC -> OMNTRIX_CRAFTER_MISC.get();
                };
            } else {
                return OMNTRIX_CRAFTER_MISC.get();
            }
        });
    }
}
