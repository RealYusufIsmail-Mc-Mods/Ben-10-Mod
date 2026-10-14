#!/usr/bin/env python3
"""Check generated wiki references and source-data coverage."""

from __future__ import annotations

import html
import json
import re
import sys
from html.parser import HTMLParser
from pathlib import Path
from urllib.parse import unquote, urlsplit


ROOT = Path(__file__).resolve().parents[2]
SITE = ROOT / "build" / "wiki"
COMMON = ROOT / "common" / "src"
LANG = COMMON / "generated" / "resources" / "assets" / "bentenmod" / "lang" / "en_us.json"
ALIENS = COMMON / "main" / "resources" / "data" / "bentenmod" / "aliens"
RECIPES = COMMON / "generated" / "resources" / "data" / "bentenmod" / "recipes"
ENTITY_LOOT = COMMON / "generated" / "resources" / "data" / "bentenmod" / "loot_tables" / "entities"
CSS_URL = re.compile(r"url\(\s*(['\"]?)(.*?)\1\s*\)", re.IGNORECASE)


class PageParser(HTMLParser):
    def __init__(self):
        super().__init__(convert_charrefs=True)
        self.references: list[tuple[str, str, str]] = []
        self.anchors: set[str] = set()

    def handle_starttag(self, tag: str, attrs):
        values = dict(attrs)
        for attribute in ("href", "src", "poster"):
            value = values.get(attribute)
            if value:
                self.references.append((tag, attribute, value))
        anchor = values.get("id") or (values.get("name") if tag == "a" else None)
        if anchor:
            self.anchors.add(anchor)


def local_target(source: Path, reference: str, site_root: Path) -> tuple[Path | None, str]:
    parts = urlsplit(reference)
    if parts.scheme or parts.netloc or parts.path.startswith("//"):
        return None, ""
    path = unquote(parts.path)
    target = (source.parent / path).resolve() if path else source.resolve()
    try:
        target.relative_to(site_root.resolve())
    except ValueError:
        return None, parts.fragment
    if target.is_dir():
        target = target / "index.html"
    return target, unquote(parts.fragment)


def source_inventory() -> tuple[dict[str, set[str]], dict[str, set[str]]]:
    language = json.loads(LANG.read_text(encoding="utf-8"))
    expected = {
        "data-registry-id": {
            f"bentenmod:{key.removeprefix('item.bentenmod.')}"
            for key in language
            if key.startswith("item.bentenmod.")
        }
        | {
            f"bentenmod:{key.removeprefix('block.bentenmod.')}"
            for key in language
            if key.startswith("block.bentenmod.")
        },
        "data-alien-id": {f"bentenmod:{path.stem}" for path in ALIENS.glob("*.json")},
        "data-recipe-id": {f"bentenmod:{path.stem}" for path in RECIPES.glob("*.json")},
        "data-entity-id": {f"bentenmod:{path.stem}" for path in ENTITY_LOOT.glob("*.json")},
    }
    names = {
        "items and blocks": {
            language[key]
            for key in language
            if key.startswith(("item.bentenmod.", "block.bentenmod."))
        },
        "aliens": {
            value
            for key, value in language.items()
            if key.startswith("alien.bentenmod.")
        },
    }
    return expected, names


def check() -> int:
    if not SITE.is_dir():
        print("Missing build/wiki. Run python3 tools/wiki/build.py first.", file=sys.stderr)
        return 1

    errors: list[str] = []
    html_files = sorted(SITE.rglob("*.html"))
    parsers: dict[Path, PageParser] = {}
    local_links = 0
    image_references = 0
    for page in html_files:
        parser = PageParser()
        parser.feed(page.read_text(encoding="utf-8"))
        parsers[page.resolve()] = parser
        for tag, attribute, reference in parser.references:
            target, fragment = local_target(page, reference, SITE)
            if target is None:
                continue
            local_links += 1
            if tag == "img" or (attribute in ("src", "poster") and Path(urlsplit(reference).path).suffix.lower() in {".png", ".jpg", ".jpeg", ".webp", ".svg"}):
                image_references += 1
            if not target.is_file():
                errors.append(f"{page.relative_to(SITE)}: missing {attribute} target {reference}")
                continue
            if fragment and target.suffix.lower() in {".html", ".htm"}:
                target_parser = parsers.get(target.resolve())
                if target_parser is None:
                    target_parser = PageParser()
                    target_parser.feed(target.read_text(encoding="utf-8"))
                    parsers[target.resolve()] = target_parser
                if fragment not in target_parser.anchors:
                    errors.append(f"{page.relative_to(SITE)}: missing fragment #{fragment} in {target.relative_to(SITE)}")

    for stylesheet in SITE.rglob("*.css"):
        text = stylesheet.read_text(encoding="utf-8")
        for _, reference in CSS_URL.findall(text):
            target, fragment = local_target(stylesheet, reference, SITE)
            if target is None:
                continue
            local_links += 1
            image_references += 1
            if not target.is_file():
                errors.append(f"{stylesheet.relative_to(SITE)}: missing CSS asset {reference}")

    if not (SITE / ".nojekyll").is_file():
        errors.append("build/wiki/.nojekyll is missing")

    expected, names = source_inventory()
    all_html = "\n".join(page.read_text(encoding="utf-8") for page in html_files)
    for attribute, identifiers in expected.items():
        for identifier in sorted(identifiers):
            marker = f'{attribute}="{html.escape(identifier, quote=True)}"'
            if marker not in all_html:
                errors.append(f"Source entry not found in generated HTML: {identifier} ({attribute})")
    for group, values in names.items():
        for name in sorted(values):
            if html.escape(name, quote=True) not in all_html:
                errors.append(f"Language name not found in generated HTML: {name} ({group})")

    if errors:
        for error in errors:
            print(f"ERROR: {error}")
        print(f"Failed: {len(errors)} issue(s) across {len(html_files)} HTML pages.")
        return 1

    print(f"OK: {local_links} local links and asset references resolve, including {image_references} image paths.")
    print(
        "Coverage: "
        f"{len(expected['data-alien-id'])} aliens, "
        f"{len(expected['data-registry-id'])} items and blocks, "
        f"{len(expected['data-recipe-id'])} recipes, and "
        f"{len(expected['data-entity-id'])} entity loot entries."
    )
    print(f"Pages checked: {len(html_files)}")
    return 0


if __name__ == "__main__":
    raise SystemExit(check())
