"""Builds the README / CurseForge page images in docs/images/ from the alien-gen preview renders.

Run tools/alien-gen/preview.py and preview_creatures.py first; they write the renders into tools/alien-gen/out/.
"""
from pathlib import Path

from PIL import Image, ImageDraw, ImageFilter, ImageFont

ROOT = Path(__file__).resolve().parents[2]
RENDERS = ROOT / "tools" / "alien-gen" / "out"
ICON = ROOT / "common/src/main/resources/assets/bentenmod/textures/item/omnitrix.png"
OUT = ROOT / "docs" / "images"

BG = (16, 20, 27)
INK = (236, 240, 244)
DIM = (140, 150, 162)
GREEN = (57, 255, 104)

AV = "/System/Library/Fonts/Avenir Next.ttc"


def font(size, idx):
    return ImageFont.truetype(AV, size, index=idx)


ALIENS = [("heatblast", "Heatblast"), ("wildmutt", "Wildmutt"), ("diamondhead", "Diamondhead"),
          ("xlr8", "XLR8"), ("greymatter", "Grey Matter"), ("fourarms", "Four Arms"),
          ("stinkfly", "Stinkfly"), ("ripjaws", "Ripjaws"), ("upgrade", "Upgrade"),
          ("ghostfreak", "Ghostfreak")]
BOSSES = [("vilgax", "Vilgax", "Overworld boss, summoned with a beacon"),
          ("zs_skayr", "Zs'Skayr", "Null Void boss, drops Master Control"),
          ("vilgax_drone", "Vilgax drone", "Sent in waves when Vilgax is hurt"),
          ("null_void_prisoner", "Null Void prisoner", "Escaped inmates of the Null Void")]


def centered(d, cx, y, text, f, fill):
    d.text((cx - d.textlength(text, font=f) / 2, y), text, font=f, fill=fill)


def square_crop(im, size):
    s = min(im.size)
    cx, cy = im.width // 2, im.height // 2
    return im.crop((cx - s // 2, cy - s // 2, cx + s // 2, cy + s // 2)).resize((size, size), Image.LANCZOS)


def glow(img, xy, r, alpha):
    g = Image.new("RGBA", img.size, (0, 0, 0, 0))
    ImageDraw.Draw(g).ellipse((xy[0] - r, xy[1] - r, xy[0] + r, xy[1] + r), fill=GREEN + (alpha,))
    img.alpha_composite(g.filter(ImageFilter.GaussianBlur(r // 3)))


def banner():
    W, H = 1600, 600
    img = Image.new("RGBA", (W, H), BG + (255,))
    glow(img, (1150, 360), 420, 46)
    d = ImageDraw.Draw(img)
    icon = Image.open(ICON).convert("RGBA").resize((128, 128), Image.NEAREST)
    img.alpha_composite(icon, (90, 110))
    d.text((90, 250), "Ben 10 Mod", font=font(96, 8), fill=INK)
    d.text((94, 385), "The Omnitrix, 10 aliens in 3D,", font=font(38, 5), fill=INK)
    d.text((94, 433), "bosses and the Null Void.", font=font(38, 5), fill=INK)
    d.text((94, 505), "Minecraft 1.20.1  ·  Forge & Fabric", font=font(30, 2), fill=GREEN)
    picks = ["fourarms", "heatblast", "diamondhead"]
    for i, n in enumerate(picks):
        im = Image.open(RENDERS / f"{n}.png").convert("RGBA")
        im = im.crop((60, 60, im.width - 60, im.height - 20))
        h = 470
        im = im.resize((int(im.width * h / im.height), h), Image.LANCZOS)
        mask = Image.new("L", im.size, 0)
        ImageDraw.Draw(mask).rounded_rectangle((0, 0, im.width - 1, im.height - 1), 26, fill=255)
        im.putalpha(mask)
        img.alpha_composite(im, (830 + i * 250, 55 + (i % 2) * 50))
    img.convert("RGB").save(OUT / "banner.png", optimize=True)


def aliens():
    cell, pad = 260, 24
    W = 5 * cell + 6 * pad
    H = 2 * (cell + 54) + 3 * pad + 70
    img = Image.new("RGB", (W, H), BG)
    d = ImageDraw.Draw(img)
    centered(d, W // 2, 22, "The 10 aliens", font(44, 0), INK)
    for i, (n, name) in enumerate(ALIENS):
        col, row = i % 5, i // 5
        x = pad + col * (cell + pad)
        y = 92 + row * (cell + 54 + pad)
        im = square_crop(Image.open(RENDERS / f"{n}.png").convert("RGB"), cell)
        mask = Image.new("L", im.size, 0)
        ImageDraw.Draw(mask).rounded_rectangle((0, 0, cell - 1, cell - 1), 22, fill=255)
        img.paste(im, (x, y), mask)
        centered(d, x + cell // 2, y + cell + 10, name, font(28, 2), GREEN)
    img.save(OUT / "aliens.png", optimize=True)


def bosses():
    sheet = Image.open(RENDERS / "creatures_sheet.png").convert("RGB")
    rows = sheet.height // 4
    cw, ch = 560, 400
    pad = 24
    W = 2 * cw + 3 * pad
    H = 2 * (ch + 92) + 3 * pad + 70
    img = Image.new("RGB", (W, H), BG)
    d = ImageDraw.Draw(img)
    centered(d, W // 2, 22, "Bosses and the Null Void", font(44, 0), INK)
    order = ["vilgax", "vilgax_drone", "zs_skayr", "null_void_prisoner"]
    for i, (n, name, blurb) in enumerate(BOSSES):
        r = order.index(n)
        tile = sheet.crop((0, r * rows, sheet.width, (r + 1) * rows))
        tile = tile.crop((0, 20, tile.width, tile.height - 20)).resize((cw, ch), Image.LANCZOS)
        col, row = i % 2, i // 2
        x = pad + col * (cw + pad)
        y = 92 + row * (ch + 92 + pad)
        mask = Image.new("L", tile.size, 0)
        ImageDraw.Draw(mask).rounded_rectangle((0, 0, cw - 1, ch - 1), 22, fill=255)
        img.paste(tile, (x, y), mask)
        d.text((x + 6, y + ch + 10), name, font=font(30, 0), fill=GREEN)
        d.text((x + 6, y + ch + 50), blurb, font=font(24, 5), fill=DIM)
    img.save(OUT / "bosses.png", optimize=True)


if __name__ == "__main__":
    OUT.mkdir(parents=True, exist_ok=True)
    banner()
    aliens()
    bosses()
