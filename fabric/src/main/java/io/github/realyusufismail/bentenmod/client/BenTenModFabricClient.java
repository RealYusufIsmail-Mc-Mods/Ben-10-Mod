/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.client;

import io.github.realyusufismail.bentenmod.client.model.KraabModel;
import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxDroneRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.NullVoidPrisonerRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.NullGuardianRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.LepidopterranRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.PrisonWardenRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.ZsSkayrRenderer;
import io.github.realyusufismail.bentenmod.client.alien.AlienPlayerReplacementRenderer;
import io.github.realyusufismail.bentenmod.client.OmnitrixWristLayer;
import io.github.realyusufismail.bentenmod.OmnitrixNetwork;
import io.github.realyusufismail.bentenmod.client.omnitrix.OmnitrixDialScreen;
import io.github.realyusufismail.bentenmod.client.omnitrix.OmnitrixGuideScreen;
import io.github.realyusufismail.bentenmod.client.screen.OmnitrixCrafterScreen;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.MenuTypeInit;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixFlash;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixHud;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.phys.HitResult;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.network.FriendlyByteBuf;
import org.lwjgl.glfw.GLFW;

/** Fabric client registration: screens, entity renderers, model layers and item colours. */
public class BenTenModFabricClient implements ClientModInitializer {
    private static boolean useWasDown;
    private static final KeyMapping OPEN_OMNITRIX = KeyBindingHelper.registerKeyBinding(
            new KeyMapping("key.bentenmod.omnitrix", GLFW.GLFW_KEY_G, "key.categories.bentenmod"));
    private static final KeyMapping USE_ALIEN_POWER =
            KeyBindingHelper.registerKeyBinding(new KeyMapping("key.bentenmod.use_alien_power",
                    GLFW.GLFW_KEY_V, "key.categories.bentenmod"));
    private static final KeyMapping REVERT_TO_HUMAN =
            KeyBindingHelper.registerKeyBinding(new KeyMapping("key.bentenmod.revert_to_human",
                    GLFW.GLFW_KEY_H, "key.categories.bentenmod"));

    @Override
    public void onInitializeClient() {
        ClientOmnitrixState.installLocalFormTransition((playerId, timedOut) -> {
            AlienPlayerReplacementRenderer.forgetPlayer(playerId);
            Minecraft client = Minecraft.getInstance();
            if (client.player != null && client.player.getUUID().equals(playerId)) {
                OmnitrixFlash.trigger(timedOut);
            }
        });
        ClientOmnitrixState.installDimensionRefresh(playerId -> {
            Minecraft client = Minecraft.getInstance();
            if (client.level != null) {
                var player = client.level.getPlayerByUUID(playerId);
                if (player != null) {
                    player.refreshDimensions();
                }
            }
        });
        OmnitrixClientHooks.install(
                () -> Minecraft.getInstance().setScreen(new OmnitrixDialScreen()),
                BenTenModFabricClient::requestTransform, () -> requestTransform(null));
        OmnitrixClientHooks
            .installGuide(() -> Minecraft.getInstance().setScreen(new OmnitrixGuideScreen()));
        ClientPlayNetworking.registerGlobalReceiver(OmnitrixNetwork.STATE_UPDATE,
                (client, handler, buffer, responseSender) -> {
                    var snapshot = OmnitrixNetwork.decode(buffer);
                    client.execute(() -> ClientOmnitrixState.accept(snapshot));
                });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_OMNITRIX.consumeClick()) {
                OmnitrixClientHooks.openDial();
            }
            if (USE_ALIEN_POWER.consumeClick() && isTransformed(client)) {
                ClientPlayNetworking.send(OmnitrixNetwork.POWER_REQUEST, PacketByteBufs.empty());
            }
            if (REVERT_TO_HUMAN.consumeClick() && isTransformed(client)) {
                requestTransform(null);
                if (client.screen instanceof OmnitrixDialScreen) {
                    client.setScreen(null);
                }
            }
            boolean useDown = client.options.keyUse.isDown();
            if (useDown && !useWasDown && client.player != null && client.screen == null
                    && !client.player.isShiftKeyDown() && client.player.getMainHandItem().isEmpty()
                    && isDialAccessTarget(client.hitResult)
                    && isOmnitrixEquipped(client.player.getUUID())) {
                OmnitrixClientHooks.openDial();
            }
            useWasDown = useDown;
            OmnitrixHud.tick(client);
            OmnitrixFlash.tick();
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            AlienPlayerReplacementRenderer.clearPlayerStates();
            ClientOmnitrixState.clear();
            OmnitrixHud.reset();
            useWasDown = false;
        });
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            float alpha = OmnitrixFlash.alpha(tickDelta);
            if (alpha > 0) {
                int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
                int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
                if (OmnitrixFlash.isTimeoutFlash()) {
                    graphics.fill(0, 0, width, height, (int) (alpha * 92) << 24 | 0xC10C12);
                } else {
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1, 1, 1, alpha);
                    graphics.blit(
                            new net.minecraft.resources.ResourceLocation("bentenmod",
                                    "textures/misc/omnitrix_flash.png"),
                            0, 0, 0, 0, width, height, 256, 256);
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    RenderSystem.disableBlend();
                }
            }
            OmnitrixHud.render(graphics, Minecraft.getInstance());
        });
        MenuScreens.register(MenuTypeInit.OmnitrixCrafterMenuType.get(),
                OmnitrixCrafterScreen::new);
        EntityRendererRegistry.register(EntityTypesInit.CRAB_ENTITY.get(), KraabRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.CRAB_BOLT_ENTITY.get(), NoopRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.VILGAX_DRONE_ENTITY.get(),
                VilgaxDroneRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.NULL_VOID_PRISONER.get(),
                NullVoidPrisonerRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.ZS_SKAYR_ENTITY.get(),
                ZsSkayrRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.NULL_GUARDIAN.get(),
                NullGuardianRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.LEPIDOPTERAN.get(),
                LepidopterranRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.PRISON_WARDEN.get(),
                PrisonWardenRenderer::new);
        EntityRendererRegistry.register(EntityTypesInit.LEPIDOPTERAN_SLIME.get(),
                ThrownItemRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.KRAAB, KraabModel::createLayer);
        LivingEntityFeatureRendererRegistrationCallback.EVENT
            .register((entityType, entityRenderer, registrationHelper, context) -> {
                if (entityRenderer instanceof PlayerRenderer renderer) {
                    registrationHelper.register(new OmnitrixWristLayer(renderer));
                }
            });
        for (ModSpawnEggItem egg : ModSpawnEggItem.modEggs()) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> egg.getColor(tintIndex), egg);
        }
    }

    private static void requestTransform(net.minecraft.resources.ResourceLocation alien) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeUtf(alien == null ? "" : alien.toString(), 256);
        ClientPlayNetworking.send(OmnitrixNetwork.DIAL_REQUEST, buffer);
    }

    private static boolean isDialAccessTarget(HitResult hitResult) {
        // Air only: right-clicking a chest or crafter must keep opening that block's menu.
        return hitResult != null && hitResult.getType() == HitResult.Type.MISS;
    }

    private static boolean isOmnitrixEquipped(java.util.UUID playerId) {
        var snapshot = ClientOmnitrixState.get(playerId);
        return snapshot != null && snapshot.equipped();
    }

    private static boolean isTransformed(Minecraft client) {
        if (client.player == null) {
            return false;
        }
        var snapshot = ClientOmnitrixState.get(client.player.getUUID());
        return snapshot != null && snapshot.currentAlien() != null;
    }
}
