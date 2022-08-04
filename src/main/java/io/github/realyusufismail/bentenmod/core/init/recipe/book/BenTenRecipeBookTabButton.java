package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.realyusufismail.bentenmod.core.init.recipe.BenTenRecipeCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BenTenRecipeBookTabButton extends StateSwitchingButton {
    private final BenTenRecipeBookCategories category;
    private static final float ANIMATION_TIME = 15.0F;
    private float animationTime;

    public BenTenRecipeBookTabButton(BenTenRecipeBookCategories pCategory) {
        super(0, 0, 35, 27, false);
        this.category = pCategory;
        this.initTextureValues(153, 2, 35, 0, BenTenRecipeBookComponent.RECIPE_BOOK_LOCATION);
    }

    public void startAnimation(Minecraft pMinecraft) {
        BenTenClientRecipeBook clientrecipebook = BenTenClientRecipeBook
            .getAsBenTenClientRecipeBook(pMinecraft.player.getRecipeBook());
        List<BenTenRecipeCollection> list = clientrecipebook.getCollection(this.category);
        if (pMinecraft.player.containerMenu instanceof RecipeBookMenu) {
            for (BenTenRecipeCollection recipecollection : list) {
                for (Recipe<?> recipe : recipecollection.getRecipes(clientrecipebook
                    .isFiltering((BenTenRecipeBookMenu) pMinecraft.player.containerMenu))) {
                    if (clientrecipebook.willHighlight(recipe)) {
                        this.animationTime = 15.0F;
                        return;
                    }
                }
            }

        }
    }

    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.animationTime > 0.0F) {
            float f = 1.0F + 0.1F
                    * (float) Math.sin((double) (this.animationTime / 15.0F * (float) Math.PI));
            pPoseStack.pushPose();
            pPoseStack.translate((double) (this.x + 8), (double) (this.y + 12), 0.0D);
            pPoseStack.scale(1.0F, f, 1.0F);
            pPoseStack.translate((double) (-(this.x + 8)), (double) (-(this.y + 12)), 0.0D);
        }

        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        RenderSystem.disableDepthTest();
        int i = this.xTexStart;
        int j = this.yTexStart;
        if (this.isStateTriggered) {
            i += this.xDiffTex;
        }

        if (this.isHoveredOrFocused()) {
            j += this.yDiffTex;
        }

        int k = this.x;
        if (this.isStateTriggered) {
            k -= 2;
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(pPoseStack, k, this.y, i, j, this.width, this.height);
        RenderSystem.enableDepthTest();
        this.renderIcon(minecraft.getItemRenderer());
        if (this.animationTime > 0.0F) {
            pPoseStack.popPose();
            this.animationTime -= pPartialTick;
        }

    }

    private void renderIcon(ItemRenderer pItemRenderer) {
        List<ItemStack> list = this.category.getIconItems();
        int i = this.isStateTriggered ? -2 : 0;
        if (list.size() == 1) {
            pItemRenderer.renderAndDecorateFakeItem(list.get(0), this.x + 9 + i, this.y + 5);
        } else if (list.size() == 2) {
            pItemRenderer.renderAndDecorateFakeItem(list.get(0), this.x + 3 + i, this.y + 5);
            pItemRenderer.renderAndDecorateFakeItem(list.get(1), this.x + 14 + i, this.y + 5);
        }

    }

    public BenTenRecipeBookCategories getCategory() {
        return this.category;
    }

    public boolean updateVisibility(BenTenClientRecipeBook pRecipeBook) {
        List<BenTenRecipeCollection> list = pRecipeBook.getCollection(this.category);
        this.visible = false;
        if (list != null) {
            for (BenTenRecipeCollection recipecollection : list) {
                if (recipecollection.hasKnownRecipes() && recipecollection.hasFitting()) {
                    this.visible = true;
                    break;
                }
            }
        }

        return this.visible;
    }

}
