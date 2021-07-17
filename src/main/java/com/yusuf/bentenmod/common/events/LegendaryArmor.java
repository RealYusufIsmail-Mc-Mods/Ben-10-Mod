package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.core.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public class LegendaryArmor extends ArmorItem implements IForgeItem {

    public LegendaryArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {

        super(materialIn, slot, builder);

    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        ItemStack boot = player.getItemBySlot(EquipmentSlotType.FEET);
        ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlotType.LEGS);
        ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);

        if (boot.getItem() == ItemInit.BOOTS.get() && legs.getItem() == ItemInit.LEGGINGS.get() && chest.getItem() == ItemInit.CHESTPLATE.get() && helmet.getItem() == ItemInit.HELMET.get())
            ;
        {
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 100, 1, false, false, true));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 2, false, false, true));
        }
    }
}

