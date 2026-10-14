package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.worldgen.OmnitrixCrashSiteFeature;
import io.github.realyusufismail.bentenmod.worldgen.NullVoidFortressFeature;
import io.github.realyusufismail.bentenmod.platform.registry.RegistrationProvider;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class FeatureInit {
    private static final RegistrationProvider<Feature<?>> FEATURES =
            RegistrationProvider.get(Registries.FEATURE, BenTenMod.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> OMNITRIX_CRASH_SITE =
            FEATURES.register("omnitrix_crash_site",
                    () -> new OmnitrixCrashSiteFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> NULL_VOID_FORTRESS =
            FEATURES.register("null_void_fortress",
                    () -> new NullVoidFortressFeature(NoneFeatureConfiguration.CODEC));

    private FeatureInit() {}

    public static void init() {}
}
