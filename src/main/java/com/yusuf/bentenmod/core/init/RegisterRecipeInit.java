package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.modules.alientable.recipes.AlienRecipe;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

import static net.minecraft.item.crafting.IRecipeType.register;

public class RegisterRecipeInit {
    public static final IRecipeType<TableRecipe> TABLE_RECIPE = new TableRecipe.Type();
    public static final IRecipeType<AlienRecipe> ALIEN_RECIPE =  register("alien");
    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type,
                                       IRecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer);
    }

    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, TABLE_RECIPE, new TableRecipe.Serializer());
        registerRecipe(event, ALIEN_RECIPE, new TableRecipe.Serializer());
    }
}

