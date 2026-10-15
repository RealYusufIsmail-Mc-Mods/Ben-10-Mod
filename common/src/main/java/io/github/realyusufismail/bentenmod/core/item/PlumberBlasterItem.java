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

import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.entity.PlumberBoltEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlumberBlasterItem extends Item {
    public PlumberBlasterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
            InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition().add(look.scale(0.7));
        PlumberBoltEntity bolt = new PlumberBoltEntity(level, player);
        bolt.setPos(start.x, start.y, start.z);
        bolt.shoot(look.x, look.y, look.z, PlumberBoltEntity.SPEED, 0.0F);
        level.addFreshEntity(bolt);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, start.x, start.y, start.z, 4, 0.025,
                    0.025, 0.025, 0.02);
        }
        level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS,
                0.55F, 1.5F);
        stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
        player.getCooldowns().addCooldown(this, PlumberBoltEntity.ITEM_COOLDOWN_TICKS);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemInit.TAYDENITE.get());
    }
}
