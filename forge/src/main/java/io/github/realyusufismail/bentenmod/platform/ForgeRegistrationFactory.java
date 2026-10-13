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
