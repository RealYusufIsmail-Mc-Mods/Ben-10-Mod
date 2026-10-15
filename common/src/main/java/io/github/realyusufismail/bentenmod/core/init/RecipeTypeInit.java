/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.core.init;


import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.IOmnitrixCraftingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;

/**
 * @see RecipeType
 */
public class RecipeTypeInit {
    public static final RegistrationProvider<RecipeType<?>> TYPES =
            RegistrationProvider.get(Registries.RECIPE_TYPE, BenTenMod.MOD_ID);

    public static final RegistryObject<RecipeType<IOmnitrixCraftingRecipe>> OMNITRIX_CRAFTER_TYPE =
            register("omnitrix_crafter");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(
            final String pIdentifier) {
        return TYPES.register(pIdentifier, () -> new RecipeType<T>() {
            private final String id = BenTenMod.getModIdAndName(pIdentifier).toString();

            @Override
            public String toString() {
                return id;
            }
        });
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
