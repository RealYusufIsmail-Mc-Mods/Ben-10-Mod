package com.yusuf.bentenmobmod.data;

import com.yusuf.bentenmobmod.Main;
import com.yusuf.bentenmobmod.core.init.BlockInit;
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
        ShapelessRecipeBuilder.shapeless(ItemInit.INFINITUM.get(), 9)

                .requires(BlockInit.INFINITUM_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.INFINITUM_BLOCK.get())
                .define('#', ItemInit.INFINITUM.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemInit.BLACK_DIAMOND.get(), 9)

                .requires(BlockInit.BLACK_DIAMOND_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.BLACK_DIAMOND_BLOCK.get())
                .define('#', ItemInit.BLACK_DIAMOND.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.BLACK_DIAMOND.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemInit.FIRE.get(), 9)

                .requires(BlockInit.FIRE_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.FIRE_BLOCK.get())
                .define('#', ItemInit.FIRE.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.INFINITUM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemInit.OMNITRIX.get(), 9)

                .requires(BlockInit.OMNITRIX_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.OMNITRIX_BLOCK.get())
                .define('#', ItemInit.OMNITRIX.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemInit.SPEED.get(), 9)

                .requires(BlockInit.SPEED_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.SPEED_BLOCK.get())
                .define('#', ItemInit.SPEED.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemInit.RUBY.get(), 9)

                .requires(BlockInit.RUBY_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.RUBY_BLOCK.get())
                .define('#', ItemInit.RUBY.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemInit.LEGENDARY_ORE.get(), 9)

                .requires(BlockInit.LEGENDARY_BLOCK.get())
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.LEGENDARY_BLOCK.get())
                .define('#', ItemInit.LEGENDARY_ORE.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer);

        //ores

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RUBY_ORE.get()), ItemInit.RUBY.get(), 0.7f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, modId("ruby_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RUBY_ORE.get()), ItemInit.RUBY.get(), 0.4f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, modId("ruby_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.OMNITRIX_ORE.get()), ItemInit.OMNITRIX.get(), 0.7f, 300)
                .unlockedBy("has_item", has(BlockInit.OMNITRIX_ORE.get()))
                .save(consumer, modId("omnitrix_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.OMNITRIX_ORE.get()), ItemInit.OMNITRIX.get(), 0.4f, 300)
                .unlockedBy("has_item", has(BlockInit.OMNITRIX_ORE.get()))
                .save(consumer, modId("omnitrix_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.FIRE_ORE.get()), ItemInit.FIRE.get(), 0.7f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, modId("fire_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.FIRE_ORE.get()), ItemInit.FIRE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, modId("fire_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.SPEED_ORE.get()), ItemInit.SPEED.get(), 0.7f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, modId("speed_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.SPEED_ORE.get()), ItemInit.SPEED.get(), 0.4f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, modId("speed_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.LEGENDARY_ORE_BLOCK.get()), ItemInit.LEGENDARY_ORE.get(), 0.7f, 300)
                .unlockedBy("has_item", has(BlockInit.LEGENDARY_ORE_BLOCK.get()))
                .save(consumer, modId("legendary_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.LEGENDARY_ORE_BLOCK.get()), ItemInit.LEGENDARY_ORE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(BlockInit.LEGENDARY_ORE_BLOCK.get()))
                .save(consumer, modId("legendary_ore_blasting_smelt"));


        //ingots

        ShapelessRecipeBuilder.shapeless(ItemInit.BLACK_DIAMOND.get(), 1)

                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(ItemInit.BLACK_DIAMOND_SCRAP.get())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlockedBy("has_item", has(ItemInit.BLACK_DIAMOND_SCRAP.get()))
                .save(consumer, modId("black_diamond_scrap"));

        ShapelessRecipeBuilder.shapeless(ItemInit.INFINITUM.get(), 1)

                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(ItemInit.INFINITUM_SCRAP.get())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlockedBy("has_item", has(ItemInit.INFINITUM_SCRAP.get()))
                .save(consumer, modId("infinitum_scrap"));



        //amour and tools
        ShapedRecipeBuilder.shaped(ItemInit.AXE.get())
        .define('#', Items.STICK.getItem())
        .define('K', ItemInit.RUBY.get())
        .define('A', ItemInit.LEGENDARY_ORE.get())
        .pattern("KK ")
        .pattern("A# ")
        .pattern(" # ")
        .unlockedBy("has_item", has(ItemInit.RUBY.get()))
        .save(consumer,modId("axe"));


        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('K', ItemInit.FIRE.get())
                .define('A', ItemInit.OMNITRIX.get())
                .pattern(" K ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_sword"));

        ShapedRecipeBuilder.shaped(ItemInit.SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("sword"));


        ShapedRecipeBuilder.shaped(ItemInit.HELMET.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.CHESTPLATE.get())
                .define('#', ItemInit.RUBY.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.LEGGINGS.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.BOOT.get())
                .define('A', ItemInit.LEGENDARY_ORE.get())
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer,modId("boot"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HELMET.get()), ItemInit.LEGENDARY_ORE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer, modId("helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.CHESTPLATE.get()), ItemInit.LEGENDARY_ORE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer, modId("chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.LEGGINGS.get()), ItemInit.LEGENDARY_ORE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer, modId("leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.BOOT.get()), ItemInit.LEGENDARY_ORE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.LEGENDARY_ORE.get()))
                .save(consumer, modId("boots_blasting_smelt"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_HELMET.get())
                .define('A', ItemInit.FIRE.get())
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_CHESTPLATE.get())
                .define('#', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.FIRE.get())
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_LEGGINGS.get())
                .define('A', ItemInit.FIRE.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_BOOT.get())
                .define('A', ItemInit.FIRE.get())
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("heatblast_boot"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_HELMET.get()), ItemInit.FIRE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer, modId("heatblast_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_CHESTPLATE.get()), ItemInit.FIRE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer, modId("heatblast_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_LEGGINGS.get()), ItemInit.FIRE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer, modId("heatblast_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_BOOT.get()), ItemInit.FIRE.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer, modId("heatblast_boots_blasting_smelt"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_HELMET.get())
                .define('A', ItemInit.SPEED.get())
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_CHESTPLATE.get())
                .define('#', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.SPEED.get())
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_LEGGINGS.get())
                .define('A', ItemInit.SPEED.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_BOOT.get())
                .define('A', ItemInit.SPEED.get())
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer,modId("xlr8_boot"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_HELMET.get()), ItemInit.SPEED.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer, modId("xlr8_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_CHESTPLATE.get()), ItemInit.SPEED.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer, modId("xlr8_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_LEGGINGS.get()), ItemInit.SPEED.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer, modId("xlr8_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_BOOT.get()), ItemInit.SPEED.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.SPEED.get()))
                .save(consumer, modId("xlr8_boots_blasting_smelt"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_HELMET.get())
                .define('A', ItemInit.RUBY.get())
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_CHESTPLATE.get())
                .define('#', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.RUBY.get())
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_LEGGINGS.get())
                .define('A', ItemInit.RUBY.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_BOOT.get())
                .define('A', ItemInit.RUBY.get())
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("fourarms_boot"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_HELMET.get()), ItemInit.RUBY.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer, modId("fourarms_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_CHESTPLATE.get()), ItemInit.RUBY.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer, modId("fourarms_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_LEGGINGS.get()), ItemInit.RUBY.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer, modId("fourarms_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_BOOT.get()), ItemInit.RUBY.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer, modId("fourarms_boots_blasting_smelt"));

        ShapedRecipeBuilder.shaped(ItemInit.JACKET.get())
                .define('A', ItemInit.OMNITRIX.get())
                .pattern("A A")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer,modId("jacket"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.JACKET.get()), ItemInit.OMNITRIX.get(), 0.4f, 300)
                .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer, modId("jacket_blasting_smelt"));



        //food
        ShapedRecipeBuilder.shaped(ItemInit.OMNITRIX_APPLE.get())
                .define('A', ItemInit.OMNITRIX.get())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer,modId("omnitrix_apple"));

        ShapedRecipeBuilder.shaped(ItemInit.ENCHANTED_OMNITRIX_APPLE.get())
                .define('A', BlockInit.OMNITRIX_BLOCK.get())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.OMNITRIX.get()))
                .save(consumer,modId("enchanted_omnitrix_apple"));

        ShapedRecipeBuilder.shaped(ItemInit.FIRE_APPLE.get())
                .define('A', ItemInit.FIRE.get())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.FIRE.get()))
                .save(consumer,modId("fire_apple"));

        ShapedRecipeBuilder.shaped(ItemInit.RUBY_APPLE.get())
                .define('A', ItemInit.RUBY.get())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("ruby_apple"));


        ShapedRecipeBuilder.shaped(Items.ENCHANTED_GOLDEN_APPLE.getItem())
                .define('A', Items.GOLD_BLOCK.getItem())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(Items.GOLD_INGOT.getItem()))
                .save(consumer,modId("enchanted_golden_apple"));

        //other items

        ShapedRecipeBuilder.shaped(ItemInit.VILGAX_SPAWN_EGG.get())
                .define('L', ItemInit.OMNITRIX.get())
                .define('A', ItemInit.RUBY.get())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(ItemInit.RUBY.get()))
                .save(consumer,modId("vilgax_spawn_egg"));



    }
        private static ResourceLocation modId(String path){
            return new ResourceLocation(Main.MOD_ID, path);
        }

}




