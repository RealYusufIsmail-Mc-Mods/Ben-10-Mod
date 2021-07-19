package com.yusuf.bentenmod.modules.bententable.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class TableRecipe implements IRecipe<IInventory> {
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
    public TableRecipe(Ingredient input1, Ingredient input2, Ingredient input3, ItemStack output, ResourceLocation recipeId) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
        this.recipeId = recipeId;
    }

    //int value is the index of Container Slot in your Container class

    @Override
    public boolean matches(IInventory inventory, World p_77569_2_) {
        return input1.test(inventory.getItem(0))
                && input2.test(inventory.getItem(1))
                && input3.test(inventory.getItem(2));
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
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
    public IRecipeType<?> getType() {
        return RegisterRecipeInit.ALIEN_RECIPE;
    }

    public static final class Type implements IRecipeType<TableRecipe> {
        @Override
        public String toString() {
            return BenTenMod.MOD_ID + ":table_recipe";
        }
    }

    public static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TableRecipe> {
        public Serializer() {
            setRegistryName(BenTenMod.MOD_ID, "table_recipe");
        }

        @SuppressWarnings("deprecation")
        @Override
        public TableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input1 = Ingredient.fromJson(getElement(json, "input1"));
            final Ingredient input2 = Ingredient.fromJson(getElement(json, "input2"));
            final Ingredient input3 = Ingredient.fromJson(getElement(json, "input3"));

            final ItemStack output;
            if (json.get("output").isJsonObject())
                output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
            else {
                output = new ItemStack(Registry.ITEM.getOptional(new ResourceLocation(JSONUtils.getAsString(json, "output"))).orElseThrow(() -> new IllegalStateException("Oops")));
            }
            return new TableRecipe(input1, input2, input3, output, recipeId);
        }

        @Override
        public TableRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient input1 = Ingredient.fromNetwork(buffer);
            final Ingredient input2 = Ingredient.fromNetwork(buffer);
            final Ingredient input3 = Ingredient.fromNetwork(buffer);

            final ItemStack output = buffer.readItem();

            return new TableRecipe(input1, input2, input3, output, recipeId);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, TableRecipe recipe) {
            recipe.input1.toNetwork(buffer);
            recipe.input2.toNetwork(buffer);
            recipe.input3.toNetwork(buffer);

            buffer.writeItemStack(recipe.output, false);
        }

        private JsonElement getElement(JsonObject json, String memberName) {
            return JSONUtils.isArrayNode(json, memberName)
                    ? JSONUtils.getAsJsonArray(json, memberName)
                    : JSONUtils.getAsJsonObject(json, memberName);
        }
    }
}