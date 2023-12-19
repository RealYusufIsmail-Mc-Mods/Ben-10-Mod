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
package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class LegendaryArmor extends ArmorItem implements IForgeItem {

    public LegendaryArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {

        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        ItemStack boot = player.getItemBySlot(EquipmentSlotType.FEET);
        ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
        ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);

        if (boot.getItem() == ItemInit.BOOTS.get()
                && legs.getItem() == ItemInit.LEGGINGS.get()
                && chest.getItem() == ItemInit.CHESTPLATE.get()
                && helmet.getItem() == ItemInit.HELMET.get())
            ;
        {
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 100, 1, false, false, true));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 2, false, false, true));
        }
    }
}
