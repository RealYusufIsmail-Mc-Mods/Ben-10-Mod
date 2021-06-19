package com.yusuf.bentenmod.entity.ai;


import com.yusuf.bentenmod.entity.VilgaxEntity;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class VilgaxAttackGoal extends MeleeAttackGoal {
   private final VilgaxEntity vilgax;
   private int raiseArmTicks;

   public VilgaxAttackGoal(VilgaxEntity vilgax, double p_i46803_2_, boolean p_i46803_4_) {
      super(vilgax, p_i46803_2_, p_i46803_4_);
      this.vilgax = vilgax;
   }

   public void start() {
      super.start();
      this.raiseArmTicks = 0;
   }

   public void stop() {
      super.stop();
      this.vilgax.setAggressive(false);
   }

   public void tick() {
      super.tick();
      ++this.raiseArmTicks;
       this.vilgax.setAggressive(this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);

   }
}