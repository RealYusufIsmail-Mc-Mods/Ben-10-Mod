package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import io.github.realyusufismail.bentenmod.core.init.ServerGamePacketListenerInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class BenTenServerboundRecipeBookChangeSettingsPacket
        implements Packet<ServerGamePacketListener> {
    private final BenTenRecipeBookType bookType;
    private final boolean isOpen;
    private final boolean isFiltering;

    public BenTenServerboundRecipeBookChangeSettingsPacket(BenTenRecipeBookType pBookType,
            boolean pIsOpen, boolean pIsFiltering) {
        this.bookType = pBookType;
        this.isOpen = pIsOpen;
        this.isFiltering = pIsFiltering;
    }

    public BenTenServerboundRecipeBookChangeSettingsPacket(FriendlyByteBuf pBuffer) {
        this.bookType = pBuffer.readEnum(BenTenRecipeBookType.class);
        this.isOpen = pBuffer.readBoolean();
        this.isFiltering = pBuffer.readBoolean();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeEnum(this.bookType);
        pBuffer.writeBoolean(this.isOpen);
        pBuffer.writeBoolean(this.isFiltering);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(ServerGamePacketListener pHandler) {
        ServerGamePacketListenerInit handler = (ServerGamePacketListenerInit) pHandler;
        handler.handleCustomRecipeBookChangeSettingsPacket(this);
    }

    public BenTenRecipeBookType getBookType() {
        return this.bookType;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isFiltering() {
        return this.isFiltering;
    }

}
