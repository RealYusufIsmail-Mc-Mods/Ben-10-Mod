# Aliens, armour sets and abilities (data reference)

Aliens and armour-set bonuses are data, not Java. Files live in `common/src/main/resources/data/bentenmod/` (or any data pack) and reload with `/reload`. A file that fails to parse is logged (`Skipping aliens bentenmod:foo: ...`) and skipped; the rest still load.

## Abilities

Passive powers, used by both aliens and armour sets. Each is an object with a `type`:

| type | Fields | Effect |
|---|---|---|
| `bentenmod:effect` | `effect` (e.g. `minecraft:speed`), `amplifier` (default 0 = level I) | keeps the potion effect on the player, hidden particles, icon shown |
| `bentenmod:flight` | `speed` (default 0.05 = creative speed) | creative-style flight; removed when the source ends (unless creative/spectator) |
| `bentenmod:attribute` | `attribute` (e.g. `minecraft:generic.attack_damage`), `operation` (`addition`, `multiply_base`, `multiply_total`; default `addition`), `amount` | attribute modifier while active; health is clamped when it ends |
| `bentenmod:no_fall_damage` | no fields | cancels fall damage |

Abilities start when their source becomes active, tick every server tick, and stop when it ends (`AbilityTicker`). New types go in `common/.../ability/type/` and are listed in `AbilityTypes`.

## Armour sets: `data/<ns>/armor_sets/<name>.json`

```json
{
  "pieces": {
    "head": "bentenmod:heatblast_helmet",
    "chest": "bentenmod:heatblast_chestplate",
    "legs": "bentenmod:heatblast_leggings",
    "feet": "bentenmod:heatblast_boots"
  },
  "abilities": [
    { "type": "bentenmod:effect", "effect": "minecraft:fire_resistance" },
    { "type": "bentenmod:flight", "speed": 0.1 }
  ]
}
```

Every listed piece must be worn. A set can list fewer slots (the jacket lists only `chest`). Armour items themselves are plain `ArmorItem`s; all behaviour is here.

## Aliens: `data/<ns>/aliens/<id>.json`

```json
{
  "width": 0.7,
  "height": 2.1,
  "unlocked_by_default": true,
  "duration_ticks": 6000,
  "dna_sources": {
    "entities": ["minecraft:wolf"],
    "blocks": ["minecraft:budding_amethyst"]
  },
  "abilities": [
    { "type": "bentenmod:effect", "effect": "minecraft:fire_resistance" },
    { "type": "bentenmod:flight", "speed": 0.06 }
  ]
}
```

| Field | Default | Meaning |
|---|---|---|
| `width`, `height` | required | hitbox in blocks while transformed |
| `abilities` | `[]` | passive abilities while transformed |
| `unlocked_by_default` | `false` | a new Omnitrix starts with this alien (currently Heatblast, Four Arms, XLR8) |
| `duration_ticks` | `6000` (5 min) | how long a transformation lasts before the Omnitrix times out |
| `dna_sources` | empty entity and block lists | entity type and block ids that can be scanned to unlock this alien |

`dna_sources` is optional. A player holding the Omnitrix can scan a listed living entity or block
while not transformed. The server persists and syncs the unlock; repeated scans do not add a second
unlock.

The built-in scan sources are Wildmutt (`minecraft:wolf`), Diamondhead
(`minecraft:budding_amethyst`), Grey Matter (`minecraft:frog`), Stinkfly (`minecraft:bee`), Ripjaws
(`minecraft:guardian`), Upgrade (`minecraft:iron_golem`) and Ghostfreak (`minecraft:phantom`).

The alien's id (`bentenmod:heatblast`) also selects its client assets through `AlienGeoModel`: `geo/alien/<id>.geo.json`, `textures/entity/alien/<id>.png` (+ `_glowmask`), `animations/<id>.animation.json`. Those come from `tools/alien-gen` (see `docs/assets.md`).

Aliens and transformation are implemented in step 3. Their DNA sources are loaded with the alien
definitions and checked by the GameTests.

## Current values

Armour sets are gear for non-transformed players and were nerfed on 2026-10-14 (XLR8 Speed III, Four Arms Strength II + Jump Boost II, Heatblast fire resistance + flight + no fall damage, Legendary Strength I + Resistance II, Jacket Resistance I). Three old bugs are fixed: the Four Arms set works, the Legendary set needs every piece, and Heatblast flight ends when the armour comes off. Rationale is in `docs/design/progression.md`.
