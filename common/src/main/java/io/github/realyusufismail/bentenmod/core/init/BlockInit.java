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
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafter;
import io.github.realyusufismail.bentenmod.core.blocks.PrisonVaultBlock;
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
    public static final RegistryObject<Block> NULL_VOID_STONE = registerSpecial("null_void_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                .strength(3.0F, 9.0F)
                .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> NULL_VOID_STONE_BRICKS =
            registerSpecial("null_void_stone_bricks",
                    () -> new Block(BlockBehaviour.Properties.of()
                        .strength(3.0F, 9.0F)
                        .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> NULL_CRYSTAL_ORE = registerSpecial("null_crystal_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                .strength(4.0F, 12.0F)
                .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TAYDENITE_ORE = registerSpecial("taydenite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                .strength(4.0F, 12.0F)
                .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TAYDENITE_BLOCK = register("taydenite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PRISON_WALL = register("prison_wall",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> PLUMBER_HULL = register("plumber_hull",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<PrisonVaultBlock> PRISON_VAULT =
            registerSpecial("prison_vault",
                    () -> new PrisonVaultBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
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
