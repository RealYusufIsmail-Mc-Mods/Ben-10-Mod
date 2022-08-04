package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import net.minecraft.world.inventory.RecipeBookType;

/**
 * @see RecipeBookType
 */
public enum BenTenRecipeBookType implements net.minecraftforge.common.IExtensibleEnum {
    OMNITRIX_CRAFTING;

    public static BenTenRecipeBookType create(String name) {
        throw new IllegalStateException("Enum not extended!");
    }

    @Override
    public void init() {
        String name = this.name().toLowerCase(java.util.Locale.ROOT).replace("_", "");
        BenTenRecipeBookSettings.addTagsForType(this, "is" + name + "GuiOpen",
                "is" + name + "FilteringCraftable");
    }
}
