package com.yusuf.bentenmod;


import com.yusuf.bentenmod.backpack.BackpackItem;
import com.yusuf.bentenmod.core.init.*;
import com.yusuf.bentenmod.core.init.RegisterRecipeInit;
import com.yusuf.bentenmod.gui.FilterContainer;
import com.yusuf.bentenmod.gui.FilterGui;
import com.yusuf.bentenmod.gui.SBContainer;
import com.yusuf.bentenmod.gui.SBGui;
import com.yusuf.bentenmod.network.OpenMessage;
import com.yusuf.bentenmod.network.SBNetwork;
import com.yusuf.bentenmod.network.ToggleMessage;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import static com.yusuf.bentenmod.core.init.ContainerInit.BACKPACK_CONTAINER;
import static com.yusuf.bentenmod.core.init.ContainerInit.FILTER_CONTAINER;
import static net.yusuf.realyusufismailcore.client.Keybindings.keyBinds;


@Mod("bentenmod")
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class Main {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "bentenmod";
	//register for backpack
	public static boolean curiosLoaded;

	public static SimpleChannel network;
	public static SBNetwork sbnetwork = new SBNetwork();
	public Main() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		EntityTypesInit.ENTITY_TYPES.register(bus);
		TileEntityInit.TILE_ENTITY.register(bus);
		ContainerInit.CONTAINERS.register(bus);

		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();
		curiosLoaded = ModList.get().isLoaded("curios");

		bus.addGenericListener(IRecipeSerializer.class, RegisterRecipeInit::registerRecipes);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeatureInit::addOres);
		MinecraftForge.EVENT_BUS.register(this);

	}

	private void pickupEvent(EntityItemPickupEvent event) {
		if (event.getPlayer().containerMenu instanceof SBContainer || event.getPlayer().isShiftKeyDown() || BackpackItem.isBackpack(event.getItem().getItem()))
			return;
		if (curiosLoaded) {
			boolean handled = CuriosApi.getCuriosHelper().findEquippedCurio(BackpackItem::isBackpack, event.getPlayer()).map((data) -> {
				// A cast here should be safe...
				if (((BackpackItem) data.getRight().getItem()).pickupEvent(event.getItem().getItem(), data.getRight())) {
					event.setResult(Event.Result.ALLOW);
					return true;
				}

				return false;
			}).orElse(false);

			if (handled) {
				return;
			}
		}
			PlayerInventory playerInv = event.getPlayer().inventory;
			for (int i = 0; i <= 8; i++) {
				ItemStack stack = playerInv.getItem(i);
				if (BackpackItem.isBackpack(stack) && ((BackpackItem) stack.getItem()).pickupEvent(event.getItem().getItem(), stack)) {
					event.setResult(Event.Result.ALLOW);
					return;
				}
			}
		}
		private void onClientTick(TickEvent.ClientTickEvent event) {
			if (keyBinds.get(0).consumeClick())
				network.sendToServer(new ToggleMessage());

			if (keyBinds.get(1).consumeClick())
				network.sendToServer(new OpenMessage());
		}
		private void clientStuff(final FMLClientSetupEvent event) {
			ScreenManager.register(BACKPACK_CONTAINER.get(), SBGui::new);
			ScreenManager.register(FILTER_CONTAINER.get(), FilterGui::new);

			keyBinds.add(0, new KeyBinding("key.main.backpackpickup.desc", -1, "key.main.category"));
			keyBinds.add(1, new KeyBinding("key.main.backpackopen.desc", -1, "key.main.category"));

			ClientRegistry.registerKeyBinding(keyBinds.get(0));
			ClientRegistry.registerKeyBinding(keyBinds.get(1));
		}
			private void onEnqueueIMC(InterModEnqueueEvent event){
				if (curiosLoaded) {
					InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
		}
	}
}
