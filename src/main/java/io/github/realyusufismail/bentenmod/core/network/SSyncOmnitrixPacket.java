/*
 * Copyright 2023 RealYusufIsmail.
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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SSyncOmnitrixPacket {

    private final CompoundNBT data;
    private final int playerEntityId;

    public SSyncOmnitrixPacket(CompoundNBT data, int playerEntityId) {
        this.data = data;
        this.playerEntityId = playerEntityId;
    }

    public static void encode(SSyncOmnitrixPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.playerEntityId);
        buf.writeNbt(packet.data);
    }

    public static SSyncOmnitrixPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        CompoundNBT nbt = buf.readNbt();
        return new SSyncOmnitrixPacket(nbt != null ? nbt : new CompoundNBT(), entityId);
    }

    public static void handle(SSyncOmnitrixPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClient(packet)));
        context.setPacketHandled(true);
    }

    private static void handleClient(SSyncOmnitrixPacket packet) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Entity entity = mc.level.getEntity(packet.playerEntityId);
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity)
                    .getCapability(CapabilityHandler.OMNITRIX_CAP)
                    .ifPresent(data -> data.deserializeNBT(packet.data));
        }
    }
}
