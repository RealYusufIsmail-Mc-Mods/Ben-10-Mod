/*
package com.yusuf.bentenmod.core.machine.testtable;

import com.yusuf.bentenmod.core.machine.bententable.TableTileEntity;
import com.yusuf.bentenmod.core.machine.testtable.TestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class TestTable extends Block {

    public TestTable() {
        super(Properties.of(Material.HEAVY_METAL)
                .strength(3)
                .harvestTool(ToolType.AXE)
                .harvestLevel(2)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHER_ORE)
        );
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState p_225533_1_, World level, BlockPos pos, PlayerEntity playerEntity, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        super.use(p_225533_1_, level, pos, playerEntity, p_225533_5_, p_225533_6_);
        if (!level.isClientSide()) {
            TileEntity te = level.getBlockEntity(pos);
            if (te instanceof TestTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) te, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }
}


 */