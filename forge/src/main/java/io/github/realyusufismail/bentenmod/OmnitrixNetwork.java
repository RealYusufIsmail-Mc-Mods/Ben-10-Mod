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
package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Forge's small state and dial channel. All transformation decisions stay on the server. */
public final class OmnitrixNetwork {
    private static final String PROTOCOL = "5";
    private static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(BenTenMod.getModIdAndName("omnitrix"), () -> PROTOCOL,
                    PROTOCOL::equals, PROTOCOL::equals);

    private OmnitrixNetwork() {}

    public static void register() {
        CHANNEL.registerMessage(0, DialRequest.class, DialRequest::encode, DialRequest::decode,
                DialRequest::handle);
        CHANNEL.registerMessage(1, StateUpdate.class, StateUpdate::encode, StateUpdate::decode,
                StateUpdate::handle);
        CHANNEL.registerMessage(2, PowerRequest.class, PowerRequest::encode, PowerRequest::decode,
                PowerRequest::handle);
        CHANNEL.registerMessage(3, PortalTravelEffect.class, PortalTravelEffect::encode,
                PortalTravelEffect::decode, PortalTravelEffect::handle);
    }

    public static void sendToPlayer(ServerPlayer player, OmnitrixSnapshot snapshot) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new StateUpdate(snapshot));
    }

    public static void syncTrackers(ServerPlayer player) {
        sendToPlayer(player, OmnitrixService.snapshot(player));
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                new StateUpdate(OmnitrixService.trackingSnapshot(player)));
    }

    public static void requestTransform(ResourceLocation alien) {
        CHANNEL.sendToServer(new DialRequest(alien == null ? "" : alien.toString()));
    }

    public static void requestPower() {
        CHANNEL.sendToServer(new PowerRequest());
    }

    public static void sendPortalTravelEffect(ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new PortalTravelEffect());
    }

    private record PortalTravelEffect() {
        private static void encode(PortalTravelEffect message, FriendlyByteBuf buffer) {}

        private static PortalTravelEffect decode(FriendlyByteBuf buffer) {
            return new PortalTravelEffect();
        }

        private static void handle(PortalTravelEffect message,
                Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(OmnitrixClientHooks::playPortalTravel);
            context.setPacketHandled(true);
        }
    }

    private record DialRequest(String alien) {
        private static void encode(DialRequest message, FriendlyByteBuf buffer) {
            buffer.writeUtf(message.alien, 256);
        }

        private static DialRequest decode(FriendlyByteBuf buffer) {
            return new DialRequest(buffer.readUtf(256));
        }

        private static void handle(DialRequest message, Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                if (player == null) {
                    return;
                }
                boolean changed;
                if (message.alien.isEmpty()) {
                    changed = OmnitrixService.revert(player);
                } else {
                    ResourceLocation alien = ResourceLocation.tryParse(message.alien);
                    changed = alien != null && OmnitrixService.transform(player, alien);
                }
                if (changed) {
                    syncTrackers(player);
                }
            });
            context.setPacketHandled(true);
        }
    }

    private record PowerRequest() {
        private static void encode(PowerRequest message, FriendlyByteBuf buffer) {}

        private static PowerRequest decode(FriendlyByteBuf buffer) {
            return new PowerRequest();
        }

        private static void handle(PowerRequest message, Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                if (player != null) {
                    if (OmnitrixService.usePower(player)) {
                        sendToPlayer(player, OmnitrixService.snapshot(player));
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }

    private record StateUpdate(OmnitrixSnapshot snapshot) {
        private static void encode(StateUpdate message, FriendlyByteBuf buffer) {
            OmnitrixSnapshot state = message.snapshot;
            buffer.writeUUID(state.playerId());
            buffer.writeBoolean(state.currentAlien() != null);
            if (state.currentAlien() != null) {
                buffer.writeResourceLocation(state.currentAlien());
            }
            buffer.writeLong(state.expiresAt());
            buffer.writeBoolean(state.equipped());
            buffer.writeBoolean(state.timeoutDisabled());
            buffer.writeLong(state.rechargeUntil());
            buffer.writeLong(state.powerCooldownUntil());
            buffer.writeLong(state.transitionId());
            buffer.writeVarInt(state.transitionKind());
            buffer.writeFloat(state.width());
            buffer.writeFloat(state.height());
            buffer.writeVarInt(state.unlockedAlienIds().size());
            state.unlockedAlienIds().forEach(buffer::writeResourceLocation);
        }

        private static StateUpdate decode(FriendlyByteBuf buffer) {
            var playerId = buffer.readUUID();
            ResourceLocation alien = buffer.readBoolean() ? buffer.readResourceLocation() : null;
            long expiresAt = buffer.readLong();
            boolean equipped = buffer.readBoolean();
            boolean timeoutDisabled = buffer.readBoolean();
            long rechargeUntil = buffer.readLong();
            long powerCooldownUntil = buffer.readLong();
            long transitionId = buffer.readLong();
            int transitionKind = buffer.readVarInt();
            float width = buffer.readFloat();
            float height = buffer.readFloat();
            int size = Math.min(buffer.readVarInt(), 256);
            List<ResourceLocation> unlocked = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                unlocked.add(buffer.readResourceLocation());
            }
            return new StateUpdate(new OmnitrixSnapshot(playerId, alien, expiresAt, equipped,
                    unlocked, width, height, timeoutDisabled, rechargeUntil, powerCooldownUntil,
                    transitionId, transitionKind));
        }

        private static void handle(StateUpdate message, Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> ClientOmnitrixState.accept(message.snapshot));
            context.setPacketHandled(true);
        }
    }
}
