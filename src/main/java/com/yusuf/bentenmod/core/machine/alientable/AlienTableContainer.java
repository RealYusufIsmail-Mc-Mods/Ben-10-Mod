
package com.yusuf.bentenmod.core.machine.alientable;

import com.yusuf.bentenmod.core.init.BlockInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class AlienTableContainer extends WorkbenchContainer {
    //private final IWorldPosCallable access;

    /*
       public AlienTableContainer(int id, PlayerInventory playerInventory, IWorldPosCallable access) {
        super(id, playerInventory, access);
        this.access = access;
    }
     */

    public AlienTableContainer(int id, World level, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(id, playerInventory);

    }

/*
 @Override
    public boolean stillValid(PlayerEntity player) {
        return stillValid(this.access, player, BlockInit.ALIEN_TABLE.get());
    }
 */


}


