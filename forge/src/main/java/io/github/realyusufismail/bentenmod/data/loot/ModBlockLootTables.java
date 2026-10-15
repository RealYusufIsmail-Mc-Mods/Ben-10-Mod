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
package io.github.realyusufismail.bentenmod.data.loot;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // ores
        add(BlockInit.BLACK_DIAMOND_ORE.get(), createOreDrop(BlockInit.BLACK_DIAMOND_ORE.get(),
                ItemInit.BLACK_DIAMOND_SCRAP.get()));

        add(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(), createOreDrop(
                BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get(), ItemInit.BLACK_DIAMOND_SCRAP.get()));

        add(BlockInit.INFINITUM_ORE.get(),
                createOreDrop(BlockInit.INFINITUM_ORE.get(), ItemInit.INFINITUM_SCRAP.get()));

        add(BlockInit.SPEED_ORE.get(),
                createOreDrop(BlockInit.SPEED_ORE.get(), ItemInit.SPEED.get()));

        add(BlockInit.DEEPSLATE_SPEED_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_SPEED_ORE.get(), ItemInit.SPEED.get()));

        add(BlockInit.OMNITRIX_ORE.get(),
                createOreDrop(BlockInit.OMNITRIX_ORE.get(), ItemInit.RAW_OMNITRIX.get()));

        add(BlockInit.DEEPSLATE_OMNITRIX_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_OMNITRIX_ORE.get(), ItemInit.RAW_OMNITRIX.get()));

        add(BlockInit.IMPERIUM_ORE.get(),
                createOreDrop(BlockInit.IMPERIUM_ORE.get(), ItemInit.RAW_IMPERIUM.get()));

        add(BlockInit.DEEPSLATE_IMPERIUM_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_IMPERIUM_ORE.get(), ItemInit.RAW_IMPERIUM.get()));

        add(BlockInit.LEGENDARY_ORE.get(),
                createOreDrop(BlockInit.LEGENDARY_ORE.get(), ItemInit.RAW_LEGENDARY.get()));

        add(BlockInit.DEEPSLATE_LEGENDARY_ORE.get(), createOreDrop(
                BlockInit.DEEPSLATE_LEGENDARY_ORE.get(), ItemInit.RAW_LEGENDARY.get()));

        add(BlockInit.RUBY_ORE.get(), createOreDrop(BlockInit.RUBY_ORE.get(), ItemInit.RUBY.get()));

        add(BlockInit.DEEPSLATE_RUBY_ORE.get(),
                createOreDrop(BlockInit.DEEPSLATE_RUBY_ORE.get(), ItemInit.RUBY.get()));

        add(BlockInit.NULL_CRYSTAL_ORE.get(),
                createOreDrop(BlockInit.NULL_CRYSTAL_ORE.get(), ItemInit.NULL_CRYSTAL.get()));
        add(BlockInit.TAYDENITE_ORE.get(),
                createOreDrop(BlockInit.TAYDENITE_ORE.get(), ItemInit.TAYDENITE.get()));

        // blocks
        dropSelf(BlockInit.OMNITRIX_BLOCK.get());
        dropSelf(BlockInit.FIRE_BLOCK.get());
        dropSelf(BlockInit.RUBY_BLOCK.get());
        dropSelf(BlockInit.SPEED_BLOCK.get());
        dropSelf(BlockInit.INFINITUM_BLOCK.get());
        dropSelf(BlockInit.BLACK_DIAMOND_BLOCK.get());
        dropSelf(BlockInit.LEGENDARY_BLOCK.get());
        dropSelf(BlockInit.IMPERIUM_BLOCK.get());
        dropSelf(BlockInit.NULL_VOID_STONE.get());
        dropSelf(BlockInit.NULL_VOID_STONE_BRICKS.get());
        dropSelf(BlockInit.TAYDENITE_BLOCK.get());
        dropSelf(BlockInit.PRISON_WALL.get());
        dropSelf(BlockInit.PLUMBER_HULL.get());
        dropSelf(BlockInit.PRISON_VAULT.get());
        dropSelf(BlockInit.FIRE_ORE.get());
        dropSelf(BlockInit.OmnitrixCrafter.get());
        add(BlockInit.OMNITRIX_POD.get(), createSingleItemTable(ItemInit.OMNITRIX_WATCH.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues()
            .stream()
            .filter(entry -> Optional.ofNullable(ForgeRegistries.BLOCKS.getKey(entry))
                .filter(key -> key.getNamespace().equals(BenTenMod.MOD_ID))
                .isPresent())
            .collect(Collectors.toSet());
    }
}

