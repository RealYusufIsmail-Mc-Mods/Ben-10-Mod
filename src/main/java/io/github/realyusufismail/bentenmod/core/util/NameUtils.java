/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.bentenmod.core.util;

import com.google.common.base.Preconditions;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class NameUtils {

    private NameUtils() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Verify name is not null, throwing an exception if it is.
     *
     * @param name Possibly null ResourceLocation
     * @return name
     * @throws NullPointerException if name is null
     */
    public static ResourceLocation checkNotNull(ResourceLocation name) {
        Preconditions.checkNotNull(name, "Name is null, make sure the object has been registered correctly");
        return name;
    }

    /**
     * Gets the item's registry name, throwing an exception if it is null
     *
     * @param item The item
     * @return The registry name
     * @throws NullPointerException if registry name is null
     */
    public static ResourceLocation fromItem(IItemProvider item) {
        Preconditions.checkNotNull(item.asItem(), "asItem() is null, has object not been fully constructed?");
        return checkNotNull(item.asItem().getRegistryName());
    }
}
