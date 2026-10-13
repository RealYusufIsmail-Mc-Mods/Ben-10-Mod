# Port from 1.19.4 to 1.20.1 (October 2026)

What changed and why, so the next port starts from known ground.

## Build

- Minecraft 1.20.1, Forge 47.4.28, GeckoLib 4.8.4, Parchment 2023.09.03-1.20.1.
- Gradle wrapper 8.1.1 → 8.8 and foojay resolver 0.5.0 → 0.8.0, because Gradle 8.1.1 can't run on any installed JDK (21, 23, 25). The Java toolchain stays 17.
- Removed `realyusufismailcore` and Lombok.

## Code changes

| Area | 1.19.4 | 1.20.1 |
|---|---|---|
| Entity level | `entity.level` field | `entity.level()` |
| Block properties | `Properties.of(Material.STONE)` | `Properties.of().mapColor(MapColor.STONE)` |
| Creative tab | `CreativeModeTabEvent.Register` | `DeferredRegister<CreativeModeTab>` (`BenTenModItemGroup.TABS`) |
| Screens | `PoseStack` + `RenderSystem.setShaderTexture` + `blit` | `GuiGraphics` and `guiGraphics.blit(texture, ...)` |
| Recipe holder | `awardUsedRecipes(player)` | `awardUsedRecipes(player, ingredients)` |
| Item comparison | `isSame` + `tagMatches` | `ItemStack.isSameItemSameTags` |
| Loot validation | `LootTables.validate(...)` | `lootTable.validate(ctx.setParams(...).enterElement(..., new LootDataId<>(...)))` |
| Core library classes | `GeneralBlock`, `Yusuf*RecipeBuilder`, `EnchantmentRecipeProvider`, `ModEnLangProviderSupport`, `COPPER`/`COPPER_BLOCK` | `Block`, vanilla `ShapedRecipeBuilder`/`ShapelessRecipeBuilder`/`SimpleCookingRecipeBuilder` (note: argument order is `(Ingredient, RecipeCategory, ...)`), Forge `LanguageProvider`, vanilla copper |

## Bugs found on the way

- **Dedicated servers crashed on startup** (`Attempted to load class EntityRenderer for invalid dist DEDICATED_SERVER`). This was already broken before the port. Client registration moved from `BenTenMod` into `client/BenTenModClient`.
- The recipe book categories listener was registered twice. It's now registered once, client-side.
- The Heatblast sword recipe asked for Fire Aspect through the core library, but the generated JSON never contained it. Behaviour is unchanged: the sword is crafted unenchanted.

## Generated data

`runData` output changed only in expected ways: 1.20 adds `"category"` to cooking recipes and `"sends_telemetry_event"` to advancements, and the copper advancements now point at `minecraft:copper_ingot`/`copper_block`.
