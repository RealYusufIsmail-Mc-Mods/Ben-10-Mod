package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.data.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        protected void buildShapelessRecipes(Consumer < IFinishedRecipe > consumer)
        {
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
        private static ResourceLocation modId(String path){
            return new ResourceLocation(Main.MOD_ID, path);
        }
    }
}



