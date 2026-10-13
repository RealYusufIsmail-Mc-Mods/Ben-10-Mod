"""Model definitions: the Omnitrix, Ben (preview only) and the aliens."""
import numpy as np

from engine import Model, circuitry, crystal, flame, flat, glow, hexrgb, magma, metal, skin

GREEN, GREEN_HOT = "3dff4a", "c8ffcf"


# ------------------------------------------------------------------ decal helpers
def on(cube, face, painter):
    """Paint on one face. painter(x, y, w, h) works in units from the face's top-left corner
    and returns (alpha 0..1, rgb (N,3) or (3,), emissive 0..1)."""
    def fn(s, rgb, emit):
        if s.cube is not cube or s.face != face:
            return rgb, emit
        a, col, e = painter(s.x, s.y, s.w, s.h)
        a = np.broadcast_to(np.asarray(a, float), (len(rgb),))
        col = np.broadcast_to(np.asarray(col, float), rgb.shape)
        e = np.broadcast_to(np.asarray(e, float), (len(rgb),))
        rgb = rgb * (1 - a[:, None]) + col * a[:, None]
        emit = emit * (1 - a) + e * a
        return rgb, emit
    return fn


def soft(d, edge=0.25):
    """1 inside (d < 0), fading to 0 over `edge` units outside."""
    return np.clip(0.5 - d / edge, 0, 1)


def omnitrix_symbol(cx=None, cy=None, r=None, ring="8a8f99", face="0d0e11", rotate=False):
    """Grey ring, black dial, glowing hourglass — the Omnitrix faceplate."""
    rg, fc, g, hot = hexrgb(ring), hexrgb(face), hexrgb(GREEN), hexrgb(GREEN_HOT)

    def painter(x, y, w, h):
        ox = w / 2 if cx is None else cx
        oy = h / 2 if cy is None else cy
        R = min(w, h) / 2 * 0.95 if r is None else r
        dx, dy = x - ox, y - oy
        if rotate:
            dx, dy = dy, dx
        d = np.hypot(dx, dy)
        disc = soft(d - R, 0.2)
        inner = soft(d - R * 0.74, 0.15)
        half = R * (0.07 + 0.62 * np.abs(dy) / (R * 0.62))
        hg = soft(np.maximum(np.abs(dx) - half, np.abs(dy) - R * 0.6), 0.15)
        core = soft(np.abs(dx) - half * 0.45, 0.15) * hg
        col = np.where(inner[:, None] > 0.5, fc, rg * (0.8 + 0.4 * (dy < 0))[:, None])
        col = col * (1 - hg[:, None]) + (g * (1 - core[:, None]) + hot * core[:, None]) * hg[:, None]
        return disc, col, hg
    return painter


def eye(color, hot="ffffff"):
    c, h = hexrgb(color), hexrgb(hot)

    def painter(x, y, w, h_):
        dx, dy = (x / w - 0.5) * 2, (y / h_ - 0.5) * 2
        d = np.hypot(dx * 1.0, dy * 1.2)
        col = c * (1 - np.clip(0.6 - d, 0, 1)[:, None]) + h * np.clip(0.6 - d, 0, 1)[:, None]
        return np.ones(len(x)), col, 1.0
    return painter


def stripe(color, x0, x1, emissive=0.0):
    c = hexrgb(color)

    def painter(x, y, w, h):
        return soft(np.maximum(x0 - x, x - x1), 0.12), c, emissive
    return painter


# ------------------------------------------------------------------ Omnitrix
def omnitrix():
    m = Model("omnitrix", 128, 128, ao_strength=0.55)
    band, band_l, shell = metal("2a2c33"), metal("6b707a"), metal("c9ccd2")
    m.bone("band", (0, 2, 0))
    m.bone("dial", (0, 7, 0), "band")
    m.bone("core", (0, 8.5, 0), "dial")
    # strap around a wrist running along X
    m.box("band", (-3, -1.5, -4.5), (6, 1.5, 9), band)
    m.box("band", (-3, 4, -4.5), (6, 1.5, 9), band)
    for z in (-4.5, 3):
        side = m.box("band", (-3, 0, z), (6, 4, 1.5), band)
        for f in ("north", "south"):
            m.decal(on(side, f, stripe(GREEN, 2.5, 3.5, 1.0)))
    # housing
    m.box("dial", (-4, 5.5, -4), (8, 1.6, 8), band_l)
    m.box("dial", (-3.3, 6.6, -3.3), (6.6, 1.0, 6.6), shell)
    for (x, z) in ((-4.6, -1), (3.6, -1)):
        b = m.box("dial", (x, 5.9, z), (1, 1, 2), glow(GREEN, GREEN_HOT))
    # dial ring (octagon from two slabs) and faceplate
    m.box("core", (-3.6, 7.6, -2.4), (7.2, 1.3, 4.8), metal("8a8f99"))
    m.box("core", (-2.4, 7.6, -3.6), (4.8, 1.3, 7.2), metal("8a8f99"))
    face = m.box("core", (-3, 8.5, -3), (6, 0.6, 6), flat("0d0e11", 0.02))
    m.decal(on(face, "up", omnitrix_symbol(r=2.95, ring="0d0e11", rotate=True)))
    return m


# ------------------------------------------------------------------ Ben (transformation preview only)
def ben():
    m = Model("ben", 128, 128)
    sk, shirt, pants = skin("e7b48f", "c98d68", "f6d2b4"), flat("ecedee", 0.04), flat("4d6b39", 0.08)
    hair, shoe = skin("6b3f22", "4a2914", "8a5634", 0.8), flat("2b2b2e", 0.05)
    for b, piv in (("head", (0, 24, 0)), ("body", (0, 12, 0)), ("right_arm", (5, 22, 0)),
                   ("left_arm", (-5, 22, 0)), ("right_leg", (2, 12, 0)), ("left_leg", (-2, 12, 0))):
        m.bone(b, piv)
    head = m.box("head", (-4, 24, -4), (8, 8, 8), sk)
    m.box("head", (-4.3, 29.4, -4.3), (8.6, 3, 8.6), hair)
    m.box("head", (-4.3, 28.6, -4.45), (8.6, 1, 0.5), hair)
    m.box("head", (-4.3, 24.5, 2.3), (8.6, 5, 2), hair)  # back hair
    for x in (1, -3):
        m.box("head", (x, 26.4, -4.15), (2, 1.2, 0.2), flat("ffffff", 0.02))
        m.box("head", (x + (1 if x > 0 else 0), 26.4, -4.25), (1, 1.2, 0.2), flat("3b9a4a", 0.03))
    body = m.box("body", (-4, 12, -2), (8, 12, 4), shirt)
    m.decal(on(body, "north", stripe("1d1d22", 3.0, 5.0)))
    m.decal(on(body, "south", stripe("1d1d22", 3.0, 5.0)))
    for side, x in (("right_arm", 4), ("left_arm", -8)):
        m.box(side, (x, 12, -2), (4, 12, 4), sk)
        m.box(side, (x - 0.2, 19, -2.2), (4.4, 5, 4.4), shirt)
    m.box("left_arm", (-8.5, 13, -2.5), (5, 2.2, 5), metal("2a2c33"))
    wf = m.box("left_arm", (-7.6, 13.2, -3.1), (3.2, 1.8, 0.6), flat("0d0e11"))
    m.decal(on(wf, "north", omnitrix_symbol(r=0.9)))
    for side, x in (("right_leg", 0), ("left_leg", -4)):
        m.box(side, (x, 2, -2), (4, 10, 4), pants)
        m.box(side, (x - 0.2, 0, -2.6), (4.4, 2.2, 4.8), shoe)
    return m


# ------------------------------------------------------------------ Heatblast
def heatblast():
    m = Model("heatblast", 256, 256)
    rock = magma(scale=0.16, width=0.1)
    plate = magma(rock="4a170c", rock2="240a05", scale=0.13, width=0.09, seed=12)
    fire = flame(24, 41)
    for b, piv in (("body", (0, 12, 0)), ("head", (0, 24, 0)), ("right_arm", (5.5, 22.5, 0)),
                   ("left_arm", (-5.5, 22.5, 0)), ("right_leg", (2.4, 12, 0)), ("left_leg", (-2.4, 12, 0))):
        m.bone(b, piv, "body" if b in ("head", "right_arm", "left_arm") else "root")
    # legs
    m.sym("right_leg", "left_leg", (0.3, 2, -2.1), (4.2, 10.5, 4.2), rock)
    m.sym("right_leg", "left_leg", (0.1, 6, -2.6), (4.6, 3, 1.2), plate)  # knee plate
    m.sym("right_leg", "left_leg", (0.0, 0, -3.2), (4.8, 2.6, 5.4), plate)  # foot
    # torso
    m.box("body", (-4.6, 12, -2.6), (9.2, 12, 5.2), rock)
    m.sym("body", "body", (0.3, 18.6, -3.3), (4.2, 4.6, 0.8), plate)  # pecs
    m.box("body", (-3.6, 12.4, -3.0), (7.2, 3.2, 0.5), plate)  # belly plate
    om = m.box("body", (-1.5, 19.6, -3.6), (3, 3, 0.6), flat("0d0e11"))
    m.decal(on(om, "north", omnitrix_symbol()))
    # arms
    for b, x in (("right_arm", 4.6), ("left_arm", -8.4)):
        m.box(b, (x, 12.5, -1.9), (3.8, 10.5, 3.8), rock)
        m.box(b, (x - 0.3, 20, -2.5), (4.6, 4.6, 5.0), plate)  # shoulder
        m.box(b, (x - 0.2, 9.4, -2.2), (4.2, 3.6, 4.4), plate)  # fist
    # head: a flame core with rock plates forming the face
    m.box("head", (-3.6, 24, -3.4), (7.2, 7.6, 7.0), plate)
    m.box("head", (-3.9, 27.5, -1.0), (7.8, 4.6, 4.8), fire, ao=False)  # flames wrapping the sides
    m.box("head", (-3.9, 29.0, -4.3), (7.8, 1.3, 1.1), plate)  # brow
    m.sym("head", "head", (1.1, 25.6, -4.2), (2.8, 1.5, 0.9), plate)  # cheeks
    m.box("head", (-2.4, 24.0, -4.1), (4.8, 1.4, 0.8), plate)  # jaw
    m.box("head", (-0.55, 26.4, -4.3), (1.1, 2.7, 0.9), plate)  # nose ridge
    m.box("head", (-3.3, 25.2, -3.75), (6.6, 4.0, 0.4), plate)  # rock mask behind the eyes
    for x in (0.7, -2.6):
        m.box("head", (x, 27.3, -4.0), (1.9, 1.2, 0.3), glow("ffe680", "ffffff"), ao=False)
    # crown of flame
    for lo, size in (((-3, 31.6, -2.6), (6, 3.6, 6.2)), ((-2.1, 35.2, -1.4), (4.2, 3.0, 5.0)),
                     ((-1.1, 38.2, 0.2), (2.2, 2.6, 3.2)), ((-0.5, 40.8, 1.2), (1.0, 1.4, 1.6))):
        m.box("head", lo, size, fire, ao=False)
    m.sym("head", "head", (3.0, 29.8, -1.6), (1.5, 3.8, 4.4), fire, ao=False)
    m.sym("head", "head", (2.1, 33.4, 0.2), (1.2, 2.6, 2.6), fire, ao=False)
    return m


# ------------------------------------------------------------------ Four Arms
def fourarms():
    m = Model("fourarms", 256, 256)
    red = skin("c23a2c", "86211a", "e8654d", 0.3, 5)
    pants, belt = flat("1b1c22", 0.06), metal("23242b")
    for b, piv, par in (("body", (0, 18, 0), "root"), ("head", (0, 35, -1), "body"),
                        ("right_arm", (10, 32, -1), "body"), ("left_arm", (-10, 32, -1), "body"),
                        ("right_arm_lower", (9.2, 25, 3), "body"), ("left_arm_lower", (-9.2, 25, 3), "body"),
                        ("right_leg", (3.8, 18, 0), "root"), ("left_leg", (-3.8, 18, 0), "root")):
        m.bone(b, piv, par)
    # legs: black pants with a white stripe, bare feet
    for b, x in (("right_leg", 1.0), ("left_leg", -6.8)):
        leg = m.box(b, (x, 3, -2.9), (5.8, 15.5, 5.8), pants)
        m.decal(on(leg, "north", stripe("e4e4e6", 2.3, 3.5)))
        m.box(b, (x - 0.4, 0, -4.4), (6.6, 3.4, 7.2), red)
    m.box("body", (-7.6, 17, -4.0), (15.2, 3.2, 8.0), belt)
    om = m.box("body", (-1.9, 16.8, -4.7), (3.8, 3.6, 0.7), flat("0d0e11"))
    m.decal(on(om, "north", omnitrix_symbol()))
    # torso
    m.box("body", (-8, 20, -4.2), (16, 14, 8.4), red)
    m.sym("body", "body", (0.3, 28.4, -4.9), (6.9, 5.2, 0.8), red)  # pecs
    for y in (21.0, 23.5, 26.0):
        m.sym("body", "body", (0.35, y, -4.6), (2.5, 2.1, 0.45), red)  # abs
    m.box("body", (-6, 33.6, -3.2), (12, 2.8, 6.4), red)  # traps
    # head: low and forward, four eyes
    m.box("head", (-4.1, 35.0, -4.6), (8.2, 7.4, 7.6), red)
    m.box("head", (-4.4, 41.0, -5.1), (8.8, 1.2, 1.4), red)  # brow ridge
    m.box("head", (-3.6, 35.0, -5.0), (7.2, 1.6, 0.5), red)  # jaw
    m.box("head", (-3.8, 37.1, -4.75), (7.6, 3.8, 0.2), skin("6e1812", "4a0f0b", "8a2a20"))  # eye sockets
    for x, y, w in ((1.3, 39.4, 1.4), (-2.7, 39.4, 1.4), (1.0, 37.5, 1.9), (-2.9, 37.5, 1.9)):
        m.box("head", (x, y, -4.9), (w, 0.8, 0.2), flat("e6dc3c", 0.03), ao=False)
    m.box("head", (-2.0, 36.1, -4.75), (4.0, 0.8, 0.2), flat("3a0d0a"))  # mouth
    # four arms
    for b, x in (("right_arm", 8.0), ("left_arm", -13.0)):
        m.box(b, (x - 0.3, 27.5, -4.4), (5.6, 7.6, 6.6), red)  # shoulder
        m.box(b, (x + 0.3, 18.5, -3.4), (4.4, 10, 4.8), red)
        m.box(b, (x, 13.6, -3.7), (5.0, 6.0, 5.4), red)  # forearm
        m.box(b, (x - 0.15, 14.6, -3.85), (5.3, 1.4, 5.7), pants)  # wristband
        m.box(b, (x - 0.1, 9.8, -3.9), (5.2, 4.0, 5.8), red)  # fist
    for b, x in (("right_arm_lower", 7.6), ("left_arm_lower", -11.6)):
        m.box(b, (x, 16.0, 0.6), (4.0, 10.6, 4.6), red)
        m.box(b, (x - 0.2, 11.6, 0.4), (4.4, 4.8, 5.0), red)
        m.box(b, (x - 0.35, 12.6, 0.25), (4.7, 1.2, 5.3), pants)
        m.box(b, (x - 0.25, 8.2, 0.3), (4.5, 3.6, 5.2), red)
    return m



def humanoid_bones(m, hip, shoulder_y, shoulder_x, leg_x, neck_y=None):
    m.bone("body", (0, hip, 0))
    m.bone("head", (0, neck_y or shoulder_y + 1, 0), "body")
    m.bone("right_arm", (shoulder_x, shoulder_y, 0), "body")
    m.bone("left_arm", (-shoulder_x, shoulder_y, 0), "body")
    m.bone("right_leg", (leg_x, hip, 0))
    m.bone("left_leg", (-leg_x, hip, 0))


def chest_omnitrix(m, bone, x, y, z, size=3.0):
    c = m.box(bone, (x - size / 2, y - size / 2, z - 0.6), (size, size, 0.6), flat("0d0e11"))
    m.decal(on(c, "north", omnitrix_symbol()))
    return c


# ------------------------------------------------------------------ Wildmutt
def wildmutt():
    m = Model("wildmutt", 256, 256)
    fur = skin("e2791d", "9c4610", "f6a54a", 0.32, 61)
    belly = skin("f0a256", "c06a22", "fbc78a", 0.4, 62)
    bone_w, dark = flat("efe7d2", 0.05), flat("2a1208", 0.05)
    m.bone("body", (0, 14, 0))
    m.bone("head", (0, 17, -8), "body")
    m.bone("tail", (0, 17, 9), "body")
    for b, x, z in (("right_arm", 4, -6), ("left_arm", -4, -6), ("right_leg", 4, 6), ("left_leg", -4, 6)):
        m.bone(b, (x, 14, z))
    m.box("body", (-5.5, 10, -8.5), (11, 9, 17), fur)
    m.box("body", (-6.2, 11, -9.5), (12.4, 9.5, 7), fur)  # heavy shoulders
    m.box("body", (-4.5, 9.4, -6), (9, 1, 12), belly)
    for z in (-6, -2, 2, 6):
        m.box("body", (-0.6, 19 + (1 if z < 0 else 0), z), (1.2, 2.2, 1.6), bone_w)  # back spines
    collar = m.box("body", (-6.4, 13.5, -10.0), (12.8, 2.6, 1.0), flat("17181c"))
    chest_omnitrix(m, "body", 0, 14.8, -10.0, 2.4)
    # eyeless head: big snout, nostril gills, teeth
    m.box("head", (-4.2, 13, -16.5), (8.4, 7, 8), fur)
    m.box("head", (-3.4, 12.8, -20.5), (6.8, 4.2, 4.5), fur)  # snout
    m.box("head", (-3.2, 11.2, -20.2), (6.4, 1.6, 4.2), belly)  # lower jaw
    m.box("head", (-3.0, 12.6, -20.6), (6.0, 0.6, 3.8), bone_w)  # teeth
    for x in (2.6, -3.8):
        m.box("head", (x, 15.2, -15.5), (1.2, 3.2, 4.5), dark)  # gill slits
    m.box("head", (-2.4, 16.8, -20.6), (4.8, 0.8, 0.3), dark)
    m.box("tail", (-1.2, 15, 8.5), (2.4, 2.4, 6), fur)
    for b, x, z in (("right_arm", 1.4, -8), ("left_arm", -6.4, -8), ("right_leg", 1.4, 4), ("left_leg", -6.4, 4)):
        m.box(b, (x, 1.5, z), (5, 11, 4.6), fur)
        m.box(b, (x - 0.3, 0, z - 1.0), (5.6, 2, 5.8), fur)
        for i in range(3):
            m.box(b, (x + 0.3 + i * 1.7, 0, z - 1.6), (1.0, 1.0, 0.8), bone_w)  # claws
    return m


# ------------------------------------------------------------------ Diamondhead
def diamondhead():
    m = Model("diamondhead", 256, 256)
    cry = crystal()
    cry2 = crystal(("14573a", "238a5a", "3fc07f", "8fe9bd"), 0.9, 33)
    suit = flat("15171b", 0.05)
    humanoid_bones(m, 13, 25, 6.2, 2.5, neck_y=27)
    for b, x in (("right_leg", 0.4), ("left_leg", -4.8)):
        m.box(b, (x, 0, -2.3), (4.4, 13, 4.6), suit)
        m.box(b, (x - 0.2, 0, -3.0), (4.8, 2.6, 5.6), cry)
    m.box("body", (-5.2, 13, -2.8), (10.4, 14, 5.6), suit)
    m.box("body", (-5.4, 21.5, -3.1), (10.8, 5.5, 6.2), cry)  # crystal chest
    m.box("body", (-0.6, 13, -2.95), (1.2, 8.5, 0.2), flat("e9ecea", 0.03))
    chest_omnitrix(m, "body", 0, 18.5, -2.8, 2.8)
    for lo, size in (((-3.2, 25.5, 2.5), (2.2, 5, 2.2)), ((1.0, 24.5, 2.6), (2.0, 6.5, 2.0)),
                     ((-1.0, 22.0, 2.8), (2.0, 4.5, 2.2))):
        m.box("body", lo, size, cry2)  # back shards
    for b, x in (("right_arm", 5.2), ("left_arm", -9.4)):
        m.box(b, (x, 13, -2.1), (4.2, 13, 4.2), cry)
        m.box(b, (x - 0.4, 22.5, -2.8), (5.0, 5.0, 5.6), cry2)  # shoulder crystal
        m.box(b, (x + 1.0, 27.5, -1.4), (2.6, 2.6, 2.8), cry2)
    # faceted, tapering head
    m.box("head", (-3.6, 27, -3.4), (7.2, 6, 6.8), cry)
    m.box("head", (-2.8, 33, -2.6), (5.6, 2.4, 5.2), cry)
    m.box("head", (-1.8, 35.4, -1.6), (3.6, 2.2, 3.2), cry2)
    m.box("head", (-0.8, 37.6, -0.6), (1.6, 1.6, 1.2), cry2)
    for x in (0.8, -2.8):
        m.box("head", (x, 29.6, -3.6), (2.0, 1.1, 0.25), flat("0b3622", 0.03))
    return m


# ------------------------------------------------------------------ XLR8
def xlr8():
    m = Model("xlr8", 256, 256)
    blue, dark = skin("2851b8", "142a66", "4f7fe0", 0.4, 71), flat("101116", 0.05)
    visor = metal("1a1d26", 1.5, 72, brushed=False)
    humanoid_bones(m, 12, 23, 5, 2.4, neck_y=24)
    m.bone("tail", (0, 13, 2.5), "body")
    for b, x in (("right_leg", 0.6), ("left_leg", -4.2)):
        m.box(b, (x, 3.2, -1.8), (3.6, 9, 3.6), dark)
        m.box(b, (x + 0.3, 6, -2.2), (3.0, 4.5, 0.6), blue)
        m.box(b, (x - 0.1, 0, -2.4), (3.8, 3.2, 4.8), metal("23262e"))  # roller ball feet
    m.box("body", (-4.2, 12, -2.4), (8.4, 11, 4.8), dark)
    chest = m.box("body", (-3.8, 16, -2.8), (7.6, 7, 0.5), blue)
    chest_omnitrix(m, "body", 0, 19.4, -2.8, 2.6)
    for i, (z, w) in enumerate(((2.4, 4.4), (8.0, 3.4), (13.0, 2.2), (17.0, 1.2))):
        seg = m.box("tail", (-w / 2, 13 - i * 0.6, z), (w, w * 0.8, 5.6), blue if i % 2 == 0 else dark)
    for b, x in (("right_arm", 4.2), ("left_arm", -7.4)):
        m.box(b, (x, 13, -1.6), (3.2, 10.5, 3.2), blue)
        for y in (15.2, 18.2):
            m.box(b, (x - 0.15, y, -1.75), (3.5, 0.8, 3.5), dark)
        for i in range(3):
            m.box(b, (x + 0.2 + i * 1.0, 11.6, -1.4), (0.6, 1.4, 0.6), flat("e8ecf2"))
    # helmet with a visor
    m.box("head", (-3.6, 23.5, -3.6), (7.2, 7.2, 7.2), blue)
    m.box("head", (-3.0, 26.6, -7.2), (6.0, 3.0, 3.8), blue)  # snout of the helmet
    m.box("head", (-3.8, 26.2, -4.1), (7.6, 2.8, 0.7), visor)
    m.box("head", (-2.4, 23.6, -6.8), (4.8, 3.0, 3.4), dark)
    for x in (1.0, -2.6):
        m.box("head", (x, 27.0, -4.25), (1.6, 0.9, 0.2), glow("4dff7a", "d8ffe2"), ao=False)
    m.box("head", (-0.5, 30.7, -3.0), (1.0, 0.8, 6.2), dark)  # helmet ridge
    return m


# ------------------------------------------------------------------ Grey Matter
def greymatter():
    m = Model("greymatter", 128, 128)
    grey, suit = skin("8f969c", "5c6267", "b6bcc1", 0.5, 81), flat("16171b", 0.05)
    humanoid_bones(m, 4, 7.5, 2.2, 1.0, neck_y=8)
    for b, x in (("right_leg", 0.2), ("left_leg", -1.8)):
        m.box(b, (x, 0, -0.9), (1.6, 4, 1.8), grey)
        m.box(b, (x - 0.3, 0, -1.8), (2.2, 0.7, 2.8), grey)
    m.box("body", (-2.0, 4, -1.3), (4.0, 4.2, 2.6), suit)
    m.box("body", (-0.4, 4, -1.4), (0.8, 4.2, 0.2), flat("e5e7ea"))
    chest_omnitrix(m, "body", 0, 6.6, -1.3, 1.3)
    for b, x in (("right_arm", 2.0), ("left_arm", -3.2)):
        m.box(b, (x, 3.6, -0.6), (1.2, 4.2, 1.2), grey)
    # oversized head with big green eyes
    m.box("head", (-3.6, 8, -3.2), (7.2, 5.6, 6.0), grey)
    m.box("head", (-3.2, 13.6, -2.6), (6.4, 0.8, 5.0), grey)
    for x in (0.4, -3.2):
        e = m.box("head", (x, 9.8, -3.6), (2.8, 2.6, 0.5), flat("2fd257", 0.03))
        m.decal(on(e, "north", lambda x_, y_, w, h: (np.hypot(x_ - w * 0.55, y_ - h * 0.45) < 0.55, np.array([0.05, 0.08, 0.06]), 0)))
    m.box("head", (-1.2, 8.8, -3.3), (2.4, 0.3, 0.2), flat("3b3f43"))
    return m


# ------------------------------------------------------------------ Stinkfly
def stinkfly():
    m = Model("stinkfly", 256, 256)
    green = skin("6fae2c", "3d6a17", "a8d85a", 0.45, 91)
    yellow = skin("d8cf3a", "988f1e", "f2ea7a", 0.5, 92)
    dark, wing = flat("1d2112", 0.05), flat("bfe8ff", 0.04)
    m.bone("body", (0, 14, 0))
    m.bone("head", (0, 16, -5), "body")
    m.bone("tail", (0, 13, 6), "body")
    m.bone("right_wing", (2, 18, 0), "body")
    m.bone("left_wing", (-2, 18, 0), "body")
    for b, x, z in (("right_arm", 2, -3), ("left_arm", -2, -3), ("right_leg", 2, 3), ("left_leg", -2, 3)):
        m.bone(b, (x, 11, z), "body")
    m.box("body", (-3.2, 10.5, -5), (6.4, 6.5, 10), green)  # thorax
    m.box("body", (-2.6, 10.0, -4.5), (5.2, 0.6, 9), yellow)
    chest_omnitrix(m, "body", 0, 14.2, -5.0, 2.4)
    for i, (z, w, h) in enumerate(((5, 5.6, 5.4), (9.5, 4.4, 4.4), (13.4, 3.0, 3.2))):
        m.box("tail", (-w / 2, 11 + (5.4 - h) / 2, z), (w, h, 4.5), yellow if i % 2 else green)
    m.box("tail", (-0.4, 12.1, 17.8), (0.8, 0.8, 2.2), dark)  # stinger
    # head with four eyes on stalks
    m.box("head", (-2.6, 12.0, -9.5), (5.2, 5.0, 5.0), green)
    m.box("head", (-2.0, 11.3, -10.4), (4.0, 1.3, 1.6), dark)  # mandibles
    for x, y, h in ((1.3, 17, 4.0), (-2.3, 17, 4.0), (2.0, 16.4, 2.6), (-3.0, 16.4, 2.6)):
        m.box("head", (x, y, -8.4 if h > 3 else -9.0), (1.0, h, 1.0), green)
        m.box("head", (x - 0.5, y + h, -8.9 if h > 3 else -9.5), (2.0, 2.0, 2.0), glow("e9f25a", "ffffff"), ao=False)
    for b, x in (("right_arm", 2.6), ("left_arm", -3.6), ("right_leg", 2.6), ("left_leg", -3.6)):
        z = -3.5 if "arm" in b else 2.5
        m.box(b, (x, 3, z), (1.0, 8.5, 1.0), dark)
        m.box(b, (x - 0.3, 2.0, z - 0.6), (1.6, 1.2, 2.0), green)
    for b, x in (("right_wing", 2.0), ("left_wing", -16.0)):
        m.box(b, (x, 17.0, -2.5), (14, 0.25, 7), wing, alpha=0.55, ao=False)
        m.box(b, (x, 17.3, 1.5), (11, 0.25, 6), wing, alpha=0.55, ao=False)
    return m


# ------------------------------------------------------------------ Ripjaws
def ripjaws():
    m = Model("ripjaws", 256, 256)
    pale = skin("b8c6cf", "6f8290", "dde6ec", 0.4, 101)
    dark, suit = skin("3c4a55", "1f2830", "5a6b78", 0.5, 102), flat("17191d", 0.05)
    teeth = flat("f4f2e8", 0.03)
    humanoid_bones(m, 12, 24, 5.6, 2.2, neck_y=25)
    m.bone("tail", (0, 12, 0))
    m.box("tail", (-4.0, 6, -2.4), (8, 6.5, 4.8), pale)
    m.box("tail", (-3.0, 2, -1.6), (6, 4.5, 3.4), pale)
    m.box("tail", (-1.6, 0, -1.0), (3.2, 2.5, 2.4), dark)
    m.box("tail", (-5.0, 0, -0.3), (10, 2.4, 0.6), dark)  # tail fin
    for b in ("right_leg", "left_leg"):
        del m.bones[b]
    m.box("body", (-4.8, 12, -2.6), (9.6, 12.5, 5.2), suit)
    m.box("body", (-0.6, 12, -2.75), (1.2, 12.5, 0.2), flat("e5e8ea"))
    chest_omnitrix(m, "body", 0, 21.0, -2.6, 2.6)
    m.box("body", (-0.4, 18, 2.6), (0.8, 6, 3.0), dark)  # dorsal fin
    for b, x in (("right_arm", 4.8), ("left_arm", -8.4)):
        m.box(b, (x, 12.5, -1.8), (3.6, 11.5, 3.6), pale)
        m.box(b, (x + 3.6 if x > 0 else x - 0.6, 15, -0.5), (0.6, 5, 2.6), dark)  # arm fins
    # head: huge underbite with teeth
    m.box("head", (-3.6, 27.5, -3.4), (7.2, 5.6, 6.8), pale)
    m.box("head", (-4.2, 24.3, -5.6), (8.4, 3.4, 8.2), dark)  # lower jaw
    for i in range(6):
        m.box("head", (-3.7 + i * 1.3, 27.6, -5.4), (0.8, 1.2, 0.6), teeth)
        m.box("head", (-3.4 + i * 1.3, 27.0, -5.0), (0.7, 0.6, 0.5), teeth)
    m.box("head", (-3.4, 27.1, -3.9), (6.8, 0.6, 0.5), flat("1a0c10"))
    for x in (1.0, -2.6):
        m.box("head", (x, 30.0, -3.6), (1.6, 1.2, 0.25), flat("f2f4f5"))
        m.box("head", (x + 0.5, 30.1, -3.7), (0.7, 1.0, 0.2), flat("0b0d10"))
    for x in (3.6, -4.6):
        m.box("head", (x, 28.5, -1.0), (1.0, 3.6, 3.6), dark)  # head fins
    return m


# ------------------------------------------------------------------ Upgrade
def upgrade():
    m = Model("upgrade", 256, 256)
    body = circuitry()
    smooth = flat("111317", 0.05)
    humanoid_bones(m, 12, 23, 4.8, 2.0, neck_y=24)
    for b, x in (("right_leg", 0.3), ("left_leg", -3.7)):
        m.box(b, (x, 0, -1.7), (3.4, 12, 3.4), body)
    m.box("body", (-4.0, 12, -2.2), (8.0, 11.5, 4.4), body)
    m.box("body", (-3.6, 17.5, -2.6), (7.2, 5.5, 0.5), smooth)
    chest_omnitrix(m, "body", 0, 20.2, -2.6, 2.6)
    for b, x in (("right_arm", 4.0), ("left_arm", -7.0)):
        m.box(b, (x, 12.5, -1.5), (3.0, 11, 3.0), body)
    # smooth dome head, single eye
    m.box("head", (-3.4, 23.5, -3.4), (6.8, 7.0, 6.8), smooth)
    m.box("head", (-2.6, 30.5, -2.6), (5.2, 1.0, 5.2), smooth)
    eye = m.box("head", (-2.0, 25.6, -3.7), (4.0, 3.6, 0.3), flat("0b0c0f"))
    m.decal(on(eye, "north", omnitrix_symbol(r=1.75, ring="3dff4a", face="0b0c0f")))
    m.box("head", (-0.25, 29.5, -3.55), (0.5, 2.6, 0.2), glow("3dff4a", "d8ffe2"), ao=False)
    return m


# ------------------------------------------------------------------ Ghostfreak
def ghostfreak():
    m = Model("ghostfreak", 256, 256)
    pale = skin("dfe2e8", "8d93a1", "ffffff", 0.35, 111)
    black = flat("16141b", 0.05)
    humanoid_bones(m, 12, 24, 5.4, 2.0, neck_y=25)
    for b in ("right_leg", "left_leg"):
        del m.bones[b]
    m.bone("tail", (0, 12, 0))
    torso = m.box("body", (-4.4, 12, -2.4), (8.8, 12, 4.8), pale, alpha=0.88)
    for f in ("north", "south"):
        m.decal(on(torso, f, lambda x, y, w, h: (
            soft(np.abs(((x - w / 2) * 0.8 + y) % 3.2 - 1.6) - 0.35, 0.15), hexrgb("16141b"), 0)))
    chest_omnitrix(m, "body", 0, 19.5, -2.4, 2.6)
    m.box("tail", (-3.6, 6, -2.0), (7.2, 6.2, 4.0), pale, alpha=0.8)
    m.box("tail", (-2.4, 2, -1.4), (4.8, 4.2, 2.8), pale, alpha=0.7)
    m.box("tail", (-1.0, -0.5, -0.6), (2.0, 2.6, 1.6), pale, alpha=0.6)
    for b, x in (("right_arm", 4.4), ("left_arm", -7.8)):
        m.box(b, (x, 12.5, -1.7), (3.4, 11.5, 3.4), pale, alpha=0.88)
        m.box(b, (x - 0.1, 15.0, -1.8), (3.6, 1.0, 3.6), black)
        for i in range(3):
            m.box(b, (x + 0.3 + i * 1.0, 10.8, -1.2), (0.6, 1.8, 0.6), black)  # claws
    m.box("head", (-3.8, 24, -3.6), (7.6, 7.6, 7.2), pale, alpha=0.9)
    eye = m.box("head", (-1.9, 26.6, -3.9), (3.8, 3.4, 0.3), flat("10110f"))
    m.decal(on(eye, "north", lambda x, y, w, h: (soft(np.hypot(x - w / 2, y - h / 2) - 1.2, 0.2), hexrgb("5dff6a"), 1.0)))
    for x, y in ((-3.8, 30.2), (2.6, 29.0), (-1.0, 31.3)):
        m.box("head", (x, y, -3.75), (1.2, 2.0, 0.2), black)  # black stripes
    return m


ALIENS = {"heatblast": heatblast, "wildmutt": wildmutt, "diamondhead": diamondhead, "xlr8": xlr8,
          "greymatter": greymatter, "fourarms": fourarms, "stinkfly": stinkfly, "ripjaws": ripjaws,
          "upgrade": upgrade, "ghostfreak": ghostfreak}
