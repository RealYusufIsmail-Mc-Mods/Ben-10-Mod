package com.yusuf.bentenmobmod.data.loot;

import com.yusuf.bentenmobmod.core.init.BlockInit;
import net.minecraft.data.loot.BlockLootTables;

public class ModBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
}

    }

