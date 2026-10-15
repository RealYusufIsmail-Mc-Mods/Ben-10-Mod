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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CustomArmorMaterial implements ArmorMaterial {
    // armorVal is indexed by ArmorItem.Type ordinal: {helmet, chestplate, leggings, boots}.
    // Anchors: iron 2/6/5/2; diamond 3/8/6/3 (toughness 2); netherite 3/8/6/3 (toughness 3,
    // knockback 0.1).
    ARMOUR("legendary", 33, new int[] {3, 8, 6, 3}, 10, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F,
            0.0f, () -> Ingredient.of(ItemInit.LEGENDARY.get())),
    JACKET("jacket", 15, new int[] {2, 6, 5, 2}, 9, SoundEvents.ARMOR_EQUIP_NETHERITE, 0.0F, 0.0f,
            () -> Ingredient.of(ItemInit.OMNITRIX.get())),
    XLR8_ARMOUR("speedy", 24, new int[] {2, 7, 6, 2}, 14, SoundEvents.ARMOR_EQUIP_NETHERITE, 0.0F,
            0.0f, () -> Ingredient.of(ItemInit.SPEED.get())),
    FOURARMS_ARMOUR("fourarms", 27, new int[] {3, 7, 6, 3}, 12, SoundEvents.ARMOR_EQUIP_NETHERITE,
            1.0F, 0.0f, () -> Ingredient.of(ItemInit.RUBY.get())),
    BLACK_DIAMOND_ARMOUR("black_diamond", 35, new int[] {3, 8, 6, 3}, 12,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5F, 0.05f,
            () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    INFINITUM_ARMOUR("infinitum", 37, new int[] {3, 8, 6, 3}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE,
            3.0F, 0.1f, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    HEATBLAST_ARMOUR("fire", 27, new int[] {3, 7, 6, 3}, 14, SoundEvents.ARMOR_EQUIP_NETHERITE,
            1.0F, 0.0f, () -> Ingredient.of(ItemInit.FIRE.get()));


    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    private final String name;
    private final int durabilityMultiplier;
    private final int[] armorVal;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;


    CustomArmorMaterial(String name, int durabilityMultiplier, int[] armorVal, int enchantability,
            SoundEvent equipSound, float toughness, float knockbackResistance,
            Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.armorVal = armorVal;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient.get();
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return durabilityMultiplier * BASE_DURABILITY[type.ordinal()];
    }


    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return armorVal[pType.ordinal()];
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
    public String getName() {
        return "bentenmod:" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
