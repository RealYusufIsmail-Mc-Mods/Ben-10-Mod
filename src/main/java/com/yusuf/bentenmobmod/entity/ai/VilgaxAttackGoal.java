package com.yusuf.bentenmobmod.entity.ai;


import com.yusuf.bentenmobmod.entity.VilgaxEntity;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class VilgaxAttackGoal extends MeleeAttackGoal {
   private final VilgaxEntity vilgax;
   private int raiseArmTicks;

   public VilgaxAttackGoal(VilgaxEntity vilgax, double p_i46803_2_, boolean p_i46803_4_) {
      super(vilgax, p_i46803_2_, p_i46803_4_);
      this.vilgax = vilgax;
   }

   public void startExecuting() {
      super.startExecuting();
      this.raiseArmTicks = 0;
   }

   public void resetTask() {
      super.resetTask();
      this.vilgax.setAggroed(false);
   }

   public void tick() {
      super.tick();
      ++this.raiseArmTicks;
      if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
         this.vilgax.setAggroed(true);
      } else {
         this.vilgax.setAggroed(false);
      }

   }
}