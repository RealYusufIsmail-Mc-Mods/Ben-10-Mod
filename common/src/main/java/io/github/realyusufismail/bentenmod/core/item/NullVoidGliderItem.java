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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NullVoidGliderItem extends Item {
    public NullVoidGliderItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId,
            boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)
                || (player.getMainHandItem() != stack && player.getOffhandItem() != stack)
                || player.onGround() || player.isInWaterOrBubble()
                || player.getDeltaMovement().y >= 0.0) {
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 4, 0, true, false));
        player.fallDistance = 0.0F;
        Vec3 look = player.getLookAngle();
        Vec3 velocity = player.getDeltaMovement();
        player.setDeltaMovement(velocity.add(look.x * 0.014, 0.0, look.z * 0.014));
        if (player.tickCount % 20 == 0) {
            InteractionHand hand = player.getMainHandItem() == stack ? InteractionHand.MAIN_HAND
                    : InteractionHand.OFF_HAND;
            stack.hurtAndBreak(1, player, broken -> broken.broadcastBreakEvent(hand));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemInit.TAYDENITE.get());
    }
}
