package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @see Stats
 */
public class StatsInit {
    public static final DeferredRegister<StatType<?>> STAT_TYPES =
            DeferredRegister.create(ForgeRegistries.STAT_TYPES, BenTenMod.MOD_ID);

    public static final RegistryObject<StatType<ResourceLocation>> CUSTOM =
            makeRegistryStatType("custom", Registry.CUSTOM_STAT);

    public static final ResourceLocation INTERACT_WITH_OMNITRIX_CRAFTER =
            registerCustom("interact_with_omnitrix_crafter", StatFormatter.DEFAULT);

    private static <T> RegistryObject<StatType<T>> makeRegistryStatType(String pKey,
            Registry<T> pRegistry) {
        return STAT_TYPES.register(pKey, () -> new StatType<>(pRegistry));
    }

    private static <T> ResourceLocation registerCustom(String pKey, StatFormatter pFormatter) {
        ResourceLocation resourcelocation = new ResourceLocation(pKey);
        Registry.register(Registry.CUSTOM_STAT, pKey, resourcelocation);
        CUSTOM.get().get(resourcelocation, pFormatter);
        return resourcelocation;
    }
}
