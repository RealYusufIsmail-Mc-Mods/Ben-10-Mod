package com.yusuf.bentenmod.core.machine.alientable;

import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AlienTableContainer extends WorkbenchContainer {
    private final IWorldPosCallable access;




    public AlienTableContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity, IWorldPosCallable p_i50090_3_) {
        super(id, world, pos, playerInventory, playerEntity);
        this.access = p_i50090_3_;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(this.access, player, BlockInit.ALIEN_TABLE.get());
    }
}
