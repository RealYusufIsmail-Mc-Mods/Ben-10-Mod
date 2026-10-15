package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.UUID;

/** Immutable player state sent to clients and used by the dial screen. */
public record OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
        boolean equipped, List<ResourceLocation> unlockedAlienIds, float width, float height,
        boolean timeoutDisabled, long rechargeUntil, long powerCooldownUntil, long transitionId,
        int transitionKind) {

    public static final int TRANSITION_NONE = 0;
    public static final int TRANSITION_FORM = 1;
    public static final int TRANSITION_REVERT = 2;
    public static final int TRANSITION_TIMEOUT = 3;

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            List<ResourceLocation> unlockedAlienIds) {
        this(playerId, currentAlien, expiresAt, false, unlockedAlienIds, 0, 0, false, 0, 0, 0,
                TRANSITION_NONE);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            boolean equipped, List<ResourceLocation> unlockedAlienIds) {
        this(playerId, currentAlien, expiresAt, equipped, unlockedAlienIds, 0, 0, false, 0, 0, 0,
                TRANSITION_NONE);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            List<ResourceLocation> unlockedAlienIds, float width, float height) {
        this(playerId, currentAlien, expiresAt, false, unlockedAlienIds, width, height, false, 0, 0,
                0, TRANSITION_NONE);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            boolean equipped, List<ResourceLocation> unlockedAlienIds, float width, float height) {
        this(playerId, currentAlien, expiresAt, equipped, unlockedAlienIds, width, height, false, 0,
                0, 0, TRANSITION_NONE);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            boolean equipped, List<ResourceLocation> unlockedAlienIds, float width, float height,
            boolean timeoutDisabled) {
        this(playerId, currentAlien, expiresAt, equipped, unlockedAlienIds, width, height,
                timeoutDisabled, 0, 0, 0, TRANSITION_NONE);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            boolean equipped, List<ResourceLocation> unlockedAlienIds, float width, float height,
            boolean timeoutDisabled, long rechargeUntil) {
        this(playerId, currentAlien, expiresAt, equipped, unlockedAlienIds, width, height,
                timeoutDisabled, rechargeUntil, 0, 0, TRANSITION_NONE);
    }

    public OmnitrixSnapshot {
        unlockedAlienIds = List.copyOf(unlockedAlienIds);
    }
}
