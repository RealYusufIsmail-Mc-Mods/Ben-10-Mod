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
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * The Omnitrix watch item. Implements {@link ICurioItem} so it can be worn in the
 * Curios {@code wrist} slot — just like in the show. Once equipped it cannot be
 * removed ({@link #canUnequip} always returns {@code false}).
 */
public class OmnitrixItem extends Item implements ICurioItem {

    public OmnitrixItem(Properties properties) {
        super(properties);
    }

    // ── ICurioItem ──────────────────────────────────────────────────────────

    /** Right-clicking from the hotbar auto-equips the Omnitrix to the wrist slot. */
    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    /** Once on Ben's wrist it stays there — cannot be removed. */
    @Override
    public boolean canUnequip(String identifier, LivingEntity livingEntity, ItemStack stack) {
        return false;
    }

    /** Play the armour equip sound when slapping it on. */
    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
    }

    // ── Item ────────────────────────────────────────────────────────────────

    /**
     * If the player somehow calls use() while already wearing the Omnitrix, open the
     * selection screen. Normally Curios intercepts the right-click and equips the item
     * before this runs.
     */
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClientSide) {
            boolean wearingOmnitrix =
                    CuriosApi.getCuriosHelper().findEquippedCurio(this, player).isPresent();
            if (wearingOmnitrix) {
                net.minecraft.client.Minecraft.getInstance()
                        .setScreen(new OmnitrixScreen(new StringTextComponent("Omnitrix")));
            }
        }
        return ActionResult.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(
            ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent("Right-click to equip to wrist").withStyle(TextFormatting.GREEN));
        tooltip.add(new StringTextComponent("Cannot be removed once equipped!").withStyle(TextFormatting.RED));
        PlayerEntity player = net.minecraft.client.Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
                    if (data.getUnlockedAliens().isEmpty()) {
                    tooltip.add(new StringTextComponent("No aliens unlocked").withStyle(TextFormatting.GRAY));
                } else {
                    tooltip.add(new StringTextComponent("Unlocked aliens:").withStyle(TextFormatting.AQUA));
                    for (AlienType alien : data.getUnlockedAliens()) {
                        tooltip.add(new StringTextComponent("  - " + alien.getDisplayName())
                                .withStyle(TextFormatting.WHITE));
                    }
                }
            });
        }
    }

    // ── Static helper ───────────────────────────────────────────────────────

    /**
     * Returns true if the player has the Omnitrix equipped in the Curios wrist slot
     * OR still in their regular inventory (before first equip).
     */
    public static boolean hasOmnitrix(PlayerEntity player) {
        boolean inCurio = CuriosApi.getCuriosHelper()
                .findEquippedCurio(ItemInit.OMNITRIX.get(), player)
                .isPresent();
        if (inCurio) return true;
        return player.inventory.items.stream()
                .anyMatch(s -> s.getItem() instanceof OmnitrixItem);
    }
}
