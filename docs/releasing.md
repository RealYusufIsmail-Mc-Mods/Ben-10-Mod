# Releasing a version

Follow these steps in order for every release. The scope of each version comes from the release plan in
[design/roadmap.md](design/roadmap.md).

## Scope
- Build what the roadmap row for the version says. A new idea goes into the roadmap row for a future version; it
  doesn't get added to the current release.
- Bugs the user finds while playtesting are fixed in the version being built.

## Before release
1. Merge every PR for the version into `1.20.1`.
2. Run `./gradlew :forge:runData && ./gradlew build && ./gradlew :forge:runGameTestServer && ./gradlew :fabric:runGameTestServer`.
   Both GameTest servers must report `All N required tests passed`.
3. The user checks the version in the game on both loaders.
4. Set `version` in `gradle.properties`, and replace `Unreleased` with the release date in the version's
   `CHANGELOG.md` heading.
5. **Wiki and roadmap:**
   - In `docs/design/roadmap.md`, set the released row's status to `Released <date>`.
   - Set the next version's status to `In progress`.
   - Make sure every row's scope says what actually shipped.
   - Run `python3 tools/wiki/build.py && python3 tools/wiki/check_links.py`.
6. Open a release PR with steps 4 and 5, and merge it into `1.20.1`. That push republishes the wiki through
   `.github/workflows/wiki.yaml`.

## Release
7. Upload to CurseForge. The token is in `CURSEFORGE_TOKEN`, which is set in `~/.zshenv`:
   `zsh -c './gradlew :forge:publishCurseForge :fabric:publishCurseForge'` (use the JDK 21 environment from `CLAUDE.md`).
8. Tag `v1.20.1-<version>` and push the tag. The release workflow attaches both jars to a GitHub release.

## After release
9. Check that the wiki workflow run succeeded and that the live roadmap
   (https://realyusufismail-mc-mods.github.io/Ben-10-Mod/roadmap.html) shows the new status.
10. Check that the files appear on CurseForge. Approval can take a while.
11. Add a dated entry to `docs/HANDOFF.md`.
