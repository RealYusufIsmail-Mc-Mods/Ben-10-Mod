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
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/** Backs {@link RegistrationProvider} with a Forge {@link DeferredRegister}. */
public class ForgeRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey,
            String modId) {
        var container = ModList.get()
            .getModContainerById(modId)
            .orElseThrow(() -> new IllegalStateException("Mod " + modId + " is not loaded"));
        if (!(container instanceof FMLModContainer fmlContainer)) {
            throw new IllegalStateException("Mod " + modId + " is not a Java FML mod");
        }
        DeferredRegister<T> register = DeferredRegister.create(registryKey, modId);
        register.register(fmlContainer.getEventBus());
        return new Provider<>(modId, register);
    }

    private static final class Provider<T> implements RegistrationProvider<T> {
        private final String modId;
        private final DeferredRegister<T> register;
        private final List<RegistryObject<T>> entries = new ArrayList<>();
        private final Collection<RegistryObject<T>> entriesView =
                Collections.unmodifiableList(entries);

        private Provider(String modId, DeferredRegister<T> register) {
            this.modId = modId;
            this.register = register;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <I extends T> RegistryObject<I> register(String name,
                Supplier<? extends I> supplier) {
            var forgeObject = register.<I>register(name, supplier);
            RegistryObject<I> object = new RegistryObject<>() {
                @Override
                public ResourceKey<I> getResourceKey() {
                    return (ResourceKey<I>) forgeObject.getKey();
                }

                @Override
                public I get() {
                    return forgeObject.get();
                }

                @Override
                public Holder<I> asHolder() {
                    return (Holder<I>) forgeObject.getHolder().orElseThrow();
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
