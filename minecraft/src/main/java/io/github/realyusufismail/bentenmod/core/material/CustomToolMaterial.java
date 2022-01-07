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
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CustomToolMaterial implements Tier {

    SWORD(20, 2500, 60f, 10f, 40, () -> Ingredient.of(ItemInit.LEGENDARY.get())),
    HEATBLAST_SWORD(20, 2500, 60f, 10f, 40, () -> Ingredient.of(ItemInit.FIRE.get())),
    BLACK_DIAMOND_SWORD(20, 2000, 60f, 12f, 40, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_AXE(20, 2500, 60f, 11f, 40, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_PICKAXE(20, 2500, 60f, 8f, 40, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_SHOVEL(20, 2500, 60f, 9f, 40, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_HOE(20, 2500, 60f, 6f, 40, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    INFINITUM_SWORD(20, 3000, 70f, 14f, 40, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_AXE(20, 3000, 70f, 13f, 40, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_PICKAXE(20, 3000, 70f, 12f, 40, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_SHOVEL(20, 3000, 70f, 10f, 40, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_HOE(20, 3000, 70f, 7f, 40, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    AXE(50, 2500, 80f, 8, 50, () -> Ingredient.of(ItemInit.RUBY.get())),
    IMPERIUM_SWORD(3, 3000, 70f, 7.5f, 40, () -> Ingredient.of(ItemInit.IMPERIUM.get())),
    IMPERIUM_PICKAXE(2, 2500, 40f, 6f, 40, () -> Ingredient.of(ItemInit.IMPERIUM.get())),
    IMPERIUM_PICKAXE_UPGRADED(3, 3000, 50f, 5f, 40, () -> Ingredient.of(ItemInit.IMPERIUM.get())),
    IMPERIUM_AXE(3, 3000, 50f, 5f, 40, () -> Ingredient.of(ItemInit.IMPERIUM.get()));


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairMaterial;

    CustomToolMaterial(int harvestLevel, int maxUses, float efficiency, float attackDamage,
            int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial.get();
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial;
    }

}
