/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.core.material;

import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum CustomArmorMaterial implements ArmorMaterial {
    ARMOUR("legendary", 30, new int[] {6, 9, 12, 6}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F,
            0.3f, () -> Ingredient.of(ItemInit.LEGENDARY.get())),
    JACKET("jacket", 30, new int[] {6, 9, 12, 6}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.3f,
            () -> Ingredient.of(ItemInit.OMNITRIX.get())),
    XLR8_ARMOUR("speedy", 30, new int[] {8, 9, 20, 6}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F,
            0.3f, () -> Ingredient.of(ItemInit.SPEED.get())),
    FOURARMS_ARMOUR("fourarms", 50, new int[] {8, 9, 20, 6}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F, 1.5f, () -> Ingredient.of(ItemInit.RUBY.get())),
    BLACK_DIAMOND_ARMOUR("black_diamond", 40, new int[] {8, 9, 20, 6}, 20,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 6.0F, 0.4f,
            () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    INFINITUM_ARMOUR("infinitum", 50, new int[] {10, 11, 20, 7}, 20,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 8.0F, 0.8f,
            () -> Ingredient.of(ItemInit.INFINITUM.get())),
    HEATBLAST_ARMOUR("fire", 30, new int[] {8, 10, 15, 13}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F, 0.3f, () -> Ingredient.of(ItemInit.FIRE.get()));


    private static final int[] baseDurability = {128, 144, 160, 112};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] armorVal;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toghness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;


    CustomArmorMaterial(String name, int durabilityMultiplier, int[] armorVal, int enchantability,
            SoundEvent equipSound, float toghness, float knockbackResistance,
            Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.armorVal = armorVal;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toghness = toghness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient.get();
    }


    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return this.armorVal[slot.getIndex()];
    }

    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.armorVal[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toghness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
