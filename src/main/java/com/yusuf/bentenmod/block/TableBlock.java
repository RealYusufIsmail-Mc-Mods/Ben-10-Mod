package com.yusuf.bentenmod.block;

import com.yusuf.bentenmod.block.tablemachine.TableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class TableBlock extends Block {

    public TableBlock() {
        super(Properties.of(Material.WOOD)
                .strength(3)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops()
                .sound(SoundType.WOOD)
        );
    }


    //handle gui {@Link NetworkHooks #Open gui}
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState p_225533_1_, World level, BlockPos pos, PlayerEntity playerEntity, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        super.use(p_225533_1_, level, pos, playerEntity, p_225533_5_, p_225533_6_);
        if (!level.isClientSide()) {
            TileEntity te = level.getBlockEntity(pos);
            if (te instanceof TableTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) te, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    /**
     * drop items inside block when break
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World level, BlockPos pos, BlockState state1, boolean p_196243_5_) {
        if (!state.is(state1.getBlock())) {
            TileEntity te = level.getBlockEntity(pos);
            if (te instanceof TableTileEntity) {
                InventoryHelper.dropContents(level, pos, (IInventory) te);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, state1, p_196243_5_);
    }
    /**
     * get the facing direction of block when placed down, this one works same as Furnace
     */


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TableTileEntity();
    }



}

