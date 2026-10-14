"""Generate the boss-progression item and block textures (16x16) and a preview sheet.

Run from the repository root: python3 tools/boss-assets/generate.py
Writes textures/item and textures/block in the mod assets, and tools/boss-assets/out/sheet.png (gitignored).
"""
import math
import random
from pathlib import Path

from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[2]
TEX = ROOT / "common/src/main/resources/assets/bentenmod/textures"
OUT = Path(__file__).resolve().parent / "out"


def rgb(h):
    h = h.lstrip("#")
    return tuple(int(h[i:i + 2], 16) for i in (0, 2, 4))


def shade(c, k):
    return tuple(max(0, min(255, int(v * k))) for v in c)


class Pix:
    """A 16x16 canvas with a bevelling pass: outline, lit top-left edge, shadowed bottom-right edge."""

    def __init__(self):
        self.p = [[None] * 16 for _ in range(16)]

    def set(self, x, y, c):
        if 0 <= x < 16 and 0 <= y < 16:
            self.p[y][x] = rgb(c) if isinstance(c, str) else c

    def get(self, x, y):
        return self.p[y][x] if 0 <= x < 16 and 0 <= y < 16 else None

    def fill(self, pts, c):
        im = Image.new("L", (16, 16), 0)
        ImageDraw.Draw(im).polygon(pts, fill=255)
        for y in range(16):
            for x in range(16):
                if im.getpixel((x, y)):
                    self.set(x, y, c)

    def rect(self, x0, y0, x1, y1, c):
        for y in range(y0, y1 + 1):
            for x in range(x0, x1 + 1):
                self.set(x, y, c)

    def disc(self, cx, cy, r, c):
        for y in range(16):
            for x in range(16):
                if math.hypot(x - cx, y - cy) <= r:
                    self.set(x, y, c)

    def line(self, x0, y0, x1, y1, c):
        n = max(abs(x1 - x0), abs(y1 - y0), 1)
        for i in range(n + 1):
            self.set(round(x0 + (x1 - x0) * i / n), round(y0 + (y1 - y0) * i / n), c)

    def bevel(self, outline=0.38, lit=1.28, dim=0.78):
        """Darken the silhouette edge, then light interior pixels beside a top/left edge and dim those beside bottom/right."""
        src = [row[:] for row in self.p]

        def empty(x, y):
            return not (0 <= x < 16 and 0 <= y < 16) or src[y][x] is None

        edge = {(x, y) for y in range(16) for x in range(16) if src[y][x] is not None and
                any(empty(x + dx, y + dy) for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1)))}
        for (x, y) in edge:
            self.p[y][x] = shade(src[y][x], outline)
        for y in range(16):
            for x in range(16):
                if src[y][x] is None or (x, y) in edge:
                    continue
                if (x, y - 1) in edge or (x - 1, y) in edge:
                    self.p[y][x] = shade(src[y][x], lit)
                elif (x, y + 1) in edge or (x + 1, y) in edge:
                    self.p[y][x] = shade(src[y][x], dim)

    def image(self):
        im = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
        for y in range(16):
            for x in range(16):
                if self.p[y][x] is not None:
                    im.putpixel((x, y), (*self.p[y][x], 255))
        return im


# ------------------------------------------------------------------ items
def kraab_claw():
    q = Pix()
    base, light, dark, tip = "c2641f", "ec9a58", "8a4515", "2a1810"
    q.fill([(0, 11), (3, 9), (6, 12), (3, 15), (0, 15)], dark)  # severed arm stub
    q.disc(7, 9, 4.6, base)  # palm
    q.fill([(3, 7), (6, 3), (10, 1), (14, 1), (14, 3), (12, 5), (10, 7), (8, 9)], base)  # upper pincer
    q.fill([(7, 9), (11, 8), (15, 9), (15, 12), (12, 13), (8, 14)], base)  # lower pincer
    q.bevel(0.35, 1.2, 0.8)
    for x, y in ((5, 6), (6, 5), (7, 4), (8, 3), (9, 2), (10, 2), (4, 8), (5, 7), (6, 7)):  # shell highlight
        q.set(x, y, light)
    for x, y in ((8, 13), (9, 13), (10, 12), (7, 12), (6, 13), (9, 10), (10, 10), (11, 11)):  # underside shading
        q.set(x, y, dark)
    for x, y in ((13, 1), (14, 1), (14, 2), (13, 2), (14, 3), (15, 10), (15, 11), (14, 12), (14, 11)):  # dark tips
        q.set(x, y, tip)
    for x, y in ((9, 7), (10, 6), (11, 5), (12, 4)):  # serrated inner edge
        q.set(x, y, dark)
    q.set(1, 12, "e8c9a0")  # cut end
    q.set(1, 13, "d9a07a")
    q.set(2, 12, "d9a07a")
    return q


def vilgax_core():
    q = Pix()
    q.fill([(4, 0), (11, 0), (15, 4), (15, 11), (11, 15), (4, 15), (0, 11), (0, 4)], "2b2c33")
    q.bevel(0.3, 1.5, 0.7)
    q.disc(7.5, 7.5, 5.6, "15161a")
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - 7.5, y - 7.5)
            if d <= 4.5:
                if d <= 1.3:
                    c = "ffe0d0"
                elif d <= 2.6:
                    c = "ff3a24"
                elif d <= 3.5:
                    c = "b3200f"
                else:
                    c = "2bd649"
                q.set(x, y, c)
            elif d <= 5.4 and (x + y) % 2 == 0:
                q.set(x, y, "7a1a14")
    for x, y in ((6, 4), (7, 4), (5, 5)):
        q.set(x, y, "8bff9d")  # green rim glint
    for x, y in ((2, 2), (13, 2), (2, 13), (13, 13)):
        q.set(x, y, "8a8d99")  # bolts
    return q


def vilgax_beacon():
    q = Pix()
    black, red = "1f1f26", "c21d1d"
    q.fill([(2, 14), (3, 7), (12, 7), (13, 14)], black)  # housing
    q.rect(6, 2, 9, 7, "34353d")  # mast
    q.rect(1, 13, 14, 14, "2a2a31")  # base plate
    q.bevel(0.35, 1.45, 0.75)
    q.rect(3, 12, 12, 12, red)  # red band
    # squid emblem: dome with tentacles, red on black
    for x, y in ((7, 8), (8, 8), (6, 9), (7, 9), (8, 9), (9, 9), (6, 10), (9, 10), (7, 10), (8, 10),
                 (5, 11), (7, 11), (8, 11), (10, 11)):
        q.set(x, y, red)
    q.set(7, 9, "ff9a8a")
    q.set(8, 9, "ff9a8a")
    q.set(4, 8, "4a4b55")
    q.set(11, 8, "4a4b55")
    # antenna and signal arcs
    q.line(7, 0, 7, 2, "3a3b44")
    q.set(8, 0, "3a3b44")
    q.set(7, 0, "ff2a1a")
    q.set(8, 0, "ff6a5a")
    for x, y in ((4, 0), (3, 1), (4, 2), (11, 0), (12, 1), (11, 2)):
        q.set(x, y, "c21d1d")
    for x, y in ((2, 0), (1, 1), (2, 2), (13, 0), (14, 1), (13, 2)):
        q.set(x, y, "7a1414")
    return q


def null_void_projector():
    q = Pix()
    body, light, mid = "b8bcc6", "f2f4f8", "7c808c"
    q.rect(4, 5, 11, 14, body)  # cylinder
    q.rect(3, 4, 12, 6, "d9dce4")  # head collar
    q.bevel(0.32, 1.12, 0.78)
    for y in range(7, 14):  # vertical cylinder shading
        q.set(5, y, light)
        q.set(6, y, light)
        q.set(10, y, mid)
    q.rect(5, 11, 10, 13, "4c4f5a")  # grip
    for y in (11, 13):
        q.rect(5, y, 10, y, "2f3138")
    q.set(6, 8, "58e07a")  # status lights
    q.set(8, 8, "e0584c")
    q.set(9, 8, "e8c24a")
    # lens: purple-black vortex
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - 7.5, y - 2.5)
            if d <= 3.3:
                q.set(x, y, "0c0614" if d < 1.4 else "3a1668" if d < 2.4 else "8a45d8")
    q.set(5, 1, "d9a8ff")
    q.set(6, 0, "d9a8ff")
    q.set(9, 4, "b36cf2")
    q.set(7, 2, "1a0a2a")
    q.set(8, 3, "5a2a98")
    return q


def null_crystal():
    q = Pix()
    q.fill([(7, 0), (11, 3), (11, 10), (7, 15), (3, 10), (3, 3)], "3c1a5e")
    q.fill([(7, 0), (3, 3), (3, 10), (7, 15)], "5a2a8a")  # lit left face
    q.fill([(7, 0), (11, 3), (11, 10), (7, 15)], "2a1245")  # dark right face
    q.fill([(12, 8), (14, 10), (14, 14), (12, 15), (11, 12)], "4a2278")  # small shard
    q.bevel(0.35, 1.0, 0.85)
    for x, y in ((6, 3), (6, 4), (5, 5), (5, 6), (5, 7)):
        q.set(x, y, "e24bf0")  # magenta glow along the ridge
    for x, y in ((7, 6), (7, 7), (8, 8), (8, 9)):
        q.set(x, y, "b020c0")
    q.set(6, 2, "ff9cff")
    q.set(5, 4, "ff7cf5")
    q.set(12, 11, "d63ee8")
    q.set(8, 13, "7a2aa8")
    return q


def master_control():
    q = Pix()
    q.fill([(4, 0), (11, 0), (15, 4), (15, 11), (11, 15), (4, 15), (0, 11), (0, 4)], "e8b81c")
    q.bevel(0.4, 1.35, 0.72)
    q.disc(7.5, 7.5, 5.8, "1a1608")
    for y in range(16):
        for x in range(16):
            if math.hypot(x - 7.5, y - 7.5) <= 6.0 and math.hypot(x - 7.5, y - 7.5) > 5.0:
                q.set(x, y, "a67c0e" if (x + y) % 2 else "c79a14")
    # gold hourglass: bars top and bottom, waist in the middle
    for y, half in ((4, 3), (5, 3), (6, 2), (7, 1), (8, 1), (9, 2), (10, 3), (11, 3)):
        for x in range(8 - half, 8 + half):
            q.set(x, y, "ffef6a")
    q.rect(7, 7, 8, 8, "fff4b0")
    q.set(6, 4, "fff4b0")
    q.set(3, 2, "fff0a0")
    q.set(12, 13, "8a6208")
    return q


# ------------------------------------------------------------------ 2.1 items: Taydenite, Plumber gear
def taydenite():
    q = Pix()
    q.fill([(5, 1), (10, 1), (14, 5), (14, 8), (8, 15), (7, 15), (1, 8), (1, 5)], "e86cb4")  # cut gem
    q.bevel(0.4, 1.15, 0.8)
    q.fill([(5, 2), (10, 2), (12, 5), (3, 5)], "ffc9ea")  # crown (table)
    q.fill([(3, 6), (12, 6), (8, 13), (7, 13)], "f08ac8")  # pavilion
    q.fill([(3, 6), (7, 6), (7, 13)], "d4559e")  # shadowed left facet
    q.fill([(8, 6), (12, 6), (8, 13)], "ff9fd8")  # lit right facet
    for x, y in ((5, 3), (6, 3), (7, 2), (4, 4)):
        q.set(x, y, "ffffff")
    q.set(11, 5, "fff0fa")
    q.set(9, 7, "ffe8f6")
    q.set(5, 7, "b03a82")
    q.set(2, 6, "b03a82")
    return q


def null_guardian_membrane():
    q = Pix()
    q.fill([(2, 3), (7, 1), (12, 2), (14, 6), (13, 11), (9, 14), (4, 13), (1, 9)], "3b2160")
    q.bevel(0.4, 1.3, 0.75)
    for x, y in ((3, 5), (4, 5), (5, 6), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10)):  # magenta veins
        q.set(x, y, "e24bf0")
    for x, y in ((7, 3), (8, 4), (9, 5), (10, 5), (11, 4), (6, 8), (6, 9), (5, 10), (11, 8), (12, 8)):
        q.set(x, y, "b02ec4")
    for x, y in ((4, 3), (3, 4), (6, 2)):
        q.set(x, y, "6a46a0")  # sheen
    for x, y in ((11, 11), (9, 12), (4, 11), (12, 5)):
        q.set(x, y, "241342")
    q.set(8, 8, "ff9cff")
    return q


def prisoner_cuffs():
    q = Pix()
    for cx, cy in ((5, 8), (11, 8)):  # two open rings
        q.disc(cx, cy, 4.4, "e0701a")
    for cx, cy in ((5, 8), (11, 8)):
        for y in range(16):
            for x in range(16):
                if math.hypot(x - cx, y - cy) <= 2.2:
                    q.p[y][x] = None
    q.bevel(0.35, 1.3, 0.75)
    for x, y in ((7, 8), (8, 8), (7, 9), (8, 9)):  # chain link between them
        q.set(x, y, "8a8e99")
    for y in range(10, 13):  # the break in the left cuff, bottom right
        q.set(7, y, None)
    q.set(8, 7, "5a5e69")
    for x, y in ((3, 5), (4, 4), (5, 4), (9, 5), (10, 4), (11, 4)):
        q.set(x, y, "ffb060")  # highlights
    for x, y in ((6, 11), (5, 12), (10, 12), (12, 11)):
        q.set(x, y, "8a4510")
    q.set(2, 8, "2a1810")  # dark hinge
    return q


def cell_key():
    q = Pix()
    q.rect(1, 3, 14, 12, "7d828e")  # key card body
    q.rect(1, 3, 14, 5, "e0701a")  # orange stripe
    q.bevel(0.35, 1.2, 0.8)
    q.rect(2, 7, 9, 8, "2f3138")  # magnetic strip
    q.rect(10, 7, 12, 10, "c9a030")  # chip
    q.set(11, 8, "8a6a10")
    q.rect(2, 10, 6, 10, "5a5e69")  # text lines
    q.rect(2, 11, 4, 11, "5a5e69")
    q.set(12, 4, "ff3a2a")  # red light
    q.set(13, 4, "ff8a7a")
    q.set(12, 5, "b01a10")
    q.set(2, 4, "c4540c")
    return q


def plumber_badge():
    q = Pix()
    q.disc(7.5, 7.5, 7.2, "9aa0ab")
    q.bevel(0.35, 1.25, 0.78)
    q.disc(7.5, 7.5, 5.2, "2a2d35")
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - 7.5, y - 7.5)
            if 5.2 < d <= 6.2:
                q.set(x, y, "c4c8d0" if y < 8 else "6d727c")
    for y, half in ((4, 3), (5, 3), (6, 2), (7, 1), (8, 1), (9, 2), (10, 3), (11, 3)):  # green hourglass
        for x in range(8 - half, 8 + half):
            q.set(x, y, "2fd05a")
    q.rect(7, 7, 8, 8, "b8ffc8")
    for x, y in ((5, 4), (6, 4)):
        q.set(x, y, "8dffa8")
    for x, y in ((9, 11), (10, 11)):
        q.set(x, y, "1d8a3c")
    return q


def plumber_blaster():
    q = Pix()
    q.rect(1, 4, 9, 7, "8a8f99")  # barrel and slide
    q.rect(8, 4, 14, 9, "6d727c")  # body
    q.fill([(9, 9), (13, 9), (12, 15), (8, 15)], "2a2d35")  # grip
    q.rect(2, 5, 8, 5, "2fd05a")  # green stripe
    q.bevel(0.35, 1.25, 0.75)
    q.rect(0, 5, 0, 6, "1a1c21")  # muzzle
    q.set(0, 5, "7dffa0")
    q.rect(10, 5, 12, 6, "1d8a3c")  # energy cell window
    q.set(11, 5, "8dffa8")
    q.set(11, 6, "2fd05a")
    q.set(8, 8, "3a3e48")  # trigger guard
    q.set(7, 9, "3a3e48")
    q.set(7, 8, "3a3e48")
    for y in range(10, 15):
        q.set(10, y, "3a3e48")
    q.set(13, 4, "c4c8d0")
    q.set(12, 4, "c4c8d0")
    return q


def null_void_glider():
    q = Pix()
    q.fill([(1, 14), (2, 4), (9, 1), (14, 6), (15, 14), (9, 11)], "4a2878")  # folded membrane
    q.bevel(0.35, 1.3, 0.78)
    q.fill([(3, 6), (8, 3), (8, 9), (4, 12)], "5f3a96")  # lit fold
    q.fill([(9, 3), (13, 7), (13, 12), (9, 10)], "33194f")  # shaded fold
    q.line(2, 14, 9, 1, "9aa0ab")  # frame spar
    q.line(9, 1, 15, 14, "6d727c")
    q.line(9, 2, 9, 11, "8a8f99")  # centre rib
    for x, y in ((5, 8), (6, 7), (11, 8), (12, 9)):
        q.set(x, y, "c24bd8")  # magenta vein glow
    q.set(2, 14, "c4c8d0")
    q.set(9, 1, "c4c8d0")
    return q


def _plumber_icon(kind):
    black, mid, light, green, green_d = "17191e", "2f333c", "4a4f5b", "2fd05a", "1d8a3c"
    q = Pix()
    if kind == "helmet":
        q.fill([(3, 3), (5, 1), (10, 1), (12, 3), (13, 12), (11, 12), (11, 8), (4, 8), (4, 12), (2, 12)], mid)
        q.bevel(0.35, 1.3, 0.78)
        q.rect(4, 5, 11, 7, black)  # visor
        q.rect(5, 6, 10, 6, "1b3a24")
        q.rect(7, 1, 8, 4, green)  # crest stripe
        q.set(7, 1, "8dffa8")
        q.set(3, 8, green_d)
        q.set(12, 8, green_d)
        q.set(4, 3, light)
        q.set(5, 2, light)
    elif kind == "chestplate":
        q.fill([(1, 2), (4, 1), (6, 3), (9, 3), (11, 1), (14, 2), (15, 7), (12, 7), (12, 14), (3, 14), (3, 7), (0, 7)], mid)
        q.bevel(0.35, 1.3, 0.78)
        q.rect(4, 4, 5, 13, black)  # green chest stripes
        q.rect(10, 4, 11, 13, black)
        q.rect(4, 4, 4, 13, green)
        q.rect(11, 4, 11, 13, green)
        q.rect(7, 6, 8, 7, green)  # badge
        q.set(7, 8, green_d)
        q.set(8, 8, green_d)
        q.set(1, 5, green)
        q.set(14, 5, green)
        q.set(2, 3, light)
        q.set(3, 2, light)
    elif kind == "leggings":
        q.fill([(3, 1), (12, 1), (13, 14), (9, 14), (8, 6), (7, 6), (6, 14), (2, 14)], mid)
        q.bevel(0.35, 1.3, 0.78)
        q.rect(3, 2, 12, 3, black)  # belt
        q.rect(3, 3, 12, 3, green)
        q.set(7, 2, "c4c8d0")
        q.set(8, 2, "c4c8d0")
        q.line(4, 5, 3, 13, green_d)
        q.line(11, 5, 12, 13, green_d)
        q.set(4, 4, light)
        q.set(3, 5, light)
    else:  # boots
        q.fill([(2, 5), (6, 5), (6, 9), (7, 10), (7, 13), (1, 13), (1, 9)], mid)
        q.fill([(9, 5), (13, 5), (14, 9), (14, 13), (8, 13), (8, 10), (9, 9)], mid)
        q.bevel(0.35, 1.3, 0.78)
        q.rect(2, 6, 5, 6, green)
        q.rect(10, 6, 13, 6, green)
        q.rect(1, 12, 6, 12, black)
        q.rect(9, 12, 14, 12, black)
        q.set(3, 8, light)
        q.set(11, 8, light)
    return q


def plumber_helmet():
    return _plumber_icon("helmet")


def plumber_chestplate():
    return _plumber_icon("chestplate")


def plumber_leggings():
    return _plumber_icon("leggings")


def plumber_boots():
    return _plumber_icon("boots")


# ------------------------------------------------------------------ blocks
def _stone(rng, base, shades, speck, speck_density, size=16):
    """Violet-black stone: mottled base with dark/light patches and faint purple specks."""
    px = [[None] * size for _ in range(size)]
    seeds = [(rng.random() * size, rng.random() * size, rng.choice(shades)) for _ in range(18)]
    for y in range(size):
        for x in range(size):
            best, col = 1e9, base
            for sx, sy, c in seeds:  # nearest-seed patches, wrapped so the tile repeats
                for ox in (-size, 0, size):
                    for oy in (-size, 0, size):
                        d = math.hypot(x - sx - ox, y - sy - oy)
                        if d < best:
                            best, col = d, c
            px[y][x] = col
    for y in range(size):
        for x in range(size):
            if rng.random() < speck_density:
                px[y][x] = rng.choice(speck)
    return px


def null_void_stone_pixels():
    rng = random.Random(1337)
    shades = [rgb("1b1426"), rgb("221a30"), rgb("171021"), rgb("281f38")]
    return _stone(rng, rgb("1b1426"), shades, [rgb("4a2a78"), rgb("5f3a96"), rgb("33194f")], 0.05)


def save_px(px, path):
    im = Image.new("RGBA", (16, 16))
    for y in range(16):
        for x in range(16):
            im.putpixel((x, y), (*px[y][x], 255))
    im.save(path)
    return im


def null_void_stone():
    return null_void_stone_pixels()


def null_void_stone_bricks():
    px = null_void_stone_pixels()
    rng = random.Random(99)
    mortar, hi, lo = rgb("0b0710"), rgb("3a2c52"), rgb("120c1c")
    rows = ((0, 7), (8, 15))
    for ri, (y0, y1) in enumerate(rows):
        for x in range(16):
            px[y1][x] = mortar  # bottom mortar line
        cuts = (7, 15) if ri == 0 else (3, 11)
        for cx in cuts:
            for y in range(y0, y1 + 1):
                px[y][cx] = mortar
        # bevel each brick: light top/left, dark bottom/right
        bx = [-1, *cuts] if ri == 0 else [-1, *cuts]
        edges = sorted({(c + 1) % 16 for c in cuts})
        for y in range(y0, y1):
            for x in range(16):
                if px[y][x] == mortar:
                    continue
                left_mortar = px[y][(x - 1) % 16] == mortar
                right_mortar = px[y][(x + 1) % 16] == mortar
                if y == y0 or left_mortar:
                    px[y][x] = hi if rng.random() < 0.8 else px[y][x]
                elif y == y1 - 1 or right_mortar:
                    px[y][x] = lo
    for _ in range(5):  # speckle
        x, y = rng.randrange(16), rng.randrange(16)
        if px[y][x] not in (mortar, hi, lo):
            px[y][x] = rgb("5b3590")
    return px


def null_crystal_ore():
    px = null_void_stone_pixels()
    pal = ["4a0e5a", "9a1fb0", "e24bf0", "ffa8ff"]
    clusters = [(3, 4), (10, 3), (6, 10), (12, 11)]
    shapes = [  # (dx, dy, palette index) relative to the cluster root
        [(0, 0, 1), (1, 0, 2), (0, 1, 1), (1, 1, 1), (0, 2, 0), (1, 2, 0), (1, -1, 3)],
        [(0, 0, 2), (1, 0, 1), (0, 1, 1), (-1, 1, 0), (0, 2, 0), (1, 1, 1)],
    ]
    for i, (cx, cy) in enumerate(clusters):
        for dx, dy, k in shapes[i % 2]:
            x, y = cx + dx, cy + dy
            if 0 <= x < 16 and 0 <= y < 16:
                px[y][x] = rgb(pal[k])
        for dx, dy in ((-1, 0), (2, 0), (-1, 2), (2, 2), (0, 3), (1, 3), (0, -1)):  # dark halo
            x, y = cx + dx, cy + dy
            if 0 <= x < 16 and 0 <= y < 16 and px[y][x] not in map(rgb, pal):
                px[y][x] = rgb("2a1245")
    return px


# ------------------------------------------------------------------ 2.1 blocks
def taydenite_ore():
    px = null_void_stone_pixels()
    pal = ["a8326e", "e86cb4", "ffc9ea", "ffffff"]
    clusters = [(2, 2), (9, 1), (5, 8), (11, 10), (1, 12)]
    shapes = [
        [(0, 0, 1), (1, 0, 2), (0, 1, 1), (1, 1, 0), (1, -1, 3), (2, 1, 0)],
        [(0, 0, 2), (1, 0, 1), (0, 1, 1), (0, 2, 0), (-1, 1, 0), (1, 1, 1)],
    ]
    halo = rgb("3a1530")
    for i, (cx, cy) in enumerate(clusters):
        for dx, dy, k in shapes[i % 2]:
            x, y = cx + dx, cy + dy
            if 0 <= x < 16 and 0 <= y < 16:
                px[y][x] = rgb(pal[k])
        for dx, dy in ((-1, 0), (2, 0), (-1, 2), (2, 2), (0, 3), (1, 3), (0, -1), (3, 1), (-2, 1)):
            x, y = cx + dx, cy + dy
            if 0 <= x < 16 and 0 <= y < 16 and px[y][x] not in map(rgb, pal):
                px[y][x] = halo
    return px


def taydenite_block():
    px = [[rgb("ec7cbc")] * 16 for _ in range(16)]
    for ty in range(2):  # four 8x8 facet tiles with X-shaped shading
        for tx in range(2):
            for y in range(8):
                for x in range(8):
                    up, left = y < x, x + y < 7
                    c = ("ffa9dc" if up and left else "f58ac8" if up else "d658a2" if left else "ec7cbc")
                    px[ty * 8 + y][tx * 8 + x] = rgb(c)
    for i in range(16):  # bevelled border and the centre cross
        for y, c in ((0, "ffd9f0"), (15, "9a3572")):
            px[y][i] = rgb(c)
        for x, c in ((0, "ffd9f0"), (15, "9a3572")):
            px[i][x] = rgb(c)
        px[7][i] = rgb("c04a90") if i not in (0, 15) else px[7][i]
        px[i][7] = rgb("c04a90") if i not in (0, 15) else px[i][7]
    for x, y in ((3, 3), (4, 2), (11, 4), (10, 11), (4, 12), (12, 12)):
        px[y][x] = rgb("ffffff")
    return px


def prison_wall():
    rng = random.Random(2101)
    px = [[None] * 16 for _ in range(16)]
    for y in range(16):
        for x in range(16):
            k = rng.choice((0.94, 1.0, 1.0, 1.05))
            px[y][x] = tuple(int(v * k) for v in rgb("6b707b"))
    for x in range(16):  # panel seams
        px[0][x] = rgb("8a8f99")
        px[15][x] = rgb("3a3e48")
        px[7][x] = rgb("3a3e48")
        px[8][x] = rgb("8a8f99")
    for y in range(16):
        px[y][0] = rgb("8a8f99")
        px[y][15] = rgb("3a3e48")
    for y in (3, 12):  # rivets
        for x in (3, 12):
            px[y][x] = rgb("a9aeb8")
            px[y + 1][x + 1] = rgb("3a3e48")
    for y in range(5, 11):  # orange hazard stripe, diagonal bars
        for x in range(16):
            if y in (5, 10):
                px[y][x] = rgb("2a2d35")
            else:
                px[y][x] = rgb("e0701a") if ((x + y) // 2) % 2 == 0 else rgb("1f2127")
    return px


def prison_vault_front():
    px = [[rgb("55596a")] * 16 for _ in range(16)]
    rng = random.Random(2102)
    for y in range(16):
        for x in range(16):
            k = rng.choice((0.94, 1.0, 1.05))
            px[y][x] = tuple(int(v * k) for v in rgb("e0701a"))
    for i in range(16):  # grey frame
        for t in range(2):
            for (x, y) in ((i, t), (i, 15 - t), (t, i), (15 - t, i)):
                px[y][x] = rgb("6b707b") if t == 0 else rgb("4a4e58")
    for y in (5, 10):  # door seams
        for x in range(2, 14):
            px[y][x] = rgb("a8500e")
    for y in range(2, 14):
        px[y][9] = rgb("a8500e")
    for y in range(2, 14):
        px[y][2] = rgb("f09a50") if y in (3, 4) else px[y][2]
    # key-card reader on the right: housing, slot and a red light
    for y in range(3, 13):
        for x in range(10, 14):
            px[y][x] = rgb("454952")
    for y in range(3, 13):
        px[y][10] = rgb("5a5e69")
        px[y][13] = rgb("2f3138")
    for y in range(7, 11):
        px[y][11] = rgb("0e0f12")
        px[y][12] = rgb("0e0f12")
    px[4][11] = rgb("ff2a1a")
    px[4][12] = rgb("ff7a6a")
    px[5][11] = rgb("a01208")
    px[5][12] = rgb("a01208")
    # lock bolts on the left
    for y in (3, 11):
        px[y][4] = rgb("9aa0ab")
        px[y][5] = rgb("5a5e69")
    px[7][4] = rgb("2a2d35")
    px[7][5] = rgb("2a2d35")
    px[8][4] = rgb("2a2d35")
    px[8][5] = rgb("2a2d35")
    return px


def prison_vault_side():
    px = prison_wall()
    for y in range(16):  # plain panel: no hazard stripe, orange corner trim
        for x in range(16):
            if 5 <= y <= 10:
                px[y][x] = rgb("6b707b") if (x + y) % 5 else rgb("626772")
    for i in range(3):
        px[1][1 + i] = rgb("e0701a")
        px[1 + i][1] = rgb("e0701a")
        px[14][14 - i] = rgb("a8500e")
        px[14 - i][14] = rgb("a8500e")
    for x in range(16):
        px[7][x] = rgb("3a3e48")
        px[8][x] = rgb("8a8f99")
    return px


def prison_vault_top():
    px = prison_wall()
    rng = random.Random(2103)
    for y in range(1, 15):
        for x in range(1, 15):
            k = rng.choice((0.95, 1.0, 1.04))
            px[y][x] = tuple(int(v * k) for v in rgb("6b707b"))
    for i in range(16):
        px[0][i] = px[15][i] = rgb("e0701a")
        px[i][0] = px[i][15] = rgb("e0701a")
        px[1][i] = rgb("a8500e") if 0 < i < 15 else px[1][i]
        px[14][i] = rgb("a8500e") if 0 < i < 15 else px[14][i]
        px[i][1] = rgb("a8500e") if 0 < i < 15 else px[i][1]
        px[i][14] = rgb("a8500e") if 0 < i < 15 else px[i][14]
    for y, x in ((3, 3), (3, 12), (12, 3), (12, 12)):
        px[y][x] = rgb("a9aeb8")
    return px


def plumber_hull():
    rng = random.Random(2104)
    px = [[None] * 16 for _ in range(16)]
    for y in range(16):
        for x in range(16):
            k = rng.choice((0.92, 1.0, 1.0, 1.06))
            px[y][x] = tuple(int(v * k) for v in rgb("3a3e48"))
    for y in range(16):  # plate edges: light top-left, dark bottom-right of each 8x8 plate
        for x in range(16):
            if y % 8 == 0 or x % 8 == 0:
                px[y][x] = rgb("565b68")
            elif y % 8 == 7 or x % 8 == 7:
                px[y][x] = rgb("22252c")
    for y in (2, 5, 10, 13):  # rivets
        for x in (2, 5, 10, 13):
            px[y][x] = rgb("6d727c")
    for y in (6, 7, 8):  # green stripe across the middle, glow-lit centre line
        for x in range(16):
            px[y][x] = rgb("2fd05a") if y == 7 else rgb("1d8a3c") if y == 6 else rgb("145f2a")
    for x in range(16):
        if x % 4 == 1:
            px[7][x] = rgb("8dffa8")
    return px


# ------------------------------------------------------------------ armour layers
def _paint(im, box, base, seed, edge=True):
    """Fill a UV rectangle with noisy plating and a darker 1 px edge."""
    rng = random.Random(seed)
    x0, y0, x1, y1 = box
    for y in range(y0, y1):
        for x in range(x0, x1):
            k = rng.choice((0.9, 1.0, 1.0, 1.08))
            im.putpixel((x, y), (*[int(v * k) for v in rgb(base)], 255))
    if edge:
        for x in range(x0, x1):
            im.putpixel((x, y0), (*rgb("4a4f5b"), 255))
            im.putpixel((x, y1 - 1), (*rgb("14161b"), 255))
        for y in range(y0, y1):
            im.putpixel((x0, y), (*rgb("3a3e48"), 255))
            im.putpixel((x1 - 1, y), (*rgb("14161b"), 255))


def _dot(im, x, y, c):
    im.putpixel((x, y), (*rgb(c), 255))


def plumber_layer_1():
    """Helmet, chestplate and boots on the vanilla 64x32 armour UV."""
    im = Image.new("RGBA", (64, 32), (0, 0, 0, 0))
    green, green_d, black = "2fd05a", "1d8a3c", "0e0f12"
    # head (0,0): faces are 8x8 at right 0,8 / front 8,8 / left 16,8 / back 24,8, top 8,0, bottom 16,0
    for box in ((8, 0, 16, 8), (0, 8, 8, 16), (8, 8, 16, 16), (16, 8, 24, 16), (24, 8, 32, 16)):
        _paint(im, box, "2f333c", 11)
    _paint(im, (16, 0, 24, 8), "22252c", 12)
    for x in range(9, 15):  # visor across the front face
        for y in (11, 12, 13):
            _dot(im, x, y, black)
        _dot(im, x, 12, "1b3a24")
    for x in (11, 12):
        for y in range(8, 11):
            _dot(im, x, y, green)
        for y in range(0, 8):
            _dot(im, x, y, green)  # centre stripe along the top face
    for x in (3, 4, 27, 28):  # side/back accents
        for y in range(9, 11):
            _dot(im, x, y, green_d)
    # body (16,16): top 20,16 / bottom 28,16 / right 16,20 / front 20,20 / left 28,20 / back 32,20
    for box in ((20, 16, 28, 20), (28, 16, 36, 20), (16, 20, 20, 32), (20, 20, 28, 32), (28, 20, 32, 32), (32, 20, 40, 32)):
        _paint(im, box, "2f333c", 21)
    for x in (21, 26):  # front stripes
        for y in range(21, 31):
            _dot(im, x, y, green)
            _dot(im, x + (1 if x == 21 else -1), y, black)
    for y in range(23, 27):  # hourglass badge between the stripes
        w = 2 if y in (23, 26) else 1
        for x in range(24 - w, 24 + w):
            _dot(im, x, y, green if y != 24 else "b8ffc8")
    for x in (34, 37):  # back stripes
        for y in range(21, 31):
            _dot(im, x, y, green_d)
    for y in range(29, 32):  # belt line on every side
        for x in range(16, 40):
            if im.getpixel((x, y))[3]:
                _dot(im, x, y, "17191e")
    # arms (40,16): top 44,16 / bottom 48,16 / right 40,20 / front 44,20 / left 48,20 / back 52,20
    for box in ((44, 16, 48, 20), (48, 16, 52, 20), (40, 20, 44, 32), (44, 20, 48, 32), (48, 20, 52, 32), (52, 20, 56, 32)):
        _paint(im, box, "2f333c", 31)
    for x0 in (40, 44, 48, 52):  # green shoulder band and cuff band
        for x in range(x0, x0 + 4):
            _dot(im, x, 22, green)
            _dot(im, x, 29, green_d)
            _dot(im, x, 30, "17191e")
    # boots: bottom six rows of the leg UV (0,16) plus the sole (8,16)
    _paint(im, (8, 16, 12, 20), "14161b", 41)
    for box in ((0, 26, 4, 32), (4, 26, 8, 32), (8, 26, 12, 32), (12, 26, 16, 32)):
        _paint(im, box, "2f333c", 42)
    for x in range(0, 16):
        _dot(im, x, 26, green)
        _dot(im, x, 31, "14161b")
    return im


def plumber_layer_2():
    """Leggings (and the belt line of the body) on the vanilla 64x32 armour UV."""
    im = Image.new("RGBA", (64, 32), (0, 0, 0, 0))
    green, green_d = "2fd05a", "1d8a3c"
    # legs (0,16): top 4,16 / bottom 8,16 / right 0,20 / front 4,20 / left 8,20 / back 12,20
    _paint(im, (4, 16, 8, 20), "22252c", 51)
    for box in ((0, 20, 4, 30), (4, 20, 8, 30), (8, 20, 12, 30), (12, 20, 16, 30)):
        _paint(im, box, "2a2d35", 52)
    for x in range(0, 16):  # belt/hip stripe and ankle trim
        _dot(im, x, 20, "17191e")
        _dot(im, x, 29, "17191e")
    for y in range(21, 29):  # green seam down the outer side and the front
        _dot(im, 3, y, green_d)
        _dot(im, 5, y, green)
    for x in (4, 5, 6, 7):  # knee plate
        _dot(im, x, 24, "4a4f5b")
        _dot(im, x, 25, "4a4f5b")
    # body belt (16,16): the bottom three rows of each side plus the underside
    for box in ((28, 16, 36, 20),):
        _paint(im, box, "22252c", 53)
    for box in ((16, 29, 20, 32), (20, 29, 28, 32), (28, 29, 32, 32), (32, 29, 40, 32)):
        _paint(im, box, "17191e", 54)
    for x in range(20, 28):
        _dot(im, x, 29, green)
    _dot(im, 23, 30, "c4c8d0")
    _dot(im, 24, 30, "c4c8d0")
    return im


# ------------------------------------------------------------------ main
ITEMS = {"kraab_claw": kraab_claw, "vilgax_core": vilgax_core, "vilgax_beacon": vilgax_beacon,
         "null_void_projector": null_void_projector, "null_crystal": null_crystal, "master_control": master_control,
         "taydenite": taydenite, "null_guardian_membrane": null_guardian_membrane,
         "prisoner_cuffs": prisoner_cuffs, "cell_key": cell_key, "plumber_badge": plumber_badge,
         "plumber_blaster": plumber_blaster, "null_void_glider": null_void_glider,
         "plumber_helmet": plumber_helmet, "plumber_chestplate": plumber_chestplate,
         "plumber_leggings": plumber_leggings, "plumber_boots": plumber_boots}
BLOCKS = {"null_void_stone": null_void_stone, "null_void_stone_bricks": null_void_stone_bricks,
          "null_crystal_ore": null_crystal_ore, "taydenite_ore": taydenite_ore, "taydenite_block": taydenite_block,
          "prison_wall": prison_wall, "prison_vault_front": prison_vault_front,
          "prison_vault_side": prison_vault_side, "prison_vault_top": prison_vault_top,
          "plumber_hull": plumber_hull}


def main():
    (TEX / "item").mkdir(parents=True, exist_ok=True)
    (TEX / "block").mkdir(parents=True, exist_ok=True)
    (TEX / "models/armor").mkdir(parents=True, exist_ok=True)
    OUT.mkdir(exist_ok=True)
    tiles = []
    for name, fn in ITEMS.items():
        im = fn().image()
        im.save(TEX / "item" / f"{name}.png")
        tiles.append((name, im))
    for name, fn in BLOCKS.items():
        im = save_px(fn(), TEX / "block" / f"{name}.png")
        tiles.append((name, im))
    layers = {"plumber_layer_1": plumber_layer_1(), "plumber_layer_2": plumber_layer_2()}
    for name, im in layers.items():
        im.save(TEX / "models/armor" / f"{name}.png")
    # preview: each tile at 12x on a checker, plus a 3x3 tiling of each block to check seams; 8 tiles a row
    s, pad, cols = 12, 12, 8
    rows = (len(tiles) + cols - 1) // cols
    cell = 16 * s + pad
    sheet = Image.new("RGBA", (cols * cell + pad, rows * (cell * 2) + pad), (44, 48, 56, 255))
    for i, (name, im) in enumerate(tiles):
        x0 = pad + (i % cols) * cell
        y0 = pad + (i // cols) * cell * 2
        chk = Image.new("RGBA", (16 * s, 16 * s))
        d = ImageDraw.Draw(chk)
        for cy in range(16):
            for cx in range(16):
                d.rectangle((cx * s, cy * s, cx * s + s - 1, cy * s + s - 1),
                            fill=(70, 74, 84, 255) if (cx + cy) % 2 else (58, 62, 72, 255))
        chk.alpha_composite(im.resize((16 * s, 16 * s), Image.NEAREST))
        sheet.paste(chk, (x0, y0))
        tile = Image.new("RGBA", (48, 48))
        for ty in range(3):
            for tx in range(3):
                tile.paste(im, (tx * 16, ty * 16))
        sheet.paste(tile.resize((16 * s, 16 * s), Image.NEAREST), (x0, y0 + cell))
    sheet.convert("RGB").save(OUT / "sheet.png")
    # actual-size check: every tile at 1x and 3x on a mid-grey slot background, the way an inventory shows it
    small = Image.new("RGBA", (len(tiles) * 52 + 8, 64), (139, 139, 139, 255))
    for i, (name, im) in enumerate(tiles):
        small.alpha_composite(im, (8 + i * 52, 8))
        small.alpha_composite(im.resize((32, 32), Image.NEAREST), (8 + i * 52, 28))
    small.convert("RGB").save(OUT / "sheet_actual_size.png")
    # armour layers at 8x on a checker
    arm = Image.new("RGBA", (64 * 8 * 2 + 24, 32 * 8 + 16), (44, 48, 56, 255))
    for i, im in enumerate(layers.values()):
        chk = Image.new("RGBA", (64 * 8, 32 * 8), (58, 62, 72, 255))
        chk.alpha_composite(im.resize((64 * 8, 32 * 8), Image.NEAREST))
        arm.paste(chk, (8 + i * (64 * 8 + 8), 8))
    arm.convert("RGB").save(OUT / "armor.png")
    print("wrote", ", ".join(n for n, _ in tiles), ", ".join(layers))


if __name__ == "__main__":
    main()
