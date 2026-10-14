package io.github.realyusufismail.bentenmod.core.item;

import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlumberBlasterItem extends Item {
    public PlumberBlasterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
            InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition().add(look.scale(0.7));
        Arrow bolt = new Arrow(level, player);
        bolt.setPos(start.x, start.y, start.z);
        bolt.shoot(look.x, look.y, look.z, 3.8F, 0.25F);
        bolt.setBaseDamage(6.0);
        bolt.pickup = AbstractArrow.Pickup.DISALLOWED;
        level.addFreshEntity(bolt);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, start.x, start.y, start.z, 4, 0.025,
                    0.025, 0.025, 0.02);
        }
        level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS,
                0.55F, 1.5F);
        stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
        player.getCooldowns().addCooldown(this, 20);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemInit.TAYDENITE.get());
    }
}
