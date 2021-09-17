package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;

public class LegendaryArmor extends ArmorItem implements IForgeItem {

    public LegendaryArmor(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builder) {

        super(materialIn, slot, builder);

    }

    @Override
    public void ArmorItem(ItemStack stack, WorldlyContainer world, Player player) {
        ItemStack boot = player.getItemBySlot(EquipmentSlot.FEET);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        if (boot.getItem() == ItemInit.BOOTS.get() && legs.getItem() == ItemInit.LEGGINGS.get() && chest.getItem() == ItemInit.CHESTPLATE.get() && helmet.getItem() == ItemInit.HELMET.get())
            ;
        {
            player.addEffect(new EffectInstance(Effect.DAMAGE_BOOST, 100, 1, false, false, true));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 2, false, false, true));
        }
    }
}

