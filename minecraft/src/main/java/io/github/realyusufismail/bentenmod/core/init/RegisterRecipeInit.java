/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.Map;

public class RegisterRecipeInit {
    public static final RecipeType<TableRecipe> TABLE_RECIPE = new TableRecipe.Type();

    private static void registerRecipe(RegistryEvent.Register<RecipeSerializer<?>> event,
            RecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE,
                new ResourceLocation(RegisterRecipeInit.TABLE_RECIPE.toString()),
                (RecipeType<?>) RegisterRecipeInit.TABLE_RECIPE);
        event.getRegistry().register(serializer);
    }

    public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
        registerRecipe(event, new TableRecipe.Serializer());
    }

    public static Map<ResourceLocation, Recipe<?>> getRecipes(RecipeType<?> type,
            RecipeManager manager) {
        final Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes =
                ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager,
                        "field_199522_d");
        return recipes.get(type);
    }

}

