package com.yusuf.bentenmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class TableBlock extends Block {

    public TableBlock() {
        super(Properties.of(Material.WOOD)
                .strength(3)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops()
                .sound(SoundType.WOOD)
        );
    }
}
