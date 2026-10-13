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

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.OmnitrixNetwork;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixFlash;
import io.github.realyusufismail.bentenmod.client.model.KraabModel;
import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnnitrixCrafterScreen;
import io.github.realyusufismail.bentenmod.core.omnitrix.OmnitrixDialScreen;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.MenuTypeInit;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import io.github.realyusufismail.bentenmod.client.alien.AlienPlayerReplacementRenderer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import io.github.realyusufismail.bentenmod.recipebook.RecipeCategoriesInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Forge client registration. Kept out of {@link io.github.realyusufismail.bentenmod.BenTenModForge}
 * so dedicated servers never load rendering classes.
 */
@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public final class BenTenModForgeClient {
    private static final KeyMapping OPEN_OMNITRIX =
            new KeyMapping("key.bentenmod.omnitrix", GLFW.GLFW_KEY_G, "key.categories.bentenmod");

    private BenTenModForgeClient() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientOmnitrixState.installLocalFormTransition(playerId -> {
                AlienPlayerReplacementRenderer.forgetPlayer(playerId);
                Minecraft client = Minecraft.getInstance();
                if (client.player != null && client.player.getUUID().equals(playerId)) {
                    OmnitrixFlash.trigger();
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
            MenuScreens.register(MenuTypeInit.OmnitrixCrafterMenuType.get(),
                    OmnnitrixCrafterScreen::new);
            OmnitrixClientHooks.install(
                    () -> Minecraft.getInstance().setScreen(new OmnitrixDialScreen()),
                    OmnitrixNetwork::requestTransform,
                    () -> OmnitrixNetwork.requestTransform(null));
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onClientTick);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onClientLogout);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onFlashOverlay);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onRenderPlayer);
        });
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_OMNITRIX);
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && OPEN_OMNITRIX.consumeClick()) {
            OmnitrixClientHooks.openDial();
        }
        if (event.phase == TickEvent.Phase.END) {
            OmnitrixFlash.tick();
        }
    }

    private static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        AlienPlayerReplacementRenderer.clearPlayerStates();
        ClientOmnitrixState.clear();
    }

    private static void onFlashOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) {
            return;
        }
        float alpha = OmnitrixFlash.alpha(event.getPartialTick());
        if (alpha <= 0) {
            return;
        }
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, alpha);
        event.getGuiGraphics()
            .blit(new net.minecraft.resources.ResourceLocation(BenTenMod.MOD_ID,
                    "textures/misc/omnitrix_flash.png"), 0, 0, 0, 0, width, height, 256, 256);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
    }

    private static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!(event.getEntity() instanceof net.minecraft.client.player.AbstractClientPlayer player)
                || !AlienPlayerReplacementRenderer.isTransformed(player)) {
            return;
        }
        AlienPlayerReplacementRenderer.get()
            .render(player, player.getYRot(), event.getPartialTick(), event.getPoseStack(),
                    event.getMultiBufferSource(), event.getPackedLight());
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        RecipeCategoriesInit.registerRecipeCategories(event);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypesInit.CRAB_ENTITY.get(), KraabRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.CRAB_BOLT_ENTITY.get(), NoopRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(
            EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.KRAAB, KraabModel::createLayer);
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        for (ModSpawnEggItem egg : ModSpawnEggItem.modEggs()) {
            event.register((stack, tintIndex) -> egg.getColor(tintIndex), egg);
        }
    }
}
