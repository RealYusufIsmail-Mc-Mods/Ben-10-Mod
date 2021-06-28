package com.yusuf.bentenmod.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.backpack.BackpackFilterHandler;
import com.yusuf.bentenmod.backpack.BackpackItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

public class FilterGui extends ContainerScreen<FilterContainer> {
    public FilterGui(FilterContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);

        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        int slot = 0;
        for (int row = 0; row < 4; row ++) {
            for (int col = 0; col < 4; col++) {
                int x = leftPos + 7 + col * 18;
                int y = topPos + 7 + row * 18;

                addButton(new SlotButton(x+1, y+1,18 ,18, slot, button -> {
                    Minecraft.getInstance().gameMode.handleInventoryButtonClick(menu.containerId, ((SlotButton)button).slot);
                    menu.clickMenuButton(inventory.player, ((SlotButton)button).slot);
                }));
                slot++;
            }
        }

        addButton(new SwitchButton(leftPos + 80, topPos + 8, "bentenmod.whitelist", ((menu.getFilterOpts() & 1) > 0) , (button)-> ((SwitchButton)button).state = (menu.setFilterOpts(menu.getFilterOpts() ^ 1) & 1) > 0));
        addButton(new SwitchButton(leftPos + 80, topPos + 8 + 18, "bentenmod.nbtdata", ((menu.getFilterOpts() & 2) > 0) , (button)-> ((SwitchButton)button).state = (menu.setFilterOpts(menu.getFilterOpts() ^ 2) & 2) > 0));
        addButton(new SwitchButton(leftPos + 80, topPos + 8 + 54, "bentenmod.autopickup", menu.getPickup() , (button)-> ((SwitchButton)button).state = menu.togglePickup()));
    }

    private final ResourceLocation GUI = new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/filter_gui.png");

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }



    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f ,1.0f);

        this.getMinecraft().textureManager.bind(GUI);
        drawTexturedQuad(leftPos, topPos, imageWidth, imageHeight, 0, 0, 1, 1, 0);
    }

    private void drawTexturedQuad(int x, int y, int width, int height, float tx, float ty, float tw, float th, float z) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuilder();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.vertex((double)x + 0, (double) y + height, z).uv(tx,ty + th).endVertex();
        buffer.vertex((double) x + width,(double) y + height, z).uv(tx + tw,ty + th).endVertex();
        buffer.vertex((double) x + width, (double) y + 0, z).uv(tx + tw,ty).endVertex();
        buffer.vertex((double) x + 0, (double) y + 0, z).uv(tx,ty).endVertex();

        tess.end();
    }



    @Override
    protected void renderTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderTooltip(matrixStack, x, y);

        for(Widget button : buttons) {
            if (button.isMouseOver(x,y) && button instanceof SlotButton) {
                menu.item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
                    if (!((BackpackItemHandler) cap).getFilterHandler().getStackInSlot(((SlotButton)button).slot).isEmpty())
                        renderTooltip(matrixStack, ((BackpackItemHandler) cap).getFilterHandler().getStackInSlot(((SlotButton)button).slot), x, y);
                });
            }
        }
    }

    class SlotButton extends Button {
        public SlotButton(int x, int y, int width, int height, int slotIn, IPressable pressable) {
            super(x,y,width,height,new StringTextComponent(""), pressable);

            this.slot = slotIn;
        }

        public int slot;

        @Override
        public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
            RenderSystem.pushMatrix();
            RenderSystem.color4f(1.0f,1.0f,1.0f,1.0f);
            FontRenderer fontRenderer = Minecraft.getInstance().font;

            boolean hovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;

            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            menu.item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
                BackpackFilterHandler filterHandler = ((BackpackItemHandler) cap).getFilterHandler();

                ItemStack tmp = filterHandler.getStackInSlot(slot);
                itemRenderer.blitOffset = 100F;
                //RenderHelper.enableGUIStandardItemLighting();
                RenderSystem.enableDepthTest();
                RenderHelper.setupFor3DItems();
                itemRenderer.renderAndDecorateItem(tmp, x, y);
                itemRenderer.renderGuiItemDecorations(fontRenderer, tmp, x, y, "");
                itemRenderer.blitOffset = 0F;
            });

            if (hovered)
                fill(stack, x,y,x+width, y+height, -2130706433);

            RenderSystem.popMatrix();
        }
    }


    class SwitchButton extends Button {
        public SwitchButton(int x, int y, String text, boolean initial, Button.IPressable pressable) {
            super(x,y,32,16,new StringTextComponent(""), pressable);
            textKey = text;
            state = initial;
        }

        private final ResourceLocation OFF = new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/switch_off.png");
        private final ResourceLocation ON = new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/switch_on.png");

        public boolean state;
        private final String textKey;

        @Override
        public void renderButton(MatrixStack stack, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
            getMinecraft().getTextureManager().bind(state ? ON : OFF);
            drawTexturedQuad(x, y, width, height,0,0,1,1, 100);
            Minecraft.getInstance().font.draw(stack, I18n.get(textKey), x + 34, y + 4, 0x404040);
        }
    }
}
