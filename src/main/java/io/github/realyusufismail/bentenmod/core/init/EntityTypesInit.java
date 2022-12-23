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

package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.entity.CrabBoltEntity;
import io.github.realyusufismail.bentenmod.entity.CrabEntity;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EntityTypesInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BenTenMod.MOD_ID);
    // registering the entities
    public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY =
            ENTITY_TYPES.register("vilgax", () -> createStandardEntityType("vilgax",
                    VilgaxEntity::new, MobCategory.MONSTER, 1f, 1f));
    public static final RegistryObject<EntityType<CrabEntity>> CRAB_ENTITY =
            ENTITY_TYPES.register("crab", () -> createStandardEntityType("crab", CrabEntity::new,
                    MobCategory.MONSTER, 1.3f, 1.8f));
    public static final RegistryObject<EntityType<CrabBoltEntity>> CRAB_BOLT_ENTITY =
            ENTITY_TYPES.register("crab_bolt", () -> createStandardEntityType("crab_bolt",
                    CrabBoltEntity::new, MobCategory.MISC, 0.5F, 0.5F));
    private static final Item.Properties spawn_egg_props = new Item.Properties();

    // register spawn eggs
    public static final RegistryObject<Item> VILGAX_SPAWN_EGG = ItemInit.ITEMS.register(
            "vilgax_spawn_egg",
            () -> new ForgeSpawnEggItem(VILGAX_ENTITY, 0xC4AA79, 0x7A5F22, spawn_egg_props));
    public static final RegistryObject<Item> CRAB_SPAWN_EGG =
            ItemInit.ITEMS.register("crab_spawn_egg",
                    () -> new ForgeSpawnEggItem(CRAB_ENTITY, 0xC4AA79, 0x7A5F22, spawn_egg_props));

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
}
