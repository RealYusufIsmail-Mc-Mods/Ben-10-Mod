package io.github.realyusufismail.bentenmod.mixin;

import io.github.realyusufismail.bentenmod.omnitrix.ClientOmnitrixState;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Keeps JSON alien dimensions when vanilla refreshes a player's pose or bounding box. */
@Mixin(Entity.class)
public abstract class EntityDimensionsMixin {
    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void bentenmod$applyAlienDimensions(Pose pose,
            CallbackInfoReturnable<EntityDimensions> callback) {
        if (!((Object) this instanceof Player player)) {
            return;
        }
        float width;
        float height;
        if (player instanceof ServerPlayer serverPlayer) {
            var definition = OmnitrixService.activeDefinition(serverPlayer).orElse(null);
            if (definition == null) {
                return;
            }
            width = definition.width();
            height = definition.height();
        } else {
            OmnitrixSnapshot snapshot = ClientOmnitrixState.get(player.getUUID());
            if (snapshot == null || snapshot.currentAlien() == null || snapshot.width() <= 0
                    || snapshot.height() <= 0) {
                return;
            }
            width = snapshot.width();
            height = snapshot.height();
        }
        callback.setReturnValue(EntityDimensions.scalable(width, height));
    }
}
