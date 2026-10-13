# 3D assets: aliens and the Omnitrix

Every alien model, texture, glow mask and animation, the Omnitrix watch model, and the Omnitrix dial and flash textures come from the Python generator in `tools/alien-gen/`. Edit the generator, not the PNGs: a re-export overwrites them.

## Setup

```bash
python3 -m pip install -r tools/alien-gen/requirements.txt
```

## Commands

| Command | Writes |
|---|---|
| `python3 tools/alien-gen/gui_base.py` | `textures/gui/omnitrix.png` (dial and selection frame, icon rows empty), `textures/misc/omnitrix_flash.png` |
| `python3 tools/alien-gen/export.py` | all `geo/`, `animations/`, alien and Omnitrix textures, and the hologram icon rows in `gui/omnitrix.png` |
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
- Every alien gets `animation.<id>.idle`, `.walk` and `.transform`. The Omnitrix gets `activate` and `deactivate` (the dial pops up and turns).
- Use `chest_omnitrix(...)` or the `omnitrix_symbol()` decal so every alien shows the Omnitrix.

## Not yet verified in game

The previews come from the generator's own renderer. Check in a real client that:
- models are not mirrored (the export negates X for Bedrock/GeckoLib geometry);
- animation rotations swing the expected way (Bedrock rotation signs);
- glow masks and translucency render with the chosen render types.

If one of these is wrong, fix it once in `engine.py`/`export.py` and re-export everything.
