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

package io.github.realyusufismail.bentenmod.platform;

import com.google.gson.JsonObject;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterCraftingBookCategory;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterShapedRecipe;
import io.github.realyusufismail.bentenmod.platform.services.IPlatformHelper;
import io.github.realyusufismail.bentenmod.core.item.ForgeOmnitrixWatchItem;
import io.github.realyusufismail.bentenmod.OmnitrixNetwork;
import io.github.realyusufismail.bentenmod.recipebook.ForgeOmnitrixShapedRecipe;
import io.github.realyusufismail.bentenmod.recipebook.RecipeBookTypeInit;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.BiFunction;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public net.minecraft.world.item.Item createOmnitrixWatchItem() {
        return new ForgeOmnitrixWatchItem(new net.minecraft.world.item.Item.Properties());
    }

    @Override
    public CreativeModeTab.Builder creativeTabBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public ItemStack itemStackFromJson(JsonObject json) {
        return CraftingHelper.getItemStack(json, true, true);
    }

    @Override
    public OmnitrixCrafterShapedRecipe createOmnitrixShapedRecipe(ResourceLocation id, String group,
            int width, int height, NonNullList<Ingredient> ingredients, ItemStack result,
            boolean showNotification, OmnitrixCrafterCraftingBookCategory category) {
        return new ForgeOmnitrixShapedRecipe(id, group, width, height, ingredients, result,
                showNotification, category);
    }

    @Override
    public boolean hasOmnitrixRecipeBook() {
        return true;
    }

    @Override
    public RecipeBookType omnitrixRecipeBookType() {
        return RecipeBookTypeInit.OMNITRIX_CRAFTER;
    }

    @Override
    public void syncOmnitrixState(ServerPlayer player) {
        if (player.connection != null) {
            OmnitrixNetwork.syncTrackers(player);
        }
    }

    @Override
    public void sendPortalTravelEffect(ServerPlayer player) {
        if (player.connection != null) {
            OmnitrixNetwork.sendPortalTravelEffect(player);
        }
    }

    @Override
    public void setCraftingPlayer(Player player) {
        ForgeHooks.setCraftingPlayer(player);
    }

    @Override
    public void onItemCrafted(Player player, ItemStack crafted, Container craftMatrix) {
        ForgeEventFactory.firePlayerCraftingEvent(player, crafted, craftMatrix);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(
            BiFunction<Integer, Inventory, T> factory) {
        return new MenuType<>(factory::apply, FeatureFlags.DEFAULT_FLAGS);
    }
}
