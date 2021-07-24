package com.yusuf.bentenmod.core.omnitrix;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class OmnitrixScreen extends Screen {
    protected OmnitrixScreen(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }
    public static final ResourceLocation SCREEN_ID = new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix.png");
    
}
