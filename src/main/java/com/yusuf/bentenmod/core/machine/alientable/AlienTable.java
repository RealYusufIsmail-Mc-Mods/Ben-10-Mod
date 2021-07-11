package com.yusuf.bentenmod.core.machine.alientable;

import com.yusuf.bentenmod.common.LangKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see CraftingTableBlock
 */
public class AlienTable extends Block {

    private static final ITextComponent AlienTableContainer = new TranslationTextComponent("container.alien_table");

    /**
     * @see TranslationTextComponent
     * @see Container
     * @see
     */

    public AlienTable() {
        super(Properties.of(Material.HEAVY_METAL)
                .strength(3)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHER_ORE)
        );
    }



    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return ActionResultType.CONSUME;
        }
    }

    /**
     * @see Stats
     * @param p_190948_1_
     * @param p_190948_2_
     * @param p_190948_3_
     * @param p_190948_4_
     */
    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> p_190948_3_, ITooltipFlag p_190948_4_) {
        p_190948_3_.add(LangKeys.ALIEN_TABLE_DISC);
    }

    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos) {
        return new AlienTableSimpleNamedContainerProvider((id, inventory, player) -> {
            return new AlienTableContainer(id, inventory, IWorldPosCallable.create(world, pos));
        }, AlienTableContainer);

    }
}

