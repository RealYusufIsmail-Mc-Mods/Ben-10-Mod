# Multi-loader layout (Forge + Fabric)

One codebase builds two jars: `forge/build/libs/bentenmod-forge-1.20.1-<version>.jar` (also meant for NeoForge 1.20.1) and `fabric/build/libs/bentenmod-fabric-1.20.1-<version>.jar`. The layout follows [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template) (1.20.1 branch).

## How the build shares code

- `common` compiles against vanilla Minecraft with Mojang/Parchment names via ModDevGradle's `mcpVersion`. Forge and Fabric classes aren't on its classpath, so loader code in common fails to compile.
- `buildSrc/.../multiloader-loader.gradle` compiles common's Java into each loader's jar and copies common's resources plus `common/src/generated/resources`.
- The Forge data run writes to `common/src/generated/resources`, so recipes, loot tables, tags, models and worldgen are generated once and shipped in both jars. Forge-only data (`data/bentenmod/forge/biome_modifier`) is ignored by Fabric.

## The platform layer

`common/.../platform/`:

| Piece | What it does | Forge | Fabric |
|---|---|---|---|
| `RegistrationProvider<T>` / `RegistryObject<T>` | Registration with the same call shape as Forge's `DeferredRegister` | `ForgeRegistrationFactory` (a real `DeferredRegister` on the mod bus) | `FabricRegistrationFactory` (registers immediately) |
| `IPlatformHelper` via `Services.PLATFORM` | The handful of loader-specific operations | `ForgePlatformHelper` | `FabricPlatformHelper` |

Implementations are found with `java.util.ServiceLoader` through `META-INF/services/<interface>` files in each loader module.

Current `IPlatformHelper` methods and why they exist:

| Method | Reason |
|---|---|
| `creativeTabBuilder()` | Fabric needs `FabricItemGroup.builder()` to place the tab |
| `createMenuType(...)` | vanilla's `MenuType.MenuSupplier` is package-private |
| `itemStackFromJson(...)` | Forge's reader also supports an `nbt` result |
| `createOmnitrixShapedRecipe(...)` | Forge adds `IShapedRecipe` so its recipe book places ingredients correctly |
| `hasOmnitrixRecipeBook()` / `omnitrixRecipeBookType()` | custom recipe book types exist only on Forge |
| `setCraftingPlayer(...)` / `onItemCrafted(...)` | Forge crafting hooks; no-ops on Fabric |

Add a method only when common code genuinely can't do the thing with vanilla classes. Prefer vanilla first: for example, armour effects use `Item#inventoryTick` instead of Forge's `onArmorTick`, and tool classes are subclassed (`ModTools`) instead of using an access transformer.

## Where each concern lives

| Concern | Common | Forge | Fabric |
|---|---|---|---|
| Registration of items/blocks/entities/menus/recipes/tabs | `core/init/*Init` | — | — |
| Entity attributes | `BenTenMod.registerEntityAttributes` | `EntityAttributeCreationEvent` | `FabricDefaultAttributeRegistry` |
| Ores in biomes | `ModOrePlacements.OVERWORLD_ORES` / `NETHER_ORES` | biome modifier JSON (data run) | `BiomeModifications.addFeature` |
| Screens, renderers, model layers, item colours | client classes | `BenTenModForgeClient` events | `BenTenModFabricClient` registries |
| Data generation | — | `forge/.../data` | uses the generated output |
| Data-pack JSON (aliens, armour sets) | `BenTenMod.reloadListeners()` | `AddReloadListenerEvent` | `ResourceManagerHelper` (wrapped with an id) |
| Ability ticking | `AbilityTicker.tick` / `forget` | `PlayerTickEvent` (END) / `PlayerLoggedOutEvent` | `ServerTickEvents.END_SERVER_TICK` / `ServerPlayConnectionEvents.DISCONNECT` |
| GameTests | `gametest/GameTestChecks` | `ForgeGameTests` (`bentenmod:empty` structure, `FakePlayerFactory`) | `FabricGameTests` (Fabric's empty structure, `FakePlayer`) |

## Recipe: add a new registered thing

1. Register it in the right `common/.../core/init/*Init` class with the class's `RegistrationProvider`.
2. If it's a new `*Init` class, call its `init()` from `BenTenMod.init()`.
3. If it needs a renderer, screen or colour: add it to both `BenTenModForgeClient` and `BenTenModFabricClient`.
4. If it needs data (recipe, loot, tag, model, lang): add it to the Forge data providers and run `:forge:runData`.
5. If the two loaders register it differently, add a check to `GameTestChecks` and to both test classes.
6. Run the verification ladder in `CLAUDE.md`.

## Known gaps

- NeoForge: the Forge jar has not been started on NeoForge 1.20.1 yet. GeckoLib publishes a separate NeoForge 1.20.1 build (4.4.9), so NeoForge players may need that one.
- Ore and storage tags use the `forge:` namespace. Fabric mods conventionally use `c:`; add `c:` tags if cross-mod compatibility on Fabric matters.
- GameTest classes ship in release jars; they only run when the GameTest server or property is enabled.
