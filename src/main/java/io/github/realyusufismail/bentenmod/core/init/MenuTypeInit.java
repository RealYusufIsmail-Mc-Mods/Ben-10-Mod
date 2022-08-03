package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BenTenMod.MOD_ID);

    public static final RegistryObject<MenuType<OmnitrixCrafterMenu>> OmnitrixCrafterMenuType =
            register("omnitrix_crafter", OmnitrixCrafterMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(
            String name, MenuType.MenuSupplier<T> pFactory) {
        return MENUS.register(name, () -> new MenuType<>(pFactory));
    }
}
