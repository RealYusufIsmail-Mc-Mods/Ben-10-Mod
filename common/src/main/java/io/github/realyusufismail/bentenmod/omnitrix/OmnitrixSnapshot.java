package io.github.realyusufismail.bentenmod.omnitrix;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.UUID;

/** Immutable player state sent to clients and used by the dial screen. */
public record OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
        List<ResourceLocation> unlockedAlienIds, float width, float height) {
    public OmnitrixSnapshot(UUID playerId, ResourceLocation currentAlien, long expiresAt,
            List<ResourceLocation> unlockedAlienIds) {
        this(playerId, currentAlien, expiresAt, unlockedAlienIds, 0, 0);
    }

    public OmnitrixSnapshot {
        unlockedAlienIds = List.copyOf(unlockedAlienIds);
    }
}
