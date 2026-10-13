"""Renders Ben -> alien transformation clips with the baked models."""
import math
import sys

import numpy as np
from PIL import Image

HERE = __import__("pathlib").Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import aliens
from engine import Scene

W, H, SS = 400, 500, 2


def ease(t):
    t = min(max(t, 0), 1)
    return t * t * (3 - 2 * t)


def flash_glow(sc, k, rot):
    ys, xs = np.mgrid[0:sc.H, 0:sc.W]
    pts, _ = sc.project(np.array([[0, 16, 0.0]]))
    cx, cy = pts[0]
    dx, dy = xs - cx, ys - cy
    r = np.hypot(dx, dy) / (sc.H * 0.5)
    a = np.arctan2(dy, dx)
    rays = np.clip(np.cos(a * 8 + rot), 0, 1) ** 8 * np.exp(-r * 1.2)
    core = np.exp(-(r / (0.15 + 0.9 * k)) ** 2)
    g = (core * 1.6 + rays * 1.2) * k
    return np.stack([g * 0.45, g * 1.0, g * 0.5], -1)


def clip(alien_name, out):
    ben, alien = aliens.ben(), getattr(aliens, alien_name)()
    ben.bake()
    alien.bake()
    top = max(c.hi[1] for c in alien.cubes)
    frames = []
    n = 44
    for f in range(n):
        sc = Scene(W, H, ss=SS, yaw=-24, pitch=-8, zoom=440 / (max(top, 34) + 14), center=(0, max(top, 32) / 2, 0))
        t = f / n
        glow = None
        if f < 14:  # Ben slams the dial
            k = ease(f / 9)
            pose = {"left_arm": (75 * k, 0, 0), "right_arm": (55 * k, 0, -25 * k), "head": (12 * k, 0, 0)}
            boost = 1 + 5 * ease((f - 6) / 8)
            tint = ((0.3, 1, 0.4), 0.6 * ease((f - 9) / 5))
            sc.draw(ben, pose, tint=tint, emit_boost=boost)
            if f >= 9:
                glow = flash_glow(sc, ease((f - 9) / 5) * 0.9, f * 0.3)
        else:  # the alien bursts out of the flash
            u = (f - 14) / 12
            s = np.interp(u, [0, 0.45, 0.75, 1], [0.08, 1.15, 0.96, 1.0])
            ry = 540 * (1 - ease(u / 0.75))
            bob = 0.35 * math.sin(f * 0.5)
            pose = {"_root": ((0, 0, 0), s, ry), "body": (0, 0, 0)}
            if "right_arm" in alien.bones:
                arm = 55 * math.sin(math.pi * min(u, 1)) if u < 1 else 0
                pose["right_arm"] = (0, 0, -arm)
                pose["left_arm"] = (0, 0, arm)
            if u >= 1:
                pose["_root"] = ((0, bob, 0), 1.0, 0)
            tint = ((0.3, 1, 0.4), 0.7 * (1 - ease(u / 0.8)))
            sc.draw(alien, pose, tint=tint)
            glow = flash_glow(sc, (1 - ease(u / 0.9)) * 1.0, f * 0.3)
        frames.append(sc.image(extra_glow=glow).convert("RGB"))
    frames += [frames[-1]] * 10
    pal = [fr.quantize(colors=255, method=Image.Quantize.MEDIANCUT) for fr in frames]
    pal[0].save(out, save_all=True, append_images=pal[1:], duration=70, loop=0, optimize=False)
    frames[13].save(out.replace(".gif", "_peak.png"))
    frames[-1].save(out.replace(".gif", "_end.png"))


if __name__ == "__main__":
    for name in sys.argv[1:] or ["heatblast"]:
        (HERE / "out").mkdir(exist_ok=True)
        clip(name, str(HERE / "out" / f"transform_{name}.gif"))
        print("done", name)
