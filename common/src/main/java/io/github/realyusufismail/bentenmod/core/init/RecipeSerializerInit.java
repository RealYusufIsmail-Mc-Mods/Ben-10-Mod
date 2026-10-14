package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterShapedRecipe;
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

    public static final RegistryObject<RecipeSerializer<OmnitrixCrafterShapedRecipe>> OMNITRIX_CRAFTER =
            register("omnitrix_crafter_shaped", new OmnitrixCrafterShapedRecipe.Serializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> RegistryObject<S> register(
            String pKey, S pRecipeSerializer) {
        return SERIALIZERS.register(pKey, () -> pRecipeSerializer);
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
