/*
 * Copyright 2023 RealYusufIsmail.
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
package com.yusuf.bentenmod.common.events;

import com.yusuf.bentenmod.BenTenMod;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BenTenMod.MOD_ID)
public class PlayerJoinHandler {

    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent evt) {

        /* Advancements are not synced immediately on login, but on the player's first server tick.
         * Thus, there is a short time clientside where the world is active and rendering but book contents
         * are not loaded (because we load when advancements do).
         * This can cause crashes for multiple reasons.
         * Since this method is idempotent and the advancements are already loaded serverside, it should be safe
         * to call this a bit earlier than vanilla does.
         * Ideally, we want to make it so that any attempts to access the books cleanly receive dummy/blank data
         * until the contents load, but alas here we are.
         * TODO: Do that^
         */
        ((ServerPlayerEntity) evt.getPlayer()).getAdvancements().flushDirty((ServerPlayerEntity) evt.getPlayer());
    }
}
