package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModSmitingRecipeBuild extends RecipeProvider implements IConditionBuilder {
    public ModSmitingRecipeBuild(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        // THIS is where you call `smithing` or other recipe builders..
        // i.e.
        SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_SWORD), Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_SWORD.get())
        .unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()))
        .save(consumer,modId("black_diamond_sword"));


        SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_HELMET), Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_HELMET.get())
                .unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer,modId("black_diamond_helmet"));

        SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_CHESTPLATE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_CHESTPLATE.get())
                .unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer,modId("black_diamond_chestplate"));

        SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_LEGGINGS), Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_LEGGINGS.get())
                .unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer,modId("black_diamond_leggings"));

        SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_BOOTS), Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_BOOTS.get())
                .unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer,modId("black_diamond_boots"));


        SmithingRecipeBuilder.smithing(Ingredient.of(ItemInit.BLACK_DIAMOND_SWORD.get()), Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_SWORD.get())
                .unlocks("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer,modId("infinitum_sword"));

    }
    private static ResourceLocation modId(String path){
        return new ResourceLocation(Main.MOD_ID, path);
    }

}
