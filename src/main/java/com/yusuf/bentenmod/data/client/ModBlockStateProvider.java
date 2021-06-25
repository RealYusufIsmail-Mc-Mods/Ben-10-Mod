
package com.yusuf.bentenmod.data.client;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FinishedVariantBlockState;
import net.minecraft.data.TexturedModel;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

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
      //  createFurnace(BlockInit.TABLE_BLOCK, TexturedModel.ORIENTABLE_ONLY_TOP);
    }


/*
    private ModelFile.ExistingModelFile getExistingModel(String blockName) {
        return models().getExistingFile(modLoc(blockName));
    }

    public void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
        ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
        ResourceLocation resourcelocation1 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
        }).createWithSuffix(p_239977_1_, "_on", this.modelOutput);
        this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation)).with(createHorizontalFacingDispatch()));
    }
    
 */


}


