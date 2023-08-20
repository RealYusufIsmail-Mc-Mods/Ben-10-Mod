/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.omnitrix;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.util.Keybinds;
import lombok.val;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class OmnitrixScreen extends Screen {
    private final Player player;

    public static final ResourceLocation SCREEN_ID =
            new ResourceLocation(BenTenMod.MOD_ID, "textures/gui/omnitrix.png");

    protected OmnitrixScreen(Component p_96550_, Player player) {
        super(p_96550_);
        this.player = player;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        super.keyPressed(pKeyCode, pScanCode, pModifiers);
        // what are the key codes. can you give me a repo link?

        val keybinds = Keybinds.getKeybind(pKeyCode);
        switch (keybinds) {
            case NUM_0 -> {
                transform(Ben10Aliens.HEATBLAST);
                return true;
            }
            case NUM_1 -> {
                transform(Ben10Aliens.FOURARMS);
                return true;
            }
            case NUM_2 -> {
                transform(Ben10Aliens.XLR8);
                return true;
            }
            case NUM_3 -> {
                transform(Ben10Aliens.DIAMONDHEAD);
                return true;
            }
            case NUM_4 -> {
                transform(Ben10Aliens.WILDMUTT);
                return true;
            }
            case NUM_5 -> {
                transform(Ben10Aliens.STINKFLY);
                return true;
            }
            case NUM_6 -> {
                transform(Ben10Aliens.UPGRADE);
                return true;
            }
            case NUM_7 -> {
                transform(Ben10Aliens.GREYMATTER);
                return true;
            }
            case NUM_8 -> {
                transform(Ben10Aliens.RIPJAWS);
                return true;
            }
            case NUM_9 -> {
                transform(Ben10Aliens.CANNONBOLT);
                return true;
            }
            default -> {
                return false;
            }
        }

    }

    private void transform(Ben10Aliens alien) {
        val omnitrix = OmnitrixWatch.getOmnitrix(player);
        omnitrix.transform(alien);
    }
}