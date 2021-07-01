
package com.yusuf.bentenmod.data.client;


import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.machine.bententable.TableBlock;

import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.data.TexturedModel.ISupplier;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import static net.yusuf.realyusufismailcore.data.client.ModelProvider.BlockModels.createFurnace;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BenTenMod.MOD_ID, exFileHelper);

    }

    @Override
    protected void registerStatesAndModels() {
            simpleBlock(BlockInit.BLACK_DIAMOND_BLOCK.get());
            simpleBlock(BlockInit.BLACK_DIAMOND_ORE.get());
            axisBlock(BlockInit.INFINITUM_ORE.get(), modLoc("block/infinitum_ore_side"), modLoc("block/infinitum_ore_top"));
            simpleBlock(BlockInit.INFINITUM_BLOCK.get());
            simpleBlock(BlockInit.FIRE_BLOCK.get());
            simpleBlock(BlockInit.FIRE_ORE.get());
            simpleBlock(BlockInit.LEGENDARY_BLOCK.get());
            simpleBlock(BlockInit.LEGENDARY_ORE_BLOCK.get());
            simpleBlock(BlockInit.OMNITRIX_BLOCK.get());
            simpleBlock(BlockInit.OMNITRIX_ORE.get());
            simpleBlock(BlockInit.RUBY_BLOCK.get());
            simpleBlock(BlockInit.RUBY_ORE.get());
            simpleBlock(BlockInit.SPEED_BLOCK.get());
            simpleBlock(BlockInit.SPEED_ORE.get());
            simpleBlock(BlockInit.IMPERIUM_BLOCK.get());
            simpleBlock(BlockInit.IMPERIUM_ORE.get());
            createFurnace(BlockInit.TABLE_BLOCK, TexturedModel.ORIENTABLE_ONLY_TOP);

    }

	private void createFurnace(RegistryObject<TableBlock> tableBlock, ISupplier orientableOnlyTop) {
    }
}





