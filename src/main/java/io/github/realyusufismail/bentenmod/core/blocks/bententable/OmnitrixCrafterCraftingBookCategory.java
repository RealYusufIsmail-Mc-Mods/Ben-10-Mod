package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import net.minecraft.util.StringRepresentable;

public enum OmnitrixCrafterCraftingBookCategory implements StringRepresentable {
    TOOLS("tools"),
    MISC("misc");

    private final String name;

    OmnitrixCrafterCraftingBookCategory(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static StringRepresentable.EnumCodec<OmnitrixCrafterCraftingBookCategory> codec() {
        return StringRepresentable.fromEnum(OmnitrixCrafterCraftingBookCategory::values);
    }
}

