package com.yusuf.bentenmod;


import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.EntityTypesInit;
import com.yusuf.bentenmod.core.init.FeatureInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmod.item.ModSpawnEggItem;
import com.yusuf.bentenmod.modules.bententable.recipes.RegisterRecipe;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod("bentenmod")
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class Main {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "bentenmod";

	public Main() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EntityTypesInit.ENTITY_TYPES.register(bus);
		GeckoLibMod.DISABLE_IN_DEV = false;
		GeckoLib.initialize();
		bus.addGenericListener(IRecipeSerializer.class, RegisterRecipe::registerRecipes);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeatureInit::addOres);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(this);


		


	};
		
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
	private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
		return DeferredRegister.create(registry, MOD_ID);
	}

}
