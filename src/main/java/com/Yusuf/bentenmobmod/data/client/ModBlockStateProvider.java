
package com.yusuf.bentenmobmod.data.client;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

       // simpleBlock(BlockInit.BLACK_DIAMOND_BLOCK.get());
        simpleBlock(BlockInit.BLACK_DIAMOND_ORE.get());
        simpleBlock(BlockInit.INFINITUM_ORE.get());

    }
}
