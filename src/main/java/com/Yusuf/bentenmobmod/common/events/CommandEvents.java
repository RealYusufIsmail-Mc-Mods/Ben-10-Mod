package com.Yusuf.bentenmobmod.common.events;

import static com.Yusuf.bentenmobmod.Main.MOD_ID;

import com.Yusuf.bentenmobmod.command.BenTenMobModCommand;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommandEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
    	BenTenMobModCommand.register(event.getDispatcher());
    }
}
