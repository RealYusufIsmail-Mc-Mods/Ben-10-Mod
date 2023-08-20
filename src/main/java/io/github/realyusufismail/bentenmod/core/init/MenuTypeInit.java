package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BenTenMod.MOD_ID);

    public static final RegistryObject<MenuType<OmnitrixCrafterMenu>> OmnitrixCrafterMenuType =
            register("omnitrix_crafter", OmnitrixCrafterMenu::new, null);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(
            String name, MenuType.MenuSupplier<T> pFactory,
            @Nullable FeatureFlagSet pFeatureFlags) {
        if (pFeatureFlags == null) {
            pFeatureFlags = FeatureFlags.REGISTRY.allFlags();
        }
        FeatureFlagSet finalPFeatureFlags = pFeatureFlags;
        return MENUS.register(name, () -> new MenuType<>(pFactory, finalPFeatureFlags));
    }
}
