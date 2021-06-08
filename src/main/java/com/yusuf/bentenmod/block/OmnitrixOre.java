package com.yusuf.bentenmod.block;

import net.minecraft.block.*;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class OmnitrixOre extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;



    public OmnitrixOre() {
        super(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)
        );
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));


    }


    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        if (p_180655_1_.getValue(LIT)) {
            spawnParticles(p_180655_2_, p_180655_3_);
        }

    }

    private static void spawnParticles(World p_180691_0_, BlockPos p_180691_1_) {
        double d0 = 0.5625D;
        Random random = p_180691_0_.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = p_180691_1_.relative(direction);
            if (!p_180691_0_.getBlockState(blockpos).isSolidRender(p_180691_0_, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)random.nextFloat();
                p_180691_0_.addParticle(RedstoneParticleData.REDSTONE, (double)p_180691_1_.getX() + d1, (double)p_180691_1_.getY() + d2, (double)p_180691_1_.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(LIT);
    }


}
