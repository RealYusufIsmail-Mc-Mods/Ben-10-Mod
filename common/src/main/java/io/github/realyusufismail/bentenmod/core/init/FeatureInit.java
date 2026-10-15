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
package io.github.realyusufismail.bentenmod.core.init;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.worldgen.OmnitrixCrashSiteFeature;
import io.github.realyusufismail.bentenmod.worldgen.NullVoidFortressFeature;
import io.github.realyusufismail.bentenmod.worldgen.FloatingPrisonFeature;
import io.github.realyusufismail.bentenmod.worldgen.PlumberOutpostFeature;
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
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FLOATING_PRISON =
            FEATURES.register("floating_prison",
                    () -> new FloatingPrisonFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PLUMBER_OUTPOST =
            FEATURES.register("plumber_outpost",
                    () -> new PlumberOutpostFeature(NoneFeatureConfiguration.CODEC));

    private FeatureInit() {}

    public static void init() {}
}
