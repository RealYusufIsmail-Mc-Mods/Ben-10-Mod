package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.machine.bententable.TableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BenTenMod.MOD_ID);

    public static final RegistryObject<TileEntityType<TableTileEntity>> TABLE_TILE = TILE_ENTITY.register("table", () -> TileEntityType.Builder.of(TableTileEntity::new, BlockInit.TABLE_BLOCK.get()).build(null));
}