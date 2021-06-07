package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.block.tablemachine.TableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.MOD_ID);

        public static final RegistryObject<TileEntityType<TableTileEntity>> TABLE_TE = TILE_TYPES.register("table", () -> TileEntityType.Builder.of(TableTileEntity::new, BlockInit.TABLE_BLOCK.get()).build(null));
    }
