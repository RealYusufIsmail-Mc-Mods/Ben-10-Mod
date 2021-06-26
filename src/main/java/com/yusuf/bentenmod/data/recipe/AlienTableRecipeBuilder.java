package com.yusuf.bentenmod.data.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

import static com.yusuf.bentenmod.Main.MOD_ID;

public class AlienTableRecipeBuilder {
    private final Ingredient input1;
    private final Ingredient input2;
    private final Ingredient input3;
    private final Ingredient input4;
    private final Ingredient input5;
    private final Ingredient input6;
    private final Ingredient input7;
    private final Ingredient input8;
    private final Ingredient input9;
    private final Item output;

    private final Advancement.Builder advancementBuilder =
            Advancement.Builder.advancement();

    public AlienTableRecipeBuilder(Ingredient input1, Ingredient input2, Ingredient input3,
                                   Ingredient input4, Ingredient input5, Ingredient input6,
                                   Ingredient input7, Ingredient input8, Ingredient input9,
                                   Item output) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.input5 = input5;
        this.input6 = input6;
        this.input7 = input7;
        this.input8 = input8;
        this.input9 = input9;
        this.output = output;
    }

    public AlienTableRecipeBuilder unlockedBy(String creterionId, ICriterionInstance criterion) {
        advancementBuilder.addCriterion(creterionId, criterion);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation rl) {
        ensureValid(rl);
        if (!advancementBuilder.getCriteria().isEmpty())
            advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(AdvancementRewards.Builder.recipe(rl)).requirements(IRequirementsStrategy.OR);
        assert output.getItemCategory() != null;
        consumer.accept(
                new AlienTableRecipeBuilder.Output(input1, input2, input3, input4, input5, input6, input7,
                        input8, input9, output, advancementBuilder,
                        new ResourceLocation(rl.getNamespace(), "recipes/" + output.getItemCategory().getRecipeFolderName() + "/" + rl.getPath())
                        , rl
                ));
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
        private final Ingredient input4;
        private final Ingredient input5;
        private final Ingredient input6;
        private final Ingredient input7;
        private final Ingredient input8;
        private final Ingredient input9;
        private final Item output;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final ResourceLocation id;

        public Output(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4,
                      Ingredient input5, Ingredient input6, Ingredient input7, Ingredient input8,
                      Ingredient input9, Item output, Advancement.Builder advancementBuilder,
                      ResourceLocation advancementId, ResourceLocation id) {
            this.input1 = input1;
            this.input2 = input2;
            this.input3 = input3;
            this.input4 = input4;
            this.input5 = input5;
            this.input6 = input6;
            this.input7 = input7;
            this.input8 = input8;
            this.input9 = input9;
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
            json.add("input4", input4.toJson());
            json.add("input5", input5.toJson());
            json.add("input6", input6.toJson());
            json.add("input7", input7.toJson());
            json.add("input8", input8.toJson());
            json.add("input9", input9.toJson());
            json.addProperty("output", Registry.ITEM.getKey(output).toString() );
        }
        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getValue(new ResourceLocation(MOD_ID, "alien_table_recipe")));
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
