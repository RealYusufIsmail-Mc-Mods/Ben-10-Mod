/*
package com.yusuf.bentenmod.compact.jei.recipe;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.common.LangKeys;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.modules.bententable.recipes.TableRecipe;
import jdk.nashorn.internal.ir.Block;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TableBlockCategory implements IRecipeCategory<TableRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(Main.MOD_ID, "table_block_category");
    private static final ResourceLocation TEXTURE_ID = new ResourceLocation(Main.MOD_ID, "textures/gui/table_block_jei.png");

    private final IDrawable background;
    private final IDrawableAnimated arrow;
    private final IDrawable icon;

    public TableBlockCategory(IGuiHelper helper) {
        background = helper.createDrawable(TEXTURE_ID, 0, 0, 0, 0);
        //TODO Change this.
        icon = helper.createDrawableIngredient(new ItemStack(BlockInit.TABLE_BLOCK.get()));

        IDrawableStatic arrowNonAnimated = helper.createDrawable(TEXTURE_ID, 0, 0, 0, 0);
        //TODO Change this.
        arrow = helper.createAnimatedDrawable(arrowNonAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
    }
    @Override
    public ResourceLocation getUid() {
        return ID;
    }
    @Override
    public Class<? extends TableRecipe> getRecipeClass() {
        return TableRecipe.class;
    }

    @Override
    public String getTitle() {
        return LangKeys.TABLE_JEI.getString();
    }
    @Override
    public IDrawable getBackground() {
        return background;
    }
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(TableRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void draw(TableRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 78, 16);
    }
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TableRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();

        group.init(0, true, 0, 0);
        //TODO Change this.
        group.init(1, true, 0, 0);
        //TODO Change this.
        group.init(2, true, 0, 0);
        //TODO Change this.
        group.init(3, false, 0, 0);
        //TODO Change this.

        group.set(ingredients);
    }

}



 */
