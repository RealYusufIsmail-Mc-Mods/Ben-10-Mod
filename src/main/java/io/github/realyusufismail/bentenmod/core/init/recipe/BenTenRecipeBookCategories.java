package io.github.realyusufismail.bentenmod.core.init.recipe;

import com.google.common.collect.ImmutableList;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BenTenRecipeBookCategories implements net.minecraftforge.common.IExtensibleEnum {
    public static final RecipeBookCategories OMNITRIX_SEARCH =
            RecipeBookCategories.create("OmnitrixSearch", new ItemStack(Items.COMPASS));
    public static final RecipeBookCategories OMNITRIX_EQUIPMENT = RecipeBookCategories.create(
            "OmnitrixEquipment", new ItemStack(ItemInit.IMPERIUM_PICKAXE_UPGRADED.get()),
            new ItemStack(ItemInit.BLACK_DIAMOND_AXE.get()));
}
