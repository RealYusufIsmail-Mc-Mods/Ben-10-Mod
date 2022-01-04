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

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

import static io.github.realyusufismail.bentenmod.BenTenMod.MOD_ID;

public class TableRecipeBuilder {
    private final Ingredient input1;
    private final Ingredient input2;
    private final Ingredient input3;
    private final Item output;

    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();


    public TableRecipeBuilder(Ingredient input1, Ingredient input2, Ingredient input3,
            Item output) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
    }

    public static TableRecipeBuilder build(Ingredient input1, Ingredient input2, Ingredient input3,
            Item output) {
        return new TableRecipeBuilder(input1, input2, input3, output.asItem());
    }

    public TableRecipeBuilder unlockedBy(String creterionId,
            InventoryChangeTrigger.TriggerInstance criterion) {
        advancementBuilder.addCriterion(creterionId, criterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation rl) {
        ensureValid(rl);
        if (!advancementBuilder.getCriteria().isEmpty())
            advancementBuilder.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl))
                .rewards(AdvancementRewards.Builder.recipe(rl))
                .requirements(RequirementsStrategy.OR);
        assert output.getItemCategory() != null;
        consumer.accept(new Output(input1, input2, input3, output, advancementBuilder,
                new ResourceLocation(rl.getNamespace(), "recipes/"
                        + output.getItemCategory().getRecipeFolderName() + "/" + rl.getPath()),
                rl));
    }

    private void ensureValid(ResourceLocation rl) {
        if (advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("Can not obtain recipe: " + rl);
        }
    }

    private final class Output implements FinishedRecipe {

        private final Ingredient input1;
        private final Ingredient input2;
        private final Ingredient input3;
        private final Item output;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final ResourceLocation id;

        public Output(Ingredient input1, Ingredient input2, Ingredient input3, Item output,
                Advancement.Builder advancementBuilder, ResourceLocation advancementId,
                ResourceLocation id) {
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.output = output;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
            this.id = id;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input1", input1.toJson());
            json.add("input2", input2.toJson());
            json.add("input3", input3.toJson());
            json.addProperty("output", Registry.ITEM.getKey(output).toString());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS
                .getValue(new ResourceLocation(MOD_ID, "table_recipe")));
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancementBuilder.getCriteria().isEmpty() ? advancementBuilder.serializeToJson()
                    : null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
