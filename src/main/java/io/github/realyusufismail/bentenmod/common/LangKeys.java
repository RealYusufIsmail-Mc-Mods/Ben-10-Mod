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
package io.github.realyusufismail.bentenmod.common;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LangKeys {
    public static final ITextComponent TABLE_SCREEN = text("container", "table_block");
    public static final ITextComponent TABLE_DISC = text("desc", "upgrade_table");
    public static final ITextComponent ALIEN_TABLE_SCREEN = text("container", "alien_table");
    public static final ITextComponent ALIEN_TABLE_DISC = text("desc", "alien_create_table");
    public static final ITextComponent TABLE_JEI = text("jei", "table_block");

    // Method extracted
    private static TranslationTextComponent text(String s, String s2) {
        return new TranslationTextComponent(s + "." + BenTenMod.MOD_ID + "." + s2);
    }
}
