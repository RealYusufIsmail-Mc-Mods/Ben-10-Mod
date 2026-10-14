package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
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
    private static final String PROTOCOL = "2";
    private static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(BenTenMod.getModIdAndName("omnitrix"), () -> PROTOCOL,
                    PROTOCOL::equals, PROTOCOL::equals);

    private OmnitrixNetwork() {}

    public static void register() {
        CHANNEL.registerMessage(0, DialRequest.class, DialRequest::encode, DialRequest::decode,
                DialRequest::handle);
        CHANNEL.registerMessage(1, StateUpdate.class, StateUpdate::encode, StateUpdate::decode,
                StateUpdate::handle);
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
            float width = buffer.readFloat();
            float height = buffer.readFloat();
            int size = Math.min(buffer.readVarInt(), 256);
            List<ResourceLocation> unlocked = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                unlocked.add(buffer.readResourceLocation());
            }
            return new StateUpdate(new OmnitrixSnapshot(playerId, alien, expiresAt, equipped,
                    unlocked, width, height));
        }

        private static void handle(StateUpdate message, Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> ClientOmnitrixState.accept(message.snapshot));
            context.setPacketHandled(true);
        }
    }
}
