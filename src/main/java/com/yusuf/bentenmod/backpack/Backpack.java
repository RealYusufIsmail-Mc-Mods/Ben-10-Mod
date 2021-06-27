package com.yusuf.bentenmod.backpack;

import com.yusuf.bentenmod.Main;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

public enum Backpack {
    BEN_TEN_BACKPACK(Rarity.EPIC, 18, "ben_ten_backpack_gui.png", 176, 150, 7, 67);

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
