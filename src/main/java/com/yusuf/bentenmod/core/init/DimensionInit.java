package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;

public class DimensionInit {
	public static final RegistryKey<DimensionType> VILGAX_DIMENSION = RegistryKey
			.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(BenTenMod.MOD_ID, "vilgax_dimension"));
	
//	public static final RegistryKey<World> DUNGEON_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
//			new ResourceLocation(BenTenMod.MOD_ID, "vilgax"));
	
}

