/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.entity;

import com.mojang.math.Vector3f;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

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
        if (!level.isClientSide && duration++ > MAX_DURATION) {
            discard();
        }

        if (level.isClientSide) {
            for (int i = 0; i < 10; i++)
                level.addParticle(new DustParticleOptions(new Vector3f(1, 1, 0), 1), getRandomX(1),
                        getRandomY(), getRandomZ(1), 0, 0, 0);
        }
    }


    // TODO: causes the game to crash
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHit(result);
        if (!level.isClientSide)
            discard();
    }



    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level.isClientSide) {
            Entity target = result.getEntity();
            Entity shooter = getOwner();
            target.hurt(DamageSource.arrow(this, shooter == null ? this : shooter),
                    (float) getBaseDamage());
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

}
