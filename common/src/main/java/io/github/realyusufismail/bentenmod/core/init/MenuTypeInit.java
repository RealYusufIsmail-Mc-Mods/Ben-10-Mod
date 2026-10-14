package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import io.github.realyusufismail.bentenmod.platform.Services;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;

public class MenuTypeInit {
    public static final RegistrationProvider<MenuType<?>> MENUS =
            RegistrationProvider.get(Registries.MENU, BenTenMod.MOD_ID);

    public static final RegistryObject<MenuType<OmnitrixCrafterMenu>> OmnitrixCrafterMenuType =
            MENUS.register("omnitrix_crafter",
                    () -> Services.PLATFORM.createMenuType(OmnitrixCrafterMenu::new));

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
