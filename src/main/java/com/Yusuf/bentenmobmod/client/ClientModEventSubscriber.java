package com.Yusuf.bentenmobmod.client;

import org.lwjgl.glfw.GLFW;

import com.Yusuf.bentenmobmod.Main;
import com.Yusuf.bentenmobmod.client.renderer.VilgaxRenderer;
import com.Yusuf.bentenmobmod.core.init.EntityTypesInit;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Main.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		Keybindings.RELOAD = new KeyBinding("key." + Main.MOD_ID + ".reload", GLFW.GLFW_KEY_R,
				"key.categories." + Main.MOD_ID);
		ClientRegistry.registerKeyBinding(Keybindings.RELOAD);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxRenderer::new);
		
	}
}