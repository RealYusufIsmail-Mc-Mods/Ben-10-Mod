package com.yusuf.bentenmod.core.init;


import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.block.tablemachine.TableContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//class for register + Make sure it class Main Class
public class ContainerInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister
            .create(ForgeRegistries.CONTAINERS, Main.MOD_ID);


    public static final RegistryObject<ContainerType<TableContainer>> TABLE = CONTAINER.register("table",
            () -> IForgeContainerType.create(TableContainer::new));

}
