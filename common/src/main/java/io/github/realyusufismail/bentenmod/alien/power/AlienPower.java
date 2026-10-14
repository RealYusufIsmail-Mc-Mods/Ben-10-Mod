package io.github.realyusufismail.bentenmod.alien.power;

import net.minecraft.resources.ResourceLocation;

/** An alien's data-driven active power and its server-side cooldown. */
public record AlienPower(ResourceLocation type, int cooldownTicks) {
}
