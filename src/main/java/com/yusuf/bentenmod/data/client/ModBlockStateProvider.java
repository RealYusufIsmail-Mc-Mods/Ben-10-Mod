
package com.yusuf.bentenmod.data.client;

import com.google.gson.JsonElement;
import com.yusuf.bentenmod.BenTenMod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.yusuf.bentenmod.core.init.BlockInit.*;

/**
 * @see BlockStateProvider#simpleBlock(Block) 
 */
public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BenTenMod.MOD_ID, exFileHelper);

    }



    @Nonnull
    @Override
    public String getName() {
        return "Ben 10 Mod - Block States/Models";
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
          // orientableMachineBlock(TABLE_BLOCK.get(), "table_block");

    }

    private static final class BlockModels extends BlockModelProvider {
        private final Consumer<IFinishedBlockState> blockStateOutput;
        private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

        public BlockModels(Consumer<IFinishedBlockState> p_i232514_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232514_2_, Consumer<net.minecraft.item.Item> p_i232514_3_) {
            super(p_i232514_1_, p_i232514_2_, p_i232514_3_);

            blockStateOutput = p_i232514_1_;
            modelOutput = p_i232514_2_;
        }

        private static BlockStateVariantBuilder createBooleanModelDispatch(BooleanProperty lit, ResourceLocation p_239894_1_, ResourceLocation p_239894_2_) {
            return BlockStateVariantBuilder.property(BlockStateProperties.LIT).select(true, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_1_)).select(false, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_2_));
        }

        private static BlockStateVariantBuilder createHorizontalFacingDispatch() {
            return BlockStateVariantBuilder.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R90)).select(Direction.SOUTH, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R180)).select(Direction.WEST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R270)).select(Direction.NORTH, BlockModelDefinition.variant());
        }

        private static FinishedVariantBlockState createSimpleBlock(Block p_239978_0_, ResourceLocation p_239978_1_) {
            return FinishedVariantBlockState.multiVariant(p_239978_0_, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239978_1_));
        }

        private void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
            ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
            ResourceLocation resourcelocation1 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
            }).createWithSuffix(p_239977_1_, "_on", this.modelOutput);
            this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation)).with(createHorizontalFacingDispatch()));
        }


        private void createTrivialCube(Block p_239975_1_) {
            this.createTrivialBlock(p_239975_1_, TexturedModel.CUBE);
        }

        private void createTrivialBlock(Block p_239956_1_, TexturedModel.ISupplier p_239956_2_) {
            this.blockStateOutput.accept(createSimpleBlock(p_239956_1_, p_239956_2_.create(p_239956_1_, this.modelOutput)));
        }

        private void createSmithingTable() {
            ModelTextures modeltextures = (new ModelTextures()).put(StockTextureAliases.PARTICLE, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(StockTextureAliases.DOWN, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_bottom")).put(StockTextureAliases.UP, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_top")).put(StockTextureAliases.NORTH, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(StockTextureAliases.SOUTH, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(StockTextureAliases.EAST, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_side")).put(StockTextureAliases.WEST, ModelTextures.getBlockTexture(Blocks.SMITHING_TABLE, "_side"));
            this.blockStateOutput.accept(createSimpleBlock(Blocks.SMITHING_TABLE, StockModelShapes.CUBE.create(Blocks.SMITHING_TABLE, modeltextures, this.modelOutput)));
        }

        private void createCraftingTableLike(Block p_239875_1_, Block p_239875_2_, BiFunction<Block, Block, ModelTextures> p_239875_3_) {
            ModelTextures modeltextures = p_239875_3_.apply(p_239875_1_, p_239875_2_);
            this.blockStateOutput.accept(createSimpleBlock(p_239875_1_, StockModelShapes.CUBE.create(p_239875_1_, modeltextures, this.modelOutput)));
        }


        @Override
        public void run() {
            createFurnace(TABLE_BLOCK.get(), TexturedModel.ORIENTABLE_ONLY_TOP);

        }
    }
}





