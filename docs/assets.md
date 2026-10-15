# 3D assets: aliens and the Omnitrix

Every alien model, texture, glow mask and animation, the Omnitrix watch model, and the Omnitrix dial and flash textures come from the Python generator in `tools/alien-gen/`. Edit the generator, not the PNGs: a re-export overwrites them.

The Omnitrix Crafter has a separate pixel-art generator: `python3 tools/crafter-assets/generate.py`. It writes the block face textures, screen, recipe button, and `docs/images/crafter.png`. The preview shows flat pixels, not an in-game render. Its authored block geometry is `models/block/omnitrix_crafter_device.json`; the Forge data provider generates the blockstate and item model for both loaders. Change the generator when changing its palette or pixel layout.

## Setup

```bash
python3 -m pip install -r tools/alien-gen/requirements.txt
```

## Commands

| Command | Writes |
|---|---|
| `python3 tools/alien-gen/gui_base.py` | `textures/gui/omnitrix.png` (dial and selection frame, icon rows empty), `textures/misc/omnitrix_flash.png` |
| `python3 tools/alien-gen/dial.py` | `textures/gui/omnitrix_wheel.png`: the turning bezel (left half) and hourglass core (right half) of the wheel dial |
| `python3 tools/alien-gen/export.py` | all `geo/`, `animations/`, alien and Omnitrix textures, and the hologram icon rows in `gui/omnitrix.png` |
| `python3 tools/alien-gen/export.py --animations-only [ids]` | only `animations/<id>.animation.json` for the given aliens (no baking, seconds); use after editing `animations()` or `fly_animation()` in `export.py` |
| `python3 tools/alien-gen/preview.py [ids]` | studio renders and `sheet.png` in `tools/alien-gen/out/` (gitignored) |
| `python3 tools/alien-gen/gif.py [ids]` | Ben-to-alien transformation clip in `tools/alien-gen/out/` |

Run `gui_base.py` before `export.py` if the dial PNG doesn't exist yet. `export.py` takes about a minute, mostly baking ambient occlusion.

## How it works

- `engine.py` holds the box-model authoring API (`Model.bone`, `Model.box`, `Model.sym` for mirrored pairs, `Model.decal`), procedural materials (`skin`, `magma`, `flame`, `crystal`, `metal`, `glow`, `circuitry`, `flat`), texture baking, GeckoLib export and a small software renderer used for previews.
- `aliens.py` defines one function per model. `ALIENS` lists the aliens that get exported.
- Baking packs every cube face into one atlas at 4 texels per model unit (`D` in `engine.py`). Each texel is shaded by its material, then by baked ambient occlusion, a height gradient and edge bevels, which gives the "rendered" look.
- Emissive texels (lava, flames, eyes, Omnitrix symbols, Upgrade's circuits) also go into `<id>_glowmask.png`, which GeckoLib's `AutoGlowingGeoLayer` draws at full brightness.
- Cubes with `alpha < 1` (Stinkfly's wings, Ghostfreak) need a translucent render type in the renderer.

## Conventions

- Units are model pixels (16 = one block). `+Y` is up, and the model faces north (`-Z`). The model's right side is `+X`.
- Bone names that the generic animations understand: `root`, `body`, `head`, `right_arm`, `left_arm`, `right_leg`, `left_leg`, `right_arm_lower`, `left_arm_lower`, `tail`, `right_wing`, `left_wing`. Quadrupeds use `*_arm` for front legs.
- Heatblast, Stinkfly and Ghostfreak also get a looping `animation.<id>.fly` (`fly_animation()` in `export.py`). The client plays it instead of idle/walk while the transformed player is flying (`AlienFlight`), and Heatblast leaves a flame trail (`AlienFlightEffects`). Rotation signs follow the Bedrock convention: positive X leans a bone's top forward; the previews' renderer shows the opposite, so negate X when posing a bone by hand. Every channel of a looping clip must span the clip's whole length; use `cycles()` for fast repeats.
- Every alien gets `animation.<id>.idle`, `.walk`, `.transform` and `.attack` (a 0.3 s non-looping swing played while the player's arm swings). The Omnitrix gets `activate` and `deactivate` (the dial pops up and turns).
- Use `chest_omnitrix(...)` or the `omnitrix_symbol()` decal so every alien shows the Omnitrix.

## Bosses, mobs and boss-progression items

Vilgax, the Vilgax drone, Zs'Skayr, the Null Void prisoner and the 2.1 Null Void mobs (Null Guardian, Lepidopterran scout, prison warden) use the same engine. Their models are in `tools/alien-gen/creatures.py` (`CREATURES`), their animations in `tools/alien-gen/export_creatures.py`. Paths and animation names follow the asset contract: `geo/entity/<id>.geo.json`, `animations/entity/<id>.animation.json`, `textures/entity/<id>.png` and `<id>_glowmask.png`. The aliens and Omnitrix files are never touched by this exporter.

| Command | Writes |
|---|---|
| `python3 tools/alien-gen/export_creatures.py [ids]` | the creatures' geo, animations, textures and glow masks (about 3 minutes for all seven; pass ids to export only those) |
| `python3 tools/alien-gen/preview_creatures.py [ids]` | front and 3/4 renders next to a 32 px Ben for scale, plus `creatures_sheet.png`, in `tools/alien-gen/out/` |
| `python3 tools/boss-assets/generate.py` | 16x16 item textures (`kraab_claw`, `vilgax_core`, `vilgax_beacon`, `null_void_projector`, `null_crystal`, `master_control`, and for 2.1 `taydenite`, `null_guardian_membrane`, `prisoner_cuffs`, `cell_key`, `plumber_badge`, `plumber_blaster`, `null_void_glider`, `plumber_helmet`/`chestplate`/`leggings`/`boots`), block textures (`null_void_stone`, `null_void_stone_bricks`, `null_crystal_ore`, and `taydenite_ore`, `taydenite_block`, `prison_wall`, `prison_vault_front`/`side`/`top`, `plumber_hull`), the 64x32 Plumber armour layers `textures/models/armor/plumber_layer_1.png` and `plumber_layer_2.png`, plus NumPy/Pillow energy textures `textures/entity/plumber_bolt.png`, `null_void_portal_swirl.png` and `null_void_portal_rim.png`. It writes `tools/boss-assets/out/` (gitignored) previews: `sheet.png` (tiles on a checker, every tile repeated 3x3 to check seams), `sheet_actual_size.png` (1x and 2x on an inventory grey) and `armor.png` (the two layers at 8x). |

Scale: a player is 32 px tall; Vilgax is about 54 px, Zs'Skayr 48 px, the prisoner 32 px, the drone about 12 px, the Null Guardian 32 px (35 wide), the Lepidopterran about 21 px, the prison warden about 47 px. The guardian's bones are `body` and `tentacle_0`..`tentacle_11`; the Lepidopterran's wings are upright sheets flapped about Y. Looping animations are `idle`, `walk`, `fly`; `attack` (0.5 s) and `summon` (1.5 s) play once. Zs'Skayr's pale parts are opaque, not translucent, so no translucent render type is needed.

## Not yet verified in game

The previews come from the generator's own renderer. Check in a real client that:
- models are not mirrored (the export negates X for Bedrock/GeckoLib geometry);
- animation rotations swing the expected way (Bedrock rotation signs);
- glow masks and translucency render with the chosen render types.

If one of these is wrong, fix it once in `engine.py`/`export.py` and re-export everything.

## Omnitrix UI art and wrist model

`python3 tools/ui-assets/generate.py` (numpy + Pillow) draws these procedurally; edit the script, not the PNGs.

| File | What |
|---|---|
| `textures/item/omnitrix.png` | 16x16 creative tab icon: 3/4 view Omnitrix, grey casing, black dial, green hourglass |
| `textures/item/omnitrix_guide.png` | 16x16 guide book, dark green cover, lime hourglass |
| `textures/gui/omnitrix_guide.png` | 256x256: 192x192 page at (0,0), text area x 36..172, y 30..170; arrows 23x13: next (0,192), prev (0,205), hovered next (23,192), hovered prev (23,205) |
| `textures/entity/omnitrix_wrist.png`, `omnitrix_wrist_glow.png` | 32x32 UV atlas for `OmnitrixWristLayer` (band wide at (0,0), band slim at (0,8), dial at (0,16)); the glow copy has only the hourglass lit and is drawn with `RenderType.eyes` |

`OmnitrixWristLayer` builds its `ModelPart` in code (`LayerDefinition.bakeRoot()`), so no model-layer registration is needed. If you move a cube, change the UV numbers in both the script and the layer. Previews (16x icons, GUI sheet, wrist cube layout) are written to `tools/ui-assets/out/` (gitignored). The wrist model and glow still need a `runClient` look on each loader, including slim-arm skins.
