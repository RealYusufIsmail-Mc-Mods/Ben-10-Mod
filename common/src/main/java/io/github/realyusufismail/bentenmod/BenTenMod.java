/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod;

import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.armorset.ArmorSets;
import io.github.realyusufismail.bentenmod.core.init.*;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.core.itemgroup.BenTenModItemGroup;
import io.github.realyusufismail.bentenmod.data.CodecJsonReloadListener;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import io.github.realyusufismail.bentenmod.entity.NullVoidPrisonerEntity;
import io.github.realyusufismail.bentenmod.entity.NullGuardianEntity;
import io.github.realyusufismail.bentenmod.entity.LepidopterranEntity;
import io.github.realyusufismail.bentenmod.entity.PrisonWardenEntity;
import io.github.realyusufismail.bentenmod.entity.VilgaxDroneEntity;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import io.github.realyusufismail.bentenmod.entity.ZsSkayrEntity;
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
        registrar.accept(EntityTypesInit.VILGAX_ENTITY.get(),
                VilgaxEntity.registerAttributes().build());
        registrar.accept(EntityTypesInit.VILGAX_DRONE_ENTITY.get(),
                VilgaxDroneEntity.registerAttributes().build());
        registrar.accept(EntityTypesInit.NULL_VOID_PRISONER.get(),
                NullVoidPrisonerEntity.registerAttributes().build());
        registrar.accept(EntityTypesInit.NULL_GUARDIAN.get(),
                NullGuardianEntity.registerAttributes().build());
        registrar.accept(EntityTypesInit.LEPIDOPTERAN.get(),
                LepidopterranEntity.registerAttributes().build());
        registrar.accept(EntityTypesInit.PRISON_WARDEN.get(),
                PrisonWardenEntity.registerAttributes().build());
        registrar.accept(EntityTypesInit.ZS_SKAYR_ENTITY.get(),
                ZsSkayrEntity.registerAttributes().build());
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
