/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.data.recipe;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.init.TagsInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModSmithingRecipeBuild extends RecipeProvider implements IConditionBuilder {
    public ModSmithingRecipeBuild(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(BenTenMod.MOD_ID, path);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        // THIS is where you call `smithing` or other recipe builders..
        // i.e.
        UpgradeRecipeBuilder
            .smithing(Ingredient.of(Items.DIAMOND_SWORD),
                    Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_SWORD.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_sword"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(Items.DIAMOND_AXE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
                    ItemInit.BLACK_DIAMOND_AXE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_axe"));

        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_PICKAXE),
                Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_PICKAXE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_pickaxe"));

        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_SHOVEL),
                Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_SHOVEL.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_shovel"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(Items.DIAMOND_HOE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
                    ItemInit.BLACK_DIAMOND_HOE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_hoe"));


        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_HELMET),
                Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_HELMET.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_helmet"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(Items.DIAMOND_CHESTPLATE),
                    Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
                    ItemInit.BLACK_DIAMOND_CHESTPLATE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_chestplate"));

        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_LEGGINGS),
                Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_LEGGINGS.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_leggings"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(Items.DIAMOND_BOOTS),
                    Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_BOOTS.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_boots"));


        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_SWORD.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_SWORD.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_sword"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_AXE.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_AXE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_axe"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_PICKAXE.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_PICKAXE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_pickaxe"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_SHOVEL.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_SHOVEL.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_shovel"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_HOE.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_HOE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_hoe"));


        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_HELMET.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_HELMET.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_helmet"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_CHESTPLATE.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_CHESTPLATE.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_chestplate"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_LEGGINGS.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_LEGGINGS.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_leggings"));

        UpgradeRecipeBuilder
            .smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_BOOTS.get()),
                    Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_BOOTS.get())
            .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_boots"));

    }

}
