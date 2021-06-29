
package com.yusuf.bentenmod.data.client;


import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

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

/*
    public static final class BlockModels extends BlockModelProvider {
        public final Consumer<IFinishedBlockState> blockStateOutput;
        public final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
        public BlockModels(Consumer<IFinishedBlockState> p_i232514_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232514_2_, Consumer<net.minecraft.item.Item> p_i232514_3_) {
            super(p_i232514_1_, p_i232514_2_, p_i232514_3_);

            blockStateOutput = p_i232514_1_;
            modelOutput = p_i232514_2_;
        }




 */





}





