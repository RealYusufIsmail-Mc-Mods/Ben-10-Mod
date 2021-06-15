package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.core.machine.bententable.TableBlock;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class RecipeTypeInit{
    public static final IRecipeType<TableRecipe> TABLE_RECIPE = new TableRecipe.Type();

    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, TABLE_RECIPE, new TableRecipe.Serializer());
    }

    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type,
                                       IRecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer);
    }

   /* public static Map<ResourceLocation, IRecipe<?>> getRecipes(IRecipeType<?> type, RecipeManager manager) {
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper
                .getPrivateValue(RecipeManager.class, manager, "recipes");
        return recipes.get(type);
    }*/
}
