package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.core.init.recipe.book.BenTenServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public interface ServerGamePacketListenerInit extends ServerGamePacketListener {
    void handleCustomRecipeBookChangeSettingsPacket(
            BenTenServerboundRecipeBookChangeSettingsPacket pPacket);
}
