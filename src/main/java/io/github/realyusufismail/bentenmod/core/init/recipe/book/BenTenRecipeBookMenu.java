package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import io.github.realyusufismail.bentenmod.core.init.recipe.BenTenServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;

public abstract class BenTenRecipeBookMenu<C extends Container> extends AbstractContainerMenu {
    public BenTenRecipeBookMenu(MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    public void handlePlacement(boolean pPlaceAll, Recipe<?> pRecipe, ServerPlayer pPlayer) {
        new BenTenServerPlaceRecipe(this).recipeClicked(pPlayer, pRecipe, pPlaceAll);
    }

    public abstract void fillCraftSlotsStackedContents(StackedContents pItemHelper);

    public abstract void clearCraftingContent();

    public abstract boolean recipeMatches(Recipe<? super C> pRecipe);

    public abstract int getResultSlotIndex();

    public abstract int getGridWidth();

    public abstract int getGridHeight();

    public abstract int getSize();

    public java.util.List<BenTenRecipeBookCategories> getRecipeBookCategories() {
        return BenTenRecipeBookCategories.getCategories(this.getRecipeBookType());
    }

    public abstract BenTenRecipeBookType getRecipeBookType();

    public abstract boolean shouldMoveToInventory(int pSlotIndex);
}
