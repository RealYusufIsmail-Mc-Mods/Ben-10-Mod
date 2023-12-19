/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.yusuf.bentenmod.data.recipe;

import static com.yusuf.bentenmod.BenTenMod.MOD_ID;

import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

public class TableRecipeBuilder {
    private final Ingredient input1;
    private final Ingredient input2;
    private final Ingredient input3;
    private final Item output;

    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public TableRecipeBuilder(Ingredient input1, Ingredient input2, Ingredient input3, Item output) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
    }

    public static TableRecipeBuilder build(
            Ingredient input1, Ingredient input2, Ingredient input3, IItemProvider output) {
        return new TableRecipeBuilder(input1, input2, input3, output.asItem());
    }

    public TableRecipeBuilder unlockedBy(String creterionId, ICriterionInstance criterion) {
        advancementBuilder.addCriterion(creterionId, criterion);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation rl) {
        ensureValid(rl);
        if (!advancementBuilder.getCriteria().isEmpty())
            advancementBuilder
                    .parent(new ResourceLocation("recipes/root"))
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl))
                    .rewards(AdvancementRewards.Builder.recipe(rl))
                    .requirements(IRequirementsStrategy.OR);
        assert output.getItemCategory() != null;
        consumer.accept(new Output(
                input1,
                input2,
                input3,
                output,
                advancementBuilder,
                new ResourceLocation(
                        rl.getNamespace(),
                        "recipes/" + output.getItemCategory().getRecipeFolderName() + "/" + rl.getPath()),
                rl));
    }

    private void ensureValid(ResourceLocation rl) {
        if (advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("Can not obtain recipe: " + rl);
        }
    }

    private final class Output implements IFinishedRecipe {

        private final Ingredient input1;
        private final Ingredient input2;
        private final Ingredient input3;
        private final Item output;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final ResourceLocation id;

        public Output(
                Ingredient input1,
                Ingredient input2,
                Ingredient input3,
                Item output,
                Advancement.Builder advancementBuilder,
                ResourceLocation advancementId,
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
        public IRecipeSerializer<?> getType() {
            return Objects.requireNonNull(
                    ForgeRegistries.RECIPE_SERIALIZERS.getValue(new ResourceLocation(MOD_ID, "table_recipe")));
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancementBuilder.getCriteria().isEmpty() ? advancementBuilder.serializeToJson() : null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}
