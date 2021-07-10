package com.yusuf.bentenmod.core.util;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.ContainerInit;
import com.yusuf.bentenmod.core.init.KeybindsInit;
import com.yusuf.bentenmod.core.machine.bententable.TableScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    private ClientEventBusSubscriber() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        KeybindsInit.register(event);
        ScreenManager.register(ContainerInit.TABLE_CONTAINER.get(), TableScreen::new);
    }
}
