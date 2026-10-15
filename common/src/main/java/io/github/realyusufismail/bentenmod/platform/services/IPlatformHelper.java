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

    /** Sends the owner-only screen transition that precedes Null Void dimension travel. */
    void sendPortalTravelEffect(ServerPlayer player);

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
