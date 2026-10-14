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
        BakedAnimations animations =
                GeckoLibCache.getBakedAnimations().get(AlienGeoModel.animationsOf(alien));
        return animations != null
                && animations.getAnimation("animation." + alien.getPath() + ".fly") != null;
    }

    public static boolean shouldPlayFly(AbstractClientPlayer player, ResourceLocation alien) {
        return isFlying(player) && hasFlyAnimation(alien);
    }
}
