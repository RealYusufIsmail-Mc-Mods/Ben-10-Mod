"""Renders a 3/4 studio preview of each model plus a contact sheet into out/.

Usage: python3 preview.py [name ...]   (default: every alien and the Omnitrix)
"""
import sys
from pathlib import Path

from PIL import Image

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import aliens
from engine import Scene

OUT = HERE / "out"


def render(name):
    m = getattr(aliens, name)()
    m.bake()
    top = max(c.hi[1] for c in m.cubes)
    if name == "omnitrix":
        sc = Scene(420, 420, yaw=-62, pitch=-30, zoom=21, center=(0, 3.5, 0))
    else:
        sc = Scene(420, 560, yaw=-28, pitch=-8, zoom=480 / (top + 8), center=(0, top / 2, 0))
    sc.draw(m)
    return sc.image()


def sheet(images, cols=5, w=300, h=400):
    s = Image.new("RGB", (cols * w, ((len(images) + cols - 1) // cols) * h), (16, 19, 24))
    for i, im in enumerate(images):
        im = im.convert("RGB").resize((w, int(im.height * w / im.width)), Image.LANCZOS)
        s.paste(im, ((i % cols) * w, (i // cols) * h + (h - im.height) // 2))
    return s


if __name__ == "__main__":
    OUT.mkdir(exist_ok=True)
    names = sys.argv[1:] or [*aliens.ALIENS, "omnitrix"]
    images = []
    for n in names:
        im = render(n)
        im.save(OUT / f"{n}.png")
        if n != "omnitrix":
            images.append(im)
        print("rendered", n)
    if images:
        sheet(images).save(OUT / "sheet.png")
