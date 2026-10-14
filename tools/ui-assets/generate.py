"""Generate the Omnitrix UI art: creative-tab icon, guide book icon, guide book GUI and the wrist model texture.

Run from the repository root: python3 tools/ui-assets/generate.py
Writes into common/src/main/resources/assets/bentenmod/textures:
  item/omnitrix.png            16x16 creative tab / inventory icon
  item/omnitrix_guide.png      16x16 guide book icon
  gui/omnitrix_guide.png       256x256: page 192x192 at (0,0), arrows 23x13 at (0,192) next, (0,205) prev,
                               (23,192) next hovered, (23,205) prev hovered
  entity/omnitrix_wrist.png    32x32 UV atlas for OmnitrixWristLayer (see WRIST_* below)
  entity/omnitrix_wrist_glow.png  same atlas, only the hourglass lit (drawn with RenderType.eyes)
Previews (16x upscaled icons, GUI sheet, wrist cube layout) go to tools/ui-assets/out/ (gitignored).

Wrist atlas layout (u, v of each cube, Minecraft box UV rules):
  band_wide  texOffs(0, 0)   4x3x4
  band_slim  texOffs(0, 8)   3x3x4
  dial       texOffs(0, 16)  1x3x3, +X face (the lit hourglass) at u 4..7, v 19..22
Keep OmnitrixWristLayer.java in step with these numbers.
"""
import math
import random
from pathlib import Path

import numpy as np
from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[2]
TEX = ROOT / "common/src/main/resources/assets/bentenmod/textures"
OUT = Path(__file__).resolve().parent / "out"


def rgb(h):
    h = h.lstrip("#")
    return tuple(int(h[i:i + 2], 16) for i in (0, 2, 4))


def shade(c, k):
    return tuple(max(0, min(255, int(v * k))) for v in c)


def lerp(a, b, t):
    return tuple(int(round(a[i] + (b[i] - a[i]) * t)) for i in range(3))


# Shared palette: the Omnitrix greys and its green.
K = rgb("#0c0e10")      # outline
D = rgb("#2a2e33")      # dark band
D2 = rgb("#3b4047")
G = rgb("#6f777f")      # mid grey
G2 = rgb("#9aa2a9")
L = rgb("#c9ced3")      # light grey
W = rgb("#f2f5f7")      # white highlight
B = rgb("#07090a")      # dial black
GN = rgb("#2fd653")     # omnitrix green
GN_D = rgb("#12873a")
GN_L = rgb("#9dffa8")
LIME = rgb("#b6ff4a")


class Canvas:
    def __init__(self, w, h):
        self.w, self.h = w, h
        self.a = np.zeros((h, w, 4), dtype=np.uint8)

    def set(self, x, y, c, a=255):
        if 0 <= x < self.w and 0 <= y < self.h and c is not None:
            self.a[y, x] = (*c, a)

    def get(self, x, y):
        return tuple(self.a[y, x])

    def rect(self, x0, y0, x1, y1, c, a=255):
        for y in range(y0, y1 + 1):
            for x in range(x0, x1 + 1):
                self.set(x, y, c, a)

    def image(self):
        return Image.fromarray(self.a)

    def save(self, path):
        path.parent.mkdir(parents=True, exist_ok=True)
        self.image().save(path)


def hourglass(c, cx, cy, half, col, mid=None, glass=None):
    """Hourglass of height 2*half+1 centred on (cx, cy): two bars joined by a pinched waist."""
    for i in range(-half, half + 1):
        w = abs(i)  # half-width grows with distance from the waist
        for x in range(cx - w, cx + w + 1):
            c.set(x, cy + i, col)
    if half >= 2:
        # outline the bars so the glyph reads as an hourglass rather than a bow tie
        for x in range(cx - half, cx + half + 1):
            c.set(x, cy - half, col)
            c.set(x, cy + half, col)
    if mid:
        c.set(cx, cy, mid)


def hourglass_mask(size):
    """Boolean mask of an hourglass in a size x size box (size odd)."""
    half = size // 2
    m = np.zeros((size, size), dtype=bool)
    for i in range(-half, half + 1):
        w = abs(i)
        for dx in range(-w, w + 1):
            m[half + i, half + dx] = True
    return m


# ---------------------------------------------------------------- creative tab icon

def build_icon():
    """Draw the tab icon procedurally: band loop behind, extruded casing, bevelled dial, hourglass."""
    c = Canvas(16, 16)
    # 1. band: a thick loop going back and to the right, grey with a lit top edge
    band = [
        # (x0, y0, x1, y1, colour) strips; painted back to front
        (10, 3, 14, 5, D),
        (11, 4, 15, 12, D),
        (9, 12, 14, 14, D),
    ]
    for x0, y0, x1, y1, col in band:
        c.rect(x0, y0, x1, y1, col)
    # lit upper-left of the band loop and a darker underside
    for y in range(3, 6):
        c.set(11, y, G)
    c.rect(12, 5, 13, 5, G2)
    c.rect(13, 6, 13, 11, G)
    c.rect(14, 7, 14, 11, D2)
    for y in (7, 9, 11):  # strap segment lines
        c.rect(12, y, 14, y, G)
    c.rect(9, 13, 13, 13, G)
    # 2. casing extrusion (the visible thickness at bottom-right) in shaded grey
    cx, cy = 6.8, 6.8
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - (cx + 1.6), y - (cy + 1.6))
            if d <= 5.9:
                c.set(x, y, G if (x - y) < 2 else D2)
    # 3. casing top: light grey ring, brighter at the top-left
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - cx, y - cy)
            if d <= 5.9:
                t = (x + y - 10) / 8.0  # -1 top-left .. +1 bottom-right
                col = lerp(W, G2, min(1.0, max(0.0, (t + 0.9) / 1.6)))
                c.set(x, y, col)
    # 4. black dial and bright hourglass
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - cx, y - cy)
            if d <= 4.2:
                c.set(x, y, B)
    # dial inner shadow ring on the top-left so the face reads as recessed
    for y in range(16):
        for x in range(16):
            d = math.hypot(x - cx, y - cy)
            if 3.4 <= d <= 4.2 and (x + y) < 13:
                c.set(x, y, rgb("#15191c"))
    hx, hy = 7, 7
    hourglass(c, hx, hy, 2, GN, mid=GN_L)
    # 5. outline everything
    alpha = c.a[:, :, 3] > 0
    for y in range(16):
        for x in range(16):
            if alpha[y, x]:
                continue
            if any(0 <= x + dx < 16 and 0 <= y + dy < 16 and alpha[y + dy, x + dx]
                   for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1))):
                c.set(x, y, K)
    # 6. specular pixel on the rim
    c.set(3, 3, W)
    c.set(4, 2, W)
    return c


# ---------------------------------------------------------------- guide book item


def build_book_icon():
    c = Canvas(16, 16)
    cover = rgb("#1c5a2b")
    cover_l = rgb("#2f8a45")
    cover_d = rgb("#0f3a1a")
    page = rgb("#e6e1cc")
    page_d = rgb("#b9b39a")
    # pages (peeking out right and bottom)
    c.rect(4, 2, 13, 14, page)
    c.rect(13, 2, 13, 14, page_d)
    c.rect(4, 14, 13, 14, page_d)
    # cover
    c.rect(2, 1, 12, 13, cover)
    c.rect(2, 1, 12, 1, cover_l)
    c.rect(2, 1, 2, 13, cover_l)
    c.rect(12, 2, 12, 13, cover_d)
    c.rect(3, 13, 12, 13, cover_d)
    # spine band
    c.rect(3, 2, 4, 12, cover_d)
    c.rect(3, 2, 3, 12, rgb("#164a24"))
    # hourglass emblem in lime on the cover face (x 5..11)
    ex, ey = 8, 7
    # dark disc behind the glyph for contrast
    for y in range(16):
        for x in range(16):
            if math.hypot(x - ex, y - ey) <= 3.7:
                c.set(x, y, rgb("#0b2a13"))
    hourglass(c, ex, ey, 2, LIME, mid=rgb("#f3ffb0"))
    # silver corner and clasp
    c.set(11, 12, G2)
    c.set(11, 2, G2)
    # outline
    alpha = c.a[:, :, 3] > 0
    for y in range(16):
        for x in range(16):
            if alpha[y, x]:
                continue
            if any(0 <= x + dx < 16 and 0 <= y + dy < 16 and alpha[y + dy, x + dx]
                   for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1))):
                c.set(x, y, K)
    return c


# ---------------------------------------------------------------- guide GUI


def build_guide_gui():
    c = Canvas(256, 256)
    rnd = random.Random(7)
    S = 192
    frame = rgb("#15181b")
    frame_hi = rgb("#2b3036")
    trim = rgb("#35e05c")
    trim_dim = rgb("#17803a")
    trim_glow = rgb("#0f3d1e")
    paper = rgb("#d9dcd5")
    paper_edge = rgb("#b8bdb5")

    # outer slab with chamfered corners
    def inside(x, y, x0, y0, x1, y1, ch):
        if not (x0 <= x <= x1 and y0 <= y <= y1):
            return False
        dx = min(x - x0, x1 - x)
        dy = min(y - y0, y1 - y)
        return dx + dy >= ch

    for y in range(S):
        for x in range(S):
            if inside(x, y, 0, 0, S - 1, S - 1, 6):
                # subtle top-left light edge, bottom-right dark edge
                col = frame
                if x < 2 or y < 2:
                    col = frame_hi
                c.set(x, y, col)
    # a darker outline
    for y in range(S):
        for x in range(S):
            if inside(x, y, 0, 0, S - 1, S - 1, 6) and not inside(x, y, 1, 1, S - 2, S - 2, 6):
                c.set(x, y, K)
    # brushed texture on the frame
    for y in range(2, S - 2):
        for x in range(2, S - 2):
            if tuple(c.a[y, x][:3]) in (frame, frame_hi):
                if rnd.random() < 0.18:
                    c.set(x, y, shade(frame, 1.25))
    # green glow trim: dim halo, then bright 1px line (inset 6 px)
    t0, t1 = 6, S - 7
    for inset, col in ((t0 - 1, trim_glow), (t1 + 1, trim_glow)):
        pass
    def ring(x0, y0, x1, y1, ch, col):
        for y in range(y0, y1 + 1):
            for x in range(x0, x1 + 1):
                if inside(x, y, x0, y0, x1, y1, ch) and not inside(x, y, x0 + 1, y0 + 1, x1 - 1, y1 - 1, max(0, ch - 1)):
                    c.set(x, y, col)
    ring(t0 - 1, t0 - 1, t1 + 1, t1 + 1, 5, trim_glow)
    ring(t0, t0, t1, t1, 4, trim)
    ring(t0 + 1, t0 + 1, t1 - 1, t1 - 1, 3, trim_dim)
    # page area
    px0, py0, px1, py1 = 10, 10, S - 11, S - 11
    for y in range(py0, py1 + 1):
        for x in range(px0, px1 + 1):
            c.set(x, y, paper)
    # inner page edge shading
    for x in range(px0, px1 + 1):
        c.set(x, py0, paper_edge)
        c.set(x, py1, shade(paper_edge, 0.92))
    for y in range(py0, py1 + 1):
        c.set(px0, y, paper_edge)
        c.set(px1, y, shade(paper_edge, 0.92))
    # soft vignette towards the edges (outside text area x 36..172, y 30..170)
    for y in range(py0 + 1, py1):
        for x in range(px0 + 1, px1):
            d = min(x - px0, px1 - x, y - py0, py1 - y)
            if d < 7:
                k = 1.0 - (7 - d) * 0.012
                c.set(x, y, shade(tuple(c.a[y, x][:3]), k))
    # paper grain, very light so text stays readable
    for y in range(py0 + 1, py1):
        for x in range(px0 + 1, px1):
            if rnd.random() < 0.07:
                r, g, b = c.a[y, x][:3]
                c.set(x, y, (int(r) - 5, int(g) - 5, int(b) - 5))
    # hourglass watermark, faint, centred in the text area
    cx, cy = 104, 100
    half = 36
    for i in range(-half, half + 1):
        w = abs(i) * 0.62
        for x in range(int(cx - w), int(cx + w) + 1):
            for dy in (0,):
                y = cy + i
                r, g, b = c.a[y, x][:3]
                c.set(x, y, (int(r) - 9, int(g) - 7, int(b) - 9))
    # thin ring around the watermark
    for ang in range(0, 3600):
        a = ang / 10.0 * math.pi / 180
        for rr in (47, 48):
            x = int(round(cx + math.cos(a) * rr))
            y = int(round(cy + math.sin(a) * rr))
            r, g, b = c.a[y, x][:3]
            c.set(x, y, (int(r) - 7, int(g) - 5, int(b) - 7))
    # little green ticks in the page corners
    for ox, oy, sx, sy in ((px0 + 3, py0 + 3, 1, 1), (px1 - 3, py0 + 3, -1, 1),
                            (px0 + 3, py1 - 3, 1, -1), (px1 - 3, py1 - 3, -1, -1)):
        for i in range(6):
            c.set(ox + sx * i, oy, trim_dim)
            c.set(ox, oy + sy * i, trim_dim)

    # page-turn arrows 23x13
    def arrow(x0, y0, right, hover):
        fill = rgb("#58f27c") if hover else rgb("#1f9c45")
        fill_d = rgb("#2dbf57") if hover else rgb("#136a2e")
        out = rgb("#07140b")
        base = rgb("#22262b")
        # button plate
        for y in range(13):
            for x in range(23):
                if inside(x, y, 0, 0, 22, 12, 2):
                    c.set(x0 + x, y0 + y, base if not hover else rgb("#2f353b"))
        for y in range(13):
            for x in range(23):
                if inside(x, y, 0, 0, 22, 12, 2) and not inside(x, y, 1, 1, 21, 11, 1):
                    c.set(x0 + x, y0 + y, trim if hover else trim_dim)
        # arrow glyph: shaft 9..., head triangle
        mid = 6
        pts = []
        for y in range(13):
            for x in range(23):
                dx = x - 11
                # arrow facing right in local coords: head from x=12..17, shaft x=5..12
                if right:
                    in_shaft = 5 <= x <= 12 and mid - 1 <= y <= mid + 1
                    in_head = 12 <= x <= 17 and abs(y - mid) <= (17 - x)
                else:
                    in_shaft = 10 <= x <= 17 and mid - 1 <= y <= mid + 1
                    in_head = 5 <= x <= 10 and abs(y - mid) <= (x - 5)
                if in_shaft or in_head:
                    pts.append((x, y))
        s = set(pts)
        for x, y in pts:
            c.set(x0 + x, y0 + y, fill if y <= mid else fill_d)
        for x, y in pts:
            for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1)):
                if (x + dx, y + dy) not in s:
                    c.set(x0 + x + dx, y0 + y + dy, out)
        # re-draw glyph fill over outline overlaps
        for x, y in pts:
            c.set(x0 + x, y0 + y, fill if y <= mid else fill_d)

    arrow(0, 192, True, False)
    arrow(0, 205, False, False)
    arrow(23, 192, True, True)
    arrow(23, 205, False, True)
    return c


# ---------------------------------------------------------------- wrist texture

WRIST_SIZE = 32


def paint_box(c, u, v, w, h, d, faces):
    """Paint a box's UV net. faces maps top/bottom/west/north/east/south to a function (lx, ly)->colour|None."""
    rects = {
        "top": (u + d, v, w, d),
        "bottom": (u + d + w, v, w, d),
        "west": (u, v + d, d, h),
        "north": (u + d, v + d, w, h),
        "east": (u + d + w, v + d, d, h),
        "south": (u + d + w + d, v + d, w, h),
    }
    for name, fn in faces.items():
        x0, y0, fw, fh = rects[name]
        for ly in range(fh):
            for lx in range(fw):
                col = fn(lx, ly, fw, fh)
                if col is not None:
                    c.set(x0 + lx, y0 + ly, col)


def band_face(lx, ly, fw, fh):
    # 3 rows: dark rail, light plate, dark rail; a faint green pin at the centre of the plate
    if ly == 0:
        return D2
    if ly == fh - 1:
        return D
    col = L if (lx + ly) % 2 == 0 else shade(L, 0.93)
    if lx == fw // 2:
        col = G2
    return col


def band_cap(lx, ly, fw, fh):
    return D


def rim_face(lx, ly, fw, fh):
    return L if ly < fh - 1 else G


DIAL_HOURGLASS = {(0, 0), (1, 0), (2, 0), (1, 1), (0, 2), (1, 2), (2, 2)}


def dial_face(lx, ly, fw, fh):
    if (lx, ly) == (1, 1):
        return GN_L
    if (lx, ly) in DIAL_HOURGLASS:
        return GN
    return B


def dial_glow(lx, ly, fw, fh):
    if (lx, ly) == (1, 1):
        return GN_L
    if (lx, ly) in DIAL_HOURGLASS:
        return GN
    return None


def build_wrist():
    c = Canvas(WRIST_SIZE, WRIST_SIZE)
    faces = {
        "top": band_cap, "bottom": band_cap,
        "west": band_face, "north": band_face, "east": band_face, "south": band_face,
    }
    paint_box(c, 0, 0, 4, 3, 4, faces)   # band_wide
    paint_box(c, 0, 8, 3, 3, 4, faces)   # band_slim
    paint_box(c, 0, 16, 1, 3, 3, {
        "top": rim_face, "bottom": band_cap, "west": rim_face, "north": rim_face,
        "east": dial_face, "south": rim_face,
    })
    g = Canvas(WRIST_SIZE, WRIST_SIZE)
    paint_box(g, 0, 16, 1, 3, 3, {"east": dial_glow})
    return c, g


# ---------------------------------------------------------------- previews


def up(img, k):
    return img.resize((img.width * k, img.height * k), Image.NEAREST)


def checker(w, h, cell=8):
    im = Image.new("RGB", (w, h))
    px = im.load()
    for y in range(h):
        for x in range(w):
            px[x, y] = (120, 120, 120) if ((x // cell + y // cell) % 2) else (139, 139, 139)
    return im


def on_checker(c, k):
    im = checker(c.w * k, c.h * k, 8 * k // 2)
    im.paste(up(c.image(), k), (0, 0), up(c.image(), k))
    return im


def wrist_sketch(c):
    """Orthographic sketch of the cube layout: side view from outside (+X) and top cross-section, wide and slim."""
    k = 22
    arm_col = rgb("#c58f6b")
    img = Image.new("RGB", (880, 520), (30, 33, 36))
    d = ImageDraw.Draw(img)

    def px(face_x, face_y):
        r, g, b, a = c.a[face_y, face_x]
        return (int(r), int(g), int(b))

    def draw_side(ox, oy, slim):
        # arm viewed from +X: z horizontal (front -Z at left), y down from the shoulder pivot
        zl, zr = -2, 2
        for yy in range(-2, 10):
            for zz in range(zl, zr):
                d.rectangle([ox + (zz + 2) * k, oy + (yy + 2) * k, ox + (zz + 3) * k - 1, oy + (yy + 3) * k - 1], fill=arm_col)
        # band: y 6.8..9.8, z -2.4..2.4 ; use +X face of band texture (4 wide? side is depth 4 across z)
        u0 = 0 if not slim else 0
        v0 = 0 if not slim else 8
        bw = 3 if slim else 4
        # east face of band: u = d + w .. , v = v0 + d
        ex = 4 + bw
        for ly in range(3):
            for lx in range(4):
                col = px(ex + lx, v0 + 4 + ly)
                x0 = ox + (-2.4 + lx * 1.2) * k + 2 * k
                y0 = oy + (6.8 + ly) * k + 2 * k
                d.rectangle([x0, y0, x0 + 1.2 * k - 1, y0 + k - 1], fill=col)
        # dial: z -1.5..1.5, y 6.8..9.8, face u 4..7, v 19..22
        for ly in range(3):
            for lx in range(3):
                col = px(4 + lx, 19 + ly)
                x0 = ox + (-1.5 + lx) * k + 2 * k
                y0 = oy + (6.8 + ly) * k + 2 * k
                d.rectangle([x0, y0, x0 + k - 1, y0 + k - 1], fill=col)
                d.rectangle([x0, y0, x0 + k - 1, y0 + k - 1], outline=(10, 12, 14))
        d.text((ox, oy - 22), "side from outside (+X)  " + ("slim" if slim else "wide"), fill=(220, 220, 220))
        # shoulder / hand ticks
        d.text((ox + 4 * k + 14, oy + 2 * k), "y=0 pivot", fill=(150, 150, 150))
        d.text((ox + 4 * k + 14, oy + 12 * k - 12), "y=10 wrist end", fill=(150, 150, 150))

    def draw_top(ox, oy, slim):
        # cross-section from above: x horizontal (outer +X right), z vertical (front -Z up)
        xr = 2 if slim else 3
        s = 1.6 * k
        def X(x): return ox + (x + 1) * s
        def Z(z): return oy + (z + 2.4) * s
        d.rectangle([X(-1), Z(-2), X(xr), Z(2)], fill=arm_col)
        d.rectangle([X(-1.4), Z(-2.4), X(xr + 0.4), Z(2.4)], outline=(200, 205, 210), width=3)
        d.rectangle([X(xr + 0.2), Z(-1.5), X(xr + 1.2), Z(1.5)], fill=(8, 10, 12), outline=(60, 255, 100), width=2)
        d.text((ox, oy - 22), "top section  " + ("slim" if slim else "wide") + "  (dial on outer +X)", fill=(220, 220, 220))

    draw_side(20, 40, False)
    draw_side(260, 40, True)
    draw_top(520, 60, False)
    draw_top(520, 290, True)
    return img


def main():
    OUT.mkdir(parents=True, exist_ok=True)
    icon = build_icon()
    icon.save(TEX / "item/omnitrix.png")
    book = build_book_icon()
    book.save(TEX / "item/omnitrix_guide.png")
    gui = build_guide_gui()
    gui.save(TEX / "gui/omnitrix_guide.png")
    wrist, glow = build_wrist()
    wrist.save(TEX / "entity/omnitrix_wrist.png")
    glow.save(TEX / "entity/omnitrix_wrist_glow.png")

    on_checker(icon, 16).save(OUT / "omnitrix_x16.png")
    on_checker(book, 16).save(OUT / "omnitrix_guide_x16.png")
    # in-slot look: the icons at 1x/2x/3x on a stone grey slot
    slot = Image.new("RGB", (16 * 3 + 40, 16 * 3 + 16), (139, 139, 139))
    for i, c in enumerate((icon, book)):
        slot.paste(up(c.image(), 3), (8 + i * 56, 8), up(c.image(), 3))
    slot.save(OUT / "slots.png")
    on_checker(gui, 3).crop((0, 0, 192 * 3, 192 * 3)).save(OUT / "guide_gui_page_x3.png")
    gui_sheet = checker(256 * 2, 256 * 2, 16)
    gui_sheet.paste(up(gui.image(), 2), (0, 0), up(gui.image(), 2))
    gui_sheet.save(OUT / "guide_gui_sheet_x2.png")
    # page with sample text lines to judge readability
    page = Image.new("RGB", (192, 192), (0, 0, 0))
    page.paste(gui.image(), (0, 0), gui.image())
    pd = ImageDraw.Draw(page)
    for i in range(14):
        pd.text((36, 30 + i * 10), "The quick brown fox jumps over", fill=(0, 0, 0))
    up(page, 4).save(OUT / "guide_gui_text_x4.png")
    on_checker(wrist, 16).save(OUT / "wrist_atlas_x16.png")
    on_checker(glow, 16).save(OUT / "wrist_glow_x16.png")
    wrist_sketch(wrist).save(OUT / "wrist_layout.png")
    print("wrote omnitrix.png, omnitrix_guide.png, gui/omnitrix_guide.png, entity/omnitrix_wrist(.png|_glow.png); previews in", OUT)


if __name__ == "__main__":
    main()
