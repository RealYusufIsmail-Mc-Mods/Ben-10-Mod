
package com.yusuf.bentenmod.data.client;


import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
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
        simpleBlock(BlockInit.IMPERIUM_ORE.get());
        simpleBlock(BlockInit.IMPERIUM_BLOCK.get());
     // createFurnace(BlockInit.TABLE_BLOCK, TexturedModel.ORIENTABLE_ONLY_TOP);
        //TODO Fix this
    }

 /*
    public void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
        ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
        ResourceLocation resourcelocation1 = ModelTextures.getBlockTexture(p_239977_1_, "_front_on");
        ResourceLocation resourcelocation2 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
            p_239963_1_.put(StockTextureAliases.FRONT, resourcelocation1);
        }).createWithSuffix(p_239977_1_, "_on", this.modelOutput);
        this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation2, resourcelocation)).with(createHorizontalFacingDispatch()));
    }

    private static BlockStateVariantBuilder createBooleanModelDispatch(BooleanProperty lit, ResourceLocation p_239894_1_, ResourceLocation p_239894_2_) {
        return BlockStateVariantBuilder.property(BlockStateProperties.LIT).select(true, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_1_)).select(false, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_2_));
    }

    private static BlockStateVariantBuilder createHorizontalFacingDispatch() {
        return BlockStateVariantBuilder.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R90)).select(Direction.SOUTH, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R180)).select(Direction.WEST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R270)).select(Direction.NORTH, BlockModelDefinition.variant());
    }

         */



}





