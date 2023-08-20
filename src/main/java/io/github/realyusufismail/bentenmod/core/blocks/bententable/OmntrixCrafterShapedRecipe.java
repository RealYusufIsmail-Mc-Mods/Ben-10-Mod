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

package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.RecipeSerializerInit;
import lombok.val;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * @see CraftingRecipe
 */
public class OmntrixCrafterShapedRecipe
        implements IOmnitrixCraftingRecipe, IShapedRecipe<OmnitrixCrafterContainer> {

    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;
    final int width;
    final int height;
    final NonNullList<Ingredient> recipeItems;
    final ItemStack result;
    final String group;
    private final ResourceLocation id;
    private final OmnitrixCrafterCraftingBookCategory craftingBookCategory;
    private final boolean showNotification;

    public OmntrixCrafterShapedRecipe(ResourceLocation pId, String pGroup, int pWidth, int pHeight,
            NonNullList<Ingredient> pRecipeItems, ItemStack pResult, boolean showNotification,
            OmnitrixCrafterCraftingBookCategory craftingBookCategory) {
        this.id = pId;
        this.group = pGroup;
        this.width = pWidth;
        this.height = pHeight;
        this.recipeItems = pRecipeItems;
        this.result = pResult;
        this.showNotification = showNotification;
        this.craftingBookCategory = craftingBookCategory;
    }

    @Override
    public boolean matches(OmnitrixCrafterContainer pInv, Level pLevel) {
        for (int i = 0; i <= pInv.getWidth() - this.width; i++) {
            for (int j = 0; j <= pInv.getHeight() - this.height; j++) {
                if (this.matches(pInv, i, j, true)) {
                    return true;
                }
                if (this.matches(pInv, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matches(OmnitrixCrafterContainer pCraftingInventory, int pWidth, int pHeight,
            boolean pMirrored) {
        for (int i = 0; i < pCraftingInventory.getWidth(); i++) {
            for (int j = 0; j < pCraftingInventory.getHeight(); j++) {
                int k = i - pWidth;
                int l = j - pHeight;
                Ingredient ingredient = Ingredient.EMPTY;

                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    ingredient =
                            pMirrored ? this.recipeItems.get(this.width - k - 1 + l * this.width)
                                    : this.recipeItems.get(k + l * this.width);
                }

                if (!ingredient
                    .test(pCraftingInventory.getItem(i + j * pCraftingInventory.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(OmnitrixCrafterContainer p_44001_, RegistryAccess p_267165_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return this.result;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.OmnitrixCrafter.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.OMNITRIX_CRAFTER.get();
    }

    @Override
    public boolean showNotification() {
        return showNotification;
    }

    @Override
    public int getRecipeWidth() {
        return this.width;
    }

    @Override
    public int getRecipeHeight() {
        return this.height;
    }

    @Override
    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream()
            .filter(ingredient -> !ingredient.isEmpty())
            .anyMatch(ForgeHooks::hasNoElements);
    }

    @VisibleForTesting
    public static String[] shrink(String... pToShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;
        for (int i1 = 0; i1 < pToShrink.length; i1++) {
            String s = pToShrink[i1];
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
        if (pToShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[pToShrink.length - l - k];
            for (int k1 = 0; k1 < astring.length; k1++) {
                astring[k1] = pToShrink[k1 + k].substring(i, j + 1);
            }
            return astring;
        }
    }

    private static int firstNonSpace(String pEntry) {
        int i = 0;
        while (i < pEntry.length() && pEntry.charAt(i) == ' ') {
            ++i;
        }
        return i;
    }

    private static int lastNonSpace(String pEntry) {
        int i = pEntry.length() - 1;
        while (i >= 0 && pEntry.charAt(i) == ' ') {
            --i;
        }
        return i;
    }

    public static String[] patternFromJson(JsonArray pPatternArray) {
        String[] astring = new String[pPatternArray.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException(
                    "Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for (int i = 0; i < astring.length; i++) {
                String s = GsonHelper.convertToString(pPatternArray.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException(
                            "Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }
                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException(
                            "Invalid pattern: each row must be the same width");
                }
                astring[i] = s;
            }
            return astring;
        }
    }

    public static Map<String, Ingredient> keyFromJson(JsonObject pKeyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
            String key = entry.getKey();
            if (key.length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + key
                        + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(key)) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            map.put(key, Ingredient.fromJson(entry.getValue()));
        }
        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack itemStackFromJson(JsonObject pStackObject) {
        return CraftingHelper.getItemStack(pStackObject, true, true);
    }

    public static NonNullList<Ingredient> dissolvePattern(String[] pPattern,
            Map<String, Ingredient> pKeys, int pPatternWidth, int pPatternHeight) {
        NonNullList<Ingredient> nonnulllist =
                NonNullList.withSize(pPatternWidth * pPatternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(pKeys.keySet());
        set.remove(" ");
        for (int i = 0; i < pPattern.length; i++) {
            for (int j = 0; j < (pPattern[i] != null ? pPattern[i].length() : 0); j++) {
                String s = pPattern[i] != null ? pPattern[i].substring(j, j + 1) : "";
                Ingredient ingredient = pKeys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s
                            + "' but it's not defined in the key");
                }
                set.remove(s);
                nonnulllist.set(j + pPatternWidth * i, ingredient);
            }
        }
        if (!set.isEmpty()) {
            throw new JsonSyntaxException(
                    "Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    @Override
    public OmnitrixCrafterCraftingBookCategory getCategory() {
        return craftingBookCategory;
    }

    public static final class Type implements RecipeType<IOmnitrixCraftingRecipe> {
        @Override
        public String toString() {
            return BenTenMod.MOD_ID + ":table_recipe";
        }
    }

    public static final class Serializer implements RecipeSerializer<OmntrixCrafterShapedRecipe> {

        public Serializer() {}

        @Override
        public OmntrixCrafterShapedRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            val s = GsonHelper.getAsString(pJson, "group", "");
            val craftingBookCategory = OmnitrixCrafterCraftingBookCategory.codec()
                .byName(GsonHelper.getAsString(pJson, "category", null),
                        OmnitrixCrafterCraftingBookCategory.MISC);
            val map = OmntrixCrafterShapedRecipe
                .keyFromJson(GsonHelper.getAsJsonObject(pJson, "key"));
            val astring = OmntrixCrafterShapedRecipe.shrink(OmntrixCrafterShapedRecipe
                .patternFromJson(GsonHelper.getAsJsonArray(pJson, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            val nonnulllist = OmntrixCrafterShapedRecipe.dissolvePattern(astring, map, i, j);
            val itemstack = OmntrixCrafterShapedRecipe
                .itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
            val flag = GsonHelper.getAsBoolean(pJson, "show_notification", true);
            return new OmntrixCrafterShapedRecipe(pRecipeId, s, i, j, nonnulllist, itemstack, flag,
                    craftingBookCategory);
        }

        @Override
        public @NotNull OmntrixCrafterShapedRecipe fromNetwork(ResourceLocation pRecipeId,
                FriendlyByteBuf pBuffer) {
            int i = pBuffer.readVarInt();
            int j = pBuffer.readVarInt();
            String s = pBuffer.readUtf();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            nonnulllist.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

            ItemStack itemstack = pBuffer.readItem();
            val flag = pBuffer.readBoolean();
            val craftingBookCategory = pBuffer.readEnum(OmnitrixCrafterCraftingBookCategory.class);
            return new OmntrixCrafterShapedRecipe(pRecipeId, s, i, j, nonnulllist, itemstack, flag,
                    craftingBookCategory);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OmntrixCrafterShapedRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.width);
            pBuffer.writeVarInt(pRecipe.height);
            pBuffer.writeUtf(pRecipe.group);

            for (Ingredient ingredient : pRecipe.recipeItems) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeBoolean(pRecipe.showNotification);
        }
    }
}
