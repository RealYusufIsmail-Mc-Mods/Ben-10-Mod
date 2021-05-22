package com.yusuf.bentenmobmod.data.loot;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
        
}
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> Main.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
    }

