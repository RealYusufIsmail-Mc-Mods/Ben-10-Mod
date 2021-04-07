package com.Yusuf.bentenmobmod;

import com.Yusuf.bentenmobmod.core.init.EntityTypesInit;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import software.bernie.example.GeckoLibMod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonListener {
	@SuppressWarnings("deprecation")
	@SubscribeEvent
    public static void registerEntityAttributes(FMLCommonSetupEvent event) {
        if (!FMLEnvironment.production && !GeckoLibMod.DISABLE_IN_DEV) {
            event.enqueueWork(() -> {
                
                GlobalEntityTypeAttributes.put(EntityTypesInit.VILGAX_ENTITY.get(), MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).build());
              
            });
        }
    }
}

