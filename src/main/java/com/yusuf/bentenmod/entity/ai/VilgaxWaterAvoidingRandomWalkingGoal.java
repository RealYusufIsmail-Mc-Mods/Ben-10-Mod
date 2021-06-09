package com.yusuf.bentenmod.entity.ai;

import com.yusuf.bentenmod.entity.VilgaxEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;


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


@Override
    protected Vector3d getPosition() {
        if (this.vilgax.isInWaterOrBubble()) {
            Vector3d vector3d = RandomPositionGenerator.getLandPos(this.vilgax, 15, 7);
            return vector3d == null ? super.getPosition() : vector3d;
        } else {
            return this.vilgax.getRandom().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.vilgax, 10, 7) : super.getPosition();
        }
    }

}
