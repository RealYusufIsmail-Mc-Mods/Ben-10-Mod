/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class HeatblastArmor extends ArmorItem implements IForgeItem {
    public final PlayerAbilities abilities = new PlayerAbilities();

    public HeatblastArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);

    }


    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
        ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
        ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);

        if (boots.getItem() == ItemInit.HEATBLAST_BOOTS.get() && legs.getItem() == ItemInit.HEATBLAST_LEGGINGS.get() && chest.getItem() == ItemInit.HEATBLAST_CHESTPLATE.get() && head.getItem() == ItemInit.HEATBLAST_HELMET.get()) {
            player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 3, false, false, true));
            player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 3, false, false, true));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 3, false, false, true));
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 100, 1, false, false, true));
            player.abilities.mayfly = true;
            player.fallDistance = 0.0F;
            player.abilities.setFlyingSpeed(0.1F);
        }


    }
}
	

