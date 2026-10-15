/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    public void vilgaxHasAttributes(GameTestHelper helper) {
        GameTestChecks.vilgaxHasAttributes(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void flyingMobsCanFly(GameTestHelper helper) {
        GameTestChecks.flyingMobsCanFly(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void livingNullVoidMobsHaveAttributes(GameTestHelper helper) {
        GameTestChecks.livingNullVoidMobsHaveAttributes(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void nullVoidLootTablesContainDrops(GameTestHelper helper) {
        GameTestChecks.nullVoidLootTablesContainDrops(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void prisonVaultUsesKeyAndOpens(GameTestHelper helper) {
        GameTestChecks.prisonVaultUsesKeyAndOpens(helper,
                player(helper, "prisonVaultUsesKeyAndOpens"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void nullVoidGliderSlowsFalling(GameTestHelper helper) {
        GameTestChecks.nullVoidGliderSlowsFalling(helper,
                player(helper, "nullVoidGliderSlowsFalling"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void plumberBlasterRecipeLoads(GameTestHelper helper) {
        GameTestChecks.plumberBlasterRecipeLoads(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 500)
    public void prisonWardenRespectsPrisonerCap(GameTestHelper helper) {
        GameTestChecks.prisonWardenRespectsPrisonerCap(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void vilgaxSwitchesPhaseAtHalfHealth(GameTestHelper helper) {
        GameTestChecks.vilgaxSwitchesPhaseAtHalfHealth(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void kraabLootDropsClaw(GameTestHelper helper) {
        GameTestChecks.kraabLootDropsClaw(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void vilgaxLootDropsCore(GameTestHelper helper) {
        GameTestChecks.vilgaxLootDropsCore(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void vilgaxBeaconRecipeLoads(GameTestHelper helper) {
        GameTestChecks.vilgaxBeaconRecipeLoads(helper);
    }

    // The dimension itself and the projector round trip are checked on Forge only: this test server
    // never
    // loads datapack dimensions (see GameTestChecks.nullVoidWorldgenDataLoaded).
    @GameTest(template = EMPTY_STRUCTURE)
    public void nullVoidWorldgenDataLoaded(GameTestHelper helper) {
        GameTestChecks.nullVoidWorldgenDataLoaded(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void zsSkayrAttributesAndLoot(GameTestHelper helper) {
        GameTestChecks.zsSkayrAttributesAndLoot(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void masterControlUnlocksAliensAndDisablesTimeout(GameTestHelper helper) {
        GameTestChecks.masterControlUnlocksAliensAndDisablesTimeout(helper,
                player(helper, "masterControlUnlocksAliensAndDisablesTimeout"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void masterControlRequiresWatch(GameTestHelper helper) {
        GameTestChecks.masterControlRequiresWatch(helper,
                player(helper, "masterControlRequiresWatch"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void nullCrystalOreDropsCrystal(GameTestHelper helper) {
        GameTestChecks.nullCrystalOreDropsCrystal(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void nullVoidProjectorRecipeLoads(GameTestHelper helper) {
        GameTestChecks.nullVoidProjectorRecipeLoads(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void nullVoidFortressChestLoot(GameTestHelper helper) {
        GameTestChecks.nullVoidFortressChestLoot(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void hunterSpawnSchedulePersistsAndIsOneShot(GameTestHelper helper) {
        GameTestChecks.hunterSpawnSchedulePersistsAndIsOneShot(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void equippedPlayerMigrationSchedulesKraab(GameTestHelper helper) {
        GameTestChecks.equippedPlayerMigrationSchedulesKraab(helper,
                player(helper, "equippedPlayerMigrationSchedulesKraab"));
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
    public void heatblastPowerSpawnsFireball(GameTestHelper helper) {
        GameTestChecks.heatblastPowerSpawnsFireball(helper,
                player(helper, "heatblastPowerSpawnsFireball"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void alienPowerCooldownBlocksImmediateUse(GameTestHelper helper) {
        GameTestChecks.alienPowerCooldownBlocksImmediateUse(helper,
                player(helper, "alienPowerCooldownBlocksImmediateUse"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void alienEyeHeightTracksTransformation(GameTestHelper helper) {
        GameTestChecks.alienEyeHeightTracksTransformation(helper,
                player(helper, "alienEyeHeightTracksTransformation"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void heatblastFireballDamagesVilgax(GameTestHelper helper) {
        GameTestChecks.heatblastFireballDamagesVilgax(helper,
                player(helper, "heatblastFireballDamagesVilgax"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void fourArmsSlamDamagesVilgax(GameTestHelper helper) {
        GameTestChecks.fourArmsSlamDamagesVilgax(helper,
                player(helper, "fourArmsSlamDamagesVilgax"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void armorLayerTexturesExist(GameTestHelper helper) {
        GameTestChecks.armorLayerTexturesExist(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void plumberBoltDealsFixedDamage(GameTestHelper helper) {
        GameTestChecks.plumberBoltDealsFixedDamage(helper,
                player(helper, "plumberBoltDealsFixedDamage"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void nullVoidProjectorOpensPortal(GameTestHelper helper) {
        GameTestChecks.nullVoidProjectorOpensPortal(helper,
                player(helper, "nullVoidProjectorOpensPortal"));
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 80)
    public void nullVoidPortalPullsZombie(GameTestHelper helper) {
        GameTestChecks.nullVoidPortalPullsZombie(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 80)
    public void nullVoidPortalResistsBosses(GameTestHelper helper) {
        GameTestChecks.nullVoidPortalResistsBosses(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE, timeoutTicks = 240)
    public void nullVoidPortalCloses(GameTestHelper helper) {
        GameTestChecks.nullVoidPortalCloses(helper);
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void bossChatterRespectsCooldown(GameTestHelper helper) {
        GameTestChecks.bossChatterRespectsCooldown(helper,
                player(helper, "bossChatterRespectsCooldown"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void creativeCanTransformWithoutUnlocking(GameTestHelper helper) {
        GameTestChecks.creativeCanTransformWithoutUnlocking(helper,
                player(helper, "creativeCanTransformWithoutUnlocking"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void timeoutStartsRechargeAndBlocksTransform(GameTestHelper helper) {
        GameTestChecks.timeoutStartsRechargeAndBlocksTransform(helper,
                player(helper, "timeoutStartsRechargeAndBlocksTransform"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void manualRevertSkipsRecharge(GameTestHelper helper) {
        GameTestChecks.manualRevertSkipsRecharge(helper,
                player(helper, "manualRevertSkipsRecharge"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void deathRevertsToHuman(GameTestHelper helper) {
        GameTestChecks.deathRevertsToHuman(helper, player(helper, "deathRevertsToHuman"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void masterControlHasNoRecharge(GameTestHelper helper) {
        GameTestChecks.masterControlHasNoRecharge(helper,
                player(helper, "masterControlHasNoRecharge"));
    }

    @GameTest(template = EMPTY_STRUCTURE)
    public void guideIsGivenOnceOnJoin(GameTestHelper helper) {
        GameTestChecks.guideIsGivenOnceOnJoin(helper, player(helper, "guideIsGivenOnceOnJoin"));
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
