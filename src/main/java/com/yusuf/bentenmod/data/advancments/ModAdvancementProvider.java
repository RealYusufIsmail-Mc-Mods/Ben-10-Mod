package com.yusuf.bentenmod.data.advancments;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.yusuf.realyusufismailcore.advancements.GenericIntTrigger;
import net.yusuf.realyusufismailcore.util.NameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import static com.yusuf.bentenmod.core.init.BlockInit.*;
import static com.yusuf.bentenmod.core.init.ItemInit.*;
import static net.yusuf.realyusufismailcore.core.init.ItemInitCore.*;
import static net.yusuf.realyusufismailcore.core.init.BlockInitCore.*;

public class ModAdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    public ModAdvancementProvider(DataGenerator generator) {
        this.generator = generator;
    }
    @Override
    public void run(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        //noinspection OverlyLongLambda
        Consumer<Advancement> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate advancement " + p_204017_3_.getId());
            } else {
                Path path1 = getPath(path, p_204017_3_);

                try {
                    IDataProvider.save(GSON, cache, p_204017_3_.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }
            }
        };

        new Advancements().accept(consumer);
    }
    @Override
    public String getName() {
        return "Ben 10 mod - Advancements";
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }
    private static class Advancements implements Consumer<Consumer<Advancement>> {
        @SuppressWarnings({"unused", "OverlyLongMethod"})
        @Override
        public void accept(Consumer<Advancement> consumer) {
            ItemStack rootIcon = new ItemStack(OMNITRIX.get());
            Advancement root = Advancement.Builder.advancement()
                    .display(rootIcon, title("root"), description("root"),
                            new ResourceLocation("minecraft:textures/block/light_blue_wool.png"), FrameType.TASK,
                            true, false, false)
                    .addCriterion("get_item", getItem(Items.CRAFTING_TABLE))
                    .save(consumer, id("root"));
            //black diamond
            Advancement black_diamond_scrap = Advancement.Builder.advancement()
                    .parent(root)
                    .display(BLACK_DIAMOND_SCRAP.get(), title("black_diamond_scrap"),
                            description("black_diamond_scrap"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_scrap", getItem(BLACK_DIAMOND_SCRAP.get()))
                    .save(consumer, id("black_diamond_scrap"));

            Advancement black_diamond_ingot = Advancement.Builder.advancement()
                    .parent(black_diamond_scrap)
                    .display(BLACK_DIAMOND.get(), title("black_diamond_ingot"),
                            description("black_diamond_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_scrap", getItem(BLACK_DIAMOND.get()))
                    .save(consumer, id("black_diamond_ingot"));

            Advancement black_diamond_block = Advancement.Builder.advancement()
                    .parent(black_diamond_scrap)
                    .display(BLACK_DIAMOND_BLOCK.get(), title("black_diamond_block"),
                            description("black_diamond_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(BLACK_DIAMOND_BLOCK.get()))
                    .save(consumer, id("black_diamond_block"));

            Advancement black_diamond_armour = Advancement.Builder.advancement()
                    .parent(black_diamond_ingot)
                    .display(BLACK_DIAMOND_CHESTPLATE.get(), title("black_diamond_armour"),
                            description("black_diamond_armour"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("black_diamond_helmet", getItem(BLACK_DIAMOND_HELMET.get()))
                    .addCriterion("black_diamond_chestplate", getItem(BLACK_DIAMOND_CHESTPLATE.get()))
                    .addCriterion("black_diamond_leggings", getItem(BLACK_DIAMOND_LEGGINGS.get()))
                    .addCriterion("black_diamond_boots", getItem(BLACK_DIAMOND_BOOTS.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("black_diamond_armour"));

            Advancement black_diamond_tools = Advancement.Builder.advancement()
                    .parent(black_diamond_ingot)
                    .display(BLACK_DIAMOND_SWORD.get(), title("black_diamond_tools"),
                            description("black_diamond_tools"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("black_diamond_sword", getItem(BLACK_DIAMOND_SWORD.get()))
                    .addCriterion("black_diamond_pickaxe", getItem(BLACK_DIAMOND_PICKAXE.get()))
                    .addCriterion("black_diamond_shovel", getItem(BLACK_DIAMOND_SHOVEL.get()))
                    .addCriterion("black_diamond_axe", getItem(BLACK_DIAMOND_AXE.get()))
                    .addCriterion("black_diamond_hoe", getItem(BLACK_DIAMOND_HOE.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("black_diamond_tools"));

            //copper
            Advancement copper_ingot = Advancement.Builder.advancement()
                    .parent(root)
                    .display(COPPER.get(), title("copper_ingot"),
                            description("copper_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(COPPER.get()))
                    .save(consumer, id("copper_ingot"));

            Advancement copper_block = Advancement.Builder.advancement()
                    .parent(copper_ingot)
                    .display(COPPER_BLOCK.get(), title("copper_block"),
                            description("copper_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(COPPER_BLOCK.get()))
                    .save(consumer, id("copper_block"));

            //fire
            Advancement fire_ore = Advancement.Builder.advancement()
                    .parent(root)
                    .display(FIRE_ORE.get(), title("fire_ore"),
                            description("fire_ore"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ore", getItem(FIRE_ORE.get()))
                    .save(consumer, id("fire_ore"));

            Advancement fire_ingot = Advancement.Builder.advancement()
                    .parent(fire_ore)
                    .display(FIRE.get(), title("fire_ingot"),
                            description("fire_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(FIRE.get()))
                    .save(consumer, id("fire_ingot"));

            Advancement fire_apple = simpleGetItem(consumer, FIRE_APPLE.get(), fire_ingot);

            Advancement fire_block = Advancement.Builder.advancement()
                    .parent(fire_ingot)
                    .display(FIRE_BLOCK.get(), title("fire_block"),
                            description("fire_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(FIRE_BLOCK.get()))
                    .save(consumer, id("fire_block"));


            Advancement heatblast_armour = Advancement.Builder.advancement()
                    .parent(fire_ingot)
                    .display(HEATBLAST_CHESTPLATE.get(), title("heatblast_armour"),
                            description("heatblast_armour"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("heatblast_helmet", getItem(HEATBLAST_HELMET.get()))
                    .addCriterion("heatblast_chestplate", getItem(HEATBLAST_CHESTPLATE.get()))
                    .addCriterion("heatblast_leggings", getItem(HEATBLAST_LEGGINGS.get()))
                    .addCriterion("heatblast_boots", getItem(HEATBLAST_BOOTS.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("heatblast_armour"));

            Advancement heatblast_sword = Advancement.Builder.advancement()
                    .parent(fire_ingot)
                    .display(HEATBLAST_SWORD.get(), title("heatblast_sword"),
                            description("heatblast_sword"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("heatblast_sword", getItem(HEATBLAST_SWORD.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("heatblast_sword"));

            //omnitrix
            Advancement omnitrix_ore = Advancement.Builder.advancement()
                    .parent(root)
                    .display(OMNITRIX_ORE.get(), title("omnitrix_ore"),
                            description("omnitrix_ore"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ore", getItem(OMNITRIX_ORE.get()))
                    .save(consumer, id("omnitrix_ore"));

            Advancement omnitrix_ingot = Advancement.Builder.advancement()
                    .parent(omnitrix_ore)
                    .display(OMNITRIX.get(), title("omnitrix_ingot"),
                            description("omnitrix_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(OMNITRIX.get()))
                    .save(consumer, id("omnitrix_ingot"));

            Advancement omnitrix_apple = simpleGetItem(consumer, OMNITRIX_APPLE.get(), omnitrix_ingot);
            Advancement enchanted_omnitrix_apple = simpleGetItem(consumer, ENCHANTED_OMNITRIX_APPLE.get(), omnitrix_ingot);

            Advancement omnitrix_block = Advancement.Builder.advancement()
                    .parent(omnitrix_ingot)
                    .display(OMNITRIX_BLOCK.get(), title("omnitrix_block"),
                            description("omnitrix_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(OMNITRIX_BLOCK.get()))
                    .save(consumer, id("omnitrix_block"));


            //ruby
            Advancement ruby_ore = Advancement.Builder.advancement()
                    .parent(root)
                    .display(RUBY_ORE.get(), title("ruby_ore"),
                            description("ruby_ore"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ore", getItem(RUBY_ORE.get()))
                    .save(consumer, id("ruby_ore"));

            Advancement ruby_ingot = Advancement.Builder.advancement()
                    .parent(ruby_ore)
                    .display(RUBY.get(), title("ruby_ingot"),
                            description("ruby_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(RUBY.get()))
                    .save(consumer, id("ruby_ingot"));

            Advancement ruby_apple = simpleGetItem(consumer, RUBY_APPLE.get(), ruby_ingot);

            Advancement ruby_block = Advancement.Builder.advancement()
                    .parent(ruby_ingot)
                    .display(RUBY_BLOCK.get(), title("ruby_block"),
                            description("ruby_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(RUBY_BLOCK.get()))
                    .save(consumer, id("ruby_block"));

            Advancement ruby_axe = Advancement.Builder.advancement()
                    .parent(ruby_ingot)
                    .display(AXE.get(), title("ruby_axe"),
                            description("ruby_axe"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("axe", getItem(AXE.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("ruby_axe"));

            //imperium
            Advancement imperium_ore = Advancement.Builder.advancement()
                    .parent(root)
                    .display(IMPERIUM_ORE.get(), title("imperium_ore"),
                            description("imperium_ore"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ore", getItem(IMPERIUM_ORE.get()))
                    .save(consumer, id("imperium_ore"));

            Advancement imperium_ingot = Advancement.Builder.advancement()
                    .parent(imperium_ore)
                    .display(IMPERIUM.get(), title("imperium_ingot"),
                            description("imperium_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(IMPERIUM.get()))
                    .save(consumer, id("imperium_ingot"));

            Advancement imperium_block = Advancement.Builder.advancement()
                    .parent(imperium_ingot)
                    .display(IMPERIUM_BLOCK.get(), title("imperium_block"),
                            description("imperium_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(IMPERIUM_BLOCK.get()))
                    .save(consumer, id("imperium_block"));

            Advancement imperium_tools = Advancement.Builder.advancement()
                    .parent(imperium_ingot)
                    .display(IMPERIUM_SWORD.get(), title("imperium_tools"),
                            description("imperium_tools"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("imperium_pickaxe", getItem(IMPERIUM_PICKAXE.get()))
                    .addCriterion("imperium_sword", getItem(IMPERIUM_SWORD.get()))
                    .addCriterion("imperium_axe", getItem(IMPERIUM_AXE.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("imperium_tools"));

            Advancement upgraded_imperium_pickaxe = Advancement.Builder.advancement()
                    .parent(imperium_tools)
                    .display(IMPERIUM_PICKAXE_UPGRADED.get(), title("upgraded_imperium_pickaxe"),
                            description("upgraded_imperium_pickaxe"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("upgraded_imperium_pickaxe", getItem(IMPERIUM_PICKAXE_UPGRADED.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("upgraded_imperium_pickaxe"));

            //speed
            Advancement speed_ingot = Advancement.Builder.advancement()
                    .parent(root)
                    .display(SPEED.get(), title("speed_ingot"),
                            description("speed_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(SPEED.get()))
                    .save(consumer, id("speed_ingot"));

            Advancement speed_block = Advancement.Builder.advancement()
                    .parent(speed_ingot)
                    .display(SPEED_BLOCK.get(), title("speed_block"),
                            description("speed_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(SPEED_BLOCK.get()))
                    .save(consumer, id("speed_block"));

            Advancement xlr8_armour = Advancement.Builder.advancement()
                    .parent(speed_ingot)
                    .display(XLR8_CHESTPLATE.get(), title("xlr8_armour"),
                            description("xlr8_armour"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("xlr8_helmet", getItem(XLR8_HELMET.get()))
                    .addCriterion("xlr8_chestplate", getItem(XLR8_CHESTPLATE.get()))
                    .addCriterion("xlr8_leggings", getItem(XLR8_LEGGINGS.get()))
                    .addCriterion("xlr8_boots", getItem(XLR8_BOOTS.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("xlr8_armour"));

            //legendary
            Advancement legendary_ore = Advancement.Builder.advancement()
                    .parent(root)
                    .display(LEGENDARY_ORE.get(), title("legendary_ore"),
                            description("legendary_ore"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ore", getItem(LEGENDARY_ORE.get()))
                    .save(consumer, id("legendary_ore"));

            Advancement legendary_ingot = Advancement.Builder.advancement()
                    .parent(legendary_ore)
                    .display(LEGENDARY.get(), title("legendary_ingot"),
                            description("legendary_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(LEGENDARY.get()))
                    .save(consumer, id("legendary_ingot"));

            Advancement legendary_block = Advancement.Builder.advancement()
                    .parent(legendary_ingot)
                    .display(LEGENDARY_BLOCK.get(), title("legendary_block"),
                            description("legendary_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(LEGENDARY_BLOCK.get()))
                    .save(consumer, id("legendary_block"));

            Advancement ascalon = Advancement.Builder.advancement()
                    .parent(legendary_ingot)
                    .display(SWORD.get(), title("ascalon"),
                            description("ascalon"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("ascalon", getItem(SWORD.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("ascalon"));

            Advancement knight_armour = Advancement.Builder.advancement()
                    .parent(legendary_ingot)
                    .display(HEATBLAST_CHESTPLATE.get(), title("knight_armour"),
                            description("knight_armour"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("knightt_helmet", getItem(HELMET.get()))
                    .addCriterion("knight_chestplate", getItem(CHESTPLATE.get()))
                    .addCriterion("knight_leggings", getItem(LEGGINGS.get()))
                    .addCriterion("knight_boots", getItem(BOOTS.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("knight_armour"));

            //infinitum
            Advancement infinitum_scrap = Advancement.Builder.advancement()
                    .parent(root)
                    .display(INFINITUM_SCRAP.get(), title("infinitum_scrap"),
                            description("infinitum_scrap"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_scrap", getItem(INFINITUM_SCRAP.get()))
                    .save(consumer, id("infinitum_scrap"));

            Advancement infinitum_ingot = Advancement.Builder.advancement()
                    .parent(infinitum_scrap)
                    .display(INFINITUM.get(), title("infinitum_ingot"),
                            description("infinitum_ingot"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_ingot", getItem(INFINITUM.get()))
                    .save(consumer, id("infinitum_ingot"));

            Advancement infinitum_block = Advancement.Builder.advancement()
                    .parent(infinitum_ingot)
                    .display(INFINITUM_BLOCK.get(), title("infinitum_block"),
                            description("infinitum_block"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("get_block", getItem(INFINITUM_BLOCK.get()))
                    .save(consumer, id("infinitum_block"));

            Advancement infinitum_armour = Advancement.Builder.advancement()
                    .parent(infinitum_ingot)
                    .display(INFINITUM_CHESTPLATE.get(), title("infinitum_armour"),
                            description("infinitum_armour"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("infinitum_helmet", getItem(INFINITUM_HELMET.get()))
                    .addCriterion("infinitum_chestplate", getItem(INFINITUM_CHESTPLATE.get()))
                    .addCriterion("infinitum_leggings", getItem(INFINITUM_LEGGINGS.get()))
                    .addCriterion("infinitum_boots", getItem(INFINITUM_BOOTS.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("infinitum_armour"));

            Advancement infinitum_tools = Advancement.Builder.advancement()
                    .parent(infinitum_ingot)
                    .display(INFINITUM_SWORD.get(), title("infinitum_tools"),
                            description("infinitum_tools"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("infinitum_sword", getItem(INFINITUM_SWORD.get()))
                    .addCriterion("infinitum_pickaxe", getItem(INFINITUM_PICKAXE.get()))
                    .addCriterion("infinitum_shovel", getItem(INFINITUM_SHOVEL.get()))
                    .addCriterion("infinitum_axe", getItem(INFINITUM_AXE.get()))
                    .addCriterion("infinitum_hoe", getItem(INFINITUM_HOE.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("infinitum_tools"));
        }
        private static Advancement simpleGetItem(Consumer<Advancement> consumer, IItemProvider item, Advancement parent) {
            return simpleGetItem(consumer, item, parent, NameUtils.fromItem(item).getPath());
        }

        private static Advancement simpleGetItem(Consumer<Advancement> consumer, IItemProvider item, Advancement parent, String key) {
            return simpleGetItem(consumer, item, new ItemStack(item), parent, key);
        }

        private static Advancement simpleGetItem(Consumer<Advancement> consumer, IItemProvider item, ItemStack icon, Advancement parent, String key) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(icon, title(key), description(key), null, FrameType.TASK, true, true, false)
                    .addCriterion("get_item", getItem(item))
                    .save(consumer, id(key));
        }
        private static String id(String path) {
            return BenTenMod.getId(path).toString();
        }

        private static ICriterionInstance getItem(IItemProvider... items) {
            return InventoryChangeTrigger.Instance.hasItems(items);
        }

        private static ICriterionInstance getItem(ITag<Item> tag) {
            return InventoryChangeTrigger.Instance.hasItems(new ItemPredicate(tag, null, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NBTPredicate.ANY));
        }

        private static ICriterionInstance genericInt(ResourceLocation id, int value) {
            return GenericIntTrigger.Instance.instance(id, value);
        }

        private static ITextComponent title(String key) {
            return new TranslationTextComponent("advancements.bentenmod." + key + ".title");
        }

        private static ITextComponent description(String key) {
            return new TranslationTextComponent("advancements.bentenmod." + key + ".description");
        }
    }
}