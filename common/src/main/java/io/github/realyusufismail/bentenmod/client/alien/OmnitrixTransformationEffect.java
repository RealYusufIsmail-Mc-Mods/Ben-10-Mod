package io.github.realyusufismail.bentenmod.client.alien;

import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/** Client-only burst shown to the transforming player and everyone tracking them. */
public final class OmnitrixTransformationEffect {
    private static final int GREEN = OmnitrixSnapshot.TRANSITION_FORM;
    private static final Map<UUID, Burst> BURSTS = new HashMap<>();

    private OmnitrixTransformationEffect() {}

    public static void play(OmnitrixSnapshot snapshot) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) {
            return;
        }
        Player player = client.level.getPlayerByUUID(snapshot.playerId());
        if (player == null) {
            return;
        }
        boolean transform = snapshot.transitionKind() == GREEN;
        boolean timeout = snapshot.transitionKind() == OmnitrixSnapshot.TRANSITION_TIMEOUT;
        float red = timeout ? 0.95F : 0.08F;
        float green = timeout ? 0.08F : 0.95F;
        float blue = timeout ? 0.16F : 0.35F;
        int duration = transform ? 12 : 6;
        BURSTS.put(snapshot.playerId(), new Burst(client.level.getGameTime(), duration,
                new DustParticleOptions(new Vector3f(red, green, blue), transform ? 1.15F : 0.8F)));
        float pitch = transform ? 1.65F : 1.85F;
        client.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, transform ? 0.75F : 0.5F, pitch,
                false);
    }

    public static void tick() {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) {
            BURSTS.clear();
            return;
        }
        Iterator<Map.Entry<UUID, Burst>> bursts = BURSTS.entrySet().iterator();
        while (bursts.hasNext()) {
            Map.Entry<UUID, Burst> entry = bursts.next();
            Burst burst = entry.getValue();
            long age = client.level.getGameTime() - burst.startedAt();
            if (age >= burst.duration()) {
                bursts.remove();
                continue;
            }
            Player player = client.level.getPlayerByUUID(entry.getKey());
            if (player == null) {
                continue;
            }
            double progress = age / (double) burst.duration();
            double angle = age * 0.9;
            double radius = 0.24 + 0.14 * Math.sin(progress * Math.PI);
            double y = player.getY() + Math.max(0.8, player.getBbHeight()) * progress;
            for (int strand = 0; strand < 2; strand++) {
                double strandAngle = angle + strand * Math.PI;
                double x = player.getX() + Math.cos(strandAngle) * radius;
                double z = player.getZ() + Math.sin(strandAngle) * radius;
                client.level.addParticle(burst.dust(), x, y, z, -Math.sin(strandAngle) * 0.025,
                        0.045, Math.cos(strandAngle) * 0.025);
                if (age % 3 == 0) {
                    client.level.addParticle(ParticleTypes.GLOW, x, y, z, 0, 0.06, 0);
                    client.level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0.08, 0);
                }
            }
        }
    }

    public static void clear() {
        BURSTS.clear();
    }

    private record Burst(long startedAt, int duration, ParticleOptions dust) {
    }
}
