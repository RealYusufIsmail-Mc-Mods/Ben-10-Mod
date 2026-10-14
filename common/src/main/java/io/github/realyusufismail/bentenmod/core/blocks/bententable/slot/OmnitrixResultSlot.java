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
package io.github.realyusufismail.bentenmod.core.blocks.bententable.slot;

import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterContainer;
import io.github.realyusufismail.bentenmod.core.init.RecipeTypeInit;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import io.github.realyusufismail.bentenmod.platform.Services;
import org.jetbrains.annotations.NotNull;

public class OmnitrixResultSlot extends Slot {
    private final OmnitrixCrafterContainer craftSlots;
    private final Player player;
    private int removeCount = 0;

    public OmnitrixResultSlot(Player pPlayer, OmnitrixCrafterContainer pCraftSlots,
            Container pContainer, int pSlot, int pXPosition, int pYPosition) {
        super(pContainer, pSlot, pXPosition, pYPosition);
        this.player = pPlayer;
        this.craftSlots = pCraftSlots;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as
     * furnace fuel.
     */
    public boolean mayPlace(ItemStack pStack) {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg.
     * Returns the new stack.
     */
    public @NotNull ItemStack remove(int pAmount) {
        if (hasItem()) {
            this.removeCount += Math.min(pAmount, this.getItem().getCount());
        }

        return super.remove(pAmount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     * Typically increases an internal count then calls onCrafting(item).
     */
    protected void onQuickCraft(ItemStack pStack, int pAmount) {
        this.removeCount += pAmount;
        this.checkTakeAchievements(pStack);
    }

    protected void onSwapCraft(int pNumItemsCrafted) {
        this.removeCount += pNumItemsCrafted;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void checkTakeAchievements(ItemStack pStack) {
        if (this.removeCount > 0) {
            pStack.onCraftedBy(this.player.level(), this.player, this.removeCount);
            Services.PLATFORM.onItemCrafted(this.player, pStack, this.craftSlots);
        }

        if (this.container instanceof RecipeHolder recipeHolder) {
            List<ItemStack> ingredients = new ArrayList<>();
            for (int i = 0; i < this.craftSlots.getContainerSize(); i++) {
                ingredients.add(this.craftSlots.getItem(i));
            }
            recipeHolder.awardUsedRecipes(this.player, ingredients);
        }

        this.removeCount = 0;
    }

    public void onTake(Player pPlayer, ItemStack pStack) {
        this.checkTakeAchievements(pStack);
        Services.PLATFORM.setCraftingPlayer(pPlayer);
        NonNullList<ItemStack> nonnulllist = pPlayer.level()
            .getRecipeManager()
            .getRemainingItemsFor(RecipeTypeInit.OMNITRIX_CRAFTER_TYPE.get(), this.craftSlots,
                    pPlayer.level());
        Services.PLATFORM.setCraftingPlayer(null);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = this.craftSlots.getItem(i);
            ItemStack itemstack1 = nonnulllist.get(i);
            if (!itemstack.isEmpty()) {
                this.craftSlots.removeItem(i, 1);
                itemstack = this.craftSlots.getItem(i);
            }

            if (!itemstack1.isEmpty()) {
                if (itemstack.isEmpty()) {
                    this.craftSlots.setItem(i, itemstack1);
                } else if (ItemStack.isSameItemSameTags(itemstack, itemstack1)) {
                    itemstack1.grow(itemstack.getCount());
                    this.craftSlots.setItem(i, itemstack1);
                } else if (!this.player.getInventory().add(itemstack1)) {
                    this.player.drop(itemstack1, false);
                }
            }
        }

    }
}
