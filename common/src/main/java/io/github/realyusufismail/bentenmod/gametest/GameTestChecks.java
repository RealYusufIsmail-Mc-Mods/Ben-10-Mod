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

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.ability.AbilityTicker;
import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.FeatureInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.init.RecipeTypeInit;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import io.github.realyusufismail.bentenmod.entity.ZsSkayrEntity;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixInteractions;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSavedData;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import io.github.realyusufismail.bentenmod.omnitrix.PlayerAnimationStateCache;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;
import io.github.realyusufismail.bentenmod.worldgen.ModOrePlacements;
import io.github.realyusufismail.bentenmod.worldgen.OmnitrixCrashSiteWorldgen;
import io.github.realyusufismail.bentenmod.worldgen.NullVoidWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * GameTest bodies shared by both loaders. Each loader has a thin test class that registers these
 * the way its GameTest API expects ({@code ForgeGameTests}, {@code FabricGameTests}).
 */
public final class GameTestChecks {
    private GameTestChecks() {}

    /** Entity attributes are registered through a different API on each loader. */
    public static void crabHasAttributes(GameTestHelper helper) {
        var crab = helper.spawn(EntityTypesInit.CRAB_ENTITY.get(), new BlockPos(1, 2, 1));
        helper.assertTrue(crab.getMaxHealth() == 80.0F, "Kraab should have 80 health");
        helper.assertTrue(crab.getAttributeValue(Attributes.ARMOR) >= 12.0,
                "Kraab should have mini-boss armour");
        helper.assertTrue(crab.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) >= 0.6,
                "Kraab should resist knockback");
        helper.succeed();
    }

    public static void vilgaxHasAttributes(GameTestHelper helper) {
        var vilgax = helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(1, 2, 1));
        var drone = helper.spawn(EntityTypesInit.VILGAX_DRONE_ENTITY.get(), new BlockPos(4, 2, 1));
        helper.assertTrue(vilgax.getMaxHealth() == 400.0F, "Vilgax should have 400 health");
        helper.assertTrue(vilgax.getAttributeValue(Attributes.ARMOR) >= 16.0,
                "Vilgax should have boss armour");
        helper.assertTrue(vilgax.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) >= 1.0,
                "Vilgax should resist knockback");
        helper.assertTrue(drone.getMaxHealth() == 16.0F, "Vilgax drones should have 16 health");
        helper.succeed();
    }

    public static void vilgaxSwitchesPhaseAtHalfHealth(GameTestHelper helper) {
        VilgaxEntity vilgax =
                helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(1, 2, 1));
        helper.assertTrue(vilgax.getPhase() == 1, "Vilgax should start in phase one");
        vilgax.setHealth(200.0F);
        vilgax.tick();
        helper.assertTrue(vilgax.getPhase() == 2, "Vilgax did not enter phase two at half health");
        helper.assertTrue(vilgax.hasEffect(MobEffects.MOVEMENT_SPEED),
                "phase two did not grant Speed");
        helper.assertTrue(vilgax.hasEffect(MobEffects.DAMAGE_RESISTANCE),
                "phase two did not grant Resistance");
        helper.succeed();
    }

    public static void kraabLootDropsClaw(GameTestHelper helper) {
        var kraab = helper.spawn(EntityTypesInit.CRAB_ENTITY.get(), new BlockPos(1, 2, 1));
        LootTable table = entityLootTable(helper, "entities/crab");
        helper.assertTrue(table != null, "Kraab loot table is missing");
        if (table == null) {
            return;
        }
        var drops = entityLoot(helper, kraab, table);
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(ItemInit.KRAAB_CLAW.get())),
                "Kraab loot table did not drop a claw");
        int clawCount = drops.stream()
            .filter(stack -> stack.is(ItemInit.KRAAB_CLAW.get()))
            .mapToInt(ItemStack::getCount)
            .sum();
        helper.assertTrue(clawCount >= 1 && clawCount <= 2,
                "Kraab must always drop one or two claws");
        helper.succeed();
    }

    public static void vilgaxLootDropsCore(GameTestHelper helper) {
        var vilgax = helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(1, 2, 1));
        LootTable table = entityLootTable(helper, "entities/vilgax");
        helper.assertTrue(table != null, "Vilgax loot table is missing");
        if (table == null) {
            return;
        }
        var drops = entityLoot(helper, vilgax, table);
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(ItemInit.VILGAX_CORE.get())),
                "Vilgax loot table is missing the Vilgax Core");
        helper.assertTrue(drops.stream()
            .filter(stack -> stack.is(ItemInit.VILGAX_CORE.get()))
            .mapToInt(ItemStack::getCount)
            .sum() == 1, "Vilgax must always drop exactly one core");
        helper.succeed();
    }

    public static void vilgaxBeaconRecipeLoads(GameTestHelper helper) {
        var recipe = helper.getLevel()
            .getRecipeManager()
            .byKey(BenTenMod.getModIdAndName("vilgax_beacon"));
        helper.assertTrue(recipe.isPresent(), "Vilgax beacon recipe is missing");
        if (recipe.isEmpty()) {
            return;
        }
        helper.assertTrue(recipe.orElseThrow()
            .getResultItem(helper.getLevel().registryAccess())
            .is(ItemInit.VILGAX_BEACON.get()), "Vilgax beacon recipe has the wrong result");
        helper.succeed();
    }

    public static void nullVoidDimensionExists(GameTestHelper helper) {
        ServerLevel level = helper.getLevel().getServer().getLevel(NullVoidWorldgen.DIMENSION);
        helper.assertTrue(level != null, "Null Void dimension did not load on the server");
        helper.succeed();
    }

    /**
     * Vanilla's GameTest server (used by Fabric) bakes its world from an empty level-stem registry,
     * so datapack dimensions never load there. This checks the parts it does load: the dimension
     * type, biome and noise settings.
     */
    public static void nullVoidWorldgenDataLoaded(GameTestHelper helper) {
        RegistryAccess registries = helper.getLevel().registryAccess();
        ResourceLocation id = BenTenMod.getModIdAndName("null_void");
        helper.assertTrue(registries.registryOrThrow(Registries.DIMENSION_TYPE).containsKey(id),
                "Null Void dimension type is missing");
        helper.assertTrue(registries.registryOrThrow(Registries.BIOME).containsKey(id),
                "Null Void biome is missing");
        helper.assertTrue(registries.registryOrThrow(Registries.NOISE_SETTINGS).containsKey(id),
                "Null Void noise settings are missing");
        helper.succeed();
    }

    public static void nullVoidProjectorRoundTrips(GameTestHelper helper, ServerPlayer player) {
        ServerLevel source = player.serverLevel();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        float yaw = player.getYRot();
        float pitch = player.getXRot();
        ItemStack projector = new ItemStack(ItemInit.NULL_VOID_PROJECTOR.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, projector);

        InteractionResult entered = ItemInit.NULL_VOID_PROJECTOR.get()
            .use(player.level(), player, InteractionHand.MAIN_HAND)
            .getResult();
        helper.assertTrue(entered.consumesAction(), "projector did not enter the Null Void");
        helper.assertTrue(player.serverLevel().dimension().equals(NullVoidWorldgen.DIMENSION),
                "projector entered the wrong dimension");
        OmnitrixSavedData.ReturnPoint point =
                OmnitrixSavedData.get(player.serverLevel()).returnPoint(player.getUUID());
        helper.assertTrue(point != null && point.dimension().equals(source.dimension().location()),
                "projector did not save the return dimension");

        player.getCooldowns().removeCooldown(ItemInit.NULL_VOID_PROJECTOR.get());
        InteractionResult returned = ItemInit.NULL_VOID_PROJECTOR.get()
            .use(player.level(), player, InteractionHand.MAIN_HAND)
            .getResult();
        helper.assertTrue(returned.consumesAction(), "projector did not return the player");
        helper.assertTrue(player.serverLevel() == source, "projector returned to the wrong level");
        helper.assertTrue(
                Math.abs(player.getX() - x) < 0.01 && Math.abs(player.getY() - y) < 0.01
                        && Math.abs(player.getZ() - z) < 0.01,
                "projector ignored the saved return point");
        helper.assertTrue(
                Math.abs(player.getYRot() - yaw) < 0.01
                        && Math.abs(player.getXRot() - pitch) < 0.01,
                "projector did not restore the saved rotation");
        helper.assertTrue(projector.getDamageValue() == 2, "projector did not cost durability");
        helper.succeed();
    }

    public static void zsSkayrAttributesAndLoot(GameTestHelper helper) {
        ZsSkayrEntity zsSkayr =
                helper.spawn(EntityTypesInit.ZS_SKAYR_ENTITY.get(), new BlockPos(1, 2, 1));
        helper.assertTrue(zsSkayr.getMaxHealth() == 500.0F, "Zs'Skayr should have 500 health");
        LootTable table = entityLootTable(helper, "entities/zs_skayr");
        helper.assertTrue(table != null, "Zs'Skayr loot table is missing");
        if (table == null) {
            return;
        }
        List<ItemStack> drops = entityLoot(helper, zsSkayr, table);
        int masterControls = drops.stream()
            .filter(stack -> stack.is(ItemInit.MASTER_CONTROL.get()))
            .mapToInt(ItemStack::getCount)
            .sum();
        helper.assertTrue(masterControls == 1,
                "Zs'Skayr must always drop exactly one Master Control");
        helper.succeed();
    }

    public static void masterControlUnlocksAliensAndDisablesTimeout(GameTestHelper helper,
            ServerPlayer player) {
        equipWithWatch(player);
        ItemStack masterControl = new ItemStack(ItemInit.MASTER_CONTROL.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, masterControl);
        InteractionResult result = ItemInit.MASTER_CONTROL.get()
            .use(player.level(), player, InteractionHand.MAIN_HAND)
            .getResult();
        helper.assertTrue(result.consumesAction(), "Master Control activation was refused");
        helper.assertTrue(masterControl.isEmpty(), "Master Control was not consumed");

        OmnitrixSnapshot state = OmnitrixService.snapshot(player);
        helper.assertTrue(state.timeoutDisabled(), "Master Control did not disable the timeout");
        helper.assertTrue(state.unlockedAlienIds().containsAll(Aliens.DATA.all().keySet()),
                "Master Control did not unlock every alien");
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Master Control did not allow transformation");
        helper.assertTrue(OmnitrixService.snapshot(player).expiresAt() == Long.MAX_VALUE,
                "Master Control transformation still has a timeout");
        helper.assertFalse(OmnitrixService.tick(player, Long.MAX_VALUE),
                "Master Control transformation expired");

        OmnitrixSavedData saved = OmnitrixSavedData.get(player.serverLevel());
        OmnitrixSavedData loaded = OmnitrixSavedData.load(saved.save(new CompoundTag()));
        helper.assertTrue(loaded.getOrCreate(player).timeoutDisabled(),
                "Master Control timeout flag did not persist");
        helper.succeed();
    }

    public static void masterControlRequiresWatch(GameTestHelper helper, ServerPlayer player) {
        ItemStack masterControl = new ItemStack(ItemInit.MASTER_CONTROL.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, masterControl);
        InteractionResult result = ItemInit.MASTER_CONTROL.get()
            .use(player.level(), player, InteractionHand.MAIN_HAND)
            .getResult();
        helper.assertFalse(result.consumesAction(), "Master Control activated without an Omnitrix");
        helper.assertTrue(masterControl.getCount() == 1,
                "Master Control was consumed without a watch");
        helper.assertFalse(OmnitrixService.snapshot(player).timeoutDisabled(),
                "timeout was disabled without an equipped Omnitrix");
        helper.succeed();
    }

    public static void nullCrystalOreDropsCrystal(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos, BlockInit.NULL_CRYSTAL_ORE.get());
        BlockPos absolute = helper.absolutePos(pos);
        ItemStack ironPickaxe = new ItemStack(Items.IRON_PICKAXE);
        List<ItemStack> drops = Block.getDrops(BlockInit.NULL_CRYSTAL_ORE.get().defaultBlockState(),
                helper.getLevel(), absolute, null, null, ironPickaxe);
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(ItemInit.NULL_CRYSTAL.get())),
                "Null Crystal Ore did not drop a Null Crystal");
        helper.succeed();
    }

    public static void nullVoidProjectorRecipeLoads(GameTestHelper helper) {
        var recipe = helper.getLevel()
            .getRecipeManager()
            .byKey(BenTenMod.getModIdAndName("null_void_projector"));
        helper.assertTrue(recipe.isPresent(), "Null Void Projector recipe is missing");
        if (recipe.isEmpty()) {
            return;
        }
        helper.assertTrue(
                recipe.orElseThrow()
                    .getResultItem(helper.getLevel().registryAccess())
                    .is(ItemInit.NULL_VOID_PROJECTOR.get()),
                "Null Void Projector recipe has the wrong result");
        helper.succeed();
    }

    public static void nullVoidFortressChestLoot(GameTestHelper helper) {
        LootTable table = entityLootTable(helper, "chests/null_void_fortress");
        helper.assertTrue(table != null, "Null Void fortress chest loot table is missing");
        if (table == null) {
            return;
        }
        LootParams params = new LootParams.Builder(helper.getLevel())
            .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
            .create(LootContextParamSets.CHEST);
        List<ItemStack> drops = table.getRandomItems(params);
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(ItemInit.NULL_CRYSTAL.get())),
                "Fortress chest loot is missing Null Crystals");
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(ItemInit.INFINITUM.get())),
                "Fortress chest loot is missing Infinitum");
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(Items.GOLDEN_APPLE)),
                "Fortress chest loot is missing golden apples");
        helper.succeed();
    }

    public static void hunterSpawnSchedulePersistsAndIsOneShot(GameTestHelper helper) {
        UUID playerId = UUID.randomUUID();
        OmnitrixSavedData data = new OmnitrixSavedData();
        helper.assertTrue(data.scheduleHunterSpawn(playerId, 6_000L),
                "first hunter spawn schedule was rejected");
        OmnitrixSavedData reloaded = OmnitrixSavedData.load(data.save(new CompoundTag()));
        helper.assertTrue(reloaded.hunterSpawnAt(playerId) == 6_000L,
                "hunter spawn schedule did not persist");
        helper.assertFalse(reloaded.scheduleHunterSpawn(playerId, 9_000L),
                "player received a duplicate hunter schedule");
        helper.assertTrue(reloaded.completeHunterSpawn(playerId),
                "first hunter spawn completion was rejected");
        OmnitrixSavedData afterSpawn = OmnitrixSavedData.load(reloaded.save(new CompoundTag()));
        helper.assertTrue(afterSpawn.hasSpawnedHunter(playerId),
                "hunter spawned flag did not persist");
        helper.assertFalse(afterSpawn.completeHunterSpawn(playerId),
                "player spawned more than one bounty hunter");
        helper.assertFalse(afterSpawn.scheduleHunterSpawn(playerId, 12_000L),
                "completed hunter schedule was recreated");
        helper.succeed();
    }

    public static void equippedPlayerMigrationSchedulesKraab(GameTestHelper helper,
            ServerPlayer player) {
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        helper.assertTrue(data.getOrCreate(player).equip(),
                "could not set up an equipped player without a hunter schedule");
        helper.assertTrue(data.hunterSpawnAt(player.getUUID()) < 0,
                "test player unexpectedly has a pending hunter schedule");

        OmnitrixService.onJoin(player);
        long now = player.serverLevel().getServer().overworld().getGameTime();
        long scheduledAt = data.hunterSpawnAt(player.getUUID());
        helper.assertTrue(scheduledAt >= now + 2_400L && scheduledAt <= now + 6_000L,
                "login migration did not schedule the Kraab hunter");
        OmnitrixService.onJoin(player);
        helper.assertTrue(data.hunterSpawnAt(player.getUUID()) == scheduledAt,
                "login migration replaced an existing hunter schedule");
        helper.succeed();
    }

    private static LootTable entityLootTable(GameTestHelper helper, String lootPath) {
        var lootId = new ResourceLocation(BenTenMod.MOD_ID, lootPath);
        return helper.getLevel()
            .getServer()
            .getLootData()
            .getElement(new LootDataId<>(LootDataType.TABLE, lootId));
    }

    private static java.util.List<ItemStack> entityLoot(GameTestHelper helper,
            net.minecraft.world.entity.Mob entity, LootTable table) {
        LootParams params = new LootParams.Builder(helper.getLevel())
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.ORIGIN, entity.position())
            .withParameter(LootContextParams.DAMAGE_SOURCE, entity.damageSources().generic())
            .create(LootContextParamSets.ENTITY);
        return table.getRandomItems(params);
    }

    /** Worldgen JSON is generated by the Forge data run and must ship in both jars. */
    public static void oreFeaturesLoaded(GameTestHelper helper) {
        var placed = helper.getLevel().registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Stream
            .concat(ModOrePlacements.OVERWORLD_ORES.stream(), ModOrePlacements.NETHER_ORES.stream())
            .forEach(key -> helper.assertTrue(placed.containsKey(key),
                    "missing placed feature " + key.location()));
        helper.assertTrue(placed.containsKey(NullVoidWorldgen.NULL_CRYSTAL_ORE_PLACED),
                "Null Void crystal ore placed feature is missing");
        helper.assertTrue(placed.containsKey(NullVoidWorldgen.FORTRESS_PLACED),
                "Null Void fortress placed feature is missing");
        helper.succeed();
    }

    public static void omnitrixCrashSiteFeatureRegistered(GameTestHelper helper) {
        var placed = helper.getLevel().registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        ResourceLocation featureId = BenTenMod.getModIdAndName("omnitrix_crash_site");
        helper.assertTrue(BuiltInRegistries.FEATURE.containsKey(featureId),
                "Omnitrix crash-site feature is not registered");
        helper.assertTrue(placed.containsKey(OmnitrixCrashSiteWorldgen.PLACED_FEATURE),
                "Omnitrix crash-site placed feature is missing");
        helper.succeed();
    }

    public static void omnitrixPodDropsWatch(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos, BlockInit.OMNITRIX_POD.get());
        var drops = Block.getDrops(BlockInit.OMNITRIX_POD.get().defaultBlockState(),
                helper.getLevel(), pos, null, null, ItemStack.EMPTY);
        helper.assertTrue(drops.size() == 1 && drops.get(0).is(ItemInit.OMNITRIX_WATCH.get()),
                "Omnitrix pod must drop exactly one watch");
        helper.succeed();
    }

    public static void omnitrixCrafterWatchRecipeLoaded(GameTestHelper helper) {
        var recipes = helper.getLevel()
            .getRecipeManager()
            .getAllRecipesFor(RecipeTypeInit.OMNITRIX_CRAFTER_TYPE.get());
        boolean watchRecipeLoaded = recipes.stream()
            .anyMatch(recipe -> recipe.getResultItem(helper.getLevel().registryAccess())
                .is(ItemInit.OMNITRIX_WATCH.get()));
        helper.assertTrue(watchRecipeLoaded,
                "Omnitrix watch recipe is missing from the Omnitrix Crafter");
        helper.succeed();
    }

    public static void omnitrixScanUnlocksWildmutt(GameTestHelper helper, ServerPlayer player) {
        Wolf wolf = helper.spawn(EntityType.WOLF, new BlockPos(1, 2, 1));
        equipWithWatch(player);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        player.setShiftKeyDown(true);
        ResourceLocation wildmutt = BenTenMod.getModIdAndName("wildmutt");

        InteractionResult result = OmnitrixInteractions.scanEntity(player, wolf);
        helper.assertTrue(result.consumesAction(), "wolf scan was not accepted");
        helper.assertTrue(OmnitrixService.snapshot(player).unlockedAlienIds().contains(wildmutt),
                "scanning a wolf did not unlock Wildmutt");
        helper.succeed();
    }

    public static void omnitrixRepeatScanIsSafe(GameTestHelper helper, ServerPlayer player) {
        Wolf wolf = helper.spawn(EntityType.WOLF, new BlockPos(1, 2, 1));
        equipWithWatch(player);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        player.setShiftKeyDown(true);
        ResourceLocation wildmutt = BenTenMod.getModIdAndName("wildmutt");
        OmnitrixInteractions.scanEntity(player, wolf);
        OmnitrixInteractions.scanEntity(player, wolf);

        long unlockCount = OmnitrixService.snapshot(player)
            .unlockedAlienIds()
            .stream()
            .filter(wildmutt::equals)
            .count();
        helper.assertTrue(unlockCount == 1, "repeat scan duplicated the Wildmutt unlock");
        helper.succeed();
    }

    public static void omnitrixScanRequiresEquippedAndUntransformedPlayer(GameTestHelper helper,
            ServerPlayer player) {
        Wolf wolf = helper.spawn(EntityType.WOLF, new BlockPos(1, 2, 1));
        ResourceLocation wildmutt = BenTenMod.getModIdAndName("wildmutt");
        ResourceLocation heatblast = BenTenMod.getModIdAndName("heatblast");
        OmnitrixService.snapshot(player);
        player.setShiftKeyDown(true);

        InteractionResult unequipped = OmnitrixInteractions.scanEntity(player, wolf);
        helper.assertFalse(unequipped.consumesAction(), "scan succeeded without an equipped watch");
        helper.assertFalse(OmnitrixService.snapshot(player).unlockedAlienIds().contains(wildmutt),
                "Wildmutt unlocked without an equipped watch");

        equipWithWatch(player);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        helper.assertTrue(OmnitrixService.transform(player, heatblast),
                "could not transform player for scan guard check");
        InteractionResult transformed = OmnitrixInteractions.scanEntity(player, wolf);
        helper.assertFalse(transformed.consumesAction(), "transformed player scanned DNA");
        helper.assertFalse(OmnitrixService.snapshot(player).unlockedAlienIds().contains(wildmutt),
                "Wildmutt unlocked while transformed");
        helper.succeed();
    }

    public static void omnitrixScanBuddingAmethystUnlocksDiamondhead(GameTestHelper helper,
            ServerPlayer player) {
        BlockPos pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos, Blocks.BUDDING_AMETHYST);
        equipWithWatch(player);
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        player.setShiftKeyDown(true);
        BlockPos absolute = helper.absolutePos(pos);
        InteractionResult result =
                OmnitrixInteractions.scanBlock(player, helper.getLevel().getBlockState(absolute));
        ResourceLocation diamondhead = BenTenMod.getModIdAndName("diamondhead");
        helper.assertTrue(result.consumesAction(), "budding amethyst scan was not accepted");
        helper.assertTrue(OmnitrixService.snapshot(player).unlockedAlienIds().contains(diamondhead),
                "scanning budding amethyst did not unlock Diamondhead");
        helper.succeed();
    }

    /** Recipes are generated data too. */
    public static void recipesLoaded(GameTestHelper helper) {
        var recipes = helper.getLevel().getRecipeManager();
        helper.assertTrue(recipes.byKey(BenTenMod.getModIdAndName("ruby_block")).isPresent(),
                "ruby_block recipe is missing");
        helper.succeed();
    }

    public static void omnitrixCrafterPlaces(GameTestHelper helper) {
        var pos = new BlockPos(1, 2, 1);
        helper.setBlock(pos, BlockInit.OmnitrixCrafter.get());
        helper.assertBlockPresent(BlockInit.OmnitrixCrafter.get(), pos);
        helper.succeed();
    }

    /** All ten alien definitions parse (a bad JSON file is skipped and logged, not loaded). */
    public static void aliensLoaded(GameTestHelper helper) {
        var aliens = Aliens.DATA.all();
        List.of("heatblast", "wildmutt", "diamondhead", "xlr8", "greymatter", "fourarms",
                "stinkfly", "ripjaws", "upgrade", "ghostfreak")
            .forEach(id -> helper.assertTrue(aliens.containsKey(BenTenMod.getModIdAndName(id)),
                    "alien missing: " + id));
        long starters = aliens.values().stream().filter(a -> a.unlockedByDefault()).count();
        helper.assertTrue(starters == 3, "expected 3 starter aliens, found " + starters);
        helper.succeed();
    }

    /** Rendering different players must not restart or suppress each other's transform clips. */
    public static void alienTransformAnimationIsPerPlayer(GameTestHelper helper) {
        PlayerAnimationStateCache states = new PlayerAnimationStateCache();
        UUID firstPlayer = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID secondPlayer = UUID.fromString("00000000-0000-0000-0000-000000000002");
        ResourceLocation heatblast = BenTenMod.getModIdAndName("heatblast");

        var first = states.bind(firstPlayer, heatblast, 1_000, 100);
        var second = states.bind(secondPlayer, heatblast, 2_000, 500);
        var firstAfterInterleavedRender = states.bind(firstPlayer, heatblast, 1_000, 101);

        helper.assertTrue(first.transformUntilTick() == 120,
                "first player's transform window was not initialized from their tick");
        helper.assertTrue(second.transformUntilTick() == 520,
                "second player inherited the first player's transform window");
        helper.assertTrue(firstAfterInterleavedRender.transformUntilTick() == 120,
                "rendering another player restarted the first player's transform animation");
        helper.assertTrue(firstAfterInterleavedRender.isTransforming(119),
                "first player's transform animation ended too early");
        helper.assertFalse(firstAfterInterleavedRender.isTransforming(120),
                "first player's transform animation did not end on time");

        var nextForm = states.bind(secondPlayer, BenTenMod.getModIdAndName("wildmutt"), 3_000, 501);
        helper.assertTrue(nextForm.transformUntilTick() == 521,
                "a player's new form did not start a new transform animation");
        helper.assertTrue(firstAfterInterleavedRender.transformUntilTick() == 120,
                "changing the second player's form altered the first player's animation");
        helper.succeed();
    }

    /**
     * Server validates the equipped state and unlock list, then applies definition-driven size and
     * powers.
     */
    public static void omnitrixTransformIsServerValidated(GameTestHelper helper,
            ServerPlayer player) {
        ResourceLocation heatblast = BenTenMod.getModIdAndName("heatblast");
        ResourceLocation diamondhead = BenTenMod.getModIdAndName("diamondhead");
        OmnitrixSnapshot initial = OmnitrixService.snapshot(player);
        helper.assertTrue(initial.unlockedAlienIds().size() == 3,
                "new Omnitrix must start with exactly three unlocked aliens");
        helper.assertFalse(OmnitrixService.transform(player, heatblast),
                "transformation succeeded without an equipped watch");
        helper.assertFalse(OmnitrixService.revert(player),
                "revert succeeded without an equipped watch");
        equipWithWatch(player);
        helper.assertFalse(OmnitrixService.transform(player, diamondhead),
                "server accepted a locked alien");
        helper.assertTrue(OmnitrixService.transform(player, heatblast),
                "server rejected an unlocked alien with an equipped watch");

        OmnitrixSnapshot transformed = OmnitrixService.snapshot(player);
        helper.assertTrue(heatblast.equals(transformed.currentAlien()),
                "current alien was not persisted");
        helper.assertTrue(transformed.expiresAt() > 0, "transformation has no timeout");
        helper.assertTrue(Math.abs(player.getBoundingBox().getXsize() - 0.7) < 0.01,
                "Heatblast width from JSON was not applied");
        helper.assertTrue(Math.abs(player.getBoundingBox().getYsize() - 2.1) < 0.01,
                "Heatblast height from JSON was not applied");

        AbilityTicker.tick(player);
        helper.assertTrue(player.hasEffect(MobEffects.FIRE_RESISTANCE),
                "Heatblast ability was not activated");
        helper.assertTrue(player.getAbilities().mayfly,
                "Heatblast flight ability was not activated");
        AbilityTicker.forget(player);
        OmnitrixService.revert(player);
        helper.succeed();
    }

    /** Expiry reverts dimensions and removes transformation abilities on the next ability tick. */
    public static void omnitrixTimeoutReverts(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        ResourceLocation heatblast = BenTenMod.getModIdAndName("heatblast");
        helper.assertTrue(OmnitrixService.transform(player, heatblast),
                "could not start Heatblast for timeout check");
        AbilityTicker.tick(player);
        OmnitrixSnapshot state = OmnitrixService.snapshot(player);
        helper.assertTrue(OmnitrixService.tick(player, state.expiresAt()),
                "expired transformation did not revert");
        AbilityTicker.tick(player);
        helper.assertTrue(OmnitrixService.snapshot(player).currentAlien() == null,
                "expired form is still active");
        helper.assertFalse(player.hasEffect(MobEffects.FIRE_RESISTANCE),
                "expired Heatblast still grants fire resistance");
        helper.assertFalse(player.getAbilities().mayfly, "expired Heatblast still grants flight");
        helper.assertTrue(Math.abs(player.getBoundingBox().getXsize() - 0.6) < 0.01,
                "player width did not return after timeout");
        AbilityTicker.forget(player);
        helper.succeed();
    }

    public static void omnitrixWatchEquipsAndConsumes(GameTestHelper helper, ServerPlayer player) {
        ItemStack watch = new ItemStack(ItemInit.OMNITRIX_WATCH.get());
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, watch);
        ItemInit.OMNITRIX_WATCH.get()
            .use(player.level(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
        helper.assertTrue(OmnitrixService.isEquipped(player),
                "watch use did not equip the Omnitrix");
        helper.assertTrue(watch.isEmpty(), "equipping did not consume the watch");
        long scheduledAt =
                OmnitrixSavedData.get(player.serverLevel()).hunterSpawnAt(player.getUUID());
        long now = player.serverLevel().getServer().overworld().getGameTime();
        helper.assertTrue(scheduledAt >= now + 2_400L && scheduledAt <= now + 6_000L,
                "first Omnitrix bind did not schedule a hunter 2–5 minutes later");
        helper.succeed();
    }

    public static void omnitrixSecondUseDoesNotConsume(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        ItemStack secondWatch = new ItemStack(ItemInit.OMNITRIX_WATCH.get());
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, secondWatch);
        ItemInit.OMNITRIX_WATCH.get()
            .use(player.level(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
        helper.assertTrue(secondWatch.getCount() == 1,
                "second Omnitrix use consumed another watch");
        helper.succeed();
    }

    public static void omnitrixEquippedStateRoundTrips(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        OmnitrixSavedData data = OmnitrixSavedData.get(player.serverLevel());
        CompoundTag encoded = data.save(new CompoundTag());
        OmnitrixSavedData decoded = OmnitrixSavedData.load(encoded);
        helper.assertTrue(decoded.isEquipped(player.getUUID()),
                "equipped state was lost during SavedData encode/decode");

        CompoundTag legacyPlayer = new CompoundTag();
        legacyPlayer.putUUID("player", player.getUUID());
        ListTag legacyPlayers = new ListTag();
        legacyPlayers.add(legacyPlayer);
        CompoundTag legacySave = new CompoundTag();
        legacySave.put("players", legacyPlayers);
        helper.assertFalse(OmnitrixSavedData.load(legacySave).isEquipped(player.getUUID()),
                "a legacy save without equipped defaulted to true");
        helper.succeed();
    }

    /** Heatblast set: effects and flight while worn, flight removed once a piece comes off. */
    public static void heatblastSetGrantsAndRevokesFlight(GameTestHelper helper,
            ServerPlayer player) {
        reset(player);
        equip(player, ItemInit.HEATBLAST_HELMET, ItemInit.HEATBLAST_CHESTPLATE,
                ItemInit.HEATBLAST_LEGGINGS, ItemInit.HEATBLAST_BOOTS);
        AbilityTicker.tick(player);
        helper.assertTrue(player.hasEffect(MobEffects.FIRE_RESISTANCE), "no fire resistance");
        helper.assertTrue(player.getAbilities().mayfly, "flight not granted");

        player.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        AbilityTicker.tick(player);
        helper.assertFalse(player.getAbilities().mayfly, "flight not removed with the armour");
        AbilityTicker.forget(player);
        helper.succeed();
    }

    /** Was broken: the old code compared the boots slot with the helmet. */
    public static void fourArmsSetWorks(GameTestHelper helper, ServerPlayer player) {
        reset(player);
        equip(player, ItemInit.FOURARMS_HELMET, ItemInit.FOURARMS_CHESTPLATE,
                ItemInit.FOURARMS_LEGGINGS, ItemInit.FOURARMS_BOOT);
        AbilityTicker.tick(player);
        helper.assertTrue(player.hasEffect(MobEffects.DAMAGE_BOOST),
                "no strength from Four Arms set");
        AbilityTicker.forget(player);
        helper.succeed();
    }

    /** Was broken: a stray semicolon applied the bonus for any single piece. */
    public static void legendaryNeedsFullSet(GameTestHelper helper, ServerPlayer player) {
        reset(player);
        player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ItemInit.HELMET.get()));
        AbilityTicker.tick(player);
        helper.assertFalse(player.hasEffect(MobEffects.DAMAGE_BOOST),
                "legendary bonus from a single piece");
        AbilityTicker.forget(player);
        helper.succeed();
    }

    /**
     * Armour tests need a real server player. Vanilla's mock player doesn't work on Forge, so each
     * loader passes its own fake player (one per test, so tests can't affect each other).
     */
    private static void reset(ServerPlayer player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            player.setItemSlot(slot, ItemStack.EMPTY);
        }
        player.removeAllEffects();
        AbilityTicker.forget(player);
    }

    private static ItemStack equipWithWatch(ServerPlayer player) {
        ItemStack watch = new ItemStack(ItemInit.OMNITRIX_WATCH.get());
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, watch);
        ItemInit.OMNITRIX_WATCH.get()
            .use(player.level(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
        return watch;
    }

    @SafeVarargs
    private static void equip(ServerPlayer player, RegistryObject<? extends Item>... pieces) {
        EquipmentSlot[] slots =
                {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < pieces.length; i++) {
            player.setItemSlot(slots[i], new ItemStack(pieces[i].get()));
        }
    }
}
