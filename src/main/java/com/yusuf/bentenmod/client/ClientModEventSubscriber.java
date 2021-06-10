package com.yusuf.bentenmod.client;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.client.renderer.VilgaxRenderer;
import com.yusuf.bentenmod.core.init.EntityTypesInit;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		Keybindings.RELOAD = new KeyBinding("key." + BenTenMod.MOD_ID + ".reload", GLFW.GLFW_KEY_R,
				"key.categories." + BenTenMod.MOD_ID);
		ClientRegistry.registerKeyBinding(Keybindings.RELOAD);
		RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxRenderer::new);
		
	}
}