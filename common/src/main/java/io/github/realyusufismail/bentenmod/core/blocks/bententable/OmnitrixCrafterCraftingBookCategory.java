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
package io.github.realyusufismail.bentenmod.core.blocks.bententable;

import net.minecraft.util.StringRepresentable;

public enum OmnitrixCrafterCraftingBookCategory implements StringRepresentable {
    OMNITRIX("omnitrix"),
    ARMOUR("armour"),
    TOOLS("tools"),
    MISC("misc");

    private final String name;

    OmnitrixCrafterCraftingBookCategory(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static StringRepresentable.EnumCodec<OmnitrixCrafterCraftingBookCategory> codec() {
        return StringRepresentable.fromEnum(OmnitrixCrafterCraftingBookCategory::values);
    }
}

