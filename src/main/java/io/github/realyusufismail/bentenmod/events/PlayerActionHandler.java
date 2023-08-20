package io.github.realyusufismail.bentenmod.events;

import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixWatch;
import lombok.val;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * This class is used to handle player actions
 *
 * @see net.minecraftforge.common.ForgeHooks
 */
public class PlayerActionHandler extends Event {
    public static void register(IEventBus bus) {
        bus.addListener(PlayerActionHandler::onPlayerInteract);
    }

    private static void onPlayerInteract(Event event) {
        if (event instanceof PlayerInteractEvent playerInteractEvent) {
            val player = playerInteractEvent.getEntity();
            val hand = playerInteractEvent.getHand();
            if (playerInteractEvent instanceof PlayerInteractEvent.RightClickItem) {
                playerInteractEvent.setCanceled(onItemRightClick(player, hand) == InteractionResult.SUCCESS);
            }
        }
    }

    public static InteractionResult onItemRightClick(Player player, InteractionHand hand) {
        //on right click open omnitrix gui
        //get item
        val item = player.getItemInHand(hand);
        if (item.getItem() instanceof OmnitrixWatch omnitrix) {
            //open screen
            omnitrix.openScreen(player);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}
