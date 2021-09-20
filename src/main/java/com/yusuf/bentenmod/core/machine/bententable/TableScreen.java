/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.core.machine.bententable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.common.LangKeys;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

import java.net.NetworkInterface;

public class TableScreen extends AbstractContainerScreen<TableContainer> {
    public static final ResourceLocation SCREEN_ID = new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/table_screen.png");

    public TableScreen(TableContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        minecraft.getTextureManager().getTexture(SCREEN_ID);

        // BenTenMod Screen
        blit(p_97787_, leftPos, topPos, 0, 0, 176, 179);

        blit(p_97787_, leftPos + 97, topPos + 38, 179, 25, menu.getProcess() + 1, 17);
    }


    //CTRL + O > OVERRIDE METHODS

    @Override
    public void render(PoseStack matrix, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        renderBackground(matrix);
        super.render(matrix, p_230430_2_, p_230430_3_, p_230430_4_);
        renderTooltip(matrix, p_230430_2_, p_230430_3_);
    }

    /*
     * CTRL + O > OVERRIDE METHODS
     * 4210752 is the default number, can't be changed
     */
    @Override
    protected void renderLabels(PoseStack matrix, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrix, this.playerInventoryTitle, 7, 81, 4210752);
        font.draw(matrix, LangKeys.TABLE_SCREEN, 5, 10, 4210752);
    }
}