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

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import io.github.realyusufismail.bentenmod.core.blocks.PrisonVaultBlock;
import io.github.realyusufismail.bentenmod.ability.AbilityTicker;
import io.github.realyusufismail.bentenmod.alien.Aliens;
import io.github.realyusufismail.bentenmod.core.init.EntityTypesInit;
import io.github.realyusufismail.bentenmod.core.init.ItemInit;
import io.github.realyusufismail.bentenmod.core.init.RecipeTypeInit;
import io.github.realyusufismail.bentenmod.entity.VilgaxEntity;
import io.github.realyusufismail.bentenmod.entity.ZsSkayrEntity;
import io.github.realyusufismail.bentenmod.entity.BossChatter;
import io.github.realyusufismail.bentenmod.entity.NullVoidPortalEntity;
import io.github.realyusufismail.bentenmod.entity.PlumberBoltEntity;
import io.github.realyusufismail.bentenmod.entity.PrisonWardenEntity;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixService;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixInteractions;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSavedData;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixSnapshot;
import io.github.realyusufismail.bentenmod.omnitrix.AlienEyeHeight;
import io.github.realyusufismail.bentenmod.omnitrix.OmnitrixPowerService;
import io.github.realyusufismail.bentenmod.core.item.NullVoidProjectorItem;
import io.github.realyusufismail.bentenmod.omnitrix.PlayerAnimationStateCache;
import io.github.realyusufismail.bentenmod.platform.registry.RegistryObject;
import io.github.realyusufismail.bentenmod.worldgen.ModOrePlacements;
import io.github.realyusufismail.bentenmod.worldgen.OmnitrixCrashSiteWorldgen;
import io.github.realyusufismail.bentenmod.worldgen.NullVoidWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
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

    public static void livingNullVoidMobsHaveAttributes(GameTestHelper helper) {
        var guardian = helper.spawn(EntityTypesInit.NULL_GUARDIAN.get(), new BlockPos(1, 2, 1));
        var scout = helper.spawn(EntityTypesInit.LEPIDOPTERAN.get(), new BlockPos(4, 2, 1));
        var warden = helper.spawn(EntityTypesInit.PRISON_WARDEN.get(), new BlockPos(7, 2, 1));
        helper.assertTrue(guardian.getMaxHealth() == 60.0F, "Null Guardian should have 60 health");
        helper.assertTrue(scout.getMaxHealth() == 20.0F, "Lepidopterran should have 20 health");
        helper.assertTrue(scout.getAttributeValue(Attributes.MOVEMENT_SPEED) >= 0.4,
                "Lepidopterran should be fast");
        helper.assertTrue(warden.getMaxHealth() == 150.0F, "Prison Warden should have 150 health");
        helper.assertTrue(warden.getAttributeValue(Attributes.ARMOR) >= 10.0,
                "Prison Warden should have boss armour");
        helper.succeed();
    }

    public static void flyingMobsCanFly(GameTestHelper helper) {
        var flyers = java.util.List.of(EntityTypesInit.NULL_GUARDIAN.get(),
                EntityTypesInit.LEPIDOPTERAN.get(), EntityTypesInit.ZS_SKAYR_ENTITY.get(),
                EntityTypesInit.VILGAX_DRONE_ENTITY.get());
        int x = 1;
        for (var type : flyers) {
            var mob = helper.spawn(type, new BlockPos(x, 4, 1));
            helper.assertTrue(mob.getAttribute(Attributes.FLYING_SPEED) != null,
                    type.getDescriptionId() + " has no flying_speed attribute");
            mob.setOnGround(false);
            mob.getMoveControl().setWantedPosition(mob.getX(), mob.getY() + 2, mob.getZ(), 1.0);
            mob.getMoveControl().tick();
            x += 3;
        }
        helper.succeed();
    }

    public static void nullVoidLootTablesContainDrops(GameTestHelper helper) {
        var prisoner =
                helper.spawn(EntityTypesInit.NULL_VOID_PRISONER.get(), new BlockPos(1, 2, 1));
        var guardian = helper.spawn(EntityTypesInit.NULL_GUARDIAN.get(), new BlockPos(4, 2, 1));
        var scout = helper.spawn(EntityTypesInit.LEPIDOPTERAN.get(), new BlockPos(7, 2, 1));
        var warden = helper.spawn(EntityTypesInit.PRISON_WARDEN.get(), new BlockPos(10, 2, 1));
        assertLootExists(helper, "entities/null_void_prisoner", prisoner,
                ItemInit.PRISONER_CUFFS.get(), 200);
        assertLootExists(helper, "entities/null_guardian", guardian,
                ItemInit.NULL_GUARDIAN_MEMBRANE.get(), 1);
        assertLootExists(helper, "entities/lepidopterran", scout, Items.SLIME_BALL, 100);
        assertLootExists(helper, "entities/lepidopterran", scout, ItemInit.TAYDENITE.get(), 300);
        assertLootExists(helper, "entities/prison_warden", warden, ItemInit.CELL_KEY.get(), 1);
        assertLootExists(helper, "entities/prison_warden", warden, ItemInit.PRISONER_CUFFS.get(),
                200);
        helper.succeed();
    }

    public static void prisonVaultUsesKeyAndOpens(GameTestHelper helper, ServerPlayer player) {
        BlockPos relative = new BlockPos(3, 2, 3);
        BlockPos pos = helper.absolutePos(relative);
        player.getAbilities().instabuild = false;
        helper.setBlock(relative, BlockInit.PRISON_VAULT.get());
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        player.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 1.5);

        InteractionResult refused = BlockInit.PRISON_VAULT.get()
            .use(helper.getLevel().getBlockState(pos), helper.getLevel(), pos, player,
                    InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(refused.consumesAction(), "vault did not handle the no-key interaction");
        helper.assertFalse(helper.getLevel().getBlockState(pos).getValue(PrisonVaultBlock.OPEN),
                "vault opened without a cell key");

        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemInit.CELL_KEY.get()));
        InteractionResult opened = BlockInit.PRISON_VAULT.get()
            .use(helper.getLevel().getBlockState(pos), helper.getLevel(), pos, player,
                    InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(opened.consumesAction(), "cell key did not open the vault");
        helper.assertTrue(helper.getLevel().getBlockState(pos).getValue(PrisonVaultBlock.OPEN),
                "vault did not enter its opened state");
        helper.assertTrue(player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty(),
                "cell key was not consumed");

        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemInit.CELL_KEY.get()));
        BlockInit.PRISON_VAULT.get()
            .use(helper.getLevel().getBlockState(pos), helper.getLevel(), pos, player,
                    InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(
                player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemInit.CELL_KEY.get()),
                "opened vault consumed a second key");
        helper.succeed();
    }

    public static void nullVoidGliderSlowsFalling(GameTestHelper helper, ServerPlayer player) {
        ItemStack glider = new ItemStack(ItemInit.NULL_VOID_GLIDER.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, glider);
        BlockPos pos = helper.absolutePos(new BlockPos(1, 10, 1));
        player.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        player.setDeltaMovement(0.0, -1.0, 0.0);
        ItemInit.NULL_VOID_GLIDER.get().inventoryTick(glider, helper.getLevel(), player, 0, true);
        helper.assertTrue(player.hasEffect(MobEffects.SLOW_FALLING),
                "held glider did not grant Slow Falling while descending");
        helper.assertTrue(player.fallDistance == 0.0F, "glider did not reset fall distance");
        helper.succeed();
    }

    public static void plumberBlasterRecipeLoads(GameTestHelper helper) {
        var recipe = helper.getLevel()
            .getRecipeManager()
            .byKey(BenTenMod.getModIdAndName("plumber_blaster"));
        helper.assertTrue(recipe.isPresent(), "Plumber Blaster Crafter recipe is missing");
        if (recipe.isEmpty()) {
            return;
        }
        helper.assertTrue(
                recipe.orElseThrow()
                    .getResultItem(helper.getLevel().registryAccess())
                    .is(ItemInit.PLUMBER_BLASTER.get()),
                "Plumber Blaster recipe has the wrong result");
        helper.succeed();
    }

    public static void prisonWardenRespectsPrisonerCap(GameTestHelper helper) {
        var warden = helper.spawn(EntityTypesInit.PRISON_WARDEN.get(), new BlockPos(4, 2, 4));
        helper.spawn(EntityTypesInit.NULL_VOID_PRISONER.get(), new BlockPos(2, 2, 2));
        helper.spawn(EntityTypesInit.NULL_VOID_PRISONER.get(), new BlockPos(6, 2, 2));
        helper.spawn(EntityTypesInit.NULL_VOID_PRISONER.get(), new BlockPos(2, 2, 6));
        helper.spawn(EntityTypesInit.NULL_VOID_PRISONER.get(), new BlockPos(6, 2, 6));
        helper.runAtTickTime(1, () -> {
            var known = helper.getLevel()
                .getEntitiesOfClass(
                        io.github.realyusufismail.bentenmod.entity.NullVoidPrisonerEntity.class,
                        warden.getBoundingBox().inflate(40.0))
                .stream()
                .map(net.minecraft.world.entity.Entity::getUUID)
                .collect(java.util.stream.Collectors.toSet());
            warden.summonPrisoners(helper.getLevel());
            boolean summoned = helper.getLevel()
                .getEntitiesOfClass(
                        io.github.realyusufismail.bentenmod.entity.NullVoidPrisonerEntity.class,
                        warden.getBoundingBox().inflate(40.0))
                .stream()
                .anyMatch(prisoner -> !known.contains(prisoner.getUUID()));
            helper.assertFalse(summoned, "Prison Warden summoned beyond its cap of four prisoners");
            helper.succeed();
        });
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
        BlockPos feet = helper.absolutePos(new BlockPos(1, 2, 1));
        source.setBlockAndUpdate(feet.below(),
                net.minecraft.world.level.block.Blocks.STONE.defaultBlockState());
        source.setBlockAndUpdate(feet,
                net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
        source.setBlockAndUpdate(feet.above(),
                net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
        player.moveTo(feet.getX() + 0.5, feet.getY(), feet.getZ() + 0.5, 0.0F, 0.0F);
        source.addNewPlayer(player);
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
        helper.assertTrue(entered.consumesAction(), "projector did not open a portal");
        NullVoidPortalEntity entryPortal = source
            .getEntitiesOfClass(NullVoidPortalEntity.class, player.getBoundingBox().inflate(8.0))
            .stream()
            .filter(portal -> portal.ownerId().filter(player.getUUID()::equals).isPresent())
            .findFirst()
            .orElse(null);
        helper.assertTrue(entryPortal != null, "projector did not spawn its portal entity");
        if (entryPortal == null) {
            return;
        }
        helper.assertTrue(projector.getDamageValue() == 1,
                "opening the portal did not cost one durability");
        entryPortal.setPos(player.getX(), player.getY(), player.getZ());
        helper.runAfterDelay(25, () -> {
            helper.assertTrue(player.serverLevel().dimension().equals(NullVoidWorldgen.DIMENSION),
                    "portal did not carry the player into the Null Void");
            OmnitrixSavedData.ReturnPoint point =
                    OmnitrixSavedData.get(player.serverLevel()).returnPoint(player.getUUID());
            helper.assertTrue(
                    point != null && point.dimension().equals(source.dimension().location()),
                    "projector did not save the return dimension");

            player.getCooldowns().removeCooldown(ItemInit.NULL_VOID_PROJECTOR.get());
            InteractionResult returned = ItemInit.NULL_VOID_PROJECTOR.get()
                .use(player.level(), player, InteractionHand.MAIN_HAND)
                .getResult();
            helper.assertTrue(returned.consumesAction(),
                    "projector did not open the return portal");
            helper.assertTrue(NullVoidProjectorItem.transferPlayer(player),
                    "the return portal could not carry the player home");
            helper.assertTrue(player.serverLevel() == source,
                    "projector returned to the wrong level");
            helper.assertTrue(
                    Math.abs(player.getX() - x) < 0.01 && Math.abs(player.getY() - y) < 0.01
                            && Math.abs(player.getZ() - z) < 0.01,
                    "projector ignored the saved return point: expected " + x + " " + y + " " + z
                            + ", got " + player.position());
            helper.assertTrue(
                    Math.abs(player.getYRot() - yaw) < 0.01
                            && Math.abs(player.getXRot() - pitch) < 0.01,
                    "projector did not restore the saved rotation");
            helper.assertTrue(projector.getDamageValue() == 2,
                    "opening the return portal did not cost one durability");
            helper.succeed();
        });
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

        BlockPos taydenitePos = new BlockPos(2, 2, 1);
        helper.setBlock(taydenitePos, BlockInit.TAYDENITE_ORE.get());
        BlockPos taydeniteAbsolute = helper.absolutePos(taydenitePos);
        ItemStack fortunePickaxe = new ItemStack(Items.IRON_PICKAXE);
        fortunePickaxe.enchant(Enchantments.BLOCK_FORTUNE, 3);
        List<ItemStack> taydeniteDrops =
                Block.getDrops(BlockInit.TAYDENITE_ORE.get().defaultBlockState(), helper.getLevel(),
                        taydeniteAbsolute, null, null, fortunePickaxe);
        helper.assertTrue(
                taydeniteDrops.stream().anyMatch(stack -> stack.is(ItemInit.TAYDENITE.get())),
                "Taydenite Ore did not drop Taydenite with Fortune");

        ItemStack silkPickaxe = new ItemStack(Items.IRON_PICKAXE);
        silkPickaxe.enchant(Enchantments.SILK_TOUCH, 1);
        List<ItemStack> silkDrops =
                Block.getDrops(BlockInit.TAYDENITE_ORE.get().defaultBlockState(), helper.getLevel(),
                        taydeniteAbsolute, null, null, silkPickaxe);
        helper.assertTrue(
                silkDrops.stream()
                    .anyMatch(stack -> stack.is(BlockInit.TAYDENITE_ORE.get().asItem())),
                "Silk Touch did not drop Taydenite Ore itself");
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

    public static void powerKeyRepeatsAfterRespawn(GameTestHelper helper) {
        var repeat = new io.github.realyusufismail.bentenmod.omnitrix.PowerKeyRepeat();
        helper.assertTrue(repeat.tryFire(5000), "the first power request was blocked");
        helper.assertFalse(repeat.tryFire(5001),
                "power requests repeated faster than every 2 ticks");
        helper.assertTrue(repeat.tryFire(5002), "a held power key stopped repeating");
        helper.assertTrue(repeat.tryFire(10), "the power key stayed blocked after the clock reset");
        var states = new PlayerAnimationStateCache();
        var state = states.bind(java.util.UUID.randomUUID(),
                new ResourceLocation(BenTenMod.MOD_ID, "heatblast"), 9000L, 5000L);
        helper.assertTrue(state.isTransforming(5019) && !state.isTransforming(5020),
                "the transform animation window is not 20 ticks of game time");
        helper.succeed();
    }

    public static void xlr8RunsOnWater(GameTestHelper helper, ServerPlayer player) {
        BlockPos water = helper.absolutePos(new BlockPos(2, 2, 2));
        helper.getLevel().setBlockAndUpdate(water, Blocks.WATER.defaultBlockState());
        helper.getLevel().setBlockAndUpdate(water.above(), Blocks.AIR.defaultBlockState());
        double surface = water.getY()
                + helper.getLevel().getFluidState(water).getHeight(helper.getLevel(), water);
        player.setSprinting(true);
        player.moveTo(water.getX() + 0.5, surface - 0.15, water.getZ() + 0.5);
        player.setDeltaMovement(0.3, -0.08, 0.0);
        helper.assertTrue(
                io.github.realyusufismail.bentenmod.omnitrix.WaterRunning.keepOnSurface(player),
                "a fast sprint did not hold the player on the water");
        helper.assertTrue(
                Math.abs(player.getY() - surface) < 1.0e-6 && player.onGround()
                        && player.getDeltaMovement().y >= 0.0,
                "the player was not standing on the water surface");

        player.moveTo(water.getX() + 0.5, surface - 0.15, water.getZ() + 0.5);
        player.setDeltaMovement(0.05, -0.08, 0.0);
        helper.assertFalse(
                io.github.realyusufismail.bentenmod.omnitrix.WaterRunning.keepOnSurface(player),
                "a slow player stayed on the water");
        player.setDeltaMovement(0.3, -0.08, 0.0);
        player.setSprinting(false);
        helper.assertFalse(
                io.github.realyusufismail.bentenmod.omnitrix.WaterRunning.keepOnSurface(player),
                "a walking player stayed on the water");
        helper.succeed();
    }

    public static void starterGliderFallsBackToChest(GameTestHelper helper, ServerPlayer player) {
        BlockPos feet = helper.absolutePos(new BlockPos(2, 2, 2));
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                helper.getLevel()
                    .setBlockAndUpdate(feet.offset(dx, -1, dz), Blocks.STONE.defaultBlockState());
                helper.getLevel()
                    .setBlockAndUpdate(feet.offset(dx, 0, dz), Blocks.AIR.defaultBlockState());
            }
        }
        player.moveTo(feet.getX() + 0.5, feet.getY(), feet.getZ() + 0.5);
        player.getInventory().clearContent();
        NullVoidProjectorItem.giveStarterGlider(player);
        helper.assertTrue(
                player.getInventory().contains(new ItemStack(ItemInit.NULL_VOID_GLIDER.get())),
                "the starter glider did not go into an empty inventory");

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            player.getInventory().setItem(slot, new ItemStack(Blocks.DIRT, 64));
        }
        NullVoidProjectorItem.giveStarterGlider(player);
        boolean inChest = false;
        for (Direction side : Direction.Plane.HORIZONTAL) {
            if (helper.getLevel()
                .getBlockEntity(feet.relative(
                        side)) instanceof net.minecraft.world.level.block.entity.ChestBlockEntity chest
                    && chest.getItem(0).is(ItemInit.NULL_VOID_GLIDER.get())) {
                inChest = true;
            }
        }
        helper.assertTrue(inChest, "a full inventory did not get the glider in a chest");
        helper.succeed();
    }

    public static void nullVoidHasIslandsWherePlayersArrive(GameTestHelper helper) {
        ServerLevel level = helper.getLevel().getServer().getLevel(NullVoidWorldgen.DIMENSION);
        helper.assertTrue(level != null, "the Null Void dimension is not loaded");
        if (level == null) {
            return;
        }
        // 150-1000 blocks from 0,0 was an empty ring when the Null Void reused the End's islands.
        int[][] arrivals =
                {{0, 0}, {240, 90}, {-410, 330}, {650, -520}, {-880, -140}, {3100, 2700}};
        for (int[] arrival : arrivals) {
            helper.assertTrue(
                    NullVoidProjectorItem.findSafeLanding(level,
                            new BlockPos(arrival[0], 0, arrival[1])) != null,
                    "no island to land on near " + arrival[0] + ", " + arrival[1]);
        }
        helper.succeed();
    }

    public static void prisonChestLoot(GameTestHelper helper) {
        LootParams params = new LootParams.Builder(helper.getLevel())
            .withParameter(LootContextParams.ORIGIN, Vec3.ZERO)
            .create(LootContextParamSets.CHEST);
        LootTable vault = entityLootTable(helper, "chests/prison_vault");
        LootTable cell = entityLootTable(helper, "chests/prison_cell");
        helper.assertTrue(vault != null && cell != null, "Prison chest loot tables are missing");
        if (vault == null || cell == null) {
            return;
        }
        List<ItemStack> vaultDrops = vault.getRandomItems(params);
        helper.assertTrue(vaultDrops.stream().anyMatch(stack -> stack.is(Items.DIAMOND)),
                "Prison vault loot is missing diamonds");
        helper.assertTrue(vaultDrops.stream()
            .filter(stack -> stack.is(ItemInit.TAYDENITE.get()))
            .mapToInt(ItemStack::getCount)
            .sum() >= 4, "Prison vault gives too little taydenite");
        helper.assertTrue(vaultDrops.size() >= 8, "Prison vault loot is too small");
        helper.assertTrue(!cell.getRandomItems(params).isEmpty(), "Prison cell chest is empty");
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

    private static void assertLootExists(GameTestHelper helper, String path,
            net.minecraft.world.entity.Mob entity, Item expected, int seeds) {
        LootTable table = entityLootTable(helper, path);
        helper.assertTrue(table != null, "Missing entity loot table: " + path);
        if (table == null) {
            return;
        }
        LootParams params = new LootParams.Builder(helper.getLevel())
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.ORIGIN, entity.position())
            .withParameter(LootContextParams.DAMAGE_SOURCE, entity.damageSources().generic())
            .create(LootContextParamSets.ENTITY);
        boolean found = false;
        for (long seed = 0; seed < seeds && !found; seed++) {
            found = table.getRandomItems(params, seed)
                .stream()
                .anyMatch(stack -> stack.is(expected));
        }
        helper.assertTrue(found, "Loot table " + path + " never returned " + expected);
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
        helper.assertTrue(placed.containsKey(NullVoidWorldgen.TAYDENITE_ORE_PLACED),
                "Taydenite ore placed feature is missing");
        helper.assertTrue(placed.containsKey(NullVoidWorldgen.FORTRESS_PLACED),
                "Null Void fortress placed feature is missing");
        helper.assertTrue(placed.containsKey(NullVoidWorldgen.FLOATING_PRISON_PLACED),
                "Floating prison placed feature is missing");
        helper.assertTrue(placed.containsKey(NullVoidWorldgen.PLUMBER_OUTPOST_PLACED),
                "Plumber outpost placed feature is missing");
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
        aliens.forEach((id, definition) -> helper.assertTrue(definition.power().isPresent(),
                "alien has no active power: " + id));
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

    public static void heatblastPowerSpawnsFireball(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Heatblast transformation failed");
        helper.assertTrue(OmnitrixService.usePower(player), "Heatblast power did not activate");
        helper.assertTrue(!player.level()
            .getEntitiesOfClass(SmallFireball.class, player.getBoundingBox().inflate(16))
            .isEmpty(), "Heatblast power did not spawn a Small Fireball");
        helper.succeed();
    }

    public static void alienPowerCooldownBlocksImmediateUse(GameTestHelper helper,
            ServerPlayer player) {
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Heatblast transformation failed");
        helper.assertTrue(OmnitrixService.usePower(player), "first power use was refused");
        helper.assertFalse(OmnitrixService.usePower(player),
                "power cooldown allowed a second immediate use");
        helper.succeed();
    }

    public static void alienEyeHeightTracksTransformation(GameTestHelper helper,
            ServerPlayer player) {
        helper.assertTrue(Math.abs(AlienEyeHeight.forPose(2.0F, Pose.STANDING) - 1.8F) < 0.001F,
                "standing eye height is not 90 percent of the hitbox");
        float expectedCrouch = 2.0F * 0.9F * (1.27F / 1.62F);
        helper.assertTrue(
                Math.abs(AlienEyeHeight.forPose(2.0F, Pose.CROUCHING) - expectedCrouch) < 0.001F,
                "crouching eye height did not scale from the standing ratio");
        helper.assertTrue(AlienEyeHeight.forPose(0.2F, Pose.STANDING) <= 0.2F,
                "eye height exceeded the hitbox");

        player.getAbilities().instabuild = true;
        equipWithWatch(player);
        ResourceLocation greyMatter = BenTenMod.getModIdAndName("greymatter");
        ResourceLocation fourArms = BenTenMod.getModIdAndName("fourarms");
        helper.assertTrue(OmnitrixService.transform(player, greyMatter),
                "Grey Matter transformation failed");
        player.refreshDimensions();
        helper.assertTrue(player.getEyeHeight() < 1.0F,
                "Grey Matter camera height was not scaled below one block");
        helper.assertTrue(OmnitrixService.revert(player), "Grey Matter revert failed");
        helper.assertTrue(OmnitrixService.transform(player, fourArms),
                "Four Arms transformation failed");
        player.refreshDimensions();
        helper.assertTrue(player.getEyeHeight() > 2.0F,
                "Four Arms camera height was not scaled above two blocks");
        helper.assertTrue(OmnitrixService.revert(player), "Four Arms revert failed");
        player.refreshDimensions();
        helper.assertTrue(Math.abs(player.getEyeHeight() - 1.62F) < 0.02F,
                "reverting did not restore vanilla standing eye height");
        helper.succeed();
    }

    public static void heatblastFireballDamagesVilgax(GameTestHelper helper, ServerPlayer player) {
        VilgaxEntity vilgax =
                helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(5, 2, 1));
        vilgax.setNoAi(true);
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Heatblast transformation failed");
        SmallFireball projectile = new SmallFireball(helper.getLevel(), player, 1, 0, 0);
        float health = vilgax.getHealth();
        OmnitrixPowerService.applyFireballHit(player, projectile, vilgax);
        helper.assertTrue(vilgax.getHealth() < health,
                "Heatblast fireball did no non-fire damage to fire-immune Vilgax");
        helper.assertFalse(vilgax.isOnFire(), "fire-immune Vilgax was set on fire");
        helper.succeed();
    }

    public static void fourArmsSlamDamagesVilgax(GameTestHelper helper, ServerPlayer player) {
        BlockPos playerFeet = helper.absolutePos(new BlockPos(1, 2, 1));
        player.setPos(playerFeet.getX() + 0.5, playerFeet.getY(), playerFeet.getZ() + 0.5);
        VilgaxEntity vilgax =
                helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(3, 2, 1));
        vilgax.setNoAi(true);
        player.getAbilities().instabuild = true;
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("fourarms")),
                "Four Arms transformation failed");
        float health = vilgax.getHealth();
        helper.assertTrue(OmnitrixService.usePower(player), "Four Arms slam was refused");
        helper.assertTrue(vilgax.getHealth() < health, "Four Arms slam did no damage to Vilgax");
        helper.succeed();
    }

    public static void armorLayerTexturesExist(GameTestHelper helper) {
        int armorPieces = 0;
        for (Item item : BuiltInRegistries.ITEM) {
            if (!(item instanceof ArmorItem armor)
                    || !"bentenmod".equals(BuiltInRegistries.ITEM.getKey(item).getNamespace())) {
                continue;
            }
            armorPieces++;
            String[] material = armor.getMaterial().getName().split(":", 2);
            String namespace = material.length == 2 ? material[0] : "minecraft";
            String path = material.length == 2 ? material[1] : material[0];
            for (int layer = 1; layer <= 2; layer++) {
                String resource = "assets/" + namespace + "/textures/models/armor/" + path
                        + "_layer_" + layer + ".png";
                helper.assertTrue(
                        GameTestChecks.class.getClassLoader().getResource(resource) != null,
                        "missing armour layer resource " + resource);
            }
        }
        helper.assertTrue(armorPieces > 0, "no mod armour items were registered");
        helper.succeed();
    }

    public static void plumberBoltDealsFixedDamage(GameTestHelper helper, ServerPlayer player) {
        Zombie zombie = helper.spawn(EntityType.ZOMBIE, new BlockPos(5, 2, 1));
        zombie.setNoAi(true);
        zombie.getAttribute(Attributes.ARMOR).setBaseValue(0.0);
        for (EquipmentSlot slot : new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            zombie.setItemSlot(slot, ItemStack.EMPTY);
        }
        PlumberBoltEntity bolt = new PlumberBoltEntity(helper.getLevel(), player);
        helper.assertTrue(bolt.isNoGravity(), "Plumber Bolt should not have gravity");
        float health = zombie.getHealth();
        bolt.damageTarget(player, zombie);
        helper.assertTrue(
                Math.abs((health - zombie.getHealth()) - PlumberBoltEntity.DAMAGE) < 0.01F,
                "Plumber Bolt should deal exactly six damage");
        helper.succeed();
    }

    public static void nullVoidProjectorOpensPortal(GameTestHelper helper, ServerPlayer player) {
        BlockPos playerFeet = helper.absolutePos(new BlockPos(1, 2, 1));
        player.setPos(playerFeet.getX() + 0.5, playerFeet.getY(), playerFeet.getZ() + 0.5);
        player.setYRot(0.0F);
        player.setXRot(0.0F);
        ItemStack projector = new ItemStack(ItemInit.NULL_VOID_PROJECTOR.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, projector);
        InteractionResult result = ItemInit.NULL_VOID_PROJECTOR.get()
            .use(player.level(), player, InteractionHand.MAIN_HAND)
            .getResult();
        helper.assertTrue(result.consumesAction(), "projector did not open a portal");
        NullVoidPortalEntity portal = helper.getLevel()
            .getEntitiesOfClass(NullVoidPortalEntity.class, player.getBoundingBox().inflate(8.0))
            .stream()
            .filter(candidate -> candidate.ownerId().filter(player.getUUID()::equals).isPresent())
            .findFirst()
            .orElse(null);
        helper.assertTrue(portal != null, "projector did not spawn a portal entity");
        helper.assertTrue(projector.getDamageValue() == 1,
                "opening the portal did not cost one durability");
        if (portal != null) {
            Vec3 look = player.getLookAngle();
            Vec3 expected = new Vec3(player.getX() + look.x * 3.0, player.getEyeY() - 1.5,
                    player.getZ() + look.z * 3.0);
            helper.assertTrue(portal.position().distanceTo(expected) < 0.01,
                    "portal did not spawn three blocks in front of the player");
        }
        helper.succeed();
    }

    public static void nullVoidPortalPullsZombie(GameTestHelper helper) {
        helper.spawn(EntityTypesInit.NULL_VOID_PORTAL.get(), new BlockPos(2, 2, 2));
        Zombie zombie = helper.spawn(EntityType.ZOMBIE, new BlockPos(6, 2, 2));
        zombie.setNoAi(true);
        zombie.setDeltaMovement(Vec3.ZERO);
        helper.runAtTickTime(16, () -> {
            helper.assertTrue(zombie.getDeltaMovement().x < -0.02,
                    "nearby zombie was not pulled toward the portal");
            helper.succeed();
        });
    }

    public static void nullVoidPortalResistsBosses(GameTestHelper helper) {
        NullVoidPortalEntity portal =
                helper.spawn(EntityTypesInit.NULL_VOID_PORTAL.get(), new BlockPos(2, 2, 2));
        VilgaxEntity vilgax =
                helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(6, 2, 2));
        ZsSkayrEntity zsSkayr =
                helper.spawn(EntityTypesInit.ZS_SKAYR_ENTITY.get(), new BlockPos(2, 2, 6));
        PrisonWardenEntity warden =
                helper.spawn(EntityTypesInit.PRISON_WARDEN.get(), new BlockPos(2, 2, 0));
        var kraab = helper.spawn(EntityTypesInit.CRAB_ENTITY.get(), new BlockPos(0, 2, 2));
        net.minecraft.world.entity.Mob[] bosses = {vilgax, zsSkayr, warden, kraab};
        for (net.minecraft.world.entity.Mob boss : bosses) {
            boss.setNoAi(true);
            boss.setDeltaMovement(Vec3.ZERO);
        }
        helper.runAtTickTime(16, () -> {
            assertPushedAwayFromPortal(helper, vilgax, portal, "Vilgax");
            assertPushedAwayFromPortal(helper, zsSkayr, portal, "Zs'Skayr");
            assertPushedAwayFromPortal(helper, warden, portal, "prison warden");
            assertPushedAwayFromPortal(helper, kraab, portal, "Kraab");
            helper.succeed();
        });
    }

    private static void assertPushedAwayFromPortal(GameTestHelper helper,
            net.minecraft.world.entity.Mob boss, NullVoidPortalEntity portal, String name) {
        Vec3 away = boss.position().subtract(portal.position()).multiply(1.0, 0.0, 1.0).normalize();
        helper.assertTrue(boss.getDeltaMovement().dot(away) > 0.02,
                "portal pulled " + name + " in instead of resisting them");
    }

    public static void nullVoidPortalCloses(GameTestHelper helper) {
        NullVoidPortalEntity portal =
                helper.spawn(EntityTypesInit.NULL_VOID_PORTAL.get(), new BlockPos(2, 2, 2));
        helper.runAtTickTime(205, () -> {
            helper.assertTrue(portal.isRemoved(), "portal was still alive after its lifetime");
            helper.succeed();
        });
    }

    public static void bossChatterRespectsCooldown(GameTestHelper helper, ServerPlayer player) {
        VilgaxEntity vilgax =
                helper.spawn(EntityTypesInit.VILGAX_ENTITY.get(), new BlockPos(2, 2, 2));
        vilgax.setNoAi(true);
        player.setPos(vilgax.getX(), vilgax.getY(), vilgax.getZ());
        boolean first = BossChatter.say(vilgax, net.minecraft.ChatFormatting.DARK_RED,
                "dialogue.bentenmod.vilgax.phase_two.1");
        boolean second = BossChatter.say(vilgax, net.minecraft.ChatFormatting.DARK_RED,
                "dialogue.bentenmod.vilgax.phase_two.2");
        helper.assertTrue(first, "boss chatter rejected its first line");
        helper.assertFalse(second, "boss chatter sent two lines inside its cooldown");
        helper.succeed();
    }

    public static void nullVoidLandingUsesNearbySurface(GameTestHelper helper) {
        ServerLevel level = helper.getLevel().getServer().getLevel(NullVoidWorldgen.DIMENSION);
        helper.assertTrue(level != null, "Null Void dimension is missing for landing test");
        if (level == null) {
            return;
        }
        BlockPos origin = new BlockPos(32000, 200, 32000);
        BlockPos platform = origin;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                level.setBlock(platform.offset(dx, 0, dz), Blocks.END_STONE.defaultBlockState(), 3);
            }
        }
        BlockPos landing = NullVoidProjectorItem.findSafeLanding(level, origin);
        helper.assertTrue(landing != null, "landing search missed the nearby island surface");
        helper.assertTrue(landing != null && landing.equals(platform.above()),
                "landing search chose the fallback pad instead of the nearby island");
        helper.succeed();
    }

    public static void creativeCanTransformWithoutUnlocking(GameTestHelper helper,
            ServerPlayer player) {
        player.getAbilities().instabuild = true;
        equipWithWatch(player);
        ResourceLocation diamondhead = BenTenMod.getModIdAndName("diamondhead");
        OmnitrixSavedData.PlayerState state =
                OmnitrixSavedData.get(player.serverLevel()).getOrCreate(player);
        helper.assertFalse(state.isUnlocked(diamondhead), "test alien was already unlocked");
        helper.assertTrue(OmnitrixService.transform(player, diamondhead),
                "creative player could not select a locked alien");
        helper.assertFalse(state.isUnlocked(diamondhead),
                "creative selection persisted a permanent unlock");
        helper.assertTrue(OmnitrixService.snapshot(player).unlockedAlienIds().contains(diamondhead),
                "creative dial snapshot did not include every alien");
        helper.succeed();
    }

    public static void timeoutStartsRechargeAndBlocksTransform(GameTestHelper helper,
            ServerPlayer player) {
        equipWithWatch(player);
        ResourceLocation heatblast = BenTenMod.getModIdAndName("heatblast");
        helper.assertTrue(OmnitrixService.transform(player, heatblast),
                "Heatblast transformation failed");
        long expiresAt = OmnitrixService.snapshot(player).expiresAt();
        helper.assertTrue(OmnitrixService.tick(player, expiresAt),
                "timeout did not force a revert");
        OmnitrixSnapshot state = OmnitrixService.snapshot(player);
        helper.assertTrue(state.rechargeUntil() > expiresAt,
                "timeout did not start the recharge window");
        helper.assertFalse(OmnitrixService.transform(player, heatblast),
                "transformation started during recharge");
        helper.succeed();
    }

    public static void manualRevertSkipsRecharge(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Heatblast transformation failed");
        helper.assertTrue(OmnitrixService.revert(player), "manual revert failed");
        helper.assertTrue(OmnitrixService.snapshot(player).rechargeUntil() == 0,
                "manual revert started a recharge window");
        helper.succeed();
    }

    public static void deathRevertsToHuman(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Heatblast transformation failed");
        helper.assertTrue(OmnitrixService.onRespawn(player, true).currentAlien() != null,
                "returning from the End reverted the player");
        helper.assertTrue(OmnitrixService.onRespawn(player, false).currentAlien() == null,
                "respawning after death kept the alien form");
        helper.succeed();
    }

    public static void masterControlHasNoRecharge(GameTestHelper helper, ServerPlayer player) {
        equipWithWatch(player);
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "pre-Master Control transformation failed");
        long expiresAt = OmnitrixService.snapshot(player).expiresAt();
        OmnitrixService.tick(player, expiresAt);
        helper.assertTrue(OmnitrixService.snapshot(player).rechargeUntil() > 0,
                "test did not enter recharge");
        helper.assertTrue(OmnitrixService.applyMasterControl(player),
                "Master Control could not be applied");
        helper.assertTrue(OmnitrixService.snapshot(player).rechargeUntil() == 0,
                "Master Control did not clear active recharge");
        helper.assertTrue(OmnitrixService.transform(player, BenTenMod.getModIdAndName("heatblast")),
                "Master Control player could not transform");
        long now = player.serverLevel().getServer().overworld().getGameTime();
        helper.assertFalse(OmnitrixService.tick(player, now + 72_000),
                "Master Control transformation timed out");
        OmnitrixSnapshot state = OmnitrixService.snapshot(player);
        helper.assertTrue(state.timeoutDisabled(), "Master Control did not disable timeout");
        helper.assertTrue(state.currentAlien() != null, "Master Control form was reverted");
        helper.assertTrue(state.rechargeUntil() == 0,
                "Master Control player received a recharge window");
        helper.succeed();
    }

    public static void guideIsGivenOnceOnJoin(GameTestHelper helper, ServerPlayer player) {
        OmnitrixService.onJoin(player);
        helper.assertTrue(guideCount(player) == 1, "first join did not give the field guide");
        OmnitrixService.onJoin(player);
        helper.assertTrue(guideCount(player) == 1, "field guide was given again on join");
        helper.assertTrue(
                OmnitrixSavedData.get(player.serverLevel()).getOrCreate(player).guideGiven(),
                "field guide delivery flag was not saved");
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

    private static int guideCount(ServerPlayer player) {
        int count = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(ItemInit.OMNITRIX_GUIDE.get())) {
                count += stack.getCount();
            }
        }
        return count;
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
