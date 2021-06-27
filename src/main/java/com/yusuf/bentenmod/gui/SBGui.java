package com.yusuf.bentenmod.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yusuf.bentenmod.backpack.BackpackItem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SBGui extends ContainerScreen<SBContainer> {
    public SBGui(SBContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);

        BackpackItem backpack = (BackpackItem) container.backpackItem.getItem();

        GUI = backpack.backpack.location;
        imageWidth = backpack.backpack.xSize;
        imageHeight = backpack.backpack.ySize;
    }

    private final ResourceLocation GUI;

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f ,1.0f);
        this.getMinecraft().textureManager.bind(GUI);
        drawTexturedQuad(leftPos, topPos, imageWidth, imageHeight, 0, 0, 1, 1, 0);
    }
    private void drawTexturedQuad(int x, int y, int width, int height, float tx, float ty, float tw, float th, float z) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuilder();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.vertex((double)x + 0, (double) y + height, (double) z).uv(tx,ty + th).endVertex();
        buffer.vertex((double) x + width,(double) y + height, (double) z).uv(tx + tw,ty + th).endVertex();
        buffer.vertex((double) x + width, (double) y + 0, (double) z).uv(tx + tw,ty).endVertex();
        buffer.vertex((double) x + 0, (double) y + 0, (double) z).uv(tx,ty).endVertex();

        tess.end();
    }

    @Override
    protected void renderTooltip(MatrixStack p_230451_1_, int mouseX, int mouseY) {
        this.font.draw(p_230451_1_, this.title.getString(), 7,6,0x404040);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_,p_render_1_, p_render_2_, p_render_3_);
        this.renderTooltip(p_230430_1_, p_render_1_, p_render_2_);
    }


    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {

        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }
}
