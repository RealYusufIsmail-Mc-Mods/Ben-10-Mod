# Progression design (proposal, 2026-10-13)

Status: proposal for review. Nothing here is built yet except where marked. Decisions needed are at the end.

## What exists today

| Area | Today | Problem |
|---|---|---|
| Ores | 6 overworld ores, all 2 to 4 times as common as diamond; Imperium is the most common (11 veins/chunk) yet makes top-tier tools. Nether: Fire (1 in 3 chunks), Infinitum (1 in 4). | No value ladder: rarity doesn't match power, so players skip straight to the best gear. |
| Omnitrix | An `omnitrix` ingot and an unused `omnitrix_watch` item. No way to transform. | The mod's centrepiece doesn't exist as gameplay. |
| Omnitrix Crafter | A crafting-table copy with its own recipe book, but only 2 recipes use it (Omnitrix block, upgraded Imperium pickaxe). | The station has no reason to exist. |
| Alien armour | Heatblast, Four Arms, XLR8 and Legendary sets give potion effects. Effects are extreme (Speed 46, Strength 9), Four Arms never triggers, Legendary triggers from any single piece, Heatblast flight never turns off. | Fixed or moved into data in step 2 (see below). |
| Bosses | Kraab exists as a normal mob. Vilgax has a texture but no entity. | No goals beyond gear. |
| Dimensions | None. | — |

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

The Ben 10 origin is a pod that crashes in the woods. Offer that as the exciting route, with crafting as the reliable one:

- **Crash site** (rare overworld structure): a meteor crater with an Omnitrix pod. Breaking the pod gives a *dormant Omnitrix*. Optional; adds a "first find" moment.
- **Crafting**: Omnitrix ingots + Legendary casing + Black diamond lens → *Omnitrix core* in the Omnitrix Crafter → Omnitrix. Mid-game (needs tier 3 to 4 ores).
- The Omnitrix starts with **3 aliens**: Heatblast, Four Arms, XLR8, matching the existing armour sets.
- **DNA unlocks**: use the Omnitrix on a creature to sample its DNA once and unlock an alien. Lore-accurate and a reason to explore:

| Alien | DNA source (proposal) |
|---|---|
| Wildmutt | wolf |
| Diamondhead | amethyst geode (use on a budding amethyst block) |
| Grey Matter | frog |
| Stinkfly | bee |
| Ripjaws | guardian |
| Upgrade | iron golem |
| Ghostfreak | phantom, or only from the Null Void (riskier alien, later unlock) |

- **Recalibration** (later): the Master Control (Null Void reward) removes the transformation timeout.

### 3. Omnitrix Crafter rework

Make it *the* Ben 10 workstation instead of a crafting-table copy:
- All Ben 10 tech is crafted here: Omnitrix parts, alien armour, Imperium/Black diamond/Infinitum tools, Null Void Projector. Vanilla crafting keeps only storage blocks and simple conversions.
- Recipe book categories: *Omnitrix*, *Armour*, *Tools*, *Misc* (Forge; Fabric has no recipe book for custom stations, so JEI/REI/EMI integration is the better cross-loader answer later).
- Optional later: an Omnitrix "charging" slot that recharges a timed-out Omnitrix.

### 4. Bosses

| Boss | Role | Where | Drops / unlocks |
|---|---|---|---|
| Kraab (exists as mob) | mini-boss: bounty hunter with ranged bolts | spawns near crash sites after you pick up the Omnitrix | Kraab claw (armour material) |
| **Vilgax** (texture exists) | main boss, 2 phases (melee, then drone/robot summons) | summoned at a "Chimera Sui Generis" ship structure, or with a crafted beacon | Vilgax core → Null Void Projector |
| Zs'Skayr | Null Void boss (Ghostfreak's species) | Null Void fortress | Master Control |

Bosses are GeckoLib entities: the model and texture pipeline from `tools/alien-gen` already produces GeckoLib geo, animations and glow masks.

### 5. Extra dimensions

Start with **one**, the **Null Void** (the prison dimension from the show): floating islands in a dark void, escaped-prisoner mobs, a fortress with the Zs'Skayr boss, and Null Void-only materials. It's entered with the Null Void Projector (crafted from the Vilgax core plus Infinitum).

Why one: a dimension needs worldgen JSON, structures, mobs, a boss and a portal or teleport item. That's the biggest feature in this list. Primus (Omnitrix DNA vault) or Galvan Prime can follow if the Null Void works.

### 6. Balance pass (cheap once armour is data)

After step 2, armour effects live in `data/bentenmod/armor_sets/*.json`. Proposed values: XLR8 Speed III (was XLVI), Four Arms Strength II + Jump Boost II (was Strength IX), Heatblast fire resistance + flight, Legendary Strength I + Resistance II.

## Suggested order

1. **Step 2 (in progress):** JSON aliens, ability system, data-driven armour sets (fixes the three armour bugs), GeckoLib in common.
2. **Step 3:** Omnitrix item + transformation (3 starter aliens), dial screen, timeout, the flash.
3. **Step 4:** ore ladder + crafter rework + Omnitrix recipe chain.
4. **Step 5:** DNA scanning unlocks.
5. **Step 6:** Kraab mini-boss, then Vilgax.
6. **Step 7:** Null Void dimension + Zs'Skayr.

Each step ships playable on both loaders.

## Decisions needed

1. Crash-site pod, crafting, or both for getting the Omnitrix?
2. Keep the existing alien armour sets alongside transformation, or retire them once the Omnitrix works?
3. DNA scanning: is the creature list above right?
4. Balance: apply the proposed values in step 2, or keep the current extreme ones until playtesting?
5. Null Void as the first dimension?
