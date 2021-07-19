/*
package com.yusuf.bentenmod.modules.alientable.recipes;

import com.google.gson.JsonObject;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Map;


public interface IAlienRecipe extends ICraftingRecipe {
    @Override
    default IRecipeType<?> getType() {
        return RegisterRecipeInit.ALIEN_RECIPE;
    }



    public static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IAlienRecipe> {
        public Serializer() {
            setRegistryName(BenTenMod.MOD_ID, "table_recipe");
        }
        @Override
        public IAlienRecipe fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
            String s = JSONUtils.getAsString(p_199425_2_, "group", "");
            Map<String, Ingredient> map = ShapedRecipe.keyFromJson(JSONUtils.getAsJsonObject(p_199425_2_, "key"));
            String[] astring = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(JSONUtils.getAsJsonArray(p_199425_2_, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = ShapedRecipe.dissolvePattern(astring, map, i, j);
            ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(p_199425_2_, "result"));
            return new IAlienRecipe(p_199425_1_, s, i, j, nonnulllist, itemstack);
        }
        @Override
        public IAlienRecipe fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
            int i = p_199426_2_.readVarInt();
            int j = p_199426_2_.readVarInt();
            String s = p_199426_2_.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(p_199426_2_));
            }

            ItemStack itemstack = p_199426_2_.readItem();
            return new IAlienRecipe(p_199426_1_, s, i, j, nonnulllist, itemstack);
        }

        @Override
        public void toNetwork(PacketBuffer p_199427_1_, IAlienRecipe p_199427_2_) {
            p_199427_1_.writeVarInt(p_199427_2_.width);
            p_199427_1_.writeVarInt(p_199427_2_.height);
            p_199427_1_.writeUtf(p_199427_2_.group);

            for(Ingredient ingredient : p_199427_2_.recipeItems) {
                ingredient.toNetwork(p_199427_1_);
            }

            p_199427_1_.writeItem(p_199427_2_.result);
        }

    }
}


 */