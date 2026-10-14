package io.github.realyusufismail.bentenmod.client.alien;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import io.github.realyusufismail.bentenmod.omnitrix.PlayerAnimationStateCache;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/** Per-player animation state used by GeckoLib while the player's vanilla renderer is replaced. */
public final class AlienPlayerAnimatable implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final PlayerAnimationStateCache playerStates = new PlayerAnimationStateCache();
    private AbstractClientPlayer player;

    public void bind(AbstractClientPlayer player, ResourceLocation alienId, long expiresAt) {
        this.player = player;
        playerStates.bind(player.getUUID(), alienId, expiresAt, player.tickCount);
    }

    public ResourceLocation alienId() {
        PlayerAnimationStateCache.State state = currentState();
        return state == null ? null : state.alienId();
    }

    public void forgetPlayer(java.util.UUID playerId) {
        playerStates.forget(playerId);
    }

    public void clearPlayerStates() {
        playerStates.clear();
    }

    private PlayerAnimationStateCache.State currentState() {
        return player == null ? null : playerStates.get(player.getUUID());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "alien_movement", state -> {
            PlayerAnimationStateCache.State playerState = currentState();
            if (player == null || playerState == null) {
                return PlayState.STOP;
            }
            String base = "animation." + playerState.alienId().getPath() + ".";
            RawAnimation animation;
            if (playerState.isTransforming(player.tickCount)) {
                animation = RawAnimation.begin().thenPlay(base + "transform");
            } else if (player.getDeltaMovement().horizontalDistanceSqr() > 0.0001) {
                animation = RawAnimation.begin().thenLoop(base + "walk");
            } else {
                animation = RawAnimation.begin().thenLoop(base + "idle");
            }
            state.setAndContinue(animation);
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "alien_attack", state -> {
            PlayerAnimationStateCache.State playerState = currentState();
            if (player == null || playerState == null
                    || playerState.isTransforming(player.tickCount)) {
                return PlayState.STOP;
            }
            if (player.swinging) {
                String base = "animation." + playerState.alienId().getPath() + ".";
                state.setAndContinue(RawAnimation.begin().thenPlay(base + "attack"));
                return PlayState.CONTINUE;
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object instance) {
        return player == null ? 0 : player.tickCount;
    }
}
