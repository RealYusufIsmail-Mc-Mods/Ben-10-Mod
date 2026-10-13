package io.github.realyusufismail.bentenmod.core.item;

import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

/** Shared watch clips, wired into each loader's GeoItem implementation. */
public final class OmnitrixWatchAnimations {
    private static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.omnitrix.idle");
    private static final RawAnimation ACTIVATE =
            RawAnimation.begin().thenPlay("animation.omnitrix.activate");
    private static final RawAnimation DEACTIVATE =
            RawAnimation.begin().thenPlay("animation.omnitrix.deactivate");

    private OmnitrixWatchAnimations() {}

    public static <T extends GeoAnimatable> void register(T animatable,
            AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(animatable, "omnitrix", state -> {
            state.setAndContinue(IDLE);
            return PlayState.CONTINUE;
        }).triggerableAnim("activate", ACTIVATE).triggerableAnim("deactivate", DEACTIVATE));
    }
}
