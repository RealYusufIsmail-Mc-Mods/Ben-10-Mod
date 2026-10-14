"""Draws the Omnitrix wheel dial (gui/omnitrix_wheel.png): a 512x256 atlas.

  x 0..255    the bezel ring that turns as you scroll (ten raised grips, tick marks, green inlay)
  x 256..511  the static core face (green glass, black hourglass, highlight)

Drawn at 4x and downsampled, so edges are anti-aliased.
Usage: python3 dial.py [assets_dir]
"""
import math
import sys
from pathlib import Path

from PIL import Image, ImageDraw, ImageFilter

ASSETS = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(__file__).resolve().parents[2] / "common/src/main/resources/assets/bentenmod"
S = 4
SIZE = 256 * S
C = SIZE / 2

BLACK = (14, 17, 16, 255)
BEZEL = (38, 44, 42, 255)
BEZEL_HI = (78, 88, 84, 255)
GRIP = (24, 28, 27, 255)
GREEN = (74, 230, 92, 255)
GREEN_DEEP = (22, 120, 40, 255)
GREEN_GLOW = (150, 255, 150, 255)


def ring(draw, r_out, r_in, fill):
    draw.ellipse((C - r_out, C - r_out, C + r_out, C + r_out), fill=fill)
    draw.ellipse((C - r_in, C - r_in, C + r_in, C + r_in), fill=(0, 0, 0, 0))


def polar(r, a):
    return C + r * math.sin(a), C - r * math.cos(a)


def bezel():
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    # outer black lip, metal bezel, inner green inlay channel
    d.ellipse((C - 126 * S, C - 126 * S, C + 126 * S, C + 126 * S), fill=BLACK)
    d.ellipse((C - 121 * S, C - 121 * S, C + 121 * S, C + 121 * S), fill=BEZEL)
    # bevel: lighter top-left crescent
    hi = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    hd = ImageDraw.Draw(hi)
    hd.ellipse((C - 121 * S, C - 121 * S, C + 121 * S, C + 121 * S), fill=BEZEL_HI)
    hd.ellipse((C - 117 * S, C - 113 * S, C + 125 * S, C + 129 * S), fill=(0, 0, 0, 0))
    img.alpha_composite(hi)
    # ten raised grips; the one at 12 o'clock carries the green pointer
    for i in range(10):
        a = i * 2 * math.pi / 10
        w = math.radians(9)
        pts = [polar(119 * S, a - w), polar(119 * S, a + w), polar(96 * S, a + w * 0.7), polar(96 * S, a - w * 0.7)]
        d.polygon(pts, fill=GRIP)
        d.line([polar(118 * S, a - w), polar(118 * S, a + w)], fill=BEZEL_HI, width=2 * S)
    # fine ticks between grips
    for i in range(40):
        if i % 4 == 0:
            continue
        a = (i + 0.0) * 2 * math.pi / 40
        d.line([polar(113 * S, a), polar(104 * S, a)], fill=(60, 70, 66, 255), width=2 * S)
    # green inlay channel with a darker inner edge
    ring(d, 92 * S, 84 * S, GREEN_DEEP)
    inlay = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    ring(ImageDraw.Draw(inlay), 90 * S, 86 * S, GREEN)
    img.alpha_composite(inlay)
    # hollow centre (the core face sits there)
    hole = Image.new("L", (SIZE, SIZE), 255)
    ImageDraw.Draw(hole).ellipse((C - 84 * S, C - 84 * S, C + 84 * S, C + 84 * S), fill=0)
    img.putalpha(Image.composite(img.getchannel("A"), Image.new("L", (SIZE, SIZE), 0), hole))
    return img


def core():
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    d = ImageDraw.Draw(img)
    d.ellipse((C - 84 * S, C - 84 * S, C + 84 * S, C + 84 * S), fill=BLACK)
    # radial green glass
    glass = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    gp = glass.load()
    r_max = 78 * S
    for y in range(int(C - r_max), int(C + r_max)):
        for x in range(int(C - r_max), int(C + r_max)):
            r = math.hypot(x - C, y - C)
            if r > r_max:
                continue
            t = r / r_max
            # brighter towards the upper-left, darker rim
            lx, ly = (x - C) / r_max, (y - C) / r_max
            light = 1.0 - 0.55 * t * t + 0.18 * (-lx - ly)
            gp[x, y] = tuple(max(0, min(255, int(GREEN_DEEP[i] + (GREEN[i] - GREEN_DEEP[i]) * light))) for i in range(3)) + (255,)
    img.alpha_composite(glass)
    # black hourglass
    w, h = 52 * S, 62 * S
    d.polygon([(C - w, C - h), (C + w, C - h), (C + 9 * S, C), (C - 9 * S, C)], fill=BLACK)
    d.polygon([(C - w, C + h), (C + w, C + h), (C + 9 * S, C), (C - 9 * S, C)], fill=BLACK)
    # clip the hourglass to the glass circle
    mask = Image.new("L", (SIZE, SIZE), 0)
    ImageDraw.Draw(mask).ellipse((C - 84 * S, C - 84 * S, C + 84 * S, C + 84 * S), fill=255)
    img.putalpha(Image.composite(img.getchannel("A"), Image.new("L", (SIZE, SIZE), 0), mask))
    # glass highlight
    hl = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    ImageDraw.Draw(hl).ellipse((C - 60 * S, C - 74 * S, C + 10 * S, C - 34 * S), fill=(255, 255, 255, 46))
    img.alpha_composite(hl.filter(ImageFilter.GaussianBlur(6 * S)))
    return img


def main():
    out = Image.new("RGBA", (512, 256), (0, 0, 0, 0))
    out.alpha_composite(bezel().resize((256, 256), Image.LANCZOS), (0, 0))
    out.alpha_composite(core().resize((256, 256), Image.LANCZOS), (256, 0))
    path = ASSETS / "textures" / "gui" / "omnitrix_wheel.png"
    out.save(path)
    print("wrote", path)


if __name__ == "__main__":
    main()
