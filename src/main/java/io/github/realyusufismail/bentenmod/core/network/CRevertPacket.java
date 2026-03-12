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
package io.github.realyusufismail.bentenmod.core.network;

import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CRevertPacket {

    public static void encode(CRevertPacket packet, PacketBuffer buf) {}

    public static CRevertPacket decode(PacketBuffer buf) {
        return new CRevertPacket();
    }

    public static void handle(CRevertPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            if (player == null) return;

            player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
                data.revert();

                SSyncOmnitrixPacket syncPacket = new SSyncOmnitrixPacket(data.serializeNBT(), player.getId());
                PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), syncPacket);
            });
        });
        context.setPacketHandled(true);
    }
}
