package io.github.realyusufismail.bentenmod.omnitrix;

import io.github.realyusufismail.bentenmod.alien.Aliens;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The Omnitrix state store is kept in the overworld so it survives player and dimension changes.
 */
public final class OmnitrixSavedData extends SavedData {
    private static final String DATA_NAME = "bentenmod_omnitrix";
    private final Map<UUID, PlayerState> players = new HashMap<>();

    public static OmnitrixSavedData get(ServerLevel level) {
        return level.getServer()
            .overworld()
            .getDataStorage()
            .computeIfAbsent(OmnitrixSavedData::load, OmnitrixSavedData::new, DATA_NAME);
    }

    public PlayerState getOrCreate(ServerPlayer player) {
        PlayerState existing = players.get(player.getUUID());
        if (existing != null) {
            return existing;
        }

        Set<ResourceLocation> starters = new LinkedHashSet<>();
        Aliens.DATA.all().forEach((id, definition) -> {
            if (definition.unlockedByDefault()) {
                starters.add(id);
            }
        });
        PlayerState created = new PlayerState(starters);
        players.put(player.getUUID(), created);
        setDirty();
        return created;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag playerTags = new ListTag();
        players.forEach((uuid, state) -> {
            CompoundTag playerTag = new CompoundTag();
            playerTag.putUUID("player", uuid);
            playerTag.putString("alien",
                    state.currentAlien == null ? "" : state.currentAlien.toString());
            playerTag.putLong("expires_at", state.expiresAt);
            ListTag unlocked = new ListTag();
            state.unlockedAlienIds.forEach(id -> unlocked.add(StringTag.valueOf(id.toString())));
            playerTag.put("unlocked", unlocked);
            playerTags.add(playerTag);
        });
        tag.put("players", playerTags);
        return tag;
    }

    private static OmnitrixSavedData load(CompoundTag tag) {
        OmnitrixSavedData data = new OmnitrixSavedData();
        ListTag playerTags = tag.getList("players", Tag.TAG_COMPOUND);
        for (int i = 0; i < playerTags.size(); i++) {
            CompoundTag playerTag = playerTags.getCompound(i);
            if (!playerTag.hasUUID("player")) {
                continue;
            }
            Set<ResourceLocation> unlocked = new LinkedHashSet<>();
            ListTag unlockedTags = playerTag.getList("unlocked", Tag.TAG_STRING);
            for (int j = 0; j < unlockedTags.size(); j++) {
                ResourceLocation id = ResourceLocation.tryParse(unlockedTags.getString(j));
                if (id != null) {
                    unlocked.add(id);
                }
            }
            ResourceLocation alien = ResourceLocation.tryParse(playerTag.getString("alien"));
            PlayerState state = new PlayerState(unlocked);
            state.currentAlien = alien;
            state.expiresAt = playerTag.getLong("expires_at");
            data.players.put(playerTag.getUUID("player"), state);
        }
        return data;
    }

    public static final class PlayerState {
        private final Set<ResourceLocation> unlockedAlienIds;
        private ResourceLocation currentAlien;
        private long expiresAt;

        private PlayerState(Set<ResourceLocation> unlockedAlienIds) {
            this.unlockedAlienIds = new LinkedHashSet<>(unlockedAlienIds);
        }

        public boolean isUnlocked(ResourceLocation alien) {
            return unlockedAlienIds.contains(alien);
        }

        public boolean unlock(ResourceLocation alien) {
            return unlockedAlienIds.add(alien);
        }

        public ResourceLocation currentAlien() {
            return currentAlien;
        }

        public long expiresAt() {
            return expiresAt;
        }

        public void transform(ResourceLocation alien, long expiresAt) {
            this.currentAlien = alien;
            this.expiresAt = expiresAt;
        }

        public void revert() {
            this.currentAlien = null;
            this.expiresAt = 0;
        }

        public OmnitrixSnapshot snapshot(UUID playerId) {
            List<ResourceLocation> unlocked = new ArrayList<>(unlockedAlienIds);
            unlocked.sort(ResourceLocation::compareTo);
            return new OmnitrixSnapshot(playerId, currentAlien, expiresAt, unlocked);
        }
    }
}
