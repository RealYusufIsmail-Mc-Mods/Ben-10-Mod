package com.yusuf.bentenmod.block.tablemachine;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yusuf.bentenmod.Main;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TableScreen extends ContainerScreen {
    public TableScreen(Container p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    public static final ResourceLocation SCREEN_ID = new ResourceLocation(Main.MOD_ID,
            "texture/gui/table_screen.png");


    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrix, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(SCREEN_ID);

        //main screen

        blit(matrix, leftPos, topPos, 0, 0, 176, 179);
    }


    @Override
    public void render(MatrixStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        renderBackground(matrix);
        super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
        renderTooltip(matrix, p_230430_2_, p_230430_3_);

    }

    @Override
    protected void renderLabels(MatrixStack matrix, int p_230451_2, int p_230451_3 ) {
        font.draw(matrix, inventory.getDisplayName(), 7, 81, 4210752);

        // TODO Render contatiner title
    }



}
