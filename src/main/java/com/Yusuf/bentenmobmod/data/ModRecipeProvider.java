package com.yusuf.bentenmobmod.data;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(MessagePassingQueue.Consumer<IFinishedRecipe> consumer) {

    }
}
