package com.yusuf.bentenmobmod.core.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
public class FoodInit {
	  // Omnitrix Apple
      @Deprecated
      public static final Food OMNITRIX_APPLE = new Food.Builder()
            .effect(new EffectInstance(Effects.REGENERATION, 1200, 0), 1)
            .effect(new EffectInstance(Effects.ABSORPTION, 3600, 1), 1)
            .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 2400, 0), 1)
            .effect(new EffectInstance(Effects.SPEED, 600, 0), 1)
            .effect(new EffectInstance(Effects.RESISTANCE, 1200, 0), 1)
            .effect(new EffectInstance(Effects.WATER_BREATHING, 600, 0), 1)
            .effect(new EffectInstance(Effects.HASTE, 2400, 0), 1)
            .hunger(4).saturation(1.2f).setAlwaysEdible().build();
    // Enchanted Omnitrix Apple
    public static final Food ENCHANTED_OMNITRIX_APPLE = new Food.Builder()
            .effect(new EffectInstance(Effects.REGENERATION, 6000, 0), 1)
            .effect(new EffectInstance(Effects.ABSORPTION, 4800, 3), 1)
            .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 4800, 0), 1)
            .effect(new EffectInstance(Effects.SPEED, 4800, 0), 1)
            .effect(new EffectInstance(Effects.WATER_BREATHING, 3600, 0), 1)
            .effect(new EffectInstance(Effects.HASTE, 6000, 0), 1)
            .effect(new EffectInstance(Effects.NIGHT_VISION, 2400, 0), 1)
            .effect(new EffectInstance(Effects.SLOW_FALLING, 6000, 0), 1)
            .hunger(4).saturation(1.2f).setAlwaysEdible().build();
    
 // Ruby  Apple
    public static final Food RUBY_APPLE = new Food.Builder()
            .effect(new EffectInstance(Effects.REGENERATION, 900000, 0), 1)
            .effect(new EffectInstance(Effects.ABSORPTION, 6000, 6), 1)
            .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 4800, 0), 1)
            .effect(new EffectInstance(Effects.SPEED, 4800, 0), 1)
            .effect(new EffectInstance(Effects.WATER_BREATHING, 3600, 0), 1)
            .effect(new EffectInstance(Effects.HASTE, 6000, 0), 1)
            .hunger(4).saturation(1.2f).setAlwaysEdible().build();



    //Fire APPLE
public static final Food FIRE_APPLE = new Food.Builder()
.effect(new EffectInstance(Effects.ABSORPTION, 4800, 2), 1)
.effect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0), 1)
.hunger(4).saturation(1.2f).setAlwaysEdible().build();
    		 
    		 
    				 
}