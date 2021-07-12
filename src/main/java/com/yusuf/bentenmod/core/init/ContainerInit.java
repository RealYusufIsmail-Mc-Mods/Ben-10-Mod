package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.core.machine.alientable.AlienTableContainer;
import com.yusuf.bentenmod.core.machine.bententable.TableContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.yusuf.bentenmod.BenTenMod.MOD_ID;

public class ContainerInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
    public static final RegistryObject<ContainerType<TableContainer>> TABLE_CONTAINER = CONTAINERS.register("table", () -> IForgeContainerType.create(TableContainer::new));
    public static final RegistryObject<ContainerType<AlienTableContainer>> ALIEN_TABLE_CONTAINER = CONTAINERS.register("alien_table", () -> IForgeContainerType.create(AlienTableContainer::new));

}