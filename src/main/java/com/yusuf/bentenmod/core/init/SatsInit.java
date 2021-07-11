package com.yusuf.bentenmod.core.init;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import static net.minecraft.stats.Stats.CUSTOM;


public class SatsInit {
  public static final ResourceLocation INTERACT_WITH_ALIEN_TABLE = makeCustomStat("interact_with_alien_table", IStatFormatter.DEFAULT);
  public static ResourceLocation makeCustomStat(String key, IStatFormatter formatter) {
    ResourceLocation resourceLocation = new ResourceLocation(key);
    Registry.register(Registry.CUSTOM_STAT, key, resourceLocation);
    CUSTOM.get(resourceLocation, formatter);
    return resourceLocation;
  }
}



