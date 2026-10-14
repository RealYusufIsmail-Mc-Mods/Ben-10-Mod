"""Boss and mob models: Vilgax, his drone, Zs'Skayr and the Null Void prisoner.

Same authoring API as aliens.py. A player is 32 px tall; hitboxes are in the asset contract
(Vilgax 54 px, Zs'Skayr 48 px, prisoner 32 px, drone 12 px). The model faces north (-Z).
"""
import numpy as np

from aliens import eye, on, soft, stripe
from engine import Model, flat, glow, hexrgb, metal, skin

RED = "ff2a1a"
RED_HOT = "ffc0b0"


def hband(color, y0, y1, emissive=0.0):
    """Horizontal band across a face, from y0 to y1 units down from the top edge."""
    c = hexrgb(color)

    def painter(x, y, w, h):
        return soft(np.maximum(y0 - y, y - y1), 0.12), c, emissive
    return painter


def ring_eye(color, hot, pupil="0a0a0c"):
    """One big eye: glowing iris with a dark slit pupil."""
    c, h, p = hexrgb(color), hexrgb(hot), hexrgb(pupil)

    def painter(x, y, w, hh):
        dx, dy = (x - w / 2) / (w / 2), (y - hh / 2) / (hh / 2)
        d = np.hypot(dx, dy)
        disc = soft(d - 0.92, 0.12)
        col = c * (1 - np.clip(0.9 - d, 0, 1)[:, None]) + h * np.clip(0.9 - d, 0, 1)[:, None]
        slit = soft(np.maximum(np.abs(dx) - 0.16, np.abs(dy) - 0.7), 0.12)
        col = col * (1 - slit[:, None]) + p * slit[:, None]
        return disc, col, disc * (1 - slit)
    return painter


# ------------------------------------------------------------------ Vilgax (54 px)
def vilgax():
    m = Model("vilgax", 256, 256)
    green = skin("5f9f3b", "386a22", "8fcb55", 0.3, 7)
    green_d = skin("4a8230", "2b5319", "68a244", 0.35, 8)
    black = metal("19181e", 0.8, 43)
    cloth = flat("27262d", 0.06)
    red = flat("b01a1a", 0.05)
    steel = metal("5a5b66", 0.9, 44)
    for b, piv, par in (("body", (0, 21, 0), "root"), ("head", (0, 40, 0), "body"),
                        ("tentacles", (0, 43, -4), "head"),
                        ("right_arm", (9.2, 37.5, 0), "body"), ("left_arm", (-9.2, 37.5, 0), "body"),
                        ("right_leg", (4.6, 21, 0), "root"), ("left_leg", (-4.6, 21, 0), "root")):
        m.bone(b, piv, par)
    # legs: dark trousers, black greaves, red knee plates, heavy boots
    m.sym("right_leg", "left_leg", (0.4, 9, -4.0), (8.4, 12.5, 8.0), cloth)
    m.sym("right_leg", "left_leg", (0.2, 3.5, -4.4), (8.8, 7.5, 8.8), black)
    m.sym("right_leg", "left_leg", (1.2, 8.2, -5.1), (6.4, 3.2, 1.0), red)  # knee plate
    m.sym("right_leg", "left_leg", (0.0, 0, -6.4), (9.2, 4.2, 12.4), black)  # boot
    m.sym("right_leg", "left_leg", (0.6, 3.2, -6.5), (8.0, 0.9, 0.2), red, ao=False)
    # torso: green skin, black chest plate and back plate
    m.box("body", (-9, 22, -4.6), (18, 17.6, 9.2), green)
    m.box("body", (-9.6, 21, -5.0), (19.2, 4.2, 10.0), black)  # belt
    buckle = m.box("body", (-2.6, 21.2, -5.6), (5.2, 3.8, 0.8), red)
    m.decal(on(buckle, "north", stripe("19181e", 2.0, 3.2)))
    plate = m.box("body", (-5.6, 25, -5.4), (11.2, 10.0, 1.0), black)
    m.decal(on(plate, "north", stripe("b01a1a", 4.9, 6.3)))  # red centre stripe
    m.decal(on(plate, "north", hband("b01a1a", 0.0, 0.9)))
    m.decal(on(plate, "north", hband("b01a1a", 9.0, 10.0)))
    m.box("body", (-5.6, 24.4, -5.5), (11.2, 0.9, 1.0), red)
    m.box("body", (-6.4, 25, 4.4), (12.8, 11.0, 1.0), black)  # back plate
    m.box("body", (-1.0, 24, 4.9), (2.0, 11, 0.6), red)
    for x in (-6.5, 4.5):  # abs peeking beside the plate
        m.box("body", (x, 26.0, -4.9), (2.0, 8.0, 0.4), green_d)
    m.box("body", (-5.4, 38.2, -3.4), (10.8, 1.6, 6.8), black)  # collar
    # shoulder pads with red trim and spikes, arms
    for b, sgn in (("right_arm", 1), ("left_arm", -1)):
        x = 6.6 if sgn > 0 else -12.2
        pad = m.box(b, (x, 36.6, -5.0), (5.6, 3.6, 10.0), black)
        m.decal(on(pad, "up", stripe("b01a1a", 0.0, 0.9)))
        m.box(b, (x + 0.3, 40.2, -2.0), (5.0, 0.9, 4.0), red)
        m.box(b, (x + 1.4, 41.1, -0.8), (2.8, 1.8, 1.6), black)  # spike
        ax = 7.2 if sgn > 0 else -11.8
        m.box(b, (ax, 27.5, -2.6), (4.6, 9.6, 5.2), green)  # upper arm
        m.box(b, (ax - 0.4, 20.0, -3.0), (5.4, 8.0, 6.0), black)  # bracer
        m.box(b, (ax - 0.5, 25.6, -3.1), (5.6, 1.0, 6.2), red)
        m.box(b, (ax - 0.2, 15.6, -2.8), (5.0, 4.6, 5.6), green)  # fist
        for i in range(3):
            m.box(b, (ax + 0.4 + i * 1.5, 14.4, -3.3), (1.0, 1.6, 1.0), black)  # claws
    # neck and head (taller torso, broad bald squid skull)
    m.box("head", (-3.4, 39.6, -2.8), (6.8, 1.6, 5.6), green_d)
    m.box("head", (-5.2, 40.4, -4.6), (10.4, 9.0, 9.2), green)
    m.box("head", (-4.8, 49.4, -4.2), (9.6, 1.8, 8.4), green)  # dome
    m.box("head", (-3.6, 51.2, -3.2), (7.2, 1.6, 6.4), green)
    m.box("head", (-1.6, 52.8, -2.0), (3.2, 1.0, 4.0), green_d)  # crest
    m.box("head", (-5.3, 46.2, -5.2), (10.6, 1.6, 1.0), green_d)  # brow ridge
    m.sym("head", "head", (0.8, 43.0, -4.9), (3.2, 3.2, 0.4), flat("1f4214", 0.03))  # sockets
    m.sym("head", "head", (1.1, 43.6, -5.15), (2.6, 1.8, 0.3), glow(RED, RED_HOT), ao=False)  # eyes
    m.box("head", (-3.4, 41.4, -4.8), (6.8, 2.0, 0.3), flat("1a1410", 0.02))  # mouth hole
    # squid beard: five tentacles hanging from the mouth, front-most in the middle
    for x, ln in ((-4.4, 9.0), (-2.4, 12.0), (-0.1, 14.0), (2.1, 12.0), (4.2, 9.0)):
        m.box("tentacles", (x - 0.2, 43.0 - ln * 0.5, -5.9), (2.2, ln * 0.5, 2.2), green_d)
        m.box("tentacles", (x + 0.1, 43.0 - ln * 0.85, -5.7), (1.6, ln * 0.35 + 0.1, 1.8), green)
        m.box("tentacles", (x + 0.3, 43.0 - ln - 0.9, -5.5), (1.1, ln * 0.15 + 1.0, 1.4), green_d)
    return m


# ------------------------------------------------------------------ Vilgax drone (12 px)
def vilgax_drone():
    m = Model("vilgax_drone", 128, 128, ao_strength=0.5)
    shell = metal("43464f", 1.0, 61)
    dark = metal("202228", 1.0, 62)
    for b, piv, par in (("body", (0, 6, 0), "root"),
                        ("right_fin", (5.0, 6, 0), "body"), ("left_fin", (-5.0, 6, 0), "body")):
        m.bone(b, piv, par)
    m.box("body", (-5.0, 2.2, -3.4), (10.0, 7.6, 6.8), shell)
    m.box("body", (-3.4, 2.2, -5.0), (6.8, 7.6, 10.0), shell)
    m.box("body", (-3.6, 0.8, -3.6), (7.2, 10.4, 7.2), shell)
    ring = m.box("body", (-5.2, 5.0, -3.5), (10.4, 1.6, 7.0), dark)  # equator band
    m.box("body", (-3.5, 5.0, -5.2), (7.0, 1.6, 10.4), dark)
    m.box("body", (-3.6, 0.0, -3.6), (7.2, 0.9, 7.2), dark)  # underside
    m.box("body", (-3.0, 11.2, -3.0), (6.0, 0.7, 6.0), dark)  # cap
    m.box("body", (-0.3, 11.8, -0.3), (0.6, 1.6, 0.6), dark)  # antenna
    m.box("body", (-0.45, 13.2, -0.45), (0.9, 0.5, 0.9), glow(RED, RED_HOT), ao=False)
    m.box("body", (-2.5, 6.5, -5.35), (5.0, 4.2, 0.4), flat("0d0d10", 0.02))  # eye socket
    lens = m.box("body", (-2.1, 6.7, -5.65), (4.2, 3.6, 0.4), flat("0d0d10", 0.02), ao=False)  # the eye
    m.decal(on(lens, "north", eye(RED, "fff0e8")))
    for b, x in (("right_fin", 4.9), ("left_fin", -6.3)):
        m.box(b, (x, 4.0, -2.8), (1.4, 5.0, 5.6), dark)
        m.box(b, (x + (0.3 if x > 0 else -0.3), 2.6, -1.4), (1.1, 2.0, 2.8), shell)
    return m


# ------------------------------------------------------------------ Zs'Skayr (48 px, floating)
def zs_skayr():
    m = Model("zs_skayr", 256, 256)
    pale = skin("dcdfe6", "8b909e", "ffffff", 0.35, 111)
    black = flat("16141b", 0.05)
    cloak = skin("2d2a36", "17151d", "46414f", 0.4, 77)
    steel = metal("8f939c", 1.0, 71)
    for b, piv, par in (("body", (0, 22, 0), "root"), ("head", (0, 36, 0), "body"),
                        ("tail", (0, 22, 0), "body"),
                        ("right_arm", (6.0, 34, 0), "body"), ("left_arm", (-6.0, 34, 0), "body"),
                        ("chains", (0, 30, -3.0), "body")):
        m.bone(b, piv, par)
    # tapering ghost tail instead of legs
    m.box("tail", (-4.4, 14, -2.6), (8.8, 8.2, 5.2), pale)
    m.box("tail", (-3.4, 8, -2.2), (6.8, 6.4, 4.4), pale)
    m.box("tail", (-2.4, 3.5, -1.6), (4.8, 5.0, 3.2), pale)
    m.box("tail", (-1.2, 0, -0.9), (2.4, 4.0, 1.8), pale)
    # tattered cloak strips hanging at different lengths
    for x, bot, w in ((-4.8, 8.0, 1.4), (-0.7, 5.0, 1.4), (3.4, 9.0, 1.4)):
        m.box("tail", (x, bot, -3.3), (w, 21.5 - bot, 0.7), cloak)
    for x, bot in ((-4.6, 4.0), (-2.0, 1.0), (0.6, 5.0), (3.0, 2.5)):
        m.box("tail", (x, bot, 2.4), (2.4, 21.5 - bot, 0.7), cloak)
    # torso, wrapped in dark bands
    torso = m.box("body", (-5.0, 22, -2.8), (10, 13.5, 5.6), pale)
    for f in ("north", "south"):
        m.decal(on(torso, f, lambda x, y, w, h: (
            soft(np.abs((y * 1.0) % 3.0 - 1.5) - 0.3 + 0.35 * np.abs(x - w / 2) / w, 0.12),
            hexrgb("16141b"), 0)))
    m.box("body", (-5.2, 21.2, -3.1), (10.4, 1.0, 6.2), steel)  # chain belt
    for x in (-4.0, -1.2, 1.6):
        m.box("body", (x, 20.0, -3.3), (1.2, 1.5, 0.6), steel)
    # shawl over the shoulders with ragged lower edge
    m.box("body", (-7.8, 32.4, -3.8), (15.6, 3.4, 7.6), cloak)
    for x, d in ((-7.4, 3.4), (-4.6, 5.2), (-1.8, 2.4), (1.0, 4.6), (3.8, 3.0), (6.0, 5.4)):
        m.box("body", (x, 32.4 - d, -3.9), (2.0, d, 0.5), cloak)
        m.box("body", (x, 32.4 - d * 0.8, 3.4), (2.0, d * 0.8, 0.5), cloak)
    # head: wrapped hood behind, pale skull, black marks, one huge eye
    m.box("head", (-5.0, 35.6, 0.8), (10.0, 9.4, 3.8), cloak)  # hood back
    m.box("head", (-5.2, 36.0, -3.0), (1.0, 8.0, 5.6), cloak)  # hood sides
    m.box("head", (4.2, 36.0, -3.0), (1.0, 8.0, 5.6), cloak)
    m.box("head", (-4.2, 36, -4.2), (8.4, 9, 8.0), pale)
    m.box("head", (-3.0, 45.0, -3.0), (6.0, 2.0, 6.0), pale)
    m.box("head", (-1.3, 46.8, -1.3), (2.6, 1.6, 2.6), pale)
    socket = m.box("head", (-3.4, 37.6, -4.5), (6.8, 5.6, 0.4), flat("0a0b0e", 0.02))
    m.decal(on(socket, "north", ring_eye("5dff6a", "e8ffe8")))
    for x, y, h in ((-4.0, 41.8, 2.8), (3.0, 42.4, 2.2), (-1.2, 43.6, 1.8), (1.6, 36.4, 1.2), (-3.4, 36.4, 1.2)):
        m.box("head", (x, y, -4.35), (1.2, h, 0.3), black)  # black marks
    m.box("head", (-1.2, 35.9, -4.35), (2.4, 0.7, 0.3), black)  # mouth line
    # arms with cuffs, claws and hanging chains
    for b, sgn in (("right_arm", 1), ("left_arm", -1)):
        x = 5.6 if sgn > 0 else -8.8
        m.box(b, (x, 20.0, -1.6), (3.2, 14.0, 3.2), pale)
        m.box(b, (x - 0.3, 20.0, -1.9), (3.8, 1.8, 3.8), black)  # wrist wrap
        m.box(b, (x - 0.3, 29.5, -1.9), (3.8, 1.2, 3.8), cloak)
        for i in range(3):
            m.box(b, (x + 0.2 + i * 1.0, 17.6, -1.0), (0.6, 2.6, 0.6), black)  # claws
        cx = x + 1.2
        for i in range(5):
            m.box(b, (cx, 18.0 - i * 2.4 - 2.2, -0.5 + (0.6 if i % 2 else 0)), (0.9, 1.8, 0.9), steel)
    # chains looped across the chest from shoulder to belt
    for i in range(7):
        m.box("chains", (-4.2 + i * 1.3, 29.0 - i * 1.4 + (0.7 if i % 2 else 0), -3.3), (1.0, 1.0, 0.8), steel)
        m.box("chains", (4.2 - i * 1.3 - 1.0, 29.0 - i * 1.4 + (0.7 if i % 2 else 0), -3.7), (1.0, 1.0, 0.8), steel)
    return m


# ------------------------------------------------------------------ Null Void prisoner (32 px, hunched)
def null_void_prisoner():
    m = Model("null_void_prisoner", 128, 128)
    purple = skin("7d52ab", "4c2c78", "a982d8", 0.4, 91)
    purple_d = skin("63408e", "3a2058", "83599f", 0.4, 92)
    orange = metal("e0701a", 0.9, 93)
    rags = flat("3b2f45", 0.1)
    dark = flat("1d1a22", 0.04)
    for b, piv, par in (("body", (0, 13, 0), "root"), ("head", (0, 24, -2), "body"),
                        ("right_arm", (4.4, 23, -2.0), "body"), ("left_arm", (-4.4, 23, -2.0), "body"),
                        ("right_leg", (2.0, 13, 0), "root"), ("left_leg", (-2.0, 13, 0), "root")):
        m.bone(b, piv, par)
    # bent legs, ragged shorts, ankle cuffs
    m.sym("right_leg", "left_leg", (0.1, 6.6, -2.0), (3.8, 7.4, 4.0), rags)
    m.sym("right_leg", "left_leg", (0.3, 1.8, -2.4), (3.4, 5.4, 3.4), purple_d)
    m.sym("right_leg", "left_leg", (0.0, 0.0, -3.4), (4.0, 2.0, 5.6), purple)  # feet
    m.sym("right_leg", "left_leg", (0.1, 2.4, -2.6), (3.8, 1.5, 3.8), orange)  # ankle cuff
    # hunched torso: two stacked blocks, the upper one pushed forward
    m.box("body", (-3.6, 12.6, -2.0), (7.2, 6.4, 4.2), purple_d)
    m.box("body", (-4.0, 17.6, -3.6), (8.0, 6.4, 5.4), purple)
    m.box("body", (-3.4, 18.4, 1.0), (6.8, 4.0, 1.2), purple_d)  # hump
    m.box("body", (-3.9, 13.4, -2.3), (7.8, 2.6, 4.8), rags)  # tattered waist
    m.box("body", (-1.5, 17.0, -4.0), (0.5, 4.0, 0.4), dark)  # scars
    # head thrust forward, ridged skull, glowing yellow eyes
    m.box("head", (-3.4, 24.0, -6.2), (6.8, 6.4, 6.6), purple)
    m.box("head", (-3.0, 30.2, -5.6), (6.0, 1.2, 5.6), purple)
    m.box("head", (-0.9, 31.2, -4.6), (1.8, 1.2, 3.6), purple_d)  # crest
    m.box("head", (-3.6, 28.2, -6.6), (7.2, 1.0, 1.0), purple_d)  # brow
    m.sym("head", "head", (0.8, 26.8, -6.4), (1.7, 1.3, 0.3), glow("ffc018", "fff2b0"), ao=False)
    m.box("head", (-2.2, 24.7, -6.4), (4.4, 1.2, 0.3), dark)  # mouth
    collar = m.box("head", (-3.9, 22.6, -5.0), (7.8, 1.8, 5.8), orange)  # prison collar
    m.box("head", (-0.6, 21.4, -5.2), (1.2, 1.4, 1.2), dark)  # broken chain stub
    # long arms hanging forward with cuffs
    for b, sgn in (("right_arm", 1), ("left_arm", -1)):
        x = 3.8 if sgn > 0 else -6.8
        m.box(b, (x, 15.0, -3.6), (3.0, 8.4, 3.0), purple)
        m.box(b, (x + 0.1, 8.6, -3.7), (2.8, 6.6, 2.8), purple_d)
        m.box(b, (x - 0.3, 9.4, -4.0), (3.6, 1.8, 3.6), orange)  # wrist cuff
        m.box(b, (x + 0.6, 7.4, -3.2), (1.0, 1.6, 1.0), dark)  # broken chain stub
        m.box(b, (x - 0.1, 6.0, -4.0), (3.2, 2.2, 3.4), purple)  # hand
        for i in range(3):
            m.box(b, (x + 0.1 + i * 1.0, 4.4, -3.6), (0.6, 1.8, 0.6), dark)
    return m


CREATURES = {"vilgax": vilgax, "vilgax_drone": vilgax_drone, "zs_skayr": zs_skayr,
             "null_void_prisoner": null_void_prisoner}
