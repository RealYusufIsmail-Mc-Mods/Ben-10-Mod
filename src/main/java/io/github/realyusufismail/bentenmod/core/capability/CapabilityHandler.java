/*
 * Copyright 2026 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.bentenmod.core.capability;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import io.github.realyusufismail.bentenmod.core.network.SSyncOmnitrixPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE)
public class CapabilityHandler {

    @CapabilityInject(IOmnitrixData.class)
    public static Capability<IOmnitrixData> OMNITRIX_CAP = null;

    private static final ResourceLocation OMNITRIX_CAP_KEY = new ResourceLocation(BenTenMod.MOD_ID, "omnitrix_data");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            OmnitrixDataProvider provider = new OmnitrixDataProvider();
            event.addCapability(OMNITRIX_CAP_KEY, provider);
            event.addListener(provider::invalidate);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(OMNITRIX_CAP).ifPresent(oldData -> event.getPlayer()
                .getCapability(OMNITRIX_CAP)
                .ifPresent(newData -> {
                    if (event.isWasDeath()) {
                        // On death: preserve unlocked aliens but clear transformation state
                        newData.deserializeNBT(oldData.serializeNBT());
                        newData.revert();
                    } else {
                        // On dimension change: copy everything
                        newData.copyFrom(oldData);
                    }
                }));
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
            serverPlayer
                    .getCapability(OMNITRIX_CAP)
                    .ifPresent(data -> PacketHandler.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> serverPlayer),
                            new SSyncOmnitrixPacket(data.serializeNBT(), serverPlayer.getId())));
        }
    }
}
