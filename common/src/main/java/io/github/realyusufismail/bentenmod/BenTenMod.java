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

import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.armorset.ArmorSets;
import io.github.realyusufismail.bentenmod.core.init.*;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.core.itemgroup.BenTenModItemGroup;
import io.github.realyusufismail.bentenmod.data.CodecJsonReloadListener;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import io.github.realyusufismail.bentenmod.platform.Services;
import io.github.realyusufismail.bentenmod.util.ModResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Shared entry point. Each loader calls {@link #init()} while its registries are open, then
 * {@link #commonSetup()} once registration is finished.
 */
public final class BenTenMod {
    public static final String MOD_ID = "bentenmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private BenTenMod() {}

    /** Creates every registry entry. Order matters on Fabric, which registers immediately. */
    public static void init() {
        BlockInit.init();
        ItemInit.init();
        FeatureInit.init();
        EntityTypesInit.init();
        MenuTypeInit.init();
        RecipeTypeInit.init();
        RecipeSerializerInit.init();
        BenTenModItemGroup.init();
        LOGGER.info("Ben 10 mod registered its content on {}", Services.PLATFORM.getPlatformName());
    }

    /** Runs after registration: things that need the registered objects. */
    public static void commonSetup() {
        ModSpawnEggItem.initSpawnEggs();
    }

    /** Data-pack reload listeners. Each loader registers these for server data. */
    public static List<CodecJsonReloadListener<?>> reloadListeners() {
        return List.of(Aliens.DATA, ArmorSets.DATA);
    }

    /** Default attributes for every living entity in the mod. */
    public static void registerEntityAttributes(
            BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> registrar) {
        registrar.accept(EntityTypesInit.CRAB_ENTITY.get(),
                CrabEntity.registerAttributes().build());
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
}
