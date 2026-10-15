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
package io.github.realyusufismail.bentenmod.platform.registry;

import io.github.realyusufismail.bentenmod.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Loader-neutral registration. Forge backs it with a {@code DeferredRegister}; Fabric registers
 * straight into the vanilla registry.
 *
 * <pre>
 * {@code
 * public static final RegistrationProvider<Item> ITEMS =
 *         RegistrationProvider.get(Registries.ITEM, BenTenMod.MOD_ID);
 * public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(...));
 * }
 * </pre>
 */
public interface RegistrationProvider<T> {

    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> registryKey,
            String modId) {
        return Factory.INSTANCE.create(registryKey, modId);
    }

    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);

    /** Every entry registered through this provider, in registration order. */
    Collection<RegistryObject<T>> getEntries();

    String getModId();

    /** Implemented once per loader and found with {@link java.util.ServiceLoader}. */
    interface Factory {
        Factory INSTANCE = Services.load(Factory.class);

        <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey,
                String modId);
    }
}
