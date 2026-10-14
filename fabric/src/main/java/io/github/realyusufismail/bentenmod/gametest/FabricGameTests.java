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

package io.github.realyusufismail.bentenmod.gametest;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

/** Fabric registration of {@link GameTestChecks} (entrypoint {@code fabric-gametest}). */
public class FabricGameTests implements FabricGameTest {

    @GameTest(template = EMPTY_STRUCTURE)
    public void crabHasAttributes(GameTestHelper helper) {
        GameTestChecks.crabHasAttributes(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void oreFeaturesLoaded(GameTestHelper helper) {
        GameTestChecks.oreFeaturesLoaded(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixCrashSiteFeatureRegistered(GameTestHelper helper) {
        GameTestChecks.omnitrixCrashSiteFeatureRegistered(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixPodDropsWatch(GameTestHelper helper) {
        GameTestChecks.omnitrixPodDropsWatch(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixCrafterWatchRecipeLoaded(GameTestHelper helper) {
        GameTestChecks.omnitrixCrafterWatchRecipeLoaded(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixScanUnlocksWildmutt(GameTestHelper helper) {
        GameTestChecks.omnitrixScanUnlocksWildmutt(helper,
                player(helper, "omnitrixScanUnlocksWildmutt"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixRepeatScanIsSafe(GameTestHelper helper) {
        GameTestChecks.omnitrixRepeatScanIsSafe(helper, player(helper, "omnitrixRepeatScanIsSafe"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixScanRequiresEquippedAndUntransformedPlayer(GameTestHelper helper) {
        GameTestChecks.omnitrixScanRequiresEquippedAndUntransformedPlayer(helper,
                player(helper, "omnitrixScanRequiresEquippedAndUntransformedPlayer"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixScanBuddingAmethystUnlocksDiamondhead(GameTestHelper helper) {
        GameTestChecks.omnitrixScanBuddingAmethystUnlocksDiamondhead(helper,
                player(helper, "omnitrixScanBuddingAmethystUnlocksDiamondhead"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void recipesLoaded(GameTestHelper helper) {
        GameTestChecks.recipesLoaded(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixCrafterPlaces(GameTestHelper helper) {
        GameTestChecks.omnitrixCrafterPlaces(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void aliensLoaded(GameTestHelper helper) {
        GameTestChecks.aliensLoaded(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void alienTransformAnimationIsPerPlayer(GameTestHelper helper) {
        GameTestChecks.alienTransformAnimationIsPerPlayer(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixTransformIsServerValidated(GameTestHelper helper) {
        GameTestChecks.omnitrixTransformIsServerValidated(helper,
                player(helper, "omnitrixTransformIsServerValidated"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixWatchEquipsAndConsumes(GameTestHelper helper) {
        GameTestChecks.omnitrixWatchEquipsAndConsumes(helper,
                player(helper, "omnitrixWatchEquipsAndConsumes"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixSecondUseDoesNotConsume(GameTestHelper helper) {
        GameTestChecks.omnitrixSecondUseDoesNotConsume(helper,
                player(helper, "omnitrixSecondUseDoesNotConsume"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixEquippedStateRoundTrips(GameTestHelper helper) {
        GameTestChecks.omnitrixEquippedStateRoundTrips(helper,
                player(helper, "omnitrixEquippedStateRoundTrips"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void omnitrixTimeoutReverts(GameTestHelper helper) {
        GameTestChecks.omnitrixTimeoutReverts(helper, player(helper, "omnitrixTimeoutReverts"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void heatblastSetGrantsAndRevokesFlight(GameTestHelper helper) {
        GameTestChecks.heatblastSetGrantsAndRevokesFlight(helper,
                player(helper, "heatblastSetGrantsAndRevokesFlight"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void fourArmsSetWorks(GameTestHelper helper) {
        GameTestChecks.fourArmsSetWorks(helper, player(helper, "fourArmsSetWorks"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void legendaryNeedsFullSet(GameTestHelper helper) {
        GameTestChecks.legendaryNeedsFullSet(helper, player(helper, "legendaryNeedsFullSet"));
    }

    private static ServerPlayer player(GameTestHelper helper, String name) {
        return FakePlayer.get(helper.getLevel(), new GameProfile(UUID.randomUUID(), name));
    }
}
