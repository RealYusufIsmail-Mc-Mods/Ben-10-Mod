package com.yusuf.bentenmod.modules.bententable.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class RegisterRecipe {
    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type,
                                       IRecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer);
    }

    public static final IRecipeType<TableRecipe> TABLE_RECIPE = new TableRecipeType();


    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, TABLE_RECIPE, new Serializer());
    }
}
