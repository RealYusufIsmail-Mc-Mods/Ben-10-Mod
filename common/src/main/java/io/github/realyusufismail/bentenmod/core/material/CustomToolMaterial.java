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
package io.github.realyusufismail.bentenmod.core.material;

import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CustomToolMaterial implements Tier {
    // Ladder: iron level 2 / 250 uses, diamond level 3 / 1561, netherite level 4 / 2031.
    // Attack damage and speed are set per item class, not here.
    SWORD(3, 1561, 10, () -> Ingredient.of(ItemInit.LEGENDARY.get())),
    HEATBLAST_SWORD(3, 1561, 10, () -> Ingredient.of(ItemInit.FIRE.get())),
    BLACK_DIAMOND_SWORD(3, 1561, 10, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_AXE(3, 1561, 10, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_PICKAXE(3, 1561, 10, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_SHOVEL(3, 1561, 10, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    BLACK_DIAMOND_HOE(3, 1561, 10, () -> Ingredient.of(ItemInit.BLACK_DIAMOND.get())),
    INFINITUM_SWORD(4, 2031, 15, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_AXE(4, 2031, 15, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_PICKAXE(4, 2031, 15, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_SHOVEL(4, 2031, 15, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    INFINITUM_HOE(4, 2031, 15, () -> Ingredient.of(ItemInit.INFINITUM.get())),
    AXE(3, 1561, 10, () -> Ingredient.of(ItemInit.RUBY.get())),
    IMPERIUM_SWORD(4, 2031, 15, () -> Ingredient.of(ItemInit.IMPERIUM.get())),
    IMPERIUM_PICKAXE(4, 2031, 15, () -> Ingredient.of(ItemInit.IMPERIUM.get())),
    IMPERIUM_PICKAXE_UPGRADED(4, 2300, 18, () -> Ingredient.of(ItemInit.IMPERIUM.get())),
    IMPERIUM_AXE(4, 2031, 15, () -> Ingredient.of(ItemInit.IMPERIUM.get()));


    private final int harvestLevel;
    private final int maxUses;
    private final int enchantability;
    private final Ingredient repairMaterial;

    CustomToolMaterial(int harvestLevel, int maxUses, int enchantability,
            Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial.get();
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    /**
     * The speed which is 4 + this value. 4 is the speed of the fist.
     *
     * @return value is set to 0 as the main speed is set in the item class
     */
    @Override
    public float getSpeed() {
        return 0f;
    }

    /** @return This is set to 0 as the main attack damage is set in the item class */
    @Override
    public float getAttackDamageBonus() {
        return 0f;
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
