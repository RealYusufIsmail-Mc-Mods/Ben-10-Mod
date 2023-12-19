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
package com.yusuf.bentenmod;

import com.yusuf.bentenmod.core.init.*;
import com.yusuf.bentenmod.core.util.ModResourceLocation;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod("bentenmod")
public class BenTenMod {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "bentenmod";

    public BenTenMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityTypesInit.ENTITY_TYPES.register(bus);
        TileEntityInit.TILE_ENTITY.register(bus);
        ContainerInit.CONTAINERS.register(bus);

        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();

        bus.addGenericListener(IRecipeSerializer.class, RegisterRecipeInit::registerRecipes);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenerationInit::addOres);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.register(this);

        bus.register(this);
    }

    public static ModResourceLocation getId(String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        EntitySpawingInit.onBiomesLoad(event);
    }
}
