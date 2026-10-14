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

package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.ability.AbilityTicker;
import io.github.realyusufismail.bentenmod.data.DataGenerators;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixInteractions;
import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

/** Forge entry point. Game content lives in common code; this wires it to Forge's events. */
@Mod(BenTenMod.MOD_ID)
public class BenTenModForge {

    @SuppressWarnings("removal")
    public BenTenModForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BenTenMod.init();
        OmnitrixNetwork.register();

        bus.addListener(this::commonSetup);
        bus.addListener(this::onEntityAttributes);
        bus.addListener(DataGenerators::gatherData);

        MinecraftForge.EVENT_BUS.addListener(this::onAddReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedOut);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerRespawn);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerChangedDimension);
        MinecraftForge.EVENT_BUS.addListener(this::onStartTracking);
        MinecraftForge.EVENT_BUS.addListener(this::onEntitySize);
        MinecraftForge.EVENT_BUS.addListener(this::onOmnitrixEntityInteractSpecific);
        MinecraftForge.EVENT_BUS.addListener(this::onOmnitrixEntityInteract);
        MinecraftForge.EVENT_BUS.addListener(this::onOmnitrixBlockInteract);
        GeckoLib.shadowInit();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(BenTenMod::commonSetup);
    }

    private void onAddReloadListeners(AddReloadListenerEvent event) {
        BenTenMod.reloadListeners().forEach(event::addListener);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player) {
            boolean transformedStateChanged = OmnitrixService.tick(player,
                    player.serverLevel().getServer().overworld().getGameTime());
            AbilityTicker.tick(player);
            if (transformedStateChanged) {
                OmnitrixNetwork.syncTrackers(player);
            }
        }
    }

    private void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AbilityTicker.forget(player);
        }
    }

    private void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            OmnitrixNetwork.sendToPlayer(player, OmnitrixService.onJoin(player));
        }
    }

    private void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            OmnitrixNetwork.syncTrackers(player);
        }
    }

    private void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            OmnitrixNetwork.syncTrackers(player);
        }
    }

    private void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof ServerPlayer tracker
                && event.getTarget() instanceof ServerPlayer target) {
            OmnitrixNetwork.sendToPlayer(tracker, OmnitrixService.trackingSnapshot(target));
        }
    }

    private void onOmnitrixEntityInteract(
            net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        InteractionResult result =
                OmnitrixInteractions.scanEntity(event.getEntity(), event.getTarget());
        if (result.consumesAction()) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    private void onOmnitrixEntityInteractSpecific(
            net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        InteractionResult result =
                OmnitrixInteractions.scanEntity(event.getEntity(), event.getTarget());
        if (result.consumesAction()) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    private void onOmnitrixBlockInteract(
            net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        InteractionResult result = OmnitrixInteractions.scanBlock(event.getEntity(),
                event.getLevel().getBlockState(event.getPos()));
        if (result.consumesAction()) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    private void onEntityAttributes(EntityAttributeCreationEvent event) {
        BenTenMod.registerEntityAttributes(event::put);
    }

    private void onEntitySize(EntityEvent.Size event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        float width;
        float height;
        if (player instanceof ServerPlayer serverPlayer) {
            var definition = OmnitrixService.activeDefinition(serverPlayer).orElse(null);
            if (definition == null) {
                return;
            }
            width = definition.width();
            height = definition.height();
        } else {
            OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
            if (snapshot == null || snapshot.currentAlien() == null || snapshot.width() <= 0
                    || snapshot.height() <= 0) {
                return;
            }
            width = snapshot.width();
            height = snapshot.height();
        }
        event.setNewSize(EntityDimensions.scalable(width, height), true);
    }
}
