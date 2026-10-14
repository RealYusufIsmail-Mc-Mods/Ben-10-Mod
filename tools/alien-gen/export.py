"""Bake every model and write GeckoLib assets into the mod, plus the dial icons and a transformation clip."""
import json
import sys
from pathlib import Path

import numpy as np
from PIL import Image

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import aliens
from engine import Scene

ASSETS = Path(sys.argv[1]) if len(sys.argv) > 1 else HERE.parents[1] / "common/src/main/resources/assets/bentenmod"
PREVIEW = HERE / "out"


def kf(*pairs):
    return {f"{t:.2f}": list(v) for t, v in pairs}


def loop(length, amp, axis=0, phase=0.0, steps=8):
    """A sine wave as keyframes (GeckoLib keyframes lerp between these)."""
    out = []
    for i in range(steps + 1):
        t = length * i / steps
        v = [0.0, 0.0, 0.0]
        v[axis] = round(amp * np.sin(2 * np.pi * (i / steps + phase)), 3)
        out.append((t, v))
    return kf(*out)


def animations(m):
    b = set(m.bones)
    quad = m.name in ("wildmutt", "stinkfly")
    idle, walk, transform = {}, {}, {}

    if "body" in b:
        idle["body"] = {"position": loop(2.4, 0.35, axis=1)}
        walk["body"] = {"position": loop(0.8, 0.4, axis=1, steps=8)}
    for arm, ph in (("right_arm", 0.0), ("left_arm", 0.5)):
        if arm in b:
            idle[arm] = {"rotation": loop(2.4, 3, axis=2 if not quad else 0, phase=ph)}
            walk[arm] = {"rotation": loop(0.8, 30, axis=0, phase=ph)}
    for leg, ph in (("right_leg", 0.5), ("left_leg", 0.0)):
        if leg in b:
            walk[leg] = {"rotation": loop(0.8, 30, axis=0, phase=ph)}
    for extra, ph in (("right_arm_lower", 0.25), ("left_arm_lower", 0.75)):
        if extra in b:
            idle[extra] = {"rotation": loop(2.4, 4, axis=2, phase=ph)}
            walk[extra] = {"rotation": loop(0.8, 22, axis=0, phase=ph)}
    if "head" in b:
        idle["head"] = {"rotation": loop(4.8, 4, axis=1)}
    if "tail" in b:
        idle["tail"] = {"rotation": loop(1.6, 8, axis=1)}
        walk["tail"] = {"rotation": loop(0.8, 12, axis=1)}
    for wing, sign in (("right_wing", 1), ("left_wing", -1)):
        if wing in b:
            flap = loop(0.2, 28 * sign, axis=2, steps=4)
            idle[wing] = {"rotation": flap}
            walk[wing] = {"rotation": flap}

    # the transformation: rise out of a green flash, spin, settle
    transform["root"] = {
        "scale": kf((0, (0.05, 0.05, 0.05)), (0.35, (1.15, 1.15, 1.15)), (0.55, (0.95, 0.95, 0.95)), (0.7, (1, 1, 1))),
        "rotation": kf((0, (0, 540, 0)), (0.55, (0, 0, 0))),
    }
    for arm, s in (("right_arm", 1), ("left_arm", -1)):
        if arm in b:
            transform[arm] = {"rotation": kf((0, (0, 0, 0)), (0.45, (0, 0, -55 * s)), (0.9, (0, 0, 0)))}
    if "head" in b:
        transform["head"] = {"rotation": kf((0.35, (0, 0, 0)), (0.55, (-15, 0, 0)), (0.9, (0, 0, 0)))}

    # the swing: matches vanilla's 6-tick arm swing
    attack = {}
    punch = -60 if m.name == "wildmutt" else -30 if m.name == "stinkfly" else -100
    if "right_arm" in b:
        attack["right_arm"] = {"rotation": kf((0, (0, 0, 0)), (0.1, (punch, 0, 0)), (0.3, (0, 0, 0)))}
    if "right_arm_lower" in b:
        attack["right_arm_lower"] = {"rotation": kf((0, (0, 0, 0)), (0.15, (-100, 0, 0)), (0.3, (0, 0, 0)))}
    if "body" in b and m.name not in ("wildmutt", "stinkfly"):
        attack["body"] = {"rotation": kf((0, (0, 0, 0)), (0.1, (0, -12, 0)), (0.3, (0, 0, 0)))}
    if "head" in b and m.name == "wildmutt":
        attack["head"] = {"rotation": kf((0, (0, 0, 0)), (0.1, (15, 0, 0)), (0.3, (0, 0, 0)))}

    n = m.name
    return {
        "format_version": "1.8.0",
        "animations": {
            f"animation.{n}.idle": {"loop": True, "animation_length": 4.8, "bones": idle},
            f"animation.{n}.walk": {"loop": True, "animation_length": 0.8, "bones": walk},
            f"animation.{n}.transform": {"animation_length": 1.0, "bones": transform},
            f"animation.{n}.attack": {"animation_length": 0.3, "bones": attack},
        },
    }


def omnitrix_animations():
    return {
        "format_version": "1.8.0",
        "animations": {
            "animation.omnitrix.idle": {"loop": True, "animation_length": 2.0, "bones": {}},
            "animation.omnitrix.activate": {
                "animation_length": 0.6,
                "bones": {
                    "dial": {"position": kf((0, (0, 0, 0)), (0.2, (0, 1.2, 0)), (0.6, (0, 1.2, 0)))},
                    "core": {"rotation": kf((0.2, (0, 0, 0)), (0.6, (0, 90, 0)))},
                },
            },
            "animation.omnitrix.deactivate": {
                "animation_length": 0.4,
                "bones": {"dial": {"position": kf((0, (0, 1.2, 0)), (0.4, (0, 0, 0)))}},
            },
        },
    }


def write(m, geo_dir, tex_dir, anim):
    geo_dir.mkdir(parents=True, exist_ok=True)
    tex_dir.mkdir(parents=True, exist_ok=True)
    (ASSETS / "animations").mkdir(exist_ok=True)
    m.save_geo(geo_dir / f"{m.name}.geo.json")
    tex_name = "omnitrix_device" if m.name == "omnitrix" else m.name
    glowing = m.save_textures(tex_dir / f"{tex_name}.png", tex_dir / f"{tex_name}_glowmask.png")
    (ASSETS / "animations" / f"{m.name}.animation.json").write_text(json.dumps(anim, indent="\t"))
    return glowing


def portrait(m):
    """Head-and-shoulders render for the dial, as a green hologram."""
    top = max(c.hi[1] for c in m.cubes)
    low = min(c.lo[1] for c in m.cubes)
    h = top - low
    sc = Scene(96, 96, ss=2, yaw=-20, pitch=-6, zoom=96 / (h * 0.62), center=(0, top - h * 0.3, 0))
    sc.draw(m)
    a = sc.alpha
    lum = (sc.color @ np.array([0.3, 0.59, 0.11]))
    lum = lum / max(lum[a > 0].max(), 1e-3)
    lum = 0.25 + 0.75 * lum ** 0.8
    rows = (np.arange(sc.H) // (2 * sc.ss)) % 2 == 1
    lum[rows] *= 0.78  # scanlines
    rgb = np.stack([0.24 * lum, lum, 0.32 * lum], -1)
    img = np.concatenate([rgb, a[..., None] * 0.95], -1)
    im = Image.fromarray((np.clip(img, 0, 1) * 255).astype(np.uint8))
    return im.resize((32, 32), Image.LANCZOS)


def main():
    PREVIEW.mkdir(exist_ok=True)
    models = {}
    for name, fn in aliens.ALIENS.items():
        m = fn()
        m.bake()
        g = write(m, ASSETS / "geo" / "alien", ASSETS / "textures" / "entity" / "alien", animations(m))
        models[name] = m
        print(f"{name:12s} {len(m.cubes):3d} cubes  {m.tex_w}x{m.tex_h}  glow={g}")
    om = aliens.omnitrix()
    om.bake()
    write(om, ASSETS / "geo" / "item", ASSETS / "textures" / "item", omnitrix_animations())
    print("omnitrix device written")

    # dial GUI: keep the dial + frame, replace the icon rows with 3D portraits
    gui_path = ASSETS / "textures" / "gui" / "omnitrix.png"
    gui = Image.open(gui_path).convert("RGBA")
    gui.paste(Image.new("RGBA", (256, 128)), (0, 128))
    for i, m in enumerate(models.values()):
        gui.alpha_composite(portrait(m), ((i % 8) * 32, 128 + (i // 8) * 32))
    gui.save(gui_path)
    gui.resize((512, 512), Image.NEAREST).save(PREVIEW / "omnitrix_gui.png")
    return models


if __name__ == "__main__":
    main()
