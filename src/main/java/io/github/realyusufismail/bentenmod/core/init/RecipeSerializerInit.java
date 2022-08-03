package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @see RecipeSerializer
 */
public class RecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BenTenMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<OmntrixCrafterShapedRecipe>> OMNITRIX_CRAFTER =
            register("omnitrix_crafter_shaped", new OmntrixCrafterShapedRecipe.Serializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> RegistryObject<S> register(
            String pKey, S pRecipeSerializer) {
        return SERIALIZERS.register(pKey, () -> pRecipeSerializer);
    }
}
