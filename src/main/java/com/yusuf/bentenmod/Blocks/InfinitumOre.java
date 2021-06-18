
package com.yusuf.bentenmod.Blocks;


import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class InfinitumOre extends OreBlock {


    public InfinitumOre() {
        super(Properties.of(Material.METAL)
                .strength(3)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .requiresCorrectToolForDrops()
                .sound(SoundType.ANCIENT_DEBRIS)
        );
    }

}


