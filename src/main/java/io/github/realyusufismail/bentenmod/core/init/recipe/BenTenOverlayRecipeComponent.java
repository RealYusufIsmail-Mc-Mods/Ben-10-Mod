package io.github.realyusufismail.bentenmod.core.init.recipe;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BenTenOverlayRecipeComponent extends GuiComponent implements Widget, GuiEventListener {
    static final ResourceLocation RECIPE_BOOK_LOCATION =
            new ResourceLocation("textures/gui/recipe_book.png");
    private static final int MAX_ROW = 4;
    private static final int MAX_ROW_LARGE = 5;
    private static final float ITEM_RENDER_SCALE = 0.375F;
    private final List<OverlayRecipeButton> recipeButtons = Lists.newArrayList();
    private boolean isVisible;
    private int x;
    private int y;
    Minecraft minecraft;
    private BenTenRecipeCollection collection;
    @Nullable
    private Recipe<?> lastRecipeClicked;
    float time;
    boolean isFurnaceMenu;

    public BenTenOverlayRecipeComponent() {}

    public void init(Minecraft pMinecraft, BenTenRecipeCollection pCollection, int pX, int pY,
            int p_100199_, int p_100200_, float p_100201_) {
        this.minecraft = pMinecraft;
        this.collection = pCollection;
        if (pMinecraft.player.containerMenu instanceof AbstractFurnaceMenu) {
            this.isFurnaceMenu = true;
        }

        boolean flag = pMinecraft.player.getRecipeBook()
            .isFiltering((RecipeBookMenu) pMinecraft.player.containerMenu);
        List<Recipe<?>> list = pCollection.getDisplayRecipes(true);
        List<Recipe<?>> list1 =
                flag ? Collections.emptyList() : pCollection.getDisplayRecipes(false);
        int i = list.size();
        int j = i + list1.size();
        int k = j <= 16 ? 4 : 5;
        int l = (int) Math.ceil((double) ((float) j / (float) k));
        this.x = pX;
        this.y = pY;
        int i1 = 25;
        float f = (float) (this.x + Math.min(j, k) * 25);
        float f1 = (float) (p_100199_ + 50);
        if (f > f1) {
            this.x = (int) ((float) this.x - p_100201_ * (float) ((int) ((f - f1) / p_100201_)));
        }

        float f2 = (float) (this.y + l * 25);
        float f3 = (float) (p_100200_ + 50);
        if (f2 > f3) {
            this.y = (int) ((float) this.y - p_100201_ * (float) Mth.ceil((f2 - f3) / p_100201_));
        }

        float f4 = (float) this.y;
        float f5 = (float) (p_100200_ - 100);
        if (f4 < f5) {
            this.y = (int) ((float) this.y - p_100201_ * (float) Mth.ceil((f4 - f5) / p_100201_));
        }

        this.isVisible = true;
        this.recipeButtons.clear();

        for (int j1 = 0; j1 < j; ++j1) {
            boolean flag1 = j1 < i;
            Recipe<?> recipe = flag1 ? list.get(j1) : list1.get(j1 - i);
            int k1 = this.x + 4 + 25 * (j1 % k);
            int l1 = this.y + 5 + 25 * (j1 / k);
            if (this.isFurnaceMenu) {
                this.recipeButtons.add(new BenTenOverlayRecipeComponent.OverlaySmeltingRecipeButton(
                        k1, l1, recipe, flag1));
            } else {
                this.recipeButtons.add(new BenTenOverlayRecipeComponent.OverlayRecipeButton(k1, l1,
                        recipe, flag1));
            }
        }

        this.lastRecipeClicked = null;
    }

    public boolean changeFocus(boolean pFocus) {
        return false;
    }

    public BenTenRecipeCollection getRecipeCollection() {
        return this.collection;
    }

    @Nullable
    public Recipe<?> getLastRecipeClicked() {
        return this.lastRecipeClicked;
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton != 0) {
            return false;
        } else {
            Iterator var6 = this.recipeButtons.iterator();

            BenTenOverlayRecipeComponent.OverlayRecipeButton $$3;
            do {
                if (!var6.hasNext()) {
                    return false;
                }

                $$3 = (BenTenOverlayRecipeComponent.OverlayRecipeButton) var6.next();
            } while (!$$3.mouseClicked(pMouseX, pMouseY, pButton));

            this.lastRecipeClicked = $$3.recipe;
            return true;
        }
    }

    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return false;
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.isVisible) {
            this.time += pPartialTick;
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, RECIPE_BOOK_LOCATION);
            pPoseStack.pushPose();
            pPoseStack.translate(0.0D, 0.0D, 170.0D);
            int i = this.recipeButtons.size() <= 16 ? 4 : 5;
            int j = Math.min(this.recipeButtons.size(), i);
            int k = Mth.ceil((float) this.recipeButtons.size() / (float) i);
            int l = 24;
            int i1 = 4;
            int j1 = 82;
            int k1 = 208;
            this.nineInchSprite(pPoseStack, j, k, 24, 4, 82, 208);
            RenderSystem.disableBlend();

            for (BenTenOverlayRecipeComponent.OverlayRecipeButton overlayrecipecomponent$overlayrecipebutton : this.recipeButtons) {
                overlayrecipecomponent$overlayrecipebutton.render(pPoseStack, pMouseX, pMouseY,
                        pPartialTick);
            }

            pPoseStack.popPose();
        }
    }

    private void nineInchSprite(PoseStack pPoseStack, int p_100215_, int p_100216_, int p_100217_,
            int p_100218_, int p_100219_, int p_100220_) {
        this.blit(pPoseStack, this.x, this.y, p_100219_, p_100220_, p_100218_, p_100218_);
        this.blit(pPoseStack, this.x + p_100218_ * 2 + p_100215_ * p_100217_, this.y,
                p_100219_ + p_100217_ + p_100218_, p_100220_, p_100218_, p_100218_);
        this.blit(pPoseStack, this.x, this.y + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_,
                p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
        this.blit(pPoseStack, this.x + p_100218_ * 2 + p_100215_ * p_100217_,
                this.y + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100217_ + p_100218_,
                p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);

        for (int $$7 = 0; $$7 < p_100215_; ++$$7) {
            this.blit(pPoseStack, this.x + p_100218_ + $$7 * p_100217_, this.y,
                    p_100219_ + p_100218_, p_100220_, p_100217_, p_100218_);
            this.blit(pPoseStack, this.x + p_100218_ + ($$7 + 1) * p_100217_, this.y,
                    p_100219_ + p_100218_, p_100220_, p_100218_, p_100218_);

            for (int $$8 = 0; $$8 < p_100216_; ++$$8) {
                if ($$7 == 0) {
                    this.blit(pPoseStack, this.x, this.y + p_100218_ + $$8 * p_100217_, p_100219_,
                            p_100220_ + p_100218_, p_100218_, p_100217_);
                    this.blit(pPoseStack, this.x, this.y + p_100218_ + ($$8 + 1) * p_100217_,
                            p_100219_, p_100220_ + p_100218_, p_100218_, p_100218_);
                }

                this.blit(pPoseStack, this.x + p_100218_ + $$7 * p_100217_,
                        this.y + p_100218_ + $$8 * p_100217_, p_100219_ + p_100218_,
                        p_100220_ + p_100218_, p_100217_, p_100217_);
                this.blit(pPoseStack, this.x + p_100218_ + ($$7 + 1) * p_100217_,
                        this.y + p_100218_ + $$8 * p_100217_, p_100219_ + p_100218_,
                        p_100220_ + p_100218_, p_100218_, p_100217_);
                this.blit(pPoseStack, this.x + p_100218_ + $$7 * p_100217_,
                        this.y + p_100218_ + ($$8 + 1) * p_100217_, p_100219_ + p_100218_,
                        p_100220_ + p_100218_, p_100217_, p_100218_);
                this.blit(pPoseStack, this.x + p_100218_ + ($$7 + 1) * p_100217_ - 1,
                        this.y + p_100218_ + ($$8 + 1) * p_100217_ - 1, p_100219_ + p_100218_,
                        p_100220_ + p_100218_, p_100218_ + 1, p_100218_ + 1);
                if ($$7 == p_100215_ - 1) {
                    this.blit(pPoseStack, this.x + p_100218_ * 2 + p_100215_ * p_100217_,
                            this.y + p_100218_ + $$8 * p_100217_, p_100219_ + p_100217_ + p_100218_,
                            p_100220_ + p_100218_, p_100218_, p_100217_);
                    this.blit(pPoseStack, this.x + p_100218_ * 2 + p_100215_ * p_100217_,
                            this.y + p_100218_ + ($$8 + 1) * p_100217_,
                            p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100218_, p_100218_,
                            p_100218_);
                }
            }

            this.blit(pPoseStack, this.x + p_100218_ + $$7 * p_100217_,
                    this.y + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100218_,
                    p_100220_ + p_100217_ + p_100218_, p_100217_, p_100218_);
            this.blit(pPoseStack, this.x + p_100218_ + ($$7 + 1) * p_100217_,
                    this.y + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100218_,
                    p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
        }

    }

    public void setVisible(boolean pIsVisible) {
        this.isVisible = pIsVisible;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    @OnlyIn(Dist.CLIENT)
    private class OverlaySmeltingRecipeButton
            extends BenTenOverlayRecipeComponent.OverlayRecipeButton {
        public OverlaySmeltingRecipeButton(int p_100262_, int p_100263_, Recipe<?> p_100264_,
                boolean p_100265_) {
            super(p_100262_, p_100263_, p_100264_, p_100265_);
        }

        protected void calculateIngredientsPositions(Recipe<?> p_100267_) {
            ItemStack[] $$1 = ((Ingredient) p_100267_.getIngredients().get(0)).getItems();
            this.ingredientPos
                .add(new BenTenOverlayRecipeComponent.OverlayRecipeButton.Pos(10, 10, $$1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private class OverlayRecipeButton extends AbstractWidget implements PlaceRecipe<Ingredient> {
        final Recipe<?> recipe;
        private final boolean isCraftable;
        protected final List<BenTenOverlayRecipeComponent.OverlayRecipeButton.Pos> ingredientPos =
                Lists.newArrayList();

        public OverlayRecipeButton(int pX, int pY, Recipe<?> pRecipe, boolean pIsCraftable) {
            super(pX, pY, 200, 20, CommonComponents.EMPTY);
            this.width = 24;
            this.height = 24;
            this.recipe = pRecipe;
            this.isCraftable = pIsCraftable;
            this.calculateIngredientsPositions(pRecipe);
        }

        protected void calculateIngredientsPositions(Recipe<?> pRecipe) {
            this.placeRecipe(3, 3, -1, pRecipe, pRecipe.getIngredients().iterator(), 0);
        }

        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }

        public void addItemToSlot(Iterator<Ingredient> pIngredients, int pSlot, int pMaxAmount,
                int pY, int pX) {
            ItemStack[] $$5 = ((Ingredient) pIngredients.next()).getItems();
            if ($$5.length != 0) {
                this.ingredientPos.add(new BenTenOverlayRecipeComponent.OverlayRecipeButton.Pos(
                        3 + pX * 7, 3 + pY * 7, $$5));
            }

        }

        public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY,
                float pPartialTick) {
            RenderSystem.setShaderTexture(0, BenTenOverlayRecipeComponent.RECIPE_BOOK_LOCATION);
            int $$4 = 152;
            if (!this.isCraftable) {
                $$4 += 26;
            }

            int $$5 = BenTenOverlayRecipeComponent.this.isFurnaceMenu ? 130 : 78;
            if (this.isHoveredOrFocused()) {
                $$5 += 26;
            }

            this.blit(pPoseStack, this.x, this.y, $$4, $$5, this.width, this.height);
            PoseStack $$6 = RenderSystem.getModelViewStack();
            $$6.pushPose();
            $$6.translate((double) (this.x + 2), (double) (this.y + 2), 125.0);
            Iterator var8 = this.ingredientPos.iterator();

            while (var8.hasNext()) {
                BenTenOverlayRecipeComponent.OverlayRecipeButton.Pos $$7 =
                        (BenTenOverlayRecipeComponent.OverlayRecipeButton.Pos) var8.next();
                $$6.pushPose();
                $$6.translate((double) $$7.x, (double) $$7.y, 0.0);
                $$6.scale(0.375F, 0.375F, 1.0F);
                $$6.translate(-8.0, -8.0, 0.0);
                RenderSystem.applyModelViewMatrix();
                BenTenOverlayRecipeComponent.this.minecraft.getItemRenderer()
                    .renderAndDecorateItem($$7.ingredients[Mth
                        .floor(BenTenOverlayRecipeComponent.this.time / 30.0F)
                            % $$7.ingredients.length], 0, 0);
                $$6.popPose();
            }

            $$6.popPose();
            RenderSystem.applyModelViewMatrix();
        }

        @OnlyIn(Dist.CLIENT)
        protected class Pos {
            public final ItemStack[] ingredients;
            public final int x;
            public final int y;

            public Pos(int pX, int pY, ItemStack[] pIngredients) {
                this.x = pX;
                this.y = pY;
                this.ingredients = pIngredients;
            }
        }
    }
}

