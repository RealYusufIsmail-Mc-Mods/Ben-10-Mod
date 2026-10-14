# LLM handoff log

Read this first when you start, resume, or come back from a context compaction. It records what has been done, what was decided, and what comes next, so work can continue without the original conversation. Keep it current: update it after every milestone (see "How to update" at the bottom). `CLAUDE.md` has the stable project rules; this file has the moving state.

## Current state (2026-10-14)

- **Branch:** `t3/whats-next` (worktree `/Volumes/Yusuf's SSD/t3-worktrees/Ben-10-Mod/t3-4856cce5`, based on `origin/1.20.1` at `3c6ac47c`). Everything below is uncommitted; nothing pushed. Helper worktrees `progression-luna` (`t3/omnitrix-acquisition-dna`) and `attack-anim-sonnet` (`t3/alien-attack-anim`) hold the worker copies already merged here.
- **PRs:** #88, #89 merged into `main`; #90 and #91 merged into `1.20.1`.
- **Version branches:** `1.19.4` holds the original `main`; older versions have their own branches. The branch is the Minecraft version.
- **Tests:** `:forge:runData`, `./gradlew build`, and 21/21 GameTests on both `:forge:runGameTestServer` and `:fabric:runGameTestServer` pass on the merged tree.
- **Client check:** both clients start, reload resources and start the sound engine with no mixin or render-layer error. In-world visuals (first-person arms, wheel dial, holograms, attack swing, wrist watch, crater) are NOT yet checked by a person. NeoForge 1.20.1 unverified.

## Done (newest first)

### 2026-10-14: Progression steps 4 and 5, equip, wheel dial, arms, attack swing
- **First-person alien arms:** `AlienPlayerReplacementRenderer.renderFirstPersonArm` draws the alien's `right_arm`/`left_arm` (+ `*_arm_lower` for Four Arms) bones at rest pose, shoulder aligned to vanilla's. Hooks: Forge `RenderArmEvent`; Fabric `mixin/PlayerRendererMixin` (`renderRightHand`/`renderLeftHand`).
- **Attack swing:** every alien has `animation.<id>.attack` (0.3 s, from `tools/alien-gen/export.py`); `AlienPlayerAnimatable` plays it on a second controller while `player.swinging`.
- **Wheel dial:** `OmnitrixDialScreen` rewritten: turning bezel (scroll, arrows/A-D), icons on a rotating ring, selected alien pops up as a green spinning 3D hologram (`client/alien/AlienHologram`, a `GeoObjectRenderer`). Click/Enter transforms, R or right-click reverts. Opens only when equipped. Texture from `tools/alien-gen/dial.py` → `textures/gui/omnitrix_wheel.png`.
- **Equip:** right-click the watch binds it for good (`equipped` in `OmnitrixSavedData`, synced to owner and trackers); empty-hand right-click on air opens the dial; `G` still works; wrist render layer on both loaders.
- **Acquisition and DNA (steps 4-5):** procedural crash-site crater with `omnitrix_pod` (drops the watch); Crafter recipe chain (`legendary_casing`, `black_diamond_lens`, `omnitrix_core` → watch) and Ben 10 gear moved into the Crafter; `dna_sources` in alien JSON; sneak + empty-hand right-click a source to unlock (`OmnitrixInteractions`, cooldown-gated).
- **Balance and ore ladder:** armour nerfed (XLR8 Speed III; Four Arms Strength II + Jump II; Heatblast fire res + flight; Legendary Strength I + Resistance II; jacket Resistance I). Ore rarity follows power (`ModOreFeatures`, `ModOrePlacements`; numbers in `docs/design/progression.md` §1). Fixed deepslate ruby/black diamond silk-touch loot.
- **Tests:** GameTest players now get a random UUID per run (the persisted GameTest world leaked Omnitrix state between runs). Scans use `isShiftKeyDown()`, not pose-based `isCrouching()`.
- **Review:** Sol (read-only) found nothing high-severity; fixed its dial-on-block and missing-cooldown findings. Crater writes stay within the 3x3 feature region.
- Routing: Opus (arms, dial, integration), Sonnet (balance/ore data, attack anims), Haiku (scout, diff check), Luna max (steps 4-5, equip), Sol medium (review).

### 2026-10-13: Step 3, Omnitrix transformation
- Added a GeckoLib `omnitrix_watch` item, dial screen and keybind, local green flash, and third-person alien player replacement renderer in `common/src/main/java/.../core`, `.../client/alien`, and `.../omnitrix`.
- Added server-authoritative saved unlocks, transformation, revert and timeout, plus JSON-defined alien dimensions and abilities. Forge and Fabric now sync owners/trackers and refresh hitboxes across login, tracking, dimension changes, and respawn. Tracker packets omit the owner's unlock list; the client cache clears on disconnect.
- Generated the watch item model and translations. Added shared GameTests for watch/unlock validation, dimensions/abilities, timeout/revert, and independent animation state for two transformed players, with wrappers on both loaders.
- Pushed implementation commit `891fc1c9` and opened PR #91 against `1.20.1`. Verified `:forge:runData`, `./gradlew build`, and 11/11 GameTests on each loader. Both client startup checks reached resource reload without a crash or mixin error before the final animation-state fix. First-person hands still use vanilla rendering, and transformed-player visuals were not checked in a live world.

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
| Omnitrix acquisition | Both the rare crash site and crafting give the same `omnitrix_watch` item |
| Equipping | Right-click the watch once to bind it permanently; it survives death and cannot be unequipped |
| Alien armour | Keep the sets for non-transformed players, nerfed (2026-10-14) |
| DNA unlocks | With the watch equipped, sneak and right-click a source with an empty main hand: wolf, budding amethyst, frog, bee, guardian, iron golem or phantom |
| Later progression | Null Void is the first dimension, built as endgame after the ore ladder and DNA unlocks; bosses (Kraab, Vilgax) come first |

## Next

1. In-game QA on both loaders (needs a person): first-person arms alignment, the wheel dial and hologram, the attack swing direction (flip the sign in `export.py` if inverted), wrist watch placement, crater look and rarity, new item textures.
2. Commit this branch and open a PR to `1.20.1` when the user asks.
3. Step 6: Kraab mini-boss, then Vilgax. Step 7: Null Void dimension + Zs'Skayr (`docs/design/progression.md` §4-5).

## Gotchas

- Build with JDK 21 and an in-repo Gradle home: `export JAVA_HOME="$(/usr/libexec/java_home -v 21)" GRADLE_USER_HOME="$PWD/.gradle/user-home"`. The default JDK 25 can't run Gradle 8.11.
- `common` can't see Forge/Fabric classes. Vanilla equivalents: `TagKey.create` (not `BlockTags.create`); anonymous `RecipeType` (not `RecipeType.simple`); `inventoryTick` (not `onArmorTick`); subclass protected tool constructors (`ModTools`).
- GeckoLib's Fabric build needs `com.eliotlash.mclib` from the GeckoLib Maven (allowed in `buildSrc/.../multiloader-common.gradle`).
- Fabric's GameTest server refuses to start with zero tests.
- GameTest saved data persists in `run/`; give test players fresh UUIDs (done in both wrappers).
- An old `run/` world can report "unidentified mappings" after items are removed; delete the GameTest world.
- `spotlessApply` runs before every compile and reformats Java.

## How to update this file

- After each milestone (a step done, a PR opened, a decision made, a blocker found), add a dated entry at the top of **Done** with what changed, where (paths), and how it was verified. Update **Current state**, **Open questions** and **Next**.
- Record facts, not intentions: commands that passed, commit hashes, PR numbers. If something wasn't verified, say so.
- Keep it under about 250 lines: fold old entries into one-line summaries once they're merged.
- Durable rules belong in `CLAUDE.md`, data formats in `docs/`, this file holds the moving state.
