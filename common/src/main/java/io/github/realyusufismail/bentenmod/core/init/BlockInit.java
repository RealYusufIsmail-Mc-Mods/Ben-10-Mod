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
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final RegistrationProvider<Block> BLOCKS =
            RegistrationProvider.get(Registries.BLOCK, BenTenMod.MOD_ID);

    // blocks
    public static final RegistryObject<Block> LEGENDARY_BLOCK =
            register("legendary_block", Blocks.NETHERITE_BLOCK);
    public static final RegistryObject<Block> OMNITRIX_BLOCK =
            register("omnitrix_block", Blocks.NETHERITE_BLOCK);
    public static final RegistryObject<Block> BLACK_DIAMOND_BLOCK =
            register("black_diamond_block", Blocks.DIAMOND_BLOCK);
    public static final RegistryObject<Block> INFINITUM_BLOCK =
            register("infinitum_block", Blocks.NETHERITE_BLOCK);
    public static final RegistryObject<Block> FIRE_BLOCK =
            register("fire_block", Blocks.DIAMOND_BLOCK);
    public static final RegistryObject<Block> SPEED_BLOCK =
            register("speed_block", Blocks.DIAMOND_BLOCK);
    public static final RegistryObject<Block> RUBY_BLOCK =
            register("ruby_block", Blocks.DIAMOND_BLOCK);
    public static final RegistryObject<Block> IMPERIUM_BLOCK =
            register("imperium_block", Blocks.IRON_BLOCK);
    public static final RegistryObject<Block> OMNITRIX_POD = registerSpecial("omnitrix_pod",
            () -> new Block(BlockBehaviour.Properties.of()
                .strength(50.0F, 1200.0F)
                .requiresCorrectToolForDrops()));

    // deepslate ores
    public static final RegistryObject<Block> DEEPSLATE_LEGENDARY_ORE =
            register("deepslate_legendary_ore", Blocks.ANCIENT_DEBRIS);
    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE =
            register("deepslate_ruby_ore", Blocks.DIAMOND_ORE);
    public static final RegistryObject<Block> DEEPSLATE_BLACK_DIAMOND_ORE =
            register("deepslate_black_diamond_ore", Blocks.DIAMOND_ORE);
    public static final RegistryObject<Block> DEEPSLATE_IMPERIUM_ORE =
            register("deepslate_imperium_ore", Blocks.IRON_BLOCK);
    public static final RegistryObject<Block> DEEPSLATE_SPEED_ORE =
            register("deepslate_speed_ore", Blocks.DIAMOND_ORE);
    public static final RegistryObject<Block> DEEPSLATE_OMNITRIX_ORE =
            register("deepslate_omnitrix_ore", Blocks.DIAMOND_ORE);

    // ores
    public static final RegistryObject<Block> LEGENDARY_ORE =
            register("legendary_ore", Blocks.NETHERITE_BLOCK);
    public static final RegistryObject<Block> RUBY_ORE =
            register("ruby_ore", Blocks.ANCIENT_DEBRIS);
    public static final RegistryObject<Block> IMPERIUM_ORE =
            register("imperium_ore", Blocks.DIAMOND_ORE);
    public static final RegistryObject<Block> BLACK_DIAMOND_ORE =
            register("black_diamond_ore", Blocks.ANCIENT_DEBRIS);
    public static final RegistryObject<Block> FIRE_ORE =
            register("fire_ore", Blocks.ANCIENT_DEBRIS);
    public static final RegistryObject<Block> SPEED_ORE =
            register("speed_ore", Blocks.ANCIENT_DEBRIS);
    public static final RegistryObject<Block> OMNITRIX_ORE =
            register("omnitrix_ore", Blocks.DIAMOND_ORE);
    public static final RegistryObject<RotatedPillarBlock> INFINITUM_ORE = registerSpecial(
            "infinitum_ore",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS)));


    // custom tables
    public static final RegistryObject<OmnitrixCrafter> OmnitrixCrafter;

    static {
        OmnitrixCrafter = registerSpecial("omnitrix_crafter", OmnitrixCrafter::new);
    }


    // helper methods for register both block and BlockItem at the same time.
    // Instead of put them in main class
    private static <T extends Block> RegistryObject<T> registerSpecial(String name,
            Supplier<T> supplier) {
        RegistryObject<T> blockReg = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties()));
        return blockReg;
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> supplier) {
        RegistryObject<Block> blockReg = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties()));
        return blockReg;
    }

    private static RegistryObject<Block> register(String name, Block existingBlock) {
        return register(name, () -> new Block(BlockBehaviour.Properties.copy(existingBlock)));
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
