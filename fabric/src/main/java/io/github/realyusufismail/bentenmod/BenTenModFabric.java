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
import io.github.realyusufismail.bentenmod.data.CodecJsonReloadListener;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixInteractions;
import io.github.realyusufismail.bentenmod.worldgen.ModOrePlacements;
import io.github.realyusufismail.bentenmod.worldgen.OmnitrixCrashSiteWorldgen;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.profiling.ProfilerFiller;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/** Fabric entry point. Game content lives in common code; this wires it to Fabric API. */
public class BenTenModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BenTenMod.init();
        BenTenMod.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
        BenTenMod.commonSetup();
        addOres();
        OmnitrixNetwork.register();
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            return OmnitrixInteractions.scanBlock(player,
                    world.getBlockState(hitResult.getBlockPos()));
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            return OmnitrixInteractions.scanEntity(player, entity);
        });

        BenTenMod.reloadListeners().forEach(BenTenModFabric::registerReloadListener);
        ServerTickEvents.END_SERVER_TICK
            .register(server -> server.getPlayerList().getPlayers().forEach(player -> {
                boolean changed = OmnitrixService.tick(player, server.overworld().getGameTime());
                OmnitrixService.tickHunterSpawn(player);
                AbilityTicker.tick(player);
                if (changed) {
                    OmnitrixNetwork.syncTrackers(player);
                }
            }));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> OmnitrixNetwork
            .sendToPlayer(handler.player, OmnitrixService.onJoin(handler.player)));
        ServerPlayConnectionEvents.DISCONNECT
            .register((handler, server) -> AbilityTicker.forget(handler.player));
        EntityTrackingEvents.START_TRACKING.register((entity, player) -> {
            if (entity instanceof net.minecraft.server.level.ServerPlayer target) {
                OmnitrixNetwork.sendToPlayer(player, OmnitrixService.trackingSnapshot(target));
            }
        });
        ServerPlayerEvents.AFTER_RESPAWN
            .register((oldPlayer, newPlayer, alive) -> OmnitrixNetwork.syncTrackers(newPlayer));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD
            .register((player, origin, destination) -> OmnitrixNetwork.syncTrackers(player));
    }

    /** Fabric needs an id on every reload listener; it wraps the shared one. */
    private static void registerReloadListener(CodecJsonReloadListener<?> listener) {
        ResourceLocation id = BenTenMod.getModIdAndName(listener.folder());
        ResourceManagerHelper.get(PackType.SERVER_DATA)
            .registerReloadListener(new IdentifiableResourceReloadListener() {
                @Override
                public ResourceLocation getFabricId() {
                    return id;
                }

                @Override
                public CompletableFuture<Void> reload(
                        PreparableReloadListener.PreparationBarrier barrier,
                        ResourceManager manager, ProfilerFiller prepareProfiler,
                        ProfilerFiller applyProfiler, Executor background, Executor game) {
                    return listener.reload(barrier, manager, prepareProfiler, applyProfiler,
                            background, game);
                }
            });
    }

    /** Same placement as the Forge biome modifiers generated by the data run. */
    private static void addOres() {
        ModOrePlacements.OVERWORLD_ORES
            .forEach(ore -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                    GenerationStep.Decoration.UNDERGROUND_ORES, ore));
        ModOrePlacements.NETHER_ORES
            .forEach(ore -> BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                    GenerationStep.Decoration.UNDERGROUND_ORES, ore));
        BiomeModifications.addFeature(BiomeSelectors.tag(OmnitrixCrashSiteWorldgen.BIOMES),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                OmnitrixCrashSiteWorldgen.PLACED_FEATURE);
    }
}
