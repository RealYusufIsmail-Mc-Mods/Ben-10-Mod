package com.yusuf.bentenmod.data;

import com.yusuf.bentenmod.BenTenMod;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.EntityTypesInit;
import com.yusuf.bentenmod.core.init.ItemInit;
import com.yusuf.bentenmod.core.init.TagsInit;
import com.yusuf.bentenmod.data.recipe.TableRecipeBuilder;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(BenTenMod.MOD_ID, path);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        //blocks
        ShapelessRecipeBuilder.shapeless(ItemInit.INFINITUM.get(), 9)

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

        ShapelessRecipeBuilder.shapeless(ItemInit.BLACK_DIAMOND.get(), 9)

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
        ShapelessRecipeBuilder.shapeless(ItemInit.FIRE.get(), 9)

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

        ShapelessRecipeBuilder.shapeless(ItemInit.OMNITRIX.get(), 9)

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

        ShapelessRecipeBuilder.shapeless(ItemInit.SPEED.get(), 9)

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

        ShapelessRecipeBuilder.shapeless(ItemInit.RUBY.get(), 9)

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

        ShapelessRecipeBuilder.shapeless(ItemInit.LEGENDARY_ORE.get(), 9)

                .requires(BlockInit.LEGENDARY_BLOCK.get())
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.LEGENDARY_BLOCK.get())
                .define('#', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer);


        //ingots

        ShapelessRecipeBuilder.shapeless(ItemInit.BLACK_DIAMOND.get(), 1)

                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP)
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlockedBy("has_item", has(TagsInit.Items.SCRAP_BLACK_DIAMOND_SCRAP))
                .save(consumer, id("black_diamond_scrap"));

        ShapelessRecipeBuilder.shapeless(ItemInit.INFINITUM.get(), 1)

                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(TagsInit.Items.SCRAP_INFINITUM_SCRAP)
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .requires(Items.GOLD_INGOT.getItem())
                .unlockedBy("has_item", has(TagsInit.Items.SCRAP_INFINITUM_SCRAP))
                .save(consumer, id("infinitum_scrap"));


        //amour and tools
        ShapedRecipeBuilder.shaped(ItemInit.AXE.get())
                .define('#', Items.STICK.getItem())
                .define('K', TagsInit.Items.INGOTS_RUBY)
                .define('A', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern("KK ")
                .pattern("A# ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("axe"));

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
        ShapedRecipeBuilder.shaped(ItemInit.SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("sword"));


        ShapedRecipeBuilder.shaped(ItemInit.IMPERIUM_SWORD.get())
                .define('#', Items.STICK.getItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer, id("imperium_sword"));

        ShapedRecipeBuilder.shaped(ItemInit.IMPERIUM_PICKAXE.get())
                .define('#', Items.STICK.getItem())
                .define('A', TagsInit.Items.INGOTS_IMPERIUM)
                .pattern("AAA")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_IMPERIUM))
                .save(consumer, id("imperium_pickaxe"));

        ShapedRecipeBuilder.shaped(ItemInit.HELMET.get())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_RUBY)
                .define('A', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.BOOT.get())
                .define('A', TagsInit.Items.INGOTS_LEGENDARY_ORE)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("boot"));


        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_HELMET.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_OMNITRIX)
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.HEATBLAST_BOOT.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_boot"));


        ShapedRecipeBuilder.shaped(ItemInit.XLR8_HELMET.get())
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_OMNITRIX)
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.XLR8_BOOT.get())
                .define('A', TagsInit.Items.INGOTS_SPEED)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_boot"));


        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_HELMET.get())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_helmet"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_CHESTPLATE.get())
                .define('#', TagsInit.Items.INGOTS_OMNITRIX)
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("A A")
                .pattern("A#A")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_chestplate"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_LEGGINGS.get())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_leggings"));

        ShapedRecipeBuilder.shaped(ItemInit.FOURARMS_BOOT.get())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("A A")
                .pattern("A A")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_boot"));


        ShapedRecipeBuilder.shaped(ItemInit.JACKET.get())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .pattern("A A")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, id("jacket"));


        //food
        ShapedRecipeBuilder.shaped(ItemInit.OMNITRIX_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_OMNITRIX)
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, id("omnitrix_apple"));

        ShapedRecipeBuilder.shaped(ItemInit.ENCHANTED_OMNITRIX_APPLE.get())
                .define('A', BlockInit.OMNITRIX_BLOCK.get())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, id("enchanted_omnitrix_apple"));

        ShapedRecipeBuilder.shaped(ItemInit.FIRE_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_FIRE)
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("fire_apple"));

        ShapedRecipeBuilder.shaped(ItemInit.RUBY_APPLE.get())
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("ruby_apple"));


        ShapedRecipeBuilder.shaped(Items.ENCHANTED_GOLDEN_APPLE.getItem())
                .define('A', Items.GOLD_BLOCK.getItem())
                .define('L', Items.APPLE.getItem())
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(Items.GOLD_INGOT.getItem()))
                .save(consumer, id("enchanted_golden_apple"));

        //other items

        ShapedRecipeBuilder.shaped(EntityTypesInit.VILGAX_SPAWN_EGG.get())
                .define('L', TagsInit.Items.INGOTS_OMNITRIX)
                .define('A', TagsInit.Items.INGOTS_RUBY)
                .pattern("AAA")
                .pattern("ALA")
                .pattern("AAA")
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("vilgax_spawn_egg"));


        //smelting and blasting recipes

        //ores

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RUBY_ORE.get()), ItemInit.RUBY.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, id("ruby_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RUBY_ORE.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.RUBY_ORE.get()))
                .save(consumer, id("ruby_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.OMNITRIX_ORE.get()), ItemInit.OMNITRIX.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.OMNITRIX_ORE.get()))
                .save(consumer, id("omnitrix_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.OMNITRIX_ORE.get()), ItemInit.OMNITRIX.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.OMNITRIX_ORE.get()))
                .save(consumer, id("omnitrix_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.FIRE_ORE.get()), ItemInit.FIRE.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, id("fire_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.FIRE_ORE.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.FIRE_ORE.get()))
                .save(consumer, id("fire_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.SPEED_ORE.get()), ItemInit.SPEED.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, id("speed_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.SPEED_ORE.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.SPEED_ORE.get()))
                .save(consumer, id("speed_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.LEGENDARY_ORE_BLOCK.get()), ItemInit.LEGENDARY_ORE.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.LEGENDARY_ORE_BLOCK.get()))
                .save(consumer, id("legendary_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.LEGENDARY_ORE_BLOCK.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.LEGENDARY_ORE_BLOCK.get()))
                .save(consumer, id("legendary_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), ItemInit.INFINITUM_SCRAP.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.INFINITUM_ORE.get()))
                .save(consumer, id("infinitum_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.INFINITUM_ORE.get()), ItemInit.INFINITUM_SCRAP.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.INFINITUM_ORE.get()))
                .save(consumer, id("infinitum_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.BLACK_DIAMOND_ORE.get()))
                .save(consumer, id("black_diamond_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.BLACK_DIAMOND_ORE.get()), ItemInit.BLACK_DIAMOND_SCRAP.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.BLACK_DIAMOND_ORE.get()))
                .save(consumer, id("black_diamond_ore_blasting_smelt"));

        CookingRecipeBuilder.smelting(Ingredient.of(BlockInit.IMPERIUM_ORE.get()), ItemInit.IMPERIUM.get(), 0.6f, 300)
                .unlockedBy("has_item", has(BlockInit.IMPERIUM_ORE.get()))
                .save(consumer, id("imperium_ore_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(BlockInit.IMPERIUM_ORE.get()), ItemInit.IMPERIUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(BlockInit.IMPERIUM_ORE.get()))
                .save(consumer, id("imperium_ore_blasting_smelt"));

        //armour

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HELMET.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.CHESTPLATE.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.LEGGINGS.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.BOOT.get()), ItemInit.LEGENDARY_ORE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_LEGENDARY_ORE))
                .save(consumer, id("boots_blasting_smelt"));


        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_HELMET.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_CHESTPLATE.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_LEGGINGS.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.XLR8_BOOT.get()), ItemInit.SPEED.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_SPEED))
                .save(consumer, id("xlr8_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_HELMET.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_CHESTPLATE.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_LEGGINGS.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.FOURARMS_BOOT.get()), ItemInit.RUBY.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_RUBY))
                .save(consumer, id("fourarms_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_HELMET.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, id("black_diamond_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_CHESTPLATE.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, id("black_diamond_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_LEGGINGS.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, id("black_diamond_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.BLACK_DIAMOND_BOOTS.get()), ItemInit.BLACK_DIAMOND.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_BLACK_DIAMOND))
                .save(consumer, id("black_diamond_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.INFINITUM_HELMET.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, id("infinitum_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.INFINITUM_CHESTPLATE.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, id("infinitum_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.INFINITUM_LEGGINGS.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, id("infinitum_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.INFINITUM_BOOTS.get()), ItemInit.INFINITUM.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_INFINITUM))
                .save(consumer, id("infinitum_boots_blasting_smelt"));


        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_HELMET.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_helmet_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_CHESTPLATE.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_chestplate_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_LEGGINGS.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_leggings_blasting_smelt"));
        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.HEATBLAST_BOOT.get()), ItemInit.FIRE.get(), 0.3f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_FIRE))
                .save(consumer, id("heatblast_boots_blasting_smelt"));

        CookingRecipeBuilder.blasting(Ingredient.of(ItemInit.JACKET.get()), ItemInit.OMNITRIX.get(), 0.4f, 300)
                .unlockedBy("has_item", has(TagsInit.Items.INGOTS_OMNITRIX))
                .save(consumer, id("jacket_blasting_smelt"));

        //EXAMPLE RECIPE
        TableRecipeBuilder.build(ingredient(Items.IRON_INGOT), ingredient(Blocks.COBBLESTONE), ingredient(Items.IRON_INGOT), Blocks.IRON_ORE)
                .unlockedBy("test_recipe", InventoryChangeTrigger.Instance.hasItems(Items.IRON_INGOT))
                .save(consumer, id("example_recipe"));
    }

    private Ingredient ingredient(IItemProvider entry) {
        return Ingredient.of(entry);
    }
}




