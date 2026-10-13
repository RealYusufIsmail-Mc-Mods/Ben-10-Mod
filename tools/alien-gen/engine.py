"""Box-model authoring, texture baking (procedural materials + AO + glow) and GeckoLib export.

Coordinates are Blockbench's: units are model pixels, +Y up, the model faces north (-Z).
"""
import json
import math

import numpy as np
from PIL import Image, ImageFilter

D = 4  # texels per model unit

# face: (normal, start corner selector, u axis, v axis, u length, v length) — texture seen from outside
FACES = {
    "north": ((0, 0, -1), lambda lo, hi: (hi[0], hi[1], lo[2]), (-1, 0, 0), (0, -1, 0), 0, 1),
    "south": ((0, 0, 1), lambda lo, hi: (lo[0], hi[1], hi[2]), (1, 0, 0), (0, -1, 0), 0, 1),
    "east": ((1, 0, 0), lambda lo, hi: (hi[0], hi[1], hi[2]), (0, 0, -1), (0, -1, 0), 2, 1),
    "west": ((-1, 0, 0), lambda lo, hi: (lo[0], hi[1], lo[2]), (0, 0, 1), (0, -1, 0), 2, 1),
    "up": ((0, 1, 0), lambda lo, hi: (lo[0], hi[1], lo[2]), (1, 0, 0), (0, 0, 1), 0, 2),
    "down": ((0, -1, 0), lambda lo, hi: (lo[0], lo[1], hi[2]), (1, 0, 0), (0, 0, -1), 0, 2),
}


# ------------------------------------------------------------------ noise
def _hash(ix, iy, iz, seed):
    h = (ix * 374761393 + iy * 668265263 + iz * 1440662683 + seed * 2654435761) & 0xFFFFFFFF
    h = ((h ^ (h >> 13)) * 1274126177) & 0xFFFFFFFF
    return ((h ^ (h >> 16)) & 0xFFFF) / 65535.0


def vnoise(p, seed=0):
    p = np.asarray(p, float)
    i = np.floor(p).astype(np.int64)
    f = p - i
    f = f * f * (3 - 2 * f)
    out = 0
    for dx in (0, 1):
        for dy in (0, 1):
            for dz in (0, 1):
                w = (f[:, 0] if dx else 1 - f[:, 0]) * (f[:, 1] if dy else 1 - f[:, 1]) * (f[:, 2] if dz else 1 - f[:, 2])
                out = out + w * _hash(i[:, 0] + dx, i[:, 1] + dy, i[:, 2] + dz, seed)
    return out


def fbm(p, scale=1.0, octaves=3, seed=0):
    total, amp, norm = 0, 1.0, 0
    for o in range(octaves):
        total = total + amp * vnoise(p * scale * (2 ** o), seed + o * 17)
        norm += amp
        amp *= 0.5
    return total / norm


def hexrgb(s):
    s = s.lstrip("#")
    return np.array([int(s[i:i + 2], 16) / 255 for i in (0, 2, 4)])


def mix(a, b, t):
    t = np.clip(t, 0, 1)[:, None] if np.ndim(t) else t
    return a * (1 - t) + b * t


# ------------------------------------------------------------------ materials
class Surf:
    """What a material sees: world positions, the face normal and the cube's local coordinates."""

    def __init__(self, P, n, cube, face):
        self.P, self.n, self.cube, self.face = P, np.array(n, float), cube, face
        self.L = (P - cube.lo) / np.maximum(cube.size, 1e-6)  # 0..1 inside the cube


def flat(color, noise=0.06, scale=0.6, seed=1):
    c = hexrgb(color)

    def m(s):
        k = 1 + noise * (fbm(s.P, scale, 3, seed) - 0.5) * 2
        return c * k[:, None], np.zeros(len(s.P))
    return m


def skin(color, dark, light, scale=0.35, seed=3):
    c, d, l = hexrgb(color), hexrgb(dark), hexrgb(light)

    def m(s):
        n = fbm(s.P, scale, 4, seed)
        rgb = mix(c, d, (0.5 - n) * 1.4)
        rgb = mix(rgb, l, (n - 0.62) * 2.2)
        fine = 1 + 0.05 * (vnoise(s.P * 2.1, seed + 9) - 0.5)
        return rgb * fine[:, None], np.zeros(len(s.P))
    return m


def magma(rock="5c1d0f", rock2="2e0f08", lava="ff6a12", hot="ffd640", scale=0.22, width=0.11, seed=11):
    r1, r2, lv, ht = map(hexrgb, (rock, rock2, lava, hot))

    def m(s):
        n = fbm(s.P, scale, 3, seed)
        ridge = 1 - np.abs(2 * n - 1)  # 1 on the crack lines
        crack = np.clip((ridge - (1 - width)) / width, 0, 1)
        plate = fbm(s.P, 0.5, 2, seed + 3)
        rgb = mix(r1, r2, plate * 1.2 - 0.2)
        rgb = mix(rgb, r1 * 1.5, np.clip((ridge - 0.55) * 1.5, 0, 1) * 0.5)  # warm rim near cracks
        glow = mix(lv, ht, crack ** 2)
        rgb = mix(rgb, glow, crack)
        return rgb, crack
    return m


def flame(base_y, top_y, seed=21):
    cols = [hexrgb(c) for c in ("fff3b0", "ffd23a", "ff8a1a", "e8410e")]

    def m(s):
        t = np.clip((s.P[:, 1] - base_y) / (top_y - base_y), 0, 1)
        t = np.clip(t + 0.35 * (fbm(s.P, 0.45, 3, seed) - 0.5), 0, 1)
        k = t * 3
        idx = np.minimum(k.astype(int), 2)
        f = k - idx
        a = np.stack(cols)[idx]
        b = np.stack(cols)[idx + 1]
        return a * (1 - f[:, None]) + b * f[:, None], np.ones(len(s.P))
    return m


def crystal(colors=("1b6b46", "2fa36b", "56d996", "b6f7d6"), scale=0.7, seed=31):
    cs = np.stack([hexrgb(c) for c in colors])

    def m(s):
        # planar facets: quantise a rotated coordinate field
        q = s.P @ np.array([[0.8, 0.5, -0.3], [-0.4, 0.7, 0.6], [0.3, -0.2, 0.9]])
        cell = np.floor(q * scale * 0.5)
        h = _hash(cell[:, 0].astype(np.int64), cell[:, 1].astype(np.int64), cell[:, 2].astype(np.int64), seed)
        idx = np.clip((h * 3.2).astype(int), 0, 3)
        rgb = cs[idx]
        sheen = np.clip((q[:, 0] * scale * 0.5) % 1, 0, 1)
        return rgb * (0.88 + 0.2 * sheen)[:, None], np.zeros(len(s.P))
    return m


def metal(color, scale=0.8, seed=41, brushed=True):
    c = hexrgb(color)

    def m(s):
        streak = vnoise(s.P * np.array([0.3, 4.0, 0.3]) * scale, seed) if brushed else 0.5
        k = 0.92 + 0.16 * streak + 0.06 * (fbm(s.P, 0.4, 2, seed + 1) - 0.5)
        return c * k[:, None], np.zeros(len(s.P))
    return m


def glow(color, core=None):
    c = hexrgb(color)
    cc = hexrgb(core) if core else c

    def m(s):
        e = np.minimum(np.abs(s.L - 0.5).max(1) * 2, 1)  # 0 centre -> 1 edge on the face
        return mix(cc, c, e ** 1.5), np.ones(len(s.P))
    return m


def circuitry(base="121418", line="3dff4a", scale=0.5, seed=51):
    b, l = hexrgb(base), hexrgb(line)

    def m(s):
        q = s.P * scale
        cell = np.floor(q).astype(np.int64)
        f = q - cell
        pick = _hash(cell[:, 0], cell[:, 1], cell[:, 2], seed)
        axis = (pick * 3).astype(int)
        # each cell draws one trace along a random axis, plus occasional nodes
        g = np.take_along_axis(np.abs(f - 0.5), axis[:, None], 1)[:, 0]
        trace = (g < 0.09) & (_hash(cell[:, 0], cell[:, 1], cell[:, 2], seed + 5) > 0.35)
        node = (np.abs(f - 0.5).max(1) < 0.16) & (pick > 0.8)
        e = (trace | node).astype(float)
        sheen = 0.9 + 0.2 * fbm(s.P, 0.3, 2, seed + 2)
        rgb = b * sheen[:, None] * (1 - e[:, None]) + l * e[:, None]
        return rgb, e
    return m


# ------------------------------------------------------------------ model
class Cube:
    def __init__(self, bone, lo, size, mat, inflate=0.0, ao=True, glowmask=True, alpha=1.0):
        self.bone, self.lo, self.size = bone, np.array(lo, float), np.array(size, float)
        self.hi = self.lo + self.size
        self.mat, self.inflate, self.ao, self.glowmask, self.alpha = mat, inflate, ao, glowmask, alpha
        self.uv = {}


class Bone:
    def __init__(self, name, pivot, parent):
        self.name, self.pivot, self.parent, self.cubes = name, np.array(pivot, float), parent, []


class Model:
    def __init__(self, name, tex_w=256, tex_h=256, ao_strength=0.65):
        self.name, self.tex_w, self.tex_h = name, tex_w * D // 2, tex_h * D // 2
        self.bones, self.cubes, self.decals = {}, [], []
        self.ao_strength = ao_strength
        self.bone("root", (0, 0, 0))

    def bone(self, name, pivot, parent="root"):
        self.bones[name] = Bone(name, pivot, parent if name != "root" else None)
        return name

    def box(self, bone, lo, size, mat, **kw):
        c = Cube(bone, lo, size, mat, **kw)
        self.bones[bone].cubes.append(c)
        self.cubes.append(c)
        return c

    def sym(self, bone_r, bone_l, lo, size, mat, **kw):
        """A cube on the model's right (+X when facing north) and its mirror on the left."""
        a = self.box(bone_r, lo, size, mat, **kw)
        b = self.box(bone_l, (-lo[0] - size[0], lo[1], lo[2]), size, mat, **kw)
        return a, b

    def decal(self, fn):
        """fn(surf, rgb, emit) -> (rgb, emit); applied to every texel after the material."""
        self.decals.append(fn)

    # -------------------------------------------------------------- baking
    def _faces(self, c):
        for name, (n, start, u, v, ui, vi) in FACES.items():
            w, h = c.size[ui], c.size[vi]
            if w <= 0 or h <= 0:
                continue
            # a flat plane only needs its two big faces
            yield name, np.array(n, float), np.array(start(c.lo, c.hi), float), np.array(u, float), np.array(v, float), w, h

    def pack(self):
        rects = []
        for ci, c in enumerate(self.cubes):
            for name, n, s, u, v, w, h in self._faces(c):
                rects.append((max(1, round(w * D)), max(1, round(h * D)), ci, name))
        rects.sort(key=lambda r: -r[1])
        x = y = row_h = 0
        for pw, ph, ci, name in rects:
            if x + pw > self.tex_w:
                x, y, row_h = 0, y + row_h, 0
            self.cubes[ci].uv[name] = (x, y, pw, ph)
            x += pw
            row_h = max(row_h, ph)
        need = y + row_h
        while need > self.tex_h:
            self.tex_h *= 2

    def _ao(self, P, n):
        rng = np.random.default_rng(5)
        k = 20
        d = rng.normal(size=(k, 3))
        d /= np.linalg.norm(d, axis=1, keepdims=True)
        d[d @ n < 0] *= -1
        d = (d + n * 0.4)
        d /= np.linalg.norm(d, axis=1, keepdims=True)
        O = P + n * 0.02
        L = 3.5
        occ = np.zeros(len(P))
        for dk in d:
            inv = 1 / np.where(np.abs(dk) < 1e-9, 1e-9, dk)
            tmin_all = np.full(len(P), np.inf)
            for c in self.cubes:
                if not c.ao:
                    continue
                t1 = (c.lo - O) * inv
                t2 = (c.hi - O) * inv
                tn = np.minimum(t1, t2).max(1)
                tf = np.maximum(t1, t2).min(1)
                hit = (tf >= np.maximum(tn, 0)) & (tn < L) & (tn > 0.01)
                tmin_all = np.where(hit, np.minimum(tmin_all, tn), tmin_all)
            occ += np.where(np.isfinite(tmin_all), 1 - tmin_all / L, 0)
        return 1 - self.ao_strength * np.clip(occ / k * 1.6, 0, 1)

    def bake(self):
        self.pack()
        tex = np.zeros((self.tex_h, self.tex_w, 4))
        gm = np.zeros((self.tex_h, self.tex_w, 4))
        ys = [c.lo[1] for c in self.cubes] + [c.hi[1] for c in self.cubes]
        y0, y1 = min(ys), max(ys)
        for c in self.cubes:
            for name, n, s, u, v, w, h in self._faces(c):
                x, y, pw, ph = c.uv[name]
                jj, ii = np.mgrid[0:ph, 0:pw]
                a = (ii.ravel() + 0.5) / pw
                b = (jj.ravel() + 0.5) / ph
                P = s + np.outer(a * w, u) + np.outer(b * h, v)
                surf = Surf(P, n, c, name)
                surf.x, surf.y = a * w, b * h  # face coords in units, from the top-left as seen from outside
                surf.w, surf.h = w, h
                rgb, emit = c.mat(surf)
                rgb, emit = np.array(rgb, float), np.array(emit, float)
                for fn in self.decals:
                    rgb, emit = fn(surf, rgb, emit)
                ao = self._ao(P, n) if c.ao else np.ones(len(P))
                # baked "studio" shading: lighter higher up, bevelled edges
                height = 0.9 + 0.18 * (P[:, 1] - y0) / max(1, y1 - y0)
                ea, eb = np.minimum(a, 1 - a) * w, np.minimum(b, 1 - b) * h
                edge = np.minimum(ea, eb)
                bevel = 1 + 0.12 * np.clip(1 - edge / 0.6, 0, 1) * (1 if n[1] >= 0 else -0.6)
                if name in ("north", "south", "east", "west"):
                    bevel = bevel - 0.1 * np.clip(1 - (h - b * h) / 0.6, 0, 1)  # darker bottom lip
                shade = (ao * height * bevel)[:, None]
                lit = rgb * shade
                e = emit[:, None]
                col = lit * (1 - e) + rgb * (0.75 + 0.25 * ao[:, None]) * e
                tex[y:y + ph, x:x + pw, :3] = np.clip(col, 0, 1).reshape(ph, pw, 3)
                tex[y:y + ph, x:x + pw, 3] = c.alpha
                if c.glowmask:
                    gm[y:y + ph, x:x + pw, :3] = np.clip(rgb, 0, 1).reshape(ph, pw, 3)
                    gm[y:y + ph, x:x + pw, 3] = np.clip(emit, 0, 1).reshape(ph, pw) ** 0.8
        self.tex = tex
        self.gm = gm
        return tex, gm

    def save_textures(self, path, glow_path=None):
        Image.fromarray((self.tex * 255).astype(np.uint8), "RGBA").save(path)
        if glow_path is not None and self.gm[..., 3].max() > 0:
            Image.fromarray((self.gm * 255).astype(np.uint8), "RGBA").save(glow_path)
            return True
        return False

    # -------------------------------------------------------------- export
    def geo(self):
        bones = []
        for b in self.bones.values():
            entry = {"name": b.name, "pivot": [-b.pivot[0], b.pivot[1], b.pivot[2]]}
            if b.parent:
                entry["parent"] = b.parent
            cubes = []
            for c in b.cubes:
                cu = {"origin": [round(-(c.lo[0] + c.size[0]), 4), round(c.lo[1], 4), round(c.lo[2], 4)],
                      "size": [round(v, 4) for v in c.size], "uv": {}}
                if c.inflate:
                    cu["inflate"] = c.inflate
                for face, (x, y, pw, ph) in c.uv.items():
                    if face in ("up", "down"):
                        cu["uv"][face] = {"uv": [x + pw, y + ph], "uv_size": [-pw, -ph]}
                    else:
                        cu["uv"][face] = {"uv": [x, y], "uv_size": [pw, ph]}
                cubes.append(cu)
            if cubes:
                entry["cubes"] = cubes
            bones.append(entry)
        top = max(c.hi[1] for c in self.cubes) / 16
        return {
            "format_version": "1.12.0",
            "minecraft:geometry": [{
                "description": {
                    "identifier": f"geometry.{self.name}",
                    "texture_width": self.tex_w, "texture_height": self.tex_h,
                    "visible_bounds_width": 4, "visible_bounds_height": math.ceil(top + 1),
                    "visible_bounds_offset": [0, top / 2, 0],
                },
                "bones": bones,
            }],
        }

    def save_geo(self, path):
        path.write_text(json.dumps(self.geo(), indent="\t"))


# ------------------------------------------------------------------ preview renderer
def rot_matrix(rx, ry, rz):
    rx, ry, rz = map(math.radians, (rx, ry, rz))
    X = np.array([[1, 0, 0], [0, math.cos(rx), -math.sin(rx)], [0, math.sin(rx), math.cos(rx)]])
    Y = np.array([[math.cos(ry), 0, math.sin(ry)], [0, 1, 0], [-math.sin(ry), 0, math.cos(ry)]])
    Z = np.array([[math.cos(rz), -math.sin(rz), 0], [math.sin(rz), math.cos(rz), 0], [0, 0, 1]])
    return Z @ Y @ X


def bone_transforms(model, pose):
    """World (R, t) for each bone. pose: {bone: (rx, ry, rz)} plus optional '_root' (offset, scale, ry)."""
    out = {}

    def get(name):
        if name in out:
            return out[name]
        b = model.bones[name]
        R = rot_matrix(*pose.get(name, (0, 0, 0)))
        t = b.pivot - R @ b.pivot
        if b.parent:
            PR, Pt = get(b.parent)
            R, t = PR @ R, PR @ t + Pt
        else:
            off, scale, ry = pose.get("_root", ((0, 0, 0), 1.0, 0))
            S = rot_matrix(0, ry, 0) * scale
            R, t = S @ R, S @ t + np.array(off, float)
        out[name] = (R, t)
        return out[name]

    for n in model.bones:
        get(n)
    return out


class Scene:
    def __init__(self, w, h, ss=3, yaw=-28, pitch=12, zoom=6.0, center=(0, 18, 0)):
        self.W, self.H, self.ss = w * ss, h * ss, ss
        self.view = rot_matrix(pitch, 0, 0) @ rot_matrix(0, yaw, 0)
        self.zoom, self.center = zoom * ss, np.array(center, float)
        self.color = np.zeros((self.H, self.W, 3))
        self.alpha = np.zeros((self.H, self.W))
        self.emit = np.zeros((self.H, self.W, 3))
        self.z = np.full((self.H, self.W), -np.inf)
        self.light = np.array([-0.45, 0.75, -0.55])
        self.light /= np.linalg.norm(self.light)

    def project(self, p):
        q = (self.view @ (p - self.center).T).T
        # camera looks along +Z (from the north side), so screen x is -X, screen y is -Y
        return np.stack([self.W / 2 - q[:, 0] * self.zoom, self.H / 2 - q[:, 1] * self.zoom], 1), -q[:, 2]

    def draw(self, model, pose=None, tint=None, opacity=1.0, emit_boost=1.0):
        pose = pose or {}
        xf = bone_transforms(model, pose)
        tex, gm = model.tex, model.gm
        for c in sorted(model.cubes, key=lambda c: c.alpha < 1):  # translucent parts last
            R, t = xf[c.bone]
            infl = c.inflate
            lo, hi = c.lo - infl, c.hi + infl
            for name, (n, start, u, v, ui, vi) in FACES.items():
                if name not in c.uv:
                    continue
                nw = R @ np.array(n, float)
                nv = self.view @ nw
                if nv[2] > 0.001 and c.size.min() > 0:  # facing away from the camera
                    continue
                s0 = R @ np.array(start(lo, hi), float) + t
                size = hi - lo
                U = R @ (np.array(u, float) * size[ui])
                V = R @ (np.array(v, float) * size[vi])
                (p0, pu, pv), (z0, zu, zv) = self.project(np.stack([s0, s0 + U, s0 + V]))
                eu, ev = pu - p0, pv - p0
                det = eu[0] * ev[1] - eu[1] * ev[0]
                if abs(det) < 1e-6:
                    continue
                corners = np.stack([p0, pu, pv, pu + ev])
                x0, y0 = np.floor(corners.min(0)).astype(int)
                x1, y1 = np.ceil(corners.max(0)).astype(int)
                x0, y0, x1, y1 = max(x0, 0), max(y0, 0), min(x1, self.W), min(y1, self.H)
                if x0 >= x1 or y0 >= y1:
                    continue
                yy, xx = np.mgrid[y0:y1, x0:x1] + 0.5
                dx, dy = xx - p0[0], yy - p0[1]
                a = (dx * ev[1] - dy * ev[0]) / det
                b = (eu[0] * dy - eu[1] * dx) / det
                inside = (a >= 0) & (a <= 1) & (b >= 0) & (b <= 1)
                depth = z0 + a * (zu - z0) + b * (zv - z0)
                zb = self.z[y0:y1, x0:x1]
                m = inside & (depth > zb)
                if not m.any():
                    continue
                tx, ty, pw, ph = c.uv[name]
                ti = np.clip((a * pw).astype(int), 0, pw - 1) + tx
                tj = np.clip((b * ph).astype(int), 0, ph - 1) + ty
                texel = tex[tj, ti]
                g = gm[tj, ti]
                lam = max(0.0, float(nw @ self.light))
                sky = 0.5 + 0.5 * nw[1]
                rim = (1 - abs(nv[2])) ** 3 * 0.25
                lightk = 0.38 + 0.62 * lam + 0.12 * sky + rim
                col = texel[..., :3] * lightk
                e = g[..., 3:4]
                col = col * (1 - e) + texel[..., :3] * e
                if tint is not None:
                    col = col * (1 - tint[1]) + np.array(tint[0]) * tint[1]
                if c.alpha < 1:
                    k = (texel[..., 3] * m)[..., None]
                    reg = self.color[y0:y1, x0:x1]
                    reg[:] = reg * (1 - k) + col * k
                    self.alpha[y0:y1, x0:x1] = np.maximum(self.alpha[y0:y1, x0:x1], k[..., 0])
                    self.emit[y0:y1, x0:x1] += (g[..., :3] * e * emit_boost) * k
                    continue
                zb[m] = depth[m]
                self.color[y0:y1, x0:x1][m] = col[m]
                self.alpha[y0:y1, x0:x1][m] = opacity
                self.emit[y0:y1, x0:x1][m] = (g[..., :3] * e * emit_boost)[m]

    def image(self, bg=("101318", "26303a"), bloom=1.0, shadow=True, extra_glow=None):
        H, W = self.H, self.W
        top, bot = hexrgb(bg[0]), hexrgb(bg[1])
        t = np.linspace(0, 1, H)[:, None, None]
        canvas = top * (1 - t) + bot * t
        canvas = np.broadcast_to(canvas, (H, W, 3)).copy()
        if shadow:
            ys, xs = np.mgrid[0:H, 0:W]
            pts, _ = self.project(np.array([[0, 0, 0.0]]))
            gp = pts[0]
            sh = np.exp(-(((xs - gp[0]) / (W * 0.22)) ** 2 + ((ys - gp[1]) / (H * 0.035)) ** 2))
            canvas *= (1 - 0.55 * sh)[..., None]
        a = self.alpha[..., None]
        out = canvas * (1 - a) + self.color * a
        glow_src = self.emit.copy()
        if extra_glow is not None:
            glow_src += extra_glow
        if bloom:
            g = Image.fromarray((np.clip(glow_src, 0, 1) * 255).astype(np.uint8))
            b1 = np.asarray(g.filter(ImageFilter.GaussianBlur(4 * self.ss)), float) / 255
            b2 = np.asarray(g.filter(ImageFilter.GaussianBlur(14 * self.ss)), float) / 255
            out = out + bloom * (0.9 * b1 + 0.7 * b2)
        out = 1 - np.exp(-out * 1.15)  # soft tone map
        img = Image.fromarray((np.clip(out / (1 - math.exp(-1.15)), 0, 1) * 255).astype(np.uint8))
        return img.resize((W // self.ss, H // self.ss), Image.LANCZOS)
