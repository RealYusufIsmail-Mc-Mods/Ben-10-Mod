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
                    .display(rootIcon, title("Download Ben ten mob mod"), description("Thank you for downloading my mod."),
                            new ResourceLocation("minecraft:textures/block/light_blue_wool.png"), FrameType.TASK,
                            true, false, false)
                    .addCriterion("get_item", getItem(Items.CRAFTING_TABLE))
                    .save(consumer, id("root"));
            //black diamond
            Advancement black_diamond_scrap = Advancement.Builder.advancement()
                    .parent(root)
                    .display(BLACK_DIAMOND_SCRAP.get(), title("Black Diamond Scrap"),
                            description("You have got a black diamond scrap. Gain more to able to get an ingot"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .addCriterion("get_scrap", getItem(BLACK_DIAMOND_SCRAP.get()))
                    .save(consumer, id("black_diamond"));

            Advancement black_diamond_ingot = Advancement.Builder.advancement()
                    .parent(black_diamond_scrap)
                    .display(BLACK_DIAMOND_SCRAP.get(), title("Black Diamond"),
                            description("You have made a black diamond ingot. You cant covert diamond armour to black diamond armour using a smiting table"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .addCriterion("get_scrap", getItem(BLACK_DIAMOND.get()))
                    .save(consumer, id("black_diamond_ingot"));

            Advancement armor = Advancement.Builder.advancement()
                    .parent(black_diamond_ingot)
                    .display(BLACK_DIAMOND_HELMET.get(), title("black_diamond_armour"),
                            description("You have crafted all the black diamond armour set. You are op"),
                            null, FrameType.CHALLENGE, true, true, false)
                    .addCriterion("helmet", getItem(BLACK_DIAMOND_HELMET.get()))
                    .addCriterion("chestplate", getItem(BLACK_DIAMOND_CHESTPLATE.get()))
                    .addCriterion("leggings", getItem(BLACK_DIAMOND_LEGGINGS.get()))
                    .addCriterion("boots", getItem(BLACK_DIAMOND_BOOTS.get()))
                    .requirements(IRequirementsStrategy.OR)
                    .save(consumer, id("black_diamond_armour"));

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