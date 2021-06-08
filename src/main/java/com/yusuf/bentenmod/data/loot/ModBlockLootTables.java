package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        //ores
        add(BlockInit.BLACK_DIAMOND_ORE.get(),
                createOreDrop(BlockInit.BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));
        add(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));
        add(BlockInit.INFINITUM_ORE.get(),
                createOreDrop(BlockInit.INFINITUM_ORE.get(), ItemInit.INFINITUM_SCRAP.get()));
        add(BlockInit.RUBY_ORE.get(),
                createOreDrop(BlockInit.RUBY_ORE.get(), ItemInit.RUBY.get()));
        add(BlockInit.DEEPSLATE_RUBY_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_RUBY_ORE.get(), ItemInit.RUBY.get()));
        add(BlockInit.SPEED_ORE.get(),
                createOreDrop(BlockInit.SPEED_ORE.get(), ItemInit.SPEED.get()));
        add(BlockInit.DEEPSLATE_SPEED_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_SPEED_ORE.get(), ItemInit.SPEED.get()));
        add(BlockInit.LEGENDARY_ORE_BLOCK.get(),
                createOreDrop(BlockInit.LEGENDARY_ORE_BLOCK.get(), ItemInit.RAW_LEGENDARY.get()));
        add(BlockInit.DEEPSLATE_LEGENDARY_ORE_BLOCK.get(),
                createOreDrop(BlockInit.DEEPSLATE_LEGENDARY_ORE_BLOCK.get(), ItemInit.RAW_LEGENDARY.get()));
        add(BlockInit.IMPERIUM_ORE.get(),
                createOreDrop(BlockInit.IMPERIUM_ORE.get(), ItemInit.RAW_IMPERIUM.get()));
        add(BlockInit.DEEPSLATE_IMPERIUM_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_IMPERIUM_ORE.get(), ItemInit.RAW_IMPERIUM.get()));
        add(BlockInit.OMNITRIX_ORE.get(),
                createOreDrop(BlockInit.OMNITRIX_ORE.get(), ItemInit.RAW_OMNITRIX.get()));
        add(BlockInit.DEEPSLATE_OMNITRIX_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_OMNITRIX_ORE.get(), ItemInit.RAW_OMNITRIX.get()));
        dropSelf(BlockInit.FIRE_ORE.get());




        //blocks
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
        dropSelf(BlockInit.FIRE_BLOCK.get());
        dropSelf(BlockInit.RUBY_BLOCK.get());
        dropSelf(BlockInit.SPEED_BLOCK.get());
        dropSelf(BlockInit.INFINITUM_BLOCK.get());
        dropSelf(BlockInit.BLACK_DIAMOND_BLOCK.get());
        dropSelf(BlockInit.LEGENDARY_BLOCK.get());
        dropSelf(BlockInit.IMPERIUM_BLOCK.get());



    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> Main.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
    }

