package com.yusuf.bentenmod.backpack;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import com.yusuf.bentenmod.gui.FilterContainer;
import com.yusuf.bentenmod.gui.SBContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.yusuf.realyusufismailcore.network.ToggleMessageMessage;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BackpackItem extends Item{
    public Backpack backpack;

    public BackpackItem(Backpack backpack) {
        super(new Item.Properties().tab(MainItemGroup.MAIN));
        this.backpack = backpack;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return this.backpack.rarity;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
            if (!worldIn.isClientSide) {
            if (playerIn.isShiftKeyDown()) {
                //filter
                playerIn.openMenu(new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) ->
                        new FilterContainer(windowId, playerInventory, null), new StringTextComponent("Backpack Filter")));

            } else {
                //open
                playerIn.openMenu(new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) ->
                        new SBContainer(windowId, playerInventory, null), playerIn.getItemInHand(handIn).getDisplayName()));
            }
        }
        return ActionResult.success(playerIn.getItemInHand(handIn));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BackpackProvider(stack, this.backpack.slots); //stack, this.backpack.slots, nbt);
    }

    public void togglePickup(PlayerEntity playerEntity, ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();

        boolean Pickup = !nbt.getBoolean("Pickup");

        nbt.putBoolean("Pickup", Pickup);
        if (playerEntity instanceof ServerPlayerEntity)
            Main.network.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) playerEntity), new ToggleMessageMessage(Pickup));
        else
            playerEntity.displayClientMessage(new StringTextComponent(I18n.get(Pickup?"bentenmod.autopickupenabled":"bentenmod.autopickupdisabled")), true);

    }

    public boolean filterItem(ItemStack item, ItemStack packItem) {
        int filterOpts = packItem.getOrCreateTag().getInt("Filter-OPT");
        boolean whitelist = (filterOpts & 1) > 0;
        boolean nbtMatch = (filterOpts & 2) > 0;

        return packItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .map(cap -> {
                    BackpackFilterHandler handler = ((BackpackItemHandler)cap).getFilterHandler();
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack stack = handler.getStackInSlot(i);

                        if (!stack.isEmpty()) {
                            if (stack.sameItem(item)) {
                                if (nbtMatch)
                                    return ItemStack.tagMatches(stack, item) == whitelist;
                                else
                                    return whitelist;
                            }
                        }
                    }

                    return !whitelist;
                }).orElse(false);
    }

    public boolean pickupEvent(ItemStack stack, ItemStack backpack) {
        if (!backpack.getOrCreateTag().getBoolean("Pickup"))
            return false;

        return backpack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .map(handler -> {
                    if (!(handler instanceof BackpackItem))
                        return false;

                    if (!filterItem(stack, backpack))
                        return false;

                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack slot = handler.getStackInSlot(i);
                        if (slot.isEmpty() || (ItemHandlerHelper.canItemStacksStack(slot, stack) && slot.getCount() < slot.getMaxStackSize() && slot.getCount() < handler.getSlotLimit(i))) {
                            ItemStack remainder = handler.insertItem(i, stack.copy(), false);
                            stack.setCount(remainder.getCount());
                            if (remainder.getCount() == 0)
                                break;
                        }
                    }

                    return stack.isEmpty();
                }).orElse(false);
    }


    private boolean hasTranslation(String key) {
        return !I18n.get(key).equals(key);
    }

    private String fallbackString(String key, String fallback) {
        String tmp = I18n.get(key);
        return tmp.equals(key)?fallback:tmp;
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        String translationKey = getDescriptionId();

        boolean pickupEnabled = stack.getOrCreateTag().getBoolean("Pickup");
        if (pickupEnabled)
            tooltip.add(new StringTextComponent(I18n.get("bentenmod.autopickupenabled")));
        else
            tooltip.add(new StringTextComponent(I18n.get("bentenmod.autopickupdisabled")));

        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent( I18n.get( translationKey + ".info") ));
            if (hasTranslation(translationKey + ".info2"))
                tooltip.add(new StringTextComponent( I18n.get(translationKey + ".info2")));
            if (hasTranslation(translationKey + ".info3"))
                tooltip.add(new StringTextComponent( I18n.get(translationKey + ".info3")));
        }
        else {
            tooltip.add(new StringTextComponent( fallbackString("simplybackpacks.shift", "Press <§6§oShift§r> for info.") ));
        }
    }


    @Nonnull
    public static ItemStack findBackpack(PlayerEntity player) {
        return findBackpack(player, false);
    }

    @Nonnull
    public static ItemStack findBackpack(PlayerEntity player, boolean justHotbar) {
        // Search order defined here, first search the players hands
        if (isBackpack(player.getMainHandItem())) {
            return player.getMainHandItem();
        }

        if (isBackpack(player.getMainHandItem())) {
            return player.getMainHandItem();
        }

        if (Main.curiosLoaded) {
            ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(BackpackItem::isBackpack, player)
                    .map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

            if (!stack.isEmpty())
                return stack;
        }

        for (int i = 0; i < (justHotbar ? 9 : player.inventory.items.size()); i++) {
            if (isBackpack(player.inventory.getItem(i))) {
                return player.inventory.getItem(i);
            }
        }

        return ItemStack.EMPTY;
    }

    public static boolean isBackpack(ItemStack stack) {
        return stack.getItem() instanceof BackpackItem;
    }
}
