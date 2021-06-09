package com.yusuf.bentenmod.entity.ai;

import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;


public class VilgaxWaterAvoidingRandomWalkingGoal extends RandomWalkingGoal {
    private VilgaxEntity vilgax;

    protected final float probability;




    public VilgaxWaterAvoidingRandomWalkingGoal(VilgaxEntity vilgax, double p_i47301_2_) {
        this(vilgax, p_i47301_2_, 0.001F);
    }

    public VilgaxWaterAvoidingRandomWalkingGoal(VilgaxEntity vilgax, double p_i47302_2_, float p_i47302_4_) {
        super(vilgax, p_i47302_2_);
        this.probability = p_i47302_4_;
    }




}
