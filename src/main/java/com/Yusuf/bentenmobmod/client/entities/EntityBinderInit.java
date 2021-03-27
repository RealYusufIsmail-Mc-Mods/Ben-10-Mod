package com.Yusuf.bentenmobmod.client.entities;

import com.Yusuf.bentenmobmod.client.entities.render.VilgaxRender;
import com.Yusuf.bentenmobmod.core.init.EntityTypesInit;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityBinderInit {
	public static void bindAllEntities() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.VILGAX.get(), VilgaxRender::new);
	}
}
