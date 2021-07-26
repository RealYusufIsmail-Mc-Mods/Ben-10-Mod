package com.yusuf.bentenmod.modules.alientable.recipes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
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
import java.util.Set;

public class AlienRecipe extends ShapedRecipe {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;

    public static void setCraftingSize(int width, int height) {
        if (MAX_WIDTH < width) MAX_WIDTH = width;
        if (MAX_HEIGHT < height) MAX_HEIGHT = height;
    }
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack result;
    private final ResourceLocation id;
    private final String group;

    public AlienRecipe(ResourceLocation p_i48162_1_, String p_i48162_2_, int p_i48162_3_, int p_i48162_4_, NonNullList<Ingredient> p_i48162_5_, ItemStack p_i48162_6_) {
        super(p_i48162_1_, p_i48162_2_, p_i48162_3_, p_i48162_4_, p_i48162_5_, p_i48162_6_);
        this.id = p_i48162_1_;
        this.group = p_i48162_2_;
        this.width = p_i48162_3_;
        this.height = p_i48162_4_;
        this.recipeItems = p_i48162_5_;
        this.result = p_i48162_6_;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return new AlienRecipe.Serializer();
    }


    private static Map<String, Ingredient> keyFromJson(JsonObject p_192408_0_) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : p_192408_0_.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    private static String[] patternFromJson(JsonArray p_192407_0_) {
        String[] astring = new String[p_192407_0_.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = JSONUtils.convertToString(p_192407_0_.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... p_194134_0_) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < p_194134_0_.length; ++i1) {
            String s = p_194134_0_[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (p_194134_0_.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[p_194134_0_.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = p_194134_0_[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }
    private static int firstNonSpace(String p_194135_0_) {
        int i;
        for(i = 0; i < p_194135_0_.length() && p_194135_0_.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String p_194136_0_) {
        int i;
        for(i = p_194136_0_.length() - 1; i >= 0 && p_194136_0_.charAt(i) == ' '; --i) {
        }

        return i;
    }

    @Override
    public int getRecipeHeight() {
        return getHeight();
    }

    private static NonNullList<Ingredient> dissolvePattern(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_192402_2_ * p_192402_3_, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
        set.remove(" ");

        for(int i = 0; i < p_192402_0_.length; ++i) {
            for(int j = 0; j < p_192402_0_[i].length(); ++j) {
                String s = p_192402_0_[i].substring(j, j + 1);
                Ingredient ingredient = p_192402_1_.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + p_192402_2_ * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
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
            String s = JSONUtils.getAsString(p_199425_2_, "group", "");
            Map<String, Ingredient> map = AlienRecipe.keyFromJson(JSONUtils.getAsJsonObject(p_199425_2_, "key"));
            String[] astring = AlienRecipe.shrink(AlienRecipe.patternFromJson(JSONUtils.getAsJsonArray(p_199425_2_, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = AlienRecipe.dissolvePattern(astring, map, i, j);
            ItemStack itemstack = AlienRecipe.itemFromJson(JSONUtils.getAsJsonObject(p_199425_2_, "result"));
            return new AlienRecipe(p_199425_1_, s, i, j, nonnulllist, itemstack);
        }

        @Nullable
        @Override
        public AlienRecipe fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
            int i = p_199426_2_.readVarInt();
            int j = p_199426_2_.readVarInt();
            String s = p_199426_2_.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(p_199426_2_));
            }

            ItemStack itemstack = p_199426_2_.readItem();
            return new AlienRecipe(p_199426_1_, s, i, j, nonnulllist, itemstack);
        }

        @Override
        public void toNetwork(PacketBuffer p_199427_1_, AlienRecipe p_199427_2_) {
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