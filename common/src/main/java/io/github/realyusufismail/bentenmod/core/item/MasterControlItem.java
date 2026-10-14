package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MasterControlItem extends Item {
    public MasterControlItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
            InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.pass(stack);
        }
        if (!OmnitrixService.isEquipped(serverPlayer)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.bentenmod.master_control_requires_watch"),
                    true);
            return InteractionResultHolder.fail(stack);
        }

        OmnitrixService.applyMasterControl(serverPlayer);
        if (!serverPlayer.getAbilities().instabuild) {
            stack.shrink(1);
        }
        serverPlayer.displayClientMessage(
                Component.translatable("message.bentenmod.master_control_applied"), true);
        level.playSound(null, serverPlayer.blockPosition(), SoundEvents.BEACON_POWER_SELECT,
                SoundSource.PLAYERS, 1.0F, 1.4F);
        if (serverPlayer.connection != null) {
            Services.PLATFORM.syncOmnitrixState(serverPlayer);
        }
        return InteractionResultHolder.consume(stack);
    }
}
