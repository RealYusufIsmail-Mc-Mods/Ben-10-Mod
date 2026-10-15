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
package io.github.realyusufismail.bentenmod.core.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

@SuppressWarnings("all")
public class FoodInit {
    // Omnitrix Apple
    public static final FoodProperties OMNITRIX_APPLE = new FoodProperties.Builder()
        .effect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 0), 1)
        .effect(new MobEffectInstance(MobEffects.ABSORPTION, 3600, 1), 1)
        .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400, 0), 1)
        .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0), 1)
        .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0), 1)
        .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0), 1)
        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 2400, 0), 1)
        .nutrition(4)
        .saturationMod(1.2f)
        .alwaysEat()
        .build();
    // Enchanted Omnitrix Apple
    public static final FoodProperties ENCHANTED_OMNITRIX_APPLE = new FoodProperties.Builder()
        .effect(new MobEffectInstance(MobEffects.REGENERATION, 6000, 0), 1)
        .effect(new MobEffectInstance(MobEffects.ABSORPTION, 4800, 3), 1)
        .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 4800, 0), 1)
        .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4800, 0), 1)
        .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3600, 0), 1)
        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 0), 1)
        .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 0), 1)
        .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 6000, 0), 1)
        .nutrition(4)
        .saturationMod(1.2f)
        .alwaysEat()
        .build();

    // Ruby Apple
    public static final FoodProperties RUBY_APPLE = new FoodProperties.Builder()
        .effect(new MobEffectInstance(MobEffects.REGENERATION, 7000, 0), 1)
        .effect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, 6), 1)
        .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 4800, 0), 1)
        .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4800, 0), 1)
        .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3600, 0), 1)
        .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 0), 1)
        .nutrition(4)
        .saturationMod(1.2f)
        .alwaysEat()
        .build();

    // Fire APPLE
    public static final FoodProperties FIRE_APPLE = new FoodProperties.Builder()
        .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 4800, 2), 1)
        .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1)
        .nutrition(4)
        .saturationMod(1.2f)
        .alwaysEat()
        .build();

}
