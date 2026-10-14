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
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;

/**
 * @see RecipeSerializer
 */
public class RecipeSerializerInit {
    public static final RegistrationProvider<RecipeSerializer<?>> SERIALIZERS =
            RegistrationProvider.get(Registries.RECIPE_SERIALIZER, BenTenMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<OmntrixCrafterShapedRecipe>> OMNITRIX_CRAFTER =
            register("omnitrix_crafter_shaped", new OmntrixCrafterShapedRecipe.Serializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> RegistryObject<S> register(
            String pKey, S pRecipeSerializer) {
        return SERIALIZERS.register(pKey, () -> pRecipeSerializer);
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
