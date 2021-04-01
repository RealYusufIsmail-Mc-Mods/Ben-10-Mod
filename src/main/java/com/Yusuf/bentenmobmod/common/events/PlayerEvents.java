package com.Yusuf.bentenmobmod.common.events;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.core.init.ItemInit;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public  class  PlayerEvents {


	@SubscribeEvent 
	public static <PlayerHitEvent> void swordHitPLayer(PlayerHitEvent event) {

		Item sword = ItemInit.HEATBLAST_SWORD.get();
		sword.isFireResistant();
			
			
			
			
			
		
	}
	
}
