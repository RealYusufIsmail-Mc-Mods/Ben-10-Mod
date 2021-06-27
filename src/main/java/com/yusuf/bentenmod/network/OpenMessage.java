package com.yusuf.bentenmod.network;

import com.yusuf.bentenmod.backpack.BackpackItem;
import com.yusuf.bentenmod.gui.SBContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenMessage {
    public static OpenMessage decode(final PacketBuffer buffer) {
        buffer.readByte();
        return new OpenMessage();
    }
    public static void encode(final OpenMessage message, final PacketBuffer buffer) {
        buffer.writeByte(0);
    }
    public static void handle(final OpenMessage message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) {
                return;
            }

            ItemStack backpack = BackpackItem.findBackpack(player);
            if (!backpack.isEmpty()) {
                player.openMenu(new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) ->
                        new SBContainer(windowId, playerInventory, null), backpack.getDisplayName()));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
