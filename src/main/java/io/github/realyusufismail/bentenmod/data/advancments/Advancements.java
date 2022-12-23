package io.github.realyusufismail.bentenmod.data.advancments;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.realyusufismailcore.data.support.advancements.GenericIntTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static io.github.realyusufismail.bentenmod.core.init.ItemInit.*;
import static io.github.realyusufismail.bentenmod.core.init.ItemInit.INFINITUM_HOE;
import static io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore.COPPER_BLOCK;
import static io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore.COPPER;

public class Advancements implements ForgeAdvancementProvider.AdvancementGenerator {
    private static @NotNull Advancement simpleGetItem(Consumer<Advancement> consumer, ItemLike item,
            Advancement parent) {
        return simpleGetItem(consumer, item, parent, item.asItem().toString());
    }

    private static @NotNull Advancement simpleGetItem(Consumer<Advancement> consumer, ItemLike item,
            Advancement parent, String key) {
        return simpleGetItem(consumer, item, new ItemStack(item), parent, key);
    }

    private static @NotNull Advancement simpleGetItem(Consumer<Advancement> consumer, ItemLike item,
            ItemStack icon, Advancement parent, String key) {
        return Advancement.Builder.advancement()
            .parent(parent)
            .display(icon, title(key), description(key), null, FrameType.TASK, true, true, false)
            .addCriterion("get_item", getItem(item))
            .save(consumer, id(key));
    }

    private static @NotNull String id(String path) {
        return BenTenMod.getId(path).toString();
    }

    private static @NotNull CriterionTriggerInstance getItem(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    private static @NotNull CriterionTriggerInstance getItem(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance
            .hasItems(new ItemPredicate(tag, null, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                    EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
    }

    private static @NotNull CriterionTriggerInstance genericInt(ResourceLocation id, int value) {
        return GenericIntTrigger.Instance.instance(id, value);
    }

    private static @NotNull Component title(String key) {
        return Component.translatable("advancements.bentenmod." + key + ".title");
    }

    private static @NotNull Component description(String key) {
        return Component.translatable("advancements.bentenmod." + key + ".description");
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer,
            ExistingFileHelper existingFileHelper) {
        ItemStack rootIcon = new ItemStack(OMNITRIX.get());
        Advancement root = Advancement.Builder.advancement()
            .display(rootIcon, title("root"), description("root"),
                    new ResourceLocation("minecraft:textures/block/light_blue_wool.png"),
                    FrameType.TASK, true, false, false)
            .addCriterion("get_item", getItem(Items.CRAFTING_TABLE))
            .save(consumer, id("root"));
        // black diamond
        Advancement black_diamond_scrap = Advancement.Builder.advancement()
            .parent(root)
            .display(BLACK_DIAMOND_SCRAP.get(), title("black_diamond_scrap"),
                    description("black_diamond_scrap"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_scrap", getItem(BLACK_DIAMOND_SCRAP.get()))
            .save(consumer, id("black_diamond_scrap"));

        Advancement black_diamond_ingot = Advancement.Builder.advancement()
            .parent(black_diamond_scrap)
            .display(BLACK_DIAMOND.get(), title("black_diamond_ingot"),
                    description("black_diamond_ingot"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_scrap", getItem(BLACK_DIAMOND.get()))
            .save(consumer, id("black_diamond_ingot"));

        Advancement black_diamond_block = Advancement.Builder.advancement()
            .parent(black_diamond_scrap)
            .display(BlockInit.BLACK_DIAMOND_BLOCK.get(), title("black_diamond_block"),
                    description("black_diamond_block"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.BLACK_DIAMOND_BLOCK.get()))
            .save(consumer, id("black_diamond_block"));

        Advancement black_diamond_armour = Advancement.Builder.advancement()
            .parent(black_diamond_ingot)
            .display(BLACK_DIAMOND_CHESTPLATE.get(), title("black_diamond_armour"),
                    description("black_diamond_armour"), null, FrameType.GOAL, true, true, false)
            .addCriterion("black_diamond_helmet", getItem(BLACK_DIAMOND_HELMET.get()))
            .addCriterion("black_diamond_chestplate", getItem(BLACK_DIAMOND_CHESTPLATE.get()))
            .addCriterion("black_diamond_leggings", getItem(BLACK_DIAMOND_LEGGINGS.get()))
            .addCriterion("black_diamond_boots", getItem(BLACK_DIAMOND_BOOTS.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("black_diamond_armour"));

        Advancement black_diamond_tools = Advancement.Builder.advancement()
            .parent(black_diamond_ingot)
            .display(BLACK_DIAMOND_SWORD.get(), title("black_diamond_tools"),
                    description("black_diamond_tools"), null, FrameType.GOAL, true, true, false)
            .addCriterion("black_diamond_sword", getItem(BLACK_DIAMOND_SWORD.get()))
            .addCriterion("black_diamond_pickaxe", getItem(BLACK_DIAMOND_PICKAXE.get()))
            .addCriterion("black_diamond_shovel", getItem(BLACK_DIAMOND_SHOVEL.get()))
            .addCriterion("black_diamond_axe", getItem(BLACK_DIAMOND_AXE.get()))
            .addCriterion("black_diamond_hoe", getItem(BLACK_DIAMOND_HOE.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("black_diamond_tools"));

        // copper
        Advancement copper_ingot = Advancement.Builder.advancement()
            .parent(root)
            .display(COPPER.get(), title("copper_ingot"), description("copper_ingot"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(COPPER.get()))
            .save(consumer, id("copper_ingot"));

        Advancement copper_block = Advancement.Builder.advancement()
            .parent(copper_ingot)
            .display(COPPER_BLOCK.get(), title("copper_block"), description("copper_block"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(COPPER_BLOCK.get()))
            .save(consumer, id("copper_block"));

        // fire
        Advancement fire_ore = Advancement.Builder.advancement()
            .parent(root)
            .display(BlockInit.FIRE_ORE.get(), title("fire_ore"), description("fire_ore"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ore", getItem(BlockInit.FIRE_ORE.get()))
            .save(consumer, id("fire_ore"));

        Advancement fire_ingot = Advancement.Builder.advancement()
            .parent(fire_ore)
            .display(FIRE.get(), title("fire_ingot"), description("fire_ingot"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(FIRE.get()))
            .save(consumer, id("fire_ingot"));

        Advancement fire_apple = simpleGetItem(consumer, FIRE_APPLE.get(), fire_ingot);

        Advancement fire_block = Advancement.Builder.advancement()
            .parent(fire_ingot)
            .display(BlockInit.FIRE_BLOCK.get(), title("fire_block"), description("fire_block"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.FIRE_BLOCK.get()))
            .save(consumer, id("fire_block"));


        Advancement heatblast_armour = Advancement.Builder.advancement()
            .parent(fire_ingot)
            .display(HEATBLAST_CHESTPLATE.get(), title("heatblast_armour"),
                    description("heatblast_armour"), null, FrameType.GOAL, true, true, false)
            .addCriterion("heatblast_helmet", getItem(HEATBLAST_HELMET.get()))
            .addCriterion("heatblast_chestplate", getItem(HEATBLAST_CHESTPLATE.get()))
            .addCriterion("heatblast_leggings", getItem(HEATBLAST_LEGGINGS.get()))
            .addCriterion("heatblast_boots", getItem(HEATBLAST_BOOTS.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("heatblast_armour"));

        Advancement heatblast_sword = Advancement.Builder.advancement()
            .parent(fire_ingot)
            .display(HEATBLAST_SWORD.get(), title("heatblast_sword"),
                    description("heatblast_sword"), null, FrameType.GOAL, true, true, false)
            .addCriterion("heatblast_sword", getItem(HEATBLAST_SWORD.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("heatblast_sword"));

        // omnitrix
        Advancement omnitrix_ore = Advancement.Builder.advancement()
            .parent(root)
            .display(RAW_OMNITRIX.get(), title("omnitrix_ore"), description("omnitrix_ore"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ore", getItem(RAW_OMNITRIX.get()))
            .save(consumer, id("omnitrix_ore"));

        Advancement omnitrix_ingot = Advancement.Builder.advancement()
            .parent(omnitrix_ore)
            .display(OMNITRIX.get(), title("omnitrix_ingot"), description("omnitrix_ingot"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(OMNITRIX.get()))
            .save(consumer, id("omnitrix_ingot"));

        Advancement omnitrix_apple = simpleGetItem(consumer, OMNITRIX_APPLE.get(), omnitrix_ingot);
        Advancement enchanted_omnitrix_apple =
                simpleGetItem(consumer, ENCHANTED_OMNITRIX_APPLE.get(), omnitrix_ingot);

        Advancement omnitrix_block = Advancement.Builder.advancement()
            .parent(omnitrix_ingot)
            .display(BlockInit.OMNITRIX_BLOCK.get(), title("omnitrix_block"),
                    description("omnitrix_block"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.OMNITRIX_BLOCK.get()))
            .save(consumer, id("omnitrix_block"));


        // ruby
        Advancement ruby_ore = Advancement.Builder.advancement()
            .parent(root)
            .display(BlockInit.RUBY_ORE.get(), title("ruby_ore"), description("ruby_ore"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ore", getItem(BlockInit.RUBY_ORE.get()))
            .save(consumer, id("ruby_ore"));

        Advancement ruby_ingot = Advancement.Builder.advancement()
            .parent(ruby_ore)
            .display(RUBY.get(), title("ruby_ingot"), description("ruby_ingot"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(RUBY.get()))
            .save(consumer, id("ruby_ingot"));

        Advancement ruby_apple = simpleGetItem(consumer, RUBY_APPLE.get(), ruby_ingot);

        Advancement ruby_block = Advancement.Builder.advancement()
            .parent(ruby_ingot)
            .display(BlockInit.RUBY_BLOCK.get(), title("ruby_block"), description("ruby_block"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.RUBY_BLOCK.get()))
            .save(consumer, id("ruby_block"));

        Advancement ruby_axe = Advancement.Builder.advancement()
            .parent(ruby_ingot)
            .display(AXE.get(), title("ruby_axe"), description("ruby_axe"), null, FrameType.GOAL,
                    true, true, false)
            .addCriterion("axe", getItem(AXE.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("ruby_axe"));

        // imperium
        Advancement imperium_ore = Advancement.Builder.advancement()
            .parent(root)
            .display(RAW_IMPERIUM.get(), title("imperium_ore"), description("imperium_ore"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ore", getItem(RAW_IMPERIUM.get()))
            .save(consumer, id("imperium_ore"));

        Advancement imperium_ingot = Advancement.Builder.advancement()
            .parent(imperium_ore)
            .display(IMPERIUM.get(), title("imperium_ingot"), description("imperium_ingot"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(IMPERIUM.get()))
            .save(consumer, id("imperium_ingot"));

        Advancement imperium_block = Advancement.Builder.advancement()
            .parent(imperium_ingot)
            .display(BlockInit.IMPERIUM_BLOCK.get(), title("imperium_block"),
                    description("imperium_block"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.IMPERIUM_BLOCK.get()))
            .save(consumer, id("imperium_block"));

        Advancement imperium_tools = Advancement.Builder.advancement()
            .parent(imperium_ingot)
            .display(IMPERIUM_SWORD.get(), title("imperium_tools"), description("imperium_tools"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("imperium_pickaxe", getItem(IMPERIUM_PICKAXE.get()))
            .addCriterion("imperium_sword", getItem(IMPERIUM_SWORD.get()))
            .addCriterion("imperium_axe", getItem(IMPERIUM_AXE.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("imperium_tools"));

        Advancement upgraded_imperium_pickaxe = Advancement.Builder.advancement()
            .parent(imperium_tools)
            .display(IMPERIUM_PICKAXE_UPGRADED.get(), title("upgraded_imperium_pickaxe"),
                    description("upgraded_imperium_pickaxe"), null, FrameType.GOAL, true, true,
                    false)
            .addCriterion("upgraded_imperium_pickaxe", getItem(IMPERIUM_PICKAXE_UPGRADED.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("upgraded_imperium_pickaxe"));

        // speed
        Advancement speed_ingot = Advancement.Builder.advancement()
            .parent(root)
            .display(SPEED.get(), title("speed_ingot"), description("speed_ingot"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(SPEED.get()))
            .save(consumer, id("speed_ingot"));

        Advancement speed_block = Advancement.Builder.advancement()
            .parent(speed_ingot)
            .display(BlockInit.SPEED_BLOCK.get(), title("speed_block"), description("speed_block"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.SPEED_BLOCK.get()))
            .save(consumer, id("speed_block"));

        Advancement xlr8_armour = Advancement.Builder.advancement()
            .parent(speed_ingot)
            .display(XLR8_CHESTPLATE.get(), title("xlr8_armour"), description("xlr8_armour"), null,
                    FrameType.GOAL, true, true, false)
            .addCriterion("xlr8_helmet", getItem(XLR8_HELMET.get()))
            .addCriterion("xlr8_chestplate", getItem(XLR8_CHESTPLATE.get()))
            .addCriterion("xlr8_leggings", getItem(XLR8_LEGGINGS.get()))
            .addCriterion("xlr8_boots", getItem(XLR8_BOOTS.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("xlr8_armour"));

        // legendary
        Advancement legendary_ore = Advancement.Builder.advancement()
            .parent(root)
            .display(RAW_LEGENDARY.get(), title("legendary_ore"), description("legendary_ore"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("get_ore", getItem(RAW_LEGENDARY.get()))
            .save(consumer, id("legendary_ore"));

        Advancement legendary_ingot = Advancement.Builder.advancement()
            .parent(legendary_ore)
            .display(LEGENDARY.get(), title("legendary_ingot"), description("legendary_ingot"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(LEGENDARY.get()))
            .save(consumer, id("legendary_ingot"));

        Advancement legendary_block = Advancement.Builder.advancement()
            .parent(legendary_ingot)
            .display(BlockInit.LEGENDARY_BLOCK.get(), title("legendary_block"),
                    description("legendary_block"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.LEGENDARY_BLOCK.get()))
            .save(consumer, id("legendary_block"));

        Advancement ascalon = Advancement.Builder.advancement()
            .parent(legendary_ingot)
            .display(SWORD.get(), title("ascalon"), description("ascalon"), null, FrameType.GOAL,
                    true, true, false)
            .addCriterion("ascalon", getItem(SWORD.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("ascalon"));

        Advancement knight_armour = Advancement.Builder.advancement()
            .parent(legendary_ingot)
            .display(HEATBLAST_CHESTPLATE.get(), title("knight_armour"),
                    description("knight_armour"), null, FrameType.GOAL, true, true, false)
            .addCriterion("knightt_helmet", getItem(HELMET.get()))
            .addCriterion("knight_chestplate", getItem(CHESTPLATE.get()))
            .addCriterion("knight_leggings", getItem(LEGGINGS.get()))
            .addCriterion("knight_boots", getItem(BOOTS.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("knight_armour"));

        // infinitum
        Advancement infinitum_scrap = Advancement.Builder.advancement()
            .parent(root)
            .display(INFINITUM_SCRAP.get(), title("infinitum_scrap"),
                    description("infinitum_scrap"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_scrap", getItem(INFINITUM_SCRAP.get()))
            .save(consumer, id("infinitum_scrap"));

        Advancement infinitum_ingot = Advancement.Builder.advancement()
            .parent(infinitum_scrap)
            .display(INFINITUM.get(), title("infinitum_ingot"), description("infinitum_ingot"),
                    null, FrameType.GOAL, true, true, false)
            .addCriterion("get_ingot", getItem(INFINITUM.get()))
            .save(consumer, id("infinitum_ingot"));

        Advancement infinitum_block = Advancement.Builder.advancement()
            .parent(infinitum_ingot)
            .display(BlockInit.INFINITUM_BLOCK.get(), title("infinitum_block"),
                    description("infinitum_block"), null, FrameType.GOAL, true, true, false)
            .addCriterion("get_block", getItem(BlockInit.INFINITUM_BLOCK.get()))
            .save(consumer, id("infinitum_block"));

        Advancement infinitum_armour = Advancement.Builder.advancement()
            .parent(infinitum_ingot)
            .display(INFINITUM_CHESTPLATE.get(), title("infinitum_armour"),
                    description("infinitum_armour"), null, FrameType.GOAL, true, true, false)
            .addCriterion("infinitum_helmet", getItem(INFINITUM_HELMET.get()))
            .addCriterion("infinitum_chestplate", getItem(INFINITUM_CHESTPLATE.get()))
            .addCriterion("infinitum_leggings", getItem(INFINITUM_LEGGINGS.get()))
            .addCriterion("infinitum_boots", getItem(INFINITUM_BOOTS.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("infinitum_armour"));

        Advancement infinitum_tools = Advancement.Builder.advancement()
            .parent(infinitum_ingot)
            .display(INFINITUM_SWORD.get(), title("infinitum_tools"),
                    description("infinitum_tools"), null, FrameType.GOAL, true, true, false)
            .addCriterion("infinitum_sword", getItem(INFINITUM_SWORD.get()))
            .addCriterion("infinitum_pickaxe", getItem(INFINITUM_PICKAXE.get()))
            .addCriterion("infinitum_shovel", getItem(INFINITUM_SHOVEL.get()))
            .addCriterion("infinitum_axe", getItem(INFINITUM_AXE.get()))
            .addCriterion("infinitum_hoe", getItem(INFINITUM_HOE.get()))
            .requirements(RequirementsStrategy.OR)
            .save(consumer, id("infinitum_tools"));
    }
}
