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

        }


    }




