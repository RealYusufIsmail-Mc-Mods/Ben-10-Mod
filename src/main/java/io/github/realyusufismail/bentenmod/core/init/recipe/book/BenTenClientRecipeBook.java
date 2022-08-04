package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import com.google.common.collect.*;
import com.mojang.logging.LogUtils;
import io.github.realyusufismail.bentenmod.core.init.RecipeTypeInit;
import io.github.realyusufismail.bentenmod.core.init.recipe.BenTenRecipeCollection;
import io.github.realyusufismail.bentenmod.core.itemgroup.MainItemGroup;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BenTenClientRecipeBook extends RecipeBook {
    private static final Logger LOGGER = LogUtils.getLogger();
    private Map<BenTenRecipeBookCategories, List<BenTenRecipeCollection>> collectionsByTab =
            ImmutableMap.of();
    private List<BenTenRecipeCollection> allCollections = ImmutableList.of();
    private final BenTenRecipeBookSettings settings = new BenTenRecipeBookSettings();

    public void setupCollections(Iterable<Recipe<?>> pRecipes) {
        Map<BenTenRecipeBookCategories, List<List<Recipe<?>>>> map =
                categorizeAndGroupRecipes(pRecipes);
        Map<BenTenRecipeBookCategories, List<BenTenRecipeCollection>> map1 = Maps.newHashMap();
        ImmutableList.Builder<BenTenRecipeCollection> builder = ImmutableList.builder();
        map.forEach((p_90630_, p_90631_) -> {
            map1.put(p_90630_,
                    p_90631_.stream()
                        .map(BenTenRecipeCollection::new)
                        .peek(builder::add)
                        .collect(ImmutableList.toImmutableList()));
        });
        BenTenRecipeBookCategories.AGGREGATE_CATEGORIES.forEach((p_90637_, p_90638_) -> {
            map1.put(p_90637_, p_90638_.stream().flatMap((p_167706_) -> {
                return map1.getOrDefault(p_167706_, ImmutableList.of()).stream();
            }).collect(ImmutableList.toImmutableList()));
        });
        this.collectionsByTab = ImmutableMap.copyOf(map1);
        this.allCollections = builder.build();
    }

    private static Map<BenTenRecipeBookCategories, List<List<Recipe<?>>>> categorizeAndGroupRecipes(
            Iterable<Recipe<?>> pRecipes) {
        Map<BenTenRecipeBookCategories, List<List<Recipe<?>>>> map = Maps.newHashMap();
        Table<BenTenRecipeBookCategories, String, List<Recipe<?>>> table = HashBasedTable.create();

        for (Recipe<?> recipe : pRecipes) {
            if (!recipe.isSpecial() && !recipe.isIncomplete()) {
                BenTenRecipeBookCategories recipebookcategories = getCategory(recipe);
                String s = recipe.getGroup();
                if (s.isEmpty()) {
                    map.computeIfAbsent(recipebookcategories, (p_90645_) -> {
                        return Lists.newArrayList();
                    }).add(ImmutableList.of(recipe));
                } else {
                    List<Recipe<?>> list = table.get(recipebookcategories, s);
                    if (list == null) {
                        list = Lists.newArrayList();
                        table.put(recipebookcategories, s, list);
                        map.computeIfAbsent(recipebookcategories, (p_90641_) -> {
                            return Lists.newArrayList();
                        }).add(list);
                    }

                    list.add(recipe);
                }
            }
        }

        return map;
    }

    private static BenTenRecipeBookCategories getCategory(Recipe<?> pRecipe) {
        RecipeType<?> recipetype = pRecipe.getType();
        if (recipetype == RecipeTypeInit.OMNITRIX_CRAFTER_TYPE.get()) {
            ItemStack itemstack = pRecipe.getResultItem();
            MainItemGroup creativemodetab = (MainItemGroup) itemstack.getItem().getItemCategory();
            if (creativemodetab == MainItemGroup.MAIN) {
                return BenTenRecipeBookCategories.OMNITRIX_EQUIPMENT;
            } else {
                return BenTenRecipeBookCategories.OMNITRIX_TOOLS;
            }
        } else {
            BenTenRecipeBookCategories categories =
                    BenTenRecipeBookManager.findCategories((RecipeType) recipetype, pRecipe);
            if (categories != null)
                return categories;
            LOGGER.warn("Unknown recipe category: {}/{}", LogUtils.defer(() -> {
                return ForgeRegistries.RECIPE_TYPES.getKey(pRecipe.getType());
            }), LogUtils.defer(pRecipe::getId));
            return BenTenRecipeBookCategories.UNKNOWN;
        }
    }

    public List<BenTenRecipeCollection> getCollections() {
        return this.allCollections;
    }

    public List<BenTenRecipeCollection> getCollection(BenTenRecipeBookCategories pCategories) {
        return this.collectionsByTab.getOrDefault(pCategories, Collections.emptyList());
    }

    public static BenTenClientRecipeBook getAsBenTenClientRecipeBook(RecipeBook pRecipeBook) {
        if (pRecipeBook instanceof BenTenClientRecipeBook) {
            return (BenTenClientRecipeBook) pRecipeBook;
        } else {
            return null;
        }
    }

    public boolean isFiltering(BenTenRecipeBookMenu<?> pBookMenu) {
        return this.isFiltering(pBookMenu.getRecipeBookType());
    }

    public boolean isFiltering(BenTenRecipeBookType pBookType) {
        return this.settings.isFiltering(pBookType);
    }

    public void setFiltering(BenTenRecipeBookType pBookType, boolean pFiltering) {
        this.settings.setFiltering(pBookType, pFiltering);
    }

    public boolean isOpen(BenTenRecipeBookType pBookType) {
        return this.settings.isOpen(pBookType);
    }

    public void setOpen(BenTenRecipeBookType pBookType, boolean pOpen) {
        this.settings.setOpen(pBookType, pOpen);
    }

    public BenTenRecipeBookSettings geCustomBookSettings() {
        return this.settings.copy();
    }
}
