/*
 * Copyright 2026 RealYusufIsmail.
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
package io.github.realyusufismail.bentenmod.common.events;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * Adjusts the player's collision hitbox size when transformed into an alien.
 * Grey Matter shrinks to 0.3×0.5; Four Arms grows to 1.2×2.4; all others use
 * the dimensions stored on the {@link AlienType} enum.
 */
@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE)
public class TransformationEventHandler {

    @SubscribeEvent
    public static void onEntitySize(EntityEvent.Size event) {
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntity();

        player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            if (!data.isTransformed()) return;
            AlienType alien = data.getCurrentAlien();
            if (alien == null) return;
            event.setNewSize(EntitySize.scalable(alien.getWidth(), alien.getHeight()));
        });
    }
}
