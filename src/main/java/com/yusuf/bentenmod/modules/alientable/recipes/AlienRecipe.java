package com.yusuf.bentenmod.modules.alientable.recipes;

import com.google.gson.*;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class AlienRecipe extends ShapedRecipe {

    public AlienRecipe(ResourceLocation p_i48162_1_, String p_i48162_2_, int p_i48162_3_, int p_i48162_4_, NonNullList<Ingredient> p_i48162_5_, ItemStack p_i48162_6_) {
        super(p_i48162_1_, p_i48162_2_, p_i48162_3_, p_i48162_4_, p_i48162_5_, p_i48162_6_);
    }

    @Override
    public IRecipeType<?> getType() {
        return RegisterRecipeInit.ALIEN_RECIPE;
    }

    public static final class Type implements IRecipeType<AlienRecipe> {
        @Override
        public String toString() {
            return BenTenMod.MOD_ID + ":alien_recipe";
        }
    }
    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlienRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("bentenmod", "alien_recipe");

        @Override
        public AlienRecipe fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
            return null;
        }

        @Nullable
        @Override
        public AlienRecipe fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
            return null;
        }

        @Override
        public void toNetwork(PacketBuffer p_199427_1_, AlienRecipe p_199427_2_) {

        }

    }
}