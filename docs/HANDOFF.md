# LLM handoff log

Read this first when you start, resume, or come back from a context compaction. It records what has been done, what was decided, and what comes next, so work can continue without the original conversation. Keep it current: update it after every milestone (see "How to update" at the bottom). `CLAUDE.md` has the stable project rules; this file has the moving state.

## Current state (2026-10-13)

- **Branch:** `t3/omnitrix-transformation` (worktree `/Volumes/Yusuf's SSD/t3-worktrees/Ben-10-Mod/t3-ac0eaad0`; local, based on the pushed Crafter branch).
- **PRs:** [#88](https://github.com/RealYusufIsmail-Mc-Mods/Ben-10-Mod/pull/88) and [#89](https://github.com/RealYusufIsmail-Mc-Mods/Ben-10-Mod/pull/89) merged into `main`; [#90](https://github.com/RealYusufIsmail-Mc-Mods/Ben-10-Mod/pull/90) merged into `1.20.1`. The Step 3 branch targets `1.20.1`.
- **Version branches:** `1.19.4` holds the original `main` (fast-forwarded to `2a50371a`). Older versions have their own branches (`1.18.2`, `1.19.3`, ...). Naming convention: the branch is the Minecraft version.
- **Builds:** Forge and Fabric jars from one codebase (`./gradlew build`).
- **Tests:** 11/11 GameTests pass on both loaders (`:forge:runGameTestServer`, `:fabric:runGameTestServer`); `./gradlew build` passes after the final animation-state fix.
- **Client check:** both `runClient` tasks registered the mod, initialized graphics/audio, and reloaded resources without a crash or mixin error, then were stopped after 75 seconds. A transformed player, dial, and Crafter have not been visually inspected in a live world. Forge jar on NeoForge 1.20.1 remains unverified.
- **Current work:** Step 3 is implemented on `t3/omnitrix-transformation`. The progression decisions below remain open and were not changed.

## Done (newest first)

### 2026-10-13: Step 3, Omnitrix transformation
- Added a GeckoLib `omnitrix_watch` item, dial screen and keybind, local green flash, and third-person alien player replacement renderer in `common/src/main/java/.../core`, `.../client/alien`, and `.../omnitrix`.
- Added server-authoritative saved unlocks, transformation, revert and timeout, plus JSON-defined alien dimensions and abilities. Forge and Fabric now sync owners/trackers and refresh hitboxes across login, tracking, dimension changes, and respawn. Tracker packets omit the owner's unlock list; the client cache clears on disconnect.
- Generated the watch item model and translations. Added shared GameTests for watch/unlock validation, dimensions/abilities, timeout/revert, and independent animation state for two transformed players, with wrappers on both loaders.
- Verified `:forge:runData`, `./gradlew build`, and 11/11 GameTests on each loader. Both client startup checks reached resource reload without a crash or mixin error before the final animation-state fix. First-person hands still use vanilla rendering, and transformed-player visuals were not checked in a live world.

### 2026-10-13: Omnitrix Crafter visual redesign
- Replaced the cube model with an eight-element console with an armored base, corner pylons, and raised dial (`models/block/omnitrix_crafter_device.json`). The Forge data provider now points the generated block and item model at it.
- Added `tools/crafter-assets/generate.py` for the block, GUI, recipe-button pixel art, and `docs/omnitrix-crafter-preview.png`, with the command documented in `docs/assets.md`. The screen now uses those assets instead of Minecraft's crafting-table textures and draws readable labels on the dark panel.
- Pushed commit `33f231a9` and opened PR #90 stacked on #89. Verified: `:forge:runData`, `build`, and both GameTest servers (8/8 each). Forge and Fabric clients reached resource loading. Visual inspection inside the game is still pending.

### 2026-10-13: Handoff follow-up PR
- PR #88 merged. Opened PR #89 for the four-file handoff commit `f46b1be5`; no game code or assets are in that PR.
- The user requested a more distinct, space-themed Ben 10 Omnitrix Crafter appearance. Visual work has not started.

### 2026-10-13: PR, version branch
- Opened PR #88 with three commits: port + multi-loader + data-driven aliens; 3D assets + generator; docs + skills.
- Fixed `.github/workflows/releases.yaml` (was calling a non-existent `shadowJar`; now uploads both loader jars).

### 2026-10-13: Step 2, data-driven aliens and abilities
- Ability system in `common/.../ability/`: types `effect`, `flight`, `attribute`, `no_fall_damage`; `AbilityTicker` starts/ticks/stops per player. Codec dispatch lives in `AbilityTypes.ABILITY_CODEC`, *not* on the `Ability` interface (class-init cycle).
- `CodecJsonReloadListener` loads `data/<ns>/aliens/*.json` and `data/<ns>/armor_sets/*.json`. Reference: `docs/data-aliens-and-abilities.md`.
- 10 alien JSONs (3 starters: Heatblast, Four Arms, XLR8). 5 armour-set JSONs replace 5 armour classes (items are plain `ArmorItem` now, ids unchanged). Armour values copy the old code exactly, only bugs fixed.
- `client/alien/AlienGeoModel` resolves geo/texture/animation paths by alien id. Common compiles against GeckoLib via `modCompileOnly` of the Forge artifact.
- GameTests use loader fake players (`FakePlayerFactory` / Fabric `FakePlayer`): vanilla's mock server player crashes on Forge and reports creative mode on Fabric.
- Wrote `docs/design/progression.md` (proposal only).

### 2026-10-13: Step 1, multi-loader layout
- `common/` (vanilla-only), `forge/` (ModDevGradle legacyforge 2.0.77), `fabric/` (Loom 1.9), `buildSrc/` conventions from MultiLoader-Template 1.20.1, Gradle 8.11.
- `RegistrationProvider`/`RegistryObject` + `IPlatformHelper` (ServiceLoader). Forge-only: Omnitrix Crafter recipe book; Fabric hides the button.
- Forge data run outputs to `common/src/generated/resources`, shipped in both jars.
- Fixed: duplicate creative-tab entries; ores never added to biomes (Forge biome modifiers + Fabric `BiomeModifications`).

### 2026-10-13: 1.19.4 → 1.20.1 port
- Forge 47.4.28, GeckoLib 4.8.4, Parchment 2023.09.03. Removed `realyusufismailcore` (user: "just a hassle"), Lombok, Logback.
- Fixed a dedicated-server crash (client classes in the mod class). Details: `docs/porting-1.20.1.md`.

### 2026-10-13: 3D assets
- `tools/alien-gen/` generates the 10 original aliens and the Omnitrix as GeckoLib models, baked textures (AO, bevels), glow masks, animations, dial icons and the flash. Docs: `docs/assets.md`. Preview renders go to `tools/alien-gen/out/` (gitignored).

## Decisions (with the user)

| Decision | Choice |
|---|---|
| Minecraft version | 1.20.1 |
| Loaders | Forge (jar also meant for NeoForge 1.20.1) + Fabric, one codebase |
| Shared-code approach | MultiLoader-Template + small platform interfaces; no Architectury API dependency |
| Alien definitions | JSON |
| Core library | removed for good in this mod; may revisit for other MC projects |
| Model routing | Opus does the Minecraft modding itself; Sonnet subagents apply bulk edits; Haiku scouts; Codex Luna max only for big detached jobs when asked |
| Git | branch per Minecraft version; PRs to `main`; commit/push only when asked |

## Open questions for the user

From `docs/design/progression.md`:
1. Getting the Omnitrix: crash-site pod, crafting, or both?
2. Keep the alien armour sets once transformation exists, or retire them?
3. DNA scanning creature list (wolf → Wildmutt, guardian → Ripjaws, ...)?
4. Apply the proposed armour balance (current XLR8 is Speed XLVI, Four Arms Strength IX)?
5. Null Void as the first dimension?

## Next

**Omnitrix Crafter visuals:** inspect the new block and screen in-game on both loaders. Adjust the pixel art and model in a follow-up if needed.

**Step 3 follow-up:** inspect the dial and transformed player in a live world on both loaders; replace the first-person vanilla hands if needed. Confirm NeoForge 1.20.1 compatibility. Commit/push only when asked.

After that, the progression plan steps 4 to 7 in `docs/design/progression.md`.

## Gotchas

- Build with JDK 21 and an in-repo Gradle home: `export JAVA_HOME="$(/usr/libexec/java_home -v 21)" GRADLE_USER_HOME="$PWD/.gradle/user-home"`. The default JDK 25 can't run Gradle 8.11.
- `common` can't see Forge/Fabric classes. Vanilla equivalents: `TagKey.create` (not `BlockTags.create`); anonymous `RecipeType` (not `RecipeType.simple`); `inventoryTick` (not `onArmorTick`); subclass protected tool constructors (`ModTools`).
- GeckoLib's Fabric build needs `com.eliotlash.mclib` from the GeckoLib Maven (allowed in `buildSrc/.../multiloader-common.gradle`).
- Fabric's GameTest server refuses to start with zero tests.
- An old `run/` world can report "unidentified mappings" after items are removed; delete the GameTest world.
- `spotlessApply` runs before every compile and reformats Java.

## How to update this file

- After each milestone (a step done, a PR opened, a decision made, a blocker found), add a dated entry at the top of **Done** with what changed, where (paths), and how it was verified. Update **Current state**, **Open questions** and **Next**.
- Record facts, not intentions: commands that passed, commit hashes, PR numbers. If something wasn't verified, say so.
- Keep it under about 250 lines: fold old entries into one-line summaries once they're merged.
- Durable rules belong in `CLAUDE.md`, data formats in `docs/`, this file holds the moving state.
