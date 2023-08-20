package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OmnitrixWatch extends Item {
    public OmnitrixWatch(Properties pProperties) {
        super(pProperties);
    }

    public static OmnitrixWatch getOmnitrix(Player player) {
        return null;
    }

    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        // TODO: Make that the item is then eqipped and can not be unequipped
    }

    public void openScreen(Player player) {

    }

    public void transform(Ben10Aliens alien) {

    }
}
