/*
 * Copyright 2026 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.bentenmod.common.events;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.capability.CapabilityHandler;
import io.github.realyusufismail.bentenmod.core.init.KeybindsInit;
import io.github.realyusufismail.bentenmod.core.network.CRevertPacket;
import io.github.realyusufismail.bentenmod.core.network.PacketHandler;
import io.github.realyusufismail.bentenmod.core.omnitrix.OmnitrixItem;
import io.github.realyusufismail.bentenmod.core.omnitrix.OmnitrixScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        handleInput(mc);
    }

    private static void handleInput(Minecraft mc) {
        if (mc.player == null) return;

        if (KeybindsInit.OMNITRIX_KEY.consumeClick()) {
            if (OmnitrixItem.hasOmnitrix(mc.player)) {
                mc.setScreen(new OmnitrixScreen(new StringTextComponent("Omnitrix")));
            }
        }

        if (KeybindsInit.REVERT_KEY.consumeClick()) {
            mc.player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
                if (data.isTransformed()) {
                    PacketHandler.CHANNEL.sendToServer(new CRevertPacket());
                }
            });
        }
    }
}
