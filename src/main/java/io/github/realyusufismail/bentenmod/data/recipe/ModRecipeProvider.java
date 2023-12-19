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
package io.github.realyusufismail.bentenmod.data.recipe;

import static io.github.realyusufismail.bentenmod.core.init.ItemInit.*;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.*;
import java.util.function.Consumer;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(BenTenMod.MOD_ID, path);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        // blocks
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

        // ingots

        ShapelessRecipeBuilder.shapeless(BLACK_DIAMOND.get(), 1)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlockedBy("has_item", has(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP))
                .save(consumer, modId("black_diamond_scrap"));

        ShapelessRecipeBuilder.shapeless(INFINITUM.get(), 1)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlockedBy("has_item", has(TagsInit.Items.SCRAP_INFINITUM_SCRAP))
                .save(consumer, modId("infinitum_scrap"));

        // amour and tools
        ShapedRecipeBuilder.shaped(AXE.get())
                .define('#', Items.STICK.getItem())
                .define('K', TagsInit.Items.INGOTS_RUBY)
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern("KK ")
                .pattern("A# ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("axe"));

        /*
                ShapedRecipeBuilder.shaped(HEATBLAST_SWORD.get())
                        .define('#', Items.STICK.getItem())
                        .define('K', FIRE.get())
                        .define('A', OMNITRIX.get())
                        .pattern(" K ")
                        .pattern(" A ")
                        .pattern(" # ")
                        .unlockedBy("has_item", has(FIRE.get()))
                        .save(consumer,modId("heatblast_sword"));
        */
        ShapedRecipeBuilder.shaped(SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("sword"));

        ShapedRecipeBuilder.shaped(IMPERIUM_SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer, modId("imperium_sword"));

        ShapedRecipeBuilder.shaped(IMPERIUM_PICKAXE.get())
                .define('#', Items.STICK.getItem())
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

        // food
        ShapedRecipeBuilder.shaped(OMNITRIX_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("omnitrix_apple"));

        ShapedRecipeBuilder.shaped(ENCHANTED_OMNITRIX_APPLE.get())
                .define('A', BlockInit.OMNITRIX_BLOCK.get())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("enchanted_omnitrix_apple"));

        ShapedRecipeBuilder.shaped(FIRE_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("fire_apple"));

        /*
        ShapedRecipeBuilder.shaped(RUBY_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("ruby_apple"));

         */
        ShapedRecipeBuilder.shaped(Items.ENCHANTED_GOLDEN_APPLE.getItem())
                .define('A', Items.GOLD_BLOCK.getItem())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(Items.GOLD_INGOT.getItem()))
                .save(consumer, modId("enchanted_golden_apple"));

        ShapedRecipeBuilder.shaped(BlockInit.TABLE_BLOCK.get().getBlock())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .define('L', Blocks.COBBLESTONE.getBlock())
                .pattern("AA ")
                .pattern("LL ")
                .pattern("LL ")
                .unlockedBy("has_item", has(Blocks.COBBLESTONE.getBlock()))
                .save(consumer, modId("table_block"));

        // other items
        /**
         * @see ShapedRecipeBuilder
         */
        ShapedRecipeBuilder.shaped(EntityTypesInit.VILGAX_SPAWN_EGG.get())
                .define('L', Items.EGG.getItem())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("vilgax_spawn_egg"));

        ShapedRecipeBuilder.shaped(EntityTypesInit.KRAAB_SPAWN_EGG.get())
                .define('L', Items.EGG.getItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("kraab_spawn_egg"));

        // smelting and blasting recipes

        // ores

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RUBY_ORE.get()), RUBY.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, modId("ruby_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RUBY_ORE.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, modId("ruby_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.OMNITRIX_ORE.get()), OMNITRIX.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.OMNITRIX_ORE.get()))
                .save(consumer, modId("omnitrix_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.OMNITRIX_ORE.get()), OMNITRIX.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.OMNITRIX_ORE.get()))
                .save(consumer, modId("omnitrix_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.FIRE_ORE.get()), FIRE.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, modId("fire_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.FIRE_ORE.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, modId("fire_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.SPEED_ORE.get()), SPEED.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, modId("speed_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.SPEED_ORE.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, modId("speed_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.LEGENDARY_ORE.get()), LEGENDARY.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.LEGENDARY_ORE.get()))
                .save(consumer, modId("legendary_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.LEGENDARY_ORE.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.LEGENDARY_ORE.get()))
                .save(consumer, modId("legendary_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), INFINITUM_SCRAP.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.INFINITUM_ORE.get()))
                .save(consumer, modId("infinitum_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), INFINITUM_SCRAP.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.INFINITUM_ORE.get()))
                .save(consumer, modId("infinitum_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(
                        Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), BLACK_DIAMOND_SCRAP.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.BLACK_DIAMOND_ORE.get()))
                .save(consumer, modId("black_diamond_ore_smelt"));

        CookingRecipeBuilder.blasting(
                        Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), BLACK_DIAMOND_SCRAP.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.BLACK_DIAMOND_ORE.get()))
                .save(consumer, modId("black_diamond_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.IMPERIUM_ORE.get()), IMPERIUM.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.IMPERIUM_ORE.get()))
                .save(consumer, modId("imperium_raw_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.IMPERIUM_ORE.get()), IMPERIUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.IMPERIUM_ORE.get()))
                .save(consumer, modId("imperium_raw_ore_blasting_smelt"));

        // armour

        CookingRecipeBuilder.blasting(Ingredient.of(HELMET.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(CHESTPLATE.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(LEGGINGS.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(BOOTS.get()), LEGENDARY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY))
                .save(consumer, modId("boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(XLR8_HELMET.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(XLR8_CHESTPLATE.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(XLR8_LEGGINGS.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(XLR8_BOOTS.get()), SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, modId("xlr8_boots_blasting_smelt"));

        /*
        CookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_HELMET.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_CHESTPLATE.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_LEGGINGS.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(FOURARMS_BOOT.get()), RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, modId("fourarms_boots_blasting_smelt"));


         */
        CookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_HELMET.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_CHESTPLATE.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_LEGGINGS.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(BLACK_DIAMOND_BOOTS.get()), BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, modId("black_diamond_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_HELMET.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_CHESTPLATE.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_LEGGINGS.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(INFINITUM_BOOTS.get()), INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, modId("infinitum_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_HELMET.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_CHESTPLATE.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_LEGGINGS.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(HEATBLAST_BOOTS.get()), FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, modId("heatblast_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(JACKET.get()), OMNITRIX.get(), 0.4f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, modId("jacket_blasting_smelt"));

        // food

        TableRecipeBuilder.build(
                        ingredient(IMPERIUM_PICKAXE.get()),
                        ingredient(IMPERIUM.get()),
                        ingredient(IMPERIUM.get()),
                        IMPERIUM_PICKAXE_UPGRADED.get())
                .unlockedBy("has_item", has(TagsInit.Items.TOOLS_IMPERIUM_PICKAXE))
                .save(consumer, modId("imperium_pickaxe_upgraded"));
    }

    private Ingredient ingredient(IItemProvider entry) {
        return Ingredient.of(entry);
    }
}
