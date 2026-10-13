"""Generate the Omnitrix Crafter's pixel-perfect block and screen textures.

Run from the repository root: python3 tools/crafter-assets/generate.py
"""

from pathlib import Path

from PIL import Image, ImageDraw


ASSETS = Path(__file__).resolve().parents[2] / "common/src/main/resources/assets/bentenmod/textures"
BLOCK = ASSETS / "block"
GUI = ASSETS / "gui"
PREVIEW = Path(__file__).resolve().parents[2] / "docs/omnitrix-crafter-preview.png"

INK = "#07110f"
SHADOW = "#101e1d"
CASING = "#263835"
EDGE = "#415a51"
STEEL = "#60786a"
LIME = "#76ee54"
GLOW = "#baff89"
DEEP = "#2c8e4b"


def texture(name):
    image = Image.new("RGBA", (16, 16), INK)
    return image, ImageDraw.Draw(image)


def save(image, name):
    image.save(BLOCK / f"omnitrix_crafter_{name}.png")


def hourglass(draw, x, y, scale=1, color=GLOW):
    # Two opposing triangles are legible even on the eight-pixel dial.
    draw.polygon([(x, y), (x + 6 * scale, y), (x + 3 * scale, y + 3 * scale)], fill=color)
    draw.polygon(
        [(x, y + 6 * scale), (x + 6 * scale, y + 6 * scale),
         (x + 3 * scale, y + 3 * scale)],
        fill=color,
    )


def block_textures():
    image, d = texture("base")
    d.rectangle((0, 0, 15, 15), fill=SHADOW, outline=EDGE)
    d.rectangle((2, 2, 13, 13), outline=CASING)
    for x in (2, 13):
        for y in (2, 13):
            d.point((x, y), fill=STEEL)
    save(image, "base")

    image, d = texture("side")
    d.rectangle((0, 0, 15, 15), fill=SHADOW, outline=STEEL)
    d.rectangle((1, 1, 14, 14), outline=CASING)
    d.rectangle((3, 3, 12, 12), fill=INK, outline=EDGE)
    d.line((3, 8, 6, 8), fill=DEEP)
    d.line((9, 8, 12, 8), fill=DEEP)
    d.rectangle((6, 6, 9, 9), fill=DEEP)
    d.line((7, 6, 8, 6), fill=GLOW)
    d.line((7, 9, 8, 9), fill=LIME)
    for x in (1, 14):
        d.line((x, 4, x, 11), fill=EDGE)
    save(image, "side")

    image, d = texture("front")
    d.rectangle((0, 0, 15, 15), fill=SHADOW, outline=STEEL)
    d.rectangle((2, 2, 13, 13), fill=INK, outline=EDGE)
    d.line((4, 4, 11, 4), fill=DEEP)
    d.line((4, 11, 11, 11), fill=DEEP)
    d.polygon([(5, 5), (10, 5), (8, 8), (10, 10), (5, 10), (7, 8)], fill=LIME)
    d.point((7, 7), fill=GLOW)
    d.point((8, 8), fill=GLOW)
    for x in (0, 15):
        d.line((x, 3, x, 12), fill=CASING)
    save(image, "front")

    image, d = texture("top")
    d.rectangle((0, 0, 15, 15), fill=CASING, outline=STEEL)
    d.polygon([(4, 1), (11, 1), (14, 4), (14, 11),
               (11, 14), (4, 14), (1, 11), (1, 4)], fill=INK, outline=EDGE)
    for start, end in [((7, 1), (7, 3)), ((7, 12), (7, 14)),
                       ((1, 7), (3, 7)), ((12, 7), (14, 7))]:
        d.line((*start, *end), fill=LIME)
    d.rectangle((5, 5, 10, 10), outline=DEEP)
    save(image, "top")

    image, d = texture("dial")
    d.rectangle((0, 0, 15, 15), fill=INK)
    d.polygon([(4, 0), (11, 0), (15, 4), (15, 11),
               (11, 15), (4, 15), (0, 11), (0, 4)], fill=CASING, outline=STEEL)
    d.polygon([(5, 2), (10, 2), (13, 5), (13, 10),
               (10, 13), (5, 13), (2, 10), (2, 5)], fill=INK, outline=DEEP)
    d.line((4, 7, 11, 7), fill=DEEP)
    hourglass(d, 4, 4, color=GLOW)
    save(image, "dial")


def frame(draw, box, fill, light=STEEL, dark=INK):
    x0, y0, x1, y1 = box
    draw.rectangle(box, fill=fill, outline=light)
    draw.line((x0 + 1, y1 - 1, x1 - 1, y1 - 1), fill=dark)
    draw.line((x1 - 1, y0 + 1, x1 - 1, y1 - 1), fill=dark)


def screen_texture():
    image = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    d = ImageDraw.Draw(image)
    frame(d, (0, 0, 175, 165), SHADOW)
    d.rectangle((2, 2, 173, 163), outline=EDGE)
    d.rectangle((4, 4, 171, 75), fill=CASING, outline=DEEP)
    d.rectangle((4, 4, 171, 13), fill=INK)
    d.line((4, 14, 171, 14), fill=LIME)
    d.rectangle((4, 75, 171, 163), fill=CASING)
    d.line((5, 76, 170, 76), fill=DEEP)
    d.line((5, 162, 170, 162), fill=DEEP)

    # Input sockets are tied together by a circuitry rail; positions match the menu slots.
    for row in range(3):
        for col in range(3):
            x, y = 29 + col * 18, 16 + row * 18
            frame(d, (x, y, x + 17, y + 17), INK, EDGE, DEEP)
            d.point((x + 2, y + 2), fill=DEEP)
    d.line((26, 16, 26, 69), fill=DEEP)
    d.line((26, 69, 84, 69), fill=DEEP)
    d.line((84, 16, 84, 69), fill=DEEP)
    d.rectangle((26, 15, 27, 17), fill=LIME)
    d.rectangle((83, 68, 85, 69), fill=LIME)

    # A small Omnitrix signal replaces the vanilla crafting arrow.
    d.line((94, 42, 115, 42), fill=DEEP, width=2)
    d.polygon([(111, 37), (118, 42), (111, 47)], fill=LIME)
    d.polygon([(97, 38), (105, 38), (101, 42)], fill=GLOW)
    d.polygon([(97, 47), (105, 47), (101, 43)], fill=GLOW)
    frame(d, (123, 34, 141, 52), INK, LIME, DEEP)
    d.rectangle((120, 31, 144, 55), outline=DEEP)
    for x in (121, 143):
        d.point((x, 43), fill=GLOW)

    for row in range(3):
        for col in range(9):
            x, y = 7 + col * 18, 83 + row * 18
            frame(d, (x, y, x + 17, y + 17), INK, EDGE, DEEP)
    for col in range(9):
        x = 7 + col * 18
        frame(d, (x, 141, x + 17, 158), INK, EDGE, DEEP)
    d.line((7, 138, 168, 138), fill=LIME)
    d.rectangle((5, 79, 6, 80), fill=GLOW)
    d.rectangle((169, 79, 170, 80), fill=GLOW)
    image.save(GUI / "omnitrix_crafter.png")


def recipe_button_texture():
    image = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    d = ImageDraw.Draw(image)
    for y, border in ((0, EDGE), (19, LIME)):
        frame(d, (0, y, 19, y + 17), CASING, border, INK)
        d.rectangle((4, y + 4, 9, y + 13), fill=INK, outline=STEEL)
        d.rectangle((10, y + 4, 15, y + 13), fill=INK, outline=STEEL)
        d.line((9, y + 5, 9, y + 12), fill=LIME)
        d.point((12, y + 7), fill=GLOW)
        d.point((12, y + 10), fill=LIME)
    image.save(GUI / "omnitrix_crafter_buttons.png")


def preview():
    image = Image.new("RGB", (680, 380), INK)
    d = ImageDraw.Draw(image)
    d.text((16, 12), "OMNITRIX CRAFTER  /  BLOCK PIXELS", fill=GLOW)
    for name, x, y in (("front", 16, 46), ("side", 160, 46),
                       ("top", 16, 218), ("dial", 160, 218)):
        face = Image.open(BLOCK / f"omnitrix_crafter_{name}.png").convert("RGB")
        image.paste(face.resize((128, 128), Image.Resampling.NEAREST), (x, y))
        d.text((x, y + 132), name.upper(), fill=STEEL)
    d.text((314, 12), "CRAFTING SCREEN", fill=GLOW)
    screen = Image.open(GUI / "omnitrix_crafter.png").convert("RGBA")
    screen = screen.crop((0, 0, 176, 166)).resize((352, 332), Image.Resampling.NEAREST)
    image.paste(screen, (310, 36), screen)
    image.save(PREVIEW)


if __name__ == "__main__":
    block_textures()
    screen_texture()
    recipe_button_texture()
    preview()
    print("Generated Omnitrix Crafter block and screen textures")
