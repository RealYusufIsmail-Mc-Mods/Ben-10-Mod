# Ben 10 Mod

Minecraft 1.20.1 mod (`modid = bentenmod`) for Forge (also meant to load on NeoForge 1.20.1) and Fabric, from one codebase. Published on CurseForge as project 459985. Ores, tools, armour, the Omnitrix Crafter, the Kraab entity, and (in progress) GeckoLib 3D aliens with an Omnitrix transformation.

## Handoff

`docs/HANDOFF.md` is the running work log (current state, done, decisions, open questions, next step). It's loaded automatically at session start, on resume and after compaction (`.claude/settings.json` hook). Read it before working, and update it after every milestone using the `handoff` skill.

## Versions

All in `gradle.properties`.

| | |
|---|---|
| Minecraft | 1.20.1 |
| Forge | 47.4.28 (NeoForge 1.20.1 is a fork of Forge 47.1; the Forge jar is meant to load there unchanged) |
| Fabric | Loader 0.16.14, Fabric API 0.92.12+1.20.1 |
| GeckoLib | 4.8.4 (`geckolib-forge-1.20.1` / `geckolib-fabric-1.20.1`) |
| Mappings | Mojang + Parchment 2023.09.03 |
| Build | Gradle 8.11, ModDevGradle (legacyforge) 2.0.77, Loom 1.9, Java toolchain 17 |

No `realyusufismailcore`, Lombok or Logback: removed because they were unused or blocked Fabric.

## Layout

```
common/   all game code, all assets, generated data. Compiles against vanilla Minecraft only.
  src/main/java/.../platform/       RegistrationProvider, RegistryObject, Services, IPlatformHelper
  src/main/resources/assets/        textures, GeckoLib geo/animations, lang, models
  src/generated/resources/          output of the Forge data run, shipped in BOTH jars
forge/    @Mod entry, Forge registration + platform impl, client events, data generators, recipe book
fabric/   ModInitializer entries, Fabric registration + platform impl, Fabric API hooks
buildSrc/ shared Gradle conventions (multiloader-common, multiloader-loader)
```

Rules:
- Game logic goes in `common`. If it needs something only one loader has, add a method to `IPlatformHelper` and implement it in `ForgePlatformHelper` and `FabricPlatformHelper`. Keep that interface small.
- Register through `RegistrationProvider.get(Registries.X, BenTenMod.MOD_ID)`, never `DeferredRegister` or `Registry.register` directly in common.
- New `*Init` registry classes need an `init()` call in `BenTenMod.init()`. Fabric registers immediately, in that order.
- Client-only classes (`net.minecraft.client.*`) may live in common but must only be referenced from client entry points (`BenTenModForgeClient`, `BenTenModFabricClient`).
- Assets and data live only in `common`. Never copy a texture or JSON into a loader module.
- Forge-only features: the Omnitrix Crafter recipe book (custom `RecipeBookType`). Fabric has no API for it, so the crafter screen hides the button there.

See [docs/multiloader.md](docs/multiloader.md) for worked examples.

## Build and verify

Gradle 8.11 doesn't run on JDK 25 (the machine default), so use JDK 21. A Gradle home inside the repo keeps the cache with the worktree:

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 21)"
export GRADLE_USER_HOME="$PWD/.gradle/user-home"   # .gradle/ is gitignored
./gradlew build                          # both jars: forge/build/libs, fabric/build/libs
./gradlew :forge:runData                 # regenerates common/src/generated/resources
./gradlew :forge:runGameTestServer       # headless Forge server + GameTests
./gradlew :fabric:runGameTestServer      # headless Fabric server + GameTests
./gradlew :forge:runClient / :fabric:runClient
```

Verification ladder for any change, in order:
1. `./gradlew build` passes for both loaders.
2. If data providers changed: `:forge:runData`, then read the generated diff and explain every change.
3. Both `runGameTestServer` tasks end with `All N required tests passed`. The tests (`common/.../gametest/GameTestChecks`) check entity attributes, shipped worldgen and recipes, and block placement on each loader. Add a check when you add something that each loader registers differently.
4. Anything visual (models, screens, effects) still needs `runClient` on each loader. Say so instead of claiming it works.

## 3D aliens and the Omnitrix

Assets are generated, not hand-drawn. See [docs/assets.md](docs/assets.md) and the `alien-assets` skill. Generator: `tools/alien-gen/` (Python, numpy + Pillow), which writes into `common/src/main/resources/assets/bentenmod`.

## Direction (decided 2026-10-13)

1. Done: port to 1.20.1; drop the core library, Lombok and Logback; split client code out.
2. Done: multi-loader layout (common/forge/fabric), shared GameTests, ores actually added to biomes.
3. Done (step 2): aliens and armour-set bonuses are JSON with reusable abilities ([docs/data-aliens-and-abilities.md](docs/data-aliens-and-abilities.md)); `AlienGeoModel` resolves every alien's GeckoLib assets by id. Common compiles against GeckoLib via `modCompileOnly` of the Forge artifact (identical API to the Fabric one).
4. Done: the transformation system (Omnitrix item, player state + sync, dial screen, flash, timeout), written once in common, using `Aliens.DATA` and `AbilityTicker`.
5. Current progression: steps 1–7 in [docs/design/progression.md](docs/design/progression.md) are implemented in this worktree; run Forge data generation, build and both GameTest suites before marking them verified.

## Working agreements

- Model routing for this repo: Opus does the Minecraft modding itself (design, ports, hard code). Sonnet subagents apply bulk or mechanical edits. Haiku scouts and checks; Codex GPT-6 Luna max handles large detached jobs only when asked.
- One agent edits the checkout at a time.
- Don't commit or push unless asked.

## Skills

- `.claude/skills/alien-assets`: add or change an alien (model, texture, animations and its JSON definition) or the Omnitrix visuals.
- `.claude/skills/multiloader-content`: add an item, block, entity, menu or other registered content so it works on Forge and Fabric.
- `.claude/skills/mc-version-port`: port to another Minecraft version or loader.
- `.claude/skills/handoff`: resume from `docs/HANDOFF.md`, and keep it updated.
