package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

@Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
            ShapelessRecipeBuilder.shapeless(ItemInit.INFINITUM.get(), 9)

                    .requires(BlockInit.INFINITUM_BLOCK.get())
                    .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
                    .save(consumer);

            ShapedRecipeBuilder.shaped(BlockInit.INFINITUM_BLOCK.get())
                    .define('#', ItemInit.INFINITUM.get())
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
                    .save(consumer);

    SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_SWORD),Ingredient.of(ItemInit.BLACK_DIAMOND.get()),ItemInit.BLACK_DIAMOND_SWORD.get()).
      unlocks("has_item", has(ItemInit.BLACK_DIAMOND_SWORD.get()));

    ShapedRecipeBuilder.shaped(ItemInit.BLACK_DIAMOND_SWORD.get())
            .define('#', ItemInit.INFINITUM.get())
            .define('K', ItemInit.INFINITUM.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
            .save(consumer);

        }


    }




