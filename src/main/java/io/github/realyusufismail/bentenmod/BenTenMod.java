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
package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxRenderer;
import io.github.realyusufismail.bentenmod.core.init.*;
import io.github.realyusufismail.bentenmod.core.keybind.Keybindings;
import io.github.realyusufismail.bentenmod.core.util.ModResourceLocation;
import io.github.realyusufismail.bentenmod.data.DataGenerators;
import io.github.realyusufismail.bentenmod.entity.KraabBoltEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod("bentenmod")
public class BenTenMod {
    public static final String MOD_ID = "bentenmod";

    public BenTenMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityTypesInit.ENTITY_TYPES.register(bus);
        TileEntityInit.TILE_ENTITY.register(bus);
        ContainerInit.CONTAINERS.register(bus);

        GeckoLib.initialize();

        bus.addGenericListener(IRecipeSerializer.class, RegisterRecipeInit::registerRecipes);
        bus.addListener(DataGenerators::gatherData);
        bus.addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenerationInit::addOres);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ModResourceLocation getId(String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }

    public void onBiomeLoad(BiomeLoadingEvent event) {
        EntitySpawingInit.onBiomesLoad(event);
    }

    public void onClientSetup(final FMLClientSetupEvent event) {
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
