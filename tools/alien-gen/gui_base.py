"""Draws the 2D Omnitrix textures: the dial screen (gui/omnitrix.png) and the transformation flash.

The hologram icon rows of the dial (y >= 128) are left empty here; export.py renders them from the 3D models.
Usage: python3 gui_base.py [assets_dir]
"""
import math
import random
import sys
from pathlib import Path

from PIL import Image

ASSETS = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(__file__).resolve().parents[2] / "common/src/main/resources/assets/bentenmod"


def hexc(s, a=255):
    s = s.lstrip("#")
    return (int(s[0:2], 16), int(s[2:4], 16), int(s[4:6], 16), a)


def shade(c, f):
    return tuple(max(0, min(255, int(ch * f))) for ch in c[:3]) + (c[3],)


class Skin:
    def __init__(self, seed):
        self.img = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
        self.px = self.img.load()
        self.rng = random.Random(seed)

    def fill(self, part, color, noise=0.08, grad=0.12, which=None):
        """Base colour with per-pixel noise and a top-lit vertical gradient."""
        for name, (x0, y0, w, h) in faces(part).items():
            if which and name not in which:
                continue
            for y in range(h):
                for x in range(w):
                    f = 1 + self.rng.uniform(-noise, noise)
                    if name not in ("top", "bottom"):
                        f += grad * (0.5 - y / max(1, h - 1))
                    elif name == "bottom":
                        f -= 0.15
                    self.px[x0 + x, y0 + y] = shade(color, f)

    def grid(self, part, face, rows, pal, ox=0, oy=0):
        x0, y0, _, _ = faces(part)[face]
        for y, row in enumerate(rows):
            for x, ch in enumerate(row):
                if ch == ".":
                    continue
                c = pal[ch]
                self.px[x0 + ox + x, y0 + oy + y] = c if callable(c) is False else c(self.rng)

    def rows(self, part, face, y_from, y_to, color, noise=0.06):
        x0, y0, w, _ = faces(part)[face]
        for y in range(y_from, y_to):
            for x in range(w):
                self.px[x0 + x, y0 + y] = shade(color, 1 + self.rng.uniform(-noise, noise))

    def band(self, part, y_from, y_to, color, sides=("front", "back", "left", "right"), noise=0.06):
        for s in sides:
            self.rows(part, s, y_from, y_to, color, noise)

    def cracks(self, part, colors, count, length):
        """Random-walk glowing lines across the sides of a part."""
        for name, (x0, y0, w, h) in faces(part).items():
            n = count if name not in ("top", "bottom") else max(1, count // 2)
            for _ in range(n):
                x, y = self.rng.randrange(w), self.rng.randrange(h)
                for i in range(length):
                    c = colors[0] if i % 3 else colors[1]
                    self.px[x0 + x, y0 + y] = c
                    dx, dy = self.rng.choice([(1, 0), (-1, 0), (0, 1), (0, 1), (1, 1), (-1, 1)])
                    x, y = max(0, min(w - 1, x + dx)), max(0, min(h - 1, y + dy))

    def save(self, path):
        self.img.save(path)


OMNI = ["sKKs", "KggK", "KggK", "sKKs"]
OMNI_PAL = {"s": hexc("6b6f78"), "K": hexc("15161a"), "g": hexc("3dff4a")}


def omnitrix_gui(skins):
    img = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    px = img.load()
    rim, rim_d, rim_l = hexc("8c9099"), hexc("4f535c"), hexc("c3c7cf")
    face, green, green_d, green_l = hexc("101114"), hexc("3dff4a"), hexc("1fa82c"), hexc("b8ffbf")
    cx = cy = 63.5
    for y in range(128):
        for x in range(128):
            dx, dy = x - cx, y - cy
            r = math.hypot(dx, dy)
            a = math.atan2(dy, dx)
            if r > 63:
                continue
            if r > 54:
                light = -math.sin(a + math.pi / 4)  # lit from top-left
                c = rim_l if light > 0.55 else rim_d if light < -0.55 else rim
                if int((a + math.pi) / (2 * math.pi) * 24) % 2 == 0 and r > 60:
                    c = shade(c, 0.8)  # grip notches
                px[x, y] = c
            elif r > 51:
                px[x, y] = hexc("2a2c31")
            else:
                # hourglass: |dx| <= half-width that shrinks towards the middle
                half = 6 + abs(dy) * 0.62
                inside = abs(dx) <= half and abs(dy) <= 36
                edge = inside and (abs(dx) > half - 3 or abs(dy) > 33)
                if inside:
                    px[x, y] = green_d if edge else (green_l if dx < -half + 7 and dy < 0 else green)
                else:
                    px[x, y] = shade(face, 1 + 0.25 * (1 - r / 51))

    # selection frame at (128, 0), 40x40
    for y in range(40):
        for x in range(40):
            border = x < 2 or y < 2 or x > 37 or y > 37
            if border:
                px[128 + x, y] = green if (x + y) % 4 < 2 else green_d
            elif x < 3 or y < 3 or x > 36 or y > 36:
                px[128 + x, y] = hexc("0c2410")

    # hologram alien icons, 32x32 each, starting at (0, 128)
    for i, (name, skin) in enumerate(skins.items()):
        src = skin.img
        head = Image.new("RGBA", (8, 8))
        head.alpha_composite(src.crop((8, 8, 16, 16)))
        head.alpha_composite(src.crop((40, 8, 48, 16)))
        head = head.resize((32, 32), Image.NEAREST)
        hp = head.load()
        ox, oy = (i % 8) * 32, 128 + (i // 8) * 32
        lums = [(0.3 * r + 0.59 * g + 0.11 * b) / 255
                for r, g, b, a in head.getdata() if a]
        lo, hi = min(lums), max(lums)
        for y in range(32):
            for x in range(32):
                r, g, b, a = hp[x, y]
                if a == 0:
                    continue
                lum = ((0.3 * r + 0.59 * g + 0.11 * b) / 255 - lo) / max(0.01, hi - lo)
                lum = 0.12 + 0.88 * lum ** 1.4
                if y % 4 == 3:
                    lum *= 0.7  # scanline
                px[ox + x, oy + y] = (int(60 * lum), int(255 * lum), int(80 * lum), 235)
    return img


def flash():
    img = Image.new("RGBA", (256, 256))
    px = img.load()
    rng = random.Random(7)
    rays = [rng.uniform(0.4, 1.0) for _ in range(16)]
    for y in range(256):
        for x in range(256):
            dx, dy = (x - 127.5) / 128, (y - 127.5) / 128
            r = math.hypot(dx, dy)
            a = (math.atan2(dy, dx) + math.pi) / (2 * math.pi) * 16
            ray = rays[int(a) % 16] * max(0, math.cos((a % 1 - 0.5) * math.pi)) ** 6
            glow = max(0, 1 - r) ** 1.6 + ray * max(0, 1 - r) * 0.8
            glow = min(1, glow)
            core = max(0, 1 - r * 3)
            # quantise to 8 steps for a pixel-art look
            q = round(glow * 8) / 8
            red = int(80 + 175 * core)
            px[x, y] = (red, 255, int(90 + 165 * core), int(255 * q))
    return img


def main():
    tex = ASSETS / "textures"
    (tex / "misc").mkdir(exist_ok=True)
    omnitrix_gui({}).save(tex / "gui" / "omnitrix.png")
    flash().save(tex / "misc" / "omnitrix_flash.png")


if __name__ == "__main__":
    main()
