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

import io.github.realyusufismail.bentenmod.alien.AlienDefinition;
import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

/** Shared, server-authoritative empty-hand DNA scanning for the two loader interaction hooks. */
public final class OmnitrixInteractions {
    private OmnitrixInteractions() {}

    public static InteractionResult scanEntity(Player player, Entity target) {
        return scan(player, BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()), true);
    }

    public static InteractionResult scanBlock(Player player, BlockState state) {
        return scan(player, BuiltInRegistries.BLOCK.getKey(state.getBlock()), false);
    }

    private static InteractionResult scan(Player player, ResourceLocation source,
            boolean entitySource) {
        if (!player.isShiftKeyDown() || !player.getMainHandItem().isEmpty()) {
            return InteractionResult.PASS;
        }

        Map.Entry<ResourceLocation, AlienDefinition> match = Aliens.DATA.all()
            .entrySet()
            .stream()
            .filter(entry -> entitySource
                    ? entry.getValue().dnaSources().entities().contains(source)
                    : entry.getValue().dnaSources().blocks().contains(source))
            .findFirst()
            .orElse(null);
        if (match == null) {
            return InteractionResult.PASS;
        }

        if (player.level().isClientSide) {
            OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
            return snapshot != null && snapshot.equipped() && snapshot.currentAlien() == null
                    ? InteractionResult.SUCCESS
                    : InteractionResult.PASS;
        }
        if (!(player instanceof ServerPlayer serverPlayer)
                || !OmnitrixService.isEquipped(serverPlayer)) {
            return InteractionResult.PASS;
        }
        if (serverPlayer.getCooldowns().isOnCooldown(ItemInit.OMNITRIX_WATCH.get())) {
            // Also absorbs the second of Forge's two entity-interact events for one click.
            return InteractionResult.CONSUME;
        }

        long worldTime = serverPlayer.serverLevel().getServer().overworld().getGameTime();
        OmnitrixService.tick(serverPlayer, worldTime);
        if (OmnitrixService.snapshot(serverPlayer).currentAlien() != null) {
            return InteractionResult.PASS;
        }

        if (!OmnitrixService.unlock(serverPlayer, match.getKey())) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.bentenmod.dna_already_sampled"), true);
            serverPlayer.getCooldowns().addCooldown(ItemInit.OMNITRIX_WATCH.get(), 30);
            return InteractionResult.CONSUME;
        }

        Services.PLATFORM.syncOmnitrixState(serverPlayer);
        serverPlayer.serverLevel()
            .playSound(null, serverPlayer.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME,
                    SoundSource.PLAYERS, 0.9F, 1.2F);
        serverPlayer.displayClientMessage(Component.translatable("message.bentenmod.dna_sampled",
                Component.translatable(match.getKey().toLanguageKey("alien"))), true);
        serverPlayer.getCooldowns().addCooldown(ItemInit.OMNITRIX_WATCH.get(), 30);
        return InteractionResult.CONSUME;
    }
}
