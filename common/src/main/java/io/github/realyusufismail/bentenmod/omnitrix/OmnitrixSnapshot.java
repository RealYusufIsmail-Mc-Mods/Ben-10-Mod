package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.UUID;

/** Immutable player state sent to clients and used by the dial screen. */
public record OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
        boolean equipped, List<ResourceLocation> unlockedAlienIds, float width, float height,
        boolean timeoutDisabled) {
    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            List<ResourceLocation> unlockedAlienIds) {
        this(playerId, currentAlien, expiresAt, false, unlockedAlienIds, 0, 0, false);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            boolean equipped, List<ResourceLocation> unlockedAlienIds) {
        this(playerId, currentAlien, expiresAt, equipped, unlockedAlienIds, 0, 0, false);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            List<ResourceLocation> unlockedAlienIds, float width, float height) {
        this(playerId, currentAlien, expiresAt, false, unlockedAlienIds, width, height, false);
    }

    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            boolean equipped, List<ResourceLocation> unlockedAlienIds, float width, float height) {
        this(playerId, currentAlien, expiresAt, equipped, unlockedAlienIds, width, height, false);
    }

    public OmnitrixSnapshot {
        unlockedAlienIds = List.copyOf(unlockedAlienIds);
    }
}
