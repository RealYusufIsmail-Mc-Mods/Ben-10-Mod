package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.MenuTypeInit;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;

public class OmnitrixCrafterMenu extends CraftingMenu {
    private final ContainerLevelAccess access;

    public OmnitrixCrafterMenu(int p_39356_, Inventory p_39357_, ContainerLevelAccess p_39358_) {
        super(p_39356_, p_39357_, p_39358_);
        this.access = p_39358_;
    }

    public OmnitrixCrafterMenu(int p_39353_, Inventory p_39354_) {
        this(p_39353_, p_39354_, ContainerLevelAccess.NULL);
    }

    // Override to return your menu type that identifies the screen to use
    @Override
    public MenuType<?> getType() {
        return MenuTypeInit.OmnitrixCrafterMenuType.get();
    }

    // Override to identify the block instance (used to force the user out of the screen if the
    // block is destroyed)
    @Override
    public boolean stillValid(Player p_39368_) {
        return stillValid(this.access, p_39368_, BlockInit.TABLE_BLOCK.get());
    }
}
