package com.yusuf.bentenmod.data.client;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

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
        simpleBlock(LEGENDARY_ORE.get());
        simpleBlock(OMNITRIX_BLOCK.get());
        simpleBlock(OMNITRIX_ORE.get());
        simpleBlock(RUBY_BLOCK.get());
        simpleBlock(RUBY_ORE.get());
        simpleBlock(SPEED_BLOCK.get());
        simpleBlock(SPEED_ORE.get());
        simpleBlock(IMPERIUM_BLOCK.get());
        simpleBlock(IMPERIUM_ORE.get());
        simpleBlock(DEEPSLATE_BLACK_DIAMOND_ORE.get());
        simpleBlock(DEEPSLATE_SPEED_ORE.get());
        simpleBlock(DEEPSLATE_RUBY_ORE.get());
        simpleBlock(DEEPSLATE_IMPERIUM_ORE.get());
        simpleBlock(DEEPSLATE_OMNITRIX_ORE.get());
        simpleBlock(DEEPSLATE_LEGENDARY_ORE.get());
        // orientableMachineBlock(TABLE_BLOCK.get(), "table_block");
        tableBlock();
        tableBlockOn();
        alienTableBlock();

    }
    @Override
    public void horizontalBlock(Block block, ModelFile model) {
        super.horizontalBlock(block, model);
        this.simpleBlockItem(block, model);
    }
    public void tableBlock() {
        ResourceLocation name = TABLE_BLOCK.get().getRegistryName();
        assert name != null;
        BlockModelBuilder builder = this.models().withExistingParent(name.getPath(), "block/orientable");
        builder.texture("top", modLoc("block/table_block_top"));
        builder.texture("front", modLoc("block/table_block_front"));
        builder.texture("side", modLoc("block/table_block_side"));
        this.simpleBlockItem(TABLE_BLOCK.get(), builder);
    }
    public void tableBlockOn() {
        String name = "table_block_on";
        assert name != null;
        BlockModelBuilder builder = this.models().withExistingParent(name, "block/orientable");
        builder.texture("top", modLoc("block/table_block_top"));
        builder.texture("front", modLoc("block/table_block_front"));
        builder.texture("side", modLoc("block/table_block_side"));
    }
    public void alienTableBlock() {
        ResourceLocation name = ALIEN_TABLE.get().getRegistryName();
        assert name != null;
        BlockModelBuilder builder = this.models().withExistingParent(name.getPath(), "block/orientable");
        builder.texture("particle", modLoc("block/alien_table_front"));
        builder.texture("north", modLoc("block/alien_table_front"));
        builder.texture("south", modLoc("block/alien_table_side"));
        builder.texture("east", modLoc("block/alien_table_side"));
        builder.texture("west", modLoc("block/alien_table_front"));
        builder.texture("up", modLoc("block/alien_table_top"));
        builder.texture("down", modLoc("block/emerald_block"));
        this.simpleBlockItem(ALIEN_TABLE.get(), builder);
    }
}




