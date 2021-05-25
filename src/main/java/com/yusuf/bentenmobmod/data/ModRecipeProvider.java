package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.init.BlockInit;
import com.yusuf.bentenmobmod.core.init.EntityTypesInit;
import com.yusuf.bentenmobmod.core.init.ItemInit;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        //blocks
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.INFINITUM.get(), 9)

                .requires(BlockInit.INFINITUM_BLOCK.get())
                .unlocks("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.INFINITUM_BLOCK.get())
                .define('#', ItemInit.INFINITUM.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.BLACK_DIAMOND.get(), 9)

                .addIngredient(BlockInit.BLACK_DIAMOND_BLOCK.get())
                .addCriterion("has_item", containsItem(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.BLACK_DIAMOND_BLOCK.get())
                .define('#', ItemInit.BLACK_DIAMOND.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.FIRE.get(), 9)

                .requires(BlockInit.FIRE_BLOCK.get())
                .unlocks("has_item", has(ItemInit.FIRE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.FIRE_BLOCK.get())
                .define('#', ItemInit.FIRE.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.OMNITRIX.get(), 9)

                .requires(BlockInit.OMNITRIX_BLOCK.get())
                .unlocks("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.OMNITRIX_BLOCK.get())
                .define('#', ItemInit.OMNITRIX.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.SPEED.get(), 9)

                .requires(BlockInit.SPEED_BLOCK.get())
                .unlocks("has_item", has(ItemInit.SPEED.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.SPEED_BLOCK.get())
                .define('#', ItemInit.SPEED.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.SPEED.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.RUBY.get(), 9)

                .requires(BlockInit.RUBY_BLOCK.get())
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.RUBY_BLOCK.get())
                .define('#', ItemInit.RUBY.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.LEGENDARY_ORE.get(), 9)

                .requires(BlockInit.LEGENDARY_BLOCK.get())
                .unlocks("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.LEGENDARY_BLOCK.get())
                .define('#', ItemInit.LEGENDARY_ORE.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .unlocks("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer);




        //ingots

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.BLACK_DIAMOND.get(), 1)

                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlocks("has_item", has(ItemInit.BLACK_DIAMOND_SCRAP.get()))
                .save(consumer, modId("black_diamond_scrap"));

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.INFINITUM.get(), 1)

                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlocks("has_item", has(ItemInit.INFINITUM_SCRAP.get()))
                .save(consumer, modId("infinitum_scrap"));



        //amour and tools
        ShapedRecipeBuilder.shapedRecipe(ItemInit.AXE.get())
        .define('#', Items.STICK.getItem())
        .define('K', ItemInit.RUBY.get())
        .define('A', ItemInit.LEGENDARY_ORE.get())
        .patternLine("KK ")
        .patternLine("A# ")
        .patternLine(" # ")
        .unlocks("has_item", has(ItemInit.RUBY.get()))
        .save(consumer,modId("axe"));

/*
        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('K', ItemInit.FIRE.get())
                .define('A', ItemInit.OMNITRIX.get())
                .pattern(" K ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_sword"));
*/
        ShapedRecipeBuilder.shapedRecipe(ItemInit.SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .patternLine(" A ")
                .patternLine(" A ")
                .patternLine(" # ")
                .unlocks("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("sword"));


        ShapedRecipeBuilder.shapedRecipe(ItemInit.HELMET.get())
                .key('A', ItemInit.LEGENDARY_ORE.get())
                .patternLine("AAA")
                .patternLine("A A")
                .addCriterion("has_item", hasItem(ItemInit.LEGENDARY_ORE.get()))
                .build(consumer,modId("helmet"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.CHESTPLATE.get())
                .define('#', ItemInit.RUBY.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .patternLine("A A")
                .patternLine("A#A")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("chestplate"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.LEGGINGS.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .patternLine("AAA")
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("leggings"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.BOOT.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("boot"));



        ShapedRecipeBuilder.shapedRecipe(ItemInit.HEATBLAST_HELMET.get())
                .define('A', ItemInit.FIRE.get())
                .patternLine("AAA")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_helmet"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.HEATBLAST_CHESTPLATE.get())
                .define('#', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.FIRE.get())
                .patternLine("A A")
                .patternLine("A#A")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_chestplate"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.HEATBLAST_LEGGINGS.get())
                .define('A', ItemInit.FIRE.get())
                .patternLine("AAA")
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_leggings"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.HEATBLAST_BOOT.get())
                .define('A', ItemInit.FIRE.get())
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_boot"));


        ShapedRecipeBuilder.shapedRecipe(ItemInit.XLR8_HELMET.get())
                .define('A', ItemInit.SPEED.get())
                .patternLine("AAA")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_helmet"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.XLR8_CHESTPLATE.get())
                .define('#', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.SPEED.get())
                .patternLine("A A")
                .patternLine("A#A")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_chestplate"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.XLR8_LEGGINGS.get())
                .define('A', ItemInit.SPEED.get())
                .patternLine("AAA")
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_leggings"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.XLR8_BOOT.get())
                .define('A', ItemInit.SPEED.get())
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_boot"));



        ShapedRecipeBuilder.shapedRecipe(ItemInit.FOURARMS_HELMET.get())
                .define('A', ItemInit.RUBY.get())
                .patternLine("AAA")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_helmet"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.FOURARMS_CHESTPLATE.get())
                .define('#', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.RUBY.get())
                .patternLine("A A")
                .patternLine("A#A")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_chestplate"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.FOURARMS_LEGGINGS.get())
                .define('A', ItemInit.RUBY.get())
                .patternLine("AAA")
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_leggings"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.FOURARMS_BOOT.get())
                .define('A', ItemInit.RUBY.get())
                .patternLine("A A")
                .patternLine("A A")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_boot"));



        ShapedRecipeBuilder.shapedRecipe(ItemInit.JACKET.get())
                .define('A', ItemInit.OMNITRIX.get())
                .patternLine("A A")
                .patternLine("AAA")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer,modId("jacket"));




        //food
        ShapedRecipeBuilder.shapedRecipe(ItemInit.OMNITRIX_APPLE.get())
                .define('A', ItemInit.OMNITRIX.get())
                .define('L', Items.APPLE.getItem())
                .patternLine("AAA")
                .patternLine("ALA")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer,modId("omnitrix_apple"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.ENCHANTED_OMNITRIX_APPLE.get())
                .define('A', BlockInit.OMNITRIX_BLOCK.get())
                .define('L', Items.APPLE.getItem())
                .patternLine("AAA")
                .patternLine("ALA")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer,modId("enchanted_omnitrix_apple"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.FIRE_APPLE.get())
                .define('A', ItemInit.FIRE.get())
                .define('L', Items.APPLE.getItem())
                .patternLine("AAA")
                .patternLine("ALA")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("fire_apple"));

        ShapedRecipeBuilder.shapedRecipe(ItemInit.RUBY_APPLE.get())
                .define('A', ItemInit.RUBY.get())
                .define('L', Items.APPLE.getItem())
                .patternLine("AAA")
                .patternLine("ALA")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("ruby_apple"));


        ShapedRecipeBuilder.shapedRecipe(Items.ENCHANTED_GOLDEN_APPLE.getItem())
                .define('A', Items.GOLD_BLOCK.getItem())
                .define('L', Items.APPLE.getItem())
                .patternLine("AAA")
                .patternLine("ALA")
                .patternLine("AAA")
                .unlocks("has_item", has(Items.GOLD_INGOT.getItem()))
                .save(consumer,modId("enchanted_golden_apple"));

        //other items

        ShapedRecipeBuilder.shapedRecipe(EntityTypesInit.VILGAX_SPAWN_EGG.get())
                .define('L', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.RUBY.get())
                .patternLine("AAA")
                .patternLine("ALA")
                .patternLine("AAA")
                .unlocks("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("vilgax_spawn_egg"));


        //smelting and blasting recipes

        //ores

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.RUBY_ORE.get()), ItemInit.RUBY.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.RUBY_ORE.get()))
                .build(consumer, modId("ruby_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.RUBY_ORE.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.RUBY_ORE.get()))
                .build(consumer, modId("ruby_ore_blasting_smelt"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.OMNITRIX_ORE.get()), ItemInit.OMNITRIX.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.OMNITRIX_ORE.get()))
                .build(consumer, modId("omnitrix_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.OMNITRIX_ORE.get()), ItemInit.OMNITRIX.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.OMNITRIX_ORE.get()))
                .build(consumer, modId("omnitrix_ore_blasting_smelt"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.FIRE_ORE.get()), ItemInit.FIRE.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.FIRE_ORE.get()))
                .build(consumer, modId("fire_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.FIRE_ORE.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.FIRE_ORE.get()))
                .build(consumer, modId("fire_ore_blasting_smelt"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.SPEED_ORE.get()), ItemInit.SPEED.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.SPEED_ORE.get()))
                .build(consumer, modId("speed_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.SPEED_ORE.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.SPEED_ORE.get()))
                .build(consumer, modId("speed_ore_blasting_smelt"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.LEGENDARY_ORE_BLOCK.get()), ItemInit.LEGENDARY_ORE.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.LEGENDARY_ORE_BLOCK.get()))
                .build(consumer, modId("legendary_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.LEGENDARY_ORE_BLOCK.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.LEGENDARY_ORE_BLOCK.get()))
                .build(consumer, modId("legendary_ore_blasting_smelt"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.INFINITUM_ORE.get()), ItemInit.INFINITUM_SCRAP.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.INFINITUM_ORE.get()))
                .build(consumer, modId("infinitum_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.INFINITUM_ORE.get()), ItemInit.INFINITUM_SCRAP.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.INFINITUM_ORE.get()))
                .build(consumer, modId("infinitum_ore_blasting_smelt"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.BLACK_DIAMOND_ORE.get()), ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.6f, 300)
                .addCriterion("has_item", hasItem(BlockInit.BLACK_DIAMOND_ORE.get()))
                .build(consumer, modId("black_diamond_ore_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.BLACK_DIAMOND_ORE.get()), ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(BlockInit.BLACK_DIAMOND_ORE.get()))
                .build(consumer, modId("black_diamond_ore_blasting_smelt"));

        //armour

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.JACKET.get()), ItemInit.OMNITRIX.get(), 0.4f, 300)
                .addCriterion("has_item", hasItem(ItemInit.OMNITRIX.get()))
                .build(consumer, modId("jacket_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.HELMET.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.LEGENDARY_ORE.get()))
                .build(consumer, modId("helmet_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.CHESTPLATE.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.LEGENDARY_ORE.get()))
                .build(consumer, modId("chestplate_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.LEGGINGS.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.LEGENDARY_ORE.get()))
                .build(consumer, modId("leggings_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.BOOT.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.LEGENDARY_ORE.get()))
                .build(consumer, modId("boots_blasting_smelt"));


        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.XLR8_HELMET.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.SPEED.get()))
                .build(consumer, modId("xlr8_helmet_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.XLR8_CHESTPLATE.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.SPEED.get()))
                .build(consumer, modId("xlr8_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.XLR8_LEGGINGS.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.SPEED.get()))
                .build(consumer, modId("xlr8_leggings_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.XLR8_BOOT.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.SPEED.get()))
                .build(consumer, modId("xlr8_boots_blasting_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.FOURARMS_HELMET.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.RUBY.get()))
                .build(consumer, modId("fourarms_helmet_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.FOURARMS_CHESTPLATE.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.RUBY.get()))
                .build(consumer, modId("fourarms_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.FOURARMS_LEGGINGS.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.RUBY.get()))
                .build(consumer, modId("fourarms_leggings_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.FOURARMS_BOOT.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.RUBY.get()))
                .build(consumer, modId("fourarms_boots_blasting_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.BLACK_DIAMOND_HELMET.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.BLACK_DIAMOND.get()))
                .build(consumer, modId("black_diamond_helmet_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.BLACK_DIAMOND_CHESTPLATE.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.BLACK_DIAMOND.get()))
                .build(consumer, modId("black_diamond_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.BLACK_DIAMOND_LEGGINGS.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.BLACK_DIAMOND.get()))
                .build(consumer, modId("black_diamond_leggings_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.BLACK_DIAMOND_BOOTS.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.BLACK_DIAMOND.get()))
                .build(consumer, modId("black_diamond_boots_blasting_smelt"));

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.INFINITUM_HELMET.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.INFINITUM.get()))
                .build(consumer, modId("infinitum_helmet_blasting_smelt"));
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.INFINITUM_CHESTPLATE.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.INFINITUM.get()))
                .build(consumer, modId("infinitum_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.INFINITUM_LEGGINGS.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.INFINITUM.get()))
                .build(consumer, modId("infinitum_leggings_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.INFINITUM_BOOTS.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.INFINITUM.get()))
                .build(consumer, modId("infinitum_boots_blasting_smelt"));


        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.HEATBLAST_HELMET.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.FIRE.get()))
                .build(consumer, modId("heatblast_helmet_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.HEATBLAST_CHESTPLATE.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.FIRE.get()))
                .build(consumer, modId("heatblast_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.HEATBLAST_LEGGINGS.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.FIRE.get()))
                .build(consumer, modId("heatblast_leggings_blasting_smelt"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.HEATBLAST_BOOT.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .addCriterion("has_item", hasItem(ItemInit.FIRE.get()))
                .build(consumer, modId("heatblast_boots_blasting_smelt"));


    }
        private static ResourceLocation modId(String path){
            return new ResourceLocation(Main.MOD_ID, path);
        }

}




