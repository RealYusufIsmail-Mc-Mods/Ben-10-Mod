package io.github.realyusufismail.bentenmod.entity;

import io.github.realyusufismail.bentenmod.core.item.NullVoidProjectorItem;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/** Server-authoritative Null Void portal with a short open, hold and close cycle. */
public final class NullVoidPortalEntity extends Entity {
    public static final int LIFETIME_TICKS = 200;
    private static final int OPEN_TICKS = 10;
    private static final int CLOSE_START = LIFETIME_TICKS - OPEN_TICKS;
    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData
        .defineId(NullVoidPortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final DustParticleOptions PURPLE_DUST =
            new DustParticleOptions(new Vector3f(0.68F, 0.18F, 0.95F), 1.0F);
    private final Set<UUID> transferred = new HashSet<>();

    public NullVoidPortalEntity(EntityType<? extends NullVoidPortalEntity> type, Level level) {
        super(type, level);
        setNoGravity(true);
    }

    public void setOwner(UUID ownerId) {
        entityData.set(OWNER, Optional.of(ownerId));
    }

    public Optional<UUID> ownerId() {
        return entityData.get(OWNER);
    }

    public float visualScale(float partialTick) {
        float age = tickCount + partialTick;
        float opening = Math.min(1.0F, age / OPEN_TICKS);
        float closing = Math.min(1.0F, Math.max(0.0F, (LIFETIME_TICKS - age) / OPEN_TICKS));
        float pulse = 0.96F + 0.04F * (float) Math.sin(age * 0.16F);
        return Math.min(opening, closing) * pulse;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(OWNER, Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("owner")) {
            setOwner(tag.getUUID("owner"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        ownerId().ifPresent(owner -> tag.putUUID("owner", owner));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            clientParticles();
            return;
        }
        if (tickCount >= LIFETIME_TICKS) {
            level().playSound(null, blockPosition(), SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS,
                    0.7F, 0.72F);
            discard();
            return;
        }
        if (tickCount > OPEN_TICKS && tickCount < CLOSE_START) {
            if (tickCount % 40 == 0) {
                level().playSound(null, blockPosition(), SoundEvents.PORTAL_AMBIENT,
                        SoundSource.BLOCKS, 0.4F, 0.9F + random.nextFloat() * 0.2F);
            }
            pullNearbyEntities();
        }
    }

    private void clientParticles() {
        if (tickCount < CLOSE_START && random.nextInt(2) == 0) {
            double x = getX() + (random.nextDouble() - 0.5) * 2.2;
            double y = getY() + random.nextDouble() * 3.0;
            double z = getZ() + (random.nextDouble() - 0.5) * 0.18;
            level().addParticle(
                    random.nextBoolean() ? ParticleTypes.PORTAL : ParticleTypes.REVERSE_PORTAL, x,
                    y, z, (getX() - x) * 0.04, (getY() + 1.5 - y) * 0.04, (getZ() - z) * 0.04);
            level().addParticle(PURPLE_DUST, x, y, z, (getX() - x) * 0.02,
                    (getY() + 1.5 - y) * 0.02, (getZ() - z) * 0.02);
        }
    }

    private void pullNearbyEntities() {
        AABB area = getBoundingBox().inflate(5.0);
        for (Entity entity : level().getEntities(this, area,
                candidate -> candidate.isAlive() && (candidate instanceof Player
                        || candidate instanceof Mob || candidate instanceof ItemEntity))) {
            if (excluded(entity)) {
                continue;
            }
            if (isBoss(entity)) {
                Vec3 away = entity.position().subtract(position()).normalize();
                entity.push(away.x * 0.08, 0.03, away.z * 0.08);
                entity.hurtMarked = true;
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, entity.getX(),
                            entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 8, 0.35,
                            0.45, 0.35, 0.04);
                }
                continue;
            }
            Vec3 toward = position().add(0, 1.5, 0).subtract(entity.getBoundingBox().getCenter());
            double distance = toward.length();
            if (distance < 0.001 || distance > 5.0) {
                continue;
            }
            double closeness = 1.0 - distance / 5.0;
            double strength = 0.025 + closeness * closeness * 0.22;
            Vec3 pull = toward.normalize().scale(strength);
            entity.setDeltaMovement(entity.getDeltaMovement().add(pull));
            entity.hurtMarked = true;
            if (getBoundingBox().inflate(0.15).intersects(entity.getBoundingBox())) {
                transfer(entity);
            }
        }
    }

    private boolean excluded(Entity entity) {
        if (entity instanceof Player player && player.isSpectator()) {
            return true;
        }
        if (tickCount < 20 && ownerId().filter(id -> id.equals(entity.getUUID())).isPresent()) {
            return true;
        }
        return entity instanceof OwnableEntity ownable
                && ownerId().filter(id -> id.equals(ownable.getOwnerUUID())).isPresent();
    }

    private void transfer(Entity entity) {
        if (isBoss(entity) || transferred.contains(entity.getUUID())) {
            return;
        }
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        boolean moved;
        if (entity instanceof ServerPlayer player) {
            moved = NullVoidProjectorItem.transferPlayer(player);
        } else {
            moved = NullVoidProjectorItem.banishEntity(serverLevel, entity);
        }
        if (moved) {
            transferred.add(entity.getUUID());
        }
    }

    private static boolean isBoss(Entity entity) {
        return entity instanceof VilgaxEntity || entity instanceof ZsSkayrEntity
                || entity instanceof PrisonWardenEntity || entity instanceof CrabEntity;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
