package com.yusuf.bentenmod.modules.bententable.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yusuf.bentenmod.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TableRecipe> {
    public Serializer() {
        setRegistryName(Main.MOD_ID, "table_recipe");
    }
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