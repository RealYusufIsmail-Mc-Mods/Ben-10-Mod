# Progression design (updated 2026-10-14)

Status: steps 1 to 7 are implemented in this worktree. Forge data generation, build and both GameTest runs still need verification.

## What exists today

| Area | Today | Problem |
|---|---|---|
| Ores | 6 overworld ores, all 2 to 4 times as common as diamond; Imperium is the most common (11 veins/chunk) yet makes top-tier tools. Nether: Fire (1 in 3 chunks), Infinitum (1 in 4). | No value ladder: rarity doesn't match power, so players skip straight to the best gear. |
| Omnitrix | Step 3 added transformation and the dial; step 4 adds a crash-site pod and a Crafter recipe chain. The watch is equipped permanently by right-clicking it. | Done in steps 3 to 5. |
| Omnitrix Crafter | Now crafts the Omnitrix chain and Ben 10 gear (alien armour, Imperium, Black diamond and Infinitum tools). | Done in step 4. |
| Alien armour | Heatblast, Four Arms, XLR8 and Legendary sets give potion effects. They used to be extreme (Speed 46, Strength 9). | Moved into data in step 2; nerfed 2026-10-14 (section 6). Legendary needs the full set. |
| Bosses | Kraab is a bounty-hunter mini-boss; Vilgax and his drones are boss entities. | The Chimera ship structure is deferred; Vilgax is summoned with a beacon. |
| Dimensions | Null Void: floating islands, Null Void-only crystal ore, fortress and Zs'Skayr. | Step 7 implementation is awaiting Forge data generation and cross-loader verification. |

## Proposed loop

```
Mine Ben 10 ores ─► Omnitrix Crafter ─► Omnitrix (3 starter aliens)
       ▲                                     │
       │                         scan creatures for DNA ─► more aliens
       │                                     │
  Nether ores ◄── Heatblast / gear     Kraab (mini-boss) ─► Vilgax (boss)
                                             │
                                Null Void Projector ─► Null Void dimension
                                             │
                                Zs'Skayr / prisoners ─► Master Control
```

### 1. Ore value ladder

Rarity follows power. Numbers are starting points for playtesting (vanilla for scale: iron ~10 veins/chunk, diamond ~1 vein per chunk deep down).

**Done (2026-10-14):** applied in `ModOreFeatures` / `ModOrePlacements`; placed and configured feature ids are unchanged. Final numbers:

| Ore | Vein size | Placement | Height |
|---|---|---|---|
| Ruby | 9 | 10 per chunk | triangle y -16 to 64 |
| Speed ore | 6 | 4 per chunk | uniform y 0 to 48 |
| Legendary | 9 | 4 per chunk | triangle y -48 to 16 |
| Imperium | 6, 50% discard on air | 5 per chunk | triangle y -64 to -16 |
| Black diamond | 4 | 3 per chunk | triangle y -64 to -32 |
| Omnitrix ore | 3 | 1 in 2 chunks | uniform y -64 to -48 |
| Fire ore (Nether) | 10 | 12 per chunk | uniform y 10 to 117 |
| Infinitum (Nether) | 3, 100% discard on air | 1 per chunk | uniform y 8 to 24 |

| Tier | Ore | Where | Proposed rarity | Used for |
|---|---|---|---|---|
| 1 | Ruby | overworld, y -16 to 64 | iron-like | early tools, food (ruby apple), crafter frame |
| 1 | Speed ore | overworld, y 0 to 48 | uncommon | XLR8 armour, speed items |
| 2 | Legendary | overworld, y -48 to 16 | gold-like | Legendary armour, Omnitrix casing |
| 3 | Imperium | deep, y -64 to -16 | diamond-like (was most common) | Imperium tools |
| 3 | Black diamond | deep, y -64 to -32 | rarer than diamond | Black diamond gear |
| 4 | Omnitrix ore | deep, y -64 to -48, small veins | rarest overworld ore | Omnitrix core |
| N | Fire ore | Nether | quartz-like | Heatblast gear, Heatblast DNA |
| N+ | Infinitum | Nether, ancient-debris rules (no air exposure) | netherite-like | top-tier gear, Null Void Projector |

All of this is data (placed feature JSON from the data run), so tuning is cheap.

### 2. Getting the Omnitrix

Both routes give the same `omnitrix_watch` item. The crash site is the rare early route; crafting is the reliable mid-game route:

- **Crash site** (about 1 in 300 chunks): a surface meteor crater with an Omnitrix pod in forest, plains, taiga and savanna biomes. Breaking the pod gives an `omnitrix_watch`.
- **Crafting**: Omnitrix ingots + Legendary casing + Black diamond lens → *Omnitrix core* in the Omnitrix Crafter → Omnitrix. Mid-game (needs tier 3 to 4 ores).
- The Omnitrix starts with **3 aliens**: Heatblast, Four Arms, XLR8, matching the existing armour sets.
- Right-click the watch once to equip it permanently; it stays equipped through death. With an empty main hand, right-click air or a block (or press **G**) to open the dial.
- **DNA unlocks**: sneak and right-click a source with an empty main hand to sample its DNA once. Scanning requires the equipped Omnitrix and is unavailable while transformed:

| Alien | DNA source |
|---|---|
| Wildmutt | wolf |
| Diamondhead | budding amethyst block |
| Grey Matter | frog |
| Stinkfly | bee |
| Ripjaws | guardian |
| Upgrade | iron golem |
| Ghostfreak | phantom |

- **Recalibration** (later): the Master Control (Null Void reward) removes the transformation timeout.

### 3. Omnitrix Crafter rework

Make it *the* Ben 10 workstation instead of a crafting-table copy:
- Ben 10 gear is crafted here: Omnitrix parts, alien armour and Imperium/Black diamond/Infinitum tools. Vanilla crafting keeps storage blocks, nugget and ingot conversions, smelting and simple conversions. The Null Void Projector belongs to a later step.
- Recipe book categories: *Omnitrix*, *Armour*, *Tools*, *Misc* (Forge; Fabric has no recipe book for custom stations, so JEI/REI/EMI integration is the better cross-loader answer later).
- Optional later: an Omnitrix "charging" slot that recharges a timed-out Omnitrix.

### 4. Bosses

**Step 6 implementation added (2026-10-14; verification pending):** Kraab now has mini-boss attributes, a ranged attack and boss bar. The first Omnitrix bind schedules one persistent Kraab hunter 2–5 game minutes later. Vilgax has two health-based phases, a ground slam, drone waves and an Overworld beacon summon. The ship structure is deferred.

| Boss | Role | Where | Drops / unlocks |
|---|---|---|---|
| Kraab | mini-boss: persistent bounty hunter with ranged bolts and melee | spawns 16–32 blocks from the player 2–5 game minutes after first equipping the Omnitrix | Kraab claw, used to craft the Vilgax beacon |
| **Vilgax** | main boss, 2 phases (ground slam, then drone summons) | summoned above a block in the Overworld with a crafted beacon; the ship structure is deferred | Vilgax core → Null Void Projector |
| Zs'Skayr | Null Void boss (Ghostfreak's species) | Null Void fortress | Master Control |

Vilgax and his drones use GeckoLib models and animations. Vilgax has an auto-glowing layer. Kraab retains its vanilla model and renderer.

### 5. Extra dimensions

**Step 7 implementation (2026-10-14; verification pending):** `bentenmod:null_void` uses the End's floating-island noise router with Null Void Stone and no fluids. Its fixed biome has dark purple colors, no rain, and Null Void Prisoner spawns. A rare fortress feature makes a brick arena with loot chests and one persistent Zs'Skayr. Zs'Skayr has 500 health, ranged shadow bolts, prisoner summons, a boss bar, and always drops Master Control.

The Null Void Projector is crafted in the Omnitrix Crafter from one Vilgax Core and two Infinitum, has 16 durability, and repairs with Null Crystals. It teleports between matching X/Z coordinates, saves each player's source dimension and exact position, and falls back to a 5x5 brick platform at Y=100 when no safe island surface is found. Master Control requires an equipped Omnitrix, unlocks every alien, and permanently removes its transformation timeout.

Why one: a dimension needs worldgen JSON, structures, mobs, a boss and a portal or teleport item. That's the biggest feature in this list. Primus (Omnitrix DNA vault) or Galvan Prime can follow if the Null Void works.

### 6. Balance pass (cheap once armour is data)

After step 2, armour effects live in `data/bentenmod/armor_sets/*.json`. Applied 2026-10-14: XLR8 Speed III (was XLVI), Four Arms Strength II + Jump Boost II (was Strength IX), Heatblast fire resistance + flight + no fall damage, Legendary Strength I + Resistance II, Jacket Resistance I. Regeneration is gone from every set.

## Suggested order

1. **Step 2 (in progress):** JSON aliens, ability system, data-driven armour sets (fixes the three armour bugs), GeckoLib in common.
2. **Step 3:** Omnitrix item + transformation (3 starter aliens), dial screen, timeout, the flash.
3. **Step 4 (in progress):** ore ladder + crash site + Crafter rework + Omnitrix recipe chain.
4. **Step 5 (in progress):** data-driven DNA scanning unlocks.
5. **Step 6:** Kraab mini-boss, then Vilgax.
6. **Step 7 (implemented):** Null Void dimension + Zs'Skayr.

Each step ships playable on both loaders.

## Decisions (2026-10-14)

1. Omnitrix acquisition: both the crash-site pod (starter) and the Crafter route give the same watch. Right-clicking it equips it permanently; it survives death and can't be removed.
2. Alien armour stays as gear for non-transformed players, nerfed (section 6).
3. DNA list as proposed; aliens unlock by scanning, not crafting. Scan by sneaking and right-clicking the source with an empty main hand while the Omnitrix is equipped.
4. Balance applied (the values in section 6).
5. The Null Void is the first dimension, built as endgame after the ore ladder and DNA unlocks.
6. Step 6 uses a crafted beacon to summon Vilgax; the Chimera ship structure is deferred. Kraab claw is a Vilgax beacon ingredient.
7. Step 7 uses End-style floating islands, with `minecraft:the_end` dimension effects and a fixed dark ambient light level of 0.1. The Null Void Projector stores a per-player return point; if it cannot find a safe surface it builds a small platform at Y=100. Fortress generation is a rare biome feature (one in 400 chunks); its chest loot includes Null Crystals, Infinitum and golden apples. Master Control removes transformation timeouts permanently for that player's equipped Omnitrix.
