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

import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterContainer;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterCraftingBookCategory;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IShapedRecipe;

/**
 * The shaped Omnitrix Crafter recipe plus Forge's {@link IShapedRecipe}, which the recipe book
 * needs to place ingredients in the recipe's shape.
 */
public class ForgeOmnitrixShapedRecipe extends OmntrixCrafterShapedRecipe
        implements IShapedRecipe<OmnitrixCrafterContainer> {

    public ForgeOmnitrixShapedRecipe(ResourceLocation id, String group, int width, int height,
            NonNullList<Ingredient> ingredients, ItemStack result, boolean showNotification,
            OmnitrixCrafterCraftingBookCategory category) {
        super(id, group, width, height, ingredients, result, showNotification, category);
    }
}
