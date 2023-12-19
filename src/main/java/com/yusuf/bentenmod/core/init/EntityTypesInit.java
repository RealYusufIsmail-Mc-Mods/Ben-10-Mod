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
package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmod.entity.KraabBoltEntity;
import com.yusuf.bentenmod.entity.KraabEntity;
import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypesInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, BenTenMod.MOD_ID);
    private static final Properties spawn_egg_props = new Item.Properties().tab(MainItemGroup.MAIN);

    private static final EntityType<VilgaxEntity> vilgax =
            createStandardEntityType("vilgax", VilgaxEntity::new, EntityClassification.MONSTER, 1f, 1f);
    // registering the entities
    public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY =
            ENTITY_TYPES.register("vilgax", () -> vilgax);
    // register spawn eggs
    public static final RegistryObject<Item> VILGAX_SPAWN_EGG = ItemInit.ITEMS.register(
            "vilgax_spawn_egg", () -> new SpawnEggItem(vilgax, 0xC4AA79, 0x7A5F22, spawn_egg_props));
    private static final EntityType<KraabEntity> kraab =
            createStandardEntityType("kraab", KraabEntity::new, EntityClassification.MONSTER, 1.3f, 1.8f);
    public static final RegistryObject<EntityType<KraabEntity>> KRAAB_ENTITY =
            ENTITY_TYPES.register("kraab", () -> kraab);
    public static final RegistryObject<Item> KRAAB_SPAWN_EGG = ItemInit.ITEMS.register(
            "kraab_spawn_egg", () -> new SpawnEggItem(kraab, 0xC4AA79, 0x7A5F22, spawn_egg_props));
    private static final EntityType<KraabBoltEntity> kraabBolt =
            createStandardEntityType("kraab", KraabBoltEntity::new, EntityClassification.MISC, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<KraabBoltEntity>> KRAAB_BOLT_ENTITY =
            ENTITY_TYPES.register("kraab_bolt", () -> kraabBolt);

    // This is where to register the actual attributes of the entities
    public static void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(
                EntityTypesInit.VILGAX_ENTITY.get(),
                VilgaxEntity.registerAttributes().build());
        GlobalEntityTypeAttributes.put(
                EntityTypesInit.KRAAB_ENTITY.get(),
                KraabEntity.registerAttributes().build());
    }

    private static <T extends Entity> EntityType<T> createStandardEntityType(
            String entity_name,
            EntityType.IFactory<T> factory,
            EntityClassification classification,
            float width,
            float height) {
        return EntityType.Builder.of(factory, classification)
                .sized(width, height)
                .build(BenTenMod.MOD_ID + ":" + entity_name);
    }
}
