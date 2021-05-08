package com.yusuf.bentenmobmod.core.init;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
	
	public static KeyBinding fireblastKey;
	
	
	public static void register(final FMLClientSetupEvent event) {
		
		fireblastKey = create("fireblast_key", KeyEvent.VK_K);
		
		ClientRegistry.registerKeyBinding(fireblastKey);
	} 
	
	private static KeyBinding create(String name, int key) {
		return new KeyBinding("key." + Main.MOD_ID + "." + name , key, "key.category." + Main.MOD_ID);
	}
}	
