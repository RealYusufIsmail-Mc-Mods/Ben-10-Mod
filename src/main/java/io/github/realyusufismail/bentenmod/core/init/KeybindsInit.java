/*
 * Copyright 2026 RealYusufIsmail.
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
package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeybindsInit {
    /** Opens the Omnitrix radial selection GUI (default: O) */
    public static KeyBinding OMNITRIX_KEY;
    /** Manually reverts alien transformation (default: R) */
    public static KeyBinding REVERT_KEY;

    public static void register(final FMLClientSetupEvent event) {
        OMNITRIX_KEY = new KeyBinding(
                "key." + BenTenMod.MOD_ID + ".omnitrix",
                InputMappings.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "key.categories." + BenTenMod.MOD_ID);
        REVERT_KEY = new KeyBinding(
                "key." + BenTenMod.MOD_ID + ".revert",
                InputMappings.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "key.categories." + BenTenMod.MOD_ID);

        ClientRegistry.registerKeyBinding(OMNITRIX_KEY);
        ClientRegistry.registerKeyBinding(REVERT_KEY);
    }
}
