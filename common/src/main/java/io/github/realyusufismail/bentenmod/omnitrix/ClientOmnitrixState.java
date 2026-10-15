package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Read-only client cache populated exclusively by the server's state packets. */
public final class ClientOmnitrixState {
    private static final Map<UUID, OmnitrixSnapshot> PLAYERS = new ConcurrentHashMap<>();
    private static Consumer<UUID> dimensionRefresh = ignored -> {
    };
    private static BiConsumer<UUID, Boolean> localFormTransition = (ignored, timeout) -> {
    };
    private static Consumer<OmnitrixSnapshot> transformationEffect = ignored -> {
    };
    private static final Map<UUID, MorphState> MORPHS = new ConcurrentHashMap<>();
    private static final long MORPH_DURATION_NANOS = 500_000_000L;

    private ClientOmnitrixState() {}

    public static OmnitrixSnapshot get(UUID playerId) {
        return PLAYERS.get(playerId);
    }

    public static void accept(OmnitrixSnapshot snapshot) {
        OmnitrixSnapshot previous = PLAYERS.put(snapshot.playerId(), snapshot);
        ResourceLocation previousAlien = previous == null ? null : previous.currentAlien();
        boolean formChanged = !Objects.equals(previousAlien, snapshot.currentAlien());
        if (formChanged) {
            boolean timeout = previousAlien != null && snapshot.currentAlien() == null
                    && snapshot.rechargeUntil() > (previous == null ? 0 : previous.rechargeUntil());
            localFormTransition.accept(snapshot.playerId(), timeout);
            dimensionRefresh.accept(snapshot.playerId());
        }
        if (previous != null && snapshot.transitionId() != previous.transitionId()) {
            ResourceLocation morphAlien =
                    snapshot.currentAlien() == null ? previousAlien : snapshot.currentAlien();
            if (morphAlien != null) {
                float startScale;
                float targetScale;
                if (snapshot.currentAlien() == null) {
                    startScale = 1.0F;
                    targetScale = scaleToHuman(previous.height());
                } else if (previousAlien == null) {
                    startScale = scaleToHuman(snapshot.height());
                    targetScale = 1.0F;
                } else {
                    startScale = previous.height() > 0 && snapshot.height() > 0
                            ? previous.height() / snapshot.height()
                            : 1.0F;
                    targetScale = 1.0F;
                }
                MORPHS.put(snapshot.playerId(),
                        new MorphState(morphAlien, startScale, targetScale, System.nanoTime()));
            }
            transformationEffect.accept(snapshot);
        }
    }

    /**
     * Installed by the loader's client initializer without loading client classes in common code.
     */
    public static void installDimensionRefresh(Consumer<UUID> refresh) {
        dimensionRefresh = refresh;
    }

    /** Installs a loader-side callback that flashes only for a local-player form change. */
    public static void installLocalFormTransition(BiConsumer<UUID, Boolean> transition) {
        localFormTransition = transition;
    }

    public static void installTransformationEffect(Consumer<OmnitrixSnapshot> effect) {
        transformationEffect = effect;
    }

    public static float modelScale(UUID playerId, float partialTick) {
        OmnitrixSnapshot snapshot = PLAYERS.get(playerId);
        if (snapshot == null) {
            return 1.0F;
        }
        MorphState morph = activeMorph(playerId);
        if (morph != null) {
            long renderNanos = System.nanoTime() + (long) (partialTick * 50_000_000L);
            float progress = Math.min(1.0F,
                    (renderNanos - morph.startedAtNanos()) / (float) MORPH_DURATION_NANOS);
            if (progress >= 1.0F) {
                MORPHS.remove(playerId, morph);
                return morph.targetScale();
            }
            float eased = progress * progress * (3.0F - 2.0F * progress);
            return morph.startScale() + (morph.targetScale() - morph.startScale()) * eased;
        }
        return 1.0F;
    }

    public static ResourceLocation renderedAlien(UUID playerId) {
        OmnitrixSnapshot snapshot = PLAYERS.get(playerId);
        if (snapshot == null) {
            return null;
        }
        if (snapshot.currentAlien() != null) {
            return snapshot.currentAlien();
        }
        MorphState morph = activeMorph(playerId);
        return morph != null && isMorphActive(morph) ? morph.alienId() : null;
    }

    public static boolean isMorphing(UUID playerId) {
        return activeMorph(playerId) != null;
    }

    private static MorphState activeMorph(UUID playerId) {
        MorphState morph = MORPHS.get(playerId);
        if (morph != null && !isMorphActive(morph)) {
            MORPHS.remove(playerId, morph);
            return null;
        }
        return morph;
    }

    private static boolean isMorphActive(MorphState morph) {
        return System.nanoTime() - morph.startedAtNanos() < MORPH_DURATION_NANOS;
    }

    private static float scaleToHuman(float alienHeight) {
        return alienHeight > 0 ? Math.max(0.05F, 1.8F / alienHeight) : 1.0F;
    }

    public static void remove(UUID playerId) {
        PLAYERS.remove(playerId);
        MORPHS.remove(playerId);
    }

    public static void clear() {
        PLAYERS.clear();
        MORPHS.clear();
        OmnitrixFlash.reset();
    }

    private record MorphState(ResourceLocation alienId, float startScale, float targetScale,
            long startedAtNanos) {
    }
}
