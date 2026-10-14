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

import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/** Fabric's small state and dial channel. All transformation decisions stay on the server. */
public final class OmnitrixNetwork {
    public static final ResourceLocation DIAL_REQUEST = BenTenMod.getModIdAndName("omnitrix_dial");
    public static final ResourceLocation STATE_UPDATE = BenTenMod.getModIdAndName("omnitrix_state");
    public static final ResourceLocation POWER_REQUEST =
            BenTenMod.getModIdAndName("omnitrix_power");

    private OmnitrixNetwork() {}

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(DIAL_REQUEST,
                (server, player, handler, buffer, responseSender) -> {
                    String request = buffer.readUtf(256);
                    server.execute(() -> {
                        boolean changed;
                        if (request.isEmpty()) {
                            changed = OmnitrixService.revert(player);
                        } else {
                            ResourceLocation alien = ResourceLocation.tryParse(request);
                            changed = alien != null && OmnitrixService.transform(player, alien);
                        }
                        if (changed) {
                            syncTrackers(player);
                        }
                    });
                });
        ServerPlayNetworking.registerGlobalReceiver(POWER_REQUEST, (server, player, handler, buffer,
                responseSender) -> server.execute(() -> OmnitrixService.usePower(player)));
    }

    public static void sendToPlayer(ServerPlayer player, OmnitrixSnapshot snapshot) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        encode(snapshot, buffer);
        ServerPlayNetworking.send(player, STATE_UPDATE, buffer);
    }

    public static void syncTrackers(ServerPlayer player) {
        OmnitrixSnapshot snapshot = OmnitrixService.snapshot(player);
        sendToPlayer(player, snapshot);
        OmnitrixSnapshot publicSnapshot = OmnitrixService.trackingSnapshot(player);
        PlayerLookup.tracking(player).forEach(tracker -> sendToPlayer(tracker, publicSnapshot));
    }

    private static void encode(OmnitrixSnapshot state, FriendlyByteBuf buffer) {
        buffer.writeUUID(state.playerId());
        buffer.writeBoolean(state.currentAlien() != null);
        if (state.currentAlien() != null) {
            buffer.writeResourceLocation(state.currentAlien());
        }
        buffer.writeLong(state.expiresAt());
        buffer.writeBoolean(state.equipped());
        buffer.writeBoolean(state.timeoutDisabled());
        buffer.writeLong(state.rechargeUntil());
        buffer.writeFloat(state.width());
        buffer.writeFloat(state.height());
        buffer.writeVarInt(state.unlockedAlienIds().size());
        state.unlockedAlienIds().forEach(buffer::writeResourceLocation);
    }

    public static OmnitrixSnapshot decode(FriendlyByteBuf buffer) {
        var playerId = buffer.readUUID();
        ResourceLocation alien = buffer.readBoolean() ? buffer.readResourceLocation() : null;
        long expiresAt = buffer.readLong();
        boolean equipped = buffer.readBoolean();
        boolean timeoutDisabled = buffer.readBoolean();
        long rechargeUntil = buffer.readLong();
        float width = buffer.readFloat();
        float height = buffer.readFloat();
        int size = Math.min(buffer.readVarInt(), 256);
        var unlocked = new java.util.ArrayList<ResourceLocation>(size);
        for (int i = 0; i < size; i++) {
            unlocked.add(buffer.readResourceLocation());
        }
        return new OmnitrixSnapshot(playerId, alien, expiresAt, equipped, unlocked, width, height,
                timeoutDisabled, rechargeUntil);
    }
}
