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
package io.github.realyusufismail.bentenmod.platform;

import com.google.gson.JsonObject;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterCraftingBookCategory;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmntrixCrafterShapedRecipe;
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
    public OmntrixCrafterShapedRecipe createOmnitrixShapedRecipe(ResourceLocation id, String group,
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
