package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.data.*;

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

    ShapelessRecipeBuilder.shapeless(ItemInit.BLACK_DIAMOND.get(), 9)

            .requires(BlockInit.BLACK_DIAMOND_BLOCK.get())
            .unlockedBy("has_item", has(ItemInit.BLACK_DIAMOND.get()))
            .save(consumer);

    ShapedRecipeBuilder.shaped(BlockInit.BLACK_DIAMOND_BLOCK.get())
            .define('#', ItemInit.BLACK_DIAMOND.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ItemInit.BLACK_DIAMOND.get()))
            .save(consumer);
    ShapelessRecipeBuilder.shapeless(ItemInit.FIRE.get(), 9)

            .requires(BlockInit.FIRE_BLOCK.get())
            .unlockedBy("has_item", has(ItemInit.FIRE.get()))
            .save(consumer);

    ShapedRecipeBuilder.shaped(BlockInit.FIRE_BLOCK.get())
            .define('#', ItemInit.FIRE.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
            .save(consumer);

    ShapelessRecipeBuilder.shapeless(ItemInit.OMNITRIX.get(), 9)

            .requires(BlockInit.OMNITRIX_BLOCK.get())
            .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
            .save(consumer);

    ShapedRecipeBuilder.shaped(BlockInit.OMNITRIX_BLOCK.get())
            .define('#', ItemInit.OMNITRIX.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
            .save(consumer);

    ShapelessRecipeBuilder.shapeless(ItemInit.SPEED.get(), 9)

            .requires(BlockInit.SPEED_BLOCK.get())
            .unlockedBy("has_item", has(ItemInit.SPEED.get()))
            .save(consumer);

    ShapedRecipeBuilder.shaped(BlockInit.SPEED_BLOCK.get())
            .define('#', ItemInit.SPEED.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_item", has(ItemInit.SPEED.get()))
            .save(consumer);

    SmithingRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_SWORD), Ingredient.of(ItemInit.BLACK_DIAMOND.get()), ItemInit.BLACK_DIAMOND_SWORD.get()).
            unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()));

}
}




