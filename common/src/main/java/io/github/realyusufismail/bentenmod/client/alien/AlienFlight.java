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

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.loading.object.BakedAnimations;

/** Client-side check for "this transformed player is flying and the alien can show it". */
public final class AlienFlight {
    private AlienFlight() {}

    /** Creative-style flight, or airborne while the form's flight ability has granted mayfly. */
    public static boolean isFlying(Player player) {
        var abilities = player.getAbilities();
        return abilities.flying || (abilities.mayfly && !player.onGround() && !player.isInWater());
    }

    /** Whether the alien's animation file defines {@code animation.<id>.fly}. */
    public static boolean hasFlyAnimation(ResourceLocation alien) {
        return hasAnimation(alien, "fly");
    }

    /** Whether the alien's animation file defines {@code animation.<id>.<name>}. */
    public static boolean hasAnimation(ResourceLocation alien, String name) {
        BakedAnimations animations =
                GeckoLibCache.getBakedAnimations().get(AlienGeoModel.animationsOf(alien));
        return animations != null
                && animations.getAnimation("animation." + alien.getPath() + "." + name) != null;
    }

    public static boolean shouldPlayFly(AbstractClientPlayer player, ResourceLocation alien) {
        return isFlying(player) && hasFlyAnimation(alien);
    }

    /** Sprinting on foot, for speedsters whose animation file has a {@code run} clip. */
    public static boolean shouldPlayRun(AbstractClientPlayer player, ResourceLocation alien) {
        return player.isSprinting() && !isFlying(player) && !player.isInWater()
                && hasAnimation(alien, "run");
    }
}
