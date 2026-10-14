package io.github.realyusufismail.bentenmod.alien.power;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

/** A dispatch id and codec for an active power definition. */
public record PowerType(ResourceLocation id, Codec<AlienPower> codec) {
}
