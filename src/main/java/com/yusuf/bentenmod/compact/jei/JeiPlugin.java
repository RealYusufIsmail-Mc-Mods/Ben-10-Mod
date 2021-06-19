/*
package com.yusuf.bentenmod.compact.jei;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.compact.jei.recipe.TableBlockCategory;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.RecipeTypeInit;
import com.yusuf.bentenmod.core.machine.bententable.TableBlock;
import com.yusuf.bentenmod.core.machine.bententable.TableContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;


import java.util.stream.Collectors;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(Main.MOD_ID, "jei_compat");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @SuppressWarnings("resource")
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(recipeManager.getRecipes().parallelStream().filter(recipe -> recipe.getType() == RecipeTypeInit.TABLE_BLOCK).collect(Collectors.toList()), TableBlockCategory.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new TableBlockCategory(helper)
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        addCatalyst(registration, new ItemStack(BlockInit.TABLE_BLOCK.get().asItem()), TableBlockCategory.ID);
    }

    private void addCatalyst(IRecipeCatalystRegistration registry, ItemStack catalystIcon, ResourceLocation categoryName) {
        registry.addRecipeCatalyst(catalystIcon, categoryName);
    }

    // Register the + icon for recipe GUI

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(TableContainer.class, TableBlockCategory.ID, 0, 2, 3, 36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(TableContainer.class, 93, 35, 23, 17, TableBlockCategory.ID);
    }
}
*/