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
package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.BenTenMod;
import java.awt.event.KeyEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
    public static KeyBinding openwatchkey;

    public static void register(final FMLClientSetupEvent event) {

        openwatchkey = create("openwatch_key", KeyEvent.VK_K);

        ClientRegistry.registerKeyBinding(openwatchkey);
    }

    private static KeyBinding create(String name, int key) {
        return new KeyBinding("key." + BenTenMod.MOD_ID + "." + name, key, "key.category." + BenTenMod.MOD_ID);
    }
}
