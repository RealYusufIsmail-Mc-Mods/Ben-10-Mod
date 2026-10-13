package io.github.realyusufismail.bentenmod.core.omnitrix;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/** The Omnitrix dial: select an unlocked alien or revert the current form. */
public final class OmnitrixDialScreen extends Screen {
    private static final ResourceLocation ATLAS =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix.png");
    private static final List<String> ATLAS_ORDER = List.of("heatblast", "wildmutt", "diamondhead",
            "xlr8", "greymatter", "fourarms", "stinkfly", "ripjaws", "upgrade", "ghostfreak");
    private static final int ICON_COLUMNS = 4;
    private static final int CELL = 36;

    public OmnitrixDialScreen() {
        super(Component.translatable("screen.bentenmod.omnitrix"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xB0081110);
        int dialX = width / 2 - 40;
        int dialY = 8;
        graphics.blit(ATLAS, dialX, dialY, 0, 0, 80, 80, 128, 128, 256, 256);
        graphics.drawCenteredString(font, title, width / 2, 2, 0xE8FFE8);

        OmnitrixSnapshot state = localState();
        List<ResourceLocation> unlocked = state == null ? List.of() : state.unlockedAlienIds();
        int rows = Math.max(1, (unlocked.size() + ICON_COLUMNS - 1) / ICON_COLUMNS);
        int startX = width / 2 - ICON_COLUMNS * CELL / 2;
        int iconY = dialY + 88;
        ResourceLocation active = state == null ? null : state.currentAlien();
        for (int i = 0; i < unlocked.size(); i++) {
            ResourceLocation alien = unlocked.get(i);
            int x = startX + (i % ICON_COLUMNS) * CELL;
            int y = iconY + (i / ICON_COLUMNS) * 38;
            if (alien.equals(active)) {
                graphics.blit(ATLAS, x, y, 128, 0, CELL, CELL, 256, 256);
            }
            int atlasIndex = ATLAS_ORDER.indexOf(alien.getPath());
            if (atlasIndex >= 0) {
                graphics.blit(ATLAS, x + 2, y + 2, (atlasIndex % 8) * 32,
                        128 + (atlasIndex / 8) * 32, 32, 32, 256, 256);
            }
            if (mouseX >= x && mouseX < x + CELL && mouseY >= y && mouseY < y + CELL) {
                graphics.renderTooltip(font, alienName(alien), mouseX, mouseY);
            }
        }

        int buttonY = iconY + rows * 38 + 3;
        int buttonX = width / 2 - 42;
        boolean transformed = active != null;
        graphics.fill(buttonX, buttonY, buttonX + 84, buttonY + 20,
                transformed ? 0xCC37683A : 0x88404444);
        graphics.drawCenteredString(font, Component.translatable("screen.bentenmod.revert"),
                width / 2, buttonY + 6, transformed ? 0xF0FFF0 : 0x888888);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        OmnitrixSnapshot state = localState();
        if (state == null) {
            return true;
        }
        List<ResourceLocation> unlocked = state.unlockedAlienIds();
        int startX = width / 2 - ICON_COLUMNS * CELL / 2;
        int iconY = 8 + 88;
        for (int i = 0; i < unlocked.size(); i++) {
            int x = startX + (i % ICON_COLUMNS) * CELL;
            int y = iconY + (i / ICON_COLUMNS) * 38;
            if (mouseX >= x && mouseX < x + CELL && mouseY >= y && mouseY < y + CELL) {
                OmnitrixClientHooks.select(unlocked.get(i));
                onClose();
                return true;
            }
        }
        int buttonX = width / 2 - 42;
        int rows = Math.max(1, (unlocked.size() + ICON_COLUMNS - 1) / ICON_COLUMNS);
        int buttonY = iconY + rows * 38 + 3;
        if (state.currentAlien() != null && mouseX >= buttonX && mouseX < buttonX + 84
                && mouseY >= buttonY && mouseY < buttonY + 20) {
            OmnitrixClientHooks.revert();
            onClose();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private OmnitrixSnapshot localState() {
        if (Minecraft.getInstance().player == null) {
            return null;
        }
        return ClientOmnitrixState.get(Minecraft.getInstance().player.getUUID());
    }

    private Component alienName(ResourceLocation alien) {
        return Component.translatable("alien." + alien.getNamespace() + "." + alien.getPath());
    }
}
