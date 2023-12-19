/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.yusuf.bentenmod.core.omnitrix;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class OmnitrixScreen extends Screen {
    protected OmnitrixScreen(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    public static final ResourceLocation SCREEN_ID =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix.png");
}
