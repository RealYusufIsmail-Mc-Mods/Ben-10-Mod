package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.ContainerInit;
import com.yusuf.bentenmod.core.machine.bententable.TableScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    private ClientEvents(){}

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ScreenManager.register(ContainerInit.TABLE.get(), TableScreen::new);
    }
}