package io.github.realyusufismail.bentenmod.core.init.recipe;

import com.google.common.collect.ImmutableList;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.init.RecipeBookTypeInit;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public enum BenTenRecipeBookCategories implements net.minecraftforge.common.IExtensibleEnum {
    OMNITRIX_SEARCH(new ItemStack(Items.COMPASS)),
    OMNITRIX_EQUIPMENT(new ItemStack(ItemInit.IMPERIUM_PICKAXE_UPGRADED.get()),
            new ItemStack(ItemInit.BLACK_DIAMOND_AXE.get())),
    UNKNOWN(new ItemStack(Items.BARRIER));

    public static final List<BenTenRecipeBookCategories> CRAFTING_CATEGORIES =
            ImmutableList.of(OMNITRIX_SEARCH, OMNITRIX_SEARCH);

    private final List<ItemStack> itemIcons;

    private BenTenRecipeBookCategories(ItemStack... pItemIcons) {
        this.itemIcons = ImmutableList.copyOf(pItemIcons);
    }

    public static List<BenTenRecipeBookCategories> getCategories(
            RecipeBookTypeInit pRecipeBookType) {
        if (pRecipeBookType == RecipeBookTypeInit.OMNITRIX_CRAFTING) {
            return CRAFTING_CATEGORIES;
        } else {
            // return
            // net.minecraftforge.client.RecipeBookManager.getCustomCategoriesOrEmpty(pRecipeBookType);
        }
        return ImmutableList.of();
    }

    public List<ItemStack> getIconItems() {
        return this.itemIcons;
    }

    public static BenTenRecipeBookCategories create(String name, ItemStack... icons) {
        throw new IllegalStateException("Enum not extended");
    }
}
