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
        buffer.writeFloat(state.width());
        buffer.writeFloat(state.height());
        buffer.writeVarInt(state.unlockedAlienIds().size());
        state.unlockedAlienIds().forEach(buffer::writeResourceLocation);
    }

    public static OmnitrixSnapshot decode(FriendlyByteBuf buffer) {
        var playerId = buffer.readUUID();
        ResourceLocation alien = buffer.readBoolean() ? buffer.readResourceLocation() : null;
        long expiresAt = buffer.readLong();
        float width = buffer.readFloat();
        float height = buffer.readFloat();
        int size = Math.min(buffer.readVarInt(), 256);
        var unlocked = new java.util.ArrayList<ResourceLocation>(size);
        for (int i = 0; i < size; i++) {
            unlocked.add(buffer.readResourceLocation());
        }
        return new OmnitrixSnapshot(playerId, alien, expiresAt, unlocked, width, height);
    }
}
