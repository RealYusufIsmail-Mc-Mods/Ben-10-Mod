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
package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.CrabBoltEntity;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import io.github.realyusufismail.bentenmod.entity.NullVoidPrisonerEntity;
import io.github.realyusufismail.bentenmod.entity.NullGuardianEntity;
import io.github.realyusufismail.bentenmod.entity.LepidopterranEntity;
import io.github.realyusufismail.bentenmod.entity.PrisonWardenEntity;
import io.github.realyusufismail.bentenmod.entity.SlimeSpitEntity;
import io.github.realyusufismail.bentenmod.entity.VilgaxDroneEntity;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import io.github.realyusufismail.bentenmod.entity.ZsSkayrEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import io.github.realyusufismail.bentenmod.core.item.ModSpawnEggItem;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;


public class EntityTypesInit {
    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES =
            RegistrationProvider.get(Registries.ENTITY_TYPE, BenTenMod.MOD_ID);
    // registering the entities
    public static final RegistryObject<EntityType<CrabEntity>> CRAB_ENTITY =
            ENTITY_TYPES.register("crab", () -> createStandardEntityType("crab", CrabEntity::new,
                    MobCategory.MONSTER, 1.3f, 1.8f));
    public static final RegistryObject<EntityType<CrabBoltEntity>> CRAB_BOLT_ENTITY =
            ENTITY_TYPES.register("crab_bolt", () -> createStandardEntityType("crab_bolt",
                    CrabBoltEntity::new, MobCategory.MISC, 0.5F, 0.5F));
    public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY =
            ENTITY_TYPES.register("vilgax",
                    () -> EntityType.Builder.of(VilgaxEntity::new, MobCategory.MONSTER)
                        .sized(1.4F, 3.4F)
                        .fireImmune()
                        .build(BenTenMod.MOD_ID + ":vilgax"));
    public static final RegistryObject<EntityType<VilgaxDroneEntity>> VILGAX_DRONE_ENTITY =
            ENTITY_TYPES.register("vilgax_drone", () -> createStandardEntityType("vilgax_drone",
                    VilgaxDroneEntity::new, MobCategory.MONSTER, 0.8F, 0.8F));
    public static final RegistryObject<EntityType<NullVoidPrisonerEntity>> NULL_VOID_PRISONER =
            ENTITY_TYPES.register("null_void_prisoner",
                    () -> createStandardEntityType("null_void_prisoner",
                            NullVoidPrisonerEntity::new, MobCategory.MONSTER, 0.8F, 2.0F));
    public static final RegistryObject<EntityType<NullGuardianEntity>> NULL_GUARDIAN =
            ENTITY_TYPES.register("null_guardian", () -> createStandardEntityType("null_guardian",
                    NullGuardianEntity::new, MobCategory.MONSTER, 2.2F, 2.0F));
    public static final RegistryObject<EntityType<LepidopterranEntity>> LEPIDOPTERAN =
            ENTITY_TYPES.register("lepidopterran", () -> createStandardEntityType("lepidopterran",
                    LepidopterranEntity::new, MobCategory.MONSTER, 1.0F, 1.2F));
    public static final RegistryObject<EntityType<PrisonWardenEntity>> PRISON_WARDEN =
            ENTITY_TYPES.register("prison_warden", () -> createStandardEntityType("prison_warden",
                    PrisonWardenEntity::new, MobCategory.MONSTER, 1.3F, 2.8F));
    public static final RegistryObject<EntityType<SlimeSpitEntity>> LEPIDOPTERAN_SLIME =
            ENTITY_TYPES.register("lepidopterran_slime",
                    () -> createStandardEntityType("lepidopterran_slime", SlimeSpitEntity::new,
                            MobCategory.MISC, 0.25F, 0.25F));
    public static final RegistryObject<EntityType<ZsSkayrEntity>> ZS_SKAYR_ENTITY =
            ENTITY_TYPES.register("zs_skayr", () -> createStandardEntityType("zs_skayr",
                    ZsSkayrEntity::new, MobCategory.MONSTER, 1.2F, 3.0F));
    private static final Item.Properties spawn_egg_props = new Item.Properties();

    // register spawn eggs
    public static final RegistryObject<Item> CRAB_SPAWN_EGG =
            ItemInit.ITEMS.register("crab_spawn_egg",
                    () -> new ModSpawnEggItem(CRAB_ENTITY, 0xC4AA79, 0x7A5F22, spawn_egg_props));
    public static final RegistryObject<Item> VILGAX_SPAWN_EGG =
            ItemInit.ITEMS.register("vilgax_spawn_egg",
                    () -> new ModSpawnEggItem(VILGAX_ENTITY, 0x4D9A52, 0x6D1717, spawn_egg_props));
    public static final RegistryObject<Item> VILGAX_DRONE_SPAWN_EGG = ItemInit.ITEMS.register(
            "vilgax_drone_spawn_egg",
            () -> new ModSpawnEggItem(VILGAX_DRONE_ENTITY, 0x383838, 0xD23A3A, spawn_egg_props));

    private EntityTypesInit() {
        throw new IllegalStateException("Entity gen class");
    }

    private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
            EntityType.EntityFactory<T> factory, MobCategory classification, float width,
            float height) {
        return EntityType.Builder.of(factory, classification)
            .sized(width, height)
            .build(BenTenMod.MOD_ID + ":" + entity_name);
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
