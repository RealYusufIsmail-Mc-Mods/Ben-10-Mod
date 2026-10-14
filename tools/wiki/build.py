#!/usr/bin/env python3
"""Build the static Ben 10 Mod player wiki into build/wiki."""

from __future__ import annotations

import html
import json
import re
import shutil
from collections import defaultdict
from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parents[2]
COMMON = ROOT / "common" / "src"
MAIN_RESOURCES = COMMON / "main" / "resources"
GENERATED_RESOURCES = COMMON / "generated" / "resources"
LANG_FILE = GENERATED_RESOURCES / "assets" / "bentenmod" / "lang" / "en_us.json"
ALIEN_DIR = MAIN_RESOURCES / "data" / "bentenmod" / "aliens"
RECIPE_DIR = GENERATED_RESOURCES / "data" / "bentenmod" / "recipes"
ARMOR_DIR = MAIN_RESOURCES / "data" / "bentenmod" / "armor_sets"
LOOT_DIR = GENERATED_RESOURCES / "data" / "bentenmod" / "loot_tables"
PLACED_DIR = GENERATED_RESOURCES / "data" / "bentenmod" / "worldgen" / "placed_feature"
CONFIGURED_DIR = GENERATED_RESOURCES / "data" / "bentenmod" / "worldgen" / "configured_feature"
WIKI_SOURCE = ROOT / "wiki"
OUTPUT = ROOT / "build" / "wiki"

CURSEFORGE_URL = "https://www.curseforge.com/minecraft/mc-mods/ben-ten-mod"
GITHUB_URL = "https://github.com/RealYusufIsmail/Ben-10-Mod"
RELEASES_URL = f"{GITHUB_URL}/releases"

NAV = [
    ("index.html", "Home", "home"),
    ("getting-started.html", "Getting started", "getting-started"),
    ("omnitrix.html", "Omnitrix", "omnitrix"),
    ("aliens.html", "Aliens", "aliens"),
    ("items.html", "Items & blocks", "items"),
    ("recipes.html", "Recipes", "recipes"),
    ("ores-and-armour.html", "Ores & armour", "ores"),
    ("bosses.html", "Bosses", "bosses"),
    ("null-void.html", "Null Void", "null-void"),
    ("roadmap.html", "Roadmap", "roadmap"),
]

ENTITY_LABELS = {
    "crab": "Kraab",
    "vilgax": "Vilgax",
    "vilgax_drone": "Vilgax Drone",
    "zs_skayr": "Zs'Skayr",
    "null_void_prisoner": "Null Void Prisoner",
}

BOSS_SOURCE = {
    "crab": "entity/CrabEntity.java",
    "vilgax": "entity/VilgaxEntity.java",
    "vilgax_drone": "entity/VilgaxDroneEntity.java",
    "zs_skayr": "entity/ZsSkayrEntity.java",
}

BOSS_ATTACKS = {
    "crab": "Kraab closes in for melee attacks and fires bolts at range.",
    "vilgax": "Vilgax uses melee attacks and a ground slam. At half health he enters phase two, gains speed and resistance, and calls in drones.",
    "vilgax_drone": "Drones fly after the player, dash toward nearby targets, and attack at close range.",
    "zs_skayr": "Zs'Skayr fires shadow bolts, summons Null Void Prisoners, and turns invisible briefly as he blinks.",
}


def read_json(path: Path):
    return json.loads(path.read_text(encoding="utf-8"))


def json_files(path: Path) -> dict[str, dict]:
    return {file.stem: read_json(file) for file in sorted(path.glob("*.json"))}


def escaped(value) -> str:
    return html.escape(str(value), quote=True)


def readable_id(value: str) -> str:
    value = value.rsplit(":", 1)[-1].replace("/", " ").replace("_", " ")
    return " ".join(part[:1].upper() + part[1:] for part in value.split())


def resource_parts(resource: str) -> tuple[str, str]:
    if ":" in resource:
        return tuple(resource.split(":", 1))
    return "bentenmod", resource


class WikiBuilder:
    def __init__(self):
        self.lang = read_json(LANG_FILE)
        self.aliens = json_files(ALIEN_DIR)
        self.recipes = json_files(RECIPE_DIR)
        self.armor_sets = json_files(ARMOR_DIR)
        self.entity_loot = json_files(LOOT_DIR / "entities")
        self.block_loot = json_files(LOOT_DIR / "blocks")
        self.placed = json_files(PLACED_DIR)
        self.configured = json_files(CONFIGURED_DIR)
        self.progression = (ROOT / "docs" / "design" / "progression.md").read_text(encoding="utf-8")
        self.roadmap = (ROOT / "docs" / "design" / "roadmap.md").read_text(encoding="utf-8")
        self.changelog = (ROOT / "CHANGELOG.md").read_text(encoding="utf-8")
        self.item_ids = sorted(key.removeprefix("item.bentenmod.") for key in self.lang if key.startswith("item.bentenmod."))
        self.block_ids = sorted(key.removeprefix("block.bentenmod.") for key in self.lang if key.startswith("block.bentenmod."))
        self.recipe_outputs = defaultdict(list)
        self.loot_sources = defaultdict(list)
        self.worldgen = self.load_worldgen()
        self.power_names = self.load_power_names()
        self.recipe_rows = self.load_recipes()
        self.item_rows = self.load_item_rows()
        self.boss_rows = self.load_bosses()
        self.release_title, self.release_intro = self.load_release()
        self.key_letters = self.load_key_letters()
        self.default_recharge = self.load_default_recharge()
        self.projector_durability = self.load_projector_durability()

    def name(self, resource: str, kind: str | None = None) -> str:
        namespace, path = resource_parts(resource)
        if namespace == "bentenmod":
            if kind == "block" and f"block.bentenmod.{path}" in self.lang:
                return self.lang[f"block.bentenmod.{path}"]
            if kind == "item" and f"item.bentenmod.{path}" in self.lang:
                return self.lang[f"item.bentenmod.{path}"]
            if f"item.bentenmod.{path}" in self.lang:
                return self.lang[f"item.bentenmod.{path}"]
            if f"block.bentenmod.{path}" in self.lang:
                return self.lang[f"block.bentenmod.{path}"]
        return readable_id(path)

    def load_power_names(self) -> dict[str, str]:
        guide = self.lang.get("guide.bentenmod.chapter.aliens_and_dna.body", "")
        alien_ids_by_name = {
            self.lang.get(f"alien.bentenmod.{alien_id}", readable_id(alien_id)).casefold(): alien_id
            for alien_id in self.aliens
        }
        result = {}
        for sentence in guide.split("."):
            if ":" not in sentence:
                continue
            alien_name, power_detail = sentence.strip().split(":", 1)
            alien_id = alien_ids_by_name.get(alien_name.strip().casefold())
            if alien_id:
                result[alien_id] = power_detail.split(",", 1)[0].strip()
        return result

    def load_release(self) -> tuple[str, str]:
        match = re.search(r"^## \[([^\]]+)\][^\n]*\n\n([^\n]+)", self.changelog, re.MULTILINE)
        if not match:
            raise ValueError("Could not find the current release section in CHANGELOG.md")
        return match.group(1), match.group(2).strip()

    def load_default_recharge(self) -> int:
        definition = (COMMON / "main" / "java" / "io" / "github" / "realyusufismail" / "bentenmod" / "alien" / "AlienDefinition.java").read_text(encoding="utf-8")
        match = re.search(r'optionalFieldOf\("recharge_ticks",\s*(\d+)\)', definition)
        if not match:
            raise ValueError("Could not find the default recharge value in AlienDefinition.java")
        return int(match.group(1))

    @staticmethod
    def load_projector_durability() -> int:
        source = (COMMON / "main" / "java" / "io" / "github" / "realyusufismail" / "bentenmod" / "core" / "init" / "ItemInit.java").read_text(encoding="utf-8")
        match = re.search(r"NULL_VOID_PROJECTOR\s*=.*?\.durability\((\d+)\)", source, re.DOTALL)
        if not match:
            raise ValueError("Could not find Null Void Projector durability in its item class")
        return int(match.group(1))

    def load_key_letters(self) -> dict[str, str]:
        roots = [ROOT / "forge" / "src" / "main" / "java", ROOT / "fabric" / "src" / "main" / "java"]
        found = defaultdict(set)
        pattern = re.compile(r'KeyMapping\(\s*"(key\.bentenmod\.[a-z_]+)"\s*,\s*GLFW\.GLFW_KEY_([A-Z0-9]+)')
        for source_root in roots:
            for source in source_root.rglob("*.java"):
                text = source.read_text(encoding="utf-8")
                for key, letter in pattern.findall(text):
                    found[key].add(letter)
        if any(len(letters) != 1 for letters in found.values()):
            raise ValueError("Forge and Fabric key mappings disagree")
        return {key: next(iter(letters)) for key, letters in found.items()}

    def load_recipes(self) -> list[dict]:
        rows = []
        for slug, recipe in self.recipes.items():
            result = recipe.get("result", {})
            if isinstance(result, dict):
                result_id = result.get("item", result.get("id", ""))
                count = result.get("count", 1)
            elif isinstance(result, str):
                result_id = result
                count = 1
            else:
                result_id = ""
                count = 1
            row = {
                "slug": slug,
                "source_id": f"bentenmod:{slug}",
                "data": recipe,
                "result_id": result_id,
                "result_count": count,
                "group": self.recipe_group(recipe.get("type", "")),
            }
            rows.append(row)
            if result_id:
                self.recipe_outputs[result_id].append(row)
        return rows

    @staticmethod
    def recipe_group(recipe_type: str) -> str:
        if recipe_type.startswith("bentenmod:omnitrix_crafter_"):
            return "Omnitrix Crafter"
        return {
            "minecraft:crafting_shaped": "Crafting table",
            "minecraft:crafting_shapeless": "Crafting table",
            "minecraft:smelting": "Smelting",
            "minecraft:blasting": "Blasting",
        }.get(recipe_type, readable_id(recipe_type))

    def load_item_rows(self) -> list[dict]:
        rows = []
        for kind, ids in (("item", self.item_ids), ("block", self.block_ids)):
            for slug in ids:
                rows.append({
                    "kind": kind,
                    "slug": slug,
                    "source_id": f"bentenmod:{slug}",
                    "name": self.name(f"bentenmod:{slug}", kind),
                })
        rows.sort(key=lambda row: (row["kind"], row["name"].casefold(), row["slug"]))
        return rows

    def load_worldgen(self) -> dict[str, dict]:
        dimensions = {}
        biome_dir = GENERATED_RESOURCES / "data" / "bentenmod" / "forge" / "biome_modifier"
        for modifier in biome_dir.glob("*.json"):
            data = read_json(modifier)
            biome = data.get("biomes", "")
            dimension = "Nether" if "nether" in biome else "Overworld" if "overworld" in biome else "world generation"
            feature_ids = data.get("features", [])
            if isinstance(feature_ids, str):
                feature_ids = [feature_ids]
            for feature in feature_ids:
                dimensions[feature.rsplit(":", 1)[-1]] = dimension

        rows = {}
        for slug, feature in self.placed.items():
            configured = self.configured.get(slug, {})
            config = configured.get("config", {})
            targets = []
            for target in config.get("targets", []):
                block = target.get("state", {}).get("Name")
                if block and block not in targets:
                    targets.append(block)
            placement = feature.get("placement", [])
            count = next((part.get("count") for part in placement if part.get("type") == "minecraft:count"), None)
            chance = next((part.get("chance") for part in placement if part.get("type") == "minecraft:rarity_filter"), None)
            height = next((part.get("height") for part in placement if part.get("type") == "minecraft:height_range"), None)
            dimension = dimensions.get(slug, "Null Void" if slug == "null_crystal_ore" else "world generation")
            rows[slug] = {
                "dimension": dimension,
                "count": count,
                "chance": chance,
                "height": height,
                "vein_size": config.get("size"),
                "targets": targets,
            }
            for target in targets:
                rows.setdefault(target, rows[slug])

        if "omnitrix_crash_site" in rows:
            rows["bentenmod:omnitrix_pod"] = {
                **rows["omnitrix_crash_site"],
                "special": "crash-site pod",
            }
        rows["bentenmod:null_void_stone"] = {"dimension": "Null Void", "special": "terrain"}
        rows["bentenmod:null_void_stone_bricks"] = {"dimension": "Null Void", "special": "fortress"}
        rows["bentenmod:null_crystal_ore"] = rows.get("null_crystal_ore", {"dimension": "Null Void"})
        return rows

    def load_loot(self):
        for entity_id, table in self.entity_loot.items():
            for item_id, minimum, maximum in self.loot_entries(table):
                self.loot_sources[item_id].append({
                    "kind": "entity",
                    "name": ENTITY_LABELS.get(entity_id, self.lang.get(f"entity.bentenmod.{entity_id}", readable_id(entity_id))),
                    "range": (minimum, maximum),
                })
        for block_id, table in self.block_loot.items():
            for item_id, minimum, maximum in self.loot_entries(table):
                self.loot_sources[item_id].append({
                    "kind": "block",
                    "name": self.name(f"bentenmod:{block_id}", "block"),
                    "range": (minimum, maximum),
                })

    @staticmethod
    def loot_entries(value):
        results = []
        if isinstance(value, dict):
            if value.get("type") == "minecraft:item" and value.get("name"):
                minimum = maximum = 1
                for function in value.get("functions", []):
                    if function.get("function") == "minecraft:set_count":
                        count = function.get("count", 1)
                        if isinstance(count, dict):
                            minimum = count.get("min", 1)
                            maximum = count.get("max", minimum)
                        else:
                            minimum = maximum = count
                results.append((value["name"], minimum, maximum))
            for child in value.values():
                results.extend(WikiBuilder.loot_entries(child))
        elif isinstance(value, list):
            for child in value:
                results.extend(WikiBuilder.loot_entries(child))
        return results

    def load_bosses(self) -> list[dict]:
        source_root = COMMON / "main" / "java" / "io" / "github" / "realyusufismail" / "bentenmod"
        rows = []
        for slug, relative in BOSS_SOURCE.items():
            source = (source_root / relative).read_text(encoding="utf-8")
            health = self.java_attribute(source, "MAX_HEALTH")
            damage = self.java_attribute(source, "ATTACK_DAMAGE")
            loot = self.entity_loot.get(slug, {})
            drops = self.loot_entries(loot)
            rows.append({
                "slug": slug,
                "source_id": f"bentenmod:{slug}",
                "name": ENTITY_LABELS[slug],
                "health": health,
                "damage": damage,
                "attacks": BOSS_ATTACKS[slug],
                "drops": drops,
            })
        return rows

    @staticmethod
    def java_attribute(source: str, attribute: str):
        match = re.search(rf"Attributes\.{attribute}\s*,\s*([0-9][0-9_]*(?:\.[0-9]+)?)", source)
        if not match:
            return None
        return float(match.group(1).replace("_", ""))

    @staticmethod
    def ticks(value: int) -> str:
        if value % 20 == 0:
            seconds = value // 20
            if seconds >= 60 and seconds % 60 == 0:
                minutes = seconds // 60
                return f"{value:,} ticks ({minutes} min)"
            return f"{value:,} ticks ({seconds} sec)"
        return f"{value:,} ticks ({value / 20:g} sec)"

    @staticmethod
    def roman(value: int) -> str:
        values = [(10, "X"), (9, "IX"), (5, "V"), (4, "IV"), (1, "I")]
        result = ""
        for amount, numeral in values:
            while value >= amount:
                result += numeral
                value -= amount
        return result

    def ability_text(self, ability: dict) -> str:
        kind = ability.get("type", "").rsplit(":", 1)[-1]
        if kind == "effect":
            effect = readable_id(ability.get("effect", "effect"))
            amplifier = int(ability.get("amplifier", 0))
            return f"{effect} {self.roman(amplifier + 1)}"
        if kind == "flight":
            return f"Flight (speed {ability.get('speed', 0.05)})"
        if kind == "no_fall_damage":
            return "No fall damage"
        if kind == "attribute":
            attribute = ability.get("attribute", "attribute").rsplit(":", 1)[-1]
            attribute = attribute.removeprefix("generic.").replace("_", " ").lower()
            operation = ability.get("operation", "addition")
            amount = float(ability.get("amount", 0))
            if operation in ("multiply_base", "multiply_total"):
                percent = f"{amount * 100:g}"
                return f"{'+' if amount > 0 else ''}{percent}% {attribute}"
            return f"{'+' if amount > 0 else ''}{amount:g} {attribute}"
        return readable_id(kind)

    def ability_list(self, abilities: list[dict]) -> str:
        if not abilities:
            return "<li>No passive abilities are listed in this definition.</li>"
        return "".join(f"<li>{escaped(self.ability_text(ability))}</li>" for ability in abilities)

    def dna_text(self, alien: dict) -> str:
        dna = alien.get("dna_sources", {})
        sources = []
        for kind in ("entities", "blocks"):
            for source in dna.get(kind, []):
                sources.append(self.name(source))
        return ", ".join(escaped(source) for source in sources) if sources else "Starter form; no DNA scan needed"

    def docs_height(self, slug: str) -> str | None:
        alias = slug.removesuffix("_ore").replace("_", " ").casefold()
        for line in self.progression.splitlines():
            if not line.lstrip().startswith("|"):
                continue
            cells = [cell.strip() for cell in line.strip().strip("|").split("|")]
            if len(cells) < 4 or not cells[0] or set(cells[0]) <= {"-", ":"}:
                continue
            if alias in cells[0].casefold() and "y " in cells[3].casefold():
                return cells[3]
        return None

    def height_text(self, slug: str, data: dict) -> str:
        documented = self.docs_height(slug)
        if documented:
            return documented
        height = data.get("height")
        if not height:
            return "Not specified by the placed-feature file"

        def bound(value: dict) -> str:
            if "absolute" in value:
                return str(value["absolute"])
            if "above_bottom" in value:
                return f"world bottom + {value['above_bottom']}"
            if "below_top" in value:
                return f"world top - {value['below_top']}"
            return "?"

        return f"y {bound(height.get('min_inclusive', {}))} to {bound(height.get('max_inclusive', {}))} ({readable_id(height.get('type', 'height range'))})"

    def chance_text(self, data: dict) -> str:
        if data.get("chance"):
            return f"1 in {data['chance']} chunks"
        if data.get("count"):
            return f"{data['count']} placements per chunk"
        return "Placement count not specified"

    def page(self, title: str, description: str, current: str, content: str, depth: int = 0) -> str:
        prefix = "../" * depth
        nav_links = []
        for path, label, key in NAV:
            current_attribute = ' aria-current="page"' if current == key else ""
            nav_links.append(f'<a href="{prefix}{escaped(path)}"{current_attribute}>{escaped(label)}</a>')
        links = "".join(nav_links)
        return f"""<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="{escaped(description)}">
  <title>{escaped(title)} | Ben 10 Mod Wiki</title>
  <link rel="icon" href="{prefix}hourglass.svg" type="image/svg+xml">
  <link rel="stylesheet" href="{prefix}style.css">
  <script defer src="{prefix}site.js"></script>
</head>
<body>
  <a class="skip-link" href="#main">Skip to content</a>
  <header class="site-header">
    <div class="header-top">
      <a class="brand" href="{prefix}index.html" aria-label="Ben 10 Mod Wiki home">
        <img src="{prefix}hourglass.svg" alt="">
        <span>Ben 10 Mod Wiki</span>
      </a>
      <nav class="site-nav" aria-label="Wiki sections">{links}</nav>
    </div>
  </header>
  <main id="main" class="page-wrap">{content}</main>
  <footer class="site-footer">
    <p>Ben 10 Mod player wiki. Not affiliated with Cartoon Network.</p>
    <div class="footer-links">
      <a href="{CURSEFORGE_URL}" rel="noopener">CurseForge</a>
      <a href="{GITHUB_URL}" rel="noopener">GitHub</a>
    </div>
  </footer>
</body>
</html>
"""

    def page_heading(self, eyebrow: str, title: str, intro: str) -> str:
        return f"""<header class="page-heading">
  <span class="eyebrow">{escaped(eyebrow)}</span>
  <h1>{escaped(title)}</h1>
  <p>{escaped(intro)}</p>
</header>"""

    def section_heading(self, title: str, identifier: str | None = None) -> str:
        id_attribute = f' id="{escaped(identifier)}"' if identifier else ""
        return f'<h2 class="section-heading"{id_attribute}>{escaped(title)}</h2>'

    def image_panel(self, filename: str, alt: str, caption: str = "", size_class: str = "") -> str:
        caption_html = f'<p class="image-caption">{escaped(caption)}</p>' if caption else ""
        classes = " ".join(part for part in ("image-panel", "casing", size_class) if part)
        return f'<figure class="{classes}"><img src="assets/images/{escaped(filename)}" alt="{escaped(alt)}">{caption_html}</figure>'

    def icon_file(self, resource: str, kind: str | None = None) -> str | None:
        namespace, slug = resource_parts(resource)
        if namespace != "bentenmod":
            return None
        if kind is None:
            kind = "block" if slug in self.block_ids else "item"
        source = self.texture_path(slug, kind)
        if not source:
            return None
        target = OUTPUT / "assets" / "icons" / f"{namespace}_{slug}.png"
        target.parent.mkdir(parents=True, exist_ok=True)
        if target.exists():
            return target.relative_to(OUTPUT).as_posix()
        with Image.open(source) as original:
            image = original.convert("RGBA")
        size = 64
        ratio = min(size / image.width, size / image.height)
        dimensions = (max(1, round(image.width * ratio)), max(1, round(image.height * ratio)))
        image = image.resize(dimensions, Image.Resampling.NEAREST)
        canvas = Image.new("RGBA", (size, size), (0, 0, 0, 0))
        canvas.alpha_composite(image, ((size - dimensions[0]) // 2, (size - dimensions[1]) // 2))
        canvas.save(target, format="PNG", optimize=True)
        return target.relative_to(OUTPUT).as_posix()

    def texture_path(self, slug: str, kind: str) -> Path | None:
        texture_root = MAIN_RESOURCES / "assets" / "bentenmod" / "textures"
        folder = "block" if kind == "block" else "item"
        exact = texture_root / folder / f"{slug}.png"
        if exact.exists():
            return exact

        texture_from_model = self.model_texture(slug, kind)
        if texture_from_model and texture_from_model.exists():
            return texture_from_model

        if kind == "block":
            for suffix in ("_front", "_top", "_side", "_base"):
                candidate = texture_root / "block" / f"{slug}{suffix}.png"
                if candidate.exists():
                    return candidate
        else:
            if slug == "omnitrix_watch":
                device = texture_root / "item" / "omnitrix_device.png"
                if device.exists():
                    return device
            placeholder = texture_root / "item" / "placeholder.png"
            if slug.endswith("_spawn_egg") and placeholder.exists():
                return placeholder
        return None

    def model_texture(self, slug: str, kind: str) -> Path | None:
        asset_roots = [MAIN_RESOURCES / "assets" / "bentenmod", GENERATED_RESOURCES / "assets" / "bentenmod"]
        model_kind = "block" if kind == "block" else "item"
        candidate_names = [slug]
        if slug == "omnitrix_watch":
            candidate_names.append("omnitrix_device")
        for candidate in candidate_names:
            for asset_root in asset_roots:
                model_file = asset_root / "models" / model_kind / f"{candidate}.json"
                if not model_file.exists():
                    continue
                model = read_json(model_file)
                textures = model.get("textures", {})
                preferred = ("layer0", "all", "front", "side", "top", "particle", "base")
                for name in preferred:
                    value = textures.get(name)
                    if not value:
                        continue
                    texture = self.texture_from_ref(value, asset_roots)
                    if texture:
                        return texture
        if kind == "block":
            texture_root = MAIN_RESOURCES / "assets" / "bentenmod" / "textures" / "block"
            for asset_root in asset_roots:
                state_file = asset_root / "blockstates" / f"{slug}.json"
                if not state_file.exists():
                    continue
                blockstate = read_json(state_file)
                models = []
                if "variants" in blockstate:
                    models.extend(value.get("model") for value in blockstate["variants"].values() if value.get("model"))
                for multipart in blockstate.get("multipart", []):
                    apply = multipart.get("apply", {})
                    if isinstance(apply, list):
                        models.extend(value.get("model") for value in apply if value.get("model"))
                    elif apply.get("model"):
                        models.append(apply["model"])
                for model_ref in models:
                    model_file = self.model_file(model_ref, asset_roots)
                    if model_file is None:
                        continue
                    textures = read_json(model_file).get("textures", {})
                    for name in ("all", "front", "side", "top", "particle", "base"):
                        value = textures.get(name)
                        if value:
                            texture = self.texture_from_ref(value, asset_roots)
                            if texture:
                                return texture
            if texture_root.exists():
                for candidate in sorted(texture_root.glob(f"{slug}_*.png")):
                    return candidate
        return None

    @staticmethod
    def model_file(model_ref: str, asset_roots: list[Path]) -> Path | None:
        namespace, model_path = resource_parts(model_ref)
        if "/" not in model_path:
            model_path = f"models/block/{model_path}"
        else:
            model_path = f"models/{model_path}"
        for root in asset_roots:
            if root.name == namespace:
                path = root / f"{model_path}.json"
                if path.exists():
                    return path
            path = root.parent / namespace / f"{model_path}.json"
            if path.exists():
                return path
        return None

    @staticmethod
    def texture_from_ref(texture_ref: str, asset_roots: list[Path]) -> Path | None:
        if texture_ref.startswith("#"):
            return None
        namespace, texture_path = resource_parts(texture_ref)
        if not texture_path.startswith("textures/"):
            texture_path = f"textures/{texture_path}"
        if not texture_path.endswith(".png"):
            texture_path += ".png"
        for root in asset_roots:
            if root.name == namespace:
                path = root / texture_path
                if path.exists():
                    return path
            path = root.parent / namespace / texture_path
            if path.exists():
                return path
        return None

    def icon_markup(self, resource: str, kind: str, prefix: str, alt: str = "") -> str:
        source = self.icon_file(resource, kind)
        if source:
            return f'<img class="item-icon" src="{prefix}{escaped(source)}" alt="{escaped(alt)}">'
        return f'<span class="missing-icon" aria-label="No source texture found"><img src="{prefix}hourglass.svg" alt=""></span>'

    def recipe_source(self, recipe: dict) -> str:
        return recipe["group"]

    def get_sources(self, row: dict) -> str:
        resource = row["source_id"]
        prefix = ""
        sources = []
        if resource == "bentenmod:omnitrix_watch":
            sources.append("Crash-site pod; the Crafter chain also has a recipe.")
        if resource == "bentenmod:omnitrix_pod":
            sources.append("Break the pod at an Omnitrix crash site.")
        if resource == "bentenmod:null_void_stone":
            sources.append("Null Void terrain.")
        if resource == "bentenmod:null_void_stone_bricks" and self.worldgen.get(resource, {}).get("special") == "fortress":
            sources.append("Null Void fortress blocks.")

        for recipe in self.recipe_outputs.get(resource, []):
            label = self.recipe_source(recipe)
            if label == "Crafting table":
                sources.append(f'<a href="recipes.html#recipe-{escaped(recipe["slug"])}">Crafting recipe</a>')
            else:
                sources.append(f'<a href="recipes.html#recipe-{escaped(recipe["slug"])}">{escaped(label)} recipe</a>')

        for source in self.loot_sources.get(resource, []):
            quantity = self.quantity_text(source["range"])
            sources.append(f'{escaped(quantity)} from {escaped(source["name"])}')

        world = self.worldgen.get(resource)
        if world and world.get("dimension"):
            location = world["dimension"]
            detail = world.get("special")
            if resource.endswith("_ore") or resource.split(":", 1)[-1].startswith("deepslate_"):
                sources.append(f"Worldgen: {escaped(location)}")
            elif detail == "crash-site pod" and not any("crash site" in item.lower() for item in sources):
                sources.append(f"Worldgen: {escaped(location)} crash site")

        if not sources:
            return "No recipe, loot drop or placed-feature source is listed in the current data."
        return "<br>".join(sources)

    @staticmethod
    def quantity_text(bounds: tuple[int, int]) -> str:
        minimum, maximum = bounds
        if minimum == maximum == 1:
            return "Drop"
        if minimum == maximum:
            return f"{WikiBuilder.number_text(minimum)} drops"
        return f"{WikiBuilder.number_text(minimum)} to {WikiBuilder.number_text(maximum)} drops"

    @staticmethod
    def number_text(value) -> str:
        return str(int(value)) if isinstance(value, float) and value.is_integer() else str(value)

    def ingredient_details(self, ingredient) -> tuple[str, str | None]:
        if isinstance(ingredient, list):
            names = [self.ingredient_details(value)[0] for value in ingredient]
            return " or ".join(names), None
        if not isinstance(ingredient, dict):
            return readable_id(str(ingredient)), None
        if "item" in ingredient:
            resource = ingredient["item"]
            return self.name(resource), resource
        if "tag" in ingredient:
            tag_path = ingredient["tag"].split(":", 1)[-1]
            item_path = tag_path.rsplit("/", 1)[-1]
            if f"item.bentenmod.{item_path}" in self.lang:
                return self.name(f"bentenmod:{item_path}", "item"), f"bentenmod:{item_path}"
            tag = tag_path.replace("/", " / ").replace("_", " ")
            return f"Tag: {tag}", None
        return "Ingredient", None

    def slot(self, ingredient, prefix: str) -> str:
        if ingredient is None:
            return '<span class="recipe-slot empty" aria-hidden="true"></span>'
        name, resource = self.ingredient_details(ingredient)
        if not resource:
            return f'<span class="recipe-slot vanilla-slot" title="{escaped(name)}"><span class="recipe-slot-name">{escaped(name)}</span></span>'
        is_tag = isinstance(ingredient, dict) and "tag" in ingredient
        title = f"{name} (any item in this tag)" if is_tag else name
        kind = "block" if resource_parts(resource)[1] in self.block_ids else "item"
        icon = self.icon_file(resource, kind)
        image = f'<img src="{prefix}{escaped(icon)}" alt="">' if icon else ""
        return f'<span class="recipe-slot" title="{escaped(title)}">{image}<span class="recipe-slot-name">{escaped(name)}</span></span>'

    def recipe_grid(self, recipe: dict, prefix: str) -> str:
        data = recipe["data"]
        cells = [None] * 9
        if "pattern" in data:
            for y, row in enumerate(data["pattern"][:3]):
                for x, marker in enumerate(row[:3]):
                    if marker != " ":
                        cells[y * 3 + x] = data.get("key", {}).get(marker)
        elif "ingredients" in data:
            for index, ingredient in enumerate(data["ingredients"][:9]):
                cells[index] = ingredient
        elif "ingredient" in data:
            cells[4] = data["ingredient"]
        grid = "".join(self.slot(cell, prefix) for cell in cells)
        return f'<div class="recipe-slots" aria-label="Recipe ingredients">{grid}</div>'

    def recipe_card(self, recipe: dict, prefix: str) -> str:
        result = recipe["result_id"]
        result_name = self.name(result) if result else "Result not specified"
        result_kind = "block" if resource_parts(result)[1] in self.block_ids else "item"
        result_icon = self.icon_file(result, result_kind) if result else None
        result_image = f'<img src="{prefix}{escaped(result_icon)}" alt="">' if result_icon else '<span class="missing-icon"><img src="' + prefix + 'hourglass.svg" alt=""></span>'
        count = recipe["result_count"]
        result_count = f'<span class="count">× {escaped(count)}</span>' if count != 1 else ""
        category = recipe["data"].get("category", "")
        category = f'<span>{escaped(readable_id(category))}</span>' if category else ""
        return f"""<article id="recipe-{escaped(recipe['slug'])}" class="recipe-card" data-recipe-id="{escaped(recipe['source_id'])}">
  <h3>{escaped(result_name)}</h3>
  <div class="recipe-card-meta"><span>{escaped(recipe['group'])}</span>{category}<span>{escaped(recipe['slug'].replace('_', ' '))}</span></div>
  <div class="recipe-flow">{self.recipe_grid(recipe, prefix)}<span class="recipe-arrow" aria-hidden="true">›</span>
    <div class="recipe-result">{result_image}<strong>{escaped(result_name)}</strong>{result_count}</div>
  </div>
</article>"""

    def render_home(self) -> str:
        cards = [
            ("getting-started.html", "Getting started", "Find or build the watch, bind it, and open the dial."),
            ("omnitrix.html", "The Omnitrix", "Transformation time, recharge, Master Control and creative mode."),
            ("aliens.html", "Ten aliens", "Browse forms, passive abilities, powers and DNA sources."),
            ("items.html", "Items & blocks", "Search the mod's item and block registry with source recipes and drops."),
            ("recipes.html", "Recipes", "Browse all current Crafter, crafting-table, smelting and blasting recipes."),
            ("ores-and-armour.html", "Ores & armour", "See ore placement data and the current armour set bonuses."),
            ("bosses.html", "Bosses", "Find Kraab, summon Vilgax and follow the trail to Zs'Skayr."),
            ("null-void.html", "The Null Void", "Enter through the Projector and explore the floating islands."),
            ("roadmap.html", "Roadmap", "Future releases from the project plan, marked as planned."),
        ]
        card_html = "".join(
            f'<article class="section-card"><div><h3>{escaped(title)}</h3><p>{escaped(description)}</p></div><a href="{escaped(path)}">Open section</a></article>'
            for path, title, description in cards
        )
        version = self.release_title.rsplit("-", 1)[-1]
        content = f"""<section class="hero casing" aria-labelledby="home-title">
  <div class="hero-copy">
    <span class="eyebrow">Player field guide</span>
    <h1 id="home-title">The Omnitrix is in your hands.</h1>
    <p>Find the crashed watch or build one in the Omnitrix Crafter. Unlock ten classic aliens, then follow the trail from a Kraab hunter to the Null Void.</p>
    <p class="release-line">Minecraft 1.20.1 · Ben 10 Mod {escaped(version)} · Forge, NeoForge and Fabric</p>
    <div class="hero-actions">
      <a class="button-link" href="getting-started.html">Start playing</a>
      <a class="button-link secondary" href="aliens.html">Meet the aliens</a>
    </div>
    <div class="fact-strip"><span class="fact-chip">GeckoLib required</span><span class="fact-chip">Fabric API required on Fabric</span></div>
  </div>
  <div class="hero-art"><img src="assets/images/banner.png" alt="Ben 10 Mod banner showing Four Arms, Heatblast and Diamondhead"></div>
</section>

<section class="content-section" aria-labelledby="start-title">
  {self.section_heading('Start here', 'start-title')}
  <div class="start-steps">
    <article class="step-card"><h3>Find or craft the watch</h3><p>Look for a crash-site pod or make the Omnitrix Core and watch in the Crafter.</p><a href="getting-started.html">How to get it</a></article>
    <article class="step-card"><h3>Bind it to your wrist</h3><p>Right-click the watch once. It stays equipped to your player.</p><a href="omnitrix.html">How the watch works</a></article>
    <article class="step-card"><h3>Choose a form</h3><p>Press G or right-click the air with an empty hand to open the dial.</p><a href="aliens.html">Browse all ten aliens</a></article>
  </div>
</section>

<section class="content-section" aria-labelledby="sections-title">
  {self.section_heading('Explore the field guide', 'sections-title')}
  <div class="section-grid">{card_html}</div>
</section>

<section class="content-section casing casing-pad-large">
  {self.section_heading('Get the mod')}
  <div class="article-copy"><p>{escaped(self.release_intro)} Version {escaped(version)} targets Minecraft 1.20.1. The mod supports Forge, NeoForge and Fabric, and requires GeckoLib. Fabric installs also need Fabric API.</p>
  <div class="hero-actions"><a class="button-link" href="{CURSEFORGE_URL}" rel="noopener">Download on CurseForge</a><a class="button-link secondary" href="{RELEASES_URL}" rel="noopener">GitHub releases</a></div></div>
</section>"""
        return self.page("Home", "A player guide to the Ben 10 Mod for Minecraft 1.20.1.", "home", content)

    def render_getting_started(self) -> str:
        guide = self.lang.get("guide.bentenmod.chapter.getting_started.body", "")
        finding = self.lang.get("guide.bentenmod.chapter.finding_the_omnitrix.body", "")
        controls = self.lang.get("guide.bentenmod.chapter.equipping_the_dial.body", "")
        crash = self.worldgen.get("omnitrix_crash_site", {})
        chance = crash.get("chance")
        crash_rate = f"The current placed-feature data attempts a crash site once per {chance} chunks." if chance else "The crash site is a rare world-generation feature."
        key_rows = []
        controls_to_show = [
            ("key.bentenmod.omnitrix", "Open the dial"),
            ("key.bentenmod.use_alien_power", "Use the selected alien power"),
            ("key.bentenmod.revert_to_human", "Revert to human"),
        ]
        for key, action in controls_to_show:
            letter = self.key_letters.get(key, "")
            label = self.lang.get(key, action)
            key_rows.append(f'<tr><th scope="row"><kbd>{escaped(letter)}</kbd></th><td>{escaped(label)}</td><td>{escaped(action)}</td></tr>')
        key_rows.append('<tr><th scope="row">Empty hand</th><td>Right-click the air</td><td>Open the dial</td></tr>')
        field_guide = self.lang.get("item.bentenmod.omnitrix_guide", "Omnitrix Field Guide")
        guide_recipe = next((row for row in self.recipe_rows if row["slug"] == "omnitrix_guide"), None)
        guide_recipe_link = f'<a href="recipes.html#recipe-{escaped(guide_recipe["slug"])}">book and lime dye recipe</a>' if guide_recipe else "book and lime dye recipe"
        content = self.page_heading("First steps", "Getting started", "Find the watch, bind it, and learn the dial controls.")
        content += f"""<div class="article-layout">
  <article class="article-copy">
    <p>{escaped(guide)}</p>
    <section class="content-section" id="find-the-watch"><h2 class="section-heading">Find the watch</h2>
      <div class="casing casing-pad-medium"><p>{escaped(finding)}</p><p>{escaped(crash_rate)}</p></div>
      <p>Crash sites are placed in forests, plains and sunflower plains, taigas, and savannas. Break the Omnitrix Pod to collect the watch.</p>
      <p>The reliable route is the Omnitrix Crafter chain: Legendary Casing and Black Diamond Lens make an Omnitrix Core, then leather and iron nuggets finish the watch.</p>
      <p><a href="recipes.html#recipe-legendary_casing">Legendary Casing recipe</a> · <a href="recipes.html#recipe-black_diamond_lens">Black Diamond Lens recipe</a> · <a href="recipes.html#recipe-omnitrix_core">Omnitrix Core recipe</a> · <a href="recipes.html#recipe-omnitrix_watch">Omnitrix recipe</a></p>
    </section>
    <section class="content-section" id="equip"><h2 class="section-heading">Equip and open the dial</h2>
      <p>{escaped(controls)}</p>
      <div class="table-scroll"><table class="key-table"><thead><tr><th>Key</th><th>Key name</th><th>Action</th></tr></thead><tbody>{''.join(key_rows)}</tbody></table></div>
      <p>Use the mouse wheel or left and right arrow keys to turn the dial. Click the form or press Enter to transform. While transformed, V uses the active power and H returns you to human form.</p>
    </section>
    <section class="content-section" id="field-guide"><h2 class="section-heading">The Field Guide</h2>
      <p>{escaped(field_guide)} is given when you first join. You can also craft it with a book and lime dye using the {guide_recipe_link}.</p>
      <p>The book's chapters cover the crash site, controls, alien DNA, timeout and recharge, armour, bosses, the Null Void and Master Control.</p>
    </section>
  </article>
  <aside class="side-panel casing"><h2>At a glance</h2><ul><li>First three forms: Heatblast, Four Arms and XLR8.</li><li>G opens the dial.</li><li>Scan listed creatures or blocks to unlock the other forms.</li><li>The Field Guide arrives on first join.</li></ul></aside>
</div>"""
        return self.page("Getting started", "Find and equip the Omnitrix, use the dial, and open the Field Guide.", "getting-started", content)

    def render_omnitrix(self) -> str:
        durations = [alien.get("duration_ticks", 6000) for alien in self.aliens.values()]
        same_duration = len(set(durations)) == 1
        duration = self.ticks(durations[0]) if durations else "Not specified"
        timeout_text = f"All {len(durations)} alien definitions set the same transformation duration: {duration}." if same_duration else f"Transformation duration comes from each alien definition. Current values range from {self.ticks(min(durations))} to {self.ticks(max(durations))}."
        recharge_explicit = [alien["recharge_ticks"] for alien in self.aliens.values() if "recharge_ticks" in alien]
        recharge_value = recharge_explicit[0] if recharge_explicit else self.default_recharge
        recharge_text = self.ticks(recharge_value)
        timer_copy = self.lang.get("guide.bentenmod.chapter.timeout_and_recharge.body", "")
        master_copy = self.lang.get("guide.bentenmod.chapter.master_control.body", "")
        creative_copy = next((line.strip()[2:] for line in self.changelog.splitlines() if line.strip().startswith("- In creative mode,")), "Creative mode puts every alien on the dial.")
        death_copy = "Death changes the player back to human form; the current code applies that revert during respawn."
        content = self.page_heading("Watch systems", "The Omnitrix", "The dial selects a form, the HUD tracks its timer, and the Field Guide explains the limits.")
        content += f"""<div class="article-layout">
  <article class="article-copy">
    <section><h2 class="section-heading">Dial and transformation</h2>
      <p>With the Omnitrix equipped, press G or right-click air with an empty hand. Turn the dial and select an unlocked alien. Press H to revert to human, or V to activate the selected form's power.</p>
      <p>{escaped(timeout_text)} The countdown appears near the hotbar. The Field Guide says it warns with beeps during the final ten seconds.</p>
    </section>
    <section class="content-section"><h2 class="section-heading">Timeout and recharge</h2>
      <p>{escaped(timer_copy)}</p>
      <div class="stat-list"><div><dt>Transformation</dt><dd>{escaped(duration)}</dd></div><div><dt>Recharge after timeout</dt><dd>{escaped(recharge_text)}</dd></div></div>
      <p>Reverting before the timer expires avoids the timeout recharge. Each alien can override the default recharge in its data file.</p>
    </section>
    <section class="content-section"><h2 class="section-heading">Master Control</h2>
      <p>{escaped(master_copy)}</p>
    </section>
    <section class="content-section"><h2 class="section-heading">Creative mode and death</h2>
      <p>{escaped(creative_copy)}</p>
      <p>{escaped(death_copy)}</p>
    </section>
  </article>
  <aside class="side-panel casing"><h2>What the watch tracks</h2><ul><li>Current alien and its hitbox.</li><li>Remaining transformation time.</li><li>Recharge after timeout.</li><li>Unlocked forms stored for your player.</li></ul></aside>
</div>"""
        return self.page("The Omnitrix", "Transformation, the dial, timer, recharge, Master Control, creative mode and death behavior.", "omnitrix", content)

    def alien_render_path(self, slug: str) -> str | None:
        source = ROOT / "tools" / "alien-gen" / "out" / f"{slug}.png"
        if not source.exists():
            return None
        target = OUTPUT / "assets" / "aliens" / f"{slug}.png"
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(source, target)
        return target.relative_to(OUTPUT).as_posix()

    def alien_card(self, slug: str, data: dict) -> str:
        name = self.lang.get(f"alien.bentenmod.{slug}", readable_id(slug))
        render = self.alien_render_path(slug)
        image = f'<img class="alien-render" src="{escaped(render)}" alt="">' if render else '<span class="missing-icon" aria-label="No alien render found"><img src="hourglass.svg" alt=""></span>'
        starter = data.get("unlocked_by_default", False)
        status = '<span class="status-tag">Starter form</span>' if starter else '<span class="status-tag locked">Scan DNA</span>'
        dna = self.dna_text(data)
        return f'<article class="alien-card" data-alien-id="bentenmod:{escaped(slug)}"><a href="aliens/{escaped(slug)}.html">{image}<h3>{escaped(name)}</h3><p>{"Starter form" if starter else "DNA: " + dna}</p>{status}</a></article>'

    def render_aliens(self) -> str:
        cards = "".join(self.alien_card(slug, data) for slug, data in sorted(self.aliens.items(), key=lambda item: self.lang.get(f"alien.bentenmod.{item[0]}", item[0]).casefold()))
        content = self.page_heading("Alien index", "Ten forms. Ten powers.", "Three forms are ready at the start. Scan the listed DNA sources to unlock the other seven.")
        content += self.image_panel("aliens.png", "All ten Ben 10 Mod aliens arranged in two rows", "The current release includes ten alien definitions.")
        content += f'<section class="content-section"><div class="alien-grid">{cards}</div></section>'
        return self.page("Aliens", "All ten aliens, their renders and DNA unlock sources.", "aliens", content)

    def render_alien(self, slug: str, data: dict) -> str:
        name = self.lang.get(f"alien.bentenmod.{slug}", readable_id(slug))
        render = self.alien_render_path(slug)
        image = f'<img class="alien-render" src="{escaped("../" + render)}" alt="{escaped(name)} render">' if render else '<div class="missing-icon"><img src="../hourglass.svg" alt=""></div>'
        duration = self.ticks(data.get("duration_ticks", 6000))
        power = data.get("power", {})
        power_type = power.get("type", "").rsplit(":", 1)[-1]
        power_name = self.power_names.get(slug, readable_id(power_type))
        cooldown = power.get("cooldown_ticks")
        cooldown_text = self.ticks(cooldown) if cooldown is not None else "No cooldown listed"
        starter = data.get("unlocked_by_default", False)
        status = "Starter form" if starter else f"Scan {self.dna_text(data)} to unlock"
        content = self.page_heading("Alien file", name, status)
        content += f"""<div class="alien-detail">
  <figure class="alien-detail-figure casing">{image}<figcaption class="image-caption">{escaped(name)} render from the repository's alien art output.</figcaption></figure>
  <div class="article-copy">
    <dl class="stat-list"><div><dt>Hitbox width</dt><dd>{escaped(data.get('width'))} blocks</dd></div><div><dt>Hitbox height</dt><dd>{escaped(data.get('height'))} blocks</dd></div><div><dt>Form duration</dt><dd>{escaped(duration)}</dd></div><div><dt>Unlock</dt><dd>{escaped(status)}</dd></div></dl>
    <section class="content-section"><h2 class="section-heading">Passive abilities</h2><ul class="ability-list">{self.ability_list(data.get('abilities', []))}</ul></section>
    <section class="content-section"><h2 class="section-heading">Active power</h2><p><strong>{escaped(power_name)}</strong>, cooldown {escaped(cooldown_text)}.</p></section>
    <section class="content-section"><h2 class="section-heading">DNA source</h2><p>{self.dna_text(data)}</p></section>
    <p><a href="../aliens.html">Back to all aliens</a></p>
  </div>
</div>"""
        return self.page(name, f"Size, passive abilities, active power and DNA source for {name}.", "aliens", content, depth=1)

    def render_items(self) -> str:
        self.load_loot()
        rows = []
        for row in self.item_rows:
            icon = self.icon_markup(row["source_id"], row["kind"], "")
            searchable = f"{row['name']} {row['source_id']} {row['kind']}".casefold()
            rows.append(f'<tr data-item-row data-registry-id="{escaped(row["source_id"])}" data-search="{escaped(searchable)}"><td>{icon}</td><td><strong>{escaped(row["name"])}</strong><span class="registry-name">{escaped(row["source_id"])}</span></td><td>{"Block" if row["kind"] == "block" else "Item"}</td><td>{self.get_sources(row)}</td></tr>')
        table = f"""<div class="table-scroll"><table class="item-table"><thead><tr><th>Icon</th><th>Name</th><th>Kind</th><th>How to get it</th></tr></thead><tbody>{''.join(rows)}</tbody></table></div>"""
        content = self.page_heading("Registry index", "Items & blocks", f"Search all {len(self.item_rows)} named mod items and blocks. Recipe links point to the matching recipe card.")
        content += f"""<div class="item-tools"><div><label class="search-label" for="item-search">Search by name or registry id</label><input class="search-input" id="item-search" type="search" autocomplete="off" placeholder="Try Vilgax, ore or bentenmod:"></div><p class="search-count" id="item-count" aria-live="polite">{len(self.item_rows)} entries</p></div>
<p class="empty-state" id="item-empty" hidden>No matching items or blocks.</p>
{table}
<p class="article-copy">A source note appears when a recipe, entity or block loot table, or placed feature identifies a way to get an entry. The current data does not provide a source for every registry entry.</p>"""
        return self.page("Items & blocks", "Search the mod's named items and blocks, with icons and recipe or loot sources.", "items", content)

    def render_recipes(self) -> str:
        groups = defaultdict(list)
        for recipe in self.recipe_rows:
            groups[recipe["group"]].append(recipe)
        order = ["Omnitrix Crafter", "Crafting table", "Smelting", "Blasting"]
        sections = []
        for group in order + sorted(set(groups) - set(order)):
            recipes = groups.get(group, [])
            if not recipes:
                continue
            cards = "".join(self.recipe_card(recipe, "") for recipe in recipes)
            sections.append(f'<section class="recipe-group" id="{escaped(group.lower().replace(" ", "-"))}"><h2>{escaped(group)} <span class="recipe-group-count">{len(recipes)} recipes</span></h2><div class="recipe-grid">{cards}</div></section>')
        content = self.page_heading("Recipe archive", "Recipes", f"Every generated recipe in the current data set: {len(self.recipe_rows)} recipes across {len(groups)} work types.")
        content += self.image_panel("crafter.png", "Omnitrix Crafter textures and a sketch of its recipe grid", "Omnitrix Crafter recipes use the custom workstation.", "image-panel-small")
        content += f'<div class="content-section recipe-groups">{"".join(sections)}</div>'
        return self.page("Recipes", "Every current Omnitrix Crafter, crafting-table, smelting and blasting recipe.", "recipes", content)

    def render_ores_armour(self) -> str:
        ore_ids = [slug for slug in self.placed if slug.endswith("_ore")]
        rows = []
        for slug in sorted(ore_ids):
            data = self.worldgen.get(slug, {})
            targets = data.get("targets", [])
            target_names = [self.name(target, "block") for target in targets]
            if not target_names:
                target_names = [self.name(f"bentenmod:{slug}", "block")]
            if slug == "null_crystal_ore":
                dimension = "Null Void"
            else:
                dimension = data.get("dimension", "Overworld")
            height = self.height_text(slug, data)
            vein = data.get("vein_size")
            rows.append(f'<tr data-ore-id="bentenmod:{escaped(slug)}"><td><strong>{escaped(" / ".join(target_names))}</strong><span class="registry-name">{escaped(dimension)}</span></td><td>{escaped(vein if vein is not None else "Not listed")}</td><td>{escaped(self.chance_text(data))}</td><td>{escaped(height)}</td></tr>')
        ore_table = f'<div class="table-scroll"><table class="ore-table"><thead><tr><th>Ore and location</th><th>Vein size</th><th>Placement</th><th>Height range</th></tr></thead><tbody>{"".join(rows)}</tbody></table></div>'
        armor_cards = []
        for slug, data in sorted(self.armor_sets.items()):
            name = self.lang.get(f"alien.bentenmod.{slug}", self.name(f"bentenmod:{slug}"))
            pieces = [self.name(resource, "item") for resource in data.get("pieces", {}).values()]
            bonuses = self.ability_list(data.get("abilities", []))
            armor_cards.append(f'<article class="armor-card"><h3>{escaped(name)} set</h3><p class="armor-pieces">Pieces: {escaped(", ".join(pieces))}</p><ul class="ability-list">{bonuses}</ul></article>')
        content = self.page_heading("World generation and gear", "Ores & armour", "Ore placement comes from the placed and configured feature data. Set bonuses come from the armour set definitions.")
        content += f'<section><h2 class="section-heading">Ores</h2><p class="article-copy">Counts, rarity filters, vein sizes and height ranges are read from world-generation data. Height labels use the current progression table where it gives final Y coordinates.</p>{ore_table}</section>'
        content += f'<section class="content-section"><h2 class="section-heading">Armour set bonuses</h2><div class="armor-grid">{"".join(armor_cards)}</div><p class="article-copy">The set bonus applies while every piece listed for that set is worn.</p></section>'
        return self.page("Ores & armour", "Ore generation and armour set pieces and bonuses.", "ores", content)

    def boss_drop_text(self, drops: list[tuple[str, int, int]]) -> str:
        if not drops:
            return "No drops listed in the entity loot table."
        entries = []
        for resource, minimum, maximum in drops:
            quantity = "" if minimum == maximum == 1 else f" ({self.number_text(minimum)} to {self.number_text(maximum)})"
            entries.append(f"{self.name(resource)}{quantity}")
        return ", ".join(entries)

    def kraab_find_text(self) -> str:
        source = (COMMON / "main" / "java" / "io" / "github" / "realyusufismail" / "bentenmod" / "omnitrix" / "OmnitrixService.java").read_text(encoding="utf-8")
        delay = re.search(r"long delay = ([0-9_]+)L \+ player\.getRandom\(\)\.nextInt\(([0-9_]+)\)", source)
        distance = re.search(r"int distance = ([0-9_]+) \+ player\.getRandom\(\)\.nextInt\(([0-9_]+)\)", source)
        if delay and distance:
            delay_low = int(delay.group(1).replace("_", ""))
            delay_high = delay_low + int(delay.group(2).replace("_", "")) - 1
            low_minutes = delay_low // 1200
            high_minutes = delay_high // 1200
            distance_low = int(distance.group(1).replace("_", ""))
            distance_high = distance_low + int(distance.group(2).replace("_", "")) - 1
            return f"The first watch bind schedules Kraab {low_minutes} to {high_minutes} game minutes later, at a valid spot {distance_low} to {distance_high} blocks away."
        return "Kraab starts hunting after the player first equips the Omnitrix."

    def render_bosses(self) -> str:
        cards = []
        for row in self.boss_rows:
            health = f"{row['health']:g} health" if row["health"] is not None else "Health not found in entity attributes"
            if row["slug"] == "crab":
                how = self.kraab_find_text()
            elif row["slug"] == "vilgax":
                how = "Craft the Vilgax Beacon in the Omnitrix Crafter. Use it on top of a solid block in the Overworld."
            elif row["slug"] == "zs_skayr":
                how = "Search the Null Void fortress. The fortress feature has a 1-in-400-chunks rarity filter."
            else:
                how = "Vilgax calls drones during phase two."
            cards.append(f'''<article class="boss-card" data-entity-id="{escaped(row['source_id'])}">
  <h3>{escaped(row['name'])}</h3>
  <p>{escaped(BOSS_ATTACKS[row['slug']])}</p>
  <p class="boss-stats"><strong>{escaped(health)}</strong>{f" · {row['damage']:g} attack damage" if row['damage'] is not None else ""}<br><strong>Drop:</strong> {escaped(self.boss_drop_text(row['drops']))}<br><strong>Find or summon:</strong> {escaped(how)}</p>
</article>''')
        drone_row = next(row for row in self.boss_rows if row["slug"] == "vilgax_drone")
        content = self.page_heading("Boss encounters", "Bosses", "Kraab hunts the watch, Vilgax answers a beacon, and Zs'Skayr waits in the Null Void.")
        content += self.image_panel("bosses.png", "Vilgax, Zs'Skayr, a Vilgax Drone and a Null Void Prisoner", "The composite illustration shows the Null Void encounter cast.")
        content += f'<section class="content-section"><div class="boss-grid">{"".join(cards)}</div></section>'
        content += f'''<section class="content-section casing casing-pad">
  <h2 class="section-heading">Vilgax Beacon</h2>
  <p class="article-copy">The Beacon recipe uses a Kraab Claw, Black Diamond, Infinitum and a Redstone Block. <a href="recipes.html#recipe-vilgax_beacon">View the recipe</a>. The summon starts after a short beacon animation. Vilgax's loot table lists a Vilgax Core, used with Infinitum to craft the Null Void Projector.</p>
  <p class="article-copy">Vilgax Drones have {drone_row['health']:g} health and {drone_row['damage']:g} attack damage. The Vilgax entity removes its own drones when it dies.</p>
</section>'''
        return self.page("Bosses", "Boss health, attacks, drops, and how to find or summon each encounter.", "bosses", content)

    def render_null_void(self) -> str:
        guide = self.lang.get("guide.bentenmod.chapter.null_void.body", "")
        fortress = self.placed.get("null_void_fortress", {})
        chance = next((part.get("chance") for part in fortress.get("placement", []) if part.get("type") == "minecraft:rarity_filter"), None)
        rarity = f"The fortress placement filter is 1 in {chance} chunks." if chance else "The fortress is a rare feature."
        prisoner_table = self.entity_loot.get("null_void_prisoner", {})
        prisoner_drops = self.loot_entries(prisoner_table)
        prisoner_drop_text = self.boss_drop_text(prisoner_drops)
        projector = next((row for row in self.recipe_rows if row["slug"] == "null_void_projector"), None)
        projector_recipe = f'<a href="recipes.html#recipe-{escaped(projector["slug"])}">Projector recipe</a>' if projector else "Projector recipe"
        content = self.page_heading("Beyond the portal", "The Null Void", "A dimension of floating islands, Null Void Stone, prisoners and a rare fortress.")
        content += self.image_panel("bosses.png", "The Null Void boss, Vilgax and an escaped prisoner", "Zs'Skayr appears in the Null Void fortress.")
        content += f"""<div class="article-layout content-section">
  <article class="article-copy">
    <p>{escaped(guide)}</p>
    <section class="content-section"><h2 class="section-heading">Getting there and back</h2>
      <p>Defeat Vilgax for a Vilgax Core, then combine one Core and two Infinitum in the Omnitrix Crafter to make the Null Void Projector. Use the item to travel in or return to your saved entry point. {projector_recipe}.</p>
      <p>The Projector has {self.projector_durability} durability and repairs with Null Crystals. The current implementation consumes one durability each time it teleports.</p>
    </section>
    <section class="content-section"><h2 class="section-heading">Islands and prisoners</h2>
      <p data-entity-id="bentenmod:null_void_prisoner">The dimension uses floating-island terrain with Null Void Stone and no fluids. Null Void Prisoners spawn there. Their current entity loot table lists: {escaped(prisoner_drop_text)}</p>
    </section>
    <section class="content-section"><h2 class="section-heading">Null crystals and the fortress</h2>
      <p>Null Crystal Ore generates in Null Void Stone. Null Crystals repair the Projector. {escaped(rarity)} The fortress contains loot chests and a persistent Zs'Skayr encounter.</p>
      <p>Zs'Skayr drops Master Control. Use it while the Omnitrix is equipped to unlock every alien and remove timeout and recharge.</p>
    </section>
  </article>
  <aside class="side-panel casing"><h2>Null Void route</h2><ul><li>Vilgax drops the Core.</li><li>Craft a Projector from the Core and two Infinitum.</li><li>Find a fortress and defeat Zs'Skayr.</li><li>Use Master Control to recalibrate the Omnitrix.</li></ul></aside>
</div>"""
        return self.page("The Null Void", "Travel to the Null Void, explore its islands and fortress, and find Zs'Skayr.", "null-void", content)

    def roadmap_rows(self) -> list[tuple[str, str, str]]:
        rows = []
        in_table = False
        for line in self.roadmap.splitlines():
            if line.strip().startswith("| Version | Name | Scope |"):
                in_table = True
                continue
            if not in_table:
                continue
            if not line.strip().startswith("|"):
                break
            cells = [cell.strip().replace("**", "") for cell in line.strip().strip("|").split("|")]
            if len(cells) < 3 or set(cells[0]) <= {"-", ":"}:
                continue
            if cells[0].startswith("2.0.0"):
                continue
            rows.append((cells[0], cells[1], cells[2]))
        return rows

    def render_roadmap(self) -> str:
        rows = self.roadmap_rows()
        items = "".join(f'<article class="roadmap-row"><div class="roadmap-version">{escaped(version)}</div><div><span class="planned-label">Planned, not in the game yet</span><h3>{escaped(name)}</h3><p>{escaped(scope)}</p></div></article>' for version, name, scope in rows)
        content = self.page_heading("Project plan", "Roadmap", "These entries come from the release plan. They are planned work, not features in the current game.")
        content += f'<div class="callout warning"><strong>Planned, not in the game yet.</strong> Release names and scope may change; the source roadmap does not promise dates beyond version 2.0.0.</div><section class="content-section roadmap-list">{items}</section>'
        return self.page("Roadmap", "Planned Ben 10 Mod releases and features from the project roadmap.", "roadmap", content)

    def prepare_output(self):
        marker = OUTPUT / ".wiki-generated"
        if OUTPUT.exists():
            if marker.exists():
                for child in OUTPUT.iterdir():
                    if child.is_dir():
                        shutil.rmtree(child)
                    else:
                        child.unlink()
            elif any(OUTPUT.iterdir()):
                raise RuntimeError("build/wiki exists and is not marked as wiki output; move it before building")
        OUTPUT.mkdir(parents=True, exist_ok=True)
        marker.write_text("Generated by tools/wiki/build.py\n", encoding="utf-8")
        for source_name in ("style.css", "site.js", "hourglass.svg", "hourglass-pattern.svg"):
            shutil.copy2(WIKI_SOURCE / source_name, OUTPUT / source_name)
        images = OUTPUT / "assets" / "images"
        images.mkdir(parents=True, exist_ok=True)
        for image in ("banner.png", "aliens.png", "bosses.png", "crafter.png"):
            shutil.copy2(ROOT / "docs" / "images" / image, images / image)
        (OUTPUT / ".nojekyll").write_text("", encoding="utf-8")

    def write(self, relative: str, content: str):
        path = OUTPUT / relative
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding="utf-8")

    def build(self):
        self.prepare_output()
        pages = {
            "index.html": self.render_home(),
            "getting-started.html": self.render_getting_started(),
            "omnitrix.html": self.render_omnitrix(),
            "aliens.html": self.render_aliens(),
            "items.html": self.render_items(),
            "recipes.html": self.render_recipes(),
            "ores-and-armour.html": self.render_ores_armour(),
            "bosses.html": self.render_bosses(),
            "null-void.html": self.render_null_void(),
            "roadmap.html": self.render_roadmap(),
        }
        for slug, data in sorted(self.aliens.items()):
            pages[f"aliens/{slug}.html"] = self.render_alien(slug, data)
        for relative, page in pages.items():
            self.write(relative, page)

        print(f"Built {len(pages)} pages in {OUTPUT.relative_to(ROOT)}")
        print(f"Included {len(self.aliens)} aliens, {len(self.item_rows)} items and blocks, {len(self.recipe_rows)} recipes, and {len(self.boss_rows)} boss entities.")


if __name__ == "__main__":
    WikiBuilder().build()
