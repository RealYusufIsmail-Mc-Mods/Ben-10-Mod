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
package io.github.realyusufismail.bentenmod.core.omnitrix;

import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import io.github.realyusufismail.bentenmod.core.network.SSyncOmnitrixPacket;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

public class AlienDNAItem extends Item {

    private final AlienType alienType;

    public AlienDNAItem(AlienType alienType, Properties properties) {
        super(properties);
        this.alienType = alienType;
    }

    public AlienType getAlienType() {
        return alienType;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return ActionResult.success(stack);
        }

        // Check if player has Omnitrix in inventory
        boolean hasOmnitrix = player.inventory.items.stream().anyMatch(s -> s.getItem() == ItemInit.OMNITRIX.get());

        if (!hasOmnitrix) {
            player.displayClientMessage(
                    new StringTextComponent("You need an Omnitrix to use this!").withStyle(TextFormatting.RED), true);
            return ActionResult.fail(stack);
        }

        player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            if (data.isAlienUnlocked(alienType)) {
                player.displayClientMessage(
                        new StringTextComponent(alienType.getDisplayName() + " is already unlocked!")
                                .withStyle(TextFormatting.YELLOW),
                        true);
            } else {
                data.unlockAlien(alienType);
                // Consume the item
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                player.displayClientMessage(
                        new StringTextComponent(alienType.getDisplayName() + " DNA unlocked!")
                                .withStyle(TextFormatting.GREEN),
                        true);
                // Immediately sync to client so the OmnitrixScreen shows the newly unlocked alien
                if (player instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    PacketHandler.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> serverPlayer),
                            new SSyncOmnitrixPacket(data.serializeNBT(), player.getId()));
                }
            }
        });

        return ActionResult.consume(stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(
            ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(alienType.getDisplayName() + " DNA Sample").withStyle(TextFormatting.AQUA));
        tooltip.add(new StringTextComponent("Right-click with Omnitrix in inventory to unlock")
                .withStyle(TextFormatting.GRAY));
    }
}
