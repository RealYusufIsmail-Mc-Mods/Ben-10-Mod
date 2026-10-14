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
            } else if (AlienFlight.shouldPlayFly(player, playerState.alienId())) {
                animation = RawAnimation.begin().thenLoop(base + "fly");
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
