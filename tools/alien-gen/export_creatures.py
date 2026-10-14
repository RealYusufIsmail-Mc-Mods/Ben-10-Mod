"""Bake the boss/mob models and write GeckoLib assets (geo, animations, textures, glow masks).

Writes only geo/entity, animations/entity and textures/entity; alien and Omnitrix assets are not touched.
Usage: python3 export_creatures.py [assets_dir] [id ...]   (no ids: every creature)
"""
import json
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
ARGS = sys.argv[1:]
sys.argv = sys.argv[:1]  # export.py reads argv[1] at import; this script has its own path handling
import creatures
import numpy as np
from export import cycles, kf, loop

_PATHS = [a for a in ARGS if "/" in a]
IDS = [a for a in ARGS if "/" not in a]
ASSETS = Path(_PATHS[0]) if _PATHS else HERE.parents[1] / "common/src/main/resources/assets/bentenmod"


def rot(*pairs):
    return {"rotation": kf(*pairs)}


def pos(*pairs):
    return {"position": kf(*pairs)}


def walk_legs(length, swing):
    return {
        "right_leg": {"rotation": loop(length, swing, axis=0, phase=0.5)},
        "left_leg": {"rotation": loop(length, swing, axis=0, phase=0.0)},
        "right_arm": {"rotation": loop(length, swing * 0.8, axis=0, phase=0.0)},
        "left_arm": {"rotation": loop(length, swing * 0.8, axis=0, phase=0.5)},
    }


def vilgax():
    idle = {
        "body": {"position": loop(3.2, 0.35, axis=1)},
        "right_arm": {"rotation": loop(3.2, 3, axis=2)},
        "left_arm": {"rotation": loop(3.2, -3, axis=2)},
        "head": {"rotation": loop(3.2, 3, axis=1)},
        "tentacles": {"rotation": loop(3.2, 5, axis=0, phase=0.25)},
    }
    walk = walk_legs(1.0, 28)
    walk["body"] = {"position": loop(0.5, 0.5, axis=1, steps=4)}
    walk["tentacles"] = {"rotation": loop(1.0, 6, axis=0)}
    attack = {  # overhead slam with the right fist
        "right_arm": rot((0, (0, 0, 0)), (0.15, (-140, 0, -10)), (0.28, (25, 0, 0)), (0.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.15, (-30, 0, 0)), (0.5, (0, 0, 0))),
        "body": rot((0, (0, 0, 0)), (0.15, (-8, -14, 0)), (0.28, (14, 12, 0)), (0.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.15, (-10, 0, 0)), (0.28, (14, 0, 0)), (0.5, (0, 0, 0))),
        "tentacles": rot((0, (0, 0, 0)), (0.28, (20, 0, 0)), (0.5, (0, 0, 0))),
    }
    summon = {  # both arms raised, head back, a rumble in the body
        "right_arm": rot((0, (0, 0, 0)), (0.5, (-165, 0, 15)), (1.1, (-165, 0, 15)), (1.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.5, (-165, 0, -15)), (1.1, (-165, 0, -15)), (1.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.5, (-25, 0, 0)), (1.1, (-25, 0, 0)), (1.5, (0, 0, 0))),
        "body": {"position": kf((0, (0, 0, 0)), (0.5, (0, 1.5, 0)), (0.7, (0, 1.0, 0)), (0.9, (0, 1.6, 0)),
                                (1.1, (0, 1.0, 0)), (1.5, (0, 0, 0)))},
        "tentacles": rot((0, (0, 0, 0)), (0.5, (-18, 0, 0)), (1.1, (18, 0, 0)), (1.5, (0, 0, 0))),
    }
    return {"idle": (3.2, True, idle), "walk": (1.0, True, walk), "attack": (0.5, False, attack),
            "summon": (1.5, False, summon)}


def vilgax_drone():
    idle = {
        "body": {"position": loop(2.0, 0.5, axis=1)},
        "right_fin": {"rotation": loop(2.0, 6, axis=2)},
        "left_fin": {"rotation": loop(2.0, -6, axis=2)},
    }
    fly = {
        "body": {"position": loop(0.8, 0.35, axis=1, steps=4), "rotation": kf((0, (12, 0, 0)), (0.8, (12, 0, 0)))},
        "right_fin": {"rotation": loop(0.2, 22, axis=2, steps=4)},
        "left_fin": {"rotation": loop(0.2, -22, axis=2, steps=4)},
    }
    attack = {  # lunge forward and flare the fins
        "body": rot((0, (0, 0, 0)), (0.15, (-28, 0, 0)), (0.3, (35, 0, 0)), (0.5, (0, 0, 0))),
        "right_fin": rot((0, (0, 0, 0)), (0.15, (0, 0, 30)), (0.5, (0, 0, 0))),
        "left_fin": rot((0, (0, 0, 0)), (0.15, (0, 0, -30)), (0.5, (0, 0, 0))),
    }
    return {"idle": (2.0, True, idle), "fly": (0.8, True, fly), "attack": (0.5, False, attack)}


def zs_skayr():
    idle = {
        "body": {"position": loop(4.0, 1.0, axis=1)},
        "tail": {"rotation": loop(4.0, 6, axis=2)},
        "right_arm": {"rotation": loop(4.0, 7, axis=2, phase=0.1)},
        "left_arm": {"rotation": loop(4.0, -7, axis=2, phase=0.1)},
        "head": {"rotation": loop(4.0, 4, axis=1)},
        "chains": {"rotation": loop(4.0, 5, axis=0, phase=0.3)},
    }
    fly = {
        "body": {"position": loop(2.0, 1.2, axis=1), "rotation": kf((0, (14, 0, 0)), (2.0, (14, 0, 0)))},
        "tail": {"rotation": loop(2.0, 12, axis=0, phase=0.25)},
        "right_arm": {"rotation": loop(2.0, 12, axis=0, phase=0.5)},
        "left_arm": {"rotation": loop(2.0, 12, axis=0, phase=0.5)},
        "chains": {"rotation": loop(2.0, 10, axis=0)},
    }
    attack = {
        "right_arm": rot((0, (0, 0, 0)), (0.15, (-105, 0, 0)), (0.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.2, (-105, 0, 0)), (0.5, (0, 0, 0))),
        "body": rot((0, (0, 0, 0)), (0.15, (-12, 0, 0)), (0.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.15, (12, 0, 0)), (0.5, (0, 0, 0))),
    }
    summon = {
        "right_arm": rot((0, (0, 0, 0)), (0.5, (-150, 0, 20)), (1.1, (-150, 0, 20)), (1.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.5, (-150, 0, -20)), (1.1, (-150, 0, -20)), (1.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.5, (-22, 0, 0)), (1.1, (-22, 0, 0)), (1.5, (0, 0, 0))),
        "body": {"position": kf((0, (0, 0, 0)), (0.6, (0, 4, 0)), (1.1, (0, 4, 0)), (1.5, (0, 0, 0)))},
        "tail": rot((0, (0, 0, 0)), (0.5, (0, 0, 10)), (1.0, (0, 0, -10)), (1.5, (0, 0, 0))),
        "chains": rot((0, (0, 0, 0)), (0.5, (-25, 0, 0)), (1.1, (25, 0, 0)), (1.5, (0, 0, 0))),
    }
    return {"idle": (4.0, True, idle), "fly": (2.0, True, fly), "attack": (0.5, False, attack),
            "summon": (1.5, False, summon)}


def null_void_prisoner():
    idle = {
        "body": {"position": loop(2.4, 0.25, axis=1), "rotation": loop(2.4, 2, axis=0)},
        "right_arm": {"rotation": loop(2.4, 4, axis=0)},
        "left_arm": {"rotation": loop(2.4, 4, axis=0, phase=0.5)},
        "head": {"rotation": loop(2.4, 8, axis=1)},
    }
    walk = walk_legs(0.8, 32)
    walk["body"] = {"position": loop(0.4, 0.4, axis=1, steps=4), "rotation": loop(0.8, 5, axis=2)}
    attack = {
        "right_arm": rot((0, (0, 0, 0)), (0.12, (-100, 0, 0)), (0.26, (-10, 0, -20)), (0.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.22, (-100, 0, 0)), (0.36, (-10, 0, 20)), (0.5, (0, 0, 0))),
        "body": rot((0, (0, 0, 0)), (0.12, (12, 0, 0)), (0.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.12, (-12, 0, 0)), (0.5, (0, 0, 0))),
    }
    return {"idle": (2.4, True, idle), "walk": (0.8, True, walk), "attack": (0.5, False, attack)}


def null_guardian():
    spots = 12

    def sway(length, amp):
        return {f"tentacle_{i}": {"rotation": loop(length, amp, axis=i % 2 * 2, phase=(i % 4) / 4)}
                for i in range(spots)}

    idle = {"body": {"position": loop(3.2, 1.2, axis=1),
                     "scale": kf((0, (1, 1, 1)), (1.6, (1.03, 0.97, 1.03)), (3.2, (1, 1, 1)))}, **sway(3.2, 7)}
    fly = {"body": {"position": loop(1.2, 1.6, axis=1), "rotation": kf((0, (10, 0, 0)), (1.2, (10, 0, 0))),
                    "scale": kf((0, (1, 1, 1)), (0.3, (1.07, 0.9, 1.07)), (0.7, (0.97, 1.05, 0.97)), (1.2, (1, 1, 1)))}}
    for i in range(spots):  # tentacles stream out behind the bell and ripple
        fly[f"tentacle_{i}"] = {"rotation": kf(*[
            (1.2 * k / 8, (14 + 9 * np.sin(2 * np.pi * (k / 8 + (i % 4) / 4)), 0,
                           6 * np.sin(2 * np.pi * (k / 8 + (i % 3) / 3))))
            for k in range(9)])}
    attack = {"body": {**rot((0, (0, 0, 0)), (0.12, (-8, 0, 0)), (0.28, (18, 0, 0)), (0.5, (0, 0, 0))),
                       "scale": kf((0, (1, 1, 1)), (0.28, (1.1, 0.94, 1.1)), (0.5, (1, 1, 1)))}}
    for i in range(spots):  # tractor beam: every tentacle flares forward
        attack[f"tentacle_{i}"] = rot((0, (0, 0, 0)), (0.12, (10, 0, 0)), (0.28, (-62, 0, 0)), (0.5, (0, 0, 0)))
    return {"idle": (3.2, True, idle), "fly": (1.2, True, fly), "attack": (0.5, False, attack)}


def lepidopterran():
    idle = {
        "body": {"position": loop(1.6, 0.35, axis=1)},
        "right_wing": {"rotation": cycles(1.6, 3, 14, axis=1)},
        "left_wing": {"rotation": cycles(1.6, 3, -14, axis=1)},
        "tail": {"rotation": loop(1.6, 6, axis=1)},
        "head": {"rotation": loop(1.6, 5, axis=1)},
        "right_arm": {"rotation": loop(1.6, 5, axis=0)},
        "left_arm": {"rotation": loop(1.6, 5, axis=0, phase=0.5)},
    }
    fly = {
        "body": {"position": loop(0.4, 0.4, axis=1, steps=4), "rotation": kf((0, (16, 0, 0)), (0.4, (16, 0, 0)))},
        "right_wing": {"rotation": cycles(0.4, 3, 40, axis=1)},
        "left_wing": {"rotation": cycles(0.4, 3, -40, axis=1)},
        "tail": {"rotation": loop(0.4, 9, axis=0, steps=4)},
        "right_leg": {"rotation": kf((0, (50, 0, 0)), (0.4, (50, 0, 0)))},
        "left_leg": {"rotation": kf((0, (50, 0, 0)), (0.4, (50, 0, 0)))},
        "right_arm": {"rotation": kf((0, (-30, 0, 0)), (0.4, (-30, 0, 0)))},
        "left_arm": {"rotation": kf((0, (-30, 0, 0)), (0.4, (-30, 0, 0)))},
    }
    attack = {  # dive at the target, then rear back and spit
        "body": rot((0, (0, 0, 0)), (0.18, (58, 0, 0)), (0.34, (-14, 0, 0)), (0.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.18, (-10, 0, 0)), (0.34, (-26, 0, 0)), (0.5, (0, 0, 0))),
        "tail": rot((0, (0, 0, 0)), (0.18, (-35, 0, 0)), (0.34, (30, 0, 0)), (0.5, (0, 0, 0))),
        "right_wing": {"rotation": cycles(0.5, 4, 40, axis=1)},
        "left_wing": {"rotation": cycles(0.5, 4, -40, axis=1)},
    }
    return {"idle": (1.6, True, idle), "fly": (0.4, True, fly), "attack": (0.5, False, attack)}


def prison_warden():
    idle = {
        "body": {"position": loop(6.0, 0.3, axis=1)},
        "right_arm": {"rotation": loop(6.0, 2.5, axis=2)},
        "left_arm": {"rotation": loop(6.0, -2.5, axis=2)},
        "head": {"rotation": loop(6.0, 4, axis=1)},
    }
    walk = walk_legs(1.1, 22)
    walk["body"] = {"position": cycles(1.1, 2, 0.5, axis=1), "rotation": loop(1.1, 2.5, axis=2)}
    walk["head"] = {"rotation": loop(1.1, 2, axis=1)}
    attack = {  # overhead baton strike
        "right_arm": rot((0, (0, 0, 0)), (0.15, (-135, 0, 0)), (0.28, (-20, 0, 0)), (0.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.15, (-25, 0, 0)), (0.5, (0, 0, 0))),
        "body": rot((0, (0, 0, 0)), (0.15, (-8, 0, 0)), (0.28, (14, 0, 0)), (0.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.15, (-8, 0, 0)), (0.28, (10, 0, 0)), (0.5, (0, 0, 0))),
    }
    summon = {  # raises the baton, slams it down to call guards; a rumble in the body
        "right_arm": rot((0, (0, 0, 0)), (0.5, (-160, 0, 8)), (1.0, (-160, 0, 8)), (1.15, (-10, 0, 0)), (1.5, (0, 0, 0))),
        "left_arm": rot((0, (0, 0, 0)), (0.5, (-120, 0, -12)), (1.0, (-120, 0, -12)), (1.5, (0, 0, 0))),
        "head": rot((0, (0, 0, 0)), (0.5, (-22, 0, 0)), (1.0, (-22, 0, 0)), (1.15, (12, 0, 0)), (1.5, (0, 0, 0))),
        "body": rot((0, (0, 0, 0)), (0.5, (-6, 0, 0)), (1.0, (-6, 0, 0)), (1.15, (10, 0, 0)), (1.5, (0, 0, 0))),
    }
    return {"idle": (6.0, True, idle), "walk": (1.1, True, walk), "attack": (0.5, False, attack),
            "summon": (1.5, False, summon)}


ANIMATIONS = {"vilgax": vilgax, "vilgax_drone": vilgax_drone, "zs_skayr": zs_skayr,
              "null_void_prisoner": null_void_prisoner, "null_guardian": null_guardian,
              "lepidopterran": lepidopterran, "prison_warden": prison_warden}


def anim_json(name, spec):
    anims = {}
    for an, (length, looping, bones) in spec.items():
        entry = {"animation_length": length, "bones": bones}
        if looping:
            entry = {"loop": True, **entry}
        anims[f"animation.{name}.{an}"] = entry
    return {"format_version": "1.8.0", "animations": anims}


def main():
    geo, anim, tex = ASSETS / "geo/entity", ASSETS / "animations/entity", ASSETS / "textures/entity"
    for d in (geo, anim, tex):
        d.mkdir(parents=True, exist_ok=True)
    for name, fn in creatures.CREATURES.items():
        if IDS and name not in IDS:
            continue
        m = fn()
        m.bake()
        m.save_geo(geo / f"{name}.geo.json")
        glowing = m.save_textures(tex / f"{name}.png", tex / f"{name}_glowmask.png")
        (anim / f"{name}.animation.json").write_text(json.dumps(anim_json(name, ANIMATIONS[name]()), indent="\t"))
        top = max(c.hi[1] for c in m.cubes)
        print(f"{name:20s} {len(m.cubes):3d} cubes  {m.tex_w}x{m.tex_h}  height={top:.1f}px  glow={glowing}")


if __name__ == "__main__":
    main()
