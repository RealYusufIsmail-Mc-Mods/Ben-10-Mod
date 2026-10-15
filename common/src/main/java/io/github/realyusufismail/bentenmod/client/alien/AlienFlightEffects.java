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
import io.github.realyusufismail.bentenmod.omnitrix.WaterRunning;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/**
 * Flame trail behind a flying Heatblast and a speed streak behind a sprinting XLR8, for every
 * transformed player in view. Also keeps the local XLR8 running on water.
 */
public final class AlienFlightEffects {
    private static final String HEATBLAST = "heatblast";
    private static final String XLR8 = "xlr8";
    private static final DustParticleOptions XLR8_STREAK =
            new DustParticleOptions(new Vector3f(0.31F, 0.5F, 0.88F), 1.1F);
    private static final double STREAK_MIN_SPEED_SQR = 0.04;

    private AlienFlightEffects() {}

    public static void tick(Minecraft client) {
        if (client.level == null || client.isPaused()) {
            return;
        }
        if (client.player != null && isAlien(client.player, XLR8)
                && WaterRunning.keepOnSurface(client.player)) {
            spawnWake(client, client.player);
        }
        for (AbstractClientPlayer player : client.level.players()) {
            OmnitrixSnapshot state = ClientOmnitrixState.get(player.getUUID());
            if (state == null || state.currentAlien() == null) {
                continue;
            }
            String alien = state.currentAlien().getPath();
            if (HEATBLAST.equals(alien) && AlienFlight.isFlying(player)) {
                spawnTrail(client, player);
            } else if (XLR8.equals(alien) && player.isSprinting()
                    && player.getDeltaMovement().horizontalDistanceSqr() > STREAK_MIN_SPEED_SQR) {
                spawnSpeedStreak(client, player);
            }
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

    private static boolean isAlien(AbstractClientPlayer player, String alien) {
        OmnitrixSnapshot state = ClientOmnitrixState.get(player.getUUID());
        return state != null && state.currentAlien() != null
                && alien.equals(state.currentAlien().getPath());
    }

    private static void spawnWake(Minecraft client, AbstractClientPlayer player) {
        RandomSource random = player.getRandom();
        Vec3 back = player.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(-1);
        for (int i = 0; i < 4; i++) {
            client.level.addParticle(ParticleTypes.SPLASH,
                    player.getX() + back.x * 0.5 + (random.nextDouble() - 0.5) * 0.6,
                    player.getY() + 0.05,
                    player.getZ() + back.z * 0.5 + (random.nextDouble() - 0.5) * 0.6, back.x * 0.1,
                    0.15, back.z * 0.1);
        }
    }

    private static void spawnSpeedStreak(Minecraft client, AbstractClientPlayer player) {
        RandomSource random = player.getRandom();
        Vec3 back = player.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(-1);
        for (int i = 0; i < 3; i++) {
            double along = 0.3 + random.nextDouble() * 1.2;
            client.level.addParticle(XLR8_STREAK,
                    player.getX() + back.x * along + (random.nextDouble() - 0.5) * 0.5,
                    player.getY(0.25 + random.nextDouble() * 0.6),
                    player.getZ() + back.z * along + (random.nextDouble() - 0.5) * 0.5, 0.0, 0.0,
                    0.0);
        }
        if (player.onGround() && random.nextInt(3) == 0) {
            client.level.addParticle(ParticleTypes.CLOUD, player.getX() + back.x * 0.4,
                    player.getY() + 0.1, player.getZ() + back.z * 0.4, back.x * 0.05, 0.02,
                    back.z * 0.05);
        }
    }
}
