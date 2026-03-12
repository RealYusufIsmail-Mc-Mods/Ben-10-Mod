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
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.AlienAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.AlienAbilityRegistry;
import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CTransformPacket {

    private final AlienType alienType;

    public CTransformPacket(AlienType alienType) {
        this.alienType = alienType;
    }

    public static void encode(CTransformPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.alienType.ordinal());
    }

    public static CTransformPacket decode(PacketBuffer buf) {
        return new CTransformPacket(AlienType.fromOrdinal(buf.readInt()));
    }

    public static void handle(CTransformPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            if (player == null) return;

            // Validate player has Omnitrix in inventory
            boolean hasOmnitrix =
                    player.inventory.items.stream().anyMatch(stack -> stack.getItem() == ItemInit.OMNITRIX.get());
            if (!hasOmnitrix) return;

            player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
                // Validate alien is unlocked and not on cooldown
                if (!data.isAlienUnlocked(packet.alienType)) return;
                if (data.getCooldownTicksRemaining() > 0) return;

                data.transform(packet.alienType);

                // Call onTransform ability hook
                AlienAbility ability = AlienAbilityRegistry.get(packet.alienType);
                if (ability != null) {
                    ability.onTransform(player);
                }

                // Sync back to client and nearby players
                SSyncOmnitrixPacket syncPacket = new SSyncOmnitrixPacket(data.serializeNBT(), player.getId());
                PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), syncPacket);
            });
        });
        context.setPacketHandled(true);
    }
}
