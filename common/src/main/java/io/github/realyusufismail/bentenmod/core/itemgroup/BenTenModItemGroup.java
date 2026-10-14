/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.core.itemgroup;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import io.github.realyusufismail.bentenmod.platform.Services;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;

public class BenTenModItemGroup {

    public static final RegistrationProvider<CreativeModeTab> TABS =
            RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, BenTenMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BENTEN_TAB = TABS.register("creativetab",
            () -> createCreativeTab(Services.PLATFORM.creativeTabBuilder()));

    private static CreativeModeTab createCreativeTab(CreativeModeTab.Builder builder) {
        // Block items are registered in ItemInit.ITEMS too, so this covers every block. Adding the
        // blocks again would put duplicate stacks in the tab, which Minecraft rejects.
        builder.displayItems((itemDisplayParameters, out) -> ItemInit.ITEMS.getEntries()
            .stream()
            .map(item -> item.get().asItem())
            .forEach(out::accept));
        builder.icon(() -> new ItemStack(ItemInit.OMNITRIX.get()));
        builder.title(Component.translatable("creativetab.bentenmod"));
        return builder.build();
    }

    /** Loads this class so its entries are registered. */
    public static void init() {}
}
