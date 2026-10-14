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

import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

/** Flame trail behind a flying Heatblast, for every transformed player in view. */
public final class AlienFlightEffects {
    private static final String HEATBLAST = "heatblast";

    private AlienFlightEffects() {}

    public static void tick(Minecraft client) {
        if (client.level == null || client.isPaused()) {
            return;
        }
        for (AbstractClientPlayer player : client.level.players()) {
            OmnitrixSnapshot state = ClientOmnitrixState.get(player.getUUID());
            if (state == null || state.currentAlien() == null
                    || !HEATBLAST.equals(state.currentAlien().getPath())
                    || !AlienFlight.isFlying(player)) {
                continue;
            }
            spawnTrail(client, player);
        }
    }

    private static void spawnTrail(Minecraft client, AbstractClientPlayer player) {
        RandomSource random = player.getRandom();
        // The body leans forward along the look direction, so the flames stream out of the head
        // and feet on the opposite side of where the player is heading.
        Vec3 back = player.getLookAngle().scale(-1);
        Vec3 velocity = player.getDeltaMovement();
        for (int i = 0; i < 3; i++) {
            double along = 0.4 + random.nextDouble() * 0.9;
            double x = player.getX() + back.x * along + (random.nextDouble() - 0.5) * 0.4;
            double y = player.getY(0.55) + back.y * along + (random.nextDouble() - 0.5) * 0.4;
            double z = player.getZ() + back.z * along + (random.nextDouble() - 0.5) * 0.4;
            client.level.addParticle(i == 0 ? ParticleTypes.FLAME : ParticleTypes.SMALL_FLAME, x, y,
                    z, velocity.x * 0.3 + back.x * 0.04, velocity.y * 0.3 + back.y * 0.04,
                    velocity.z * 0.3 + back.z * 0.04);
        }
    }
}
