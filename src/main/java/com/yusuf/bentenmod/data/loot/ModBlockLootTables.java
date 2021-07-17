package com.yusuf.bentenmod.data.loot;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

import static com.yusuf.bentenmod.core.init.BlockInit.*;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        //ores
        add(BLACK_DIAMOND_ORE.get(),
                createOreDrop(BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));
        add(INFINITUM_ORE.get(),
                createOreDrop(INFINITUM_ORE.get(), ItemInit.INFINITUM_SCRAP.get()));
        add(SPEED_ORE.get(),
                createOreDrop(SPEED_ORE.get(), ItemInit.SPEED.get()));

        dropSelf(LEGENDARY_ORE.get());
        dropSelf(IMPERIUM_ORE.get());
        dropSelf(OMNITRIX_ORE.get());
        dropSelf(FIRE_ORE.get());
        dropSelf(RUBY_ORE.get());
        dropSelf(DEEPSLATE_BLACK_DIAMOND_ORE.get());
        dropSelf(DEEPSLATE_IMPERIUM_ORE.get());
        dropSelf(DEEPSLATE_OMNITRIX_ORE.get());
        dropSelf(DEEPSLATE_LEGENDARY_ORE.get());
        dropSelf(DEEPSLATE_RUBY_ORE.get());
        dropSelf(DEEPSLATE_SPEED_ORE.get());



        //blocks
        dropSelf(OMNITRIX_BLOCK.get());
        dropSelf(FIRE_BLOCK.get());
        dropSelf(RUBY_BLOCK.get());
        dropSelf(SPEED_BLOCK.get());
        dropSelf(INFINITUM_BLOCK.get());
        dropSelf(BLACK_DIAMOND_BLOCK.get());
        dropSelf(LEGENDARY_BLOCK.get());
        dropSelf(IMPERIUM_BLOCK.get());
        dropSelf(TABLE_BLOCK.get());
        dropSelf(ALIEN_TABLE.get());


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> BenTenMod.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}

