package io.github.realyusufismail.bentenmod.client.omnitrix;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.client.alien.AlienHologram;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixHud;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * The Omnitrix dial: a bezel you turn with the scroll wheel or arrow keys. Each step brings the
 * next unlocked alien to the top and pops its hologram up out of the core.
 */
public final class OmnitrixDialScreen extends Screen {
    private static final ResourceLocation ATLAS =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix.png");
    private static final ResourceLocation WHEEL =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix_wheel.png");
    private static final List<String> ATLAS_ORDER = List.of("heatblast", "wildmutt", "diamondhead",
            "xlr8", "greymatter", "fourarms", "stinkfly", "ripjaws", "upgrade", "ghostfreak");
    private static final long OPEN_MS = 220;
    private static final long POP_MS = 320;
    private static final long SLAM_MS = 180;

    private final AlienHologram hologram = new AlienHologram();
    private final long openedAt = Util.getMillis();
    private int selected;
    private boolean selectionInitialised;
    /** Bezel turn in steps; eases toward {@link #targetTurn}, which may go past the list size. */
    private float turn;
    private int targetTurn;
    private long lastFrame = Util.getMillis();
    private long selectedAt = Util.getMillis();
    private long slamAt = -1;
    private ResourceLocation slamAlien;

    public OmnitrixDialScreen() {
        super(Component.translatable("screen.bentenmod.omnitrix"));
    }

    @Override
    protected void init() {
        OmnitrixSnapshot state = localState();
        if (state == null || !state.equipped()) {
            Minecraft client = Minecraft.getInstance();
            if (client.player != null) {
                client.player.displayClientMessage(
                        Component.translatable("message.bentenmod.omnitrix_not_equipped"), true);
            }
            client.setScreen(null);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long now = Util.getMillis();
        float frameSeconds = Math.min(0.1f, (now - lastFrame) / 1000f);
        lastFrame = now;
        turn += (targetTurn - turn) * (1 - (float) Math.exp(-frameSeconds * 14));

        float open = ease(Mth.clamp((now - openedAt) / (float) OPEN_MS, 0, 1));
        graphics.fill(0, 0, width, height, (int) (0xB0 * open) << 24 | 0x04100A);

        OmnitrixSnapshot state = localState();
        List<ResourceLocation> aliens = state == null ? List.of() : state.unlockedAlienIds();
        ResourceLocation active = state == null ? null : state.currentAlien();
        initialiseSelection(aliens, active);

        float radius = radius();
        float cx = width / 2f;
        float cy = centreY(radius);
        float step = aliens.isEmpty() ? 0 : 360f / aliens.size();
        float slam = slamAt < 0 ? 0 : Mth.clamp((now - slamAt) / (float) SLAM_MS, 0, 1);
        float dialScale = (0.75f + 0.25f * open) * (1 - 0.08f * (float) Math.sin(slam * Math.PI));

        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(cx, cy, 0);
        pose.scale(dialScale, dialScale, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, open);
        int size = Math.round(radius * 2);
        int half = size / 2;
        pose.pushPose();
        pose.mulPose(Axis.ZP.rotationDegrees(-turn * step));
        graphics.blit(WHEEL, -half, -half, size, size, 0, 0, 256, 256, 512, 256);
        pose.popPose();
        graphics.blit(WHEEL, -half, -half, size, size, 256, 0, 256, 256, 512, 256);
        renderIcons(graphics, aliens, active, radius, step, open);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        pose.popPose();

        if (!aliens.isEmpty()) {
            ResourceLocation shown = slamAlien != null ? slamAlien : aliens.get(selected);
            renderHologram(graphics, shown, cx, cy, radius, now, open, slam);
            Component name = alienName(shown);
            graphics.drawCenteredString(font, name, (int) cx, (int) (cy + radius * 1.5f), 0x9CFF9C);
        } else {
            graphics.drawCenteredString(font, Component.translatable("screen.bentenmod.no_dna"),
                    (int) cx, (int) cy, 0x9CFF9C);
        }

        long rechargeTicks = rechargeTicks(state);
        if (rechargeTicks > 0) {
            graphics.drawCenteredString(font,
                    Component.translatable("screen.bentenmod.recharging",
                            OmnitrixHud.formatTicks(rechargeTicks)),
                    (int) cx, height - 38, 0xFFFF6565);
        }

        graphics.drawCenteredString(font, title, (int) cx, 8, 0xE8FFE8);
        Component hint =
                Component.translatable(active != null ? "screen.bentenmod.dial_hint_transformed"
                        : "screen.bentenmod.dial_hint");
        graphics.drawCenteredString(font, hint, (int) cx, height - 14, 0x7FBF8A);

        if (slam >= 1) {
            onClose();
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderIcons(GuiGraphics graphics, List<ResourceLocation> aliens,
            ResourceLocation active, float radius, float step, float open) {
        float ring = radius * 1.24f;
        int icon = Math.max(16, Math.round(radius * 0.3f));
        for (int i = 0; i < aliens.size(); i++) {
            double angle = Math.toRadians((i - turn) * step - 90);
            float x = (float) Math.cos(angle) * ring;
            float y = (float) Math.sin(angle) * ring;
            boolean isSelected = i == selected;
            float scale = isSelected ? 1.35f : 0.9f;
            int drawn = Math.round(icon * scale);
            PoseStack pose = graphics.pose();
            pose.pushPose();
            pose.translate(x, y, 0);
            if (isSelected) {
                graphics.fill(-drawn / 2 - 2, -drawn / 2 - 2, drawn / 2 + 2, drawn / 2 + 2,
                        0xCC4AE65C);
                graphics.fill(-drawn / 2 - 1, -drawn / 2 - 1, drawn / 2 + 1, drawn / 2 + 1,
                        0xEE0E1110);
            }
            float shade = isSelected ? 1 : 0.55f;
            RenderSystem.setShaderColor(shade, shade, shade, open);
            int atlasIndex = ATLAS_ORDER.indexOf(aliens.get(i).getPath());
            if (atlasIndex >= 0) {
                graphics.blit(ATLAS, -drawn / 2, -drawn / 2, drawn, drawn, (atlasIndex % 8) * 32,
                        128 + (atlasIndex / 8) * 32, 32, 32, 256, 256);
            }
            if (aliens.get(i).equals(active)) {
                graphics.fill(-3, drawn / 2 + 3, 3, drawn / 2 + 5, 0xFF4AE65C);
            }
            pose.popPose();
        }
        RenderSystem.setShaderColor(1, 1, 1, open);
    }

    private void renderHologram(GuiGraphics graphics, ResourceLocation alien, float cx, float cy,
            float radius, long now, float open, float slam) {
        float pop = easeOutBack(Mth.clamp((now - selectedAt) / (float) POP_MS, 0, 1));
        float flicker =
                0.8f + 0.12f * (float) Math.sin(now / 55.0) + 0.05f * (float) Math.sin(now / 13.0);
        float height = radius * 1.05f * pop * (1 + 0.25f * slam);
        float alpha = Mth.clamp(flicker * open * (1 - slam * 0.6f), 0, 1);
        int beamTop = (int) (cy - height - radius * 0.1f);
        int beamHalf = (int) (radius * 0.32f * pop);
        graphics.fillGradient((int) cx - beamHalf, beamTop, (int) cx + beamHalf, (int) cy,
                0x0040FF60, (int) (0x60 * alpha) << 24 | 0x40FF60);
        float spin = (now % 9000) / 9000f * 360f + 25;
        hologram.render(graphics, alien, cx, cy + radius * 0.2f, height, spin, alpha);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta != 0) {
            turnBy(delta > 0 ? -1 : 1);
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_A -> turnBy(-1);
            case GLFW.GLFW_KEY_RIGHT, GLFW.GLFW_KEY_D -> turnBy(1);
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER, GLFW.GLFW_KEY_SPACE -> transform();
            case GLFW.GLFW_KEY_R -> revert();
            default -> {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (slamAt >= 0) {
            return true;
        }
        OmnitrixSnapshot state = localState();
        List<ResourceLocation> aliens = state == null ? List.of() : state.unlockedAlienIds();
        if (button == 1) {
            revert();
            return true;
        }
        if (button != 0 || aliens.isEmpty()) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        float radius = radius();
        float cx = width / 2f;
        float cy = centreY(radius);
        float step = 360f / aliens.size();
        float ring = radius * 1.24f;
        float hit = Math.max(10, radius * 0.22f);
        for (int i = 0; i < aliens.size(); i++) {
            double angle = Math.toRadians((i - turn) * step - 90);
            float x = cx + (float) Math.cos(angle) * ring;
            float y = cy + (float) Math.sin(angle) * ring;
            if (Math.abs(mouseX - x) < hit && Math.abs(mouseY - y) < hit) {
                if (i == selected) {
                    transform();
                } else {
                    int forward = Math.floorMod(i - selected, aliens.size());
                    turnBy(forward <= aliens.size() / 2 ? forward : forward - aliens.size());
                }
                return true;
            }
        }
        if (Math.hypot(mouseX - cx, mouseY - cy) < radius) {
            transform();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void turnBy(int steps) {
        OmnitrixSnapshot state = localState();
        int count = state == null ? 0 : state.unlockedAlienIds().size();
        if (count == 0 || slamAt >= 0) {
            return;
        }
        targetTurn += steps;
        selected = Math.floorMod(selected + steps, count);
        selectedAt = Util.getMillis();
        Minecraft.getInstance()
            .getSoundManager()
            .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.value(), 1.8f, 0.35f));
    }

    private void transform() {
        OmnitrixSnapshot state = localState();
        if (state == null || state.unlockedAlienIds().isEmpty() || slamAt >= 0) {
            return;
        }
        long rechargingTicks = rechargeTicks(state);
        if (rechargingTicks > 0) {
            Minecraft client = Minecraft.getInstance();
            if (client.player != null) {
                client.player.displayClientMessage(
                        Component.translatable("message.bentenmod.omnitrix_recharging",
                                OmnitrixHud.formatTicks(rechargingTicks)),
                        true);
            }
            return;
        }
        slamAlien = state.unlockedAlienIds().get(selected);
        slamAt = Util.getMillis();
        Minecraft.getInstance()
            .getSoundManager()
            .play(SimpleSoundInstance.forUI(SoundEvents.BEACON_POWER_SELECT, 1.4f));
        OmnitrixClientHooks.select(slamAlien);
    }

    private void revert() {
        OmnitrixSnapshot state = localState();
        if (state == null || state.currentAlien() == null) {
            return;
        }
        Minecraft.getInstance()
            .getSoundManager()
            .play(SimpleSoundInstance.forUI(SoundEvents.BEACON_DEACTIVATE, 1.6f));
        OmnitrixClientHooks.revert();
        onClose();
    }

    private void initialiseSelection(List<ResourceLocation> aliens, ResourceLocation active) {
        if (selectionInitialised || aliens.isEmpty()) {
            return;
        }
        selectionInitialised = true;
        int index = active == null ? 0 : Math.max(0, aliens.indexOf(active));
        selected = index;
        targetTurn = index;
        turn = index;
    }

    private float radius() {
        return Math.max(40, Math.min(Math.min(height * 0.26f, width * 0.22f), 96));
    }

    private float centreY(float radius) {
        return height / 2f + radius * 0.12f;
    }

    private static float ease(float t) {
        return 1 - (1 - t) * (1 - t) * (1 - t);
    }

    private static float easeOutBack(float t) {
        float c = 1.9f;
        float u = t - 1;
        return 1 + (c + 1) * u * u * u + c * u * u;
    }

    private OmnitrixSnapshot localState() {
        if (Minecraft.getInstance().player == null) {
            return null;
        }
        return ClientOmnitrixState.get(Minecraft.getInstance().player.getUUID());
    }

    private static long rechargeTicks(OmnitrixSnapshot state) {
        Minecraft client = Minecraft.getInstance();
        return state == null || client.level == null ? 0
                : Math.max(0, state.rechargeUntil() - client.level.getGameTime());
    }

    private Component alienName(ResourceLocation alien) {
        return Component.translatable("alien." + alien.getNamespace() + "." + alien.getPath());
    }
}
