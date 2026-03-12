# Ben 10 Mod — Omnitrix Development Plan

> **For AI coding agents (Sonnet):** Follow this plan phase-by-phase, task-by-task. Each task is sized for a single coding session. Do not skip tasks — later phases depend on earlier ones.

---

## Session Progress Tracker

### ✅ DONE (Session 1 — 2026-03-12)

**Phase 1: Core Infrastructure — ALL 9 tasks complete**
- [x] 1.1 `AlienType` enum (10 aliens, display names, icon paths, timers, hitbox dims)
- [x] 1.2 `IOmnitrixData` capability interface
- [x] 1.3 `OmnitrixDataImpl` + `OmnitrixDataStorage` + `OmnitrixDataProvider`
- [x] 1.4 `CapabilityHandler` (attaches cap, death/respawn handling, login sync)
- [x] 1.5 `OmnitrixItem` class; `ItemInit.OMNITRIX` updated to use it
- [x] 1.6 `PacketHandler` + `CTransformPacket` + `CRevertPacket` + `SSyncOmnitrixPacket`
- [x] 1.7 `OmnitrixConfig` (ForgeConfigSpec: duration, cooldown, HP threshold, creative bypass)
- [x] 1.8 `OmnitrixTickHandler` (ticks cap, calls ability hooks, 20t sync, low-HP revert)
- [x] 1.9 Capability persistence on death (aliens kept, transform cleared) and dimension change

**Phase 2: Transformation — partially complete**
- [x] 2.1 `AlienAbility` interface + `AlienAbilityRegistry`
- [x] 2.2 Ability system wired into tick handler (`onTick`, `onRevert`, `onAttack`) and `CTransformPacket` (`onTransform`)
- [ ] 2.3 `AlienRenderHandler` — player visual override (GeckoLib model rendering while transformed) — **NOT STARTED**
- [ ] 2.4 `TransformationEventHandler` — hitbox size changes via `EntityEvent.Size` — **NOT STARTED**
- [ ] 2.5 `SoundInit` — sound event registry + `sounds.json` — **NOT STARTED**
- [x] 2.6 `KeybindsInit` updated (OMNITRIX_KEY=O, REVERT_KEY=R); `InputEvents` rewritten
- [ ] 2.7 `OmnitrixHudOverlay` — alien icon + timer bar in corner — **NOT STARTED**
- [ ] 2.8 Multiplayer sync for visual rendering — **NOT STARTED**

**Alien abilities — ALL 10 complete**
- [x] `HeatblastAbility` (fire, flight, fireball)
- [x] `XLR8Ability` (speed, dash, momentum damage)
- [x] `FourArmsAbility` (strength, ground slam)
- [x] `DiamondheadAbility` (armor, crystal shard)
- [x] `GreyMatterAbility` (small hitbox, analysis)
- [x] `StinkflyAbility` (flight, slow projectile)
- [x] `RipjawsAbility` (water breathing, aquatic bonuses)
- [x] `GhostfreakAbility` (invisibility, phasing)
- [x] `WildmuttAbility` (tracking, howl)
- [x] `UpgradeAbility` (tech drops, resistance)

**Phase 3: GUI — partially complete**
- [x] 3.1 `OmnitrixScreen` rewritten as full radial dial (10 slots, locked overlay, confirmation animation)
- [ ] 3.2 Alien icon textures — **USER MUST GENERATE** (see texture list below)
- [ ] 3.3 Keybind lang keys — **NOT STARTED**
- [ ] 3.4 Selection confirmation animation — **DONE INLINE** in OmnitrixScreen

**Phase 5: Progression — partially complete**
- [ ] 5.1 Omnitrix crafting recipe — **NOT STARTED**
- [x] 5.2 `AlienDNAItem` + 10 DNA items in `ItemInit`
- [ ] 5.3 DNA loot modifiers (mob drops) — **NOT STARTED**
- [ ] 5.4 Progression docs — **NOT STARTED**
- [ ] 5.5 `PrimusCoreItem` (Vilgax boss drop) — **NOT STARTED**

---

### ⏳ REMAINING

**Next session should start here (in order):**
1. **2.3** `AlienRenderHandler` — render GeckoLib alien model on transformed player
2. **2.4** `TransformationEventHandler` — hitbox changes
3. **2.5** `SoundInit` + `sounds.json` + wire sounds into transform/revert
4. **2.7** `OmnitrixHudOverlay` — timer bar + alien icon HUD
5. **Phase 4A** — All 10 alien GeckoLib model classes (needs geo.json assets first)
6. **Phase 4B** — All 10 alien geo.json + animation.json + textures
7. **5.1** Omnitrix crafting recipe (Ben Ten Table)
8. **5.3** DNA loot modifiers
9. **5.5** PrimusCoreItem
10. **Phase 6** — Sounds, advancements, lang keys

---

### 🖼️ TEXTURES THE USER MUST GENERATE

All paths are under `src/main/resources/assets/bentenmod/`:

**GUI textures (used immediately — game will show missing texture without these):**
| File | Size | Description |
|------|------|-------------|
| `textures/gui/omnitrix_dial.png` | 64×64 | Central Omnitrix emblem (green hourglass symbol on black circle) |
| `textures/gui/alien_locked.png` | 32×32 | Grey lock icon overlay for locked aliens |
| `textures/gui/omnitrix_hud.png` | 64×32 | HUD frame/bar background (used for HUD overlay, Phase 2.7) |

**Alien icon textures for GUI radial dial (32×32 each):**
| File | Alien | Colors / Style |
|------|-------|---------------|
| `textures/gui/aliens/heatblast_icon.png` | Heatblast | Orange/red flaming rock creature |
| `textures/gui/aliens/xlr8_icon.png` | XLR8 | Blue velociraptor with visor helmet |
| `textures/gui/aliens/fourarms_icon.png` | Four Arms | Red 4-armed hulking alien |
| `textures/gui/aliens/diamondhead_icon.png` | Diamondhead | Teal/green crystal humanoid |
| `textures/gui/aliens/greymatter_icon.png` | Grey Matter | Tiny grey alien with big head |
| `textures/gui/aliens/stinkfly_icon.png` | Stinkfly | Green insect with wings |
| `textures/gui/aliens/ripjaws_icon.png` | Ripjaws | Blue/green fish-like alien with big jaw |
| `textures/gui/aliens/ghostfreak_icon.png` | Ghostfreak | White/grey ghostly alien with eye |
| `textures/gui/aliens/wildmutt_icon.png` | Wildmutt | Orange wolf-like alien, no eyes |
| `textures/gui/aliens/upgrade_icon.png` | Upgrade | Black alien with yellow circuit pattern |

**Item textures (16×16 each, for DNA items):**
| File | Description |
|------|-------------|
| `textures/item/alien_dna_heatblast.png` | Orange vial |
| `textures/item/alien_dna_xlr8.png` | Blue vial |
| `textures/item/alien_dna_four_arms.png` | Red vial |
| `textures/item/alien_dna_diamondhead.png` | Teal vial |
| `textures/item/alien_dna_grey_matter.png` | Grey vial |
| `textures/item/alien_dna_stinkfly.png` | Green vial |
| `textures/item/alien_dna_ripjaws.png` | Dark blue vial |
| `textures/item/alien_dna_ghostfreak.png` | White/pale vial |
| `textures/item/alien_dna_wildmutt.png` | Orange/brown vial |
| `textures/item/alien_dna_upgrade.png` | Black vial with yellow circuits |

**Alien model textures (for Phase 4, when GeckoLib models are added):**
| File | Size | Description |
|------|------|-------------|
| `textures/model/aliens/heatblast.png` | 64×64 | Texture map for Heatblast geo.json model |
| `textures/model/aliens/xlr8.png` | 64×64 | Texture map for XLR8 model |
| `textures/model/aliens/fourarms.png` | 64×64 | Texture map for Four Arms model |
| `textures/model/aliens/diamondhead.png` | 64×64 | Texture map for Diamondhead model |
| `textures/model/aliens/greymatter.png` | 64×64 | Texture map for Grey Matter model |
| `textures/model/aliens/stinkfly.png` | 64×64 | Texture map for Stinkfly model |
| `textures/model/aliens/ripjaws.png` | 64×64 | Texture map for Ripjaws model |
| `textures/model/aliens/ghostfreak.png` | 64×64 | Texture map for Ghostfreak model |
| `textures/model/aliens/wildmutt.png` | 64×64 | Texture map for Wildmutt model |
| `textures/model/aliens/upgrade.png` | 64×64 | Texture map for Upgrade model |

> **Priority order:** GUI textures first (needed to play without crashes), then item textures, then model textures (only needed once Phase 4 GeckoLib models are added).

---

## Project Context

| Key | Value |
|-----|-------|
| **Minecraft Version** | 1.16.5 |
| **Forge Version** | 36.2.34 |
| **Java Version** | 8 |
| **GeckoLib Version** | 3 (geckolib-forge-1.16.5:3.0.106) |
| **Mod ID** | `bentenmod` |
| **Base Package** | `io.github.realyusufismail.bentenmod` |
| **License Header** | Apache 2.0 (see spotless config in build.gradle) |
| **Code Style** | palantirJavaFormat via Spotless |

### What Already Exists
- **Entities:** Vilgax (GeckoLib boss, 300 HP), Kraab (ranged attacker with bolt projectile)
- **Ores & Materials:** Legendary, Omnitrix, Ruby, Black Diamond, Infinitum, Imperium, Fire, Speed — each with ore/deepslate/block/ingot
- **Armor Sets:** Heatblast (fire resist + flight), XLR8 (super speed), Legendary, Black Diamond, Infinitum, Four Arms (commented out)
- **Tools:** Full tool sets per material tier
- **Custom Crafting:** "Ben Ten Table" (TableBlock/Container/Screen/TileEntity) with custom recipe type
- **Items:** `OMNITRIX` item (`OmnitrixItem` with GUI opening), `OMNITRIX_WATCH` (plain Item), custom foods, 10 `AlienDNAItem` items
- **Omnitrix System:** Full capability system (IOmnitrixData/OmnitrixDataImpl/Provider/Storage), network packets (CTransformPacket/CRevertPacket/SSyncOmnitrixPacket), config (OmnitrixConfig), tick handler (OmnitrixTickHandler), keybinds (O/R keys), input events, OmnitrixScreen (radial GUI with confirmation animation)
- **Alien System:** AlienType enum (10 aliens), AlienAbility interface, AlienAbilityRegistry, all 10 ability implementations
- **Rendering:** AlienRenderHandler (uses GeoModelProvider<Void> + manual bone rendering), 10 alien model classes (GeoModelProvider<Void>)
- **Assets:** All 10 geo.json models, all 10 animation.json files, all 10 alien textures, all 10 GUI icon textures, omnitrix_dial.png, omnitrix_hud.png, alien_locked.png, HeatBlast.bbmodel + feedBack.bbmodel, Vilgax full geo.json + animation.json
- **Data Gen:** Full pipeline (recipes, tags, loot, advancements, lang)

### Asset Notes
- **2D textures** (items, icons): User has access to an AI texture generator
- **3D models** (GeckoLib geo.json): AI agent can generate Blockbench-format JSON models
- All GeckoLib models use `.geo.json` format, animations use `.animation.json`

### Critical Technical Note — GeckoLib3 and PlayerEntity
GeckoLib3's `AnimatedGeoModel<T>` requires `T extends IAnimatable`. `PlayerEntity` does NOT implement `IAnimatable`.
**Solution already implemented:** All 10 alien model classes extend `GeoModelProvider<Void>` (no IAnimatable constraint). `AlienRenderHandler` does manual recursive bone rendering copied from `IGeoRenderer`'s logic. This means **no GeckoLib animations play** — models render statically. To add animations later, either:
1. Create a dummy `IAnimatable` wrapper, or
2. Create invisible companion entities that extend `IAnimatable` and render on those

---

## Current Status (as of 2026-03-12)

### ✅ COMPLETED — Phase 1: Core Omnitrix Infrastructure
All 9 tasks done:
- ✅ Task 1.1 — `AlienType` enum with 10 aliens, dimensions, timers, icon textures
- ✅ Task 1.2 — `IOmnitrixData` capability interface
- ✅ Task 1.3 — `OmnitrixDataImpl`, `OmnitrixDataStorage`, `OmnitrixDataProvider`
- ✅ Task 1.4 — `CapabilityHandler` (attach to players, clone on death, sync on login)
- ✅ Task 1.5 — `OmnitrixItem` (opens GUI on right-click, tooltip with unlocked aliens)
- ✅ Task 1.6 — Network packets (`PacketHandler`, `CTransformPacket`, `CRevertPacket`, `SSyncOmnitrixPacket`)
- ✅ Task 1.7 — `OmnitrixConfig` (transform duration, cooldown, low HP revert, creative bypass)
- ✅ Task 1.8 — `OmnitrixTickHandler` (ticks capability, calls ability onTick/onRevert, syncs to client)
- ✅ Task 1.9 — Capability persistence on death/respawn (in CapabilityHandler)

### ✅ COMPLETED — Phase 2: Transformation System (partial)
- ✅ Task 2.1 — `AlienAbility` interface + `AlienAbilityRegistry` with all 10 aliens registered
- ✅ Task 2.2 — Ability system wired into tick handler (onTick, onRevert, onAttack via LivingAttackEvent, onRightClick via PlayerInteractEvent)
- ✅ Task 2.3 — `AlienRenderHandler` (cancels player render, renders GeckoLib geo model manually, supports multiplayer via PLAYER_ALIEN_MAP)

### ❌ NOT STARTED — Phase 2 remaining tasks:
- ❌ Task 2.4 — `TransformationEventHandler.java` (hitbox/size changes via EntityEvent.Size) — **NEEDED**
- ❌ Task 2.5 — `SoundInit.java` + `sounds.json` (sound effects) — **NEEDED**
- ❌ Task 2.6 — Revert mechanics (low HP auto-revert with LivingHurtEvent) — OmnitrixTickHandler has TODO for warning sound
- ❌ Task 2.7 — `OmnitrixHudOverlay.java` (HUD showing alien icon, timer bar, cooldown) — **NEEDED**
- ❌ Task 2.8 — Multiplayer sync for visual rendering (broadcast to TRACKING_ENTITY_AND_SELF) — partially done via SSyncOmnitrixPacket but only syncs to self

### ✅ COMPLETED — Phase 3: Omnitrix GUI
- ✅ Task 3.1 — `OmnitrixScreen` (radial selection, unlocked/locked aliens, hover, click to transform)
- ✅ Task 3.2 — All 10 alien icon textures (32×32) in `textures/gui/aliens/`
- ✅ Task 3.3 — `KeybindsInit` (OMNITRIX_KEY=O, REVERT_KEY=R) + `InputEvents` wired up
- ✅ Task 3.4 — Selection confirmation animation in OmnitrixScreen (green flash, scale up, 15-tick delay)

### ✅ COMPLETED — Phase 4: Alien Forms (partial)
All 10 ability implementations exist in `core/omnitrix/ability/aliens/`:
- ✅ Task 4.1A — HeatblastAbility (fire resist, flight, fireball on right-click, fire on attack)
- ✅ Task 4.2A — XLR8Ability (speed, dash on right-click, momentum attacks)
- ✅ Task 4.3A — FourArmsAbility (strength, knockback resist, ground slam on right-click)
- ✅ Task 4.4A — DiamondheadAbility (armor, damage reflect) — **NOTE: CrystalShardEntity NOT created yet**
- ✅ Task 4.5A — GreyMatterAbility (small hitbox, glowing analysis, XP multiplier)
- ✅ Task 4.6A — StinkflyAbility (flight, slowness attack) — **NOTE: SlimeProjectileEntity NOT created yet**
- ✅ Task 4.7A — RipjawsAbility (water breathing, swim speed, land weakness)
- ✅ Task 4.8A — GhostfreakAbility (invisibility, phasing, scare attack)
- ✅ Task 4.9A — WildmuttAbility (night vision, tracking, pounce bonus)
- ✅ Task 4.10A — UpgradeAbility (tech drain, machine interaction)

All 10 model files (B tasks) exist:
- ✅ 10 `GeoModelProvider<Void>` model classes in `client/model/aliens/`
- ✅ 10 `.geo.json` files in `assets/bentenmod/geo/aliens/`
- ✅ 10 `.animation.json` files in `assets/bentenmod/animations/aliens/`
- ✅ 10 texture `.png` files in `assets/bentenmod/textures/model/aliens/`

### ❌ NOT STARTED — Phase 4 missing entities:
- ❌ `CrystalShardEntity.java` (Diamondhead projectile) + registration in EntityTypesInit
- ❌ `SlimeProjectileEntity.java` (Stinkfly projectile) + registration in EntityTypesInit

### ❌ NOT STARTED — Phase 5: Crafting & Progression
- ❌ Task 5.1 — Omnitrix crafting recipe (Ben Ten Table recipe)
- ❌ Task 5.2 — AlienDNAItem already exists ✅, but textures for DNA items (`textures/item/alien_dna_*.png`) need generating by user
- ❌ Task 5.3 — `AlienDNALootModifier.java` (loot table integration for DNA drops from mobs)
- ❌ Task 5.4 — Progression documentation (no code needed)
- ❌ Task 5.5 — `PrimusCoreItem.java` (boss drop, resets cooldown + unlocks random alien)

### ❌ NOT STARTED — Phase 6: Polish & Content
- ❌ Task 6.1 — Sound effect files (.ogg placeholders) + wiring sounds into transform/revert/tick
- ❌ Task 6.2 — Advancement tree (TransformationTrigger + new advancements in ModAdvancementProvider)
- ❌ Task 6.3 — Patchouli guide book entries
- ❌ Task 6.4 — JEI integration (check if Ben Ten Table recipes show already)
- ❌ Task 6.5 — Lang file updates (DNA item names, key names, GUI strings, alien names, advancement names)
- ❌ Task 6.6 — Cleanup (nerf HeatblastArmor/Xlr8Armor, uncomment FourArmsArmor)

---

## NEXT TASKS TO DO (Priority Order)

### Immediate (Phase 2 completion):
1. **Task 2.4** — Create `TransformationEventHandler.java` subscribing to `EntityEvent.Size`
2. **Task 2.5** — Create `SoundInit.java` + `sounds.json` (register sound events, placeholder .ogg files)
3. **Task 2.7** — Create `OmnitrixHudOverlay.java` (RenderGameOverlayEvent.Post)
4. **Task 2.6** — Wire low-HP auto-revert in OmnitrixTickHandler via LivingHurtEvent
5. **Task 2.8** — Fix multiplayer sync (broadcast SSyncOmnitrixPacket to TRACKING_ENTITY_AND_SELF)

### Then (Phase 4 entities):
6. **Task 4.4A** — Create `CrystalShardEntity.java` + register in EntityTypesInit
7. **Task 4.6A** — Create `SlimeProjectileEntity.java` + register in EntityTypesInit

### Then (Phase 5):
8. **Task 5.1** — Omnitrix crafting recipe
9. **Task 5.3** — AlienDNALootModifier
10. **Task 5.5** — PrimusCoreItem

### Then (Phase 6):
11. **Task 6.5** — Lang file updates
12. **Task 6.2** — Advancement tree
13. **Task 6.6** — Cleanup (nerf armor sets)
14. **Task 6.1** — Sound wiring (once .ogg files exist)

---

## Detailed Task Descriptions

### Task 2.4 — `TransformationEventHandler.java` (Hitbox / Size Changes)

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/common/events/TransformationEventHandler.java`

Subscribe to `EntityEvent.Size` on the FORGE bus. When a player is transformed, override `event.setNewSize()` and `event.setNewEyeHeight()` using the width/height from `AlienType`.

```java
@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE)
public class TransformationEventHandler {

    @SubscribeEvent
    public static void onEntitySize(EntityEvent.Size event) {
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntity();
        player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
            if (data.isTransformed() && data.getCurrentAlien() != null) {
                AlienType alien = data.getCurrentAlien();
                event.setNewSize(EntitySize.scalable(alien.getWidth(), alien.getHeight()));
                event.setNewEyeHeight(alien.getHeight() * 0.85f);
            }
        });
    }
}
```

After creating, call `player.refreshDimensions()` in `CTransformPacket` handler and `OmnitrixTickHandler` on revert so hitbox updates immediately.

---

### Task 2.5 — `SoundInit.java` + `sounds.json`

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/core/init/SoundInit.java`

Register sound events using `DeferredRegister<SoundEvent>`:
- `OMNITRIX_TRANSFORM` — played when player transforms
- `OMNITRIX_REVERT` — played when player reverts
- `OMNITRIX_TIMEOUT_WARNING` — played at 10 seconds remaining
- `OMNITRIX_DIAL_CLICK` — played when selecting alien in GUI
- `OMNITRIX_COOLDOWN_READY` — played when cooldown finishes

```java
public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BenTenMod.MOD_ID);

    public static final RegistryObject<SoundEvent> OMNITRIX_TRANSFORM =
            SOUNDS.register("omnitrix_transform", () -> new SoundEvent(
                    new ResourceLocation(BenTenMod.MOD_ID, "omnitrix_transform")));
    // ... etc
}
```

Register `SoundInit.SOUNDS.register(bus)` in `BenTenMod` constructor.

**New file:** `src/main/resources/assets/bentenmod/sounds.json`

```json
{
  "omnitrix_transform": { "sounds": [{ "name": "bentenmod:omnitrix_transform", "stream": false }] },
  "omnitrix_revert": { "sounds": [{ "name": "bentenmod:omnitrix_revert", "stream": false }] },
  "omnitrix_timeout_warning": { "sounds": [{ "name": "bentenmod:omnitrix_timeout_warning", "stream": false }] },
  "omnitrix_dial_click": { "sounds": [{ "name": "bentenmod:omnitrix_dial_click", "stream": false }] },
  "omnitrix_cooldown_ready": { "sounds": [{ "name": "bentenmod:omnitrix_cooldown_ready", "stream": false }] }
}
```

Create placeholder `.ogg` files (silent 0.1s mono OGG) in `assets/bentenmod/sounds/`. The user will replace them with real sounds later. If you cannot create binary `.ogg` files, create the `sounds.json` and `SoundInit.java` only and leave a note that `.ogg` files need to be added manually.

---

### Task 2.6 — Low-HP Auto-Revert

In `OmnitrixTickHandler`, add a `LivingHurtEvent` handler:

```java
@SubscribeEvent
public void onLivingHurt(LivingHurtEvent event) {
    if (!(event.getEntityLiving() instanceof ServerPlayerEntity)) return;
    ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
    player.getCapability(CapabilityHandler.OMNITRIX_CAP).ifPresent(data -> {
        if (data.isTransformed()) {
            float healthAfter = player.getHealth() - event.getAmount();
            if (OmnitrixConfig.ENABLE_LOW_HP_REVERT.get() && healthAfter <= 4.0f) {
                AlienType alien = data.getCurrentAlien();
                data.revert();
                if (alien != null) {
                    AlienAbility ability = AlienAbilityRegistry.get(alien);
                    if (ability != null) ability.onRevert(player);
                }
                player.refreshDimensions();
                // Play revert sound if SoundInit exists
                // Sync to client
                SSyncOmnitrixPacket syncPacket = new SSyncOmnitrixPacket(data.serializeNBT(), player.getId());
                PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), syncPacket);
            }
        }
    });
}
```

Also wire the TODO warning sound in `onPlayerTick` once `SoundInit` exists.

---

### Task 2.7 — `OmnitrixHudOverlay.java`

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/client/renderer/OmnitrixHudOverlay.java`

Subscribe to `RenderGameOverlayEvent.Post` (element type `ALL`), client-side only.

When the local player is transformed, render in the top-left corner:
1. The alien's 32×32 icon texture (from `AlienType.getIconTexture()`)
2. The alien's display name (from `AlienType.getDisplayName()`)
3. A timer bar showing `transformTicksRemaining / transformDurationTicks` as a green->red gradient bar
4. When on cooldown (not transformed), show a cooldown bar with "Cooldown: Xs" text

Use `@EventBusSubscriber(modid = BenTenMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)`.

Render with `AbstractGui.blit()` for textures and `font.draw()` for text. Use `minecraft.getTextureManager().bind()` to bind textures.

The HUD texture `textures/gui/omnitrix_hud.png` already exists.

---

### Task 2.8 — Multiplayer Sync Fix

Currently `SSyncOmnitrixPacket` only sends to `PacketDistributor.PLAYER` (self). Change to `PacketDistributor.TRACKING_ENTITY_AND_SELF` so other players see transformations.

In `OmnitrixTickHandler.onPlayerTick()`, change:
```java
// OLD:
PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), syncPacket);
// NEW:
PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), syncPacket);
```

In `SSyncOmnitrixPacket.handle()`, on the client side:
- If the entity ID matches the local player, update capability directly
- If it's another player, update `AlienRenderHandler.PLAYER_ALIEN_MAP` so they render as the alien

Also do the same in `CTransformPacket` and `CRevertPacket` handlers — after processing, broadcast sync packet to all tracking players.

---

### Task 4.4B — `CrystalShardEntity.java` (Diamondhead Projectile)

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/entity/CrystalShardEntity.java`

Extend `AbstractArrowEntity` or `ProjectileItemEntity`. On hit:
- Deal 6 damage
- Apply Slowness I for 3 seconds
- Play glass break sound

Register in `EntityTypesInit`:
```java
public static final RegistryObject<EntityType<CrystalShardEntity>> CRYSTAL_SHARD_ENTITY =
        ENTITY_TYPES.register("crystal_shard", () -> EntityType.Builder
                .<CrystalShardEntity>of(CrystalShardEntity::new, EntityClassification.MISC)
                .sized(0.25f, 0.25f).build("crystal_shard"));
```

Register a simple renderer in `BenTenMod.onClientSetup()`.

Update `DiamondheadAbility.onRightClick()` to spawn this entity instead of a TODO comment.

---

### Task 4.6B — `SlimeProjectileEntity.java` (Stinkfly Projectile)

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/entity/SlimeProjectileEntity.java`

Extend `ProjectileItemEntity`. On hit:
- Apply Slowness II for 5 seconds
- Apply Weakness I for 3 seconds
- Spawn slime particles

Register in `EntityTypesInit`:
```java
public static final RegistryObject<EntityType<SlimeProjectileEntity>> SLIME_PROJECTILE_ENTITY =
        ENTITY_TYPES.register("slime_projectile", () -> EntityType.Builder
                .<SlimeProjectileEntity>of(SlimeProjectileEntity::new, EntityClassification.MISC)
                .sized(0.25f, 0.25f).build("slime_projectile"));
```

Register renderer in `BenTenMod.onClientSetup()`.

Update `StinkflyAbility.onRightClick()` to spawn this entity instead of a TODO comment.

---

### Task 5.1 — Omnitrix Crafting Recipe

Create a Ben Ten Table recipe JSON in `src/main/resources/data/bentenmod/recipes/` that crafts the Omnitrix.

Recipe layout (Ben Ten Table is a 3×3 custom crafting):
```
[Omnitrix Ingot] [Ruby] [Omnitrix Ingot]
[Imperium]       [Legendary] [Imperium]
[Omnitrix Ingot] [Black Diamond] [Omnitrix Ingot]
```
Output: 1× Omnitrix

If the existing recipe serializer supports JSON data-driven recipes, create the JSON. Otherwise, add it programmatically in the data gen pipeline (`ModRecipeProvider`).

---

### Task 5.3 — `AlienDNALootModifier.java`

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/common/loot/AlienDNALootModifier.java`

Use Forge's `GlobalLootModifierProvider` system. When certain mobs die, they have a chance to drop an Alien DNA item:
- Blaze → Heatblast DNA (10% chance)
- Enderman → Ghostfreak DNA (10%)
- Drowned → Ripjaws DNA (10%)
- Spider → Stinkfly DNA (10%)
- Zombie → Four Arms DNA (10%)
- Skeleton → Diamondhead DNA (10%)
- Silverfish → Grey Matter DNA (10%)
- Wolf → Wildmutt DNA (10%)
- Creeper → Upgrade DNA (10%)
- Phantom → XLR8 DNA (10%)
- Vilgax → Random DNA (100%, guaranteed)

Register via `data/forge/loot_modifiers/global_loot_modifiers.json`.

---

### Task 5.5 — `PrimusCoreItem.java`

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/item/PrimusCoreItem.java`

Special item dropped by Vilgax (add to Vilgax loot table). On right-click while holding:
1. Resets Omnitrix cooldown to 0
2. Unlocks a random locked alien
3. Consumed on use (stacksTo 1)
4. Epic rarity, glowing enchanted effect

Register in `ItemInit`:
```java
public static final RegistryObject<Item> PRIMUS_CORE = ITEMS.register("primus_core",
        () -> new PrimusCoreItem(new Item.Properties().tab(MainItemGroup.MAIN).stacksTo(1).rarity(Rarity.EPIC)));
```

---

### Task 6.1 — Sound Wiring

Wire `SoundInit` sounds into the game logic:
- `CTransformPacket` handler → play `OMNITRIX_TRANSFORM` at player position
- `CRevertPacket` handler → play `OMNITRIX_REVERT`
- `OmnitrixTickHandler` at 200 ticks remaining → play `OMNITRIX_TIMEOUT_WARNING`
- `OmnitrixTickHandler` when cooldown hits 0 → play `OMNITRIX_COOLDOWN_READY`
- `OmnitrixScreen` on alien click → play `OMNITRIX_DIAL_CLICK`

Use `player.level.playSound(null, player.blockPosition(), sound, SoundCategory.PLAYERS, 1.0f, 1.0f)` on server.

---

### Task 6.2 — Advancement Tree

**New file:** `src/main/java/io/github/realyusufismail/bentenmod/core/init/TransformationTrigger.java`

Custom `CriteriaTriggerInstance` that fires when a player transforms for the first time, or transforms into a specific alien.

Add advancements in `ModAdvancementProvider`:
- "It's Hero Time!" — Transform for the first time (root advancement)
- "Alien X-perience" — Transform into all 10 aliens
- Per-alien advancements: "Burning Up" (Heatblast), "Speed Demon" (XLR8), etc.
- "Omnitrix Master" — Unlock all 10 aliens
- "Primus Power" — Use a Primus Core

---

### Task 6.5 — Lang File Updates

Update `src/generated/resources/assets/bentenmod/lang/en_us.json` (or the data gen lang provider) to include:
- All 10 `item.bentenmod.alien_dna_*` entries (e.g., "Heatblast DNA Sample")
- `key.bentenmod.omnitrix` → "Open Omnitrix"
- `key.bentenmod.revert` → "Revert Transformation"
- `key.categories.bentenmod` → "Ben 10 Mod"
- `item.bentenmod.primus_core` → "Primus Core"
- All advancement titles and descriptions
- Alien display names for GUI: `alien.bentenmod.heatblast` → "Heatblast", etc.

---

### Task 6.6 — Cleanup

1. **Nerf HeatblastArmor:** Reduce fire resist duration from permanent to 10 seconds. Remove creative flight — only Heatblast alien form should fly.
2. **Nerf Xlr8Armor:** Reduce speed boost from Speed V to Speed II.
3. **Uncomment FourArmsArmor:** Find the commented-out Four Arms armor code and enable it with Strength I and Resistance I.
4. **Code cleanup:** Remove any `System.out.println` debug statements, fix TODO comments, ensure all `@OnlyIn(Dist.CLIENT)` annotations are correct.

---

## File Map (Key Source Files)

| File | Purpose |
|------|---------|
| `BenTenMod.java` | Main mod class, registration |
| `core/omnitrix/AlienType.java` | Enum of 10 aliens with stats |
| `core/omnitrix/OmnitrixItem.java` | Right-click opens GUI |
| `core/omnitrix/OmnitrixScreen.java` | Radial alien selection GUI |
| `core/omnitrix/AlienDNAItem.java` | DNA items that unlock aliens |
| `core/capability/IOmnitrixData.java` | Capability interface |
| `core/capability/OmnitrixDataImpl.java` | Capability implementation |
| `core/capability/CapabilityHandler.java` | Attach/clone/sync capability |
| `core/capability/OmnitrixDataProvider.java` | Capability provider |
| `core/capability/OmnitrixDataStorage.java` | NBT serialization |
| `core/network/PacketHandler.java` | Network channel setup |
| `core/network/CTransformPacket.java` | Client→Server transform request |
| `core/network/CRevertPacket.java` | Client→Server revert request |
| `core/network/SSyncOmnitrixPacket.java` | Server→Client capability sync |
| `core/config/OmnitrixConfig.java` | Server config (durations, flags) |
| `core/init/KeybindsInit.java` | O and R keybinds |
| `core/init/ItemInit.java` | All item registrations |
| `core/init/EntityTypesInit.java` | Entity type registrations |
| `common/events/OmnitrixTickHandler.java` | Server tick logic |
| `common/events/InputEvents.java` | Client keybind handling |
| `core/omnitrix/ability/AlienAbility.java` | Ability interface |
| `core/omnitrix/ability/AlienAbilityRegistry.java` | Registry mapping AlienType→ability |
| `core/omnitrix/ability/aliens/*.java` | 10 ability implementations |
| `client/model/aliens/*.java` | 10 GeoModelProvider<Void> model classes |
| `client/renderer/AlienRenderHandler.java` | Cancel player render, render geo model |
| `client/renderer/VilgaxRenderer.java` | Vilgax GeckoLib renderer |
| `client/renderer/KraabRenderer.java` | Kraab GeckoLib renderer |

## Asset Map

| Path | Contents |
|------|----------|
| `assets/bentenmod/geo/aliens/*.geo.json` | 10 Blockbench geo models |
| `assets/bentenmod/animations/aliens/*.animation.json` | 10 animation files |
| `assets/bentenmod/textures/model/aliens/*.png` | 10 alien model textures |
| `assets/bentenmod/textures/gui/aliens/*_icon.png` | 10 alien GUI icons (32×32) |
| `assets/bentenmod/textures/gui/omnitrix_dial.png` | Omnitrix dial background |
| `assets/bentenmod/textures/gui/omnitrix_hud.png` | HUD overlay background |
| `assets/bentenmod/textures/gui/alien_locked.png` | Locked alien icon |
| `assets/bentenmod/geo/vilgax.geo.json` | Vilgax entity model |
| `assets/bentenmod/animations/vilgax.animation.json` | Vilgax animations |

