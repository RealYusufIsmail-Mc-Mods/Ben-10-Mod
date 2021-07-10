package com.yusuf.bentenmod.core.init;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class SatsInit {
    public static final StatType<ResourceLocation> CUSTOM = makeRegistryStatType("custom", Registry.CUSTOM_STAT);
    public static final ResourceLocation INTERACT_WITH_ALIEN_TABLE = makeCustomStat("interact_with_alien_table", IStatFormatter.DEFAULT);

    private static ResourceLocation makeCustomStat(String p_199084_0_, IStatFormatter p_199084_1_) {
        ResourceLocation resourcelocation = new ResourceLocation(p_199084_0_);
        Registry.register(Registry.CUSTOM_STAT, p_199084_0_, resourcelocation);
        CUSTOM.get(resourcelocation, p_199084_1_);
        return resourcelocation;
    }

    /**
     *
     * @param p_199085_0_
     * @param p_199085_1_
     * @param <T>
     * @return
     * @see DeferredRegister
     */
    private static <T> StatType<T> makeRegistryStatType(String p_199085_0_, Registry<T> p_199085_1_) {
        return Registry.register(ForgeRegistries.STAT_TYPES, p_199085_0_, new StatType<>(p_199085_1_));
    }
}
