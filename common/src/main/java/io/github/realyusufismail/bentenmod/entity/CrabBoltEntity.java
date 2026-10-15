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
package io.github.realyusufismail.bentenmod.entity;

import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3f;

public class CrabBoltEntity extends AbstractArrow {

    private static final int MAX_DURATION = 40;

    private int duration;

    public CrabBoltEntity(EntityType<? extends CrabBoltEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
        this.setBaseDamage(6);
    }

    public CrabBoltEntity(double x, double y, double z, Level world, Entity owner) {
        super(EntityTypesInit.CRAB_BOLT_ENTITY.get(), x, y, z, world);
        this.setNoGravity(true);
        this.setBaseDamage(6);
        this.setOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && duration++ > MAX_DURATION) {
            discard();
        }

        if (level().isClientSide) {
            for (int i = 0; i < 10; i++)
                level().addParticle(new DustParticleOptions(new Vector3f(1, 1, 0), 1),
                        getRandomX(1), getRandomY(), getRandomZ(1), 0, 0, 0);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide)
            discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

}
