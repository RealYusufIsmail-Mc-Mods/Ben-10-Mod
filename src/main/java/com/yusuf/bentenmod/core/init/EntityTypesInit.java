/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmod.entity.KraabBoltEntity;
import com.yusuf.bentenmod.entity.KraabEntity;
import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.structure.RuinedPortalPiece;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;


public class EntityTypesInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            BenTenMod.MOD_ID);
    private static final Item.Properties spawn_egg_props = new Item.Properties().tab(MainItemGroup.MAIN);

    private static final EntityType<VilgaxEntity> vilgax = createStandardEntityType("vilgax", VilgaxEntity::new,
            MobCategory.MONSTER, 1f, 1f);
    // registering the entities
    public static final RegistryObject<EntityType<VilgaxEntity>> VILGAX_ENTITY = ENTITY_TYPES.register("vilgax",
            () -> vilgax);
    // register spawn eggs
    public static final RegistryObject<Item> VILGAX_SPAWN_EGG = ItemInit.ITEMS.register("vilgax_spawn_egg",
            () -> new SpawnEggItem(vilgax, 0xC4AA79, 0x7A5F22, spawn_egg_props));
    private static final EntityType<KraabEntity> kraab = createStandardEntityType("kraab", KraabEntity::new,
            MobCategory.MONSTER, 1.3f, 1.8f);
    public static final RegistryObject<EntityType<KraabEntity>> KRAAB_ENTITY = ENTITY_TYPES.register("kraab",
            () -> kraab);
    public static final RegistryObject<Item> KRAAB_SPAWN_EGG = ItemInit.ITEMS.register("kraab_spawn_egg",
            () -> new SpawnEggItem(kraab, 0xC4AA79, 0x7A5F22, spawn_egg_props));
    private static final EntityType<KraabBoltEntity> kraabBolt = createStandardEntityType("kraab", KraabBoltEntity::new,
            MobCategory.MISC, 0.5F, 0.5F);
    public static final RegistryObject<EntityType<KraabBoltEntity>> KRAAB_BOLT_ENTITY = ENTITY_TYPES.register("kraab_bolt",
            () -> kraabBolt);

    private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name,
                                                                             EntityType.EntityFactory<T> factory, MobCategory classification, float width, float height) {
        return EntityType.Builder.of(factory, classification).sized(width, height)
                .build(BenTenMod.MOD_ID + ":" + entity_name);
    }
}
