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
package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.client.renderer.VilgaxRenderer;
import io.github.realyusufismail.bentenmod.common.events.OmnitrixTickHandler;
import io.github.realyusufismail.bentenmod.core.capability.IOmnitrixData;
import io.github.realyusufismail.bentenmod.core.capability.OmnitrixDataImpl;
import io.github.realyusufismail.bentenmod.core.capability.OmnitrixDataStorage;
import io.github.realyusufismail.bentenmod.core.config.OmnitrixConfig;
import io.github.realyusufismail.bentenmod.core.init.*;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import io.github.realyusufismail.bentenmod.core.util.ModResourceLocation;
import io.github.realyusufismail.bentenmod.data.DataGenerators;
import io.github.realyusufismail.bentenmod.entity.KraabBoltEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;
import top.theillusivec4.curios.api.SlotTypeMessage;

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
        bus.addListener(this::onCommonSetup);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenerationInit::addOres);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new OmnitrixTickHandler());

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, OmnitrixConfig.SPEC);
    }

    public static ModResourceLocation getId(String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }

    public void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(IOmnitrixData.class, new OmnitrixDataStorage(), OmnitrixDataImpl::new);
            PacketHandler.init();
        });

        // Register the Omnitrix wrist slot with Curios API
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("wrist").size(1).build());
    }

    public void onBiomeLoad(BiomeLoadingEvent event) {
        EntitySpawingInit.onBiomesLoad(event);
    }

    public void onClientSetup(final FMLClientSetupEvent event) {
        KeybindsInit.register(event);
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
