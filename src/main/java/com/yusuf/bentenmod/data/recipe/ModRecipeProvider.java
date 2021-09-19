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

package com.yusuf.bentenmod.data.recipe;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.yusuf.bentenmod.core.init.ItemInit.*;


public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(BenTenMod.MOD_ID, path);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        //blocks
        ShapelessRecipeBuilder.shapeless(INFINITUM.get(), 9)

                .requires(BlockInit.INFINITUM_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.INFINITUM_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_INFINITUM)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(BLACK_DIAMOND.get(), 9)

                .requires(BlockInit.BLACK_DIAMOND_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.BLACK_DIAMOND_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_BLACK_DIAMOND)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(FIRE.get(), 9)

                .requires(BlockInit.FIRE_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.FIRE_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_FIRE)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(OMNITRIX.get(), 9)

                .requires(BlockInit.OMNITRIX_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.OMNITRIX_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_OMNITRIX)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(SPEED.get(), 9)

                .requires(BlockInit.SPEED_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.SPEED_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_SPEED)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RUBY.get(), 9)

                .requires(BlockInit.RUBY_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.RUBY_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_RUBY)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(BlockInit.LEGENDARY_ORE.get(), 9)

                .requires(BlockInit.LEGENDARY_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.LEGENDARY_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(IMPERIUM.get(), 9)

                .requires(BlockInit.IMPERIUM_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.IMPERIUM_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer);


        //ingots

        ShapelessRecipeBuilder.shapeless(BLACK_DIAMOND.get(), 1)

                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(Items.GOLD_INGOT.asItem())
                .requires(Items.GOLD_INGOT.asItem())
                .requires(Items.GOLD_INGOT.asItem())
                .requires(Items.GOLD_INGOT.asItem())
                .unlockedBy("has_item", has(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP))
                .save(consumer, modId("black_diamond_scrap"));

        ShapelessRecipeBuilder.shapeless(INFINITUM.get(), 1)

                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(Items.GOLD_INGOT.asItem())
                .requires(Items.GOLD_INGOT.asItem())
                .requires(Items.GOLD_INGOT.asItem())
                .requires(Items.GOLD_INGOT.asItem())
                .unlockedBy("has_item", has(TagsInit.Items.SCRAP_INFINITUM_SCRAP))
                .save(consumer, modId("infinitum_scrap"));


        //amour and tools
        ShapedRecipeBuilder.shaped(AXE.get())
                .define('#', Items.STICK.asItem())
                .define('K', TagsInit.Items.INGOTS_RUBY)
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("KK ")
                .pattern("A# ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("axe"));

/*
        ShapedRecipeBuilder.shaped(HEATBLAST_SWORD.get())
                .define('#', Items.STICK.asItem())
                .define('K', FIRE.get())
                .define('A', OMNITRIX.get())
                .pattern(" K ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(FIRE.get()))
                .save(consumer,modId("heatblast_sword"));
*/
        ShapedRecipeBuilder.shaped(SWORD.get())
                .define('#', Items.STICK.asItem())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("sword"));

        ShapedRecipeBuilder.shaped(IMPERIUM_SWORD.get())
                .define('#', Items.STICK.asItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer, modId("imperium_sword"));

        ShapedRecipeBuilder.shaped(IMPERIUM_PICKAXE.get())
                .define('#', Items.STICK.asItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern("AAA")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer, modId("imperium_pickaxe"));


        ShapedRecipeBuilder.shaped(HELMET.get())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("helmet"));

        ShapedRecipeBuilder.shaped(CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_RUBY)
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("chestplate"));

        ShapedRecipeBuilder.shaped(LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("leggings"));

        ShapedRecipeBuilder.shaped(BOOTS.get())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("boots"));


        ShapedRecipeBuilder.shaped(HEATBLAST_HELMET.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_helmet"));

        ShapedRecipeBuilder.shaped(HEATBLAST_CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_OMNITRIX)
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_chestplate"));

        ShapedRecipeBuilder.shaped(HEATBLAST_LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_leggings"));

        ShapedRecipeBuilder.shaped(HEATBLAST_BOOTS.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_boots"));


        ShapedRecipeBuilder.shaped(XLR8_HELMET.get())
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_helmet"));

        ShapedRecipeBuilder.shaped(XLR8_CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_OMNITRIX)
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_chestplate"));

        ShapedRecipeBuilder.shaped(XLR8_LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_leggings"));

        ShapedRecipeBuilder.shaped(XLR8_BOOTS.get())
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_boots"));


        ShapedRecipeBuilder.shaped(JACKET.get())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .pattern("A A")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("jacket"));


        //food
        ShapedRecipeBuilder.shaped(OMNITRIX_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .define('L', Items.APPLE.asItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("omnitrix_apple"));

        ShapedRecipeBuilder.shaped(ENCHANTED_OMNITRIX_APPLE.get())
                .define('A', BlockInit.OMNITRIX_BLOCK.get())
                .define('L', Items.APPLE.asItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("enchanted_omnitrix_apple"));

        ShapedRecipeBuilder.shaped(FIRE_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .define('L', Items.APPLE.asItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("fire_apple"));

        /*
        ShapedRecipeBuilder.shaped(RUBY_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .define('L', Items.APPLE.asItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("ruby_apple"));

         */
        ShapedRecipeBuilder.shaped(Items.ENCHANTED_GOLDEN_APPLE.asItem())
                .define('A', Items.GOLD_BLOCK.asItem())
                .define('L', Items.APPLE.asItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(Items.GOLD_INGOT.asItem()))
                .save(consumer, modId("enchanted_golden_apple"));

        ShapedRecipeBuilder.shaped(BlockInit.TABLE_BLOCK.get().asItem())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .define('L', Blocks.COBBLESTONE.asItem())
                .pattern("AA ")
                .pattern("LL ")
                .pattern("LL ")
                .unlockedBy("has_item", has(Blocks.COBBLESTONE.asItem()))
                .save(consumer, modId("table_block"));

        //other items
/**
 * @see ShapedRecipeBuilder
 */
        ShapedRecipeBuilder.shaped(EntityTypesInit.VILGAX_SPAWN_EGG.get())
                .define('L', Items.EGG.asItem())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("vilgax_spawn_egg"));

        ShapedRecipeBuilder.shaped(EntityTypesInit.KRAAB_SPAWN_EGG.get())
                .define('L', Items.EGG.asItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("kraab_spawn_egg"));


        //smelting and blasting recipes

        //ores

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RUBY_ORE.get()), RUBY.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, modId("ruby_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RUBY_ORE.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, modId("ruby_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RAW_OMNITRIX.get()), OMNITRIX.get(), 0.6f, 300)
                .unlockedBy("has_item", has(RAW_OMNITRIX.get()))
                .save(consumer, modId("omnitrix_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RAW_OMNITRIX.get()), OMNITRIX.get(), 0.3f, 300)
                .unlockedBy("has_item", has(RAW_OMNITRIX.get()))
                .save(consumer, modId("omnitrix_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.FIRE_ORE.get()), FIRE.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, modId("fire_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.FIRE_ORE.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, modId("fire_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.SPEED_ORE.get()), SPEED.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, modId("speed_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.SPEED_ORE.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, modId("speed_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RAW_LEGENDARY.get()), LEGENDARY.get(), 0.6f, 300)
                .unlockedBy("has_item", has(RAW_LEGENDARY.get()))
                .save(consumer, modId("legendary_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RAW_LEGENDARY.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(RAW_LEGENDARY.get()))
                .save(consumer, modId("legendary_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), INFINITUM_SCRAP.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.INFINITUM_ORE.get()))
                .save(consumer, modId("infinitum_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), INFINITUM_SCRAP.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.INFINITUM_ORE.get()))
                .save(consumer, modId("infinitum_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), BLACK_DIAMOND_SCRAP.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.BLACK_DIAMOND_ORE.get()))
                .save(consumer, modId("black_diamond_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), BLACK_DIAMOND_SCRAP.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.BLACK_DIAMOND_ORE.get()))
                .save(consumer, modId("black_diamond_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RAW_IMPERIUM.get()), IMPERIUM.get(), 0.6f, 300)
                .unlockedBy("has_item", has(RAW_IMPERIUM.get()))
                .save(consumer, modId("imperium_raw_ore_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RAW_IMPERIUM.get()), IMPERIUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(RAW_IMPERIUM.get()))
                .save(consumer, modId("imperium_raw_ore_blasting_smelt"));

        //armour

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(HELMET.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(CHESTPLATE.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(LEGGINGS.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BOOTS.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("boots_blasting_smelt"));


        SimpleCookingRecipeBuilder.blasting(Ingredient.of(XLR8_HELMET.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(XLR8_CHESTPLATE.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(XLR8_LEGGINGS.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(XLR8_BOOTS.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_boots_blasting_smelt"));

        /*
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_HELMET.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_CHESTPLATE.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_LEGGINGS.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_BOOT.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_boots_blasting_smelt"));


         */
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_HELMET.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_CHESTPLATE.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_LEGGINGS.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_BOOTS.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_boots_blasting_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_HELMET.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_CHESTPLATE.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_LEGGINGS.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_BOOTS.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_boots_blasting_smelt"));


        SimpleCookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_HELMET.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_CHESTPLATE.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_LEGGINGS.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_BOOTS.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_boots_blasting_smelt"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(JACKET.get()), OMNITRIX.get(), 0.4f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("jacket_blasting_smelt"));

        //food

        TableRecipeBuilder.build(ingredient(IMPERIUM_PICKAXE.get()), ingredient(IMPERIUM.get()), ingredient(IMPERIUM.get()), IMPERIUM_PICKAXE_UPGRADED.get())
                .unlockedBy("has_item", has(TagsInit.Items.TOOLS_IMPERIUM_PICKAXE))
                .save(consumer, modId("imperium_pickaxe_upgraded"));


    }

    private Ingredient ingredient(Item entry) {
        return Ingredient.of(entry);
    }

}




