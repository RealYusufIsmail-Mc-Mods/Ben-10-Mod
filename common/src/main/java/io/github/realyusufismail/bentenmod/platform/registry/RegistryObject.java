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

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * A registered (or soon to be registered) object. Same shape as Forge's {@code RegistryObject} so
 * call sites read the same on both loaders.
 */
public interface RegistryObject<T> extends Supplier<T> {

    ResourceKey<T> getResourceKey();

    default ResourceLocation getId() {
        return getResourceKey().location();
    }

    @Override
    T get();

    Holder<T> asHolder();
}
