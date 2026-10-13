---
name: mc-version-port
description: Port this Minecraft mod to a different Minecraft version or mod loader (Forge, NeoForge, Fabric), or bump Forge/GeckoLib/mappings. Use for "update to 1.21", "add Fabric", "bump Forge", or a build broken by a dependency upgrade.
---

# Minecraft version/loader port

Playbook from the 1.19.4 → 1.20.1 port (`docs/porting-1.20.1.md`).

## 1. Pick versions from real sources

- Forge: `https://maven.minecraftforge.net/net/minecraftforge/forge/maven-metadata.xml`
- NeoForge: `https://maven.neoforged.net/releases/net/neoforged/neoforge/maven-metadata.xml` (1.20.1 is `net/neoforged/forge`)
- GeckoLib: Modrinth API `project/geckolib/version?game_versions=["X"]&loaders=["forge"]`, or the cloudsmith Maven `software/bernie/geckolib/geckolib-<loader>-<mc>`
- Parchment: `https://maven.parchmentmc.org/org/parchmentmc/data/parchment-<mc>/maven-metadata.xml` (follow redirects)
- Fabric: `https://meta.fabricmc.net/v2/versions/loader/<mc>`; Fabric API from Modrinth `project/fabric-api/version`

Check which JDK the target needs (1.20.1: 17; 1.20.5+: 21; 26.x: 25) and which Gradle version can run on an installed JDK.

## 2. Bump the build, then let the compiler list the work

Versions live in `gradle.properties`; plugin versions in the root `build.gradle`; loader metadata in `forge/src/main/resources/META-INF/mods.toml` and `fabric/src/main/resources/fabric.mod.json` (both templated from `gradle.properties`). Start from the matching MultiLoader-Template branch for build-script changes. Then:

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 21)" GRADLE_USER_HOME="$PWD/.gradle/user-home"
./gradlew :common:compileJava 2>&1 | grep -A3 "error:"   # vanilla API changes first
./gradlew build 2>&1 | grep -A3 "error:"                 # then each loader
```

Group the errors by cause. Check real signatures with `javap` against the jars in `.gradle/user-home` instead of guessing.

## 3. Fix by cause, not by file

Common causes: renamed fields/methods, removed classes (e.g. `Material` in 1.20), new registries (creative tabs), rendering API (`GuiGraphics`), data-gen provider constructors, recipe builder argument order. Keep behaviour identical; note anything that can't be.

## 4. Verify

1. `./gradlew build` (both jars).
2. `./gradlew :forge:runData`, then compare `common/src/generated/resources` with the previous output (parse the JSON and ignore known format additions). Only format changes from the new version should appear.
3. `./gradlew :forge:runGameTestServer :fabric:runGameTestServer`. Both must end with `All N required tests passed` and no `bentenmod` errors. This catches client classes on a dedicated server and loader wiring differences.
4. Ask for `:forge:runClient` and `:fabric:runClient` checks of screens, renderers and models. Don't claim visual behaviour works without them.

## Loader notes

- NeoForge 1.20.1 is Forge 47.1 under another name: test the Forge jar on it rather than porting.
- From 1.20.2 on, NeoForge is a separate loader: add a `neoforge/` module next to `forge/` and `fabric/` (MultiLoader-Template has NeoForge branches), with its own `RegistrationProvider.Factory` and `IPlatformHelper` implementations.
- Keep game logic in `common/` with vanilla classes only; see `docs/multiloader.md` and the `multiloader-content` skill.
