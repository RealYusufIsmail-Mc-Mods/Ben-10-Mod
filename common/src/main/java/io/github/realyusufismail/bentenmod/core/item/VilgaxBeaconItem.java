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
package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VilgaxBeaconItem extends Item {
    public VilgaxBeaconItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        if (!(context.getLevel() instanceof ServerLevel level)
                || !level.dimension().equals(Level.OVERWORLD)
                || !level.getBlockState(context.getClickedPos())
                    .isFaceSturdy(level, context.getClickedPos(), Direction.UP)) {
            fail(context);
            return InteractionResult.FAIL;
        }

        BlockPos spawnPos = context.getClickedPos().above();
        Vec3 center = Vec3.atBottomCenterOf(spawnPos);
        var vilgax = EntityTypesInit.VILGAX_ENTITY.get().create(level);
        if (vilgax == null) {
            fail(context);
            return InteractionResult.FAIL;
        }

        vilgax.moveTo(center.x, center.y, center.z, level.getRandom().nextFloat() * 360.0F, 0.0F);
        if (center.y + vilgax.getBbHeight() > level.getMaxBuildHeight()
                || !level.getWorldBorder().isWithinBounds(vilgax.getBoundingBox())
                || !level.noCollision(vilgax)) {
            fail(context);
            return InteractionResult.FAIL;
        }
        vilgax.beginBeaconSummon(60);
        if (!level.addFreshEntity(vilgax)) {
            fail(context);
            return InteractionResult.FAIL;
        }

        level.sendParticles(ParticleTypes.PORTAL, center.x, center.y + 1.0, center.z, 40, 0.8, 1.5,
                0.8, 0.2);
        level.playSound(null, spawnPos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F,
                0.7F);
        if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.CONSUME;
    }

    private static void fail(UseOnContext context) {
        if (context.getPlayer() != null) {
            context.getPlayer()
                .displayClientMessage(
                        Component.translatable("message.bentenmod.vilgax_beacon_failed"), true);
        }
    }
}
