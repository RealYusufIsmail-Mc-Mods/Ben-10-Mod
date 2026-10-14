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
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.OMNITRIX_GUIDE.get())
            .requires(Items.BOOK)
            .requires(Items.LIME_DYE)
            .unlockedBy(HAS_ITEM, has(Items.BOOK))
            .save(consumer, modId("omnitrix_guide"));

        // blocks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.INFINITUM.get(), 9)

            .requires(BlockInit.INFINITUM_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.INFINITUM_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_INFINITUM)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer);

        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.NULL_VOID_STONE_BRICKS.get(), 4)
            .define('#', BlockInit.NULL_VOID_STONE.get())
            .pattern("##")
            .pattern("##")
            .unlockedBy(HAS_ITEM, has(BlockInit.NULL_VOID_STONE.get()))
            .save(consumer, modId("null_void_stone_bricks"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.BLACK_DIAMOND.get(), 9)

            .requires(BlockInit.BLACK_DIAMOND_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer);

        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.BLACK_DIAMOND_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_BLACK_DIAMOND)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.FIRE.get(), 9)

            .requires(BlockInit.FIRE_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.FIRE_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_FIRE)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.OMNITRIX.get(), 9)
            .requires(BlockInit.OMNITRIX_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.OMNITRIX_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_OMNITRIX)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.SPEED.get(), 9)

            .requires(BlockInit.SPEED_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.SPEED_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_SPEED)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RUBY.get(), 9)

            .requires(BlockInit.RUBY_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.RUBY_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_RUBY)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlockInit.LEGENDARY_ORE.get(), 9)

            .requires(BlockInit.LEGENDARY_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.LEGENDARY_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.IMPERIUM.get(), 9)

            .requires(BlockInit.IMPERIUM_BLOCK.get())
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.IMPERIUM_BLOCK.get())
            .define('#', TagsInit.Items.INGOTS_IMPERIUM)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
            .save(consumer);


        // ingots

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.BLACK_DIAMOND.get(), 1)
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.INFINITUM.get(), 1)
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


        // The Crafter owns Ben 10 gear and special items; it remains craftable in a crafting table.
        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.MISC,
                ItemInit.LEGENDARY_CASING.get()).define('L', TagsInit.Items.INGOTS_LEGENDARY)
                    .define('I', Items.IRON_INGOT)
                    .pattern("LIL")
                    .pattern("I I")
                    .pattern("LIL")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
                    .save(consumer, modId("legendary_casing"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.MISC,
                ItemInit.BLACK_DIAMOND_LENS.get()).define('G', Items.GLASS)
                    .define('D', TagsInit.Items.INGOTS_BLACK_DIAMOND)
                    .pattern(" G ")
                    .pattern("GDG")
                    .pattern(" G ")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                    .save(consumer, modId("black_diamond_lens"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.MISC,
                ItemInit.OMNITRIX_CORE.get()).define('O', TagsInit.Items.INGOTS_OMNITRIX)
                    .define('L', ItemInit.BLACK_DIAMOND_LENS.get())
                    .define('C', ItemInit.LEGENDARY_CASING.get())
                    .pattern("OLO")
                    .pattern("OCO")
                    .pattern("OOO")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
                    .save(consumer, modId("omnitrix_core"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.MISC,
                ItemInit.OMNITRIX_WATCH.get()).define('L', Items.LEATHER)
                    .define('N', Items.IRON_NUGGET)
                    .define('C', ItemInit.OMNITRIX_CORE.get())
                    .pattern("LNL")
                    .pattern("NCN")
                    .unlockedBy(HAS_ITEM, has(ItemInit.OMNITRIX_CORE.get()))
                    .save(consumer, modId("omnitrix_watch"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.FOOD,
                ItemInit.OMNITRIX_APPLE.get()).define('A', TagsInit.Items.INGOTS_OMNITRIX)
                    .define('L', Items.APPLE)
                    .pattern("AAA")
                    .pattern("ALA")
                    .pattern("AAA")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
                    .save(consumer, modId("omnitrix_apple"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.FOOD,
                ItemInit.ENCHANTED_OMNITRIX_APPLE.get()).define('A', BlockInit.OMNITRIX_BLOCK.get())
                    .define('L', Items.APPLE)
                    .pattern("AAA")
                    .pattern("ALA")
                    .pattern("AAA")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
                    .save(consumer, modId("enchanted_omnitrix_apple"));

        crafter(OmnitrixCrafterCraftingBookCategory.MISC, RecipeCategory.FOOD,
                ItemInit.FIRE_APPLE.get()).define('A', TagsInit.Items.INGOTS_FIRE)
                    .define('L', Items.APPLE)
                    .pattern("AAA")
                    .pattern("ALA")
                    .pattern("AAA")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
                    .save(consumer, modId("fire_apple"));

        crafter(OmnitrixCrafterCraftingBookCategory.MISC, RecipeCategory.FOOD, RUBY_APPLE.get())
            .define('A', TagsInit.Items.INGOTS_RUBY)
            .define('L', Items.APPLE)
            .pattern("AAA")
            .pattern("ALA")
            .pattern("AAA")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("ruby_apple"));

        crafter(OmnitrixCrafterCraftingBookCategory.MISC, RecipeCategory.FOOD,
                Items.ENCHANTED_GOLDEN_APPLE).define('A', Items.GOLD_BLOCK)
                    .define('L', Items.APPLE)
                    .pattern("AAA")
                    .pattern("ALA")
                    .pattern("AAA")
                    .unlockedBy(HAS_ITEM, has(Items.GOLD_INGOT))
                    .save(consumer, modId("enchanted_golden_apple"));

        crafter(OmnitrixCrafterCraftingBookCategory.MISC, RecipeCategory.MISC,
                EntityTypesInit.CRAB_SPAWN_EGG.get()).define('L', Items.EGG)
                    .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                    .pattern("AAA")
                    .pattern("ALA")
                    .pattern("AAA")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
                    .save(consumer, modId("kraab_spawn_egg"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.MISC,
                ItemInit.VILGAX_BEACON.get()).define('K', ItemInit.KRAAB_CLAW.get())
                    .define('I', ItemInit.INFINITUM.get())
                    .define('B', ItemInit.BLACK_DIAMOND.get())
                    .define('R', Blocks.REDSTONE_BLOCK)
                    .pattern("KI")
                    .pattern("BR")
                    .unlockedBy(HAS_ITEM, has(ItemInit.KRAAB_CLAW.get()))
                    .save(consumer, modId("vilgax_beacon"));

        crafter(OmnitrixCrafterCraftingBookCategory.OMNITRIX, RecipeCategory.MISC,
                ItemInit.NULL_VOID_PROJECTOR.get()).define('I', ItemInit.INFINITUM.get())
                    .define('V', ItemInit.VILGAX_CORE.get())
                    .pattern("I I")
                    .pattern(" V ")
                    .unlockedBy(HAS_ITEM, has(ItemInit.VILGAX_CORE.get()))
                    .save(consumer, modId("null_void_projector"));

        // Tools
        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS, ItemInit.AXE.get())
            .define('#', Items.STICK)
            .define('K', TagsInit.Items.INGOTS_RUBY)
            .define('A', TagsInit.Items.INGOTS_LEGENDARY)
            .pattern("KK ")
            .pattern("A# ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("axe"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                ItemInit.SWORD.get()).define('#', Items.STICK)
                    .define('A', TagsInit.Items.INGOTS_LEGENDARY)
                    .pattern(" A ")
                    .pattern(" A ")
                    .pattern(" # ")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
                    .save(consumer, modId("sword"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                ItemInit.HEATBLAST_SWORD.get()).define('#', Items.STICK)
                    .define('K', ItemInit.FIRE.get())
                    .define('A', ItemInit.OMNITRIX.get())
                    .pattern(" K ")
                    .pattern(" A ")
                    .pattern(" # ")
                    .unlockedBy(HAS_ITEM, has(ItemInit.FIRE.get()))
                    .save(consumer, modId("heatblast_sword"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                ItemInit.IMPERIUM_SWORD.get()).define('#', Items.STICK)
                    .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                    .pattern(" A ")
                    .pattern(" A ")
                    .pattern(" # ")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
                    .save(consumer, modId("imperium_sword"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                ItemInit.IMPERIUM_PICKAXE.get()).define('#', Items.STICK)
                    .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                    .pattern("AAA")
                    .pattern(" # ")
                    .pattern(" # ")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
                    .save(consumer, modId("imperium_pickaxe"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                ItemInit.IMPERIUM_AXE.get()).define('#', Items.STICK)
                    .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                    .pattern("AA ")
                    .pattern("A# ")
                    .pattern(" # ")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
                    .save(consumer, modId("imperium_axe"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS,
                ItemInit.IMPERIUM_PICKAXE_UPGRADED.get())
                    .define('A', ItemInit.IMPERIUM_PICKAXE.get())
                    .define('L', TagsInit.Items.INGOTS_OMNITRIX)
                    .pattern(" A ")
                    .pattern(" L ")
                    .pattern(" L ")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_IMPERIUM))
                    .save(consumer, modId("imperium_upgraded_pickaxe"));

        addToolRecipes(consumer, TagsInit.Items.INGOTS_BLACK_DIAMOND,
                ItemInit.BLACK_DIAMOND_SWORD.get(), ItemInit.BLACK_DIAMOND_PICKAXE.get(),
                ItemInit.BLACK_DIAMOND_AXE.get(), ItemInit.BLACK_DIAMOND_SHOVEL.get(),
                ItemInit.BLACK_DIAMOND_HOE.get(), "black_diamond");
        addToolRecipes(consumer, TagsInit.Items.INGOTS_INFINITUM, ItemInit.INFINITUM_SWORD.get(),
                ItemInit.INFINITUM_PICKAXE.get(), ItemInit.INFINITUM_AXE.get(),
                ItemInit.INFINITUM_SHOVEL.get(), ItemInit.INFINITUM_HOE.get(), "infinitum");

        // Armour
        armorRecipes(consumer, TagsInit.Items.INGOTS_LEGENDARY, ItemInit.HELMET.get(),
                ItemInit.CHESTPLATE.get(), ItemInit.LEGGINGS.get(), ItemInit.BOOTS.get(), "",
                TagsInit.Items.INGOTS_RUBY);
        armorRecipes(consumer, TagsInit.Items.INGOTS_FIRE, ItemInit.HEATBLAST_HELMET.get(),
                ItemInit.HEATBLAST_CHESTPLATE.get(), ItemInit.HEATBLAST_LEGGINGS.get(),
                ItemInit.HEATBLAST_BOOTS.get(), "heatblast_", TagsInit.Items.INGOTS_OMNITRIX);
        armorRecipes(consumer, TagsInit.Items.INGOTS_SPEED, ItemInit.XLR8_HELMET.get(),
                ItemInit.XLR8_CHESTPLATE.get(), ItemInit.XLR8_LEGGINGS.get(),
                ItemInit.XLR8_BOOTS.get(), "xlr8_", TagsInit.Items.INGOTS_OMNITRIX);
        armorRecipes(consumer, TagsInit.Items.INGOTS_RUBY, ItemInit.FOURARMS_HELMET.get(),
                ItemInit.FOURARMS_CHESTPLATE.get(), ItemInit.FOURARMS_LEGGINGS.get(),
                ItemInit.FOURARMS_BOOT.get(), "fourarms_", null);

        crafter(OmnitrixCrafterCraftingBookCategory.ARMOUR, RecipeCategory.COMBAT,
                ItemInit.JACKET.get()).define('A', TagsInit.Items.INGOTS_OMNITRIX)
                    .pattern("A A")
                    .pattern("AAA")
                    .pattern("AAA")
                    .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_OMNITRIX))
                    .save(consumer, modId("jacket"));

        ShapedRecipeBuilder
            .shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.OmnitrixCrafter.get().asItem())
            .define('A', TagsInit.Items.INGOTS_OMNITRIX)
            .define('L', Blocks.COBBLESTONE.asItem())
            .pattern("AAA")
            .pattern("LLL")
            .pattern("LLL")
            .unlockedBy(HAS_ITEM, has(Blocks.COBBLESTONE.asItem()))
            .save(consumer, modId("omntrix_block"));


        // smelting and blasting recipes
        // ores
        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(BlockInit.RUBY_ORE.get()), RecipeCategory.MISC, RUBY.get(),
                    0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.RUBY_ORE.get()))
            .save(consumer, modId("ruby_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(BlockInit.RUBY_ORE.get()), RecipeCategory.MISC, RUBY.get(),
                    0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.RUBY_ORE.get()))
            .save(consumer, modId("ruby_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(ItemInit.RAW_OMNITRIX.get()), RecipeCategory.MISC,
                    ItemInit.OMNITRIX.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_OMNITRIX.get()))
            .save(consumer, modId("omnitrix_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.RAW_OMNITRIX.get()), RecipeCategory.MISC,
                    ItemInit.OMNITRIX.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_OMNITRIX.get()))
            .save(consumer, modId("omnitrix_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(BlockInit.FIRE_ORE.get()), RecipeCategory.MISC,
                    ItemInit.FIRE.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.FIRE_ORE.get()))
            .save(consumer, modId("fire_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(BlockInit.FIRE_ORE.get()), RecipeCategory.MISC,
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.FIRE_ORE.get()))
            .save(consumer, modId("fire_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(BlockInit.SPEED_ORE.get()), RecipeCategory.MISC,
                    ItemInit.SPEED.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.SPEED_ORE.get()))
            .save(consumer, modId("speed_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(BlockInit.SPEED_ORE.get()), RecipeCategory.MISC,
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.SPEED_ORE.get()))
            .save(consumer, modId("speed_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(ItemInit.RAW_LEGENDARY.get()), RecipeCategory.MISC,
                    ItemInit.LEGENDARY.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_LEGENDARY.get()))
            .save(consumer, modId("legendary_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.RAW_LEGENDARY.get()), RecipeCategory.MISC,
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_LEGENDARY.get()))
            .save(consumer, modId("legendary_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), RecipeCategory.MISC,
                    ItemInit.INFINITUM_SCRAP.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.INFINITUM_ORE.get()))
            .save(consumer, modId("infinitum_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), RecipeCategory.MISC,
                    ItemInit.INFINITUM_SCRAP.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.INFINITUM_ORE.get()))
            .save(consumer, modId("infinitum_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), RecipeCategory.MISC,
                    ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.BLACK_DIAMOND_ORE.get()))
            .save(consumer, modId("black_diamond_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), RecipeCategory.MISC,
                    ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(BlockInit.BLACK_DIAMOND_ORE.get()))
            .save(consumer, modId("black_diamond_ore_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .smelting(Ingredient.of(ItemInit.RAW_IMPERIUM.get()), RecipeCategory.MISC,
                    ItemInit.IMPERIUM.get(), 0.6f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_IMPERIUM.get()))
            .save(consumer, modId("imperium_raw_ore_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.RAW_IMPERIUM.get()), RecipeCategory.MISC,
                    ItemInit.IMPERIUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(ItemInit.RAW_IMPERIUM.get()))
            .save(consumer, modId("imperium_raw_ore_blasting_smelt"));

        // armour
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.HELMET.get()), RecipeCategory.COMBAT,
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.CHESTPLATE.get()), RecipeCategory.COMBAT,
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.LEGGINGS.get()), RecipeCategory.COMBAT,
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.BOOTS.get()), RecipeCategory.COMBAT,
                    ItemInit.LEGENDARY.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_LEGENDARY))
            .save(consumer, modId("boots_blasting_smelt"));


        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.XLR8_HELMET.get()), RecipeCategory.COMBAT,
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.XLR8_CHESTPLATE.get()), RecipeCategory.COMBAT,
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.XLR8_LEGGINGS.get()), RecipeCategory.COMBAT,
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.XLR8_BOOTS.get()), RecipeCategory.COMBAT,
                    ItemInit.SPEED.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_SPEED))
            .save(consumer, modId("xlr8_boots_blasting_smelt"));


        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(FOURARMS_HELMET.get()), RecipeCategory.COMBAT, RUBY.get(), 0.3f,
                    300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_helmet_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(FOURARMS_CHESTPLATE.get()), RecipeCategory.COMBAT, RUBY.get(),
                    0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_chestplate_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(FOURARMS_LEGGINGS.get()), RecipeCategory.COMBAT, RUBY.get(),
                    0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_leggings_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(FOURARMS_BOOT.get()), RecipeCategory.COMBAT, RUBY.get(), 0.3f,
                    300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_RUBY))
            .save(consumer, modId("fourarms_boots_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_HELMET.get()), RecipeCategory.COMBAT,
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_CHESTPLATE.get()), RecipeCategory.COMBAT,
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_LEGGINGS.get()), RecipeCategory.COMBAT,
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_BOOTS.get()), RecipeCategory.COMBAT,
                    ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
            .save(consumer, modId("black_diamond_boots_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.INFINITUM_HELMET.get()), RecipeCategory.COMBAT,
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.INFINITUM_CHESTPLATE.get()), RecipeCategory.COMBAT,
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.INFINITUM_LEGGINGS.get()), RecipeCategory.COMBAT,
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.INFINITUM_BOOTS.get()), RecipeCategory.COMBAT,
                    ItemInit.INFINITUM.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_INFINITUM))
            .save(consumer, modId("infinitum_boots_blasting_smelt"));


        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.HEATBLAST_HELMET.get()), RecipeCategory.COMBAT,
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_helmet_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.HEATBLAST_CHESTPLATE.get()), RecipeCategory.COMBAT,
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_chestplate_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.HEATBLAST_LEGGINGS.get()), RecipeCategory.COMBAT,
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_leggings_blasting_smelt"));
        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.HEATBLAST_BOOTS.get()), RecipeCategory.COMBAT,
                    ItemInit.FIRE.get(), 0.3f, 300)
            .unlockedBy(HAS_ITEM, has(TagsInit.Items.INGOTS_FIRE))
            .save(consumer, modId("heatblast_boots_blasting_smelt"));

        SimpleCookingRecipeBuilder
            .blasting(Ingredient.of(ItemInit.JACKET.get()), RecipeCategory.COMBAT,
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

    private OmnitrixRecipeBuilder crafter(OmnitrixCrafterCraftingBookCategory category,
            RecipeCategory recipeCategory, ItemLike result) {
        return new OmnitrixRecipeBuilder().shaped(category, recipeCategory, result);
    }

    private void addToolRecipes(Consumer<FinishedRecipe> consumer, TagKey<Item> material,
            ItemLike sword, ItemLike pickaxe, ItemLike axe, ItemLike shovel, ItemLike hoe,
            String prefix) {
        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS, sword)
            .define('#', Items.STICK)
            .define('A', material)
            .pattern(" A ")
            .pattern(" A ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "_sword"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS, pickaxe)
            .define('#', Items.STICK)
            .define('A', material)
            .pattern("AAA")
            .pattern(" # ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "_pickaxe"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS, axe)
            .define('#', Items.STICK)
            .define('A', material)
            .pattern("AA ")
            .pattern("A# ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "_axe"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS, shovel)
            .define('#', Items.STICK)
            .define('A', material)
            .pattern(" A ")
            .pattern(" # ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "_shovel"));

        crafter(OmnitrixCrafterCraftingBookCategory.TOOLS, RecipeCategory.TOOLS, hoe)
            .define('#', Items.STICK)
            .define('A', material)
            .pattern("AA ")
            .pattern(" # ")
            .pattern(" # ")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "_hoe"));
    }

    private void armorRecipes(Consumer<FinishedRecipe> consumer, TagKey<Item> material,
            ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots, String prefix,
            TagKey<Item> chestAccent) {
        crafter(OmnitrixCrafterCraftingBookCategory.ARMOUR, RecipeCategory.COMBAT, helmet)
            .define('A', material)
            .pattern("AAA")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "helmet"));

        OmnitrixRecipeBuilder chestplateRecipe = crafter(OmnitrixCrafterCraftingBookCategory.ARMOUR,
                RecipeCategory.COMBAT, chestplate).define('A', material);
        if (chestAccent == null) {
            chestplateRecipe.pattern("A A").pattern("AAA").pattern("AAA");
        } else {
            chestplateRecipe.define('#', chestAccent).pattern("A A").pattern("A#A").pattern("AAA");
        }
        chestplateRecipe.unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "chestplate"));

        crafter(OmnitrixCrafterCraftingBookCategory.ARMOUR, RecipeCategory.COMBAT, leggings)
            .define('A', material)
            .pattern("AAA")
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "leggings"));

        crafter(OmnitrixCrafterCraftingBookCategory.ARMOUR, RecipeCategory.COMBAT, boots)
            .define('A', material)
            .pattern("A A")
            .pattern("A A")
            .unlockedBy(HAS_ITEM, has(material))
            .save(consumer, modId(prefix + "boots"));
    }

    private Ingredient ingredient(Item entry) {
        return Ingredient.of(entry);
    }
}


