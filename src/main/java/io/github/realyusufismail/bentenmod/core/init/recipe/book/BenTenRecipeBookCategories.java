package io.github.realyusufismail.bentenmod.core.init.recipe.book;

import com.google.common.collect.ImmutableList;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

/**
 * @see net.minecraft.client.RecipeBookCategories
 */
@OnlyIn(Dist.CLIENT)
public enum BenTenRecipeBookCategories implements net.minecraftforge.common.IExtensibleEnum {
    OMNITRIX_SEARCH(new ItemStack(Items.COMPASS)),
    OMNITRIX_TOOLS(new ItemStack(ItemInit.IMPERIUM_PICKAXE_UPGRADED.get()),
            new ItemStack(ItemInit.BLACK_DIAMOND_AXE.get())),
    OMNITRIX_EQUIPMENT(new ItemStack(ItemInit.INFINITUM_CHESTPLATE.get())),
    UNKNOWN(new ItemStack(Items.BARRIER));

    public static final List<BenTenRecipeBookCategories> OMNITRIX_CATEGORIES =
            ImmutableList.of(OMNITRIX_SEARCH, OMNITRIX_TOOLS, OMNITRIX_EQUIPMENT);

    private final List<ItemStack> itemIcons;
    public static final Map<BenTenRecipeBookCategories, List<BenTenRecipeBookCategories>> AGGREGATE_CATEGORIES =
            BenTenRecipeBookManager.getAggregateCategories();

    BenTenRecipeBookCategories(ItemStack... pItemIcons) {
        this.itemIcons = ImmutableList.copyOf(pItemIcons);
    }

    public static List<BenTenRecipeBookCategories> getCategories(
            BenTenRecipeBookType pRecipeBookType) {
        switch (pRecipeBookType) {
            case OMNITRIX_CRAFTING:
                return OMNITRIX_CATEGORIES;
            default:
                return BenTenRecipeBookManager.getCustomCategoriesOrEmpty(pRecipeBookType);
        }
    }

    public List<ItemStack> getIconItems() {
        return this.itemIcons;
    }
}
