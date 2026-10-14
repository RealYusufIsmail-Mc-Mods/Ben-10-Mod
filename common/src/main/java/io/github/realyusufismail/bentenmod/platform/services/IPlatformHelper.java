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

package io.github.realyusufismail.bentenmod.platform.services;

import com.google.gson.JsonObject;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterCraftingBookCategory;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterShapedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.BiFunction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;

/**
 * The small set of things that differ between Forge and Fabric. Everything else lives in common
 * code.
 */
public interface IPlatformHelper {

    /** "Forge" or "Fabric". */
    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    /** Creates the watch with the loader's client-only GeckoLib item renderer bridge. */
    Item createOmnitrixWatchItem();

    /** Forge and Fabric each need their own builder so the tab gets a position in the menu. */
    CreativeModeTab.Builder creativeTabBuilder();

    /** Reads a recipe result. Forge also reads the optional {@code nbt} field. */
    ItemStack itemStackFromJson(JsonObject json);

    /**
     * Creates the Omnitrix Crafter shaped recipe. Forge returns a subclass that implements
     * {@code IShapedRecipe} so the recipe book places ingredients in the right shape.
     */
    OmnitrixCrafterShapedRecipe createOmnitrixShapedRecipe(ResourceLocation id, String group,
            int width, int height, NonNullList<Ingredient> ingredients, ItemStack result,
            boolean showNotification, OmnitrixCrafterCraftingBookCategory category);

    /** Whether the Omnitrix Crafter has its own recipe book (Forge only). */
    boolean hasOmnitrixRecipeBook();

    /** The recipe book type of the Omnitrix Crafter, or {@link RecipeBookType#CRAFTING}. */
    RecipeBookType omnitrixRecipeBookType();

    /** Sends the updated unlock list through each loader's existing Omnitrix state packet. */
    void syncOmnitrixState(ServerPlayer player);

    /**
     * Creates a menu type for a menu that needs no extra data from the server. Vanilla keeps the
     * factory interface package-private; each loader exposes it.
     */
    <T extends AbstractContainerMenu> MenuType<T> createMenuType(
            BiFunction<Integer, Inventory, T> factory);

    /** Sets the player whose crafting is being processed (Forge uses it for container items). */
    default void setCraftingPlayer(Player player) {}

    /** Fires the loader's "item crafted" event, if it has one. */
    default void onItemCrafted(Player player, ItemStack crafted, Container craftMatrix) {}
}
