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

/** The watch is consumed once to bind it permanently to a player's saved Omnitrix state. */
public class OmnitrixWatchItem extends Item {
    public OmnitrixWatchItem(Properties properties) {
        super(properties.stacksTo(1));
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

        if (!OmnitrixService.equip(serverPlayer)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.bentenmod.omnitrix_already_equipped"), true);
            return InteractionResultHolder.consume(stack);
        }

        stack.shrink(1);
        serverPlayer.displayClientMessage(
                Component.translatable("message.bentenmod.omnitrix_equipped"), true);
        level.playSound(null, serverPlayer.blockPosition(), SoundEvents.ARMOR_EQUIP_IRON,
                SoundSource.PLAYERS, 1.0F, 1.0F);
        if (serverPlayer.connection != null) {
            Services.PLATFORM.syncOmnitrixState(serverPlayer);
        }
        return InteractionResultHolder.consume(stack);
    }
}
