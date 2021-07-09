
package com.yusuf.bentenmod.data.client;

import com.google.gson.JsonElement;
import com.yusuf.bentenmod.BenTenMod;

import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import static com.yusuf.bentenmod.core.init.BlockInit.*;

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
            simpleBlock(COPPER_ORE.get());
            simpleBlock(COPPER_BLOCK.get());
            //createFurnace(TABLE_BLOCK.get(), TexturedModel.ORIENTABLE_ONLY_TOP);

    }

}





