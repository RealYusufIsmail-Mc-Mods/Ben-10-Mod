# LLM handoff log

Read this first when you start, resume, or come back from a context compaction. It records what has been done, what was decided, and what comes next, so work can continue without the original conversation. Keep it current: update it after every milestone (see "How to update" at the bottom). `CLAUDE.md` has the stable project rules; this file has the moving state.

## Current state (2026-10-15)

- **Branch:** `t3/2.1.0-playtest` (worktree `/Volumes/Yusuf's SSD/t3-worktrees/Ben-10-Mod/t3-74735a92`), based on 2.1.0 plus the cleanup pass.
- **PR history:** #88 and #89 merged into `main`; #90-#93 merged into `1.20.1`; #96 and #97 were 2.0.0 release prep.
- **Version branches:** `1.19.4` holds the original `main`; older versions have their own branches. The branch is the Minecraft version.
- **Release 2.0.0: released 2026-10-14.** Uploaded to CurseForge (Forge and Fabric files) and tagged `v1.20.1-2.0.0` (GitHub release workflow). `publishCurseForge` reads `CURSEFORGE_TOKEN` from the user's `~/.zshenv`, so run it through `zsh -c`; uploads need the `Client`/`Server` game versions or CurseForge rejects them (error 1021).
- **Client check:** no `runClient` for steps 4-7. In-world visuals still need a person's review on both loaders; NeoForge 1.20.1 is unverified.
- **2.1.0 gameplay:** implementation and art are integrated. The cleanup pass below is complete. The root's playtest run passed `:forge:runData` and `build`; Forge GameTests reported six failures, now addressed in shared test checks. Retesting is pending; Fabric has not run. No game was run.

## Done (newest first)

### 2026-10-15: Released 2.0.1
- Hotfix from tag `v1.20.1-2.0.0` on branch `release/2.0.1`: `flying_speed` for Zs'Skayr and Vilgax drones (FlyingMoveControl crashed without it). Forge 45/45, Fabric 43/43.
- Uploaded to CurseForge (Forge + Fabric) and GitHub release `v1.20.1-2.0.1` created with both jars (the 2.0.0-based tree still has the old RELEASE_TOKEN workflow, so the release was made with `gh`).
- Roadmap row and changelog section added on `1.20.1`; wiki rebuilt.

### 2026-10-15: Fix six failed 2.1.0 playtest GameTests
- Corrected test setup in `common/src/main/java/io/github/realyusufismail/bentenmod/gametest/GameTestChecks.java`: positions now use the GameTest structure origin, projector checks select their own portal, and the round-trip fake player is registered in the level and transferred through normal scheduled ticks.
- The failures were test issues. Portal pull and boss-resistance tests had moved helper-spawned portals to absolute coordinates; the Four Arms player was likewise outside its helper-spawned Vilgax's structure-relative radius. The Plumber Bolt test now clears the Zombie's armour before asserting six effective damage. Boss resistance coverage includes Vilgax, Zs'Skayr, the prison warden and Kraab (`CrabEntity`). No game code changed.
- Verification: `git diff --check` passed. Did not run Gradle or the game; root verification is pending.

### 2026-10-15: Playtest follow-up — alien camera, effects, powers and portals
- Added shared pose-aware camera height scaling for transformed players, client-side transformation bursts and third-person morphs, held-power repeat, cooldown HUD sync, and tuned cooldowns/damage. Heatblast now hits fire-immune bosses with non-fire damage.
- Added the fixed six-damage Plumber energy bolt, namespaced armour material paths and relocated legacy layer textures, plus shared tests for camera height, boss damage, bolt damage and armour resources.
- The Null Void Projector now spawns a walkable portal with pull, boss resistance, lifetime, travel effects, wider island search, a lit fallback pad, and arrival hints. Added boss chatter and shared tests for portal behavior and chatter cooldown; Forge retains the dimension round trip and adds a landing-surface test.
- Added generated bolt and portal textures and documented them in `docs/assets.md`. Updated power cooldown docs and the 2.1.0 changelog. The requested `git mv` was blocked by worktree index permissions; textures were moved inside the writable workspace and Git can detect them as renames.
- Validation: not run (sandbox). Root should run Forge `runData`, `build` and both GameTest servers. Do not run the game; in-world camera, morph, particle, sound and portal visuals still need review.

### 2026-10-15: Cleanup and modernisation
- Removed dead `EntitySpawingInit`/`OmnitrixScreen` classes, obsolete commented smithing recipes and a commented superclass call, two stale TODOs, and tracked root runtime logs. Removed unused copper/emerald block textures, the obsolete Infinitum ore front texture, four replaced Crafter/recipe-book GUI textures, unused backpack/Omnitrix glowmask/copper/Vilgax spawn-egg item textures, and the legacy Vilgax texture path. Kept wiki `placeholder.png`, source `.bbmodel` files, entity glow masks and `OmnitrixPowerService` dispatch data because they still serve tooling/rendering/data use or are ambiguous.
- Moved client screens under `client`, `PlumberArmorMaterial` under `core/material`, and advancement sources into the correctly spelled `data/advancements` package. Corrected the Crafter recipe class name and private typos (`toghness`, `creterionId`, `OMNTRIX` constants); behavior and IDs are unchanged.
- Simplified item model provider classification, modernized recipe category and target pattern matching, removed the unused common Sponge/Mixin dependency and its repositories (Fabric mixin classes come from `fabric-loader`), and removed unused imports. Generated resources were not edited.
- Static reference, legacy-name and import checks passed; `git diff --check` passed. Forge `runData`, builds and both GameTest servers remain pending root verification.

### 2026-10-14: 2.1.0 integrated and verified
- Merged three workstreams: Luna (gameplay), Sonnet (art in `b10-art`: three GeckoLib mobs, items, blocks, Plumber armour layers, `fly` animations for Heatblast/Stinkfly/Ghostfreak, `AlienFlight`/`AlienFlightEffects`), Haiku (#99 balance in `b10-balance`).
- Root fixes: Luna's compile errors (`levelgen.feature.Feature`, `loot.parameters.LootContextParamSets`, `SpawnerBlockEntity.setEntityId(type, random)`, `SoundEvents.BLAZE_SHOOT` is not a holder); Plumber leggings recipe didn't use Taydenite; armour durability now uses vanilla's base table (11/16/15/13 x multiplier) in `CustomArmorMaterial` and `PlumberArmorMaterial` (was multiplier x defense, about half of vanilla).
- Verified: `runData`, `build`, Forge 51/51, Fabric 49/49; wiki builds (115 items, 116 recipes) and links check.
- Not seen in game: new mobs and their glow/translucency, flight animation direction, Heatblast flame trail, Plumber armour on the player model, glider feel, prison and outpost structures.

### 2026-10-14: 2.1.0 Living Null Void gameplay implementation
- Added prisoner pack/flank/flee behavior, neutral tractor-beam Null Guardians, Lepidopterran slime spitters and the persistent prison warden. Added rare floating prison and Plumber outpost features, Taydenite ore, the key-locked prison vault, Plumber equipment, loot, recipes, tags, translations and render registration on Forge/Fabric.
- Added shared GameTests and both loader wrappers for mob attributes, drops, vault use, glider, blaster recipe, Taydenite drops, structure features and the warden summon cap. Updated `tools/wiki/build.py` and marked Phase A gameplay complete; Plumber missions remain deferred.
- Validation: not run (sandbox). Root should run data generation, builds and both GameTest servers after integrating the art worker's assets. Do not launch `runClient` for this task.

### 2026-10-14: Release prep for 2.0.0
- Fixed: dying while transformed kept the alien form (`OmnitrixService.onRespawn`, called from both loaders' respawn events; returning from the End keeps the form). GameTest `deathRevertsToHuman`.
- Fixed: the four new GeckoLib renderers passed `entity/<id>` to `DefaultedEntityGeoModel`, which adds `entity/` itself; any of the new mobs crashed the client when seen. GameTests can't catch this (no rendering).
- Fixed Luna's compile errors: `ClipContext` is in `net.minecraft.world.level`, Haste is `MobEffects.DIG_SPEED`, `SoundEvents.NOTE_BLOCK_PLING` is a `Holder` (`.value()`).
- Sonnet: new creative-tab icon, guide book textures, wrist-mounted Omnitrix model (`OmnitrixWristLayer`, code-built `ModelPart`, glowing dial), generator `tools/ui-assets/generate.py`.
- Version 2.0.0; `publishCurseForge` uploads only the newest changelog section with a display name; token from the `curseforge.token` property or `CURSEFORGE_TOKEN`.
- Page: new README, `docs/curseforge/description.md` (paste into CurseForge; images load from the `1.20.1` branch after merge), images in `docs/images/` from `tools/page-assets/generate.py`.
- Plans: `docs/design/roadmap.md` (release plan 2.0.0 to 5.1.0, Null Void, Diagon, Alien X, Azmuth and the watch upgrades). User decisions: Diagon in its own rift dimension, Plumber NPC missions.
- Verified: `runData`, `build`, Forge 45/45, Fabric 43/43. Not seen in game yet: powers, HUD timer, guide screen, wrist model, icon.

### 2026-10-14: QA round 1 implementation (verified in the entry above)
- Added dispatched JSON active-power definitions for all ten aliens, server-side power execution/cooldowns, safe projectile and AoE targeting, and V/H keybind packets on Forge and Fabric. Creative snapshots expose every alien without saving unlocks.
- Timeout now starts the configured recharge period; snapshots sync its end time. Added the countdown HUD, final-ten-second beeps, timeout flash, recharge refusal and Master Control bypass.
- Added the Omnitrix Field Guide item, first-join delivery flag, vanilla shapeless recipe, client page screen, generated language/model entries and Master Control tooltip.
- Added shared GameTests with wrappers on both loaders for power parsing/use/cooldown, creative transforms, timeout/recharge, Master Control and one-time guide delivery. Updated the alien data reference and progression decisions.
- Validation: not run (sandbox). Next run `:forge:runData`, both builds and both GameTest servers; art files remain owned by the other worker.

### 2026-10-14: Steps 6 and 7 verified, review fixes, PR
- Root fixes so the chain passes: `public` save-data overrides on Vilgax/drone; `getHeightmapPos` for hunter spawns; projector uses `durability(16)` without `stacksTo` (Item.Properties rejects both); no loot table for `crab_bolt` (not a living entity); biome uses `has_precipitation`.
- Sol review (read-only) found three issues, fixed by Haiku: Zs'Skayr fired exploding wither skulls (now `CrabBoltEntity`, damage 8); the projector could return players into blocks (`isSafeReturn`/`findUpwardReturn` with heightmap fallback); prisoner summons were uncapped (max 6 within 32 blocks). Fortress rarity is now 1 in 400 chunks.
- Fabric GameTests can't load datapack dimensions (vanilla's GameTestServer builds its world from an empty LevelStem registry), so Fabric checks `nullVoidWorldgenDataLoaded` (dimension type, biome, noise settings registered) instead of the dimension and projector round trip; Forge runs all three.
- Art (Sonnet): `tools/boss-assets/generate.py` and `tools/alien-gen/creatures.py` generate Vilgax, drone, Zs'Skayr and prisoner GeckoLib models/animations/textures plus the new item and block textures.
- Verified: `:forge:runData`, `./gradlew build`, Forge 37/37, Fabric 35/35. No client run.
- Routing: Luna max (steps 6-7 code), Sonnet (art), Haiku (brief check, review fixes), Sol medium (review), Opus (integration, build fixes).

### 2026-10-14: Progression step 6 implementation
- Upgraded `CrabEntity` (id `crab`) to an 80-health mini-boss with armour, knockback resistance, boss bar, close melee and a `RangedAttackGoal` firing damaging `crab_bolt`s. Hunter scheduling is stored with the Omnitrix player's saved data, starts on first bind, retries after peaceful/no-safe-position cases, and marks a successful spawn as one-shot; hunters are persistent.
- Added `VilgaxEntity` and flying/dashing `VilgaxDroneEntity` with GeckoLib controllers, phase-synced boss health logic, slam, capped drone waves, boss bars and the Vilgax glow renderer. Added the Overworld `vilgax_beacon` use flow with a 3-second delayed reveal.
- Registered entities, attributes, spawn eggs and items across both loaders; added Forge provider entries for item models, language, boss loot and the Crafter beacon recipe. Added shared GameTests and wrappers for attributes, phase switch, loot, recipe and hunter schedule persistence. Updated progression decisions: beacon only (ship deferred), Kraab claw as an ingredient.
- Verified later by the root (see the entry above).

### 2026-10-14: Progression step 7 implementation and step 6 review fixes
- Added the data-driven Null Void dimension type, dimension, End-island noise settings and prisoner biome, plus Null Void Stone, bricks, crystal ore generation, and a bounded rare fortress feature with loot chests and one persistent Zs'Skayr. Added the 500-health flying boss, Null Void Prisoner, both GeckoLib renderers, projector return-point storage and travel, and Master Control unlock/timeout state synced through the Omnitrix snapshot.
- Added Forge data-provider entries for models, language, block tags, ore and entity loot, fortress chest loot, and the stone brick/projector recipes. Added nine shared GameTests with Forge and Fabric wrappers, including fortress loot and the previously equipped player migration. Updated progression decisions and this handoff.
- Fixed the review findings in step 6: Vilgax persists through Peaceful and beacon summoning, its synced phase drives summon animation and slams skip Vilgax drones, Kraab clears its boss bar on death/removal, and login schedules a missing Kraab hunter for previously equipped players.
- Verified later by the root (see the newest entry).

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
| Step 6 summon route | Crafted Overworld beacon only; Chimera ship structure deferred; Kraab claw is a beacon ingredient (2026-10-14) |
| Step 7 Null Void | End-style floating islands, projector return point per player, 1/400-chunk fortress, permanent Master Control timeout removal (2026-10-14) |

## Next

1. Rerun Forge GameTests after the test fixes; then run Fabric GameTests. `runData` and `build` passed before these test-only changes.
2. Consider Modrinth publishing for Fabric update notices.

## Open questions

- Do the corrected Forge GameTests pass, and what does the Fabric GameTest suite report?

## Gotchas

- Build with JDK 21 and an in-repo Gradle home: `export JAVA_HOME="$(/usr/libexec/java_home -v 21)" GRADLE_USER_HOME="$PWD/.gradle/user-home"`. The default JDK 25 can't run Gradle 8.11.
- `common` can't see Forge/Fabric classes. Vanilla equivalents: `TagKey.create` (not `BlockTags.create`); anonymous `RecipeType` (not `RecipeType.simple`); `inventoryTick` (not `onArmorTick`); subclass protected tool constructors (`ModTools`).
- GeckoLib's Fabric build needs `com.eliotlash.mclib` from the GeckoLib Maven (allowed in `buildSrc/.../multiloader-common.gradle`).
- Fabric's GameTest server refuses to start with zero tests.
- GameTest saved data persists in `run/`; give test players fresh UUIDs (done in both wrappers).
- An old `run/` world can report "unidentified mappings" after items are removed; delete the GameTest world.
- `spotlessApply` runs before every compile and reformats Java.
- Fabric's GameTest server ignores datapack dimensions; test dimension travel on Forge only.
- `Item.Properties` can't have both `durability` and `stacksTo`; entity loot providers reject tables for non-living entities.

## How to update this file

- After each milestone (a step done, a PR opened, a decision made, a blocker found), add a dated entry at the top of **Done** with what changed, where (paths), and how it was verified. Update **Current state**, **Open questions** and **Next**.
- Record facts, not intentions: commands that passed, commit hashes, PR numbers. If something wasn't verified, say so.
- Keep it under about 250 lines: fold old entries into one-line summaries once they're merged.
- Durable rules belong in `CLAUDE.md`, data formats in `docs/`, this file holds the moving state.
