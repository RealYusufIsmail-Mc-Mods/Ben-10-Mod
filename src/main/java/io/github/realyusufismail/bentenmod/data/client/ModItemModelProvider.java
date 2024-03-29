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
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static io.github.realyusufismail.bentenmod.core.init.EntityTypesInit.CRAB_SPAWN_EGG;
import static io.github.realyusufismail.bentenmod.core.init.ItemInit.*;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), BenTenMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // items
        ITEMS.getEntries().stream().map(RegistryObject::get).forEach(this::registerItems);
    }

    public void registerItems(Item item) {
        String itemAsAsString = item.toString();

        // items
        if (itemAsAsString.equals(BLACK_DIAMOND.get().toString()))
            item(BLACK_DIAMOND.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_SCRAP.get().toString()))
            item(BLACK_DIAMOND_SCRAP.get().toString());
        else if (itemAsAsString.equals(INFINITUM.get().toString()))
            item(INFINITUM.get().toString());
        else if (itemAsAsString.equals(INFINITUM_SCRAP.get().toString()))
            item(INFINITUM_SCRAP.get().toString());
        else if (itemAsAsString.equals(LEGENDARY.get().toString()))
            item(LEGENDARY.get().toString());
        else if (itemAsAsString.equals(OMNITRIX.get().toString()))
            item(OMNITRIX.get().toString());
        else if (itemAsAsString.equals(FIRE.get().toString()))
            item(FIRE.get().toString());
        else if (itemAsAsString.equals(RUBY.get().toString()))
            item(RUBY.get().toString());
        else if (itemAsAsString.equals(SPEED.get().toString()))
            item(SPEED.get().toString());
        else if (itemAsAsString.equals(IMPERIUM.get().toString()))
            item(IMPERIUM.get().toString());
        else if (itemAsAsString.equals(OMNITRIX_APPLE.get().toString()))
            item(OMNITRIX_APPLE.get().toString());
        else if (itemAsAsString.equals(ENCHANTED_OMNITRIX_APPLE.get().toString()))
            item(ENCHANTED_OMNITRIX_APPLE.get().toString());
        else if (itemAsAsString.equals(FIRE_APPLE.get().toString()))
            item(FIRE_APPLE.get().toString());
        else if (itemAsAsString.equals(RUBY_APPLE.get().toString()))
            item(RUBY_APPLE.get().toString());
        else if (itemAsAsString.equals(CRAB_SPAWN_EGG.get().toString()))
            item(CRAB_SPAWN_EGG.get().toString());
        else if (itemAsAsString.equals(RAW_LEGENDARY.get().toString()))
            item(RAW_LEGENDARY.get().toString());
        else if (itemAsAsString.equals(RAW_IMPERIUM.get().toString()))
            item(RAW_IMPERIUM.get().toString());
        else if (itemAsAsString.equals(RAW_OMNITRIX.get().toString()))
            item(RAW_OMNITRIX.get().toString());

        // tools
        else if (itemAsAsString.equals(BLACK_DIAMOND_AXE.get().toString()))
            tool(BLACK_DIAMOND_AXE.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_HOE.get().toString()))
            tool(BLACK_DIAMOND_HOE.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_PICKAXE.get().toString()))
            tool(BLACK_DIAMOND_PICKAXE.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_SHOVEL.get().toString()))
            tool(BLACK_DIAMOND_SHOVEL.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_SWORD.get().toString()))
            tool(BLACK_DIAMOND_SWORD.get().toString());
        else if (itemAsAsString.equals(INFINITUM_AXE.get().toString()))
            tool(INFINITUM_AXE.get().toString());
        else if (itemAsAsString.equals(INFINITUM_HOE.get().toString()))
            tool(INFINITUM_HOE.get().toString());
        else if (itemAsAsString.equals(INFINITUM_PICKAXE.get().toString()))
            tool(INFINITUM_PICKAXE.get().toString());
        else if (itemAsAsString.equals(INFINITUM_SHOVEL.get().toString()))
            tool(INFINITUM_SHOVEL.get().toString());
        else if (itemAsAsString.equals(INFINITUM_SWORD.get().toString()))
            tool(INFINITUM_SWORD.get().toString());
        else if (itemAsAsString.equals(AXE.get().toString()))
            tool(AXE.get().toString());
        else if (itemAsAsString.equals(SWORD.get().toString()))
            tool(SWORD.get().toString());
        else if (itemAsAsString.equals(HEATBLAST_SWORD.get().toString()))
            tool(HEATBLAST_SWORD.get().toString());
        else if (itemAsAsString.equals(IMPERIUM_AXE.get().toString()))
            tool(IMPERIUM_AXE.get().toString());
        else if (itemAsAsString.equals(IMPERIUM_PICKAXE.get().toString()))
            tool(IMPERIUM_PICKAXE.get().toString());
        else if (itemAsAsString.equals(IMPERIUM_PICKAXE_UPGRADED.get().toString()))
            tool(IMPERIUM_PICKAXE_UPGRADED.get().toString());
        else if (itemAsAsString.equals(IMPERIUM_AXE.get().toString()))
            tool(IMPERIUM_AXE.get().toString());
        else if (itemAsAsString.equals(IMPERIUM_SWORD.get().toString()))
            tool(IMPERIUM_SWORD.get().toString());

        // xlr8 armour
        else if (itemAsAsString.equals(XLR8_HELMET.get().toString()))
            item(XLR8_HELMET.get().toString());
        else if (itemAsAsString.equals(XLR8_CHESTPLATE.get().toString()))
            item(XLR8_CHESTPLATE.get().toString());
        else if (itemAsAsString.equals(XLR8_LEGGINGS.get().toString()))
            item(XLR8_LEGGINGS.get().toString());
        else if (itemAsAsString.equals(XLR8_BOOTS.get().toString()))
            item(XLR8_BOOTS.get().toString());

        // black diamond armour
        else if (itemAsAsString.equals(BLACK_DIAMOND_HELMET.get().toString()))
            item(BLACK_DIAMOND_HELMET.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_CHESTPLATE.get().toString()))
            item(BLACK_DIAMOND_CHESTPLATE.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_LEGGINGS.get().toString()))
            item(BLACK_DIAMOND_LEGGINGS.get().toString());
        else if (itemAsAsString.equals(BLACK_DIAMOND_BOOTS.get().toString()))
            item(BLACK_DIAMOND_BOOTS.get().toString());

        // infinitum armour
        else if (itemAsAsString.equals(INFINITUM_HELMET.get().toString()))
            item(INFINITUM_HELMET.get().toString());
        else if (itemAsAsString.equals(INFINITUM_CHESTPLATE.get().toString()))
            item(INFINITUM_CHESTPLATE.get().toString());
        else if (itemAsAsString.equals(INFINITUM_LEGGINGS.get().toString()))
            item(INFINITUM_LEGGINGS.get().toString());
        else if (itemAsAsString.equals(INFINITUM_BOOTS.get().toString()))
            item(INFINITUM_BOOTS.get().toString());

        // 4 arms
        else if (itemAsAsString.equals(FOURARMS_HELMET.get().toString()))
            item(FOURARMS_HELMET.get().toString());
        else if (itemAsAsString.equals("fourarms_chestplate"))
            item("fourarms_chestplate");
        else if (itemAsAsString.equals(FOURARMS_LEGGINGS.get().toString()))
            item(FOURARMS_LEGGINGS.get().toString());
        else if (itemAsAsString.equals(FOURARMS_BOOT.get().toString()))
            item(FOURARMS_BOOT.get().toString());

        // legendary armour
        else if (itemAsAsString.equals(HELMET.get().toString()))
            item(HELMET.get().toString());
        else if (itemAsAsString.equals(CHESTPLATE.get().toString()))
            item(CHESTPLATE.get().toString());
        else if (itemAsAsString.equals(LEGGINGS.get().toString()))
            item(LEGGINGS.get().toString());
        else if (itemAsAsString.equals(BOOTS.get().toString()))
            item(BOOTS.get().toString());

        // heat blast
        else if (itemAsAsString.equals(HEATBLAST_HELMET.get().toString()))
            item(HEATBLAST_HELMET.get().toString());
        else if (itemAsAsString.equals(HEATBLAST_CHESTPLATE.get().toString()))
            item(HEATBLAST_CHESTPLATE.get().toString());
        else if (itemAsAsString.equals(HEATBLAST_LEGGINGS.get().toString()))
            item(HEATBLAST_LEGGINGS.get().toString());
        else if (itemAsAsString.equals(HEATBLAST_BOOTS.get().toString()))
            item(HEATBLAST_BOOTS.get().toString());

        else if (itemAsAsString.equals(JACKET.get().toString()))
            item(JACKET.get().toString());
    }

    private void tool(String name) {
        getBuilder(name).parent(getExistingFile(mcLoc("item/handheld")))
            .texture("layer0", "item/" + name);
    }

    private void item(String name) {
        getBuilder(name).parent(getExistingFile(mcLoc("item/generated")))
            .texture("layer0", "item/" + name);
    }
}


