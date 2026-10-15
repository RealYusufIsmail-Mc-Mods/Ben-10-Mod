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

    public record ReturnPoint(ResourceLocation dimension, double x, double y, double z, float yaw,
            float pitch) {
    }

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

        PlayerState created = new PlayerState(starterAlienIds());
        players.put(player.getUUID(), created);
        setDirty();
        return created;
    }

    private PlayerState getOrCreate(UUID playerId) {
        return this.players.computeIfAbsent(playerId,
                ignored -> new PlayerState(starterAlienIds()));
    }

    private static Set<ResourceLocation> starterAlienIds() {
        Set<ResourceLocation> starters = new LinkedHashSet<>();
        Aliens.DATA.all().forEach((id, definition) -> {
            if (definition.unlockedByDefault()) {
                starters.add(id);
            }
        });
        return starters;
    }

    public boolean scheduleHunterSpawn(UUID playerId, long spawnAt) {
        PlayerState state = getOrCreate(playerId);
        if (state.hunterSpawned || state.hunterSpawnAt >= 0) {
            return false;
        }
        state.hunterSpawnAt = spawnAt;
        setDirty();
        return true;
    }

    public long hunterSpawnAt(UUID playerId) {
        PlayerState state = this.players.get(playerId);
        return state == null ? -1 : state.hunterSpawnAt;
    }

    public boolean rescheduleHunterSpawn(UUID playerId, long spawnAt) {
        PlayerState state = this.players.get(playerId);
        if (state == null || state.hunterSpawned || state.hunterSpawnAt < 0) {
            return false;
        }
        state.hunterSpawnAt = spawnAt;
        setDirty();
        return true;
    }

    public boolean completeHunterSpawn(UUID playerId) {
        PlayerState state = this.players.get(playerId);
        if (state == null || state.hunterSpawned || state.hunterSpawnAt < 0) {
            return false;
        }
        state.hunterSpawnAt = -1;
        state.hunterSpawned = true;
        setDirty();
        return true;
    }

    public boolean hasSpawnedHunter(UUID playerId) {
        PlayerState state = this.players.get(playerId);
        return state != null && state.hunterSpawned;
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
            playerTag.putLong("recharge_until", state.rechargeUntil);
            playerTag.putBoolean("equipped", state.equipped);
            playerTag.putBoolean("timeout_disabled", state.timeoutDisabled);
            playerTag.putBoolean("guide_given", state.guideGiven);
            playerTag.putBoolean("null_void_arrival_hint", state.nullVoidArrivalHintShown);
            playerTag.putLong("transition_id", state.transitionId);
            playerTag.putInt("transition_kind", state.transitionKind);
            playerTag.putLong("kraab_hunter_at", state.hunterSpawnAt);
            playerTag.putBoolean("kraab_hunter_spawned", state.hunterSpawned);
            if (state.returnPoint != null) {
                CompoundTag returnTag = new CompoundTag();
                returnTag.putString("dimension", state.returnPoint.dimension().toString());
                returnTag.putDouble("x", state.returnPoint.x());
                returnTag.putDouble("y", state.returnPoint.y());
                returnTag.putDouble("z", state.returnPoint.z());
                returnTag.putFloat("yaw", state.returnPoint.yaw());
                returnTag.putFloat("pitch", state.returnPoint.pitch());
                playerTag.put("projector_return", returnTag);
            }
            ListTag unlocked = new ListTag();
            state.unlockedAlienIds.forEach(id -> unlocked.add(StringTag.valueOf(id.toString())));
            playerTag.put("unlocked", unlocked);
            playerTags.add(playerTag);
        });
        tag.put("players", playerTags);
        return tag;
    }

    public static OmnitrixSavedData load(CompoundTag tag) {
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
            state.rechargeUntil = playerTag.getLong("recharge_until");
            state.equipped = playerTag.getBoolean("equipped");
            state.timeoutDisabled = playerTag.getBoolean("timeout_disabled");
            state.guideGiven = playerTag.getBoolean("guide_given");
            state.nullVoidArrivalHintShown = playerTag.getBoolean("null_void_arrival_hint");
            state.transitionId = playerTag.getLong("transition_id");
            state.transitionKind = playerTag.getInt("transition_kind");
            state.hunterSpawnAt =
                    playerTag.contains("kraab_hunter_at") ? playerTag.getLong("kraab_hunter_at")
                            : -1;
            state.hunterSpawned = playerTag.getBoolean("kraab_hunter_spawned");
            if (playerTag.contains("projector_return", Tag.TAG_COMPOUND)) {
                CompoundTag returnTag = playerTag.getCompound("projector_return");
                ResourceLocation dimension =
                        ResourceLocation.tryParse(returnTag.getString("dimension"));
                if (dimension != null) {
                    state.returnPoint = new ReturnPoint(dimension, returnTag.getDouble("x"),
                            returnTag.getDouble("y"), returnTag.getDouble("z"),
                            returnTag.getFloat("yaw"), returnTag.getFloat("pitch"));
                }
            }
            data.players.put(playerTag.getUUID("player"), state);
        }
        return data;
    }

    public static final class PlayerState {
        private final Set<ResourceLocation> unlockedAlienIds;
        private ResourceLocation currentAlien;
        private long expiresAt;
        private long rechargeUntil;
        private boolean equipped;
        private boolean timeoutDisabled;
        private boolean guideGiven;
        private boolean nullVoidArrivalHintShown;
        private long transitionId;
        private int transitionKind;
        private ReturnPoint returnPoint;
        private long hunterSpawnAt = -1;
        private boolean hunterSpawned;

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

        public long rechargeUntil() {
            return rechargeUntil;
        }

        public boolean equipped() {
            return equipped;
        }

        public boolean timeoutDisabled() {
            return timeoutDisabled;
        }

        public boolean guideGiven() {
            return guideGiven;
        }

        public long transitionId() {
            return transitionId;
        }

        public int transitionKind() {
            return transitionKind;
        }

        public void markGuideGiven() {
            guideGiven = true;
        }

        public boolean markNullVoidArrivalHintShown() {
            if (nullVoidArrivalHintShown) {
                return false;
            }
            nullVoidArrivalHintShown = true;
            return true;
        }

        public void markTransition(int kind) {
            transitionId++;
            transitionKind = kind;
        }

        public void disableTimeout() {
            timeoutDisabled = true;
            rechargeUntil = 0;
        }

        public ReturnPoint returnPoint() {
            return returnPoint;
        }

        public void setReturnPoint(ReturnPoint returnPoint) {
            this.returnPoint = returnPoint;
        }

        public boolean equip() {
            if (equipped) {
                return false;
            }
            equipped = true;
            return true;
        }

        public void transform(ResourceLocation alien, long expiresAt) {
            this.currentAlien = alien;
            this.expiresAt = expiresAt;
            this.rechargeUntil = 0;
        }

        public void revert() {
            this.currentAlien = null;
            this.expiresAt = 0;
            this.rechargeUntil = 0;
        }

        public void timeout(long rechargeUntil) {
            this.currentAlien = null;
            this.expiresAt = 0;
            this.rechargeUntil = rechargeUntil;
        }

        public OmnitrixSnapshot snapshot(UUID playerId) {
            List<ResourceLocation> unlocked = new ArrayList<>(unlockedAlienIds);
            unlocked.sort(ResourceLocation::compareTo);
            return new OmnitrixSnapshot(playerId, currentAlien, expiresAt, equipped, unlocked, 0, 0,
                    timeoutDisabled, rechargeUntil, 0, transitionId, transitionKind);
        }
    }

    public ReturnPoint returnPoint(UUID playerId) {
        PlayerState state = this.players.get(playerId);
        return state == null ? null : state.returnPoint();
    }

    public void setReturnPoint(UUID playerId, ReturnPoint returnPoint) {
        getOrCreate(playerId).setReturnPoint(returnPoint);
        setDirty();
    }

    public boolean isEquipped(UUID playerId) {
        PlayerState state = players.get(playerId);
        return state != null && state.equipped();
    }
}
