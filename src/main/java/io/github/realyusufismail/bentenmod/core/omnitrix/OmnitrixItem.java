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
package io.github.realyusufismail.bentenmod.core.omnitrix;

import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
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

public class OmnitrixItem extends Item {

    public OmnitrixItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClientSide) {
            net.minecraft.client.Minecraft.getInstance().setScreen(new OmnitrixScreen(new StringTextComponent("Omnitrix")));
        }
        return ActionResult.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("Right-click to open Omnitrix").withStyle(TextFormatting.GREEN));
        PlayerEntity player = net.minecraft.client.Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
                if (data.getUnlockedAliens().isEmpty()) {
                    tooltip.add(new StringTextComponent("No aliens unlocked").withStyle(TextFormatting.GRAY));
                } else {
                    tooltip.add(new StringTextComponent("Unlocked aliens:").withStyle(TextFormatting.AQUA));
                    for (AlienType alien : data.getUnlockedAliens()) {
                        tooltip.add(new StringTextComponent("  - " + alien.getDisplayName()).withStyle(TextFormatting.WHITE));
                    }
                }
            });
        }
    }
}
