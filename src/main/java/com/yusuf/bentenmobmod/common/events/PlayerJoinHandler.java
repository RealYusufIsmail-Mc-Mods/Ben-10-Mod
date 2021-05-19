package com.yusuf.bentenmobmod.common.events;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)

public class PlayerJoinHandler {

    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent evt) {
        ((ServerPlayerEntity) evt.getPlayer()).getAdvancements().flushDirty((ServerPlayerEntity) evt.getPlayer());

    }
}
