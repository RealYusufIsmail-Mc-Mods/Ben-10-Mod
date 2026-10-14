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
package io.github.realyusufismail.bentenmod.client;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.OmnitrixNetwork;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixFlash;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixHud;
import io.github.realyusufismail.bentenmod.client.model.KraabModel;
import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxDroneRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.NullVoidPrisonerRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.NullGuardianRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.LepidopterranRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.PrisonWardenRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.ZsSkayrRenderer;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnnitrixCrafterScreen;
import io.github.realyusufismail.bentenmod.core.omnitrix.OmnitrixDialScreen;
import io.github.realyusufismail.bentenmod.core.omnitrix.OmnitrixGuideScreen;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.MenuTypeInit;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import io.github.realyusufismail.bentenmod.client.alien.AlienPlayerReplacementRenderer;
import io.github.realyusufismail.bentenmod.client.OmnitrixWristLayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import io.github.realyusufismail.bentenmod.recipebook.RecipeCategoriesInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderArmEvent;
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
    private static final KeyMapping USE_ALIEN_POWER = new KeyMapping(
            "key.bentenmod.use_alien_power", GLFW.GLFW_KEY_V, "key.categories.bentenmod");
    private static final KeyMapping REVERT_TO_HUMAN = new KeyMapping(
            "key.bentenmod.revert_to_human", GLFW.GLFW_KEY_H, "key.categories.bentenmod");
    private static boolean useWasDown;

    private BenTenModForgeClient() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
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
            MenuScreens.register(MenuTypeInit.OmnitrixCrafterMenuType.get(),
                    OmnnitrixCrafterScreen::new);
            OmnitrixClientHooks.install(
                    () -> Minecraft.getInstance().setScreen(new OmnitrixDialScreen()),
                    OmnitrixNetwork::requestTransform,
                    () -> OmnitrixNetwork.requestTransform(null));
            OmnitrixClientHooks
                .installGuide(() -> Minecraft.getInstance().setScreen(new OmnitrixGuideScreen()));
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onClientTick);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onClientLogout);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onFlashOverlay);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onRenderPlayer);
            MinecraftForge.EVENT_BUS.addListener(BenTenModForgeClient::onRenderArm);
        });
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_OMNITRIX);
        event.register(USE_ALIEN_POWER);
        event.register(REVERT_TO_HUMAN);
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (OPEN_OMNITRIX.consumeClick()) {
                OmnitrixClientHooks.openDial();
            }
            var client = Minecraft.getInstance();
            if (USE_ALIEN_POWER.consumeClick() && isTransformed(client)) {
                OmnitrixNetwork.requestPower();
            }
            if (REVERT_TO_HUMAN.consumeClick() && isTransformed(client)) {
                OmnitrixNetwork.requestTransform(null);
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
        }
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

    private static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        useWasDown = false;
        AlienPlayerReplacementRenderer.clearPlayerStates();
        ClientOmnitrixState.clear();
        OmnitrixHud.reset();
    }

    private static void onFlashOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) {
            return;
        }
        float alpha = OmnitrixFlash.alpha(event.getPartialTick());
        if (alpha > 0) {
            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();
            if (OmnitrixFlash.isTimeoutFlash()) {
                event.getGuiGraphics()
                    .fill(0, 0, width, height, (int) (alpha * 92) << 24 | 0xC10C12);
            } else {
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(1, 1, 1, alpha);
                event.getGuiGraphics()
                    .blit(new net.minecraft.resources.ResourceLocation(BenTenMod.MOD_ID,
                            "textures/misc/omnitrix_flash.png"), 0, 0, 0, 0, width, height, 256,
                            256);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
            }
        }
        OmnitrixHud.render(event.getGuiGraphics(), Minecraft.getInstance());
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

    private static void onRenderArm(RenderArmEvent event) {
        if (AlienPlayerReplacementRenderer.get()
            .renderFirstPersonArm(event.getPlayer(), event.getArm(), event.getPoseStack(),
                    event.getMultiBufferSource(), event.getPackedLight())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        RecipeCategoriesInit.registerRecipeCategories(event);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypesInit.CRAB_ENTITY.get(), KraabRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.CRAB_BOLT_ENTITY.get(), NoopRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.VILGAX_DRONE_ENTITY.get(),
                VilgaxDroneRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.NULL_VOID_PRISONER.get(),
                NullVoidPrisonerRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.ZS_SKAYR_ENTITY.get(), ZsSkayrRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.NULL_GUARDIAN.get(),
                NullGuardianRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.LEPIDOPTERAN.get(),
                LepidopterranRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.PRISON_WARDEN.get(),
                PrisonWardenRenderer::new);
        event.registerEntityRenderer(EntityTypesInit.LEPIDOPTERAN_SLIME.get(),
                ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(
            EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.KRAAB, KraabModel::createLayer);
    }

    @SubscribeEvent
    public static void onAddPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            PlayerRenderer renderer = event.getPlayerSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new OmnitrixWristLayer(renderer));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        for (ModSpawnEggItem egg : ModSpawnEggItem.modEggs()) {
            event.register((stack, tintIndex) -> egg.getColor(tintIndex), egg);
        }
    }
}
