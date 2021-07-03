
package com.yusuf.bentenmod.data.client;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import java.util.function.BiFunction;

import static com.yusuf.bentenmod.core.init.BlockInit.*;
import static net.yusuf.realyusufismailcore.data.client.ModelProvider.BlockModels.*;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BenTenMod.MOD_ID, exFileHelper);

    }

    @Override
    protected void registerStatesAndModels() {
            simpleBlock(BLACK_DIAMOND_BLOCK.get());
            simpleBlock(BLACK_DIAMOND_ORE.get());
            axisBlock(INFINITUM_ORE.get(), modLoc("block/infinitum_ore_side"), modLoc("block/infinitum_ore_top"));
            simpleBlock(INFINITUM_BLOCK.get());
            simpleBlock(FIRE_BLOCK.get());
            simpleBlock(FIRE_ORE.get());
            simpleBlock(LEGENDARY_BLOCK.get());
            simpleBlock(LEGENDARY_ORE_BLOCK.get());
            simpleBlock(OMNITRIX_BLOCK.get());
            simpleBlock(OMNITRIX_ORE.get());
            simpleBlock(RUBY_BLOCK.get());
            simpleBlock(RUBY_ORE.get());
            simpleBlock(SPEED_BLOCK.get());
            simpleBlock(SPEED_ORE.get());
            simpleBlock(IMPERIUM_BLOCK.get());
            simpleBlock(IMPERIUM_ORE.get());
            //createFurnace(TABLE_BLOCK.get(), TexturedModel.ORIENTABLE_ONLY_TOP);

    }
    public void createTrivialCube(Block p_239975_1_) {
        this.createTrivialBlock(p_239975_1_, TexturedModel.CUBE);
    }
    public void createTrivialBlock(Block p_239956_1_, TexturedModel.ISupplier p_239956_2_) {
        blockStateOutput.accept(createSimpleBlock(p_239956_1_, p_239956_2_.create(p_239956_1_, modelOutput)));
    }
    public void createSmithingTable() {
        ModelTextures modeltextures = (new ModelTextures()).put(StockTextureAliases.PARTICLE, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(StockTextureAliases.DOWN, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_bottom")).put(StockTextureAliases.UP, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_top")).put(StockTextureAliases.NORTH, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(StockTextureAliases.SOUTH, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(StockTextureAliases.EAST, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_side")).put(StockTextureAliases.WEST, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_side"));
       blockStateOutput.accept(createSimpleBlock(Blocks.SMITHING_TABLE, StockModelShapes.CUBE.create(Blocks.SMITHING_TABLE, modeltextures, modelOutput)));
    }

    public void createCraftingTableLike(Block p_239875_1_, Block p_239875_2_, BiFunction<Block, Block, ModelTextures> p_239875_3_) {
        ModelTextures modeltextures = p_239875_3_.apply(p_239875_1_, p_239875_2_);
        blockStateOutput.accept(createSimpleBlock(p_239875_1_, StockModelShapes.CUBE.create(p_239875_1_, modeltextures, modelOutput)));
    }

    public static void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
        ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, modelOutput);
        ResourceLocation resourcelocation1 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
        }).createWithSuffix(p_239977_1_, "_on", modelOutput);
        blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation)).with(createHorizontalFacingDispatch()));
    }
}





