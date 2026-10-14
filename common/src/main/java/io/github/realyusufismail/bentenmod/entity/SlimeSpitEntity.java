package io.github.realyusufismail.bentenmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class SlimeSpitEntity extends ThrowableItemProjectile {
    public SlimeSpitEntity(EntityType<? extends SlimeSpitEntity> type, Level level) {
        super(type, level);
    }

    public SlimeSpitEntity(EntityType<? extends SlimeSpitEntity> type, LivingEntity owner,
            Level level) {
        super(type, owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target
                && target != getOwner()) {
            target.hurt(this.level().damageSources().magic(), 2.0F);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0));
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            discard();
        }
    }
}
