package com.yusuf.bentenmod;


import com.yusuf.bentenmod.core.init.*;
import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;


@Mod("bentenmod")
@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.MOD)
public class BenTenMod {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "bentenmod";

	public BenTenMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EntityTypesInit.ENTITY_TYPES.register(bus);
		TileEntityInit.TILE_ENTITY.register(bus);
		ContainerInit.CONTAINERS.register(bus);

		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();

		bus.addGenericListener(IRecipeSerializer.class, RegisterRecipeInit::registerRecipes);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeatureInit::addOres);
		MinecraftForge.EVENT_BUS.register(this);

	}
}
