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
package io.github.realyusufismail.bentenmod.platform;

import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/** Registers straight into the vanilla registries, which Fabric leaves open during init. */
public class FabricRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey,
            String modId) {
        Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
        if (registry == null) {
            throw new IllegalArgumentException("Unknown registry " + registryKey.location());
        }
        return new Provider<>(modId, registry);
    }

    private static final class Provider<T> implements RegistrationProvider<T> {
        private final String modId;
        private final Registry<T> registry;
        private final List<RegistryObject<T>> entries = new ArrayList<>();
        private final Collection<RegistryObject<T>> entriesView =
                Collections.unmodifiableList(entries);

        private Provider(String modId, Registry<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <I extends T> RegistryObject<I> register(String name,
                Supplier<? extends I> supplier) {
            ResourceLocation id = new ResourceLocation(modId, name);
            I value = Registry.register(registry, id, supplier.get());
            ResourceKey<I> key = (ResourceKey<I>) ResourceKey.create(registry.key(), id);
            RegistryObject<I> object = new RegistryObject<>() {
                @Override
                public ResourceKey<I> getResourceKey() {
                    return key;
                }

                @Override
                public I get() {
                    return value;
                }

                @Override
                public Holder<I> asHolder() {
                    return (Holder<I>) registry.getHolderOrThrow((ResourceKey<T>) key);
                }
            };
            entries.add((RegistryObject<T>) object);
            return object;
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return entriesView;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }
}
