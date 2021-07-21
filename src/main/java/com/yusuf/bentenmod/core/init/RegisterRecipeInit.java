package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.modules.alientable.recipes.AlienRecipe;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class RegisterRecipeInit {
    public static final IRecipeType<TableRecipe> TABLE_RECIPE = new TableRecipe.Type();
    public static final IRecipeType<AlienRecipe> ALIEN_RECIPE = new AlienRecipe.Type();

    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type,
                                       IRecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer);
    }

    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, TABLE_RECIPE, new TableRecipe.Serializer());
        registerRecipe(event, ALIEN_RECIPE, new AlienRecipe.Serializer());
    }

    public static Map<ResourceLocation, IRecipe<?>> getRecipes(IRecipeType<?> type, RecipeManager manager) {
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper
                .getPrivateValue(RecipeManager.class, manager, "field_199522_d");
        return recipes.get(type);
    }

}

