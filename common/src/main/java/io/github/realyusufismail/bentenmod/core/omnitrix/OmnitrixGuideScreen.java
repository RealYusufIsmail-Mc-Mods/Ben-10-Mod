/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.core.omnitrix;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public final class OmnitrixGuideScreen extends Screen {
    private static final ResourceLocation PAGE =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix_guide.png");
    private static final int PAGE_SIZE = 192;
    private static final int BUTTON_WIDTH = 23;
    private static final int BUTTON_HEIGHT = 13;
    private static final List<String> CHAPTERS = List.of("getting_started", "finding_the_omnitrix",
            "equipping_the_dial", "aliens_and_dna", "timeout_and_recharge", "ores_and_armour",
            "bosses", "null_void", "master_control");

    private int page;

    public OmnitrixGuideScreen() {
        super(Component.translatable("screen.bentenmod.guide"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xB0000000);
        int x = (width - PAGE_SIZE) / 2;
        int y = (height - PAGE_SIZE) / 2;
        graphics.blit(PAGE, x, y, 0, 0, PAGE_SIZE, PAGE_SIZE, 256, 256);

        String chapter = CHAPTERS.get(page);
        graphics.drawCenteredString(font,
                Component.translatable("guide.bentenmod.chapter." + chapter + ".title"),
                x + PAGE_SIZE / 2, y + 17, 0x26322B);
        List<net.minecraft.util.FormattedCharSequence> lines = font
            .split(Component.translatable("guide.bentenmod.chapter." + chapter + ".body"), 154);
        int lineY = y + 39;
        for (int index = 0; index < Math.min(lines.size(), 12); index++) {
            graphics.drawString(font, lines.get(index), x + 19, lineY, 0x393D36, false);
            lineY += 10;
        }
        graphics.drawCenteredString(font,
                Component.translatable("guide.bentenmod.page", page + 1, CHAPTERS.size()),
                x + PAGE_SIZE / 2, y + PAGE_SIZE - 15, 0x4A5549);

        int buttonY = y + PAGE_SIZE - 31;
        int previousX = x + 13;
        int nextX = x + PAGE_SIZE - 13 - BUTTON_WIDTH;
        boolean previousHovered = contains(mouseX, mouseY, previousX, buttonY);
        boolean nextHovered = contains(mouseX, mouseY, nextX, buttonY);
        graphics.blit(PAGE, previousX, buttonY, previousHovered ? 23 : 0, 205, BUTTON_WIDTH,
                BUTTON_HEIGHT, 256, 256);
        graphics.blit(PAGE, nextX, buttonY, nextHovered ? 23 : 0, 192, BUTTON_WIDTH, BUTTON_HEIGHT,
                256, 256);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT || keyCode == GLFW.GLFW_KEY_A) {
            page = Math.max(0, page - 1);
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_RIGHT || keyCode == GLFW.GLFW_KEY_D) {
            page = Math.min(CHAPTERS.size() - 1, page + 1);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        int x = (width - PAGE_SIZE) / 2;
        int y = (height - PAGE_SIZE) / 2 + PAGE_SIZE - 31;
        if (contains(mouseX, mouseY, x + 13, y)) {
            page = Math.max(0, page - 1);
            return true;
        }
        if (contains(mouseX, mouseY, x + PAGE_SIZE - 13 - BUTTON_WIDTH, y)) {
            page = Math.min(CHAPTERS.size() - 1, page + 1);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private static boolean contains(double mouseX, double mouseY, int x, int y) {
        return mouseX >= x && mouseX < x + BUTTON_WIDTH && mouseY >= y
                && mouseY < y + BUTTON_HEIGHT;
    }
}
