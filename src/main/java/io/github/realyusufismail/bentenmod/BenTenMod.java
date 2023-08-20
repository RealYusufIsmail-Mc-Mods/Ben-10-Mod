/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.client.model.KraabModel;
import io.github.realyusufismail.bentenmod.client.renderer.KraabRenderer;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnnitrixCrafterScreen;
import io.github.realyusufismail.bentenmod.core.init.*;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.core.itemgroup.BenTenModItemGroup;
import io.github.realyusufismail.bentenmod.data.DataGenerators;
import io.github.realyusufismail.bentenmod.entity.CrabBoltEntity;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import io.github.realyusufismail.bentenmod.events.PlayerActionHandler;
import io.github.realyusufismail.bentenmod.util.ModResourceLocation;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.GeckoLib;

@Mod(BenTenMod.MOD_ID)
public class BenTenMod {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "bentenmod";
    public static ModelLayerLocation KRAAB =
            new ModelLayerLocation(new ResourceLocation(BenTenMod.MOD_ID, "kraab"), "main");

    public BenTenMod() {
        final var bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityTypesInit.ENTITY_TYPES.register(bus);
        MenuTypeInit.MENUS.register(bus);
        RecipeTypeInit.TYPES.register(bus);
        RecipeSerializerInit.SERIALIZERS.register(bus);

        // listener to events
        bus.addListener(RecipeCategoriesInit::registerRecipeCategories);
        bus.addListener(this::commonSetup);
        bus.addListener(this::onRegisterEntityRendererLayerDefinitions);
        bus.addListener(this::onRegisterEntities);
        bus.addListener(DataGenerators::gatherData);
        bus.addListener(BenTenModItemGroup::registerCreativeTab);
        bus.addListener(RecipeCategoriesInit::registerRecipeCategories);
        bus.addListener(PlayerActionHandler::register);

        GeckoLib.shadowInit();
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Armour, item, and entities loaded");
    }

    public static ModResourceLocation getId(@NotNull String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }

    public static ResourceLocation getModIdAndName(@NotNull String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MenuScreens.register(MenuTypeInit.OmnitrixCrafterMenuType.get(),
                OmnnitrixCrafterScreen::new);
        EntityRenderers.register(EntityTypesInit.CRAB_ENTITY.get(), KraabRenderer::new);
        EntityRenderers.register(EntityTypesInit.CRAB_BOLT_ENTITY.get(),
                m -> new EntityRenderer<>(m) {
                    @Override
                    public ResourceLocation getTextureLocation(@NotNull CrabBoltEntity entity) {
                        return null;
                    }
                });
    }

    public void onRegisterEntityRendererLayerDefinitions(
            EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KRAAB, KraabModel::createLayer);
    }

    public void onRegisterEntities(EntityAttributeCreationEvent entityAttributeCreationEvent) {

        ModSpawnEggItem.initSpawnEggs();

        // This is where to register the actual attributes of the entities
        entityAttributeCreationEvent.put(EntityTypesInit.CRAB_ENTITY.get(),
                CrabEntity.registerAttributes().build());
    }
}
