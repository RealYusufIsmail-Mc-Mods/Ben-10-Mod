package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.core.machine.bententable.TableContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.yusuf.bentenmod.BenTenMod.MOD_ID;

public class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final RegistryObject<MenuType<TableContainer>> TABLE_CONTAINER = CONTAINERS.register("table", () -> IForgeContainerType.create(TableContainer::new));


}