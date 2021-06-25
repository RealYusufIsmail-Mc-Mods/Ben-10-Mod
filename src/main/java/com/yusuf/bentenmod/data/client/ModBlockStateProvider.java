
package com.yusuf.bentenmod.data.client;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yusuf.realyusufismailcore.block.IBlockProvider;

public class ModBlockStateProvider extends BlockStateProvider{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlockInit.BLACK_DIAMOND_BLOCK.get());
        simpleBlock(BlockInit.BLACK_DIAMOND_ORE.get());
        axisBlock(BlockInit.INFINITUM_ORE.get(),modLoc("block/infinitum_ore_side"), modLoc("block/infinitum_ore_top"));
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
        simpleBlock(BlockInit.IMPERIUM_ORE.get());
        simpleBlock(BlockInit.IMPERIUM_BLOCK.get());

        horizontalBlock(BlockInit.TABLE_BLOCK.get(),modLoc("block/table_block_side"), modLoc("block/table_block_front"),modLoc("block/table_block_bottom"));
    }

    private ModelFile.ExistingModelFile getExistingModel(String blockName) {
        return models().getExistingFile(modLoc(blockName));
    }
    private void orientableMachineBlock(IBlockProvider block, String name) {
        ModelFile.ExistingModelFile offModel = getExistingModel(name);
        ModelFile.ExistingModelFile onModel = getExistingModel(name + "_on");
        getVariantBuilder(block.asBlock()).forAllStates(state -> {
            Direction facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
            boolean lit = state.get(BlockStateProperties.LIT);
            return ConfiguredModel.builder()
                    .modelFile(lit ? onModel : offModel)
                    .rotationY((int) facing.getOpposite().toYRot())
                    .build();
        });
    }
}


