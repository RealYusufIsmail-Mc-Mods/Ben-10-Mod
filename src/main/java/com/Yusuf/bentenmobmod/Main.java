package com.yusuf.bentenmobmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import com.yusuf.bentenmobmod.core.init.ConfiguredFeatures;
import com.yusuf.bentenmobmod.core.init.EntityTypesInit;
import com.yusuf.bentenmobmod.core.init.ItemInit;
import com.yusuf.bentenmobmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmobmod.item.ModSpawnEggItem;

import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod("bentenmobmod")
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class Main {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "bentenmobmod";

	public Main() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EntityTypesInit.ENTITY_TYPES.register(bus);
		GeckoLibMod.DISABLE_IN_DEV = false;
		GeckoLib.initialize();

		MinecraftForge.EVENT_BUS.register(this);
		
		MinecraftForge.EVENT_BUS.register(this);

	};
	@SubscribeEvent
	public void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> {
			//Moved the methods bellow into enqueue to make sure they dont cause issues during registration - andrew
			ConfiguredFeatures.registerConfiguredFeatures();
			
		});
		}
		
	@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
	public static class CommonModEvents {
	}

	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggItem.initSpawnEggs();
		EntityTypesInit.registerEntityAttributes();
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(MainItemGroup.MAIN))
					.setRegistryName(block.getRegistryName()));

		});

	}

}
