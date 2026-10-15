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
package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/** Loader-neutral callbacks installed by the client entrypoint. */
public final class OmnitrixClientHooks {
    private static Runnable openDial = () -> {
    };
    private static Runnable openGuide = () -> {
    };
    private static Consumer<ResourceLocation> selectAlien = ignored -> {
    };
    private static Runnable revert = () -> {
    };

    private OmnitrixClientHooks() {}

    public static void install(Runnable open, Consumer<ResourceLocation> select,
            Runnable revertAction) {
        openDial = open;
        selectAlien = select;
        revert = revertAction;
    }

    public static void openDial() {
        openDial.run();
    }

    public static void installGuide(Runnable open) {
        openGuide = open;
    }

    public static void openGuide() {
        openGuide.run();
    }

    public static void select(ResourceLocation alien) {
        selectAlien.accept(alien);
    }

    public static void revert() {
        revert.run();
    }
}
