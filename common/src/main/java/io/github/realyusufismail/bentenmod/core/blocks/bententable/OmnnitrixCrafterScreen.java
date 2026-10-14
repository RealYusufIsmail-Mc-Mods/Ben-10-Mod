package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import io.github.realyusufismail.bentenmod.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import java.util.Objects;

/**
 * The Omnitrix Crafter screen. The recipe book only exists on loaders that can register a custom
 * recipe book type (Forge); elsewhere the screen behaves like a plain 3x3 crafter.
 *
 * @see CraftingScreen
 */
public class OmnnitrixCrafterScreen extends AbstractContainerScreen<OmnitrixCrafterMenu>
        implements RecipeUpdateListener {
    private static final ResourceLocation CRAFTER_LOCATION =
            new ResourceLocation("bentenmod", "textures/gui/omnitrix_crafter.png");
    private static final ResourceLocation RECIPE_BUTTON_LOCATION =
            new ResourceLocation("bentenmod", "textures/gui/omnitrix_crafter_buttons.png");
    private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
    private final boolean hasRecipeBook = Services.PLATFORM.hasOmnitrixRecipeBook();
    private boolean widthTooNarrow;

    public OmnnitrixCrafterScreen(OmnitrixCrafterMenu pMenu, Inventory pPlayerInventory,
            Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private boolean bookVisible() {
        return this.hasRecipeBook && this.recipeBookComponent.isVisible();
    }

    protected void init() {
        super.init();
        this.titleLabelX = 29;
        if (!this.hasRecipeBook) {
            return;
        }
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height,
                Objects.requireNonNull(this.minecraft, "Minecraft is null in table screen"),
                this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0,
                0, 19, RECIPE_BUTTON_LOCATION, (button) -> {
                    this.recipeBookComponent.toggleVisibility();
                    this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width,
                            this.imageWidth);
                    button.setPosition(this.leftPos + 5, this.height / 2 - 49);
                }));
        this.addWidget(this.recipeBookComponent);
        this.setInitialFocus(this.recipeBookComponent);
    }

    public void containerTick() {
        super.containerTick();
        if (this.hasRecipeBook) {
            this.recipeBookComponent.tick();
        }
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        if (!this.hasRecipeBook) {
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
            return;
        }
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
            this.recipeBookComponent.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        } else {
            this.recipeBookComponent.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.recipeBookComponent.renderGhostRecipe(pGuiGraphics, this.leftPos, this.topPos,
                    true, pPartialTick);
        }

        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        this.recipeBookComponent.renderTooltip(pGuiGraphics, this.leftPos, this.topPos, pMouseX,
                pMouseY);
    }

    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pX, int pY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(CRAFTER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xB9FF91,
                false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX,
                this.inventoryLabelY, 0xC8D8C7, false);
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX,
            double pMouseY) {
        return (!this.widthTooNarrow || !bookVisible())
                && super.isHovering(pX, pY, pWidth, pHeight, pMouseX, pMouseY);
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.hasRecipeBook
                && this.recipeBookComponent.mouseClicked(pMouseX, pMouseY, pButton)) {
            this.setFocused(this.recipeBookComponent);
            return true;
        }
        return this.widthTooNarrow && bookVisible()
                || super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    protected boolean hasClickedOutside(double pMouseX, double pMouseY, int pGuiLeft, int pGuiTop,
            int pMouseButton) {
        boolean flag = pMouseX < (double) pGuiLeft || pMouseY < (double) pGuiTop
                || pMouseX >= (double) (pGuiLeft + this.imageWidth)
                || pMouseY >= (double) (pGuiTop + this.imageHeight);
        if (!this.hasRecipeBook) {
            return flag;
        }
        return this.recipeBookComponent.hasClickedOutside(pMouseX, pMouseY, this.leftPos,
                this.topPos, this.imageWidth, this.imageHeight, pMouseButton) && flag;
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
        if (this.hasRecipeBook) {
            this.recipeBookComponent.slotClicked(pSlot);
        }
    }

    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }

    public void recipesUpdated() {
        if (this.hasRecipeBook) {
            this.recipeBookComponent.recipesUpdated();
        }
    }
}
