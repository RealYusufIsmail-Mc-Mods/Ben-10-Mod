package io.github.realyusufismail.bentenmod.core.init.recipe;

import io.github.realyusufismail.bentenmod.core.init.recipe.book.BenTenRecipeBookComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface BenTenRecipeUpdateListener {
    void recipesUpdated();

    BenTenRecipeBookComponent getRecipeBookComponent();
}
