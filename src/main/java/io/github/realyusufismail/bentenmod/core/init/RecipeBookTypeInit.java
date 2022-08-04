package io.github.realyusufismail.bentenmod.core.init;

import net.minecraft.world.inventory.RecipeBookType;

public enum RecipeBookTypeInit implements net.minecraftforge.common.IExtensibleEnum {
    OMNITRIX_CRAFTING;

    public static RecipeBookTypeInit create(String name) {
        throw new IllegalStateException("Enum not extended!");
    }

    public RecipeBookType get() {
        if (this == RecipeBookTypeInit.OMNITRIX_CRAFTING) {
            return RecipeBookType.CRAFTING;
        } else {
            throw new IllegalStateException("Enum not extended!");
        }
    }
}
