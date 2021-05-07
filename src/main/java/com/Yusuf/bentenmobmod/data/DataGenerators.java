package com.Yusuf.bentenmobmod.data;

import com.Yusuf.bentenmobmod.Main;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ;
    }
}
