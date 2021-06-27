package com.yusuf.bentenmod.backpack;

import com.yusuf.bentenmod.Main;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

/**
 * Represents a singleton instance of a backpack. Sets up each backpack in it's own way
 */
public enum Backpack {
    BEN_TEN_BACKPACK(Rarity.EPIC, 33, "ben_ten_backpack_gui.png", 212, 168, 25, 85);

    public Rarity rarity;
    public int slots;

    public ResourceLocation location;
    public int xSize;
    public int ySize;
    public int slotXOffset;
    public int slotYOffset;

    Backpack(Rarity rarity, int slots, String location, int xSize, int ySize, int slotXOffset, int slotYOffset) {
        this.rarity = rarity;
        this.slots = slots;
        this.location = new ResourceLocation(Main.MOD_ID, "textures/gui/" + location);
        this.xSize = xSize;
        this.ySize = ySize;
        this.slotXOffset = slotXOffset;
        this.slotYOffset = slotYOffset;
    }
}
