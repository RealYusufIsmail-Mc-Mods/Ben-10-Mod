/*
package com.yusuf.bentenmod.core.machine.alientable;

import com.yusuf.bentenmod.common.LangKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class AlienTableSimpleNamedContainerProvider implements INamedContainerProvider {
    private final IContainerProvider menuConstructor;


    public AlienTableSimpleNamedContainerProvider(IContainerProvider p_i50396_1_, ITextComponent ALIEN_TABLE_SCREEN) {
        this.menuConstructor = p_i50396_1_;

    }
    @Override
    public ITextComponent getDisplayName() {
        return LangKeys.ALIEN_TABLE_SCREEN;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return this.menuConstructor.createMenu(p_createMenu_1_, p_createMenu_2_, p_createMenu_3_);
    }
}
 */