---
name: multiloader-content
description: Add or change registered content in the Ben 10 mod (item, block, entity, menu/screen, recipe type, creative tab, worldgen, armour effect) so it works on both Forge and Fabric. Use for any "add a new ...", any change that touches registration, events, client rendering hooks, or when code in common/ fails to compile because it used a Forge or Fabric API.
---

# Add content on both loaders

Read `docs/multiloader.md` first. Short version: write it once in `common/`, using vanilla classes and `RegistrationProvider`; add per-loader hooks only where a loader API is unavoidable.

## Steps

1. **Registration**: add it to the matching `common/src/main/java/.../core/init/*Init` class:
   ```java
   public static final RegistryObject<Item> FOO = ITEMS.register("foo", () -> new Item(new Item.Properties()));
   ```
   For a new `*Init` class: create its `RegistrationProvider.get(Registries.X, BenTenMod.MOD_ID)` and call `XInit.init()` from `BenTenMod.init()`, in dependency order.
2. **Behaviour**: implement with vanilla methods. If a Forge-only method seems needed (`onArmorTick`, `IForgeItem`, `NetworkHooks`, `ForgeRegistries`, `BlockTags.create`...), find the vanilla equivalent first (`inventoryTick`, the default packet, `Registries`, `TagKey.create`). If a vanilla constructor or type is protected or package-private, subclass it in common (see `core/item/ModTools`) before reaching for access transformers.
3. **Platform-specific need**: add one method to `IPlatformHelper` with a comment saying why, then implement it in `forge/.../platform/ForgePlatformHelper` and `fabric/.../platform/FabricPlatformHelper`.
4. **Client**: register renderers, model layers, screens and item colours in both `forge/.../client/BenTenModForgeClient` (events) and `fabric/.../client/BenTenModFabricClient` (Fabric API registries).
5. **Events**: loader events go in the loader entry (`BenTenModForge` / `BenTenModFabric`) and call a shared method in common (see `BenTenMod.registerEntityAttributes`).
6. **Data**: add recipes/loot/tags/models/lang in `forge/.../data/*` and run `:forge:runData`. Output lands in `common/src/generated/resources` for both jars.
7. **Assets**: textures/models go in `common/src/main/resources/assets/bentenmod` only. 3D aliens come from `tools/alien-gen` (see the `alien-assets` skill).
8. **Test**: if each loader wires it differently, add a check to `common/.../gametest/GameTestChecks` and a one-line wrapper in `ForgeGameTests` and `FabricGameTests`.

## Verify

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 21)" GRADLE_USER_HOME="$PWD/.gradle/user-home"
./gradlew build
./gradlew :forge:runData                 # only if data changed; then review the generated diff
./gradlew :forge:runGameTestServer :fabric:runGameTestServer
```

Both servers must print `All N required tests passed`. Visual changes still need `:forge:runClient` and `:fabric:runClient`; say so if not done.
