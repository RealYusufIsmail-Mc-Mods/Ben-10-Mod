# LLM handoff log

Read this first when you start, resume, or come back from a context compaction. It records what has been done, what was decided, and what comes next, so work can continue without the original conversation. Keep it current: update it after every milestone (see "How to update" at the bottom). `CLAUDE.md` has the stable project rules; this file has the moving state.

## Current state (2026-10-13)

- **Branch:** `1.20.1` (worktree `/Volumes/Yusuf's SSD/t3-worktrees/Ben-10-Mod/t3-ac0eaad0`).
- **Open PR:** [#88](https://github.com/RealYusufIsmail-Mc-Mods/Ben-10-Mod/pull/88) `1.20.1` → `main`. CI was pending when last checked; `spotlessCheck test build` pass locally.
- **Version branches:** `1.19.4` holds the original `main` (fast-forwarded to `2a50371a`). Older versions have their own branches (`1.18.2`, `1.19.3`, ...). Naming convention: the branch is the Minecraft version.
- **Builds:** Forge and Fabric jars from one codebase (`./gradlew build`).
- **Tests:** 8/8 GameTests pass on both loaders (`:forge:runGameTestServer`, `:fabric:runGameTestServer`).
- **Not verified:** `runClient` on either loader; Forge jar on NeoForge 1.20.1.
- **Next step:** step 3, the Omnitrix and transformation (see "Next").

## Done (newest first)

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

**Step 3, Omnitrix + transformation** (doesn't depend on the open questions):
- Omnitrix item (the existing `omnitrix_watch` id, or a new one) with the 3D model (`geo/item/omnitrix.geo.json`, `textures/item/omnitrix_device*.png`) via GeckoLib `GeoItem`.
- Per-player transformation state: current alien, unlocked aliens, timeout. Needs saving and syncing to clients, so a small networking/persistence piece in `IPlatformHelper` (Forge capabilities or attachments vs Fabric components or player NBT).
- Dial screen (`textures/gui/omnitrix.png`: dial at 0,0 128×128; frame at 128,0 40×40; icons at 0,128, 32×32 each) and a keybind.
- Rendering the player as the alien (GeckoLib replaced-entity renderer + `AlienGeoModel`), hitbox from `AlienDefinition`, the `transform` animation and the `misc/omnitrix_flash.png` overlay.
- Abilities: make the active alien a source in `AbilityTicker.currentSources`.
- New GameTests for transform/untransform/timeout; then a `runClient` check on both loaders (ask the user).

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
