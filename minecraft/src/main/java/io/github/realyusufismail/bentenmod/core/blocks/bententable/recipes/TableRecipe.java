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

package io.github.realyusufismail.bentenmod.core.blocks.bententable.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.RegisterRecipeInit;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class TableRecipe implements Recipe<Container> {
    /**
     * Set the input ingredients
     */
    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    /**
     * Set the output ItemStack
     */
    public final ItemStack output;
    /**
     * Set recipe id. THIS IS IMPORTANT
     */
    private final ResourceLocation recipeId;

    /**
     * put all of final variables to constructor
     */
    public TableRecipe(Ingredient input1, Ingredient input2, Ingredient input3, ItemStack output,
            ResourceLocation recipeId) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
        this.recipeId = recipeId;
    }

    // int value is the index of Container Slot in your Container class
    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        return input1.test(p_44002_.getItem(0)) && input2.test(p_44002_.getItem(1))
                && input3.test(p_44002_.getItem(2));
    }

    @Override
    public ItemStack assemble(Container p_44001_) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(input1);
        ingredients.add(input2);
        ingredients.add(input3);
        return ingredients;
    }

    @Override
    public RecipeType<?> getType() {
        return RegisterRecipeInit.TABLE_RECIPE;
    }

    public static final class Type implements RecipeType<TableRecipe> {
        @Override
        public String toString() {
            return BenTenMod.MOD_ID + ":table_recipe";
        }
    }

    public static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<TableRecipe> {
        public Serializer() {
            setRegistryName(BenTenMod.MOD_ID, "table_recipe");
        }

        @SuppressWarnings("deprecation")
        @Override
        public TableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input1 = Ingredient.fromJson(json.get("input1"));
            final Ingredient input2 = Ingredient.fromJson(json.get("input2"));
            final Ingredient input3 = Ingredient.fromJson(json.get("input3"));

            final ItemStack output;
            if (json.get("output").isJsonObject())
                output = new ItemStack(
                        ShapedRecipe.itemFromJson(json.get("output").getAsJsonObject()));
            else
                output = new ItemStack((Registry.ITEM
                    .getOptional(new ResourceLocation(json.get("output").getAsString()))
                    .orElseThrow(() -> new IllegalStateException("Oops"))));
            return new TableRecipe(input1, input2, input3, output, recipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TableRecipe recipe) {
            recipe.input1.toNetwork(buffer);
            recipe.input2.toNetwork(buffer);
            recipe.input3.toNetwork(buffer);

            buffer.writeItemStack(recipe.output, false);
        }

        @Override
        public TableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final Ingredient input1 = Ingredient.fromNetwork(buffer);
            final Ingredient input2 = Ingredient.fromNetwork(buffer);
            final Ingredient input3 = Ingredient.fromNetwork(buffer);

            final ItemStack output = buffer.readItem();

            return new TableRecipe(input1, input2, input3, output, recipeId);
        }

        private JsonElement getElement(JsonObject json, String memberName) {
            return GsonHelper.isArrayNode(json, memberName)
                    ? GsonHelper.getAsJsonArray(json, memberName)
                    : GsonHelper.getAsJsonObject(json, memberName);

            // JSONUtils is GsonHelper in 1.17
        }
    }
}
