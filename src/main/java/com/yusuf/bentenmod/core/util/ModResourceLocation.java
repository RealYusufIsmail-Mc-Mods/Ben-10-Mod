package com.yusuf.bentenmod.core.util;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.util.ResourceLocation;

public class ModResourceLocation extends ResourceLocation {
    public ModResourceLocation(String resourceName) {
        super(addModNamespace(resourceName));
    }

    private static String addModNamespace(String resourceName) {
        if (resourceName.contains(":")) {
            return resourceName;
        }
        return BenTenMod.MOD_ID + ":" + resourceName;
    }
}
