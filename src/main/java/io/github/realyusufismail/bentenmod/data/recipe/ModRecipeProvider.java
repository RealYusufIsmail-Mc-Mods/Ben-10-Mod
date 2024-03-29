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

package io.github.realyusufismail.bentenmod.data.recipe;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.bententable.OmnitrixCrafterCraftingBookCategory;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.init.TagsInit;
import io.github.realyusufismail.realyusufismailcore.recipe.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

import static io.github.realyusufismail.bentenmod.core.init.ItemInit.*;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final String HAS_ITEM = "has_item";

    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn.getPackOutput());
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(BenTenMod.MOD_ID, path);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // blocks
        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.INFINITUM.get(), 9)

            .requires(BlockInit.INFINITUM_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer);

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.INFINITUM_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_INFINITUM)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer);

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.BLACK_DIAMOND.get(), 9)

            .requires(BlockInit.BLACK_DIAMOND_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer);

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.BLACK_DIAMOND_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_BLACK_DIAMOND)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer);
        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.FIRE.get(), 9)

            .requires(BlockInit.FIRE_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer);

        YusufShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.FIRE_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_FIRE)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer);

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.OMNITRIX.get(), 9)
            .requires(BlockInit.OMNITRIX_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer);

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.OMNITRIX_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_OMNITRIX)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer);

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.SPEED.get(), 9)

            .requires(BlockInit.SPEED_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer);

        YusufShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.SPEED_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_SPEED)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer);

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RUBY.get(), 9)

            .requires(BlockInit.RUBY_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer);

        YusufShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.RUBY_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_RUBY)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer);

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlockInit.LEGENDARY_ORE.get(), 9)

            .requires(BlockInit.LEGENDARY_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer);

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.LEGENDARY_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer);

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.IMPERIUM.get(), 9)

            .requires(BlockInit.IMPERIUM_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer);

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.IMPERIUM_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_IMPERIUM)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer);


        // ingots

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.BLACK_DIAMOND.get(), 1)
            .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
            .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
            .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
            .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
            .requires(Items.GOLD_INGOT.asItem())
            .requires(Items.GOLD_INGOT.asItem())
            .requires(Items.GOLD_INGOT.asItem())
            .requires(Items.GOLD_INGOT.asItem())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP))
            .save(consumer, modId("black_diamond_scrap"));

        YusufShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.INFINITUM.get(), 1)
            .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
            .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
            .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
            .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
            .requires(Items.GOLD_INGOT.asItem())
            .requires(Items.GOLD_INGOT.asItem())
            .requires(Items.GOLD_INGOT.asItem())
            .requires(Items.GOLD_INGOT.asItem())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.SCRAP_INFINITUM_SCRAP))
            .save(consumer, modId("infinitum_scrap"));


        // amour and tools
        YusufShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemInit.AXE.get())
            .define('#', Items.STICK.asItem())
            .define('K', TagsInit.Items.INGOTS_RUBY)
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("KK ")
            .pattern("A# ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("axe"));

        /*
         * YusufShapedRecipeBuilder.shaped(HEATBLAST_SWORD.get()) .define('#', Items.STICK.asItem())
         * .define('K', FIRE.get()) .define('A', OMNITRIX.get()) .pattern(" K ") .pattern(" A ")
         * .pattern(" # ") .unlockedBy(HAS_ITEM, has(FIRE.get()))
         * .save(consumer,modId("heatblast_sword"));
         */
        YusufShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemInit.SWORD.get())
            .define('#', Items.STICK.asItem())
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern(" A ")
            .pattern(" A ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("sword"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemInit.IMPERIUM_SWORD.get())
            .define('#', Items.STICK.asItem())
            .define('A', TagsInit.Items.INGOTS_IMPERIUM)
            .pattern(" A ")
            .pattern(" A ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer, modId("imperium_sword"));

        EnchantmentRecipeProvider.shaped(RecipeCategory.TOOLS, ItemInit.HEATBLAST_SWORD.get())
            .define('#', Items.STICK.asItem())
            .define('K', ItemInit.FIRE.get())
            .define('A', ItemInit.OMNITRIX.get())
            .pattern(" K ")
            .pattern(" A ")
            .pattern(" # ")
            .setEnchantment(Enchantments.FIRE_ASPECT, 1)
            .setHideFlags(1)
            .unlockedBy(HAS_ITEM, has(ItemInit.FIRE.get()))
            .save(consumer, modId("heatblast_sword"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemInit.IMPERIUM_PICKAXE.get())
            .define('#', Items.STICK.asItem())
            .define('A', TagsInit.Items.INGOTS_IMPERIUM)
            .pattern("AAA")
            .pattern(" # ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer, modId("imperium_pickaxe"));


        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.HELMET.get())
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("AAA")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("helmet"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.CHESTPLATE.get())
            .define('#', TagsInit.Items.INGOTS_RUBY)
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("A A")
            .pattern("A#A")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("chestplate"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.LEGGINGS.get())
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("AAA")
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("leggings"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.BOOTS.get())
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("boots"));


        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.HEATBLAST_HELMET.get())
            .define('A', TagsInit.Items.INGOTS_FIRE)
            .pattern("AAA")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_helmet"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.HEATBLAST_CHESTPLATE.get())
            .define('#', TagsInit.Items.INGOTS_OMNITRIX)
            .define('A', TagsInit.Items.INGOTS_FIRE)
            .pattern("A A")
            .pattern("A#A")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_chestplate"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.HEATBLAST_LEGGINGS.get())
            .define('A', TagsInit.Items.INGOTS_FIRE)
            .pattern("AAA")
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_leggings"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.HEATBLAST_BOOTS.get())
            .define('A', TagsInit.Items.INGOTS_FIRE)
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_boots"));


        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.XLR8_HELMET.get())
            .define('A', TagsInit.Items.INGOTS_SPEED)
            .pattern("AAA")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_helmet"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.XLR8_CHESTPLATE.get())
            .define('#', TagsInit.Items.INGOTS_OMNITRIX)
            .define('A', TagsInit.Items.INGOTS_SPEED)
            .pattern("A A")
            .pattern("A#A")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_chestplate"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.XLR8_LEGGINGS.get())
            .define('A', TagsInit.Items.INGOTS_SPEED)
            .pattern("AAA")
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_leggings"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.XLR8_BOOTS.get())
            .define('A', TagsInit.Items.INGOTS_SPEED)
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_boots"));


        YusufShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemInit.JACKET.get())
            .define('A', TagsInit.Items.INGOTS_OMNITRIX)
            .pattern("A A")
            .pattern("AAA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer, modId("jacket"));


        // food
        YusufShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemInit.OMNITRIX_APPLE.get())
            .define('A', TagsInit.Items.INGOTS_OMNITRIX)
            .define('L', Items.APPLE.asItem())
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer, modId("omnitrix_apple"));

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.FOOD, ItemInit.ENCHANTED_OMNITRIX_APPLE.get())
            .define('A', BlockInit.OMNITRIX_BLOCK.get())
            .define('L', Items.APPLE.asItem())
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer, modId("enchanted_omnitrix_apple"));

        YusufShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemInit.FIRE_APPLE.get())
            .define('A', TagsInit.Items.INGOTS_FIRE)
            .define('L', Items.APPLE.asItem())
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("fire_apple"));


        YusufShapedRecipeBuilder.shaped(RecipeCategory.FOOD, RUBY_APPLE.get())
            .define('A', TagsInit.Items.INGOTS_RUBY)
            .define('L', Items.APPLE.asItem())
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("ruby_apple"));


        YusufShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE.asItem())
            .define('A', Items.GOLD_BLOCK.asItem())
            .define('L', Items.APPLE.asItem())
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(Items.GOLD_INGOT.asItem()))
            .save(consumer, modId("enchanted_golden_apple"));

        YusufShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.OmnitrixCrafter.get().asItem())
            .define('A', TagsInit.Items.INGOTS_OMNITRIX)
            .define('L', Blocks.COBBLESTONE.asItem())
            .pattern("AAA")
            .pattern("LLL")
            .pattern("LLL")
            .unlockedBy(HAS_ITEM, has(Blocks.COBBLESTONE.asItem()))
            .save(consumer, modId("omntrix_block"));

        // other items
        YusufShapedRecipeBuilder.shaped(RecipeCategory.MISC, EntityTypesInit.CRAB_SPAWN_EGG.get())
            .define('L', Items.EGG.asItem())
            .define('A', TagsInit.Items.INGOTS_IMPERIUM)
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("kraab_spawn_egg"));

        OmnitrixRecipeBuilder.INSTANCE
            .shaped(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                    IMPERIUM_PICKAXE_UPGRADED.get())
            .define('A', IMPERIUM_PICKAXE.get())
            .define('L', TagsInit.Items.INGOTS_OMNITRIX)
            .pattern(" A ")
            .pattern(" L ")
            .pattern(" L ")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer, modId("imperium_upgraded_pickaxe"));


        // smelting and blasting recipes
        // ores
        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(BlockInit.RUBY_ORE.get()), RUBY.get(),
                    0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.RUBY_ORE.get()))
            .save(consumer, modId("ruby_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(BlockInit.RUBY_ORE.get()), RUBY.get(),
                    0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.RUBY_ORE.get()))
            .save(consumer, modId("ruby_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(ItemInit.RAW_OMNITRIX.get()),
                    ItemInit.OMNITRIX.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_OMNITRIX.get()))
            .save(consumer, modId("omnitrix_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(ItemInit.RAW_OMNITRIX.get()),
                    ItemInit.OMNITRIX.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_OMNITRIX.get()))
            .save(consumer, modId("omnitrix_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(BlockInit.FIRE_ORE.get()),
                    ItemInit.FIRE.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.FIRE_ORE.get()))
            .save(consumer, modId("fire_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(BlockInit.FIRE_ORE.get()),
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.FIRE_ORE.get()))
            .save(consumer, modId("fire_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(BlockInit.SPEED_ORE.get()),
                    ItemInit.SPEED.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.SPEED_ORE.get()))
            .save(consumer, modId("speed_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(BlockInit.SPEED_ORE.get()),
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.SPEED_ORE.get()))
            .save(consumer, modId("speed_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(ItemInit.RAW_LEGENDARY.get()),
                    ItemInit.LEGENDARY.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_LEGENDARY.get()))
            .save(consumer, modId("legendary_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(ItemInit.RAW_LEGENDARY.get()),
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_LEGENDARY.get()))
            .save(consumer, modId("legendary_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(BlockInit.INFINITUM_ORE.get()),
                    ItemInit.INFINITUM_SCRAP.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.INFINITUM_ORE.get()))
            .save(consumer, modId("infinitum_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(BlockInit.INFINITUM_ORE.get()),
                    ItemInit.INFINITUM_SCRAP.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.INFINITUM_ORE.get()))
            .save(consumer, modId("infinitum_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()),
                    ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.BLACK_DIAMOND_ORE.get()))
            .save(consumer, modId("black_diamond_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()),
                    ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.BLACK_DIAMOND_ORE.get()))
            .save(consumer, modId("black_diamond_ore_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .smelting(RecipeCategory.MISC, Ingredient.of(ItemInit.RAW_IMPERIUM.get()),
                    ItemInit.IMPERIUM.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_IMPERIUM.get()))
            .save(consumer, modId("imperium_raw_ore_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.MISC, Ingredient.of(ItemInit.RAW_IMPERIUM.get()),
                    ItemInit.IMPERIUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_IMPERIUM.get()))
            .save(consumer, modId("imperium_raw_ore_blasting_smelt"));

        // armour
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.HELMET.get()),
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("helmet_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.CHESTPLATE.get()),
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("chestplate_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.LEGGINGS.get()),
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("leggings_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.BOOTS.get()),
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("boots_blasting_smelt"));


        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.XLR8_HELMET.get()),
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_helmet_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.XLR8_CHESTPLATE.get()),
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_chestplate_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.XLR8_LEGGINGS.get()),
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_leggings_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.XLR8_BOOTS.get()),
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_boots_blasting_smelt"));


        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(FOURARMS_HELMET.get()), RUBY.get(), 0.3f,
                    300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_helmet_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(FOURARMS_CHESTPLATE.get()), RUBY.get(),
                    0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_chestplate_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(FOURARMS_LEGGINGS.get()), RUBY.get(),
                    0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_leggings_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(FOURARMS_BOOT.get()), RUBY.get(), 0.3f,
                    300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_boots_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.BLACK_DIAMOND_HELMET.get()),
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_helmet_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.BLACK_DIAMOND_CHESTPLATE.get()),
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_chestplate_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.BLACK_DIAMOND_LEGGINGS.get()),
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_leggings_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.BLACK_DIAMOND_BOOTS.get()),
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_boots_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.INFINITUM_HELMET.get()),
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_helmet_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.INFINITUM_CHESTPLATE.get()),
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_chestplate_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.INFINITUM_LEGGINGS.get()),
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_leggings_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.INFINITUM_BOOTS.get()),
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_boots_blasting_smelt"));


        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.HEATBLAST_HELMET.get()),
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_helmet_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.HEATBLAST_CHESTPLATE.get()),
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_chestplate_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.HEATBLAST_LEGGINGS.get()),
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_leggings_blasting_smelt"));
        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.HEATBLAST_BOOTS.get()),
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_boots_blasting_smelt"));

        YusufSimpleCookingRecipeBuilder
            .blasting(RecipeCategory.COMBAT, Ingredient.of(ItemInit.JACKET.get()),
                    ItemInit.OMNITRIX.get(), 0.4f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer, modId("jacket_blasting_smelt"));


        /*
         * // Upgrade table YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(Items.DIAMOND_SWORD), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_SWORD.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer, modId("black_diamond_sword"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(Items.DIAMOND_AXE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_AXE.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer, modId("black_diamond_axe"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(Items.DIAMOND_PICKAXE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_PICKAXE.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer,
         * modId("black_diamond_pickaxe"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(Items.DIAMOND_SHOVEL), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_SHOVEL.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer, modId("black_diamond_shovel"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(Items.DIAMOND_HOE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_HOE.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer, modId("black_diamond_hoe"));
         * 
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(Items.DIAMOND_HELMET), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_HELMET.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer, modId("black_diamond_helmet"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(Items.DIAMOND_CHESTPLATE), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_CHESTPLATE.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer,
         * modId("black_diamond_chestplate"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(Items.DIAMOND_LEGGINGS), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_LEGGINGS.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer,
         * modId("black_diamond_leggings"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(Items.DIAMOND_BOOTS), Ingredient.of(ItemInit.BLACK_DIAMOND.get()),
         * ItemInit.BLACK_DIAMOND_BOOTS.get()) .unlocks("has_item",
         * has(TagsInit.Items.INGOTS_BLACK_DIAMOND)) .save(consumer, modId("black_diamond_boots"));
         * 
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_SWORD.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_SWORD.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_sword"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_AXE.get()), Ingredient.of(ItemInit.INFINITUM.get()),
         * ItemInit.INFINITUM_AXE.get()) .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
         * .save(consumer, modId("infinitum_axe"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_PICKAXE.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_PICKAXE.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_pickaxe"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_SHOVEL.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_SHOVEL.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_shovel"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.TOOLS,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_HOE.get()), Ingredient.of(ItemInit.INFINITUM.get()),
         * ItemInit.INFINITUM_HOE.get()) .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
         * .save(consumer, modId("infinitum_hoe"));
         * 
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_HELMET.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_HELMET.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_helmet"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_CHESTPLATE.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_CHESTPLATE.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_chestplate"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_LEGGINGS.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_LEGGINGS.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_leggings"));
         * 
         * YusufUpgradeRecipeBuilder .smithing(RecipeCategory.COMBAT,
         * Ingredient.of(ItemInit.BLACK_DIAMOND_BOOTS.get()),
         * Ingredient.of(ItemInit.INFINITUM.get()), ItemInit.INFINITUM_BOOTS.get())
         * .unlocks("has_item", has(TagsInit.Items.INGOTS_INFINITUM)) .save(consumer,
         * modId("infinitum_boots"));
         **/
    }

    private Ingredient ingredient(Item entry) {
        return Ingredient.of(entry);
    }
}


