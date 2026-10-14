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
