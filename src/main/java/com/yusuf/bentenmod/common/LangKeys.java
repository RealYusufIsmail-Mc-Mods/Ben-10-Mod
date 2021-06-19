package com.yusuf.bentenmod.common;

import com.yusuf.bentenmod.Main;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LangKeys {
    public static final ITextComponent TABLE_SCREEN = text("container", "Upgrdade");
    public static final ITextComponent TABLE_DISC = text("desc", "upgrade_table");
    //Method extracted
    private static TranslationTextComponent text(String s, String s2) {
        return new TranslationTextComponent(s  + "." + Main.MOD_ID + "." + s2);
    }
}
