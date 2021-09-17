package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.core.init.*;
import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;


public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        //ores
        add(BlockInit.BLACK_DIAMOND_ORE.get(),
                createOreDrop(BlockInit.BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));
        add(BlockInit.INFINITUM_ORE.get(),
                createOreDrop(BlockInit.INFINITUM_ORE.get(), ItemInit.INFINITUM_SCRAP.get()));
        add(BlockInit.SPEED_ORE.get(),
                createOreDrop(BlockInit.SPEED_ORE.get(), ItemInit.SPEED.get()));

        dropSelf(BlockInit.LEGENDARY_ORE.get());
        dropSelf(BlockInit.IMPERIUM_ORE.get());
        dropSelf(BlockInit.OMNITRIX_ORE.get());
        dropSelf(BlockInit.FIRE_ORE.get());
        dropSelf(BlockInit.RUBY_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_IMPERIUM_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_OMNITRIX_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_LEGENDARY_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_RUBY_ORE.get());
        dropSelf(BlockInit.DEEPSLATE_SPEED_ORE.get());


        //blocks
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
        dropSelf(BlockInit.FIRE_BLOCK.get());
        dropSelf(BlockInit.RUBY_BLOCK.get());
        dropSelf(BlockInit.SPEED_BLOCK.get());
        dropSelf(BlockInit.INFINITUM_BLOCK.get());
        dropSelf(BlockInit.BLACK_DIAMOND_BLOCK.get());
        dropSelf(BlockInit.LEGENDARY_BLOCK.get());
        dropSelf(BlockInit.IMPERIUM_BLOCK.get());
        dropSelf(BlockInit.TABLE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> BenTenMod.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}

