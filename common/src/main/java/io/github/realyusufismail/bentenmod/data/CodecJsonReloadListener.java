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
package io.github.realyusufismail.bentenmod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import io.github.realyusufismail.bentenmod.BenTenMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Loads every JSON file under {@code data/<namespace>/<folder>/} with a codec. Bad files are logged
 * and skipped so one typo doesn't break the rest. Reloads with {@code /reload}.
 */
public class CodecJsonReloadListener<T> extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    private final String folder;
    private final Codec<T> codec;
    private Map<ResourceLocation, T> entries = Map.of();

    public CodecJsonReloadListener(String folder, Codec<T> codec) {
        super(GSON, folder);
        this.folder = folder;
        this.codec = codec;
    }

    public String folder() {
        return folder;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> files, ResourceManager resourceManager,
            ProfilerFiller profiler) {
        Map<ResourceLocation, T> loaded = new LinkedHashMap<>();
        files.forEach((id, json) -> codec.parse(JsonOps.INSTANCE, json)
            .resultOrPartial(
                    error -> BenTenMod.LOGGER.error("Skipping {} {}: {}", folder, id, error))
            .ifPresent(value -> loaded.put(id, value)));
        this.entries = Collections.unmodifiableMap(loaded);
        BenTenMod.LOGGER.info("Loaded {} {}", loaded.size(), folder);
    }

    public Map<ResourceLocation, T> all() {
        return entries;
    }

    public Optional<T> get(ResourceLocation id) {
        return Optional.ofNullable(entries.get(id));
    }
}
