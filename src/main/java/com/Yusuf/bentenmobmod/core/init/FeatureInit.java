package com.Yusuf.bentenmobmod.core.init;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class FeatureInit {
	public void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> {
			//Moved the methods bellow into enqueue to make sure they dont cause issues during registration - andrew
			ConfiguredFeatures.registerConfiguredFeatures();
			
		});
	}
}
	