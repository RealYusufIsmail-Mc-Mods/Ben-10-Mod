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
package io.github.realyusufismail.bentenmod.common.events;

import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.config.OmnitrixConfig;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import io.github.realyusufismail.bentenmod.core.network.SSyncOmnitrixPacket;
import io.github.realyusufismail.bentenmod.core.omnitrix.AlienType;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.AlienAbility;
import io.github.realyusufismail.bentenmod.core.omnitrix.ability.AlienAbilityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class OmnitrixTickHandler {

    private int syncTimer = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level.isClientSide) return;
        if (!(event.player instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.player;

        player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            // Creative players can bypass cooldown
            if (OmnitrixConfig.ALLOW_CREATIVE_BYPASS_COOLDOWN.get() && player.isCreative()) {
                // Still tick transformation timer in creative
                if (data.isTransformed()) {
                    data.tick(player);
                }
                return;
            }

            boolean wasTransformed = data.isTransformed();
            AlienType alienBefore = data.getCurrentAlien();
            int ticksBefore = data.getTransformTicksRemaining();

            data.tick(player);

            boolean nowTransformed = data.isTransformed();

            // Call ability onTick while transformed
            if (wasTransformed && nowTransformed && alienBefore != null) {
                AlienAbility ability = AlienAbilityRegistry.get(alienBefore);
                if (ability != null) {
                    ability.onTick(player);
                }
            }

            // If just reverted, call onRevert
            if (wasTransformed && !nowTransformed && alienBefore != null) {
                AlienAbility ability = AlienAbilityRegistry.get(alienBefore);
                if (ability != null) {
                    ability.onRevert(player);
                }
            }

            // Play warning sound at 10 seconds remaining
            if (wasTransformed && nowTransformed && ticksBefore > 200 && data.getTransformTicksRemaining() <= 200) {
                // TODO: play warning sound via packet when SoundInit is created
            }

            // Sync to client every 20 ticks (1 second)
            syncTimer++;
            if (syncTimer >= 20) {
                syncTimer = 0;
                SSyncOmnitrixPacket syncPacket = new SSyncOmnitrixPacket(data.serializeNBT(), player.getId());
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), syncPacket);
            }
        });
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        // Call onAttack for transformed players
        if (!(event.getSource().getEntity() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity attacker = (ServerPlayerEntity) event.getSource().getEntity();
        if (!(event.getEntityLiving() instanceof LivingEntity)) return;
        LivingEntity target = event.getEntityLiving();

        attacker.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            if (!data.isTransformed() || data.getCurrentAlien() == null) return;
            AlienAbility ability = AlienAbilityRegistry.get(data.getCurrentAlien());
            if (ability != null) {
                ability.onAttack(attacker, target);
            }
        });
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();

        player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            if (!data.isTransformed()) return;

            float healthAfterDamage = player.getHealth() - event.getAmount();
            float threshold = player.getMaxHealth() * (float) (double) OmnitrixConfig.REVERT_ON_LOW_HP_THRESHOLD.get();

            if (healthAfterDamage <= threshold) {
                data.revert();
                // Give brief invulnerability (40 ticks = 2 seconds)
                player.invulnerableTime = 40;

                SSyncOmnitrixPacket syncPacket = new SSyncOmnitrixPacket(data.serializeNBT(), player.getId());
                PacketHandler.CHANNEL.send(
                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), syncPacket);
            }
        });
    }
}
