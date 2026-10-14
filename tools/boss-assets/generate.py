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


# ------------------------------------------------------------------ main
ITEMS = {"kraab_claw": kraab_claw, "vilgax_core": vilgax_core, "vilgax_beacon": vilgax_beacon,
         "null_void_projector": null_void_projector, "null_crystal": null_crystal, "master_control": master_control}
BLOCKS = {"null_void_stone": null_void_stone, "null_void_stone_bricks": null_void_stone_bricks,
          "null_crystal_ore": null_crystal_ore}


def main():
    (TEX / "item").mkdir(parents=True, exist_ok=True)
    (TEX / "block").mkdir(parents=True, exist_ok=True)
    OUT.mkdir(exist_ok=True)
    tiles = []
    for name, fn in ITEMS.items():
        im = fn().image()
        im.save(TEX / "item" / f"{name}.png")
        tiles.append((name, im))
    for name, fn in BLOCKS.items():
        im = save_px(fn(), TEX / "block" / f"{name}.png")
        tiles.append((name, im))
    # preview: each tile at 12x on a checker, plus a 3x3 tiling of each block to check seams
    s, pad = 12, 12
    sheet = Image.new("RGBA", (len(tiles) * (16 * s + pad) + pad, 16 * s * 2 + pad * 3), (44, 48, 56, 255))
    for i, (name, im) in enumerate(tiles):
        x0 = pad + i * (16 * s + pad)
        chk = Image.new("RGBA", (16 * s, 16 * s))
        d = ImageDraw.Draw(chk)
        for cy in range(16):
            for cx in range(16):
                d.rectangle((cx * s, cy * s, cx * s + s - 1, cy * s + s - 1),
                            fill=(70, 74, 84, 255) if (cx + cy) % 2 else (58, 62, 72, 255))
        chk.alpha_composite(im.resize((16 * s, 16 * s), Image.NEAREST))
        sheet.paste(chk, (x0, pad))
        tile = Image.new("RGBA", (48, 48))
        for ty in range(3):
            for tx in range(3):
                tile.paste(im, (tx * 16, ty * 16))
        sheet.paste(tile.resize((16 * s, 16 * s), Image.NEAREST), (x0, pad * 2 + 16 * s))
    sheet.convert("RGB").save(OUT / "sheet.png")
    print("wrote", ", ".join(n for n, _ in tiles))


if __name__ == "__main__":
    main()
