"""Renders front and 3/4 views of each creature next to a 32 px Ben for scale, plus a sheet, into out/.

Usage: python3 preview_creatures.py [id ...]
"""
import sys
from pathlib import Path

from PIL import Image

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import aliens
import creatures
from engine import Scene

OUT = HERE / "out"


def render(name, yaw, pose=None, scale_ref=True):
    m = creatures.CREATURES[name]()
    m.bake()
    top = max(c.hi[1] for c in m.cubes)
    ref = aliens.ben()
    ref.bake()
    sc = Scene(420, 560, yaw=yaw, pitch=-6, zoom=470 / (max(top, 36) + 8), center=(-3, max(top, 36) / 2, 0))
    sc.draw(m, pose)
    if scale_ref:
        sc.draw(ref, {"_root": ((-18, 0, 0), 1.0, 0)})
    return sc.image()


def main(names):
    OUT.mkdir(exist_ok=True)
    rows = []
    for n in names:
        views = [render(n, y) for y in (0, -30)]
        row = Image.new("RGB", (840, 560))
        for i, v in enumerate(views):
            row.paste(v, (i * 420, 0))
        row.save(OUT / f"{n}.png")
        rows.append(row)
        print("rendered", n)
    sheet = Image.new("RGB", (840, 560 * len(rows)))
    for i, r in enumerate(rows):
        sheet.paste(r, (0, i * 560))
    sheet.save(OUT / "creatures_sheet.png")


if __name__ == "__main__":
    main(sys.argv[1:] or list(creatures.CREATURES))
