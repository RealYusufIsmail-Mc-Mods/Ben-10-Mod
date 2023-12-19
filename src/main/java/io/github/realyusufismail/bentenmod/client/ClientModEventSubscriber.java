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
package io.github.realyusufismail.bentenmod.client;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxRenderer;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.keybind.Keybindings;
import io.github.realyusufismail.bentenmod.entity.KraabBoltEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        Keybindings.RELOAD = new KeyBinding(
                "key." + BenTenMod.MOD_ID + ".reload", GLFW.GLFW_KEY_R, "key.categories." + BenTenMod.MOD_ID);
        ClientRegistry.registerKeyBinding(Keybindings.RELOAD);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.VILGAX_ENTITY.get(), VilgaxRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.KRAAB_ENTITY.get(), KraabRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityTypesInit.KRAAB_BOLT_ENTITY.get(), m -> new EntityRenderer<KraabBoltEntity>(m) {
                    @Override
                    public ResourceLocation getTextureLocation(KraabBoltEntity entity) {
                        return null;
                    }
                });
    }
}
