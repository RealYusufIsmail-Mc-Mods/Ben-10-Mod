package com.Yusuf.bentenmobmod.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
@SuppressWarnings("deprecation")
public class FoodInit {
    // Omnitrix Apple
    public static final Food OMNITRIX_APPLE = new Food.Builder()
            .effect(new EffectInstance(Effects.REGENERATION, 1200, 0), 1)
            .effect(new EffectInstance(Effects.ABSORPTION, 3600, 1), 1)
            .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 2400, 0), 1)
            .effect(new EffectInstance(Effects.MOVEMENT_SPEED, 600, 0), 1)
            .effect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1200, 0), 1)
            .effect(new EffectInstance(Effects.WATER_BREATHING, 600, 0), 1)
            .effect(new EffectInstance(Effects.DIG_SPEED, 2400, 0), 1)
            .nutrition(4).saturationMod(1.2f).alwaysEat().build();
    // Enchanted Omnitrix Apple
    public static final Food ENCHANTED_OMNITRIX_APPLE = new Food.Builder()
            .effect(new EffectInstance(Effects.REGENERATION, 6000, 0), 1)
            .effect(new EffectInstance(Effects.ABSORPTION, 4800, 3), 1)
            .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 4800, 0), 1)
            .effect(new EffectInstance(Effects.MOVEMENT_SPEED, 4800, 0), 1)
            .effect(new EffectInstance(Effects.WATER_BREATHING, 3600, 0), 1)
            .effect(new EffectInstance(Effects.DIG_SPEED, 6000, 0), 1)
            .effect(new EffectInstance(Effects.INVISIBILITY, 2400, 0), 1)
            .effect(new EffectInstance(Effects.SLOW_FALLING, 6000, 0), 1)
            .nutrition(4).saturationMod(1.2f).alwaysEat().build();

}
