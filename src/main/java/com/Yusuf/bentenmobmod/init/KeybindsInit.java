package com.Yusuf.bentenmobmod.init;

import com.Yusuf.bentenmobmod.Main;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
	
	public static KeyBinding fireblastKey;
	
	
	public static void register(final FMLClientSetupEvent event) {
		
	}
	
	private static KeyBinding create(String name, int key) {
		return new KeyBinding("key." + Main.MOD_ID + "." + name , key, "key.category." + Main.MOD_ID);
	}
}	
