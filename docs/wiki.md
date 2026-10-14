# Building the player wiki

The player wiki is generated from the mod's language file, alien definitions, recipes, loot tables, world-generation data, armour sets, release notes and mechanics code. Its source CSS, JavaScript and Omnitrix marks live in the `wiki` directory. Generated files go to `build/wiki`, which is ignored by Git.

From the repository root, install Python 3.12, Pillow and NumPy, then run:

    pip install pillow numpy
    python3 tools/wiki/build.py
    python3 tools/wiki/check_links.py

The builder uses Python's standard library and Pillow. It copies existing alien renders from `tools/alien-gen/out` when present; it does not generate new renders. The workflow installs NumPy for the existing preview tools if render generation is added later.

The GitHub Actions workflow rebuilds the site when wiki sources, images, mod data or the changelog change on `1.20.1`. It publishes `build/wiki` to the `gh-pages` branch. Enable GitHub Pages to serve that branch at the project URL.
