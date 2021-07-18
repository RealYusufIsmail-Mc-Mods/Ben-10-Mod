package com.yusuf.bentenmod.core.machine.bententable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.common.LangKeys;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TableScreen extends ContainerScreen<TableContainer> {
    public static final ResourceLocation SCREEN_ID = new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/table_screen.png");

    public TableScreen(TableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(SCREEN_ID);

        // BenTenMod Screen
        blit(matrix, leftPos, topPos, 0, 0, 176, 179);

        blit(matrix, leftPos + 97, topPos + 38, 179, 25, menu.getProcess() + 1, 17);
    }


     //CTRL + O > OVERRIDE METHODS

    @Override
    public void render(MatrixStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        renderBackground(matrix);
        super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
        renderTooltip(matrix, p_230430_2_, p_230430_3_);
    }

    /*
     * CTRL + O > OVERRIDE METHODS
     * 4210752 is the default number, can't be changed
     */
    @Override
    protected void renderLabels(MatrixStack matrix, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrix, inventory.getDisplayName(), 7, 81, 4210752);
        font.draw(matrix, LangKeys.TABLE_SCREEN, 5, 10, 4210752);
    }
}