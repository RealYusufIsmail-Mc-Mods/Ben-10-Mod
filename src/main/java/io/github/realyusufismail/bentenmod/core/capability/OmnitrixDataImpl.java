/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.core.capability;

import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.Constants;

public class OmnitrixDataImpl implements IOmnitrixData {

    @Nullable
    private AlienType currentAlien = null;
    private int transformTicksRemaining = 0;
    private int cooldownTicksRemaining = 0;
    private final Set<AlienType> unlockedAliens = EnumSet.noneOf(AlienType.class);

    @Nullable
    @Override
    public AlienType getCurrentAlien() {
        return currentAlien;
    }

    @Override
    public boolean isTransformed() {
        return currentAlien != null && transformTicksRemaining > 0;
    }

    @Override
    public int getTransformTicksRemaining() {
        return transformTicksRemaining;
    }

    @Override
    public int getCooldownTicksRemaining() {
        return cooldownTicksRemaining;
    }

    @Override
    public Set<AlienType> getUnlockedAliens() {
        return unlockedAliens;
    }

    @Override
    public void transform(AlienType alien) {
        this.currentAlien = alien;
        this.transformTicksRemaining = alien.getTransformDurationTicks();
        this.cooldownTicksRemaining = 0;
    }

    @Override
    public void revert() {
        if (currentAlien != null) {
            this.cooldownTicksRemaining = currentAlien.getCooldownTicks();
        }
        this.currentAlien = null;
        this.transformTicksRemaining = 0;
    }

    @Override
    public void unlockAlien(AlienType alien) {
        unlockedAliens.add(alien);
    }

    @Override
    public boolean isAlienUnlocked(AlienType alien) {
        return unlockedAliens.contains(alien);
    }

    @Override
    public void tick(PlayerEntity player) {
        if (isTransformed()) {
            transformTicksRemaining--;
            if (transformTicksRemaining <= 0) {
                revert();
            }
        } else if (cooldownTicksRemaining > 0) {
            cooldownTicksRemaining--;
        }
    }

    @Override
    public void copyFrom(IOmnitrixData other) {
        this.currentAlien = other.getCurrentAlien();
        this.transformTicksRemaining = other.getTransformTicksRemaining();
        this.cooldownTicksRemaining = other.getCooldownTicksRemaining();
        this.unlockedAliens.clear();
        this.unlockedAliens.addAll(other.getUnlockedAliens());
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if (currentAlien != null) {
            nbt.putString("currentAlien", currentAlien.name());
        }
        nbt.putInt("transformTicks", transformTicksRemaining);
        nbt.putInt("cooldownTicks", cooldownTicksRemaining);
        ListNBT unlockedList = new ListNBT();
        for (AlienType alien : unlockedAliens) {
            unlockedList.add(StringNBT.valueOf(alien.name()));
        }
        nbt.put("unlockedAliens", unlockedList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("currentAlien")) {
            currentAlien = AlienType.fromName(nbt.getString("currentAlien"));
        } else {
            currentAlien = null;
        }
        transformTicksRemaining = nbt.getInt("transformTicks");
        cooldownTicksRemaining = nbt.getInt("cooldownTicks");
        unlockedAliens.clear();
        ListNBT unlockedList = nbt.getList("unlockedAliens", Constants.NBT.TAG_STRING);
        for (int i = 0; i < unlockedList.size(); i++) {
            AlienType alien = AlienType.fromName(unlockedList.getString(i));
            if (alien != null) {
                unlockedAliens.add(alien);
            }
        }
    }
}
