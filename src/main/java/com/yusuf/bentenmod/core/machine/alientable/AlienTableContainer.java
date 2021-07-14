package com.yusuf.bentenmod.core.machine.alientable;

import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AlienTableContainer extends WorkbenchContainer {
    private final IWorldPosCallable access;



    public AlienTableContainer(int id, PlayerInventory playerInventory, PlayerEntity playerEntity, BlockPos pos, World world) {
        this(id, playerInventory, playerEntity, pos, world, IWorldPosCallable.NULL);
    }


    public AlienTableContainer(int id, PlayerInventory playerInventory, PlayerEntity playerEntity, BlockPos pos, World world, IWorldPosCallable access) {
        super(ContainerInit.ALIEN_TABLE_CONTAINER, id, playerInventory, playerEntity, pos, world);
        this.access = access;
    }



    @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(this.access, player, BlockInit.ALIEN_TABLE.get());
    }
}
