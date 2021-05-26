package com.yusuf.bentenmobmod.core.util;

import com.yusuf.bentenmobmod.Main;
import net.minecraft.util.ResourceLocation;

public class ModResourceLocation extends ResourceLocation {
    public ModResourceLocation(String resourceName) {
        super(addModNamespace(resourceName));
    }

    private static String addModNamespace(String resourceName) {
        if (resourceName.contains(":")) {
            return resourceName;
        }
        return Main.MOD_ID + ":" + resourceName;
    }
}
