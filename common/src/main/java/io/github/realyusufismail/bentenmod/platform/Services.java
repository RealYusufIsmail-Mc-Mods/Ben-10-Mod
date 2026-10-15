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

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

/**
 * Loads the loader-specific implementation of each platform service. Each loader module lists its
 * implementation in {@code META-INF/services/<interface name>}.
 */
public final class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    private Services() {}

    public static <T> T load(Class<T> clazz) {
        final T service = ServiceLoader.load(clazz, Services.class.getClassLoader())
            .findFirst()
            .orElseThrow(() -> new NullPointerException(
                    "Failed to load service for " + clazz.getName()));
        BenTenMod.LOGGER.debug("Loaded {} for service {}", service, clazz);
        return service;
    }
}
