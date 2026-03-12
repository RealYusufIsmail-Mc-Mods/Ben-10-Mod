/*
 * Copyright 2026 RealYusufIsmail.
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
package io.github.realyusufismail.bentenmod.core.omnitrix;

import io.github.realyusufismail.bentenmod.BenTenMod;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;

public enum AlienType {
    HEATBLAST("Heatblast", 3600, 600, 0.6f, 1.8f),
    XLR8("XLR8", 3600, 600, 0.6f, 1.8f),
    FOUR_ARMS("Four Arms", 3600, 600, 1.2f, 2.5f),
    DIAMONDHEAD("Diamondhead", 3600, 600, 0.6f, 1.8f),
    GREY_MATTER("Grey Matter", 3600, 600, 0.3f, 0.5f),
    STINKFLY("Stinkfly", 3600, 600, 0.6f, 1.8f),
    RIPJAWS("Ripjaws", 3600, 600, 0.6f, 1.8f),
    GHOSTFREAK("Ghostfreak", 3600, 600, 0.6f, 1.8f),
    WILDMUTT("Wildmutt", 3600, 600, 0.8f, 1.0f),
    UPGRADE("Upgrade", 3600, 600, 0.6f, 1.8f);

    private final String displayName;
    private final ResourceLocation iconTexture;
    private final int transformDurationTicks;
    private final int cooldownTicks;
    private final float width;
    private final float height;

    AlienType(String displayName, int transformDurationTicks, int cooldownTicks, float width, float height) {
        this.displayName = displayName;
        this.iconTexture = new ResourceLocation(
                BenTenMod.MOD_ID, "textures/gui/aliens/" + name().toLowerCase().replace("_", "") + "_icon.png");
        this.transformDurationTicks = transformDurationTicks;
        this.cooldownTicks = cooldownTicks;
        this.width = width;
        this.height = height;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ResourceLocation getIconTexture() {
        return iconTexture;
    }

    public int getTransformDurationTicks() {
        return transformDurationTicks;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Nullable
    public static AlienType fromName(String name) {
        for (AlienType type : values()) {
            if (type.name().equalsIgnoreCase(name) || type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public static AlienType fromOrdinal(int ordinal) {
        AlienType[] values = values();
        if (ordinal < 0 || ordinal >= values.length) {
            return HEATBLAST;
        }
        return values[ordinal];
    }
}
