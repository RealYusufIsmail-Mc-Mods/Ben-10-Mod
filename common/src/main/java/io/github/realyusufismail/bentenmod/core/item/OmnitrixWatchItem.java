package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixClientHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Existing watch item id, now opening the transformation dial when used. */
public class OmnitrixWatchItem extends Item {
    public OmnitrixWatchItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
            InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            OmnitrixClientHooks.openDial();
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
