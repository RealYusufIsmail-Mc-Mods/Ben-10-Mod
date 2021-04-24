package com.Yusuf.bentenmobmod.common.events;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModHooks {

	public static ItemStack onHeatblastSwordCrafted(PlayerEntity player, @Nonnull ItemStack output, @Nonnull ItemStack left,
			@Nonnull ItemStack right) {
		CraftingEventTrigger e = new CraftingEventTrigger(player, left, right, output);
		MinecraftForge.EVENT_BUS.post(e);
		return output;
	}
}
