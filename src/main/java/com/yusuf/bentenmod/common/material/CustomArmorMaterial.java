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
package com.yusuf.bentenmod.common.material;

import com.yusuf.bentenmod.core.init.ItemInit;
import java.util.function.Supplier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum CustomArmorMaterial implements IArmorMaterial {
    ARMOUR(
            "legendary",
            30,
            new int[] {6, 9, 12, 6},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            0.3f,
            () -> Ingredient.of(ItemInit.LEGENDARY.get())),
    JACKET(
            "jacket",
            30,
            new int[] {6, 9, 12, 6},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            0.3f,
            () -> Ingredient.of(ItemInit.OMNITRIX.get())),
    XLR8_ARMOUR(
            "speedy",
            30,
            new int[] {8, 9, 20, 6},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            0.3f,
            () -> Ingredient.of(ItemInit.SPEED.get())),
    FOURARMS_ARMOUR(
            "fourarms",
            50,
            new int[] {8, 9, 20, 6},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            1.5f,
            () -> Ingredient.of(ItemInit.RUBY.get())),
    BLACK_DIAMOND_ARMOUR(
            "black_diamond",
            40,
            new int[] {8, 9, 20, 6},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            6.0F,
            0.4f,
            () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    INFINITUM_ARMOUR(
            "infinitum",
            50,
            new int[] {10, 11, 20, 7},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            8.0F,
            0.8f,
            () -> Ingredient.of(ItemInit.INFINITUM.get())),
    HEATBLAST_ARMOUR(
            "fire",
            30,
            new int[] {8, 10, 15, 13},
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4.0F,
            0.3f,
            () -> Ingredient.of(ItemInit.FIRE.get()));

    private static final int[] baseDurability = {128, 144, 160, 112};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] armorVal;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toghness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;

    CustomArmorMaterial(
            String name,
            int durabilityMultiplier,
            int[] armorVal,
            int enchantability,
            SoundEvent equipSound,
            float toghness,
            float knockbackResistance,
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
    public int getDurabilityForSlot(EquipmentSlotType slot) {
        return baseDurability[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slot) {
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
