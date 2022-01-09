/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.data.client;


import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.realyusufismailcore.util.NameUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.CRAB_SPAWN_EGG;
import static io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.VILGAX_SPAWN_EGG;
import static io.github.realyusufismail.bentenmod.core.init.ItemInit.*;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        block("black_diamond_block");
        block("black_diamond_ore");
        block("infinitum_ore");
        block("fire_block");
        block("fire_ore");
        block("legendary_block");
        block("omnitrix_block");
        block("speed_block");
        block("ruby_block");
        block("ruby_ore");
        block("infinitum_block");
        block("speed_ore");
        block("omnitrix_ore");
        block("imperium_ore");
        block("legendary_ore");
        block("imperium_block");
        block("deepslate_legendary_ore");
        block("deepslate_ruby_ore");
        block("deepslate_black_diamond_ore");
        block("deepslate_imperium_ore");
        block("deepslate_speed_ore");
        block("deepslate_omnitrix_ore");
        block("deepslate_omnitrix_ore");

        ModelFile itemGenerated = getExistingFile(new ResourceLocation("item/generated"));
        ModelFile itemHandHeld = getExistingFile(new ResourceLocation("item/handheld"));


        // items
        builder(BLACK_DIAMOND.get(), itemGenerated);
        builder(BLACK_DIAMOND_SCRAP.get(), itemGenerated);
        builder(INFINITUM.get(), itemGenerated);
        builder(INFINITUM_SCRAP.get(), itemGenerated);
        builder(BOOTS.get(), itemGenerated);
        builder(CHESTPLATE.get(), itemGenerated);
        builder(LEGENDARY.get(), itemGenerated);
        builder(OMNITRIX.get(), itemGenerated);
        builder(FIRE.get(), itemGenerated);
        builder(RUBY.get(), itemGenerated);
        builder(SPEED.get(), itemGenerated);
        builder(HELMET.get(), itemGenerated);
        builder(LEGGINGS.get(), itemGenerated);
        builder(JACKET.get(), itemGenerated);
        builder(HEATBLAST_HELMET.get(), itemGenerated);
        builder(HEATBLAST_CHESTPLATE.get(), itemGenerated);
        builder(HEATBLAST_LEGGINGS.get(), itemGenerated);
        builder(HEATBLAST_BOOTS.get(), itemGenerated);
        // xlr8 armour
        builder(XLR8_HELMET.get(), itemGenerated);
        builder(XLR8_CHESTPLATE.get(), itemGenerated);
        builder(XLR8_LEGGINGS.get(), itemGenerated);
        builder(XLR8_BOOTS.get(), itemGenerated);
        builder(OMNITRIX_APPLE.get(), itemGenerated);
        builder(ENCHANTED_OMNITRIX_APPLE.get(), itemGenerated);
        builder(FIRE_APPLE.get(), itemGenerated);
        builder(RUBY_APPLE.get(), itemGenerated);
        builder(VILGAX_SPAWN_EGG.get(), itemGenerated);
        // black diamond armour
        builder(BLACK_DIAMOND_HELMET.get(), itemGenerated);
        builder(BLACK_DIAMOND_CHESTPLATE.get(), itemGenerated);
        builder(BLACK_DIAMOND_LEGGINGS.get(), itemGenerated);
        builder(BLACK_DIAMOND_BOOTS.get(), itemGenerated);
        // infinitum armour
        builder(INFINITUM_HELMET.get(), itemGenerated);
        builder(INFINITUM_CHESTPLATE.get(), itemGenerated);
        builder(INFINITUM_LEGGINGS.get(), itemGenerated);
        builder(INFINITUM_BOOTS.get(), itemGenerated);
        builder(IMPERIUM.get(), itemGenerated);
        builder(CRAB_SPAWN_EGG.get(), itemGenerated);
        builder(RAW_LEGENDARY.get(), itemGenerated);
        builder(RAW_IMPERIUM.get(), itemGenerated);
        builder(RAW_OMNITRIX.get(), itemGenerated);
        // 4 arms
        builder(FOURARMS_HELMET.get(), itemGenerated);
        builder(FOURARMS_CHESTPLATE.get(), itemGenerated);
        builder(FOURARMS_LEGGINGS.get(), itemGenerated);
        builder(FOURARMS_BOOT.get(), itemGenerated);
        // tools
        builder(BLACK_DIAMOND_AXE.get(), itemHandHeld);
        builder(BLACK_DIAMOND_HOE.get(), itemHandHeld);
        builder(BLACK_DIAMOND_PICKAXE.get(), itemHandHeld);
        builder(BLACK_DIAMOND_SHOVEL.get(), itemHandHeld);
        builder(BLACK_DIAMOND_SWORD.get(), itemHandHeld);
        builder(INFINITUM_AXE.get(), itemHandHeld);
        builder(INFINITUM_HOE.get(), itemHandHeld);
        builder(INFINITUM_PICKAXE.get(), itemHandHeld);
        builder(INFINITUM_SHOVEL.get(), itemHandHeld);
        builder(INFINITUM_SWORD.get(), itemHandHeld);
        builder(AXE.get(), itemHandHeld);
        builder(SWORD.get(), itemHandHeld);
        builder(HEATBLAST_SWORD.get(), itemHandHeld);
        builder(IMPERIUM_AXE.get(), itemHandHeld);
        builder(IMPERIUM_PICKAXE.get(), itemHandHeld);
        builder(IMPERIUM_PICKAXE_UPGRADED.get(), itemHandHeld);
        builder(IMPERIUM_AXE.get(), itemHandHeld);
        builder(IMPERIUM_SWORD.get(), itemHandHeld);
    }

    private ItemModelBuilder builder(ItemLike item) {
        return getBuilder(NameUtils.fromItem(item).getPath());
    }

    private ItemModelBuilder builder(ItemLike item, ModelFile parent) {
        String name = NameUtils.fromItem(item).getPath();
        return builder(item, parent, "item/" + name);
    }

    private ItemModelBuilder builder(ItemLike item, ModelFile parent, String texture) {
        return getBuilder(NameUtils.fromItem(item).getPath()).parent(parent)
            .texture("layer0", texture);
    }

    private void block(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }
}


